package petrineteditor.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Observable;

import petrineteditor.component.parser.PNMLWriter;
import petrineteditor.view.PetrinetView;

/**
 * Allgemeine abstrakte Klasse für ein Petrinetz-Element
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public abstract class PetrinetElement extends Observable
{
	/**
	 * Eindeutige ID des Elements
	 */
	protected String elementID;
	
	/**
	 * Position des Elementes
	 */
	protected Point location;
	
	/**
	 * Auswahlflag des Elementes
	 */
	protected boolean selected;
		
	/**
	 * Erzeugt ein neues Element an einer gewünschten Position
	 * @param x x-Koordinate der Position
	 * @param y y-Koordinate der Position
	 */
	public PetrinetElement(int x, int y)
	{
		this.selected = false;
		this.location = new Point(x, y);
		
		this.elementID = this.toString();
		
	}
	
	/**
	 * Gibt die ID des Elements zurück
	 * @return ID des Elements
	 */
	public String getUniqueID() 
	{
		return this.elementID;
	}

	/**
	 * Setzt die ID eines Elements
	 * @param uniqueID Neue ID des Elements
	 */
	public void setUniqueID(String uniqueID)
	{
		this.elementID = uniqueID;
	}
	
	/**
	 * Typvergleich des Elements
	 * @param elemType Der zu prüfende Elementtyp
	 * @return true, falls das Element vom übergebenen Typ ist, false sonst
	 */
	public boolean isOfType(String elemType)
	{
		String elemSimpleType = this.getClass().getSimpleName(); 
		return elemSimpleType.equals(elemType);
	}

	/**
	 * Spezielle Funktion für Kanten, ob eine Kante valide ist
	 * @return true, wenn eine Kante valide ist, false sonst und bei Knoten
	 */
	protected boolean isValid()
	{
		return false;
	}
	
	/**
	 * Trägt einen Beobachter (die View) für Änderungen ein
	 * @param petrinet Das zugehörige Petrinetz
	 */
	public void getObservedBy(Petrinet petrinet)
	{
		PetrinetView view = petrinet.getView();
		this.addObserver(view);
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Wählt ein Element aus
	 */
	public void select() 
	{
		this.selected = true;
	}
	
	/**
	 * Wählt ein Element ab
	 */
	public void deselect()
	{
		this.selected = false;
	}
	
	/** 
	 * Prüft, ob ein Element ausgewählt ist
	 * @return true, falls das Element ausgewählt ist, false sonst
	 */
	public boolean isSelected()
	{
		return this.selected;
	}
	
	/**
	 * Schaltet zwischen Status Ausgewählt und Abgewählts
	 */
	public void toggleSelect()
	{
		if (this.isSelected()) {
			this.deselect();
		}
		else {
			this.select();
		}
	}
	
	/**
	 * Gibt die aktuelle Position des Elements zurück
	 * @return Die aktuelle Position des Elements
	 */
	public Point getLocation()
	{
		return this.location;
	}
	
	/**
	 * Setzt die Position eines Elements
	 * @param x x-Koordinate des Elements
	 * @param y y-Koordinate des Elements
	 */
	public void setLocation(int x, int y)
	{
		this.location.x = x;
		this.location.y = y;
	}
	
	/**
	 * Setzt die Position eines Elements
	 * @param p Die Zielkoordinate
	 */
	public void setLocation(Point p)
	{
		this.location = p;
	}
	
	/**
	 * Allgemeine Funktion zum Zeichnen eines Elements
	 * @param g Das Graphics-Objekt des Petrinetzes
	 * @return Das Graphics-Objekt des Petrinetzes
	 */
	public Graphics2D visualize(Graphics2D g)
	{
		if (this.isSelected()) {
			g.setColor(Color.RED);
		}
		else {
			g.setColor(Color.BLACK);
		}
		
		return g;
	}
	
	/**
	 * Prüft, ob ein Element den übergebenen Punkt enthält
	 * @param p Der zu prüfende Punkt
	 * @return true, falls der Punkt im Element liegt, false sonst
	 */
	public abstract boolean contains(Point p);
	
	/**
	 * Allgemeine Funktion zum Speichern eines Elements
	 * @param pnmlWriter Schreibobjekt
	 * @return Gibt das Schreibobjekt wieder zurück
	 */
	public abstract PNMLWriter saveContent(PNMLWriter pnmlWriter);
}
