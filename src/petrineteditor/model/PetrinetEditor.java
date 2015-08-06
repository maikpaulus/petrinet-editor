package petrineteditor.model;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Observable;

import petrineteditor.view.PetrinetEditorView;

/**
 * Model-Klasse als Container f�r Petrinetze (Singleton)
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class PetrinetEditor extends Observable
{
	/**
	 * Singleton-Instanz
	 */
	private static PetrinetEditor instance;
	
	/**
	 * Globale Skalierung der Petrinetze
	 */
	private static int SCALE = 100;
	
	/**
	 * Globale Schriftgr��e in den Petrinetzen
	 */
	private static int FONT_SIZE = 12;
	
	
	/**
	 * Letztes ge�ffnetes Verzeichnis beim Laden / Speichern
	 */
	private static String LAST_DIRECTORY = "";
	
	/**
	 * Globale Schriftart 
	 */
	private static Font FONT = null; 
	
	/**
	 * Liste aller offenen Petrinetze
	 */
	private ArrayList<Petrinet> petrinets = null;
	
	/**
	 * Legt Grundeinstellungen f�r den Editor fest, Initialisierung
	 */
	protected PetrinetEditor()
	{
		this.petrinets = new ArrayList<Petrinet>();
		FONT = new Font(Font.SANS_SERIF, Font.PLAIN, this.getFontSize());
		this.addObserver(PetrinetEditorView.getInstance());
	}
	
	/**
	 * Singleton-Methode der Klasse
	 * @return Einzige Instanz der PetrinetEditor-Klasse
	 */
	public static PetrinetEditor getInstance()
	{
		if (instance == null) {
			instance = new PetrinetEditor();
		}
		
		return instance;
	}
	
	/**
	 * Liefert die Liste der Petrinetze
	 * @return Eine Liste mit allen offenen Petrinetze, kann auch leer sein
	 */
	public ArrayList<Petrinet> getPetrinets()
	{
		return this.petrinets;
	}
	
	/**
	 * F�gt der Container-Klasse ein neues Petrinetz hinzu
	 * @param petrinet Das neu zu erstellende Petrinetz
	 */
	public void addPetrinet(Petrinet petrinet)
	{
		this.petrinets.add(petrinet);
		this.setChanged();
		this.notifyObservers(this.petrinets);
	}

	/**
	 * L�scht ein Petrinetz aus der Container-Klasse
	 * @param petrinet Das zu l�schende Petrinetz
	 */
	public void deletePetrinet(Petrinet petrinet)
	{
		this.petrinets.remove(petrinet);
		this.setChanged();
		this.notifyObservers(this.petrinets);
	}
	
	/**
	 * Setzt die Skalierung und Schriftart
	 * @param scale Skalierung
	 */
	public void setScale(int scale)
	{
		SCALE = scale;
		FONT = new Font(Font.SANS_SERIF, Font.PLAIN, this.getFontSize());
	}
	
	/**
	 * Gibt die Skalierung zur�ck
	 * @return Globale Skalierung 
	 */
	public int getScale()
	{
		return PetrinetEditor.SCALE;
	}
	
	/**
	 * Berechnet die Gr��e der Elemente aus der Skalierung
	 * @return Gr��e der Elemente
	 */
	public static int getElementSize()
	{
		return (int) (SCALE * 0.15);
	}
	
	/**
	 * Gibt das letzte besuchte Verzeichnis zur�ck
	 * @return Verzeichnisname
	 */
	public static String getLastDirectory()
	{
		return LAST_DIRECTORY;
	}
	
	/**
	 * Setzt das letzte besuchte Verzeichnis
	 * @param dir Ein Verzeichnisname
	 */
	public static void setLastDirectory(String dir)
	{
		LAST_DIRECTORY = dir;
	}
	
	/**
	 * Gibt die Schrift zur�ck
	 * @return Schrift des Petrinetzes
	 */
	public static Font getFont()
	{
		return FONT;
	}
	
	/**
	 * Berechnet die Schriftgr��e aus der Skalierung und der globalen Schriftgr��e
	 * @return Die berechnete Schriftgr��e
	 */
	private int getFontSize()
	{
		return (int) ((SCALE * FONT_SIZE) / 100);
	}
}
