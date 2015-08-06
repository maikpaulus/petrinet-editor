package petrineteditor.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Observable;

import petrineteditor.component.parser.PNMLWriter;
import petrineteditor.view.PetrinetView;

/**
 * Allgemeine abstrakte Klasse f�r ein Petrinetz-Element
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
	 * Erzeugt ein neues Element an einer gew�nschten Position
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
	 * Gibt die ID des Elements zur�ck
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
	 * @param elemType Der zu pr�fende Elementtyp
	 * @return true, falls das Element vom �bergebenen Typ ist, false sonst
	 */
	public boolean isOfType(String elemType)
	{
		String elemSimpleType = this.getClass().getSimpleName(); 
		return elemSimpleType.equals(elemType);
	}

	/**
	 * Spezielle Funktion f�r Kanten, ob eine Kante valide ist
	 * @return true, wenn eine Kante valide ist, false sonst und bei Knoten
	 */
	protected boolean isValid()
	{
		return false;
	}
	
	/**
	 * Tr�gt einen Beobachter (die View) f�r �nderungen ein
	 * @param petrinet Das zugeh�rige Petrinetz
	 */
	public void getObservedBy(Petrinet petrinet)
	{
		PetrinetView view = petrinet.getView();
		this.addObserver(view);
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * W�hlt ein Element aus
	 */
	public void select() 
	{
		this.selected = true;
	}
	
	/**
	 * W�hlt ein Element ab
	 */
	public void deselect()
	{
		this.selected = false;
	}
	
	/** 
	 * Pr�ft, ob ein Element ausgew�hlt ist
	 * @return true, falls das Element ausgew�hlt ist, false sonst
	 */
	public boolean isSelected()
	{
		return this.selected;
	}
	
	/**
	 * Schaltet zwischen Status Ausgew�hlt und Abgew�hlts
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
	 * Gibt die aktuelle Position des Elements zur�ck
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
	 * Pr�ft, ob ein Element den �bergebenen Punkt enth�lt
	 * @param p Der zu pr�fende Punkt
	 * @return true, falls der Punkt im Element liegt, false sonst
	 */
	public abstract boolean contains(Point p);
	
	/**
	 * Allgemeine Funktion zum Speichern eines Elements
	 * @param pnmlWriter Schreibobjekt
	 * @return Gibt das Schreibobjekt wieder zur�ck
	 */
	public abstract PNMLWriter saveContent(PNMLWriter pnmlWriter);
}
