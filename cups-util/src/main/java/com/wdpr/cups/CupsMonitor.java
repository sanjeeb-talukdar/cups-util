package com.wdpr.cups;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wdpr.cups.thread.ExternalPrinterMonitor;
import com.wdpr.cups.thread.InternalPrinterMonitor;
import com.wdpr.cups.util.CommandUtil;

public class CupsMonitor {
	private static final Logger logger = LoggerFactory.getLogger(CupsMonitor.class);
	static{
		try {
			CommandUtil.getDriver(null);
		} catch(final Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		if(args == null || args.length < 5) {
			logger.error("Usage java -jar <PATH>/cups-util.jar <EXTERNAL-FILE> <POLING-INTERVAL-EXTERNAL-FILE> <INTERNAL-FILE> <POLING-INTERVAL-INTERNAL-FILE> <OUTPUT-FILE>");
			return;
		}
		final String cupsExternalFile = args[0] != null ? args[0] : "/media/sf_share/conf/input/printers.conf.input";
		final String cupsInternalFile =  args[2] != null ? args[2] : "/etc/cups/printers.conf";
		final String cupsOutputFile =  args[4] != null ? args[4] : "/media/sf_share/conf/output/printers.conf.output";
		final long poolingIntervalForExternalFile = args[1] != null ? Long.valueOf(args[1].trim()) : 1000L;
		final long poolingIntervalForInternalFile = args[3] != null ? Long.valueOf(args[3].trim()) : 1000L;
		final ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new ExternalPrinterMonitor(cupsExternalFile, cupsInternalFile, poolingIntervalForExternalFile));
		executor.execute(new InternalPrinterMonitor(cupsInternalFile, cupsOutputFile, poolingIntervalForInternalFile));
		executor.shutdown();
	}
}
 