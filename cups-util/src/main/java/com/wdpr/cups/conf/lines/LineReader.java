package com.wdpr.cups.conf.lines;

import com.wdpr.cups.conf.tree.ConfNode;

/**
 * @author sanjeeb
 */
public interface LineReader {
	
	boolean matches(String line);
	
	ConfNode process(ConfNode where, String line);

}
