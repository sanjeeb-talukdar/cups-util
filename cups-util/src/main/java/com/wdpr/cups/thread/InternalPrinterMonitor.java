package com.wdpr.cups.thread;

import java.io.IOException;

import com.wdpr.cups.thread.local.InternalPrinters;
import com.wdpr.cups.util.FileUtil;

public class InternalPrinterMonitor extends PrinterMonitor {
	public InternalPrinterMonitor(final String internalCupsFileName, final String outputFileName, final long poolingInterval) {
		super(internalCupsFileName, outputFileName, InternalPrinters.getInstance(), poolingInterval);
	}

	@Override
	protected boolean execute(final String internalCupsFileName, final String outputFileName) throws IOException {
		/** Write output file */
		return FileUtil.copy(internalCupsFileName, outputFileName);
	}

}
