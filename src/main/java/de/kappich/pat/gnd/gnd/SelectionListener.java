/*
 * Copyright 2010 by Kappich Systemberatung Aachen
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
package de.kappich.pat.gnd.gnd;

import de.bsvrz.dav.daf.main.config.SystemObject;

import java.util.Collection;

/**
 * Ein spezielles Listener-Interface für die Selektion von Systemobjekten in der Kartenansicht.
 * Der gegenwärtig einzige Anwendungsfall ist das GNDPlugin, also die Klasse, die die GND als GTM-Modul
 * nutzbar macht. Wird im Menü 'Extras' der Menüpunkt 'Ausgewählte Objekte an den GTM zurückgeben' ausgeführt,
 * so wird dort über den Notify-Mechanismus die einzige Methode des Interfaces aufgerufen.
 * <p>
 * Die Namensgebung ist also etwas unglücklich, weil viel zu allgemein. 
 *  
 * @author Kappich Systemberatung
 * @version $Revision$
 *
 */
public interface SelectionListener {
	/**
	 * Teilt dem Listener mit, dass die übergebene Menge von Systemobjekte selektiert ist.
	 * 
	 * @param systemObjects die selektierten Systemobjekte
	 */
	public void setSelectedObjects(Collection<SystemObject> systemObjects);
}
