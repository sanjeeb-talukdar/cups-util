package com.wdpr.cups.conf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LineParser {
	private String fileName;

	public LineParser(final String fileName) {
		this.fileName = fileName;
	}

	public Map<Integer, List<String>> parse(final String mode, final char eof, final String start, final String end) {
		final Map<Integer, List<String>> lines = new LinkedHashMap<Integer, List<String>>();
		try {
			final RandomAccessFile aFile = new RandomAccessFile(fileName, mode);
			int id = 0;
			String str = null;
			while((str = aFile.readLine()) != null) {
				id = parse(str, start, end, lines, id);
			}
			aFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new IllegalStateException("File : " + fileName + " not found..", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("Error opening File : " + fileName + "..", e);
		} finally {
		}
		return lines;
	}

	private int parse(final String val, final String start, final String end, final Map<Integer, List<String>> lines, int id) {
		if (val.startsWith(start)) {
			final List<String> starting = new ArrayList<String>();
			starting.add(val);
			lines.put(id + 1, starting);
			id++;
		} else if (val.startsWith(end) && lines.get(id) != null) {
			final List<String> ending = lines.get(id);
			ending.add(val);
		} else if (lines.get(id) != null) {
			final List<String> ending = lines.get(id);
			ending.add(val);
		}
		return id;
	}

}
