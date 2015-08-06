package petrineteditor.component.action.petrinetelement;

import petrineteditor.component.PetrinetPanel;
import petrineteditor.component.action.BaseAction;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Petrinet;

/**
 * Action zum Deaktivieren des Kantenmodus
 * @author Maik Paulus
 * @version 1.0
 * @since 06.01.2015
 */
public class DisableEdgeModeAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	
	private static final String ICON = "cross.png";
	private static final String TEXT = "Kanten-Modus beenden";
	
	/**
	 * Erzeugt eine neue Action für die Toolbar
	 * @param withIcon
	 */
	public DisableEdgeModeAction(boolean withIcon)
	{
		super(TEXT, ICON);
	}
	
	@Override
	public void run()
	{
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		Petrinet petrinet = controller.getCurrentPetrinet();
		
		if (petrinet != null) {
			PetrinetPanel panel = petrinet.getView().getPanel();
			panel.deactivateEdgeMode();
			panel.repaint();
		}
	}

}
