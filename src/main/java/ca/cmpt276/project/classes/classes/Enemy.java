package ca.cmpt276.project.classes.classes;

import ca.cmpt276.project.classes.helper.Position;


public class Enemy extends GameItems{

    Enemy() {
        super(new Position(0,0));
    }

    public Enemy(int x, int y) {
        super( new Position(x, y));
    }

}
