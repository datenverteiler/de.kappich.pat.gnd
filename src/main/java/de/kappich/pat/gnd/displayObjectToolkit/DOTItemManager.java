/*
 * Copyright 2009 by Kappich Systemberatung Aachen
 * 
 * This file is part of de.kappich.pat.gnd.
 * 
 * de.kappich.pat.gnd is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * de.kappich.pat.gnd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with de.kappich.pat.gnd; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package de.kappich.pat.gnd.displayObjectToolkit;

import de.kappich.pat.gnd.pluginInterfaces.DisplayObjectType.DisplayObjectTypeItem;
import de.kappich.pat.gnd.utils.Interval;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Eine Klasse zur Verwaltung von DisplayObjectItems.
 * <p>
 * Ein DOTItemManager dient zur Verwaltung von DisplayObjectTypeItems einer Eigenschaft
 * ({@link DOTProperty}) oder eines Paars bestehend aus einer Grundfigur
 * und einer Eigenschaft ({@link PrimitiveFormPropertyPair}). Der DOTItemManager stellt
 * mehrere effiziente Zugriffsm�glichkeiten zur Verf�gung, die im Umfeld mit
 * Datenverteiler-Anwendungen ben�tigt werden. Dies sind z.B. die Menge aller notwendigen
 * Anmeldungen (s. getSubscriptionData()), die Intervalle mit zugeh�rigen Items einer Anmeldung
 * ({@link #getTreemaps} und {@link #get(String)) und die Verwaltung aller Items mit ihren Intervalls in
 * einer Liste ({@link #get( int)}).
 * 
 * @author Kappich Systemberatung
 * @version $Revision: 8094 $
 *
 */
public class DOTItemManager <E extends DisplayObjectTypeItem> {
	
	/**
	 * Initialisiert einen leeren Manager.
	 */
	public DOTItemManager() {
		super();
		_attributeNames = new HashMap<DOTSubscriptionData, List<String> > ();
		_displayObjectTypesItemMap = new HashMap< String, TreeMap<Interval<Double>, E>> ();
		_displayObjectTypesItemList = new ArrayList<DisplayObjectTypeItemWithInterval>();
	}
	
	/**
	 * Gibt eine Read-Only-Ansicht aller internen TreeMaps zur�ck. Jede solche TreeMap
	 * speichert f�r eine Anmeldung die notwendigen Intervalle mit zugeh�rigen Items.
	 * 
	 * @return gibt eine Read-Only-Ansicht aller internen TreeMaps zur�ck
	 */
	public Collection<TreeMap<Interval<Double>, E>> getTreeMaps() {
		return Collections.unmodifiableCollection( _displayObjectTypesItemMap.values());
	}
	
	/**
	 * Gibt eine Read-Only-Menge mit allen Anmeldeinformation des Managers zur�ck.
	 * 
	 * @return gibt eine Read-Only-Menge mit allen Anmeldeinformation des Managers zur�ck
	 */
	public Set<DOTSubscriptionData> getSubscriptionData() {
		return Collections.unmodifiableSet( _attributeNames.keySet());
	}
	
	/**
	 * Gibt <code>true</code> zur�ck, wenn es mindestens eines der verwalteten Items ein Anmeldung
	 * auf subscriptionData ben�tigt, sonst <code>false</code>.
	 * 
	 * @param subscriptionData eine Anmeldung
	 * @return <code>true</code> genau dann, wenn die Anmeldung ben�tigt wird
	 */
	public boolean hasSubscriptionData(DOTSubscriptionData subscriptionData) {
		return _attributeNames.containsKey(subscriptionData);
	}
	
	/**
	 * Gibt die Menge aller Attributnamen zur�ck, f�r die von mindestens einem Item
	 * zu subscriptionData Daten ben�tigt werden.
	 * 
	 * @param subscriptionData eine Anmeldung
	 * @return die verwendeten Attributnamen zu der Anmeldung
	 */
	public List<String> getAttributeNames( DOTSubscriptionData subscriptionData) {
		return _attributeNames.get( subscriptionData);
	}
	
	/**
	 * Gibt die Anzahl der Items in der Liste zur�ck, die gleichzeitig auch die Summe
	 * der Anzahl der Items �ber alle TreeMaps ist. Dient im Wesentlichen zur Begrenzung
	 * von Vorschleifen.
	 * 
	 * @return die Anzahl von Items
	 */
	public int size() {
		int size = 0;
		for ( TreeMap<Interval<Double>, E> treeMap : _displayObjectTypesItemMap.values()) {
			size += treeMap.size();
		}
		return _displayObjectTypesItemList.size();
	}
	
	/**
	 * Gibt die m�glichen Zugriffsschl�ssel f�r die Methode {@link #get( String}), die eine TreeMap
	 * liefert, zur�ck. Ein solcher Zugriffsschl�ssel besteht aus den aneinandergeh�ngter und nur durch
	 * Punkt getrennten Attributgruppenname, Aspektname und Attributname (s. auch {@link #getKeyString}).
	 * 
	 * @return alle Zugriffsschl�ssel der Methode {@link #get( String})
	 */
	public Set<String> keySet() {
		return Collections.unmodifiableSet(_displayObjectTypesItemMap.keySet());
	}
	
	/**
	 * Gibt die TreeMap zu dem Schl�ssel <code>key</code> zur�ck oder <code>null</code>. Ein solcher 
	 * Zugriffsschl�ssel besteht aus den aneinandergeh�ngter und nur durch Punkt getrennten 
	 * Attributgruppenname, Aspektname und Attributname ((s. auch {@link #getKeyString}).
	 * 
	 * @param key ein Zugriffsschl�ssel
	 * @return eine TreeMap, die Intervallen Items zuordnet
	 */
	public TreeMap<Interval<Double>, E> get( String key) {
		return _displayObjectTypesItemMap.get(key);
	}
	
	/**
	 * Gibt <code>true</code> zur�ck, wenn <code>key</code> ein g�ltiger Schl�ssel f�r eine TreeMap ist.
	 * 
	 * @param ein Zugriffsschl�ssel
	 * @return <code>true</code> genau dann, wenn zum Schl�ssel eine TreeMap existiert
	 */
	public boolean containsKey( String key) {
		return _displayObjectTypesItemMap.containsKey(key);
	}
	
	/**
	 * F�gt dem Manager das Item f�r das Werteintervall hinzu oder macht ein Update des Items,
	 * wenn f�r die Anmeldedaten des Items (getAttributeGroup, getAspect) das Interval bereits 
	 * benutzt wird.
	 * 
	 * @param interval ein Intervall
	 * @param item ein Item 
	 */
	public void put ( Interval<Double> interval, E item) {
		PutOperation putOperation = internalPut( interval, item);
		DisplayObjectTypeItemWithInterval newItemWithInterval = new DisplayObjectTypeItemWithInterval(interval, item);
		if ( putOperation == PutOperation.Insert ) {
			_displayObjectTypesItemList.add( newItemWithInterval);
		} else if ( putOperation == PutOperation.Update) {
			for ( DisplayObjectTypeItemWithInterval typeItemWithInterval : _displayObjectTypesItemList) {
				if ( newItemWithInterval.getInterval().equals( typeItemWithInterval.getInterval())) {
					final E item2 = typeItemWithInterval.getItem();
					if ( item.getAttributeGroup().equals( item2.getAttributeGroup()) &&
							item.getAspect().equals( item2.getAspect()) &&
							item.getAttributeName().equals( item2.getAttributeName())) {
						typeItemWithInterval.setItem( item);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * F�gt das Item f�r das Interval hinzu.
	 * 
	 * @param interval ein Intervall
	 * @param item ein Item 
	 */
	public void insert ( Interval<Double> interval, E item) {
		internalInsert( interval, item);
		DisplayObjectTypeItemWithInterval newItemWithInterval = new DisplayObjectTypeItemWithInterval(interval, item);
		_displayObjectTypesItemList.add( newItemWithInterval);
	}
	
	/**
	 * Liefert Item und Interval aus der Listenverwaltung des Managers f�r den Index.
	 * 
	 * @param index ein Index zwischen 0 und size()-1
	 * @return das entsprechende DisplayObjectTypeItemWithInterval
	 */
	public DisplayObjectTypeItemWithInterval get( int index) {
		return _displayObjectTypesItemList.get(index);
	}
	
	/**
	 * Entfernt Item und Interval aus der Listenverwaltung des Managers f�r den Index.
	 * 
	 *  @param index ein Index zwischen 0 und size()-1
	 */
	public void remove( int index) {
		final DisplayObjectTypeItemWithInterval itemWithInterval = _displayObjectTypesItemList.remove(index);
		if ( itemWithInterval != null) {
			_attributeNames.clear();
			_displayObjectTypesItemMap.clear();
			for ( DisplayObjectTypeItemWithInterval i : _displayObjectTypesItemList) {
				internalPut(i.getInterval(), i.getItem());
			}
		}
	}
	
	/**
	 * Der Schl�ssel-String eines Items bzw. der �bergebenen Daten entsteht durch Aneinanderh�ngung
	 * von Attributgruppenname, Aspekt und Attributname, wobei jeweils ein Punkt als Trennzeichen
	 * verwendet wird. Als Attributnamen kommen aber nicht nur die tats�chlichen Attribute der
	 * Attributgruppe in Frage, sondern auch spezielle Zeichenketten zur Verwaltung der Stati
	 * f�r 'leere Daten', 'keine Daten', 'keine Quelle' und 'keine Rechte' ( diese sind statische 
	 * Member von DynamicDefinitionComponent).
	 * 
	 * @param data eine Anmeldung
	 * @param attributeName ein Attributname zu dieser Anmeldung
	 */
	public String getKeyString( DOTSubscriptionData data, String attributeName) {
		return data.getAttributeGroup() + "." + data.getAspect() + "." + attributeName;
	}
	
	private enum PutOperation { 
		Insert, Update
	}
	
	private PutOperation internalPut( Interval<Double> interval, E item) {
		final DOTSubscriptionData sData = new DOTSubscriptionData( item.getAttributeGroup(), item.getAspect());
		final String attributeName = item.getAttributeName();
		if ( hasSubscriptionData( sData)) {
			List<String> sLst = getAttributeNames( sData);
			if ( !sLst.contains( attributeName)) {
				sLst.add( attributeName);
			}
		} else {
			List<String> sLst = new ArrayList<String> ();
			_attributeNames.put( sData, sLst);
		}
		
		PutOperation putOperation = PutOperation.Insert;
		String keyString = getKeyString( sData, attributeName);
		if ( containsKey( keyString)) {
			final TreeMap<Interval<Double>, E> treeMap = _displayObjectTypesItemMap.get(keyString);
			if ( treeMap.containsKey( interval)) {
				putOperation = PutOperation.Update;
			}
			treeMap.put( interval, item);
		} else {
			final TreeMap<Interval<Double>, E> treeMap = new TreeMap<Interval<Double>, E>();
			treeMap.put( interval, item);
			_displayObjectTypesItemMap.put(keyString, treeMap);
		}
		return putOperation;
	}
	
	private void internalInsert( Interval<Double> interval, E item) {
		final DOTSubscriptionData sData = new DOTSubscriptionData( item.getAttributeGroup(), item.getAspect());
		final String attributeName = item.getAttributeName();
		if ( hasSubscriptionData( sData)) {
			List<String> sLst = getAttributeNames( sData);
			if ( !sLst.contains( attributeName)) {
				sLst.add( attributeName);
			}
		} else {
			List<String> sLst = new ArrayList<String> ();
			sLst.add( attributeName);
			_attributeNames.put( sData, sLst);
		}
		
		String keyString = getKeyString( sData, attributeName);
		if ( containsKey( keyString)) {
			final TreeMap<Interval<Double>, E> treeMap = _displayObjectTypesItemMap.get(keyString);
			while ( treeMap.containsKey( interval)) {
				interval.setCounter( interval.getCounter()+1);
			}
			treeMap.put( interval, item);
		} else {
			final TreeMap<Interval<Double>, E> treeMap = new TreeMap<Interval<Double>, E>();
			treeMap.put( interval, item);
			_displayObjectTypesItemMap.put(keyString, treeMap);
		}
	}
	
	/**
	 * Gibt die Menge der Indizes aus der Listenverwaltung des Managers zur�ck,
	 * f�r die ein anderes �berlappendes Interval existiert.
	 * 
	 * @return die Menge von Indizes �berlappender Intervalle
	 */
	public Set<Integer> getConflictingRows() {
		Set<Integer> conflictingRows = new HashSet<Integer>();
		for ( int i = 0; i < _displayObjectTypesItemList.size(); i++) {
			for ( int j = i+1; j < _displayObjectTypesItemList.size(); j++) {
				DisplayObjectTypeItemWithInterval iItem = _displayObjectTypesItemList.get(i);
				DisplayObjectTypeItemWithInterval jItem = _displayObjectTypesItemList.get(j);
				if ( iItem.getItem().getAttributeGroup().equals( jItem.getItem().getAttributeGroup()) &&
						iItem.getItem().getAspect().equals( jItem.getItem().getAspect()) &&
						iItem.getItem().getAttributeName().equals( jItem.getItem().getAttributeName())) {
					if ( iItem.getInterval().intersects( jItem.getInterval())) {
						conflictingRows.add( i);
						conflictingRows.add( j);
					}
				}
			}
		}
		return conflictingRows;
	}
	
	/**
	 * Die Klasse DisplayObjectTypeItemWithInterval kapselt ein Paar bestehend aus einem
	 * Interval und einem Item.
	 * 
	 * @author Kappich Systemberatung
	 * @version $Revision: 8094 $
	 *
	 */
	public class DisplayObjectTypeItemWithInterval {
		private Interval<Double> _interval;
		private E _item;
		
		/**
		 * Konstruiert ein Objekt mit den �bergebenen Daten.
		 * 
		 * @param interval ein Intervall
		 * @param item ein Item
		 */
		public DisplayObjectTypeItemWithInterval(Interval<Double> interval, E item) {
			super();
			_interval = interval;
			_item = item;
		}
		
		/**
		 * Gibt das Intervall zur�ck.
		 * 
		 * @return das Intervall
		 */
		public Interval<Double> getInterval() {
			return _interval;
		}
		
		/**
		 * Gibt das Item zur�ck.
		 * 
		 * @return das Item
		 */
		public E getItem() {
			return _item;
		}
		
		/**
		 * Setzt das Intervall. 
		 * 
		 * @param das Intervall
		 */
		public void setInterval(Interval<Double> interval) {
			_interval = interval;
		}
		
		/**
		 * Setzt das Item.
		 * 
		 * @param das Item
		 */
		public void setItem(E item) {
			_item = item;
		}
		
		@Override
		public String toString() {
			return "[DOTItemWithInterval:" + _interval.toString() + _item.toString() + "]";
		}
		
		@SuppressWarnings("unchecked")
		public DisplayObjectTypeItemWithInterval getCopy() {
			Interval<Double> newInterval = _interval.getCopy();
			final DisplayObjectTypeItem newItem = _item.getCopy();
			final E newItemAsE = (E) newItem;
			return new DisplayObjectTypeItemWithInterval( newInterval, newItemAsE);
		}
	}
	
	protected Map<DOTSubscriptionData, List<String> > _attributeNames;
	// (attribute group, aspect) -> attributeName1, attributeName2, ...
	protected Map< String, TreeMap<Interval<Double>, E>> _displayObjectTypesItemMap;
	// Map: attributeGroup.aspect.attributeName -> TreeMap
	// TreeMap: interval -> dotItem
	protected List<DisplayObjectTypeItemWithInterval> _displayObjectTypesItemList;
}
