package petrineteditor.view.dialog;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import petrineteditor.component.error.ErrorHandler;
import petrineteditor.component.error.ErrorValues;

/**
 * Allgemeiner Dialog zur Eingabe mit einem Textfeld
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public abstract class TextFieldView extends BaseDialogView
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Ein Textfeld zur Eingabe
	 */
	protected JTextField textfield;
	
	/**
	 * Vordefinierter Wert des Textfeldes
	 */
	protected String predefined;
	
	/**
	 * Erzeugt einen neuen Textfeld-Dialog mit vorgegebenem Wert
	 * @param predefined
	 */
	public TextFieldView(String predefined)
	{		
		super();
		
		if (predefined == null) {
			this.predefined = "";
		}
		else {
			this.predefined = predefined;
		}
		
		this.setModal(true);
		this.pack();
	}
	
	/**
	 * Prüft den eingegebenen Text auf Korrektheit
	 * @param emptyOption Flag, ob Text leer sein darf
	 * @return true, falls Text korrekt, false sonst
	 */
	protected boolean isValidText(boolean emptyOption)
	{
		String text = this.textfield.getText();
		text = text.trim();
		
		if (text.isEmpty() && emptyOption == false) {
			ErrorHandler.handle(
				ErrorHandler.J_OPTION_PANE_WARNING, 
				ErrorValues.TEXTFIELD_EMPTY
			);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Gibt den eingegebenen Text zurück
	 * @return Der eingegebene text des Textfeldes
	 */
	protected String getText()
	{
		return this.textfield.getText();
	}

	@Override
	protected void addComponents() {
		final TextFieldView me = this;
		
		this.textfield = new JTextField(this.predefined);
		this.textfield.setPreferredSize(new Dimension(200, 25));
		
		this.textfield.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					me.saveHandler();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					me.abortHandler();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		
		this.constraints.fill = GridBagConstraints.HORIZONTAL;
		this.constraints.gridwidth = 3;
		this.constraints.insets = new Insets(5, 5, 5, 5);
		this.constraints.gridx = 0;
		this.constraints.gridy = 0;
		
		this.add(this.textfield, this.constraints);
	}

	@Override
	protected abstract void saveHandler();
}
