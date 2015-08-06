package petrineteditor.component.action.petrinet;

import java.io.File;

import petrineteditor.component.action.BaseAction;
import petrineteditor.component.parser.PNMLParser;
import petrineteditor.model.Petrinet;
import petrineteditor.model.PetrinetEditor;
import petrineteditor.view.PNChooser;

/**
 * Action zum Laden eines Petrinetzes aus einer Datei
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class LoadPetrinetAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	
	private static final String ICON = "folder-horizontal-open.png";
	private static final String TEXT = "Petrinetz öffnen";
		
	/**
	 * Erzeugt eine neue Action
	 */
	public LoadPetrinetAction()
	{
		super();
	}
	
	/**
	 * Erzeugt eine neue Action für die Toolbar
	 * @param withIcon
	 */
	public LoadPetrinetAction(boolean withIcon)
	{
		super(TEXT, ICON);
	}
	
	@Override
	public void run() {
		PNChooser filechooser = new PNChooser(null);
		int state = filechooser.showOpenDialog(null);
		boolean validFile;
		
		if (state == PNChooser.APPROVE_OPTION) {
			File file = filechooser.getSelectedFile();
			PNMLParser parser = new PNMLParser(file);
			validFile = parser.initParser();
			
			if (validFile) {
				CreatePetrinetAction action = new CreatePetrinetAction(file.getName());
				action.run();
				Petrinet petrinet = action.getPetrinet();
				
				if (petrinet != null) {
					parser.parse(petrinet);
					String dir = file.getParent();
					PetrinetEditor.setLastDirectory(dir);
				}	
			}
		}	
	}
}
