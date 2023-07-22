package ca.cmpt276.project.classes.classes;

import ca.cmpt276.project.classes.helper.Position;

public class Reward extends GameItems {
    private int prize;

    public Reward(int x, int y) {
        super(new Position(x, y));
        prize = 50;
    }

    /**
     * A method to set the prize of the reward,
     * @param newPrize the new prize to be set 
     */

    public void setPrize(int newPrize) {
        prize = newPrize;
    }

    /**
     * A method to get the value of the reward,
     * @return the prize of the reward
     */

    public int getPrize() {
        return prize;
    }

}
