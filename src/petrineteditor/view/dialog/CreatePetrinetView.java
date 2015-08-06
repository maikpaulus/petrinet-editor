package petrineteditor.view.dialog;

import petrineteditor.component.action.petrinet.CreatePetrinetAction;

/**
 * Dialog zum Erzeugen eines neuen Petrinetzes
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class CreatePetrinetView extends TextFieldView
{
	private static final long serialVersionUID = 1L;
	
	final static String DIALOG_TITLE = "Neues Petrinetz erstellen";
	final static String DIALOG_DESC = "Bitte geben Sie einen Dateinamen ein:";
	final static boolean EMPTY_OPTION = false;
	
	/**
	 * Aktion, von der aus dieser Dialog geöffnet wird
	 */
	protected CreatePetrinetAction action;
	
	/**
	 * Erzeugt ein neues Dialog-Fenster
	 * @param action Herkunft des Aufrufes
	 */
	public CreatePetrinetView(CreatePetrinetAction action)
	{
		super("Unbenannt");
		this.action = action;
		this.setTitle(DIALOG_TITLE);
	}

	@Override
	protected void saveHandler()
	{
		if (this.isValidText(EMPTY_OPTION)) {
			String filename = this.getText();
			this.action.setFilename(filename);
			this.dispose();
		}
	}
	
}
