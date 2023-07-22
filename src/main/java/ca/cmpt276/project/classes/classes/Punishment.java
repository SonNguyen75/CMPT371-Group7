package ca.cmpt276.project.classes.classes;

import ca.cmpt276.project.classes.helper.Position;

public class Punishment extends GameItems {
    private int cost;

    public Punishment(int x, int y) {
        super(new Position(x, y));
        cost = 25;
    }

     /**
     * A method to set the cost of the punishment,
     * @param newCost the new cost to be set 
     */

    public void setCost(int newCost) {
        cost = newCost;
    }

    /**
     * A method to set the cost of the punishment,
     * @return the cost of the punishment
     */

    public int getCost() {
        return cost;
    }

}
