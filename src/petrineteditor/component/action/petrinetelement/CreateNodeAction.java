package petrineteditor.component.action.petrinetelement;

import petrineteditor.component.action.BaseAction;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Node;
import petrineteditor.model.Petrinet;

/**
 * Action zum Erstellen eines neuen Knotens
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public abstract class CreateNodeAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	
	private Node node;
	protected int posX;
	protected int posY;
	
	public void run() {
		this.node = this.getNode();
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		Petrinet petrinet = controller.getCurrentPetrinet();

		if (petrinet != null) {
			petrinet.addElement(this.node);			
		}
	}
	
	/**
	 * Gibt den Knoten zurück
	 * @return Knoten
	 */
	protected abstract Node getNode();
	
}
