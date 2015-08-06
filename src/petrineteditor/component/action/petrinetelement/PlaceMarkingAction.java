package petrineteditor.component.action.petrinetelement;

import petrineteditor.component.action.BaseAction;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Petrinet;
import petrineteditor.model.Place;
import petrineteditor.view.dialog.PlaceMarkingView;

/**
 * Action zum Ändern der Marken der Stellen
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class PlaceMarkingAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	
	private Place element;
	private int marking;
	
	/**
	 * Erzeugt eine neue Action mit übergebener Stelle
	 * @param element Die Stelle, die verändert werden soll
	 */
	public PlaceMarkingAction(Object element)
	{
		this.element = (Place) element;
		this.marking = this.element.getMarking();
	}

	@Override
	public void run() {
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		Petrinet current = controller.getCurrentPetrinet();
		
		PlaceMarkingView view = new PlaceMarkingView(this, this.marking);
		view.showDialog();
	
		current.changeMarking(this.element, this.marking);
	}

	/**
	 * Setzt die Marke der Stelle 
	 * @param newMarking Neue Marke
	 */
	public void setMarking(int newMarking) {
		this.marking = newMarking;
	}
}
