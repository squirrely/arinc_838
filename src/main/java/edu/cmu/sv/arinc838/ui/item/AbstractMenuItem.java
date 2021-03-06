/*
 * Copyright (c) 2012 Chris Ellison, Mike Deats, Liron Yahdav, Ryan Neal,
 * Brandon Sutherlin, Scott Griffin
 * 
 * This software is released under the MIT license
 * (http://www.opensource.org/licenses/mit-license.php)
 * 
 * Created on Feb 18, 2012
 */
package edu.cmu.sv.arinc838.ui.item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class AbstractMenuItem implements MenuItem {
	public static final MenuItem[] EMPTY_ITEMS = new MenuItem[0];
	
	private String prompt;

	public AbstractMenuItem(String prompt) {
		this.prompt = prompt;
	}

	@Override
	public String getPrompt() {
		return prompt;
	}

	public String getHeader() {
		return null;
	}

	public static String promptForResponse(String toSay) throws IOException {

		System.out.print(toSay + " ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String ret = br.readLine();
		return ret;
	}

	public MenuItem[] getEmptyItems() {
		return AbstractMenuItem.EMPTY_ITEMS;
	}
}
