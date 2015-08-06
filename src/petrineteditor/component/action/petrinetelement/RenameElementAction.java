package petrineteditor.component.action.petrinetelement;

import petrineteditor.component.action.BaseAction;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Node;
import petrineteditor.model.Petrinet;
import petrineteditor.view.dialog.RenameElementView;

/**
 * Action zum Umbenennen einer Knotenbezeichnung
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class RenameElementAction extends BaseAction
{	
	private static final long serialVersionUID = 1L;
	
	private Node element;
	private String label;

	/**
	 * Erzeugt eine neue Action mit übergebenem Knoten
	 * @param node
	 */
	public RenameElementAction(Object node) 
	{
		this.element = (Node) node;
	}
	
	@Override
	public void run()
	{
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		Petrinet current = controller.getCurrentPetrinet();
		
		RenameElementView view = new RenameElementView(this, element.getLabel());
		view.showDialog();
		
		if (this.label != null) {
			current.renameElement(this.element, this.label);
		}
	}
	
	/**
	 * Setzt die Bezeichnung eines Elements
	 * @param label Neue Bezeichnung
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}
}
