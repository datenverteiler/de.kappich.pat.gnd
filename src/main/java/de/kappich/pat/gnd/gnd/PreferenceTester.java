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
package de.kappich.pat.gnd.gnd;

import de.kappich.pat.gnd.displayObjectToolkit.DOTManager;
import de.kappich.pat.gnd.displayObjectToolkit.DynamicDOTItem;
import de.kappich.pat.gnd.layerManagement.Layer;
import de.kappich.pat.gnd.layerManagement.LayerManager;
import de.kappich.pat.gnd.linePlugin.DOTLine;
import de.kappich.pat.gnd.viewManagement.View;
import de.kappich.pat.gnd.viewManagement.ViewEntry;
import de.kappich.pat.gnd.viewManagement.ViewManager;

import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Eine veraltete kleine Testapplikation für Zugriff auf und das Löschen von Präferenzen.
 * Im Moment ist sie eigentlich nur deshalb noch von Interesse, weil man mit ihrer Hilfe
 * die Präferenzen löschen kann, wenn die GND bei der Initialisierung daraus scheitert.
 * 
 * @author Kappich Systemberatung
 * @version $Revision$
 *
 */
public class PreferenceTester {
	
	/**
	 * @param args
	 * @throws BackingStoreException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, BackingStoreException {
		clearAll();
//		DOTTest();
//		LayerTest();
		ViewTest();
		getStatistics();
		System.out.println("Fertig!");
	}
	
	public static void getStatistics() {
		ViewManager viewManager = ViewManager.getInstance();
		viewManager.getStatistics();
	}
	
	public static void clearAll() throws ClassNotFoundException, BackingStoreException {
		Preferences gndPrefs;
        gndPrefs = Preferences.userRoot().node("de/kappich/pat/gnd");
    	gndPrefs.clear();
    	clearChildren( gndPrefs);
	}
	
	private static void clearChildren( Preferences prefs) throws BackingStoreException {
		final String[] childrenNames = prefs.childrenNames();
		for ( String childName : childrenNames) {
			Preferences childPrefs = prefs.node( prefs.absolutePath() + "/" + childName);
			childPrefs.clear();
			clearChildren(childPrefs);
			childPrefs.removeNode();
		}
	}
	
	public static void DOTTest () {
		putTestDOTs(true);
		getTestDots(true);
	}
	
	public static void LayerTest () {
		putTestDOTs(false);
		putTestLayer(true);
		getTestLayer(true);
	}
	
	public static void ViewTest () {
		putTestDOTs(false);
		putTestLayer(false);
		putTestViews(true);
		getTestViews(true);
	}
	
	public static void putTestViews ( boolean printInfo) {
		LayerManager layerManager = LayerManager.getInstance();
		ViewManager viewManager = ViewManager.getInstance();
		viewManager.clearViews();
		
		ViewEntry viewEntry1 = new ViewEntry(layerManager.getLayer("Layer 1"), 100000, 1, true, true);
		ViewEntry viewEntry2 = new ViewEntry(layerManager.getLayer("Layer 2"), 1000000, 1, false, true);
		
		ArrayList<ViewEntry> viewEntries = new ArrayList<ViewEntry>();
		viewEntries.add( viewEntry1);
		viewEntries.add( viewEntry2);
		
		View view1 = new View("Test-Ansicht", viewEntries);
		viewManager.addView( view1);
		
		if ( printInfo) {
			if ( view1 != null) {
				System.out.println("PUT: " + view1.toString());
			} else {
				System.out.println("view1 ist null");
			}
		}
	}
	
	public static void putTestLayer( boolean printInfo) {
		DOTManager dotManager = DOTManager.getInstance();
		LayerManager layerManager = LayerManager.getInstance();
		layerManager.clearLayers();
		
		Layer layer1 = new Layer("Layer 1", "Ich bin Layer 1", "Linien-Layer");
		layer1.addDisplayObjectType(dotManager.getDisplayObjectType("Linie 1"), 1000, 1);
		layer1.addDisplayObjectType(dotManager.getDisplayObjectType("Linie 2"), 10000, 1001);
		layerManager.addLayer(layer1);
		
		Layer layer2 = new Layer("Layer 2", "Ich bin Layer 2", "Linien-Layer");
		layer2.addDisplayObjectType(dotManager.getDisplayObjectType("Linie 1"), 500, 1);
		layer2.addDisplayObjectType(dotManager.getDisplayObjectType("Linie 2"), 10000, 501);
		layerManager.addLayer(layer2);
		
		if ( printInfo) {
			if ( layer1 != null) {
				System.out.println(layer1.toString());
			} else {
				System.out.println("layer1 ist null");
			}
			if ( layer2 != null) {
				System.out.println(layer2.toString());
			} else {
				System.out.println("layer2 ist null");
			}
		}
	}
	
	public static void putTestDOTs ( boolean printInfo) {
		DOTManager dotManager = DOTManager.getInstance();

		dotManager.clearDisplayObjectTypes();

		DOTLine dot1 = new DOTLine( "Linie 1", "");
		DynamicDOTItem dynamicItem = new DynamicDOTItem("", "", "", "Beschreibung", null);
		dotManager.saveDisplayObjectType(dot1);

		DOTLine dot2 = new DOTLine( "Linie 2", "");
		DynamicDOTItem defaultDOT2ForValue = new DynamicDOTItem( "", "", "", "Beschreibung", null);
		dotManager.saveDisplayObjectType(dot2);

		if ( printInfo) {
			if ( dot1 != null) {
				System.out.println(dot1.toString());
			} else {
				System.out.println("dot1 ist null");
			}
			if ( dot2 != null) {
				System.out.println(dot2.toString());
			} else {
				System.out.println("dot2 ist null");
			}
		}
	}
	
	public static void getTestDots ( boolean printInfo) {
		DOTManager manager = DOTManager.getInstance();
		
		DOTLine dot1 = (DOTLine) manager.getDisplayObjectType("Linie 1"); 
		DOTLine dot2 = (DOTLine) manager.getDisplayObjectType("Linie 2");
		
		if ( printInfo) {
			if ( dot1 != null) {
				System.out.println(dot1.toString());
			} else {
				System.out.println("dot1 ist null");
			}
			if ( dot2 != null) {
				System.out.println(dot2.toString());
			} else {
				System.out.println("dot2 ist null");
			}
		}
	}
	
	public static void getTestLayer( boolean printInfo) {
		LayerManager layerManager = LayerManager.getInstance();
		System.out.println(layerManager.toString());
		
		Layer layer1 = layerManager.getLayer("Layer 1");
		
		if ( printInfo) {
			if ( layer1 != null) {
				System.out.println(layer1.toString());
			} else {
				System.out.println("layer1 ist null");
			}
		}
	}
	
	public static void getTestViews( boolean printInfo) {
		ViewManager viewManager = ViewManager.getInstance();
		
		View view = viewManager.getView("Test-Ansicht");
		if ( printInfo) {
			if ( view != null) {
				System.out.println("GET: " + view.toString());
			} else {
				System.out.println("view ist null");
			}
		}
		
	}
	
}
