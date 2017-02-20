package com.wdpr.cups.conf.tree;

/**
 * @author sanjeeb
 */
public class PathNode extends ConfNode{
	private static final long serialVersionUID = 1L;
	private String alies;
	
	public PathNode(ConfNode parent)
	{
		super(parent);
	}

	@Override
	public String getValue () {
		return null;
	}

	public String getAlies() {
		return alies;
	}

	public void setAlies(String alies) {
		this.alies = alies;
	}
	
	
	
}
