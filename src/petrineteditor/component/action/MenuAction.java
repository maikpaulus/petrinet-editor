package petrineteditor.component.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import petrineteditor.component.action.petrinet.CreatePetrinetAction;
import petrineteditor.component.action.petrinet.DeletePetrinetAction;
import petrineteditor.component.action.petrinet.LoadPetrinetAction;
import petrineteditor.component.action.petrinet.SavePetrinetAction;
import petrineteditor.component.action.petrineteditor.CloseEditorAction;
import petrineteditor.component.action.petrinetelement.CreateEdgeAction;
import petrineteditor.component.action.petrinetelement.CreatePlaceAction;
import petrineteditor.component.action.petrinetelement.CreateTransitionAction;
import petrineteditor.component.action.petrinetelement.PlaceMarkingAction;
import petrineteditor.component.action.petrinetelement.RemoveElementAction;
import petrineteditor.component.action.petrinetelement.RemoveSelectedAction;
import petrineteditor.component.action.petrinetelement.RenameElementAction;
import petrineteditor.component.action.petrinetelement.StartTransitionAction;
import petrineteditor.component.action.petrinetelement.TranslateNodeAction;
import petrineteditor.model.PetrinetElement;

/**
 * Klasse zum Verwalten der Aktionen in den Menüs (Hauptmenü, Toolbar, Kontextmenüs)
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class MenuAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	
	public static final String NEW_ACTION 					= "NEW";
	public static final String LOAD_ACTION 					= "LOAD";
	public static final String SAVE_ACTION 					= "SAVE";
	public static final String DELETE_ACTION 				= "DELETE";
	public static final String SETTINGS_ACTION 				= "SETTINGS";
	public static final String CLOSE_ACTION 				= "CLOSE";
	
	public static final String NEW_TRANSITION_ACTION		= "NEW_TRANSITION";
	public static final String NEW_PLACE_ACTION				= "NEW_PLACE";
	public static final String REMOVE_SELECTED_ACTION		= "REMOVE_SELECTED";
	
	public static final String RENAME_ELEMENT_ACTION		= "RENAME_ELEMENT";
	public static final String REMOVE_ELEMENT_ACTION		= "REMOVE_ELEMENT";
	public static final String TRANSLATE_NODE_ACTION		= "TRANSLATE_NODE";
	public static final String CREATE_EDGE_IN_ACTION		= "CREATE_EDGE_IN";
	public static final String CREATE_EDGE_OUT_ACTION		= "CREATE_EDGE_OUT";
	public static final String CHANGE_MARKING_ACTION		= "CHANGE_MARKING";
	public static final String START_TRANSITION_ACTION		= "START_TRANSITION";
	
	/**
	 * Der Action-Code
	 */
	private String actionCode;
	
	/**
	 * Liste von Parametern
	 */
	private Object[] parameter;

	/**
	 * Erzeugt eine neue Action eines Menüs
	 * @param itemName Der Name des Menu-Items
	 * @param action Die auszuführende Action
	 * @param parameter Parameter für die Action
	 */
	public MenuAction(String itemName, String action, Object[] parameter)
	{
		super(itemName);
		this.actionCode = action;
		this.parameter = parameter;
	}
	
	/**
	 * Führt eine Aktion anhand des Aktionscodes aus
	 */
	private void handleAction()
	{
		BaseAction action = null;
		
		switch(this.actionCode) {
			case MenuAction.NEW_ACTION:
				action = new CreatePetrinetAction();
				action.run();
				break;
			case MenuAction.LOAD_ACTION:
				action = new LoadPetrinetAction();
				action.run();
				break;
			case MenuAction.SAVE_ACTION:
				action = new SavePetrinetAction();
				action.run();
				break;
			case MenuAction.DELETE_ACTION:
				action = new DeletePetrinetAction();
				action.run();
				break;
			case MenuAction.CLOSE_ACTION:
				action = new CloseEditorAction();
				action.run();
				break;
			case MenuAction.NEW_TRANSITION_ACTION:
				action = new CreateTransitionAction((int) this.parameter[0], (int) this.parameter[1]);
				action.run();
				break;
			case MenuAction.NEW_PLACE_ACTION:
				action = new CreatePlaceAction((int) this.parameter[0], (int) this.parameter[1]);
				action.run();
				break;
			case MenuAction.REMOVE_SELECTED_ACTION:
				action = new RemoveSelectedAction();
				action.run();
				break;	
			case MenuAction.RENAME_ELEMENT_ACTION:
				action = new RenameElementAction(this.parameter[0]);
				action.run();
				break;
			case MenuAction.REMOVE_ELEMENT_ACTION:
				action = new RemoveElementAction(this.parameter[0]);
				action.run();
				break;
			case MenuAction.TRANSLATE_NODE_ACTION:
				ArrayList<PetrinetElement> nodes = new ArrayList<PetrinetElement>();
				nodes.add((PetrinetElement) this.parameter[0]);
				action = new TranslateNodeAction(nodes);
				action.run();
				break;
			case MenuAction.CREATE_EDGE_IN_ACTION:
				action = new CreateEdgeAction(this.parameter[0], false);
				action.run();
				break;
			case MenuAction.CREATE_EDGE_OUT_ACTION:
				action = new CreateEdgeAction(this.parameter[0], true);
				action.run();
				break;
			case MenuAction.CHANGE_MARKING_ACTION:
					action = new PlaceMarkingAction(this.parameter[0]);
					action.run();
				break;
			case MenuAction.START_TRANSITION_ACTION:
					action = new StartTransitionAction();
					action.run();
				break;	
			default:
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		this.handleAction();
	}
}
