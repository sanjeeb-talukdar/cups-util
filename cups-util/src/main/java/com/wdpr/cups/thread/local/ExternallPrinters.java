package com.wdpr.cups.thread.local;

import java.util.Date;

public final class ExternallPrinters implements LastAcceesMonitorIF {
	private static final LastAcceesMonitorIF _SELF = new ExternallPrinters();
	private final ThreadLocal<Date> lastTimeStamp = new ThreadLocal<Date>();
	private ExternallPrinters(){}
	
	public static LastAcceesMonitorIF getInstance(){
		return _SELF;
	}
	
	/* (non-Javadoc)
	 * @see com.wdpr.cups.thread.local.LastAcceesMonitorIF#setLastAccess(java.util.Calendar)
	 */
	@Override
	public void setLastAccess(final Date c) {
		lastTimeStamp.set(c);
	}
	
	/* (non-Javadoc)
	 * @see com.wdpr.cups.thread.local.LastAcceesMonitorIF#getLastAccess()
	 */
	@Override
	public Date getLastAccess() {
		return lastTimeStamp.get();
	}
}
