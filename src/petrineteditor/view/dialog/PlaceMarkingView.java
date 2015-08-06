package petrineteditor.view.dialog;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;

import petrineteditor.component.action.petrinetelement.PlaceMarkingAction;

/**
 * Dialog zum Editieren der Marken einer Stelle
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class PlaceMarkingView extends BaseDialogView 
{
	private static final long serialVersionUID = 1L;

	protected static String DIALOG_TITLE 	= "Marken ändern";
	
	/**
	 * Aktion, von der aus dieser Dialog geöffnet wird
	 */
	protected PlaceMarkingAction action;
	
	/**
	 * Eingabe der Marken
	 */
	protected JSpinner marking;
	
	/**
	 * Erzeugt ein neues Dialog-Fenster
	 * @param action Herkunft des Aufrufes 
	 * @param marking Vorausgewählte Anzahl der Marken
	 */
	public PlaceMarkingView(PlaceMarkingAction action, int marking) {
		super();
		this.setModal(true);
		
		this.action = action;
		
		this.marking = new JSpinner();
		this.marking.setValue(marking);
		
		this.setTitle(DIALOG_TITLE);
	}
	
	@Override
	protected void addComponents() {
		JLabel xlabel = new JLabel("Marken:");	
		
		this.constraints.gridy = 0;
		this.constraints.gridx = 0;
		this.constraints.gridwidth = 1;
		this.add(xlabel, this.constraints);
		
		DefaultEditor xeditor = (DefaultEditor) this.marking.getEditor();
		xeditor.setPreferredSize(new Dimension(150, 20));
		
		this.constraints.insets = new Insets(5, 5, 5, 5);
		this.constraints.gridy = 0;
		this.constraints.gridx = 1;
		this.constraints.gridwidth = 2;
		this.add(this.marking, this.constraints);
	}
	
	@Override
	protected void saveHandler() {
		int newMarking = (int) this.marking.getValue();
		this.action.setMarking(newMarking);
		this.dispose();
	}
}
