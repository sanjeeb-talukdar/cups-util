package com.wdpr.cups.conf.tree;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author sanjeeb
 */
public abstract class ConfNode implements Serializable {
	private static final long serialVersionUID = 1L;

	private Map<String,ConfNode> children = new LinkedHashMap<String,ConfNode>();

	private ConfNode parent = null;

	private String comment = null;
	private String name = null;

	public ConfNode (ConfNode parent) {
		this.parent = parent;
	}

	public String getComment () {
		return comment;
	}

	public void setComment (String comment) {
		this.comment = comment;
	}

	public Map<String,ConfNode> getChildren () {
		return children;
	}

	public ConfNode getParent () {
		return parent;
	}
	
	
	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public abstract String getValue(); 
	
	
}
