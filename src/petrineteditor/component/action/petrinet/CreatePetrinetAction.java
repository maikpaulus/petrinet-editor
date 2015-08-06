package petrineteditor.component.action.petrinet;

import petrineteditor.component.action.BaseAction;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Petrinet;
import petrineteditor.view.dialog.CreatePetrinetView;

/**
 * Action zum Erstellen eines Petrinetzes
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class CreatePetrinetAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	
	private static final String ICON = "document--plus.png";
	private static final String TEXT = "Neues Petrinetz";
	
	private String filename = null;
	private Petrinet petrinet = null;
	
	/**
	 * Erzeugt eine neue Action
	 */
	public CreatePetrinetAction()
	{
		super();
		this.filename = null;
	}
	
	/**
	 * Erzeugt eine neue Action für die Toolbar
	 * @param withIcon
	 */
	public CreatePetrinetAction(boolean withIcon)
	{
		super(TEXT, ICON);
		this.filename = null;
	}
	
	/**
	 * Erzeugt eine neue Action mit vorgegebenem Dateinamen (Beim Laden)
	 * @param filename Der Dateiname
	 */
	public CreatePetrinetAction(String filename)
	{
		super();
		this.filename = filename;
	}
	
	/**
	 * Gibt das aktuelle Petrinetz zurück
	 * @return Das aktuelle Petrinetz
	 */
	public Petrinet getPetrinet()
	{
		return this.petrinet;
	}
	
	/** Gibt den Dateinamen zurück
	 * @return Der Dateiname
	 */
	public String getFilename()
	{
		return this.filename;
	}
	
	/**
	 * Setzt den Dateinamen
	 * @param filename Dateiname
	 */
	public void setFilename(String filename)
	{
		this.filename = filename;
	}
	
	@Override
	public void run()
	{
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
	
		if (this.filename == null) {
			CreatePetrinetView view = new CreatePetrinetView(this);
			view.showDialog();
		}

		if (this.filename != null) {
			this.petrinet = controller.createNewPetrinet(this.filename);
			this.filename = null;
		}
	}
}
