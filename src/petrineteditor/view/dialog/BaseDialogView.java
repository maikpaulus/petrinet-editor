package petrineteditor.view.dialog;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

/**
 * Algemeine Klasse für einen Dialog mit dem Anwender
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public abstract class BaseDialogView extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	protected static String BUTTON_SAVE 	= "Speichern";
	protected static String BUTTON_ABORT 	= "Abbrechen";
	
	/**
	 * Speichern-Button 
	 */
	protected JButton save;
	
	/**
	 * Abbrechen-Button
	 */
	protected JButton abort;
	
	/**
	 * Layout des Dialogs
	 */
	protected GridBagLayout layout;
	
	/**
	 * Layout-Constraints des Dialogs 
	 */
	protected GridBagConstraints constraints;
	
	/**
	 * Erzeugt ein neues Dialog-Fenster
	 */
	public BaseDialogView()
	{
		final BaseDialogView me = this;
		
		this.layout = new GridBagLayout();
		this.constraints = new GridBagConstraints();
		
		this.setResizable(false);
		this.setLayout(this.layout);
		this.setLocationRelativeTo(getRootPane());
				
		this.save = new JButton(BaseDialogView.BUTTON_SAVE);
		this.save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				me.saveHandler();				
			}
		});
		
		this.abort = new JButton(BaseDialogView.BUTTON_ABORT);
		this.abort.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				me.abortHandler();				
			}
		});
	}
	
	/**
	 * Fügt dem JDialog alle Komponenten hinzu und zeigt ihn an.
	 */
	public void showDialog()
	{
		this.addComponents();
		
		this.constraints.gridx = 0;
		this.constraints.gridy = this.constraints.gridy + 1;
		this.constraints.gridwidth = 1;
		this.constraints.insets = new Insets(2, 5, 5, 5);
		this.add(this.save, this.constraints);
		
		this.constraints.gridx = 1;
		this.add(this.abort, this.constraints);
		
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Schließt das Dialog-Fenster
	 */
	protected void abortHandler()
	{
		this.dispose();
	};
	
	/**
	 * Fügt dem Dialog variable Komponenten hinzu
	 */
	protected abstract void addComponents();
	
	/**
	 * Führt die Aktion nach Betätigen des Speichern-Buttons aus
	 */
	protected abstract void saveHandler();
	
}
