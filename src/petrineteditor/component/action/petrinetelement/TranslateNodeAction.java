package petrineteditor.component.action.petrinetelement;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import petrineteditor.component.action.BaseAction;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Node;
import petrineteditor.model.Petrinet;
import petrineteditor.model.PetrinetElement;
import petrineteditor.view.dialog.NodePositionView;

/**
 * Action zum Verschieben von Knoten auf der Zeichenfläche
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class TranslateNodeAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Liste aller verschiebbaren Knoten
	 */
	private ArrayList<PetrinetElement> nodes;
	
	/**
	 * Aktuelle Position des Mauszeigers
	 */
	private Point point;
	
	/**
	 * Position des Mauszeigers zu Beginn der Verschiebung
	 */
	private Point start;
	
	/**
	 * Erzeugt eine neue Action mit übergebenenen Knoten
	 * @param nodes Liste an Knoten
	 */
	public TranslateNodeAction(ArrayList<PetrinetElement> nodes) 
	{
		if (nodes == null) {
			this.nodes = new ArrayList<PetrinetElement>();			
		}
		else {
			this.nodes = nodes;
		}
	}
	
	@Override
	public void run() 
	{
		boolean drag = true;
		if (this.nodes.size() == 0) {
			return;
		}
			
		if (this.point == null && this.start == null) {
			Node startNode = (Node) this.nodes.get(0);
			this.start = startNode.getLocation();
			NodePositionView view = new NodePositionView(this, startNode.getLocation());
			view.showDialog();
			drag = false;
		}
		
		if (this.point == null) {
			return;
		}
		
		Point diff = new Point(this.point.x - this.start.x, this.point.y - this.start.y);
	
		if (drag) {
			this.start.translate(diff.x, diff.y);			
		}
	
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		Petrinet current = controller.getCurrentPetrinet();
		
		if (this.nodes.size() == 1) {
			Node node = (Node) nodes.get(0);
			Point location = node.getLocation();
			location.translate(diff.x, diff.y);
			current.translateElement(node, location);
		}
		else {
			Iterator<PetrinetElement> iterator = this.nodes.iterator();
			
			while (iterator.hasNext()) {
				Node node = (Node) iterator.next();
				Point location = node.getLocation();
				location.translate(diff.x, diff.y);
				current.translateElement(node, location);
			}
		}
	}
	
	/**
	 * Setzt die aktuelle Position des Mauszeigers
	 * @param p Eine Position
	 */
	public void setPoint(Point p)
	{
		this.point = p;
	}

	/**
	 * Setzt die Startposition des Verschiebens
	 * @param p Startposition
	 */
	public void setStartPoint(Point p) {
		this.start = p;
	}
	
	public void addElements(ArrayList<PetrinetElement> elements) {
		this.nodes.addAll(elements);
	}
	
	public void removeElements() {
		this.nodes.clear();
	}
	
}
