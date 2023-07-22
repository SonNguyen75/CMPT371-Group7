package ca.cmpt276.project.classes.classes;

import ca.cmpt276.project.classes.helper.Position;

public class GameItems {
    private final Position position;

    public GameItems(Position position) {
        this.position = position;
    }

    /**
     * A method to get the position of the item,
     * @return the position of the item
     */

    public Position getPosition(){
        return position;
    }

    /**
     * A method to set the position of the item,
     * @param x the coordinate x of the position to be set
     * @param y the coordinate y of the position to be set
     */

    public void setPosition(int x, int y){
        position.setX(x);
        position.setY(y);
    }
}
