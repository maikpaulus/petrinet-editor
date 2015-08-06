package petrineteditor.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import petrineteditor.component.menu.MainMenu;
import petrineteditor.component.menu.MainToolBar;
import petrineteditor.controller.PetrinetEditorController;
import petrineteditor.model.Petrinet;

/**
 * Hauptfenster der Anwendung (Singleton)
 * @author Maik Paulus
 * @version 1.0
 * @since 04.01.2015
 */
public class PetrinetEditorView extends JFrame implements Observer
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Titel des Hauptfensters
	 */
	final String WINDOW_TITLE = "Petrinetz-Editor: Maik Paulus, Matrnr. 9039031";
	
	/**
	 * Anfängliche Breite des Hauptfensters 
	 */
	final int WINDOW_WIDTH = 1000;
	
	/**
	 * Anfängliche Höhe des Hauptfensters 
	 */
	final int WINDOW_HEIGHT = 600;
	
	/**
	 * Singleton-Instanz des Hauptfensters
	 */
	private static PetrinetEditorView instance;
	
	/**
	 * Tabcontainer, der die Petrinetze enthält
	 */
	private JTabbedPane mainPanel;
	
	/**
	 * Konstruktor des Hauptfensters, nimmt Grundeinstellungen vor
	 */
	protected PetrinetEditorView()
	{
		this.setTitle(this.WINDOW_TITLE);
		this.setSize(this.WINDOW_WIDTH, this.WINDOW_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(0, 2));
		this.setLocationRelativeTo(this.getParent());
	}
	
	/**
	 * Singleton-Methode des Hauptfensters
	 * @return Gibt die einzige Instanz des Hauptfensters zurück
	 */
	public static PetrinetEditorView getInstance()
	{
		if (instance == null) {
			instance = new PetrinetEditorView();
		}
		
		return instance;
	}
	
	/**
	 * Rendern und Anzeigen das Hauptfensters 
	 */
	public void display()
	{
		this.addComponents();
		this.setVisible(true);
	}
	
	/**
	 * Fügt alle Komponenten zum Hauptfenster hinzu
	 */
	private void addComponents()
	{
		MainMenu mainMenu = MainMenu.getInstance();
		this.setJMenuBar(mainMenu);
		
		this.mainPanel = new JTabbedPane();
		final JTabbedPane me = this.mainPanel;
		
		this.mainPanel.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if (me.getSelectedIndex() != -1) {
					PetrinetEditorController controller = PetrinetEditorController.getInstance();
					ArrayList<Petrinet> petrinets = controller.getPetrinets();
					
					if (petrinets.size() > 0) {
						Iterator<Petrinet> iterator = petrinets.iterator();
						
						while (iterator.hasNext()) {
							Petrinet petrinet = iterator.next();
							petrinet.deselectAll();
						}
					}
				}
			}
		});
		
		MainToolBar toolbar = MainToolBar.getInstance();
		this.add(toolbar, BorderLayout.PAGE_START);
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1)
	{
		this.refresh();
	}
	
	/**
	 * Aktualisieren des Hauptfensters nach Änderungen
	 */
	public void refresh()
	{
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		ArrayList<Petrinet> petrinets = controller.getPetrinets();
		this.convertToTabs(petrinets);
	}
	
	/**
	 * Konvertiert eine Liste von Petrinetzen in sichtbare Tabs
	 * @param petrinets Liste aller offenenen Petrinetze
	 */
	private void convertToTabs(ArrayList<Petrinet> petrinets)
	{	
		this.mainPanel.removeAll();
		if (!petrinets.isEmpty()) {
			Iterator<Petrinet> iterator = petrinets.iterator();
			while(iterator.hasNext()) {
				Petrinet elem = iterator.next();
				PetrinetView view = new PetrinetView(elem);
				this.mainPanel.addTab(elem.getFileName(), view);
			}
			
			int tabs = this.mainPanel.getTabCount();
			this.mainPanel.setSelectedIndex(tabs-1);
			
			this.add(this.mainPanel, BorderLayout.CENTER);
			this.setVisible(true);
			
			this.updateActions(true);
		}
		else {
			this.updateActions(false);
		}
	}

	/**
	 * Aktualisiert die Actions, wenn ein Petrinetz geöffnet ist
	 * @param activate Flag, ob Features aktiviert oder deaktiviert werden sollen
	 */
	private void updateActions(boolean activate)
	{
		MainMenu menu = MainMenu.getInstance();
		menu.getDeletePetrinetAction().setEnabled(activate);
		menu.getSavePetrinetAction().setEnabled(activate);
		
		MainToolBar toolbar = MainToolBar.getInstance();
		toolbar.getDeletePetrinetAction().setEnabled(activate);
		toolbar.getSavePetrinetAction().setEnabled(activate);
		toolbar.getReducePanelSizeAction().setEnabled(activate);
		toolbar.getExtendPanelSizeAction().setEnabled(activate);
		toolbar.getReduceZoomAction().setEnabled(activate);
		toolbar.getExtendZoomAction().setEnabled(activate);
	}

	/**
	 * Gibt das aktuell offene Petrinetz zurück
	 * @return Gibt das aktuell offene Petrinetz zurück, null, falls kein Petrinetz offen ist
	 */
	public Petrinet getCurrentPetrinet()
	{
		
		if (this.mainPanel.getTabCount() == 0) {
			return null;
		}

		int currentTab = this.mainPanel.getSelectedIndex();
		
		PetrinetEditorController controller = PetrinetEditorController.getInstance();
		ArrayList<Petrinet> petrinets = controller.getPetrinets();
		
		Petrinet selected = petrinets.get(currentTab);
		return selected;
	}
}
