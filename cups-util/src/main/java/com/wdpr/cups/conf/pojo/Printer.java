package com.wdpr.cups.conf.pojo;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Printer implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Printer.class);
	private String printer;
	private String uuid;
	private String info;
	private String location;
	private String makeModel;
	private String deviceURI;
	private String pPDTimeStamp;
	private String state;
	private String stateTime;
	private String type;
	private String accepting;
	private String shared;
	private String colorManaged;
	private String jobSheets;
	private String quotaPeriod;
	private String pageLimit;
	private String kLimit;
	private String opPolicy;
	private String errorPolicy;
	private boolean dirty = false;
	private boolean printerDirty = false;
	private boolean uuidDirty = false;
	private boolean infoDirty = false;
	private boolean locationDirty = false;
	private boolean makeModelDirty = false;
	private boolean deviceURIDirty = false;
	private boolean pPDTimeStampDirty = false;
	private boolean stateDirty = false;
	private boolean stateTimeDirty = false;
	private boolean typeDirty = false;
	private boolean acceptingDirty = false;
	private boolean sharedDirty = false;
	private boolean colorManagedDirty = false;
	private boolean jobSheetsDirty = false;
	private boolean quotaPeriodDirty = false;
	private boolean pageLimitDirty = false;
	private boolean kLimitDirty = false;
	private boolean opPolicyDirty = false;
	private boolean errorPolicyDirty = false;

	public Printer(final String printer, final String deviceURI) {
		if (printer == null || deviceURI == null)
			throw new IllegalStateException("Printer name and device url can not be null..");
		this.printer = printer;
		this.deviceURI = deviceURI;
	}
	
	public boolean isDirty() {
		return dirty;
	}

	public boolean isPrinterDirty() {
		return printerDirty;
	}

	public boolean isUuidDirty() {
		return uuidDirty;
	}

	public boolean isInfoDirty() {
		return infoDirty;
	}

	public boolean isLocationDirty() {
		return locationDirty;
	}

	public boolean isMakeModelDirty() {
		return makeModelDirty;
	}

	public boolean isDeviceURIDirty() {
		return deviceURIDirty;
	}

	public boolean ispPDTimeStampDirty() {
		return pPDTimeStampDirty;
	}

	public boolean isStateDirty() {
		return stateDirty;
	}

	public boolean isStateTimeDirty() {
		return stateTimeDirty;
	}

	public boolean isTypeDirty() {
		return typeDirty;
	}

	public boolean isAcceptingDirty() {
		return acceptingDirty;
	}

	public boolean isSharedDirty() {
		return sharedDirty;
	}

	public boolean isColorManagedDirty() {
		return colorManagedDirty;
	}

	public boolean isJobSheetsDirty() {
		return jobSheetsDirty;
	}

	public boolean isQuotaPeriodDirty() {
		return quotaPeriodDirty;
	}

	public boolean isPageLimitDirty() {
		return pageLimitDirty;
	}

	public boolean iskLimitDirty() {
		return kLimitDirty;
	}

	public boolean isOpPolicyDirty() {
		return opPolicyDirty;
	}

	public boolean isErrorPolicyDirty() {
		return errorPolicyDirty;
	}

	public void setPrinter(String printer) {
		this.printer = printer;
	}

	public void setDeviceURI(String deviceURI) {
		this.deviceURI = deviceURI;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getMakeModel() {
		return makeModel;
	}


	public void setMakeModel(String makeModel) {
		this.makeModel = makeModel;
	}


	public String getpPDTimeStamp() {
		return pPDTimeStamp;
	}


	public void setpPDTimeStamp(String pPDTimeStamp) {
		this.pPDTimeStamp = pPDTimeStamp;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getStateTime() {
		return stateTime;
	}


	public void setStateTime(String stateTime) {
		this.stateTime = stateTime;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getAccepting() {
		return accepting;
	}


	public void setAccepting(String accepting) {
		this.accepting = accepting;
	}


	public String getShared() {
		return shared!=null && "Yes".equalsIgnoreCase(shared)? "true" : "false";
	}


	public void setShared(String shared) {
		this.shared = shared;
	}


	public String getColorManaged() {
		return colorManaged;
	}


	public void setColorManaged(String colorManaged) {
		this.colorManaged = colorManaged;
	}


	public String getJobSheets() {
		return jobSheets != null ? jobSheets.replaceAll(" ", ","): "none,none";
	}


	public void setJobSheets(String jobSheets) {
		this.jobSheets = jobSheets;
	}


	public String getQuotaPeriod() {
		return quotaPeriod;
	}


	public void setQuotaPeriod(String quotaPeriod) {
		this.quotaPeriod = quotaPeriod;
	}


	public String getPageLimit() {
		return pageLimit;
	}


	public void setPageLimit(String pageLimit) {
		this.pageLimit = pageLimit;
	}


	public String getkLimit() {
		return kLimit;
	}


	public void setkLimit(String kLimit) {
		this.kLimit = kLimit;
	}


	public String getOpPolicy() {
		return opPolicy;
	}


	public void setOpPolicy(String opPolicy) {
		this.opPolicy = opPolicy;
	}


	public String getErrorPolicy() {
		return errorPolicy;
	}


	public void setErrorPolicy(String errorPolicy) {
		this.errorPolicy = errorPolicy;
	}


	public String getPrinter() {
		return printer;
	}


	public String getDeviceURI() {
		return deviceURI;
	}

	public boolean equals(final Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Printer))
			return false;
		final Printer other = (Printer) o;
		if (!this.printer.equals(other.printer))
			return false;
		makeDirty(other);
		return true;
	}
	
	private boolean comapre(final String str1, final String str2) {
		if(str1 == null && str2 == null) return true;
		if(str1 == null && str2 != null) return false;
		if(str1 != null && str2 == null) return false;
		return str1.equals(str2);
	}

	private void makeDirty(final Printer other) {
		
		if(!comapre(this.info, other.info)) {
			logger.error("Info dirty: ["+this.info+"]["+other.info+"]");
			this.infoDirty = true;
			other.infoDirty = true;
			dirty(other);
		}
		if(!comapre(this.location, other.location)) {
			logger.error("Location dirty: ["+this.location+"]["+other.location+"]");
			this.locationDirty = true;
			other.locationDirty = true;
			dirty(other);
		}
		if(!comapre(this.makeModel, other.makeModel)) {
			logger.error("makeModel dirty: ["+this.makeModel+"]["+other.makeModel+"]");
			this.makeModelDirty = true;
			other.makeModelDirty = true;
			dirty(other);
		}
		if(!comapre(this.deviceURI, other.deviceURI)) {
			logger.error("deviceURI dirty: ["+this.deviceURI+"]["+other.deviceURI+"]");
			this.deviceURIDirty = true;
			other.deviceURIDirty = true;
			dirty(other);
		}
		/*if(!comapre(this.pPDTimeStamp, other.pPDTimeStamp)) {
			logger.error("pPDTimeStamp dirty: ["+this.pPDTimeStamp+"]["+other.pPDTimeStamp+"]");
			this.pPDTimeStampDirty = true;
			other.pPDTimeStampDirty = true;
			dirty(other);
		}*/
		/*if(!comapre(this.state, other.state)) {
			logger.error("state dirty: ["+this.state+"]["+other.state+"]");
			this.stateDirty = true;
			other.stateDirty = true;
			dirty(other);
		}*/
		/*if(!comapre(this.stateTime, other.stateTime)) {
			logger.error("stateTime dirty: ["+this.stateTime+"]["+other.stateTime+"]");
			this.stateTimeDirty = true;
			other.stateTimeDirty = true;
			dirty(other);
		}*/
		/*if(!comapre(this.type, other.type)) {
			logger.error("type dirty: ["+this.type+"]["+other.type+"]");
			this.typeDirty = true;
			other.typeDirty = true;
			dirty(other);
		}*/
		/*if(!comapre(this.accepting, other.accepting)) {
			logger.error("accepting dirty: ["+this.accepting+"]["+other.accepting+"]");
			this.acceptingDirty = true;
			other.acceptingDirty = true;
			dirty(other);
		}*/
		if(!comapre(this.shared, other.shared)) {
			logger.error("shared dirty: ["+this.shared+"]["+other.shared+"]");
			this.sharedDirty = true;
			other.sharedDirty = true;
			dirty(other);
		}
		/*if(!comapre(this.colorManaged, other.colorManaged)) {
			logger.error("colorManaged dirty: ["+this.colorManaged+"]["+other.colorManaged+"]");
			this.colorManagedDirty = true;
			other.colorManagedDirty = true;
			dirty(other);
		}*/
		if(!comapre(this.jobSheets, other.jobSheets)) {
			logger.error("jobSheets dirty: ["+this.jobSheets+"]["+other.jobSheets+"]");
			this.jobSheetsDirty = true;
			other.jobSheetsDirty = true;
			dirty(other);
		}
		if(!comapre(this.quotaPeriod, other.quotaPeriod)) {
			logger.error("quotaPeriod dirty: ["+this.quotaPeriod+"]["+other.quotaPeriod+"]");
			this.quotaPeriodDirty = true;
			other.quotaPeriodDirty = true;
			dirty(other);
		}
		if(!comapre(this.pageLimit, other.pageLimit)) {
			logger.error("pageLimit dirty: ["+this.pageLimit+"]["+other.pageLimit+"]");
			this.pageLimitDirty = true;
			other.pageLimitDirty = true;
			dirty(other);
		}
		if(!comapre(this.kLimit, other.kLimit)) {
			logger.error("kLimit dirty: ["+this.kLimit+"]["+other.kLimit+"]");
			this.kLimitDirty = true;
			other.kLimitDirty = true;
			dirty(other);
		}
		if(!comapre(this.opPolicy, other.opPolicy)) {
			logger.error("opPolicy dirty: ["+this.opPolicy+"]["+other.opPolicy+"]");
			this.opPolicyDirty = true;
			other.opPolicyDirty = true;
			dirty(other);
		}
		if(!comapre(this.errorPolicy, other.errorPolicy)) {
			logger.error("errorPolicy dirty: ["+this.errorPolicy+"]["+other.errorPolicy+"]");
			this.errorPolicyDirty = true;
			other.errorPolicyDirty = true;
			dirty(other);
		}
	}

	private void dirty(final Printer other) {
		this.dirty = true;
		other.dirty = true;
	}

	public int hashCode() {
		return printer.hashCode();
	}

	@Override
	public String toString() {
		return "{Printer [" + printer + "] " + 
			"Info [" + info  + "] " +
			"UUID [" + uuid + "] " +
			"Location [" + location + "] " +
			"MakeModel [" + makeModel + "] " +
			"DeviceURI [" + deviceURI + "] " +
			"Shared [" + shared + "] " +
			"KLimit [" + kLimit + "] " +
			"PageLimit [" + pageLimit + "] " +
			"OpPolicy [" + opPolicy + "] " +
			"ErrorPolicy [" + errorPolicy + "] " +
			"JobSheets [" + jobSheets + "] " +
			"QuotaPeriod [" + quotaPeriod + "] " +
			"PPDTimeStamp [" + pPDTimeStamp + "] " +
			"State [" + state + "] " +
			"StateTime [" + stateTime + "] " +
			"Type [" + type + "] " +
			"Accepting [" + accepting + "] " +
			"ColorManaged " + colorManaged + "]}";
	}
	
}
