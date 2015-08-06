package petrineteditor.component.action.petrinet;

import javax.swing.JOptionPane;

import petrineteditor.component.action.BaseAction;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Petrinet;

/**
 * Action f�r das L�schen von Petrinetzen
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class DeletePetrinetAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	
	private static final String DELETE_MESSAGE = "M�chten Sie das Petrinetz wirklich l�schen?";
	private static final String ICON = "document--minus.png";
	private static final String TEXT = "Petrinetz l�schen";
	
	/**
	 * Erzeugt eine neue Action
	 */
	public DeletePetrinetAction()
	{
		super();
	}
	
	/**
	 * Erzeugt eine neue Action f�r die Toolbar
	 * @param withIcon
	 */
	public DeletePetrinetAction(boolean withIcon)
	{
		super(TEXT, ICON);
	}
	
	@Override
	public void run()
	{
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		Petrinet petrinet = controller.getCurrentPetrinet();
		
		if (petrinet == null) {
			return;
		}
		
		int response = JOptionPane.showConfirmDialog(null, DELETE_MESSAGE);
		
		if (response == 0) {
			controller.deletePetrinet(petrinet);
		}
	}

}
