package com.wdpr.cups.thread.local;

import java.util.Date;

public final class InternalPrinters  implements LastAcceesMonitorIF {
	private static final InternalPrinters _SELF = new InternalPrinters();
	private final ThreadLocal<Date> lastTimeStamp = new ThreadLocal<Date>();
	private InternalPrinters(){}
	
	public static InternalPrinters getInstance(){
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
