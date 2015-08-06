package petrineteditor.component.error;

import javax.swing.JOptionPane;

/**
 * Allgemeiner Error-Handler für das Programm
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class ErrorHandler implements ErrorValues {
	
	/**
	 * Konstante für eine einfache Ausgabe einer Nachricht
	 */
	public static final int J_OPTION_PANE_MESSAGE = 1;
	
	/**
	 * Konstante für die Ausgabe einer Warnung
	 */
	public static final int J_OPTION_PANE_WARNING = 2;
	
	/**
	 * Steuert die Fehlerbehandlung
	 * @param errorType Angabe des Fehlertyps
	 * @param errorMessage Angabe der Fehlernachricht
	 */
	public static void handle(int errorType, String errorMessage)
	{
		switch (errorType) {
			
			case ErrorHandler.J_OPTION_PANE_MESSAGE:
				JOptionPane.showMessageDialog(null, errorMessage);
				break;
			
			case ErrorHandler.J_OPTION_PANE_WARNING:
				JOptionPane.showMessageDialog(null, errorMessage, "Warnung", JOptionPane.WARNING_MESSAGE);
				break;
			
			default:
				break;
		}
	}
	
}
