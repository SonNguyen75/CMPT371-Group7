package ca.cmpt276.project.classes.classes;

import ca.cmpt276.project.classes.helper.Position;

public class Barrier extends GameItems {
    boolean northSideOpen = true;
    boolean southSideOpen = true;

    /**
     * A method to get the state of the North side,
     * @return True if the side is open; otherwise, False
     */

    public boolean isNorthSideOpen() {
        return northSideOpen;
    }

    /**
     * A method to set the state of the North side,
     * @param northSideOpen the state of the North side to set (True: open, False: close)
     */

    public void setNorthSideOpen(boolean northSideOpen) {
        this.northSideOpen = northSideOpen;
    }

    /**
     * A method to get the state of the South side,
     * @return True if the side is open; otherwise, False
     */

    public boolean isSouthSideOpen() {
        return southSideOpen;
    }

    /**
     * A method to set the state of the South side,
     * @param southSideOpen the state of the South side to set (True: open, False: close)
     */

    public void setSouthSideOpen(boolean southSideOpen) {
        this.southSideOpen = southSideOpen;
    }

    /**
     * A method to get the state of the East side,
     * @return True if the side is open; otherwise, False
     */

    public boolean isEastSideOpen() {
        return eastSideOpen;
    }

    /**
     * A method to set the state of the East side,
     * @param eastSideOpen the state of the East side to set (True: open, False: close)
     */

    public void setEastSideOpen(boolean eastSideOpen) {
        this.eastSideOpen = eastSideOpen;
    }

    /**
     * A method to get the state of the West side,
     * @return True if the side is open; otherwise, False
     */

    public boolean isWestSideOpen() {
        return westSideOpen;
    }

    /**
     * A method to set the state of the West side,
     * @param westSideOpen the state of the West side to set (True: open, False: close)
     */

    public void setWestSideOpen(boolean westSideOpen) {
        this.westSideOpen = westSideOpen;
    }

    boolean eastSideOpen = true;
    boolean westSideOpen = true;

    public Barrier(int x, int y, String closeSide) {
        super(new Position(x, y));
        switch (closeSide){
            case "north" ->{
                northSideOpen = false;
            }
            case "south" -> {
                southSideOpen = false;
            }
            case "east" -> {
                eastSideOpen = false;
            }
            case "west" -> {
                westSideOpen = false;
            }

        }

    }

    /**
     * A method to close a selected side of the barrier,
     * @param closeSide the side to be closed 
     */

    public void setSide(String closeSide){
         northSideOpen = true;
         southSideOpen = true;
         eastSideOpen = true;
         westSideOpen = true;

        switch (closeSide){
            case "north" ->{
                northSideOpen = false;
            }
            case "south" -> {
                southSideOpen = false;
            }
            case "east" -> {
                eastSideOpen = false;
            }
            case "west" -> {
                westSideOpen = false;
            }

        }
    }


}