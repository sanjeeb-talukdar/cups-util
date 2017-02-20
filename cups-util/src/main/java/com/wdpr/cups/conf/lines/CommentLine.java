package com.wdpr.cups.conf.lines;

import com.wdpr.cups.conf.tree.ConfNode;

/**
 * @author sanjeeb
 */
public class CommentLine implements LineReader{

	@Override
	public boolean matches (String line) {
		return line.trim().startsWith("#");
	}

	@Override
	public ConfNode process (ConfNode where, String line) {
		String comment = line.trim();
		where.setComment(comment);
		return where;
	}

}
