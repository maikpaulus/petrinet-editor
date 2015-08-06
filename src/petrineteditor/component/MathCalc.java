package petrineteditor.component;

import java.awt.Point;

/**
 * Hilfs-Klasse für mathematische Berechnungen
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class MathCalc
{
	/**
	 * Ermittelt die Steigung einer Geraden zwischen zwei übergebenen Punkten
	 * @param p1 Startpunkt
	 * @param p2 Endpunkt
	 * @return Die Steigung der Geraden
	 */
	public static double gradient(Point p1, Point p2)
	{
		double numerator = p2.y - p1.y;
		double denominator = p2.x - p1.x;
		
		if (denominator != 0) {
			return numerator / denominator;
		}
		
		return 0.0;
	}	
}
