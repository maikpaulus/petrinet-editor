package petrineteditor.component.action.petrinet;

import java.util.ArrayList;
import java.util.Iterator;

import petrineteditor.component.action.BaseAction;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Petrinet;
import petrineteditor.model.PetrinetEditor;

/**
 * Action zur Veränderung des Zooms in den Petrinetzen
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class ChangeZoomAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	
	public static int REDUCE_ZOOM = 0;
	public static int EXTEND_ZOOM = 1;
	
	private static final String[] ICON = {"magnifier-zoom-out.png","magnifier-zoom-in.png"};
	private static final String[] TEXT = {"Elemente verkleinern", "Elemente vergrößern"};
	
	private static int ZOOM_SIZE = 10;
	private int mode = 1;
	
	/**
	 * Erstellt die Action mit verschiedenen Modi
	 * @param mode Flag, ob der Zooms größer oder kleiner werden soll
	 */
	public ChangeZoomAction(int mode) 
	{
		this.mode = mode;
	}
	
	/**
	 * Erstellt die Action für die Toolbar
	 * @param mode Flag, ob Fläche größer oder kleiner werden soll
	 * @param withIcon Flag, dass ein Icon in die Action eingebaut werden soll
	 */
	public ChangeZoomAction(int mode, boolean withIcon)
	{
		super(TEXT[mode], ICON[mode]);
		this.mode = mode;
	}
	
	@Override
	public void run() {
		if (this.mode == 0 || this.mode == 1) {
			PetrinetEditor editor = PetrinetEditor.getInstance();
			int scale = editor.getScale();
			
			if (this.mode == 1) {
				editor.setScale(scale + ChangeZoomAction.ZOOM_SIZE);
			}
			else {
				editor.setScale(scale - ChangeZoomAction.ZOOM_SIZE);
			}
			
			PetrinetEditorController controller = PetrinetEditorController.getInstance();
			ArrayList<Petrinet> petrinets = controller.getPetrinets();
			
			if (petrinets.size() > 0) {
				Iterator<Petrinet> iterator = petrinets.iterator();
				
				while (iterator.hasNext()) {
					Petrinet petrinet = iterator.next();
					petrinet.updateAfterZoom();
				}
			}
		}
	}

}
