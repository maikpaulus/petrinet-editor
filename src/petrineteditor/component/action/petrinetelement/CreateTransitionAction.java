package petrineteditor.component.action.petrinetelement;

import petrineteditor.model.Node;
import petrineteditor.model.Transition;

/**
 * Action zum Erstellen einer neuen Transition
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class CreateTransitionAction extends CreateNodeAction
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Erstellt eine neue Transition an gewünschter Position
	 * @param x x-Koordinate der Transition
	 * @param y y-Koordinate der Transition
	 */
	public CreateTransitionAction(int x, int y) 
	{
		this.posX = x;
		this.posY = y;
	}
	
	@Override
	protected Node getNode() {
		return new Transition(this.posX, this.posY);
	}

}
