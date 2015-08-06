package petrineteditor.component.action.petrinetelement;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import petrineteditor.component.action.BaseAction;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Petrinet;
import petrineteditor.model.PetrinetElement;

/**
 * Action zum Löschen aller ausgewählten Elemente
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class RemoveSelectedAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	private final static String DELETE_MESSAGE = "Möchten Sie diese Elemente wirklich löschen?";
	
	@Override
	public void run() {
		int shouldDelete = JOptionPane.showConfirmDialog(null, RemoveSelectedAction.DELETE_MESSAGE);
		
		if (shouldDelete == 0) {
			PetrinetEditorController controller = PetrinetEditorController.getInstance();
			Petrinet current = controller.getCurrentPetrinet();
			
			ArrayList<PetrinetElement> elements = current.getPetrinetElements();
			ArrayList<PetrinetElement> deleted = new ArrayList<PetrinetElement>();
		
			if (elements.size() > 0) {
				Iterator<PetrinetElement> iterator = elements.iterator();
				
				while (iterator.hasNext()) {
					PetrinetElement elem = iterator.next();
					
					if (elem.isSelected()) {
						deleted.add(elem);
					}
				}									
			}
			
			if (deleted.size() > 0) {
				Iterator<PetrinetElement> iterator = deleted.iterator();
				
				while (iterator.hasNext()) {
					PetrinetElement elem = iterator.next();
					current.removeElement(elem);
				}									
			}
		}
	}

}
