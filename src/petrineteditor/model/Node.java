package petrineteditor.model;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;

import petrineteditor.controller.PetrinetEditorController;

/**
 * Model-Klasse für Knoten
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public abstract class Node extends PetrinetElement 
{
	/**
	 * Sichtbare Bezeichnung eines Knotens
	 */
	protected String label;
	
	/**
	 * Flag, ob Knoten gerade verschiebbar ist
	 */
	private boolean draggable;
	
	/**
	 * Erzeugt einen Knoten an gewünschte Position und Initialisierung
	 * @param x
	 * @param y
	 */
	public Node(int x, int y)
	{
		super(x, y);
		String nodeType = this.getClass().getSimpleName();
		
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		Petrinet petrinet = controller.getCurrentPetrinet();
		
		this.label = nodeType + petrinet.getUniqueElementID();
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Gibt die Bezeichnung des Knotens 
	 * @return Die Bezeichnung des Knotens
	 */
	public String getLabel()
	{
		return this.label;
	}
	
	/**
	 * Setzt die Bezeichnung des Knotens
	 * @param label Die neue Bezeichnung
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * Prüft, ob ein Element verschiebbar ist
	 * @return true, falls das Element verschiebbar ist, false sonst
	 */
	public boolean isDraggable()
	{
		return this.draggable;
	}
	
	/**
	 * Schaltet zwischen Status 'Verschiebbar' und 'Nicht verschiebbar'
	 */
	public void toggleDrag() {
		if (this.draggable == true) {
			this.draggable = false;
		}
		else {
			this.draggable = true;
		}
	}	
	
	public Graphics2D visualize(Graphics2D g)
	{
		int size = 2*PetrinetEditor.getElementSize();
		g.setFont(PetrinetEditor.getFont());
		
		FontMetrics metrics = g.getFontMetrics();
		double strWidth = metrics.stringWidth(this.getLabel());
		int strPosX = this.location.x - (int) ((strWidth - size) / 2);
		
		g = super.visualize(g);
		
		if (this.isDraggable()) {
			g.setColor(Color.BLUE);
		}
		
		g.drawString(this.getLabel(), strPosX, this.location.y - 5);
		return g;
	}
	
	public abstract boolean contains(Point p);
}
