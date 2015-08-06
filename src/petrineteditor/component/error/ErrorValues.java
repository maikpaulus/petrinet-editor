package petrineteditor.component.error;

/**
 * Verschiedene vordefinierte Fehlernachrichten
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public interface ErrorValues
{	
	public static final String TEXTFIELD_EMPTY 		= "Das Textfeld darf nicht leer sein.";
	public static final String FILE_ALREADY_EXISTS 	= "Das von Ihnen gewünschte Petrinetz existiert bereits.";

	public static final String ILLEGAL_EDGE			= "Die Kante konnte nicht erstellt werden.";
	public static final String INVALID_FILE_NAME 	= "Der Name der Datei ist ungültig.";
	public static final String INVALID_XML			= "Die XML-Datei ist ungültig.";
	public static final String FILE_NOT_LOADED 		= "Die Datei konnte nicht geladen werden.";
}