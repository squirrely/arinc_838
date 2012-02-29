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

import java.io.BufferedReader;
import java.io.InputStreamReader;

import edu.cmu.sv.arinc838.builder.SoftwareDefinitionFileBuilder;
import edu.cmu.sv.arinc838.ui.item.MenuItem;

public class MenuRunner {
	private static final String DASHED_LINE = "-------------------";

	private SoftwareDefinitionFileBuilder builder;
	private final BufferedReader br;
	
	private MenuRunner() {
		br = new BufferedReader(new InputStreamReader(System.in));
		builder = new SoftwareDefinitionFileBuilder();
	}

	private void runMenu(MenuItem[] menuItems, String header) {
		while (menuItems != null && menuItems.length > 0) {
			StringBuffer sb = new StringBuffer();

			if (header != null) {
				sb.append(DASHED_LINE);
				sb.append(header + "\n");
			}

			for (int i = 0; i < menuItems.length; ++i) {
				sb.append(i + ": " + menuItems[i].getPrompt() + "\n");
			}

			System.out.println("\n\n" + sb);
			System.out.print("Please make a selection: ");

			MenuItem[] subMenuItems = null;
			String subHeader = null;
			
			try {
				int convertedValue = inInt(br);

				MenuItem itemToDo = menuItems[convertedValue];

				subMenuItems = itemToDo.execute(builder);
				subHeader = itemToDo.getHeader ();
			} catch (Exception e) {
				// try again?
				e.printStackTrace();
			}

			if (subMenuItems == null) { // null is used to indicate breakout  
				break; 
			} else {
				// empty indicates loop, otherwise exec the menu
				runMenu(subMenuItems, subHeader);  
			}
			
			System.out.flush();
		}
	}

	public static int inInt(BufferedReader br) throws Exception {
		String inString = br.readLine();
		return Integer.valueOf(inString.trim()).intValue();
	}

	public static void main(String[] args) {
		MenuRunner mr = new MenuRunner();

		mr.runMenu(new InitialMenu().getItems(), null);
	}
}
