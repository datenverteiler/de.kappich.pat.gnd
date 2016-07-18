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
package de.kappich.pat.gnd.complexPlugin;

import de.kappich.pat.gnd.displayObjectToolkit.DOTDefinitionDialogFrame;
import de.kappich.pat.gnd.displayObjectToolkit.DOTProperty;
import de.kappich.pat.gnd.pluginInterfaces.DOTDefinitionDialog;
import de.kappich.pat.gnd.pluginInterfaces.DisplayObjectPainter;
import de.kappich.pat.gnd.pluginInterfaces.DisplayObjectType;
import de.kappich.pat.gnd.pluginInterfaces.DisplayObjectTypePlugin;

import de.bsvrz.dav.daf.main.config.DataModel;
import de.bsvrz.dav.daf.main.config.SystemObjectType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Die Selbstbeschreibung des Plugins für komplexe Objekte.
 * 
 * @author Kappich Systemberatung
 * @version $Revision$
 *
 */
public class DOTComplexPlugin  implements DisplayObjectTypePlugin {
	
	/**
	 * Konstruiert eine Selbstbeschreibung des Plugins.
	 */
	public DOTComplexPlugin() {
		super();
	}

	public String getName() {
		return new String( "Komplex");
	}
	
	public DOTDefinitionDialog getDialog(DOTDefinitionDialogFrame dotDefinitionDialogFrame) {
		return new DOTComplexDialog( dotDefinitionDialogFrame);
    }

	public DisplayObjectType getDisplayObjectType() {
	    return new DOTComplex();
    }

	public DisplayObjectPainter getPainter() {
	   return new DOTComplexPainter();
    }

	public String[] getPrimitiveFormTypes() {
	   return new String[0];
    }

	public DOTProperty[] getProperties(Object o) {
		DOTProperty[] properties = null;
		if ( o == null) {
			properties = new DOTProperty[] { DOTProperty.FARBE};
		}
		return properties;
    }
	
	public String getGeometryType() {
		return "typ.geoReferenzObject";
	}

	public boolean isSystemObjectTypeSupported(DataModel configuration, SystemObjectType systemObjectType) {
		if ( _supportedSystemObjectTypePIDs == null) {
			initSupportedSystemObjectTypePIDs( configuration);
		}
		return _supportedSystemObjectTypePIDs.contains( systemObjectType.getPid());
    }
	
	private void initSupportedSystemObjectTypePIDs(DataModel configuration) {
		final List<SystemObjectType> geoReferenceObjectTypes = new ArrayList<SystemObjectType>();
		final SystemObjectType geoReferenceObjectType = configuration.getType("typ.komplex");
		geoReferenceObjectTypes.add( geoReferenceObjectType);
		for( int i = 0; i < geoReferenceObjectTypes.size(); i++) {
			geoReferenceObjectTypes.addAll( geoReferenceObjectTypes.get(i).getSubTypes());
		}
		_supportedSystemObjectTypePIDs = new HashSet<String>();
		for ( SystemObjectType systemObjectType : geoReferenceObjectTypes) {
			_supportedSystemObjectTypePIDs.add( systemObjectType.getPid());
		}
    }
	
	private static Set<String> _supportedSystemObjectTypePIDs = null;
	
}
