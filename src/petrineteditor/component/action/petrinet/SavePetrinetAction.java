package petrineteditor.component.action.petrinet;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import petrineteditor.component.action.BaseAction;
import petrineteditor.component.parser.PNMLWriter;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Petrinet;
import petrineteditor.model.PetrinetEditor;
import petrineteditor.model.PetrinetElement;
import petrineteditor.view.PNChooser;

/**
 * Action zum Speichern eines Petrinetzes
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class SavePetrinetAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	
	private static final String ICON = "disks.png";
	private static final String TEXT = "Petrinetz speichern";
	
	/**
	 * Erzeugt eine neue Action
	 */
	public SavePetrinetAction()
	{
		super();
	}
	
	/**
	 * Erzeugt eine neue Action für die Toolbar
	 * @param withIcon
	 */
	public SavePetrinetAction(boolean withIcon)
	{
		super(TEXT, ICON);
	}
	
	@Override
	public void run() {     
        PetrinetEditorController controller = PetrinetEditorController.getInstance();
        Petrinet current = controller.getCurrentPetrinet();
        
        if (current != null) {
    		PNChooser filechooser = new PNChooser(current.getFileName());
    		int state = filechooser.showSaveDialog(null);
    		boolean validFile;
        	
    		if (state == PNChooser.APPROVE_OPTION) {
    			File file = filechooser.getSelectedFile();

    			if (!file.getAbsolutePath().endsWith(PNChooser.FILE_SUFFIX)) {
    				file = new File(filechooser.getSelectedFile() + PNChooser.FILE_SUFFIX);
    			}
    			
    			PNMLWriter pnmlWriter = new PNMLWriter(file);
    			validFile = pnmlWriter.startXMLDocument();    				
    			
    			if (!validFile) {
    				return;
    			}
    			
    			ArrayList<PetrinetElement> elements = current.getPetrinetElements();
    			
    			if (elements.size() > 0) {
    				Iterator<PetrinetElement> iterator = elements.iterator();
    				
    				while (iterator.hasNext()) {
    					PetrinetElement elem = iterator.next();
    					pnmlWriter = elem.saveContent(pnmlWriter);
    				}
    			}
    			
    			pnmlWriter.finishXMLDocument();
    			
    			String dir = file.getParent();
    			PetrinetEditor.setLastDirectory(dir);
    			System.out.println(file.getName());
    			current.setFileName(file.getName());
    		}
        }
	}

}
