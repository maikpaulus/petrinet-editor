package petrineteditor.component.menu;

import javax.swing.JPopupMenu;

import petrineteditor.component.action.MenuAction;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Petrinet;

/**
 * Generelles Kontextmenü der Zeichenfläche
 * @author Maik Paulus
 * @version 1.0
 * @since 06.01.2015
 */
public class GeneralPopupMenu extends JPopupMenu
{
	private static final long serialVersionUID = 1L;

	/**
	 * Erstellt ein neues Kontextmenü an der geklickten Stelle
	 * @param posX x-Koordinate des Mausevents
	 * @param posY y-Koordinate des Mausevents
	 */
	public GeneralPopupMenu(int posX, int posY)
	{
		super();
		
		Object[] parameter = new Object[] {
			posX,
			posY
		};
		
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		Petrinet current = controller.getCurrentPetrinet();
		
		this.add(new MenuAction("Neue Transition erstellen", MenuAction.NEW_TRANSITION_ACTION, parameter));
		this.add(new MenuAction("Neue Stelle erstellen", MenuAction.NEW_PLACE_ACTION, parameter));
		
		if (current.countSelectedElements() > 0) {
			this.add(new MenuAction("Ausgewählte Elemente löschen", MenuAction.REMOVE_SELECTED_ACTION, parameter));			
		}
	}
}
