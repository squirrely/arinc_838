/*
 * Copyright (c) 2012 Chris Ellison, Mike Deats, Liron Yahdav, Ryan Neal,
 * Brandon Sutherlin, Scott Griffin
 * 
 * This software is released under the MIT license
 * (http://www.opensource.org/licenses/mit-license.php)
 * 
 * Created on Feb 18, 2012
 */
package edu.cmu.sv.arinc838.ui;

import edu.cmu.sv.arinc838.ui.item.ExitItem;
import edu.cmu.sv.arinc838.ui.item.MenuItem;
import edu.cmu.sv.arinc838.ui.item.XmlReadItem;



public class InitalMenu implements Menu {
	private static final MenuItem[] items = new MenuItem[] { 
		new XmlReadItem ("Read in Xml File"),
		new ExitItem()
		
//		new AbstractMenuItem ("Say something") {
//
//			@Override
//			public Menu execute(SoftwareDefinitionFileBuilder builder) {
//				// TODO Auto-generated method stub
//				String version = MenuHelper.getInput ("What is the version");
//				builder.setFileFormatVersion(version);
//				return null;
//			}
//		}
	};
	
	@Override
	public MenuItem[]getItems() {
		return items;
	}

	@Override
	public String getHeader() {
		return null;
	}
}
