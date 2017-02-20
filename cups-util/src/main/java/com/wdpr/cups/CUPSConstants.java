package com.wdpr.cups;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public interface CUPSConstants {
	static final String DOT = ".";
	static final String SPACE = " ";
	static final String QUAT = "\"";
	static final String ADD_OP = "add";
	static final String UPDATE_OP = "update";
	static final String DELETE_OP = "delete";
	static final String ROOT_TAG_NAME = "Printer";
	static final String PRINTER_COMMAND = new StringBuilder().append(File.separator +"usr" + File.separator + "sbin" + File.separator + "lpadmin").toString();
	static final String PRINTER_INFO_COMMAND = new StringBuilder().append(File.separator +"usr" + File.separator + "sbin" + File.separator + "lpinfo").toString();
	static final String PRINTER_NAME_OPTION = "-p";
	static final String OR_OPERATOR = "|";
	static final String GREP_COMMAND = "grep";
	static final String PRINTER_ENABLE_OPTION = "-E";
	static final String PRINTER_CLASSE_OPTION = "-c";
	static final String PRINTER_INTERFACE_OPTION = "-i";
	static final String PRINTER_MODEL_OPTION = "-m";
	static final String PRINTER_OPTION = "-o";
	static final String PRINTER_SHARE_OPTION = "printer-is-shared=";
	static final String PRINTER_JOB_KLIMIT_OPTION = "job-k-limit=";
	static final String PRINTER_JOB_PAGE_LIMIT_OPTION = "job-page-limit=";
	static final String PRINTER_OP_POLICY_OPTION = "printer-op-policy=";
	static final String PRINTER_ERROR_POLICY_OPTION = "printer-error-policy=";
	static final String PRINTER_JOB_SHEET_BANNERS_OPTION = "job-sheets-default=";
	static final String PRINTER_JOB_QUOTA_PERIOD_OPTION = "job-quota-period=";
	static final String PRINTER_DEVICE_URL_OPTION = "-v";
	static final String PRINTER_USER_OPTION = "-u";
	static final String PRINTER_INFO_OPTION = "-D";
	static final String PRINTER_LOCATION_OPTION = "-L";
	static final String PRINTER_DELETE_OPTION = "-x";
	static final String PRINTER_OPTION_REMOVE_OPTION = "-R";
	static final String PRINTER_REMOVE_FROM_CLASS_OPTION = "-r";
	static final String[] ATTRIBUTES = { "Printer", "UUID", "Info", "Location", "MakeModel", "DeviceURI", "PPDTimeStamp", "State", "StateTime",
			"Type", "Accepting", "Shared", "ColorManaged", "JobSheets", "QuotaPeriod", "PageLimit", "KLimit", "OpPolicy", "ErrorPolicy" };
	static final Map<String, String> MODEL_MAP = new HashMap<String, String>();
	
}
