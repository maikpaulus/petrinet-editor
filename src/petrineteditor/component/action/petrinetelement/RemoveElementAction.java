package petrineteditor.component.action.petrinetelement;

import javax.swing.JOptionPane;

import petrineteditor.component.action.BaseAction;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Petrinet;
import petrineteditor.model.PetrinetElement;

/**
 * Action zum L�schen eines Elements
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class RemoveElementAction extends BaseAction
{
	private static final long serialVersionUID = 1L;

	private final static String DELETE_MESSAGE = "M�chten Sie das Element wirklich l�schen?";
	
	/**
	 * Das selektierte Petrinetz-Element
	 */
	private PetrinetElement element;
	
	/**
	 * Erzeugt eine neue Action mit ausgew�hltem Petrinetz-Element
	 * @param element
	 */
	public RemoveElementAction(Object element)
	{
		this.element = (PetrinetElement) element;
	}

	@Override
	public void run() {
		int shouldDelete = JOptionPane.showConfirmDialog(null, RemoveElementAction.DELETE_MESSAGE);
		
		if (shouldDelete == 0) {
			PetrinetEditorController controller = PetrinetEditorController.getInstance();
			Petrinet current = controller.getCurrentPetrinet();
			
			current.removeElement(element);
		}
	}
}
