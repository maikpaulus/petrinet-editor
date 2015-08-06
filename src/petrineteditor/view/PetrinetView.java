package petrineteditor.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JScrollPane;

import petrineteditor.component.PetrinetPanel;
import petrineteditor.model.Petrinet;
import petrineteditor.model.PetrinetElement;

/**
 * View-Klasse f�r ein offenes Petrinetz
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class PetrinetView extends JScrollPane implements Observer
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Zugeh�riges Petrinetz
	 */
	private Petrinet petrinet;
	
	/**
	 * Zugeh�rige Zeichenfl�che
	 */
	private PetrinetPanel panel = null;
	
	/** Erzeugt eine neue View und die zugeh�rige Zeichenfl�che und verkn�pft diese mit dem Petrinetz
	 * @param petrinet Das betroffene Petrinetz
	 */
	public PetrinetView(Petrinet petrinet)
	{
		this.petrinet = petrinet;
		this.petrinet.setView(this);
		
		this.panel = new PetrinetPanel(this);
		this.panel.setLayout(new FlowLayout());
		this.panel.setBackground(Color.WHITE);
		this.panel.setMinimumSize(this.panel.getSize());		
		
		this.setViewportView(this.panel);
	}
	
	/**
	 * Gibt die Zeichenfl�che zur�ck
	 * @return Die Zeichenfl�che des Petrinetzes
	 */
	public PetrinetPanel getPanel()
	{
		return this.panel;
	}
	
	/**
	 * Gibt das Petrinetz zur�ck
	 * @return Das Petrinetz
	 */
	public Petrinet getPetrinet()
	{
		return this.petrinet;
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		this.clear();
		this.paint();
	}
	
	/**
	 * Bereinigt das View-Panel beim Aktualisieren
	 */
	private void clear()
	{
		this.panel.removeAll();
	}
	
	/**
	 * Setzt den korrekten Viewport beim Neuzeichnen
	 */
	public void paint()
	{
		this.setViewportView(this.panel);
	}
	
	/**
	 * Gibt alle Petrinetz-Elemente zur�ck
	 * @return Liste aller Petrinetz-Elemente
	 */
	public ArrayList<PetrinetElement> getPetrinetElements()
	{
		return this.petrinet.getPetrinetElements();
	}
}