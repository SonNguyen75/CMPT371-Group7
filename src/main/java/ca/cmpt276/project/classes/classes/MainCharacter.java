package ca.cmpt276.project.classes.classes;

import ca.cmpt276.project.classes.helper.Position;


public class MainCharacter extends GameItems{
    private int c;
    private Position position;
    int score;

    public MainCharacter(int x, int y) {
        super(new Position(x, y));
        score = 50;
    }

     /**
     * A method to set the score of the character,
     * @param newScore the new score to be set 
     */

    public void setScore(int newScore) {
        score = newScore;
    }

     /**
     * A method to get the score of the character,
     * @return the score of the character
     */

    public int getScore() {
        return score;
    }

}
