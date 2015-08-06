package petrineteditor.model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import petrineteditor.component.MathCalc;
import petrineteditor.component.parser.PNMLWriter;

/**
 * Model-Klasse für eine Kante
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class Edge extends PetrinetElement
{
	/**
	 * Dieser Wert besagt, wie genau man auf eine Kante klicken muss, um sie auszuwählen
	 */
	private static int SELECT_EPSILON = 4;
	
	/**
	 * Quell-Knoten
	 */
	private Node source;
	
	/**
	 * Ziel-Knoten
	 */
	private Node target;
	
	/**
	 * Flag, ob die Kante korrekt ist
	 */
	private boolean isValidEdge;
	
	/**
	 * Endpunkt der Kante
	 */
	private Point endLocation;
	
	/**
	 * Steigung der Kante
	 */
	private double gradient;
	
	/**
	 * Hilfs-Punkt am Start der Kante
	 */
	private Point startHelper;
	
	/**
	 * Hilfs-Punkt am Ende der Kante
	 */
	private Point endHelper;
	
	/**
	 * Erzeugt, falls die Kante valide ist, eine neue Kante mit übergebener Quelle und Ziel
	 * @param source Quell-Knoten
	 * @param target Ziel-Knoten
	 */
	public Edge(Node source, Node target)
	{
		super(0, 0);
		this.endLocation = null;
				
		String sourceType = source.getClass().getSimpleName();
		String targetType = target.getClass().getSimpleName();
		this.isValidEdge = false;
		
		if (!sourceType.equals(targetType)) {
			this.source = source;
			this.target = target;
			this.isValidEdge = true;
			
			this.renderEndPoints();
			
			this.location = this.startHelper;
			this.endLocation = this.endHelper;
		}
	}
	
	/**
	 * Gibt den Quell-Knoten zurück
	 * @return Der Quell-Knoten
	 */
	public Node getSource()
	{
		return this.source;
	}
	
	/**
	 * Gibt den Ziel-Knoten zurück
	 * @return Der Ziel-Knoten
	 */
	public Node getTarget()
	{
		return this.target;
	}
	
	/**
	 * Berechnet die Endpunkte der Kante
	 */
	private void renderEndPoints() {	
		int size = PetrinetEditor.getElementSize();
		double step = 0;
		
		this.startHelper = new Point(source.getLocation());
		this.startHelper.translate(size, size);
		
		this.endHelper = new Point(target.getLocation());
		this.endHelper.translate(size, size);
		
		this.gradient = MathCalc.gradient(this.startHelper, this.endHelper);
		
		if (this.endHelper.distance(this.startHelper) == 0.0) {
			return;
		}
		
		if (Math.abs(this.startHelper.x - this.endHelper.x) == 0) {
			if (this.startHelper.y <= this.endHelper.y) {
				this.startHelper.translate(0, size);
				this.endHelper.translate(0, -size);
				return;
			}
			else {
				this.startHelper.translate(0, -size);
				this.endHelper.translate(0, size);
				return;
			}
		}
		
		step = calculateStepsToWalk();
		
		if (this.endHelper.x > this.startHelper.x) {
			this.calculatePoints(step);
		}
		else {
			this.calculateNegPoints(step);
		}
	}
	
	/**
	 * Ermittlung der Berechnungs-Schritte
	 * @return Schrittgeschwindigkeit bei der Berechnung
	 */
	private double calculateStepsToWalk()
	{
		double step = 0;
		
		if (Math.abs(this.gradient) > 1) {
			step = Math.abs(1/this.gradient);
		}
		else {
			step = Math.abs(this.gradient);
		}
		
		if (step == 0.0) {
			step = 1;
		}
		return step;
	}
	
	/**
	 * Berechnung der Endpunkte anhand der Berechnungsschritte. Hierzu wandert ein Hilfspunkt von einem Endpunkt zum anderen und prüft
	 * jeweils, ob dieser Hilfspunkt in den Elementen enthalten wird.
	 * Richtung: Startpunkt zu Endpunkt (abhängig von der Steigung der Kante)
	 * @param step Berechnungsschritte
	 */
	private void calculatePoints(double step)
	{
		Point temp = new Point(this.endHelper);
		Point itEnd = new Point(temp);
		
		for (double i = this.endHelper.x; i >= this.startHelper.x; i-= step) {
			temp.setLocation(i, this.gradient*(i - this.startHelper.x) + this.startHelper.y);
			if (this.target.contains(temp)) {
				itEnd.setLocation(temp);
			}
			
			
			if (this.source.contains(temp)) {
				this.endHelper = itEnd;
				this.startHelper = temp;
				return;
			}
		}
	}
	
	/**
	 * Berechnung der Endpunkte anhand der Berechnungsschritte. Hierzu wandert ein Hilfspunkt von einem Endpunkt zum anderen und prüft
	 * jeweils, ob dieser Hilfspunkt in den Elementen enthalten wird.
	 * Richtung: Endpunkt zu Startpunkt (abhängig von der Steigung der Kante)
	 * @param step Berechnungsschritte
	 */
	private void calculateNegPoints(double step)
	{
		Point temp = new Point(this.startHelper);
		Point itEnd = new Point(temp);
				
		for (double i = this.endHelper.x; i <= this.startHelper.x; i+= step) {
			temp.setLocation(i, this.gradient*(i - this.startHelper.x) + this.startHelper.y);
			if (this.target.contains(temp)) {
				itEnd.setLocation(temp);
			}
			
			if (this.source.contains(temp)) {
				this.endHelper = itEnd;
				this.startHelper = temp;
				return;
			}
		}
	}
	
	/**
	 * Simuliert das Wandern entlang der Kante
	 * @param p Hilfspunkt
	 * @param start Startpunkt
	 * @param i i-ter Berechnungsschritt
	 * @return Gibt den Hilfspunkt zurück
	 */
	private Point walkOnEdge(Point p, Point start, double i)
	{
		p.setLocation(i, this.gradient*(i - start.x) + start.y);
		return p;
	}
	
	/**
	 * Gibt den Endpunkt zurück
	 * @return Der Endpunkt der Kante
	 */
	public Point getEndLocation()
	{
		return this.endLocation;
	}
	
	/**
	 * Setzt den Endpunkt der Kante
	 * @param x x-Koordinate des Endpunktes
	 * @param y y-Koordinate des Endpunktes
	 */
	public void setEndLocation(int x, int y)
	{
		this.endLocation.x = x;
		this.endLocation.y = y;
	}
	
	/**
	 * Setzt den Endpunkt der Kante
	 * @param p Koordinate des Endpunktes
	 */
	public void setEndLocation(Point p)
	{
		this.endLocation = p;
	}
	
	/**
	 * Erzeugt die Pfeilspitze der Kante. Hierzu wird auf die Transformationsmatrix zugegriffen
	 * @return Die Pfeilspitze als Polygon
	 */
	private Polygon createRotatedTriangle()
	{
		int size = PetrinetEditor.getElementSize() / 2;
		Polygon p = new Polygon();
		p.addPoint(this.endLocation.x, this.endLocation.y);
		
		// Drehung mit diesen beiden Punkten 
		Point firstCorner = new Point(-size, size);
		Point secondCorner = new Point(-size, -size);
		
		double radian = 0;
		
		if (this.location.x == this.endLocation.x) {
			if (this.location.y == this.endLocation.y) {
				return p;
			}
			
			if (this.location.y > this.endLocation.y) {
				radian = 1.5*Math.PI + Math.atan(-this.gradient);				
			}
			else {
				radian = 0.5*Math.PI + Math.atan(-this.gradient);				
			}
		}
		else {
			if (this.location.x < this.endLocation.x) {
				radian =  Math.atan(this.gradient);
			}
			else {
				radian = Math.PI - Math.atan(-this.gradient);
			}
		}
			
		// Transformationsberechnung anhand Drehmatrix im R2
		double xx = firstCorner.x*Math.cos(radian) - firstCorner.y*Math.sin(radian);
		double yy = firstCorner.x*Math.sin(radian) + firstCorner.y*Math.cos(radian);
		
		double xxx = secondCorner.x*Math.cos(radian) - secondCorner.y*Math.sin(radian);
		double yyy = secondCorner.x*Math.sin(radian) + secondCorner.y*Math.cos(radian);
		
		firstCorner.setLocation(xx, yy);
		firstCorner.translate(this.endLocation.x, this.endLocation.y);
		secondCorner.setLocation(xxx, yyy);
		secondCorner.translate(this.endLocation.x, this.endLocation.y);
		
		p.addPoint(firstCorner.x, firstCorner.y);
		p.addPoint(secondCorner.x, secondCorner.y);
		return p;
	}

	/**
	 * Aktualisiert die Kante (Neuberechnung der Endpunkte)
	 */
	public void update()
	{
		this.renderEndPoints();
		
		this.location = this.startHelper;
		this.endLocation = this.endHelper;
	}
	
	@Override
	public Graphics2D visualize(Graphics2D g) {
		g = super.visualize(g);
		g.drawLine(this.location.x, this.location.y, this.endLocation.x, this.endLocation.y);
		
		Polygon p = this.createRotatedTriangle();
		g.fillPolygon(p);
		
		return g;
	}

	@Override
	public PNMLWriter saveContent(PNMLWriter pnmlWriter)
	{
		String id = this.getUniqueID();
		String sourceID = this.getSource().getUniqueID();
		String targetID = this.getTarget().getUniqueID();
		
		pnmlWriter.addArc(id, sourceID, targetID);
		return pnmlWriter;
	}
	
	public boolean contains(Point p)
	{
		Point temp = new Point(this.endLocation);
		double step = calculateStepsToWalk();
		double minDistance = -1;
				
		if (this.endLocation.x > this.location.x) {
			for (double i = this.endLocation.x; i >= this.location.x; i-= step) {
				temp = this.walkOnEdge(temp, this.location, i);
				
				if ((temp.distance(p) < minDistance)
					|| (minDistance < 0)
					&& (!this.target.contains(temp))
				) {
					minDistance = temp.distance(p);
				}
								
				if (this.source.contains(temp)) {
					break;
				}
			}
		} else {
			for (double i = this.endLocation.x; i <= this.location.x; i+= step) {
				temp = this.walkOnEdge(temp, this.location, i);
				
				if ((temp.distance(p) < minDistance)
					|| (minDistance < 0)
					&& (!this.target.contains(temp))
				) {
					minDistance = temp.distance(p);
				}
				
				if (this.source.contains(temp)) {
					break;
				}
			}
		}
		
		if (minDistance < Edge.SELECT_EPSILON) {
			return true;
		}
		
		return false;
	}
	
	public boolean isValid()
	{
		return this.isValidEdge;
	}
	
}
