package petrineteditor.component.action.petrinetelement;

import java.util.ArrayList;

import petrineteditor.component.action.BaseAction;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Petrinet;
import petrineteditor.model.Transition;

/**
 * Action zur Schaltung eines Transitionsschrittes
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class StartTransitionAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Transition> transitions;
	
	/**
	 * Erzeugt eine neue Action mit übergebener Transition
	 * @param node
	 */
	public StartTransitionAction() 
	{
		this.getSelectedTransitions();
	}
	
	public void getSelectedTransitions()
	{
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		Petrinet current = controller.getCurrentPetrinet();

		this.transitions = current.getTransitionGroup();
	}
	
	@Override
	public void run() {
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		Petrinet current = controller.getCurrentPetrinet();

		if (this.transitions.size() > 0) {
			if (this.transitions.size() == 1) {
				Transition transition = this.transitions.get(0);
				current.startTransition(transition);				
			} 
			else {
				current.startTransitionGroup(this.transitions);				
			}
		}
	}

}
