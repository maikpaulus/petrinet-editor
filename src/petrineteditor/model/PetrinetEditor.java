package petrineteditor.model;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Observable;

import petrineteditor.view.PetrinetEditorView;

/**
 * Model-Klasse als Container für Petrinetze (Singleton)
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
	 * Globale Schriftgröße in den Petrinetzen
	 */
	private static int FONT_SIZE = 12;
	
	
	/**
	 * Letztes geöffnetes Verzeichnis beim Laden / Speichern
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
	 * Legt Grundeinstellungen für den Editor fest, Initialisierung
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
	 * Fügt der Container-Klasse ein neues Petrinetz hinzu
	 * @param petrinet Das neu zu erstellende Petrinetz
	 */
	public void addPetrinet(Petrinet petrinet)
	{
		this.petrinets.add(petrinet);
		this.setChanged();
		this.notifyObservers(this.petrinets);
	}

	/**
	 * Löscht ein Petrinetz aus der Container-Klasse
	 * @param petrinet Das zu löschende Petrinetz
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
	 * Gibt die Skalierung zurück
	 * @return Globale Skalierung 
	 */
	public int getScale()
	{
		return PetrinetEditor.SCALE;
	}
	
	/**
	 * Berechnet die Größe der Elemente aus der Skalierung
	 * @return Größe der Elemente
	 */
	public static int getElementSize()
	{
		return (int) (SCALE * 0.15);
	}
	
	/**
	 * Gibt das letzte besuchte Verzeichnis zurück
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
	 * Gibt die Schrift zurück
	 * @return Schrift des Petrinetzes
	 */
	public static Font getFont()
	{
		return FONT;
	}
	
	/**
	 * Berechnet die Schriftgröße aus der Skalierung und der globalen Schriftgröße
	 * @return Die berechnete Schriftgröße
	 */
	private int getFontSize()
	{
		return (int) ((SCALE * FONT_SIZE) / 100);
	}
}
