package com.wdpr.cups.command;

//import static com.wdpr.cups.CUPSConstants.MODEL_MAP;
import static com.wdpr.cups.CUPSConstants.MODEL_MAP;
import static com.wdpr.cups.CUPSConstants.PRINTER_COMMAND;
import static com.wdpr.cups.CUPSConstants.PRINTER_DELETE_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_DEVICE_URL_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_ENABLE_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_ERROR_POLICY_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_INFO_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_JOB_KLIMIT_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_JOB_PAGE_LIMIT_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_JOB_QUOTA_PERIOD_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_JOB_SHEET_BANNERS_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_LOCATION_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_MODEL_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_NAME_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_OP_POLICY_OPTION;
import static com.wdpr.cups.CUPSConstants.PRINTER_SHARE_OPTION;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wdpr.cups.conf.pojo.Printer;
import com.wdpr.cups.util.CommandUtil;

// <Printer MumbaiSecuredPrint>
// Info HP Universal Printing PCL 6 (v5.6.0)
// Location Aroli 3rd Floor
// MakeModel HP LaserJet M4555 MFP Postscript (recommended)
// DeviceURI ipp://10.74.158.206/ipp
// Shared Yes
// KLimit 0
// PageLimit 0
// OpPolicy default
// ErrorPolicy retry-job
// JobSheets none none
// QuotaPeriod 0
// PPDTimeStamp *
// State Idle
// StateTime 1421845448
// Type 8425684
// Accepting Yes
// ColorManaged Yes
// UUID urn:uuid:5d7bab02-4717-392a-56fa-4062d14d727a
// </Printer>
// usr/sbin/lpadmin -p MumbaiSecuredPrint -v ipp://10.74.158.206/ipp -D
// "HP Universal Printing PCL 6 (v5.6.0)" -L "Aroli 3rd Floor" -o
// printer-is-shared=true -o job-k-limit=0 -o job-page-limit=0 -o
// printer-op-policy=default -o printer-error-policy=retry-job -o
// job-sheets-default=none,none -o job-quota-period=0 -m
// /usr/lib/cups/driver/postscript-hp:0/ppd/hplip/HP/hp-laserjet_m4555_mfp-ps.ppd
// -E
public class PrinterCommand implements Command<Printer> {
	private static final Logger logger = LoggerFactory.getLogger(PrinterCommand.class);
	@Override
	public Set<List<String>> add(final Set<Printer> printers) {
		final Set<List<String>> set = new LinkedHashSet<List<String>>();
		for (final Printer printer : printers) {
			set.add(add(printer));
		}
		return set;
	}

	@Override
	public Set<List<String>> delete(final Set<Printer> printers) {
		final Set<List<String>> set = new LinkedHashSet<List<String>>();
		for (final Printer printer : printers) {
			final List<String> cmds = new LinkedList<String>();
			cmds.add(PRINTER_COMMAND);
			cmds.add(PRINTER_DELETE_OPTION);
			cmds.add(printer.getPrinter());
			set.add(cmds);
		}
		return set;
	}

	@Override
	public Set<List<String>> update(final Set<Printer> printers) {
		final Set<List<String>> set = new LinkedHashSet<List<String>>();
		for (final Printer printer : printers) {
			logger.error("Updateable :"+printer.toString() +" isDirty: " + printer.isDirty());
			if(printer.isDirty()) {
				set.add(update(printer));
			}
		}
		return set;
	}
	
	private List<String> update(final Printer printer) {
		final List<String> cmds = new LinkedList<String>();
		cmds.add(PRINTER_COMMAND);
		cmds.add(PRINTER_NAME_OPTION);
		cmds.add(printer.getPrinter());
		if(printer.isDeviceURIDirty()) {
			cmds.add(PRINTER_DEVICE_URL_OPTION);
			cmds.add(printer.getDeviceURI());
		}
		if(printer.isInfoDirty()) {
			cmds.add(PRINTER_INFO_OPTION);
			cmds.add(printer.getInfo());
		}
		if(printer.isLocationDirty()) {
			cmds.add(PRINTER_LOCATION_OPTION);
			cmds.add(printer.getLocation());
		}
		if(printer.isSharedDirty()) {
			cmds.add(PRINTER_OPTION);
			cmds.add(PRINTER_SHARE_OPTION + printer.getShared());
		}
		if(printer.iskLimitDirty()) {
			cmds.add(PRINTER_OPTION);
			cmds.add(PRINTER_JOB_KLIMIT_OPTION + printer.getkLimit());
		}
		if(printer.isPageLimitDirty()) {
			cmds.add(PRINTER_OPTION);
			cmds.add(PRINTER_JOB_PAGE_LIMIT_OPTION + printer.getPageLimit());
		}
		if(printer.isOpPolicyDirty()) {
			cmds.add(PRINTER_OPTION);
			cmds.add(PRINTER_OP_POLICY_OPTION + printer.getOpPolicy());
		}
		if(printer.isErrorPolicyDirty()) {
			cmds.add(PRINTER_OPTION);
			cmds.add(PRINTER_ERROR_POLICY_OPTION + printer.getErrorPolicy());
		}
		if(printer.isJobSheetsDirty()) {
			cmds.add(PRINTER_OPTION);
			cmds.add(PRINTER_JOB_SHEET_BANNERS_OPTION + printer.getJobSheets());
		}
		if(printer.isQuotaPeriodDirty()) {
			cmds.add(PRINTER_OPTION);
			cmds.add(PRINTER_JOB_QUOTA_PERIOD_OPTION + printer.getQuotaPeriod());
		}
		if(printer.isMakeModelDirty() && printer.getMakeModel() != null) {
			//"postscript-hp:0/ppd/hplip/HP/hp-laserjet_m4555_mfp-ps.ppd"
			final String driver = getDriver(printer.getMakeModel());
			if(driver!= null) {
				logger.error("Driver found : " + driver);
				cmds.add(PRINTER_MODEL_OPTION);
				cmds.add(driver);
			}
		}
		//cmds.add(PRINTER_ENABLE_OPTION);
		return cmds;
	}
	
	private String getDriver(final String string) {
		String driver = MODEL_MAP.get(string);
		if(driver == null) {
			driver = CommandUtil.getDriver(string);
			if(driver != null) {
				MODEL_MAP.put(string, driver);
			}
		}
		return driver;
	}

	private List<String> add(final Printer printer) {
		final List<String> cmds = new LinkedList<String>();
		cmds.add(PRINTER_COMMAND);
		cmds.add(PRINTER_NAME_OPTION);
		cmds.add(printer.getPrinter());
		cmds.add(PRINTER_DEVICE_URL_OPTION);
		cmds.add(printer.getDeviceURI());
		cmds.add(PRINTER_INFO_OPTION);
		cmds.add(printer.getInfo());
		cmds.add(PRINTER_LOCATION_OPTION);
		cmds.add(printer.getLocation());
		cmds.add(PRINTER_OPTION);
		cmds.add(PRINTER_SHARE_OPTION + printer.getShared());
		cmds.add(PRINTER_OPTION);
		cmds.add(PRINTER_JOB_KLIMIT_OPTION + printer.getkLimit());
		cmds.add(PRINTER_OPTION);
		cmds.add(PRINTER_JOB_PAGE_LIMIT_OPTION + printer.getPageLimit());
		cmds.add(PRINTER_OPTION);
		cmds.add(PRINTER_OP_POLICY_OPTION + printer.getOpPolicy());
		cmds.add(PRINTER_OPTION);
		cmds.add(PRINTER_ERROR_POLICY_OPTION + printer.getErrorPolicy());
		cmds.add(PRINTER_OPTION);
		cmds.add(PRINTER_JOB_SHEET_BANNERS_OPTION + printer.getJobSheets());
		cmds.add(PRINTER_OPTION);
		cmds.add(PRINTER_JOB_QUOTA_PERIOD_OPTION + printer.getQuotaPeriod());
		if(printer.getMakeModel() != null) {
			//"postscript-hp:0/ppd/hplip/HP/hp-laserjet_m4555_mfp-ps.ppd"
			final String driver = getDriver(printer.getMakeModel());
			if(driver != null) {
				logger.error("Driver found : " + driver);
				cmds.add(PRINTER_MODEL_OPTION);
				cmds.add(driver);
			}
		}
		cmds.add(PRINTER_ENABLE_OPTION);
		return cmds;
	}

}
