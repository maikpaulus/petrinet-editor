package petrineteditor.controller;

import java.util.ArrayList;
import java.util.Iterator;

import petrineteditor.component.error.ErrorHandler;
import petrineteditor.model.Petrinet;
import petrineteditor.model.PetrinetEditor;
import petrineteditor.view.PetrinetEditorView;

/**
 * Hauptcontroller, der allgemeine Aufgaben im Programm steuert und weitergibt (Singleton) 
 * 
 * @author Maik Paulus
 * @version 1.0
 * @since 04.01.2015
 */
public class PetrinetEditorController
{
	/**
	 * Singleton-Instanz des Controllers
	 */
	private static PetrinetEditorController instance = null;
	
	/**
	 * Singleton-Methode des Controllers
	 * @return Gibt die einzige Instanz des Controllers zurück
	 */
	public static PetrinetEditorController getInstance()
	{
		if (instance == null) {
			instance = new PetrinetEditorController();
		}
		
		return instance;
	}

	/**
	 * Start des Programms
	 */
	public void startProgram()
	{
		PetrinetEditorView petrinetEditorView = PetrinetEditorView.getInstance();
		petrinetEditorView.display();
	}

	/**
	 * Liefert alle offenen Petrinetze
	 * @return Eine Liste aller offenen Petrinetze (kann auch leer sein)
	 */
	public ArrayList<Petrinet> getPetrinets()
	{
		PetrinetEditor editor = PetrinetEditor.getInstance();
		return editor.getPetrinets();
	}

	/**
	 * Erzeugt ein neues Petrinetz
	 * @param label Name des Petrinetzes
	 * @return Das neu erzeugte Petrinetz oder null, wenn das Petrinetz bereits vorhanden ist
	 */
	public Petrinet createNewPetrinet(String label)
	{
		PetrinetEditor editor = PetrinetEditor.getInstance();
		
		if (this.fileNotExists(label)) {
			Petrinet petrinet = new Petrinet(label);
			editor.addPetrinet(petrinet);
			return petrinet;
		}
		else {
			ErrorHandler.handle(
				ErrorHandler.J_OPTION_PANE_MESSAGE,
				ErrorHandler.FILE_ALREADY_EXISTS
			);
			
			return null;
		}
	}

	/**
	 * Prüft, ob ein gewählter Name für das Petrinetz bereits existiert 
	 * @param file Zu prüfender Name des Petrinetzes
	 * @return true, wenn der Name noch nicht existiert, false sonst
	 */
	private boolean fileNotExists(String file)
	{
		ArrayList<Petrinet> petrinets = this.getPetrinets();
		
		if (petrinets.size() > 0) {
			Iterator<Petrinet> iterator = petrinets.iterator();
			
			while (iterator.hasNext()) {
				Petrinet petrinet = iterator.next();
				if (petrinet.getFileName().equals(file)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Löscht ein Petrinetz
	 * @param petrinet Das zu löschende Petrinetz
	 */
	public void deletePetrinet(Petrinet petrinet)
	{
		PetrinetEditor editor = PetrinetEditor.getInstance();
		editor.deletePetrinet(petrinet);
	}

	/**
	 * Gibt das aktuell offene Petrinetz zurück, ruft dazu entsprechende Hauptfenster-Methode auf 
	 * @return Das aktuelle Petrinetz, null falls kein Petrinetz offen ist 
	 */
	public Petrinet getCurrentPetrinet()
	{
		PetrinetEditorView petrinetEditorView = PetrinetEditorView.getInstance();
		Petrinet petrinet = petrinetEditorView.getCurrentPetrinet();
		return petrinet;
	}
	
}
