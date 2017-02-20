package com.wdpr.cups.util;

import static com.wdpr.cups.CUPSConstants.MODEL_MAP;
import static com.wdpr.cups.CUPSConstants.PRINTER_INFO_COMMAND;
import static com.wdpr.cups.CUPSConstants.PRINTER_MODEL_OPTION;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CommandUtil {
	private static final Logger logger = LoggerFactory.getLogger(CommandUtil.class);
	private CommandUtil() {
	}

	public static boolean executeCommand(final String... strings) throws IOException, InterruptedException {
		final ProcessBuilder builder = new ProcessBuilder(strings);
		logger.error("Execution command : " + builder.command());
		final Process process = builder.start();
		final BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		final BufferedReader er = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = null;
		while ((line = br.readLine()) != null) {
			logger.error(line);
		}
		while ((line = er.readLine()) != null) {
			logger.error(line);
		}
		process.waitFor();
		process.destroy();
		logger.error("Exit code : " + process.exitValue());
		return process.exitValue() == 0;
	}
	
	public static void executeCommands(final Set<List<String>> commands) throws IOException, InterruptedException {
		if (commands != null && !commands.isEmpty()) {
			for (final List<String> string : commands) {
				logger.error("Procees output : " + executeCommand(string.toArray(new String [string.size()])));
			}
		}
	}

	public static String getDriver(final String string) {
		logger.error("String makemodel [" + string + "]");
		if(MODEL_MAP.isEmpty() || MODEL_MAP.get(string) == null) {
			final ProcessBuilder builder = new ProcessBuilder(PRINTER_INFO_COMMAND, PRINTER_MODEL_OPTION);
			logger.error("Execution command : " + builder.command());
			try {
				final Process process = builder.start();
				final BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
				final BufferedReader er = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				while ((line = br.readLine()) != null) {
					populateModelMap(line);
				}
				while ((line = er.readLine()) != null) {
					populateModelMap(line);
				}
				process.waitFor();
				process.destroy();
				final Iterator<Map.Entry<String, String>> entr = MODEL_MAP.entrySet().iterator();
				while (entr.hasNext()) {
					final Map.Entry<String, String> entry = entr.next();
					if(entry.getKey().contains("M4555")) {
						logger.error("String key [" + entry.getKey() + "]");
						logger.error("String key [" + entry.getValue() + "]");
					}
				}
				logger.error("Exit code : " + process.exitValue());
			} catch (final IOException | InterruptedException e) {
				e.printStackTrace();
			} 
		}
		return MODEL_MAP.get(string);
	}

	private static void populateModelMap(String line) {
		final String str = line.trim();
		int index = str.indexOf(" ");
		if (index < 0) index = str.indexOf("\t");
		final String key = index > -1 ? str.substring(0, index) : str;
		final String value = index > -1 ? str.substring(index, str.length()) : str;
		if(key != null && value != null) {
			MODEL_MAP.put(value.trim(), key.trim());
		}
	}
	
}
