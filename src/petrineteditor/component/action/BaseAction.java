package petrineteditor.component.action;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

/**
 * Allgemeine Klasse zum Behandeln von Actions
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public abstract class BaseAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	private static String RESOURCE_PATH = "petrineteditor/resources/";
	/**
	 * Erstellen einer normalen Action
	 */
	public BaseAction()
	{
		super();
	}
	
	/**
	 * Erstellen einer Action mit Text und Icon
	 * @param text Text
	 * @param image Bildpfad
	 */
	public BaseAction(String text, String image)
	{
		super(text);
		URL iconURL = ClassLoader.getSystemResource(BaseAction.RESOURCE_PATH + image);
		this.putValue(SMALL_ICON, new ImageIcon(iconURL));
		this.putValue(SHORT_DESCRIPTION, text);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.run();
	}

	/**
	 * Ausgeführte Methode der Action
	 */
	public abstract void run();
}
