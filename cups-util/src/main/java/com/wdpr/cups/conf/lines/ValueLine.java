package com.wdpr.cups.conf.lines;

import com.wdpr.cups.conf.tree.ConfNode;
import com.wdpr.cups.conf.tree.ValueNode;

/**
 * @author sanjeeb
 */
public class ValueLine implements LineReader {

	@Override
	public boolean matches(final String line) {
		return line.trim().matches("[^\\s]+[\\s]+[^\\s]+") || line.trim().indexOf(" ") > -1 || line.trim().indexOf("\t") > -1;
	}

	@Override
	public ConfNode process(final ConfNode where, final String line) {
		final String str = line.trim();
		int index = str.indexOf(" ");
		if (index < 0) index = str.indexOf("\t");
		String key = index > -1 ? str.substring(0, index) : str;
		String value = index > -1 ? str.substring(index, str.length()) : str;
		final ValueNode node = new ValueNode(where);
		node.setName(key != null ? key.trim() : key);
		node.setValue(value != null ? value.trim() : value);
		where.getChildren().put(key, node); // Overwrite previous node if present
		return where;

	}

}
