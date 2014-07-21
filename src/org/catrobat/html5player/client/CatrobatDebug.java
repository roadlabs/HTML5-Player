/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2014 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.html5player.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gwt.core.client.GWT;

public class CatrobatDebug {

	private static boolean debug = true;
	private static Logger log = null;

	private CatrobatDebug() {
	}
	
	private static void init() {
		if (log == null)
			log = Logger.getLogger("Catrobat.HTML5Player");
	}

	@Deprecated
	public static void console(String message) {
		if(debug)
			System.out.println(message);
	}

	@Deprecated
	public static void GWTLogger(String message) {
		if(debug)
			GWT.log(message);
	}

	/*
	 * Inform user about current operation.
	 */
	public static void info(String message) {
		init();
		log.log(Level.INFO, message);
	}

	/*
	 * Provide debugging information for problem analysis.
	 */
	public static void debug(String message) {
		init();
		log.log(Level.INFO, message);
	}

	/*
	 * A warning informs the developer about an unusual state.
	 * The system will proceed will but might fail.
	 */
	public static void warn(String message) {
		log.log(Level.WARNING, message);
	}

	/*
	 * Tell about a serious failure. Throw Exception afterwards.
	 */
	public static void error(String message) {
		log.log(Level.SEVERE, message);
	}

	@Deprecated
	public static void on() {
		debug = true;
	}

	@Deprecated
	public static void off() {
		debug = false;
	}	
}