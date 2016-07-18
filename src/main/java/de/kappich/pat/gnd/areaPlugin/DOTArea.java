/*
 * Copyright 2009 by Kappich Systemberatung Aachen
 * 
 * This file is part of de.kappich.pat.gnd.
 * 
 * de.kappich.pat.gnd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * de.kappich.pat.gnd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with de.kappich.pat.gnd.  If not, see <http://www.gnu.org/licenses/>.

 * Contact Information:
 * Kappich Systemberatung
 * Martin-Luther-Straße 14
 * 52062 Aachen, Germany
 * phone: +49 241 4090 436 
 * mail: <info@kappich.de>
 */
package de.kappich.pat.gnd.areaPlugin;

import de.kappich.pat.gnd.pluginInterfaces.DefaultDisplayObjectType;
import de.kappich.pat.gnd.pluginInterfaces.DisplayObjectType;
import de.kappich.pat.gnd.pluginInterfaces.DisplayObjectTypePlugin;

/**
 * Der Darstellungstyp für Flächenobjekte.
 * <p>
 * DOTArea erweitert DefaultDisplayObjectType ausschließlich um die Implementation der abstrakten Methode(n).
 * 
 * @author Kappich Systemberatung
 * @version $Revision$
 *
 */
public class DOTArea extends DefaultDisplayObjectType implements DisplayObjectType {
	
	/**
	 * Konstruktor mit leerem Namen und leerer Info. Namen und Info können später noch gesetzt werden;
	 * ein Speichern ohne diese Daten ist nicht sinnvoll. 
	 */
	public DOTArea () {
		super();
	}
	
	/**
	 * Konstruktor mit vorgegebenem Namen und Info. 
	 * @param name der unter allen DisplayObjectTypes eindeutige Name
	 * @param info eine Kurzinformation, die z.B. als Tooltipp verwendet wird
	 */
	public DOTArea (String name, String info) {
		super();
		_name = name;
		_info = info;
	}
	
	@Override
	public DisplayObjectTypePlugin getDisplayObjectTypePlugin() {
		return new DOTAreaPlugin();
	}
}
