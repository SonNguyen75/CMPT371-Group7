package ca.cmpt276.project.classes.classes;

import ca.cmpt276.project.classes.helper.Position;


public class BonusReward extends GameItems {
    private int timeExist;
    private int currentTicks;

    public int getTimeExist() {
        return timeExist;
    }

     /**
     * A method to set the time that this bonus reward will exist,
     * @param timeExist the time that the barrier will exist 
     */

    public void setTimeExist(int timeExist) {
        this.timeExist = timeExist;
    }

     /**
     * A method to set how many ticks has counted,
     * @param northSideOpen the amount of ticks that has been counted since this bonus reward spawned 
     */

    public void setCurrentTicks(int currentTicks) {
        this.currentTicks = currentTicks;
    }

     /**
     * A method to set the existing state of the bonus reward,
     * @param northSideOpen the existing state of the bonus reward (True: existing, False: not existing)
     */

    public void setExisting(boolean existing) {
        isExisting = existing;
    }

    private boolean isExisting;

    public BonusReward() {
        super(new Position(0, 0));
        timeExist = 0;
        currentTicks = 0;
        isExisting = false;
    }

    public BonusReward(int time, int point, int x, int y) {
        super(new Position(x, y));
        timeExist = time;
        currentTicks = point;
        isExisting = false;
    }

     /**
     * A method to spawn the bonus reward,
     */

    public void spawn() {
        isExisting = true;
    }

     /**
     * A method to spawn the bonus reward with a specific existinng time,
     * @param timeExist the amount of ticks the bonus reward can exist 
     */

    public void spawn(int timeExist) {
        this.timeExist = timeExist;
        isExisting = true;
    }

     /**
     * A method to update the number of ticks counted since the bonus reward spawned,
     */

    public void updateCurrentTicks() {
        currentTicks++;
    }

     /**
     * A method to despawn the bonus reward,
     */

    public void despawn() {
        timeExist = 0;
        isExisting = false;
    }

     /**
     * A method to get the number of ticks counted since the bonus reward spawned,
     */

    public int getCurrentTicks() {
        return currentTicks;
    }

     /**
     * A method to get the existing state of the bonus reward,
     * @return True if the bonus reward is existing; otherwise, False
     */

    public boolean isExisting() {
        return isExisting;
    }

    /**
     * A method to update the existing state of the bonus reward when it is collected,
     */

    public void isCollected() {
        isExisting = false;
    }

}
