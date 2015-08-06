package petrineteditor.component.action.petrinetelement;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import petrineteditor.component.PetrinetPanel;
import petrineteditor.component.action.BaseAction;
import petrineteditor.component.error.ErrorHandler;
import petrineteditor.component.error.ErrorValues;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Edge;
import petrineteditor.model.Node;
import petrineteditor.model.Petrinet;
import petrineteditor.model.PetrinetElement;

/**
 * Action zur Erstellung einer neuen Kante
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class CreateEdgeAction extends BaseAction 
{
	private static final long serialVersionUID = 1L;
	
	protected Node element;
	protected boolean nodeIsSource;
	
	/**
	 * Erzeugt eine neue Action
	 * @param element Ausgewähltes Element
	 * @param nodeIsSource Flag, ob Eingangskante oder Ausgangskante
	 */
	public CreateEdgeAction(Object element, boolean nodeIsSource) 
	{
		this.element = (Node) element;
		this.nodeIsSource = nodeIsSource;
	}
	
	@Override
	public void run()
	{
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		
		final Petrinet current = controller.getCurrentPetrinet();
		final CreateEdgeAction me = this;
		final PetrinetPanel panel = current.getView().getPanel();
		
		panel.activateEdgeMode();
		
		MouseListener m = new MouseListener() {
			
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!panel.isEdgeModeEnabled()) {
					return;
				}
				
				Point p = e.getPoint();
				ArrayList<PetrinetElement> elements = panel.findElements(p, false);
				
				if (elements != null) {
					if (elements.get(0).isOfType("Edge")) {
						return;
					}
					Node found = (Node) elements.get(0);
					Node source;
					Node target;
					boolean invalidEdge = true;	
					
					if (!found.equals(me.element)) {
						if (me.nodeIsSource) {
							invalidEdge = current.findEdge(me.element, found);
							source = me.element;
							target = found;
						}
						else {
							invalidEdge = current.findEdge(found, me.element);
							source = found;
							target = me.element;
						}
						
						String sourceType = source.getClass().getSimpleName();
						String targetType = target.getClass().getSimpleName();
						
						if (sourceType.equals(targetType)) {
							invalidEdge = true;
						}
						
						if (!invalidEdge) {
							Edge edge = new Edge(source, target);
							current.addElement(edge);
						} 
						else {
							ErrorHandler.handle(ErrorHandler.J_OPTION_PANE_WARNING, ErrorValues.ILLEGAL_EDGE);
						}
						panel.removeMouseListener(this);
						panel.deactivateEdgeMode();
					}
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
		};
		
		panel.addMouseListener(m);
	}
	
}
