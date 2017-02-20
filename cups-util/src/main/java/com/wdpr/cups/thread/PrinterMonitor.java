package com.wdpr.cups.thread;

import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;

import com.wdpr.cups.thread.local.LastAcceesMonitorIF;
import com.wdpr.cups.util.FileUtil;

public abstract class PrinterMonitor extends Thread {
	protected String fileToBeMonitored;
	protected String fileToBeCompared;
	private LastAcceesMonitorIF lastAcceesMonitorIF;
	private long poolingInterval;

	protected PrinterMonitor(final String fileToBeMonitored, final String fileToBeComparedOrOutputFile, final LastAcceesMonitorIF lastAcceesMonitorIF, final long poolingInterval) {
		this.fileToBeMonitored = fileToBeMonitored;
		this.fileToBeCompared = fileToBeComparedOrOutputFile;
		this.lastAcceesMonitorIF = lastAcceesMonitorIF;
		this.poolingInterval = poolingInterval;
	}
	
	@PostConstruct
	private void postConstruct() {
		if (this.fileToBeMonitored == null || this.fileToBeCompared == null || this.lastAcceesMonitorIF == null || poolingInterval < 0) 
			throw new IllegalStateException("Required parameter missing : fileToBeMonitored = [" + this.fileToBeMonitored + "] fileToBeComparedOrOutputFile = [" + this.fileToBeCompared + "] lastAcceesMonitorIF = [" + this.lastAcceesMonitorIF + "]");
		FileUtil.validate(this.fileToBeMonitored);
		this.lastAcceesMonitorIF.setLastAccess(FileUtil.getLastAccessDate(this.fileToBeMonitored));
	}

	@Override
	public void run() {
		do {
			try {
				sync();
				Thread.sleep(this.poolingInterval);
			} catch (final IOException | InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);
	}

	private void sync() throws IOException, InterruptedException {
		if (FileUtil.isFileValid(this.fileToBeMonitored)) {
			final Date date = this.lastAcceesMonitorIF.getLastAccess();
			final Date currDate = FileUtil.getLastAccessDate(this.fileToBeMonitored);
			if (date == null || currDate.after(date)) {
				if (execute(this.fileToBeMonitored, this.fileToBeCompared)) {
					this.lastAcceesMonitorIF.setLastAccess(currDate);
				}
			}
		}
	}

	protected abstract boolean execute(final String fileToBeMonitored, final String fileToBeCompared) throws IOException, InterruptedException ;

}
