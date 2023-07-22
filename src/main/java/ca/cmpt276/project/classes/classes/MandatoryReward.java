package ca.cmpt276.project.classes.classes;

import ca.cmpt276.project.classes.helper.Position;

public class MandatoryReward extends  GameItems{
    private int value;

    MandatoryReward(int x, int y, int value) {
        super(new Position (x,y));
        this.value = value;
    }

     /**
     * A method to set the value of the reward,
     * @param value the new value to be set 
     */

    public void setValue(int value) {
        this.value = value;
    }

     /**
     * A method to get the value of the reward,
     * @return the value of the reward
     */

    public int getValue() {
        return value;
    }


}
