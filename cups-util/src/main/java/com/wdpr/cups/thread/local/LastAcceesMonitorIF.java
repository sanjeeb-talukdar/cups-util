package com.wdpr.cups.thread.local;

import java.util.Date;

public interface LastAcceesMonitorIF {

	abstract void setLastAccess(Date c);

	abstract Date getLastAccess();

}