package com.wdpr.cups.conf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wdpr.cups.conf.lines.CommentLine;
import com.wdpr.cups.conf.lines.LineReader;
import com.wdpr.cups.conf.lines.PathLine;
import com.wdpr.cups.conf.lines.ValueLine;
import com.wdpr.cups.conf.tree.ConfNode;
import com.wdpr.cups.conf.tree.PathNode;

/**
 * @author sanjeeb
 */
public class ConfReader {
	private static final Logger logger = LoggerFactory.getLogger(ConfReader.class);
	private final List<LineReader> readers = new ArrayList<LineReader>();
	private ConfNode rootNode = new PathNode(null);

	/**
	 * Creates a new instance of ConfReader
	 */
	public ConfReader() {
		readers.add(new CommentLine());
		readers.add(new PathLine());
		readers.add(new ValueLine());
	}

	/**
	 * Reads a legacy .conf Perl file
	 * 
	 * @param in
	 *            the file
	 * @throws IOException
	 *             something was wrong during reading.
	 */
	public void readFile(final List<String> lines) {
		for (final String line : lines) {
			if (StringUtils.isBlank(line)) {
				continue;
			}
			boolean ok = process(line);
			if (!ok) {
				logger.warn("Unknown line {}", line);
			}
		}

	}

	private boolean process(String line) {
		for (LineReader reader : readers) {
			if (reader.matches(line)) {
				rootNode = reader.process(rootNode, line);
				return true;
			}
		}
		return false;
	}

	/**
	 * Finds a value in the configuration file
	 * 
	 * @param path
	 *            a path using full stops
	 * @return the value or null if nothing was found
	 */
	public String readValue(String path) {
		ConfNode node = followPath(path);
		if (node != null) {
			return node.getValue();
		} else {
			return null;
		}
	}

	/**
	 * Finds all available keys that hang from this path
	 * 
	 * @param path
	 *            a path using full stops
	 * @return the values found
	 */
	public Set<String> readKeys(String path) {
		ConfNode node = followPath(path);
		if (node != null) {
			return node.getChildren().keySet();
		} else {
			return new HashSet<String>();
		}
	}

	/**
	 * Checks if a key is present or not
	 * 
	 * @param path
	 *            a path using full stops
	 * @return true if the key exists
	 */
	public boolean keyExists(String path) {
		return followPath(path) != null;
	}

	private ConfNode followPath(String path) {
		String[] paths = path.split("\\.");
		ConfNode start = rootNode;
		for (String pathName : paths) {
			if (!start.getChildren().containsKey(pathName)) {
				return null;
			}
			start = start.getChildren().get(pathName);
		}
		return start;
	}

}
