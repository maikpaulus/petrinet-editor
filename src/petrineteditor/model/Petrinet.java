package petrineteditor.model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import petrineteditor.component.PetrinetPanel;
import petrineteditor.component.error.ErrorHandler;
import petrineteditor.view.PetrinetEditorView;
import petrineteditor.view.PetrinetView;

/**
 * Model-Klasse für ein Petrinetz
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class Petrinet extends Observable
{	
	/**
	 * Liste aller Elemente des Petrinetzes
	 */
	private ArrayList<PetrinetElement> elements;
	
	/**
	 * Name des Petrinetzes
	 */
	private String fileName;
	
	/**
	 * View zum Petrinetz
	 */
	private PetrinetView view = null;
	
	/**
	 * Globaler Zähler für die Elemente
	 */
	private int globalUniqueElemID = 0;

	/**
	 * Initialisierung des Petrinetzes
	 * @param fileName Name des Petrinetzes
	 */
	public Petrinet(String fileName)
	{
		this.elements = new ArrayList<PetrinetElement>();
		this.globalUniqueElemID = 0;
		this.fileName = fileName;
		this.addObserver(PetrinetEditorView.getInstance());
	}
	
	/**
	 * Erhöht den Elementzähler
	 * @return Der aktuelle Zählers 
	 */
	public int getUniqueElementID()
	{
		return globalUniqueElemID++;
	}
	
	/**
	 * Findet ein Element anhand der ID
	 * @param id ID des Elementes
	 * @return Das gesuchte Element oder null, falls nichts gefunden wurde
	 */
	public PetrinetElement findByID(String id)
	{
		if (this.elements.size() > 0 ) {
			Iterator<PetrinetElement> iterator = this.elements.iterator();
		
			while (iterator.hasNext()) {
				PetrinetElement elem = iterator.next();
				
				if (elem.getUniqueID().equals(id)) {
					return elem;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Fügt dem Petrinetz ein Element hinzu
	 * @param element Das hinzuzufügende Element
	 */
	public void addElement(PetrinetElement element)
	{
		if (!element.isOfType("Edge") || element.isValid()) {
			this.elements.add(element);
			element.getObservedBy(this);
		}
		else {
			ErrorHandler.handle(
				ErrorHandler.J_OPTION_PANE_WARNING,
				ErrorHandler.ILLEGAL_EDGE
			);
			this.view.getPanel().deactivateEdgeMode();
			this.view.repaint();
			
		}
	}

	/** Gibt den Namen des Petrinetzes zurück
	 * @return Name des Petrinetzes
	 */
	public String getFileName()
	{
		return this.fileName;
	}

	/** Setzt den Namen des Petrinetzes
	 * @param fileName Name des Petrinetzes
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
		this.setChanged();
		this.notifyObservers();
	}

	/** Setzt die zum Petrinetz gehörende View
	 * @param petrinetView Eine Petrinetz-View
	 */
	public void setView(PetrinetView petrinetView)
	{
		this.view = petrinetView;
	}
	
	/** 
	 * Gibt die zum Petrinetz gehörende View aus
	 * @return Die zugehörige View
	 */
	public PetrinetView getView()
	{
		return this.view;
	}

	/** Gibt die Elemente 
	 * @return Liste aller Petrinetz-Elemente
	 */
	public ArrayList<PetrinetElement> getPetrinetElements() {
		return this.elements;
	}
	
	public ArrayList<Transition> getTransitionGroup() {
		
		
		if (this.elements.size() > 0) {
			ArrayList<Transition> transitions = new ArrayList<Transition>();
			
			Iterator<PetrinetElement> iterator = this.elements.iterator();
			
			while (iterator.hasNext()) {
				PetrinetElement elem = iterator.next();
				
				if (elem.isOfType("Transition")) {
					Transition transition = (Transition) elem;
					
					if (transition.isInTransitionGroup()) {
						transitions.add(transition);						
					}
				}
			}
			
			return transitions;
		}
		
		return null;
	}
	
	/**
	 * Umbenennung eines Petrinetz-Knotens 
	 * @param node Der Petrinetz-Knoten
	 * @param label Der Name des Elementes
	 */
	public void renameElement(Node node, String label)
	{
		node.setLabel(label);
		this.view.repaint();
	}
	
	/**
	 * Löscht ein Element aus dem Petrinetz
	 * @param element Das zu löschende Element
	 */
	public void removeElement(PetrinetElement element)
	{
		this.elements.remove(element);
		this.checkForDependencies(element);
		this.view.repaint();
	}
	
	/**
	 * Sucht eine Kante in einem Petrinetz
	 * @param source Quelle der gesuchten Kante
	 * @param target Ziel der gesuchten Kante
	 * @return true, wenn die Kante gefunden wurde, false sonst
	 */
	public boolean findEdge(Node source, Node target)
	{
		if (!this.elements.isEmpty()) {
			Iterator<PetrinetElement> iterator = this.elements.iterator();
			
			while (iterator.hasNext()) {
				PetrinetElement elem = iterator.next();

				if (elem.isOfType("Edge")) {
					Edge edge = (Edge) elem;
					if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
						return true;
					};
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Prüft ein Element auf abhängige Kanten und löscht diese
	 * @param element Das zu prüfende Element 
	 */
	private void checkForDependencies(PetrinetElement element)
	{
		if (!this.elements.isEmpty()) {
			Iterator<PetrinetElement> iterator = this.elements.iterator();
			
			while (iterator.hasNext()) {
				PetrinetElement elem = iterator.next();

				if (elem.isOfType("Edge")) {
					Edge edge = (Edge) elem;
					if (edge.getSource().equals(element) || edge.getTarget().equals(element)) {
						if (edge.getTarget().isOfType("Transition")) {
							Transition transition = (Transition) edge.getTarget();
							transition.deactivate();
						}
						this.elements.remove(edge);
						
						iterator = this.elements.iterator();
					};
				}
			}
		}
	}
	
	/**
	 * Findet zu einem Knoten alle zugehörigen Kanten
	 * @param node Der zu prüfende Knoten
	 * @return Liste aller zugehörigen Kanten, kann auch leer sein
	 */
	private ArrayList<Edge> findRelatedEdges(Node node)
	{
		ArrayList<Edge> edges = new ArrayList<Edge>(this.elements.size());
		Iterator<PetrinetElement> iterator = this.elements.iterator();
		
		while (iterator.hasNext()) {
			PetrinetElement elem = iterator.next();
			if (elem.isOfType("Edge")) {
				Edge edge = (Edge) elem;
				if (edge.getSource().equals(node) || edge.getTarget().equals(node)) {
					edges.add(edge);
				};
			}
		}
		
		return edges;
	}
	
	/**
	 * Verschiebt einen Knoten auf der Fläche
	 * @param node Der gewünschte Knoten
	 * @param location Zielposition
	 */
	public void translateElement(Node node, Point location)
	{
		location = this.validate(location);
		node.setLocation(location);
		this.updateDependentEdges(node);
		this.view.repaint();
	}
	
	/**
	 * Prüft die übergebene Position auf Korrektheit
	 * @param location Eine Positionsangabe
	 * @return Die geprüfte und eventuell korrigierte Position
	 */
	private Point validate(Point location)
	{
		PetrinetPanel panel = this.view.getPanel();
		Dimension dim = panel.getSize();
		
		if (location.x < 0) {
			location.x = 0;
		}
		
		if (location.x > dim.width - 2*PetrinetEditor.getElementSize()) {
			location.x = dim.width - 2*PetrinetEditor.getElementSize();
		}
		
		if (location.y < 0) {
			location.y = 0;
		}
		
		if (location.y > dim.height - 2*PetrinetEditor.getElementSize()) {
			location.y = dim.height - 2*PetrinetEditor.getElementSize();
		}
		
		return location;
	}
	
	/**
	 * Kanten zu einem Knoten werden neu berechnet
	 * @param node Der betroffene Knoten
	 */
	private void updateDependentEdges(Node node)
	{
		Iterator<PetrinetElement> iterator = this.elements.iterator();
		
		while (iterator.hasNext()) {
			PetrinetElement elem = iterator.next();

			if (elem.isOfType("Edge")) {
				Edge edge = (Edge) elem;
				if (edge.getSource().equals(node) || edge.getTarget().equals(node)) {
					edge.update();
				};
			}
		}
	}

	/**
	 * Ändert die Marken einer Stelle
	 * @param place Betroffene Stelle
	 * @param marking Neue Marke
	 */
	public void changeMarking(Place place, int marking)
	{
		place.setMarking(marking);
		this.view.repaint();
	}
	
	/**
	 * Prüft, ob eine Transition schaltbar ist und markiert diese gegebenenfalls
	 * @param transition Die zu prüfende Transition
	 */
	public void checkActivationState(Transition transition)
	{
		ArrayList<Edge> related = this.findRelatedEdges(transition);
		boolean activation = true;
		int countEdges = 0;
		
		if (related.size() > 0) {
			Iterator<Edge> iterator = related.iterator();
			
			while (iterator.hasNext()) {
				Edge edge = iterator.next();
				
				if (edge.getTarget().equals(transition)) {
					countEdges++;
					Place place = (Place) edge.getSource();
					if (place.getMarking() == 0) {
						activation = false;
					}
				}
			}
			
		}
		
		if (countEdges == 0) {
			activation = false;
		}
		
		if (activation) {
			transition.activate();
		} 
		else {
			transition.deactivate();
		}
		
	}
	
	public void calculateResources(ArrayList<Transition> transitions) {
		if (transitions.size() > 0) {
			Iterator<Transition> iterator = transitions.iterator();
			
			while (iterator.hasNext()) {
				Transition transition = iterator.next();
				ArrayList<Edge> related = this.findRelatedEdges(transition);
				
				if (related.size() > 0) {
					Iterator<Edge> edgeIt = related.iterator();
					
					while (edgeIt.hasNext()) {
						Edge edge = edgeIt.next();
						Node node = edge.getSource();
						
						if (node.isOfType("Place")) {
							Place place = (Place) node;
							place.raiseLimit();
						}
					}
				}
			}
		}
	}
	
	public void startTransitionGroup(ArrayList<Transition> transitions)
	{	
		if (transitions.size() > 0) {
			Iterator<Transition> iterator = transitions.iterator();
			
			if (this.isValidGroup(transitions)) {
				while (iterator.hasNext()) {
					Transition transition = iterator.next();
					this.startTransition(transition);
				}
			}
			else {
				System.out.println("Transaktionsschritt kann nicht gestartet werden.");
			}
		}
	}
	
	public boolean isValidGroup(ArrayList<Transition> transitions) {
		this.calculateResources(transitions);

		Iterator<PetrinetElement> iterator = this.elements.iterator();
		
		while (iterator.hasNext()) {
			PetrinetElement elem = iterator.next();
			
			if (elem.isOfType("Place")) {
				Place place = (Place) elem;

				if (place.getMarking() < place.getLowLimit()) {
					System.out.println("Marken: " + place.getMarking());
					System.out.println("Limit: " + place.getLowLimit());
					place.resetLimit();
					return false;
				}
				
				place.resetLimit();
			}
		}
		
		return true;
	}

	/**
	 * Startet einen Schaltvorgang einer Transition
	 * @param transition Die zu schaltende Transition
	 */
	public void startTransition(Transition transition) {
		ArrayList<Edge> edges = this.findRelatedEdges(transition);

		if (edges.size() > 0) {
			Iterator<Edge> iterator = edges.iterator();
			
			while (iterator.hasNext()) {
				Edge edge = iterator.next();
				Place place;
				
				if (edge.getSource() == transition) {
					place = (Place) edge.getTarget();
					int markings = place.getMarking() + 1;
					place.setMarking(markings);
				}
				else {
					place = (Place) edge.getSource();
					int markings = place.getMarking() - 1;
					place.setMarking(markings);
				}
			}
			
			this.view.repaint();
		}
	}
	
	/** Verändert die Größe der Zeichenfläche
	 * @param diff Wert der Änderung
	 */
	public void changeSize(int diff)
	{
		PetrinetPanel panel = this.view.getPanel();
		Dimension dim = panel.getSize();
		dim.setSize(dim.width + diff, dim.height + diff);
		panel.setPreferredSize(dim);
		
		this.view.revalidate();
		panel.revalidate();
	}

	/**
	 * Jedes Petrinetz wird nach einem Zoom-Vorgang aktualisiert
	 */
	public void updateAfterZoom() {
		if (this.elements.size() > 0) {
			Iterator<PetrinetElement> iterator = this.elements.iterator();
			
			while (iterator.hasNext()) {
				PetrinetElement elem = iterator.next();
				if (!elem.isOfType("Edge")) {
					Node node = (Node) elem;
					this.updateDependentEdges(node);
				}
			}
			
			this.view.repaint();		
		}
	}

	/**
	 * Alle ausgewählten Elemente werden deselektiert
	 */
	public void deselectAll()
	{
		if (this.elements.size() > 0) {
			Iterator<PetrinetElement> iterator = this.elements.iterator();
			
			while (iterator.hasNext()) {
				PetrinetElement elem = iterator.next();
				elem.deselect();
			}
		}
	}
	
	/**
	 * Zählt die aktuell ausgewählten Elemente
	 * @return Anzahl der ausgewählten Elemente
	 */
	public int countSelectedElements()
	{
		int count = 0;
		
		if (this.elements.size() > 0) {
			Iterator<PetrinetElement> iterator = this.elements.iterator();
			
			while (iterator.hasNext()) {
				PetrinetElement elem = iterator.next();
				if (elem.isSelected()) {
					count++;
				}
			}
		}
		
		return count;
	}
}

