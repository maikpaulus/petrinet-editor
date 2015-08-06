package petrineteditor;

import petrineteditor.controller.PetrinetEditorController;

/**
 * Hauptklasse, aus der heraus die Anwendung gestartet wird
 * 
 * @author Maik Paulus
 * @version 1.0
 * @since 04.01.2015
 * 
 */
public class MainPetrinetEditor
{
	/**
	 * main-Methode, die das Programm startet
	 * @param args Standardparameter
	 */
	public static void main(String[] args)
	{
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		controller.startProgram();
	}

}
