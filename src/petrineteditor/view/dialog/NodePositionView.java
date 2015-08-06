package petrineteditor.view.dialog;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;

import petrineteditor.component.action.petrinetelement.TranslateNodeAction;

/**
 * Dialog zum Verschieben eines Knotens
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class NodePositionView extends BaseDialogView 
{
	private static final long serialVersionUID = 1L;

	protected static String DIALOG_TITLE 	= "Position ändern";
	
	/**
	 * Aktion, von der aus dieser Dialog geöffnet wird
	 */
	protected TranslateNodeAction action;
	
	/**
	 * Auswahl der x-Koordinate
	 */
	protected JSpinner xposition;
	
	/**
	 * Auswahl der y-Koordinate
	 */
	protected JSpinner yposition;
	
	/**
	 * Vordefinierte Koordinate 
	 */
	protected Point point;
	
	/**
	 * Erzeugt ein neues Dialog-Fenster
	 * @param action Herkunft des Aufrufes 
	 * @param location Übergebener Punkt
	 */
	public NodePositionView(TranslateNodeAction action, Point location) {
		super();
		this.setModal(true);
		
		this.action = action;
		this.point = location;

		this.setTitle(DIALOG_TITLE);
	}
	
	@Override
	protected void addComponents() {
		JLabel xlabel = new JLabel("X-Position:");	
		
		this.constraints.gridy = 0;
		this.constraints.gridx = 0;
		this.constraints.gridwidth = 1;
		this.add(xlabel, this.constraints);
		
		this.xposition = new JSpinner();
		this.xposition.setValue(this.point.x);
		DefaultEditor xeditor = (DefaultEditor) this.xposition.getEditor();
		xeditor.setPreferredSize(new Dimension(150, 20));
		
		this.constraints.insets = new Insets(5, 5, 5, 5);
		this.constraints.gridy = 0;
		this.constraints.gridx = 1;
		this.constraints.gridwidth = 2;
		this.add(this.xposition, this.constraints);	
		
		JLabel ylabel = new JLabel("Y-Position:");	
		
		this.constraints.gridy = 1;
		this.constraints.gridx = 0;
		this.constraints.gridwidth = 1;
		this.add(ylabel, this.constraints);
		
		this.yposition = new JSpinner();
		this.yposition.setValue(this.point.y);
		DefaultEditor yeditor = (DefaultEditor) this.yposition.getEditor();
		yeditor.setPreferredSize(new Dimension(150, 20));
		
		this.constraints.insets = new Insets(5, 5, 5, 5);
		this.constraints.gridy = 1;
		this.constraints.gridx = 1;
		this.constraints.gridwidth = 2;
		
		this.add(this.yposition, this.constraints);	
	}
	
	@Override
	protected void saveHandler() {
		int x = (int) this.xposition.getValue();
		int y = (int) this.yposition.getValue();
		Point p = new Point(x, y);
		this.action.setPoint(p);
		this.dispose();
	}
}
