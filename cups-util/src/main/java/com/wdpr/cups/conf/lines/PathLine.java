package com.wdpr.cups.conf.lines;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.wdpr.cups.conf.tree.ConfNode;
import com.wdpr.cups.conf.tree.PathNode;
import com.wdpr.cups.conf.tree.ValueNode;

/**
 * @author sanjeeb
 */
public class PathLine implements LineReader{
	
	private ConfNode now = null;
	private String line = null;

	@Override
	public boolean matches (String line) {
		return line.trim().startsWith("<");
	}

	@Override
	public ConfNode process (ConfNode where, String textline) {
		this.line = textline.trim();
		now = where;
		if(line.startsWith("</"))
		{
			return closeTag();
		}
		else // <
		{
			return openTag();
		}
	}
	
	private ConfNode openTag()
	{
		String name = StringUtils.substringBetween(line,"<", ">");
		String alies = null;
		if(name.trim().matches("[^\\s]+[\\s]+[^\\s]+")) {
			name = name.trim();
			Pattern pattern = Pattern.compile("([^\\s]+)[\\s]+([^\\s]+)");
			Matcher matcher = pattern.matcher(name);
			matcher.matches();
			name = matcher.group(1);
			alies = matcher.group(2);
		}
		
		if(now.getChildren().containsKey(name))
		{
			return now.getChildren().get(name);
		}
		else
		{
			PathNode newNode = new PathNode(now);
			newNode.setName(name);
			newNode.setAlies(alies);
			ValueNode node = new ValueNode(newNode);
			node.setName(name);
			node.setValue(alies);
			newNode.getChildren().put(name, node);
			now.getChildren().put(name,newNode);
			return newNode;
		}
		
		
	}
	
	private ConfNode closeTag()
	{
		//TODO maybe check that the node closed was the one opened
		return now.getParent();
	}
	

}
