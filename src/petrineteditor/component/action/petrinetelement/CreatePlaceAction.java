package petrineteditor.component.action.petrinetelement;

import petrineteditor.model.Node;
import petrineteditor.model.Place;

/**
 * Action zum Erstellen einer Stelle
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class CreatePlaceAction extends CreateNodeAction
{
	private static final long serialVersionUID = 1L;

	/**
	 * Erzeugt eine Stelle an gewünschter Position
	 * @param x x-Koordinate der Stelle
	 * @param y y-Koordinate der Stelle
	 */
	public CreatePlaceAction(int x, int y)
	{
		this.posX = x;
		this.posY = y;
	}
	
	@Override
	protected Node getNode() {
		return new Place(this.posX, this.posY);
	}
	
	
	
}
