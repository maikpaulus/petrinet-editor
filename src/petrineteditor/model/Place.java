package petrineteditor.model;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;

import petrineteditor.component.parser.PNMLWriter;

/**
 * Model-Klasse für eine Stelle
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class Place extends Node
{
	/**
	 * Anzahl der Marken
	 */
	private int marking = 0;
	
	private int lowMarkingLimit = 0;
	
	/**
	 * Erzeugt eine neue Stelle an gewünschte Position
	 * @param x x-Koordinate der Stelle
	 * @param y y-Koordinate der Stelle
	 */
	public Place(int x, int y)
	{
		super(x, y);
	}
	
	
	/**
	 * Gibt die Anzahl der Marken zurück
	 * @return Anzahl der Marken
	 */
	public int getMarking() {
		return this.marking;
	}
	
	public void raiseLimit()
	{
		this.lowMarkingLimit++;
	}
	
	public void resetLimit()
	{
		this.lowMarkingLimit = 0;
	}
	
	public int getLowLimit()
	{
		return this.lowMarkingLimit;
	}

	/**
	 * Setzt die Anzahl der Marken
	 * @param marking Neue Anzahl der Marken
	 */
	public void setMarking(int marking) {
		if (marking <= 0) {
			this.marking = 0;
		}
		else {
			this.marking = marking;
		}
	}

	@Override
	public Graphics2D visualize(Graphics2D g) {
		int size = 2*PetrinetEditor.getElementSize();
		FontMetrics metrics = g.getFontMetrics();
		
		double strWidth = metrics.stringWidth(Integer.toString(this.marking));
		strWidth = (size - strWidth)/2;
		
		double strHeight = metrics.getAscent();
		strHeight = size - strHeight + metrics.getDescent();
		
		g = super.visualize(g);
		g.drawOval(this.location.x, this.location.y, size, size);
				
		if (this.marking != 0) {
			if (this.marking == 1) {
				g.fillOval(this.location.x + (size/3), this.location.y + (size/3), (size/3), (size/3));
			} else {
				g.drawString(Integer.toString(this.marking),
					(float) (this.location.x + strWidth),
					(float) (this.location.y + strHeight)
				);
			}
		}
		return g;
	}
	
	public boolean contains(Point p)
	{
		int size = PetrinetEditor.getElementSize();
		Point middle = new Point(this.location);
		
		middle.translate(size, size);
		
		if (p.distance(middle) <= size) {
			return true;
		}
		
		return false;
	}

	@Override
	public PNMLWriter saveContent(PNMLWriter pnmlWriter) {
		String id = this.getUniqueID();
		String label = this.getLabel();
		String pX = Integer.toString(this.getLocation().x);
		String pY = Integer.toString(this.getLocation().y);
		String marking = Integer.toString(this.getMarking());
		
		pnmlWriter.addPlace(id, label, pX, pY, marking);
		return pnmlWriter;
	}

}
