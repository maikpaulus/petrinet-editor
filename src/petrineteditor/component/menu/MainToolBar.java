package petrineteditor.component.menu;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import petrineteditor.component.action.petrinet.ChangePanelSizeAction;
import petrineteditor.component.action.petrinet.ChangeZoomAction;
import petrineteditor.component.action.petrinet.CreatePetrinetAction;
import petrineteditor.component.action.petrinet.DeletePetrinetAction;
import petrineteditor.component.action.petrinet.LoadPetrinetAction;
import petrineteditor.component.action.petrinet.SavePetrinetAction;
import petrineteditor.component.action.petrinetelement.DisableEdgeModeAction;

/**
 * Klasse für die Werkzeugleiste (Singleton)
 * @author Maik Paulus
 * @version 1.0
 * @since 06.01.2015
 */
public class MainToolBar extends JToolBar
{
	private static final long serialVersionUID = 1L;

	public static final String STANDARD = "Zielkante wählen";
	
	private CreatePetrinetAction createPetrinetAction;
	private DeletePetrinetAction deletePetrinetAction;
	private LoadPetrinetAction loadPetrinetAction;
	private SavePetrinetAction savePetrinetAction;
	private ChangePanelSizeAction reducePanelSizeAction;

	private ChangePanelSizeAction extendPanelSizeAction;
	private ChangeZoomAction reduceZoomAction;
	private ChangeZoomAction extendZoomAction;
	private DisableEdgeModeAction disableEdgeModeAction;
	
	/**
	 * Singleton-Instanz der MainToolBar
	 */
	private static MainToolBar instance;
	
	/**
	 * Infolabel zum Anzeigen von Informationen
	 */
	private JLabel edgeMode;
	/**
	 * Initialisierung der Toolbar
	 */
	protected MainToolBar()
	{
		super();
		this.setFloatable(false);
		this.setRollover(true);
		this.addActions();
		this.addEdgeModeLabel();
	}
	
	/**
	 * Singleton-Methode für die Klasse
	 */
	public static MainToolBar getInstance()
	{
		if (instance == null) {
			instance = new MainToolBar();
		}
		
		return instance;
	}
	
	public CreatePetrinetAction getCreatePetrinetAction()
	{
		return createPetrinetAction;
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
	
	public ChangePanelSizeAction getReducePanelSizeAction()
	{
		return reducePanelSizeAction;
	}
	
	public ChangePanelSizeAction getExtendPanelSizeAction()
	{
		return extendPanelSizeAction;
	}
	
	public ChangeZoomAction getReduceZoomAction() {
		return reduceZoomAction;
	}
	
	public ChangeZoomAction getExtendZoomAction() {
		return extendZoomAction;
	}
	
	public DisableEdgeModeAction getDisableEdgeModeAction() 
	{
		return disableEdgeModeAction;
	}
	/**
	 * Fügt der Toolbar Standard-Aktionen hinzu
	 */
	private void addActions()
	{
		boolean withIcon = true;
		
		this.createPetrinetAction = new CreatePetrinetAction(withIcon);
		this.add(this.createPetrinetAction);
		
		
		this.loadPetrinetAction = new LoadPetrinetAction(withIcon);
		this.add(this.loadPetrinetAction);
		
		this.savePetrinetAction = new SavePetrinetAction(withIcon);
		this.savePetrinetAction.setEnabled(false);
		this.add(this.savePetrinetAction);
		
		this.deletePetrinetAction = new DeletePetrinetAction(withIcon);
		this.deletePetrinetAction.setEnabled(false);
		this.add(this.deletePetrinetAction);
		this.addSeparator();
		
		this.reducePanelSizeAction = new ChangePanelSizeAction(ChangePanelSizeAction.REDUCE_PANEL_SIZE, withIcon);
		this.reducePanelSizeAction.setEnabled(false);
		this.add(this.reducePanelSizeAction);
		
		this.extendPanelSizeAction = new ChangePanelSizeAction(ChangePanelSizeAction.EXTEND_PANEL_SIZE, withIcon);
		this.extendPanelSizeAction.setEnabled(false);
		this.add(this.extendPanelSizeAction);
		this.addSeparator();
		
		this.reduceZoomAction = new ChangeZoomAction(ChangeZoomAction.REDUCE_ZOOM, withIcon);
		this.reduceZoomAction.setEnabled(false);
		this.add(this.reduceZoomAction);
		
		this.extendZoomAction = new ChangeZoomAction(ChangeZoomAction.EXTEND_ZOOM, withIcon);
		this.extendZoomAction.setEnabled(false);
		this.add(this.extendZoomAction);
		this.addSeparator();
		
		this.disableEdgeModeAction = new DisableEdgeModeAction(withIcon);
		this.disableEdgeModeAction.setEnabled(false);
		this.add(this.disableEdgeModeAction);
		this.addSeparator();
	}
	
	/**
	 * Fügt der Toolbar ein Infopanel hinzu
	 */
	private void addEdgeModeLabel()
	{
		this.edgeMode = new JLabel(MainToolBar.STANDARD);
		
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 11);
		this.edgeMode.setFont(font);
		this.edgeMode.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
		this.edgeMode.setEnabled(false);
		this.add(this.edgeMode);
	}
	
	/**
	 * Gibt das Infolabel der MainToolBar zurück
	 * @return Das Infolabel
	 */
	public JLabel getEdgeModeLabel()
	{
		return this.edgeMode;
	}
	
}
