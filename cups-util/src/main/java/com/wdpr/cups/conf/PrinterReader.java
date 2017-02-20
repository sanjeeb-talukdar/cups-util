package com.wdpr.cups.conf;

import static com.wdpr.cups.CUPSConstants.ATTRIBUTES;
import static com.wdpr.cups.CUPSConstants.DOT;
import static com.wdpr.cups.CUPSConstants.ROOT_TAG_NAME;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wdpr.cups.conf.pojo.Printer;

public class PrinterReader implements ObjectReaderIF<Printer> {
	private String fileName;

	public PrinterReader(final String fileName) {
		if (fileName == null)
			throw new IllegalStateException("Please provide a valid filename and tag name..");
		this.fileName = fileName;
	}

	@Override
	public Set<Printer> readObject() {
		final Set<Printer> set = new HashSet<Printer>();
		final Map<Integer, List<String>> map = new LineParser(fileName).parse("r", '\r', "<" + ROOT_TAG_NAME, "</" + ROOT_TAG_NAME + ">");
		final Collection<List<String>> lists = map.values();
		for (final List<String> list : lists) {
			final ConfReader confReader = new ConfReader();
			confReader.readFile(list);
			confReader.readKeys(ROOT_TAG_NAME);
			final Printer printer = new Printer(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[0]), confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[5]));
			printer.setUuid(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[1]));
			printer.setInfo(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[2]));
			printer.setLocation(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[3]));
			printer.setMakeModel(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[4]));
			printer.setpPDTimeStamp(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[6]));
			printer.setState(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[7]));
			printer.setStateTime(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[8]));
			printer.setType(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[9]));
			printer.setAccepting(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[10]));
			printer.setShared(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[11]));
			printer.setColorManaged(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[12]));
			printer.setJobSheets(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[13]));
			printer.setQuotaPeriod(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[14]));
			printer.setPageLimit(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[15]));
			printer.setkLimit(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[16]));
			printer.setOpPolicy(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[17]));
			printer.setErrorPolicy(confReader.readValue(ROOT_TAG_NAME + DOT + ATTRIBUTES[18]));
			set.add(printer);
		}
		return set;
	}

	@Override
	public void writeObject(final Set<Printer> printers) {
		// TODO Auto-generated method stub

	}

}
