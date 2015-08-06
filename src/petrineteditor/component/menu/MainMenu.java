package petrineteditor.component.menu;

import petrineteditor.component.action.petrinet.CreatePetrinetAction;
import petrineteditor.component.action.petrinet.DeletePetrinetAction;
import petrineteditor.component.action.petrinet.LoadPetrinetAction;
import petrineteditor.component.action.petrinet.SavePetrinetAction;
import petrineteditor.component.action.petrineteditor.CloseEditorAction;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * Klasse für die Hauptmenüleiste
 * @author Maik Paulus
 * @version 1.0
 * @since 06.01.2015
 */
public class MainMenu extends JMenuBar
{
	private static final long serialVersionUID = 1L;
	
	private static MainMenu instance;
	private CreatePetrinetAction createPetrinetAction;
	private DeletePetrinetAction deletePetrinetAction;

	private LoadPetrinetAction loadPetrinetAction;
	private SavePetrinetAction savePetrinetAction;
	private CloseEditorAction closeEditorAction;
		
	public MainMenu() 
	{
		this.addItems();
	}
	
	public static MainMenu getInstance()
	{
		if (instance == null) {
			instance = new MainMenu();
		}
		
		return instance;
	}
	
	/**
	 * Fügt dem Hauptmenü Items zu
	 */
	private void addItems()
	{
		JMenu file = new JMenu("Datei");
		boolean withIcon = true;
			
		this.createPetrinetAction = new CreatePetrinetAction(withIcon);
		file.add(this.createPetrinetAction);
		
		this.loadPetrinetAction = new LoadPetrinetAction(withIcon);
		file.add(this.loadPetrinetAction);
		
		this.savePetrinetAction = new SavePetrinetAction(withIcon);
		this.savePetrinetAction.setEnabled(false);
		file.add(this.savePetrinetAction);
		
		this.deletePetrinetAction = new DeletePetrinetAction(withIcon);
		this.deletePetrinetAction.setEnabled(false);
		file.add(this.deletePetrinetAction);
		
		this.closeEditorAction = new CloseEditorAction();
		file.add(closeEditorAction);
		
		this.add(file);
	}
	
	public CreatePetrinetAction getCreatePetrinetItem()
	{
		return this.createPetrinetAction;
	}
	
	public CloseEditorAction getCloseEditorAction()
	{
		return closeEditorAction;
	}
	
	public DeletePetrinetAction getDeletePetrinetAction()
	{
		return deletePetrinetAction;
	}
	
	public LoadPetrinetAction getLoadPetrinetAction()
	{
		return loadPetrinetAction;
	}
	
	public SavePetrinetAction getSavePetrinetAction()
	{
		return savePetrinetAction;
	}
}
