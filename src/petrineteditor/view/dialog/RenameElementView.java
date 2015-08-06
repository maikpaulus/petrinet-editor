package petrineteditor.view.dialog;

import petrineteditor.component.action.petrinetelement.RenameElementAction;

/**
 * Dialog zum Umbenennen eines Elements
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class RenameElementView extends TextFieldView
{
	private static final long serialVersionUID = 1L;
	
	protected final static String DIALOG_TITLE = "Umbenennen";
	protected final static String DIALOG_DESC = "Bitte umbenennen:";
	protected final static boolean EMPTY_OPTION = true;
	protected final static int MAX_LENGTH = 30;
	
	/**
	 * Aktion, von der aus dieser Dialog geöffnet wird
	 */
	protected RenameElementAction action;
	
	/**
	 * Erzeugt ein neues Dialog-Fenster
	 * @param action Herkunft des Aufrufes 
	 * @param label Voreingestellte Bezeichnung
	 */
	public RenameElementView(RenameElementAction action, String label)
	{
		super(label);
		this.action = action;
		
		this.setTitle(DIALOG_TITLE);
	}

	@Override
	protected void saveHandler() {
		if (this.isValidText(EMPTY_OPTION)) {
			String label = this.getText();
			this.action.setLabel(label);
			this.dispose();
		}
	}
}
