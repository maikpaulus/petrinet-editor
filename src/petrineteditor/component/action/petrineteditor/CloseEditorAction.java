package petrineteditor.component.action.petrineteditor;

import javax.swing.JOptionPane;

import petrineteditor.component.action.BaseAction;

/**
 * Action zum Schließen des Editors
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class CloseEditorAction extends BaseAction
{
	private static final long serialVersionUID = 1L;

	private static final String CLOSE_MESSAGE = "Möchten Sie das Programm wirklich verlassen?";
	private static final String TEXT		  = "Editor beenden";
	private static final String ICON		  = "door-open.png";
	
	public CloseEditorAction() 
	{
		super(TEXT, ICON);
	}
	
	@Override
	public void run() {
		int response = JOptionPane.showConfirmDialog(null, CLOSE_MESSAGE);
		
		if (response == 0) {
			System.exit(0);
		}
	}

}
