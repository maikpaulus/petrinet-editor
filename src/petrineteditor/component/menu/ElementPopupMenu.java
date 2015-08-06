package petrineteditor.component.menu;

import javax.swing.JPopupMenu;

import petrineteditor.component.action.MenuAction;
import petrineteditor.model.PetrinetElement;
import petrineteditor.model.Transition;

/**
 * Kontextmen� eines Elementes
 * @author Maik Paulus
 * @version 1.0
 * @since 06.01.2015
 */
public class ElementPopupMenu extends JPopupMenu 
{
	private static final long serialVersionUID = 1L;
	
	private PetrinetElement element = null;
	private Object[] parameter;
	
	/**
	 * Erzeugt das Men� bei einem �bergebenem Element
	 * @param element Ein Petrinetz-Element
	 */
	public ElementPopupMenu(PetrinetElement element)
	{
		super();
		
		this.element = element;
		
		this.parameter = new Object[] {
			element
		};
		
		if (!element.isOfType("Edge")) {
			this.buildNodeItems();
		}
		
		if (element.isOfType("Transition")) {
			this.buildTransitionItems();
		}
		
		if (element.isOfType("Place")) {
			this.buildPlaceItems();
		}
		
		this.buildGeneralItems();
	}
	
	/**
	 * Men�eintr�ge nur f�r Transitions
	 */
	private void buildTransitionItems()
	{
		Transition transition = (Transition) this.element;
		if (transition.isActivated()) {
			this.addSeparator();
			this.add(new MenuAction("Aktivierte Transitionen schalten", MenuAction.START_TRANSITION_ACTION, this.parameter));
		}
	}
	
	/**
	 * Men�eintr�ge nur f�r Stellen
	 */
	private void buildPlaceItems()
	{
		this.addSeparator();
		this.add(new MenuAction("Markierung bearbeiten", MenuAction.CHANGE_MARKING_ACTION, this.parameter));
	}
	
	/**
	 * Men�eintr�ge nur f�r Knoten
	 */
	private void buildNodeItems()
	{
		this.add(new MenuAction("Element umbenennen", MenuAction.RENAME_ELEMENT_ACTION, this.parameter));
		this.add(new MenuAction("Verschieben nach", MenuAction.TRANSLATE_NODE_ACTION, this.parameter));
		this.add(new MenuAction("Eingangskante erstellen", MenuAction.CREATE_EDGE_IN_ACTION, this.parameter));
		this.add(new MenuAction("Ausgangsskante erstellen", MenuAction.CREATE_EDGE_OUT_ACTION, this.parameter));
	}
	
	/**
	 * Allgemeine Men�eintr�ge 
	 */
	private void buildGeneralItems()
	{
		if (!this.element.isOfType("Edge")) {
			this.addSeparator();
		}
		
		this.add(new MenuAction("Element l�schen", MenuAction.REMOVE_ELEMENT_ACTION, this.parameter));
	}
}
