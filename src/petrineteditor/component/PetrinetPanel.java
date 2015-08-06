package petrineteditor.component;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;

import petrineteditor.component.action.petrinetelement.TranslateNodeAction;
import petrineteditor.component.menu.ElementPopupMenu;
import petrineteditor.component.menu.GeneralPopupMenu;
import petrineteditor.component.menu.MainToolBar;
import petrineteditor.model.Node;
import petrineteditor.model.Petrinet;
import petrineteditor.model.PetrinetElement;
import petrineteditor.model.Transition;
import petrineteditor.view.PetrinetView;

/**
 * Zeichenfläche eines Petrinetzes
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class PetrinetPanel extends JPanel implements MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Richtwert zum Vorinitialisieren der Array-Listen
	 */
	private static final int MAX_NOF_ELEMENTS = 100;
	
	/**
	 * Zugehörige Petrinetz-View
	 */
	private PetrinetView view;
	
	/**
	 * Flag, ob Standard-Mausevents aktiv sind
	 */
	private boolean mouseEventEnabled = true;
	
	/**
	 * Flag, ob der Kanten-Modus aktiv ist (bei Kantenerstellung)
	 */
	private boolean edgeMode = false;
	
	/**
	 * Eine Instanz der Action zum Verschieben von Elementen
	 */
	private TranslateNodeAction action;
	
	/** Erzeugt eine neue Zeichenfläche und verknüpft sie mit der Petrinetz-View
	 * @param view
	 */
	public PetrinetPanel(PetrinetView view)
	{
		super();
		this.view = view;
		
		this.action = new TranslateNodeAction(null);
	
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	/**
	 * Aktiviert die Standard-Mausevents
	 */
	public void enableEvents()
	{
		this.mouseEventEnabled = true;
	}
	
	/**
	 * Deaktiviert die Standard-Mausevents
	 */
	public void disableEvents()
	{
		this.mouseEventEnabled = false;
	}
	
	/**
	 * Aktiviert Kanten-Modus
	 */
	public void activateEdgeMode()
	{
		this.edgeMode = true;
		this.disableEvents();		
		
		MainToolBar toolbar = MainToolBar.getInstance();
		toolbar.getDisableEdgeModeAction().setEnabled(true);
		
		JLabel edgelabel = toolbar.getEdgeModeLabel();
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 11);
		edgelabel.setFont(font);
		edgelabel.setEnabled(true);
		
		this.repaint();
	}
	
	/**
	 * Deaktiviert Kanten-Modus 
	 */
	public void deactivateEdgeMode()
	{
		this.edgeMode = false;
		
		MainToolBar toolbar = MainToolBar.getInstance();
		toolbar.getDisableEdgeModeAction().setEnabled(false);
		
		JLabel edgelabel = toolbar.getEdgeModeLabel();
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 11);
		edgelabel.setFont(font);
		edgelabel.setEnabled(false);
		
		
		this.enableEvents();
	}
	
	/**
	 * Prüft, ob der Kantenmodus aktiviert ist.
	 * @return true, falls der Modus aktiviert ist, false sonst
	 */
	public boolean isEdgeModeEnabled()
	{
		return this.edgeMode;
	}
	
	/**
	 * Prüft ob ein Punkt in einem Element liegt
	 * @param elem Das Petrinetz-Element
	 * @param p Der Punkt
	 * @return true, falls das Element den Punkt enthält, false sonst
	 */
	private boolean findElement(PetrinetElement elem, Point p)
	{
		return elem.contains(p);
	}
	
	/**
	 * Findet eine Liste von Petrinetz-Elementen an einem beliebigen Punkt
	 * @param p Der gewünschte Punkt
	 * @param multiple Flag, ob mehr als ein Petrinetz-Element zurückgegeben wird
	 * @return Liste der gefundenen Petrinetz-Elemente
	 */
	public ArrayList<PetrinetElement> findElements(Point p, boolean multiple)
	{
		ArrayList<PetrinetElement> elements = new ArrayList<PetrinetElement>(PetrinetPanel.MAX_NOF_ELEMENTS);
		elements.addAll(this.view.getPetrinetElements());
		
		if (elements.size() == 0) {
			return null;
		}
		
		Iterator<PetrinetElement> iterator = elements.iterator();
		
		ArrayList<PetrinetElement> found = new ArrayList<PetrinetElement>(elements.size());
		
		while (iterator.hasNext()) {
			PetrinetElement elem = iterator.next();
			if (this.findElement(elem, p)) {
				found.add(elem);
				if (multiple == false) {
					break;
				}
			}
		}
		
		if (found.size() == 0) {
			return null;			
		}
		else {
			return found;
		}
	}
	
	/**
	 * Wählt Petrinetz-Elemente an einem bestimmten Punkt aus / ab
	 * @param p Der gewünschte Punkt
	 * @param multiple Flag, ob mehrere Petrinetz-Elemente ausgewählt werden dürfen
	 * @param dragClick Flag, ob der Klick im Drag-Modus stattfand
	 * @return true, wenn etwas ausgewählt wurde, false sonst
	 */
	private boolean selectElements(Point p, boolean multiple, boolean dragClick) {
		ArrayList<PetrinetElement> found = this.findElements(p, multiple);
		if (found != null) {
			Iterator<PetrinetElement> iterator = found.iterator();
			
			while (iterator.hasNext()) {
				PetrinetElement elem = iterator.next();
				
				if (dragClick) {
					if (!elem.isOfType("Edge")) {
						Node node = (Node) elem;
						node.toggleDrag();
						node.deselect();
					}
				}
				else {
					elem.toggleSelect();
					
					if (elem.isOfType("Transition")) {
						Transition transition = (Transition) elem;
						transition.toggleGroup();
					}
				}
			}
				 
			this.repaint();
			return true;
		}
		return false;
	}
	
	/**
	 * Verschiebt alle verschiebbaren Elemente
	 * @param p Position des neuen Punktes
	 */
	private void dragElements(Point p)
	{
		this.action.setPoint(p);
		this.action.run();
		this.repaint();			
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {		
		if (!mouseEventEnabled) {
			return;
		}
		
		boolean dragClick = e.isControlDown();
		
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			this.selectElements(e.getPoint(), false, dragClick);
			this.action.removeElements();
			break;
			
		case MouseEvent.BUTTON2:
			break;
			
		case MouseEvent.BUTTON3:
			ArrayList<PetrinetElement> found = this.findElements(e.getPoint(), false);
			
			if (found == null) {
				GeneralPopupMenu menu = new GeneralPopupMenu(e.getX(), e.getY());
				menu.show(e.getComponent(), e.getX(), e.getY());					
			}
			else {
				if (found.size() == 1) {
					ElementPopupMenu menu = new ElementPopupMenu(found.get(0));
					menu.show(e.getComponent(), e.getX(), e.getY());					
				}
			}
			break;
			
		default: 
			break;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		this.action.removeElements();
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		this.dragElements(e.getPoint());
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		this.action.setStartPoint(e.getPoint());
		ArrayList<PetrinetElement> found = this.findElements(e.getPoint(), false);
		if (found != null) {
			this.action.addElements(found);			
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e){}
	
	public void paintComponent(Graphics g)
	{
		g = (Graphics2D) g;
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
						
		ArrayList<PetrinetElement> elements = new ArrayList<PetrinetElement>(PetrinetPanel.MAX_NOF_ELEMENTS);
		elements.addAll(this.view.getPetrinetElements());
		Iterator<PetrinetElement> iterator = elements.iterator();
		
		while (iterator.hasNext()) {
			PetrinetElement elem = iterator.next();
			
			if (elem.isOfType("Transition")) {
				Transition transition = (Transition) elem;
				Petrinet current = this.view.getPetrinet();
				current.checkActivationState(transition);
			}
			
			g = elem.visualize((Graphics2D) g);
		}
	}
}
