package com.wdpr.cups.conf.tree;


/**
 * @author sanjeeb
 */
public class ValueNode extends ConfNode {
	private static final long serialVersionUID = 1L;
	private String value;

	public ValueNode (ConfNode parent) {
		super(parent);
	}

	@Override
	public String getValue () {
		return value;
	}

	public void setValue (String value) {
		this.value = value;
	}

}
