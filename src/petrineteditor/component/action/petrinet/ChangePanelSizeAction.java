package petrineteditor.component.action.petrinet;

import petrineteditor.component.action.BaseAction;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Petrinet;

/**
 * Action zur Veränderung der Größe der Zeichenfläche
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class ChangePanelSizeAction extends BaseAction 
{
	private static final long serialVersionUID = 1L;
	
	public static int REDUCE_PANEL_SIZE = 0;
	public static int EXTEND_PANEL_SIZE = 1;
	private static int CHANGE_SIZE = 100;
	private static final String[] ICON = {"zone-resize-actual.png","zone-resize.png"};
	private static final String[] TEXT = {"Zeichenfläche verkleinern", "Zeichenfläche vergrößern"};
	
	private int mode = 1;
	
	/**
	 * Erstellt die Action mit verschiedenen Modi
	 * @param mode Flag, ob Fläche größer oder kleiner werden soll
	 */
	public ChangePanelSizeAction(int mode)
	{
		this.mode = mode;
	}
	
	/**
	 * Erstellt die Action für die Toolbar
	 * @param mode Flag, ob Fläche größer oder kleiner werden soll
	 * @param withIcon Icon
	 */
	public ChangePanelSizeAction(int mode, boolean withIcon)
	{
		super(TEXT[mode], ICON[mode]);
		this.mode = mode;
	}
	
	@Override
	public void run() {
		if (this.mode == 0 || this.mode == 1) {
			PetrinetEditorController controller = PetrinetEditorController.getInstance();
			Petrinet current = controller.getCurrentPetrinet();
			
			if (current == null) {
				return;
			}
			
			if (this.mode == 1) {
				current.changeSize(ChangePanelSizeAction.CHANGE_SIZE);
			} 
			else {
				current.changeSize(-ChangePanelSizeAction.CHANGE_SIZE);
			}
		}		
	}
}
