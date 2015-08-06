package petrineteditor.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import petrineteditor.component.parser.PNMLWriter;

/**
 * Model-Klasse für eine Transition
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class Transition extends Node
{
	/**
	 * Flag, ob die Transition schaltbar ist
	 */
	private boolean activated = false;
	
	private boolean shouldStart = false;
	
	/**
	 * Erzeugt eine neue Transition
	 * @param x x-Koordinate der Transition
	 * @param y y-Koordinate der Transition
	 */
	public Transition(int x, int y)
	{
		super(x, y);
	}
	
	/**
	 * Aktiviert die Transition
	 */
	public void activate()
	{
		this.activated = true;
	}
	
	/**
	 * Deaktiviert die Transition
	 */
	public void deactivate()
	{
		this.activated = false;
	}
	
	/**
	 * Prüft ob die Transition aktiviert ist
	 * @return true, falls die Transition aktiviert ist, false sonst
	 */
	public boolean isActivated()
	{
		return this.activated;
	}
	
	@Override
	public Graphics2D visualize(Graphics2D g) {
		int size = 2*PetrinetEditor.getElementSize();
		
		g = super.visualize(g);
		
		if (this.shouldStart) {
			g.setColor(Color.GRAY);
			g.fillRect(this.location.x + 1, this.location.y + 1, size - 1, size - 1);
		}
		
		if (this.activated) {
			g.setColor(Color.GREEN);
		}
		
		g.drawRect(this.location.x, this.location.y, size, size);
		return g;
	}
	
	public boolean contains(Point p)
	{
		int size = 2*PetrinetEditor.getElementSize();
		Point end = new Point(this.location);
		end.translate(size, size);
		
		if (((p.x >= this.location.x) && (p.y >= this.location.y))
			&& ((p.x <= end.x) && (p.y <= end.y))
		) {
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
		
		pnmlWriter.addTransition(id, label, pX, pY);
		return pnmlWriter;
	}
	
	public void selectForGroup()
	{
		this.shouldStart = true;
	}
	
	public void deselectForGroup()
	{
		this.shouldStart = false;
	}
	
	public boolean isInTransitionGroup() {
		return this.shouldStart;
	}
	
	public void toggleGroup()
	{
		if (this.shouldStart) {
			this.deselectForGroup();
		}
		else {
			this.selectForGroup();
		}
	}
}

