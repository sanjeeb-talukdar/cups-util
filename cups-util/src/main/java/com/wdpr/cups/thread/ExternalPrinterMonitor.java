package com.wdpr.cups.thread;

import static com.wdpr.cups.CUPSConstants.ADD_OP;
import static com.wdpr.cups.CUPSConstants.DELETE_OP;
import static com.wdpr.cups.CUPSConstants.UPDATE_OP;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wdpr.cups.command.Command;
import com.wdpr.cups.command.PrinterCommand;
import com.wdpr.cups.conf.PrinterReader;
import com.wdpr.cups.conf.pojo.Printer;
import com.wdpr.cups.thread.local.ExternallPrinters;
import com.wdpr.cups.util.CommandUtil;
import com.wdpr.cups.util.FileUtil;

public class ExternalPrinterMonitor extends PrinterMonitor {
	private static final Logger logger = LoggerFactory.getLogger(ExternalPrinterMonitor.class);
	public ExternalPrinterMonitor(final String externalInputFileName, final String internalCupsFileName, final long poolingInterval) {
		super(externalInputFileName, internalCupsFileName, ExternallPrinters.getInstance(), poolingInterval);
	}
	
	@PostConstruct
	private void postConstruct() {
		FileUtil.validate(this.fileToBeCompared);
	}

	@Override
	protected boolean execute(final String inputFileName, final String internalCupsFileName) throws IOException, InterruptedException {
		/** create the existing printers set and compare with external printers */
		final Command<Printer> command = new PrinterCommand();
		final Set<Printer> prnMonitored = new PrinterReader(inputFileName).readObject();
		final Set<Printer> prnCompared = new PrinterReader(internalCupsFileName).readObject();
		final Map<String, Set<Printer>> mapOperations = mapOperations(prnMonitored, prnCompared);
		for (final Map.Entry<String, Set<Printer>> printerOps : mapOperations.entrySet()) {
			final String operation = printerOps.getKey();
			final Set<Printer> opPrns = printerOps.getValue();
			switch (operation) {
				case ADD_OP: 
					CommandUtil.executeCommands(command.add(opPrns));
					break;
				case UPDATE_OP: 
					// TODO Implement update pending
					CommandUtil.executeCommands(command.update(opPrns));
					break;
				case DELETE_OP: 
					CommandUtil.executeCommands(command.delete(opPrns));
					break;
				default:
					continue;
			}
		}
		return true;
	}
	
	private Map<String, Set<Printer>> mapOperations(final Set<Printer> prnMonitored, final Set<Printer> prnCompared) {
		final Map<String, Set<Printer>> opMap = new LinkedHashMap<String, Set<Printer>>();
		final Set<Printer> opAdd = new HashSet<Printer>(prnMonitored);
		final Set<Printer> opUpdate = new HashSet<Printer>(prnMonitored);
		final Set<Printer> opDelete = new HashSet<Printer>(prnCompared);
		opUpdate.retainAll(prnCompared);
		opDelete.removeAll(prnMonitored);
		opAdd.removeAll(prnCompared);
		logger.error("Add : " + opAdd);
		logger.error("Update : " + opUpdate);
		logger.error("Delete : " + opDelete);
		opMap.put(ADD_OP, opAdd);
		opMap.put(UPDATE_OP, opUpdate);
		opMap.put(DELETE_OP, opDelete);
		return opMap;
	}
}
