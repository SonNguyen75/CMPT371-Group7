package ca.cmpt276.project.controller;

import ca.cmpt276.project.classes.classes.*;
import ca.cmpt276.project.classes.helper.Position;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


/**
 * This class Store the position for the game, implement the methods for gaming logic function. including:
 * initialize the gaming when first start.
 * checking the Physical collision.
 * updating the character and enemy position during the movement
 * getting bonus and rewards when character get.
 *
 */
public class GameSystem {
    private ArrayList<ArrayList<GameItems>> map;
    private ArrayList<ArrayList<GameItems>> character;
    private int bounsCount = 0;
    private ArrayList<BonusReward> bonusRewardList;
    private MainCharacter mainCharacter;
    private Enemy enemy1;
    private Enemy enemy2;
    private int enemy1CurrentPos;
    private int enemy2CurrentPos;
    private ArrayList<Position> enemy1Movement;
    private ArrayList<Position> enemy2Movement;
    private int gamingProcessing = 5;
    private final int southBorder = 13;
    private final int northBorder = 1;
    private final int westBorder = 0;
    private final int eastBorder = 14;

    private Reward reward1;
    private Reward reward2;
    private Reward reward3;
    private Reward reward4;
    private Reward reward5;

    private Punishment trap1;
    private Punishment trap2;

    public Reward getReward1() {
        return reward1;
    }

    public void setReward1(Reward reward1) {
        this.reward1 = reward1;
    }

    public Reward getReward2() {
        return reward2;
    }

    public void setReward2(Reward reward2) {
        this.reward2 = reward2;
    }

    public Reward getReward3() {
        return reward3;
    }

    public void setReward3(Reward reward3) {
        this.reward3 = reward3;
    }

    public Reward getReward4() {
        return reward4;
    }

    public void setReward4(Reward reward4) {
        this.reward4 = reward4;
    }

    public Reward getReward5() {
        return reward5;
    }

    public void setReward5(Reward reward5) {
        this.reward5 = reward5;
    }

    public Punishment getTrap1() {
        return trap1;
    }

    public void setTrap1(Punishment trap1) {
        this.trap1 = trap1;
    }

    public Punishment getTrap2() {
        return trap2;
    }

    public void setTrap2(Punishment trap2) {
        this.trap2 = trap2;
    }


    public MainCharacter getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public int getGamingProcessing() {
        return gamingProcessing;
    }

    public void setGamingProcessing(int gamingProcessing) {
        this.gamingProcessing = gamingProcessing;
    }

    public Enemy getEnemy1() {
        return enemy1;
    }

    public void setEnemy1(Enemy enemy1) {
        this.enemy1 = enemy1;
    }

    public Enemy getEnemy2() {
        return enemy2;
    }

    public void setEnemy2(Enemy enemy2) {
        this.enemy2 = enemy2;
    }


    /**
     * initialize the gaming when first start
     * modularize the map and store the barriers, traps, rewards, bonus, enemy and main character.
     * create the 2d arraylist to store the map inorder to compare for the physical collision.
     * Link the map in the image and the map of the Arraylist through the mapping method.
     * All the map decorations are stored in map Arraylist and enemy and character are stored in character Arraylist
     * using different Arraylist but share same index address can be more efficient for tracking the movement of enemy and main character.
     */
    public void init(){

        // building map and character Arraylist
        map = new ArrayList<>(15);
        for (int i = 0; i < 15; i++) {
            map.add(new ArrayList<>(15));
        }

        character = new ArrayList<>(15);
        for (int i = 0; i < 15; i++) {
            character.add(new ArrayList<>(15));
        }

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                GameItems cell = new GameItems(new Position(i, j));
                map.get(i).add(cell);
                character.get(i).add(cell);
            }
        }

        // set init position for character
        mainCharacter = new MainCharacter(0,9);
        character.get(0).set(9,mainCharacter);

        //set zigzagoon
        enemy1 = new Enemy(5,5);
        enemy2 = new Enemy(2,12);
        enemy1Movement = new ArrayList<>();
        enemy1Movement.add(new Position(5, 5));
        enemy1Movement.add(new Position(5, 4));
        enemy1Movement.add(new Position(5, 5));
        enemy1Movement.add(new Position(5, 6));
        enemy2Movement = new ArrayList<>();
        enemy2Movement.add(new Position(2, 12));
        enemy2Movement.add(new Position(2, 11));
        enemy2Movement.add(new Position(2, 12));
        enemy2Movement.add(new Position(2, 13));
        enemy1CurrentPos = 0;
        enemy2CurrentPos = 0;
        character.get(enemy1.getPosition().getX()).set(enemy1.getPosition().getY(), enemy1);
        character.get(enemy2.getPosition().getX()).set(enemy2.getPosition().getY(), enemy2);


        //set reward
        reward1 = new Reward(3, 3);
        reward2 = new Reward(14, 1);
        reward3 = new Reward(14, 7);
        reward4 = new Reward(4, 12);
        reward5 = new Reward(10, 12);
        map.get(reward1.getPosition().getX()).set(reward1.getPosition().getY(), reward1);
        map.get(reward2.getPosition().getX()).set(reward2.getPosition().getY(), reward2);
        map.get(reward3.getPosition().getX()).set(reward3.getPosition().getY(), reward3);
        map.get(reward4.getPosition().getX()).set(reward4.getPosition().getY(), reward4);
        map.get(reward5.getPosition().getX()).set(reward5.getPosition().getY(), reward5);

        //set trap
        trap1 = new Punishment(8, 8);
        trap2 = new Punishment(1, 12);
        map.get(trap1.getPosition().getX()).set(trap1.getPosition().getY(), trap1);
        map.get(trap2.getPosition().getX()).set(trap2.getPosition().getY(), trap2);

        //set barrier
        for (int i = 0; i < 4; i++) {
            Barrier barrier = new Barrier(i, 4, "north");
            map.get(i).set(4, barrier);
        }//1

        for (int i = 2; i < 4; i++) {
            Barrier barrier = new Barrier(4, i, "west");
            map.get(4).set(i, barrier);
        }//2

        for (int i = 4; i < 9; i++) {
            Barrier barrier = new Barrier(i, 1, "south");
            map.get(i).set(1, barrier);
        }//3

        for (int i = 2; i < 4; i++) {
            Barrier barrier = new Barrier(9, i, "west");
            map.get(9).set(i, barrier);
        }//4

        for (int i = 2; i < 4; i++) {
            Barrier barrier = new Barrier(11, i, "east");
            map.get(11).set(i, barrier);
        }//5

        for (int i = 12; i < 15; i++) {
            Barrier barrier = new Barrier(i,  2, "north");
            map.get(i).set(2, barrier);
        }//6

        for (int i = 1; i < 3; i++) {
            Barrier barrier = new Barrier(i, 10, "south");
            map.get(i).set(10, barrier);
        }//7

        for (int i = 9; i < 11; i++) {
            Barrier barrier = new Barrier(3, i, "west");
            map.get(3).set(i, barrier);
        }//8

        for (int i = 3; i < 6; i++) {
            Barrier barrier = new Barrier(i, 8, "south");
            map.get(i).set(8, barrier);
        }//9

        for (int i = 7; i < 9; i++) {
            Barrier barrier = new Barrier(6, i, "west");
            map.get(6).set(i, barrier);
        }//10

        Barrier single = new Barrier(6, 6, "south");
        map.get(6).set(6, single);//11

        for (int i = 10; i < 13; i++) {
            Barrier barrier = new Barrier(i, 9, "north");
            map.get(i).set(9, barrier);
        }//12

        single = new Barrier(12, 8, "east");
        map.get(12).set(8, single);//13

        for (int i = 13; i < 15; i++) {
            Barrier barrier = new Barrier(i, 8,  "north");
            map.get(i).set(8, barrier);
        }//14

        for (int i = 11; i < 15; i++) {
            Barrier barrier = new Barrier(i, 6, "south");
            map.get(i).set(6, barrier);
        }//15

        single = new Barrier(10, 7, "east");
        map.get(10).set(7, single);//16

        single = new Barrier(10 , 8, "north");
        map.get(10).set(8, single);//17

        for (int i = 3; i < 5; i++) {
            Barrier barrier = new Barrier(i, 11, "south");
            map.get(i).set(11, barrier);
        }//18

        for (int i = 12; i < 14; i++) {
            Barrier barrier = new Barrier(5, i, "west");
            map.get(5).set(i, barrier);
        }//19

        single = new Barrier(9, 12, "east");
        map.get(9).set(12, single);//20

        for (int i = 10; i < 15; i++) {
            Barrier barrier = new Barrier(i, 11, "south");
            map.get(i).set(11, barrier);
        }//21

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                map.get(i).get(j).setPosition(map.get(i).get(j).getPosition().getX() * 32, map.get(i).get(j).getPosition().getY() * 32);
            }
        }

        for (int i = 0; i < character.size(); i++) {
            for (int j = 0; j < character.get(i).size(); j++) {
                character.get(i).get(j).setPosition(character.get(i).get(j).getPosition().getX() * 32, character.get(i).get(j).getPosition().getY() * 32);
            }
        }

        BonusRewardInit();
    }

    /**
     *Doing physical collision for main character by checking the type of target cell on map arraylist (barrier, reward, trap, bonus,) and checking the type of target cell on character arraylist (enemy)
     * @param move get direction from UI layer
     * @return String type, give the instruction to UI layer.
     * Instruction will be four type:
     * "Close" means that detect the barrier and let UI layer stop moving
     * "Death" means that character hit the enemy, game over
     * "Trap" means that character hit the trap, keep moving but will lose score
     * "Reward" means that character get the rewards or bonus, function will let UI layer keep moving and remove the rewards or bonus, if it's bonus, system will add a new bonus on the other place. And add the score
     * every time the character move, or UI layer call this function, will let system update the information pf the bonus(reducing remain time).
     */
    public String CheckingItems(String move){
        Enemy enemy = new Enemy(0,0);
        Punishment trap = new Punishment(0,0);
        Barrier wall = new Barrier(0,0, "");
        Reward reward = new Reward(0,0);
        BonusReward bonus = new BonusReward();
        if (move.equals("up")){
            int x,y;
            x = mainCharacter.getPosition().getX() / 32;
            y = mainCharacter.getPosition().getY() / 32;
            if (y - 1 >= 1) {
                if (map.get(x).get(y).getClass().equals(wall.getClass())) {
                    wall = (Barrier) map.get(x).get(y);
                    if (!wall.isNorthSideOpen()) {
                        return "Close";
                    }
                }
                if (map.get(x).get(y - 1).getClass().equals(wall.getClass())) {
                    wall = (Barrier) map.get(x).get(y - 1);
                    if (!wall.isSouthSideOpen()) {
                        return "Close";
                    }
                }
                if (character.get(x).get(y - 1).getClass().equals(enemy.getClass())) {
                    return "Death";
                }

                if (map.get(x).get(y - 1).getClass().equals(trap.getClass())) {
                    ScoreManage("Trap");
                }
                if (map.get(x).get(y - 1).getClass().equals(reward.getClass())) {
                    ScoreManage("Reward");
                    gamingProcessing --;
                    GameItems cell = new GameItems(new Position(x * 32, (y - 1) * 32));
                    mainCharacter.setPosition(x * 32, (y - 1) * 32);
                    map.get(x).set((y - 1), cell);
                    return "Reward";
                }
                if (map.get(x).get(y - 1).getClass().equals(bonus.getClass())) {
                    int index = FindIndexOfBonusByPosition(x, y - 1);
                    mainCharacter.setScore(mainCharacter.getScore() + bonusRewardList.get(index).getCurrentTicks());
                    GameItems cell = new GameItems(new Position(x * 32, (y - 1) * 32));
                    mainCharacter.setPosition(x * 32, (y - 1) * 32);
                    map.get(x).set((y - 1), cell);
                    bonusRewardList.set(index, BonusSetting());
                    return "Reward";
                }
            }else {
                return "Close";
            }
            mainCharacter.setPosition(x * 32, (y - 1) * 32);
            return "Move";
        }else if (move.equals("down")){
            int x,y;
            x = mainCharacter.getPosition().getX() / 32;
            y = mainCharacter.getPosition().getY() / 32;
            if (y + 1 <= 13) {
                if (map.get(x).get(y).getClass().equals(wall.getClass())) {
                    wall = (Barrier) map.get(x).get(y);
                    if (!wall.isSouthSideOpen()) {
                        return "Close";
                    }
                }
                if (map.get(x).get(y + 1).getClass().equals(wall.getClass())) {
                    wall = (Barrier) map.get(x).get(y + 1);
                    if (!wall.isNorthSideOpen()) {
                        return "Close";
                    }
                }
                if (character.get(x).get(y + 1).getClass().equals(enemy.getClass())) {
                    return "Death";
                }
                if (map.get(x).get(y + 1).getClass().equals(trap.getClass())) {
                    ScoreManage("Trap");
                }
                if (map.get(x).get(y + 1).getClass().equals(reward.getClass())) {
                    ScoreManage("Reward");
                    gamingProcessing --;
                    GameItems cell = new GameItems(new Position(x * 32, (y + 1) * 32));
                    mainCharacter.setPosition(x * 32, (y + 1) * 32);
                    map.get(x).set((y + 1), cell);
                    return "Reward";
                }
                if (map.get(x).get(y + 1).getClass().equals(bonus.getClass())) {
                    int index = FindIndexOfBonusByPosition(x, y + 1);
                    mainCharacter.setScore(mainCharacter.getScore() + bonusRewardList.get(index).getCurrentTicks());
                    GameItems cell = new GameItems(new Position(x * 32, (y + 1) * 32));
                    mainCharacter.setPosition(x * 32, (y + 1) * 32);
                    map.get(x).set((y + 1), cell);
                    bonusRewardList.set(index, BonusSetting());
                    return "Reward";
                }
            }else {
                return "Close";
            }
            mainCharacter.setPosition(x * 32, (y + 1) * 32);
            return "Move";
        }else if (move.equals("left")){
            int x,y;
            x = mainCharacter.getPosition().getX() / 32;
            y = mainCharacter.getPosition().getY() / 32;
            if (x - 1 >= 0) {
                if (map.get(x).get(y).getClass().equals(wall.getClass())) {
                    wall = (Barrier) map.get(x).get(y);
                    if (!wall.isWestSideOpen()) {
                        return "Close";
                    }
                }
                if (map.get(x - 1).get(y).getClass().equals(wall.getClass())) {
                    wall = (Barrier) map.get(x - 1).get(y);
                    if (!wall.isEastSideOpen()) {
                        return "Close";
                    }
                }
                if (character.get(x - 1).get(y).getClass().equals(enemy.getClass())) {
                    return "Death";
                }
                if (map.get(x - 1).get(y).getClass().equals(trap.getClass())) {
                    ScoreManage("Trap");
                }
                if (map.get(x - 1).get(y).getClass().equals(reward.getClass())) {
                    ScoreManage("Reward");
                    gamingProcessing --;
                    GameItems cell = new GameItems(new Position((x - 1) * 32, (y) * 32));
                    mainCharacter.setPosition((x - 1) * 32, y * 32);
                    map.get(x - 1).set(y, cell);
                    return "Reward";
                }
                if (map.get(x - 1).get(y).getClass().equals(bonus.getClass())) {
                    int index = FindIndexOfBonusByPosition(x - 1, y);
                    mainCharacter.setScore(mainCharacter.getScore() + bonusRewardList.get(index).getCurrentTicks());
                    GameItems cell = new GameItems(new Position((x - 1) * 32, y * 32));
                    mainCharacter.setPosition((x - 1) * 32, y * 32);
                    map.get(x - 1).set(y, cell);
                    bonusRewardList.set(index, BonusSetting());
                    return "Reward";
                }
            }else {
                return "Close";
            }
            mainCharacter.setPosition((x - 1) * 32, y * 32);
            return "Move";
        }else if (move.equals("right")){
            int x,y;
            x = mainCharacter.getPosition().getX() / 32;
            y = mainCharacter.getPosition().getY() / 32;
            if (x + 1 <= 14) {
                if (map.get(x).get(y).getClass().equals(wall.getClass())) {
                    wall = (Barrier) map.get(x).get(y);
                    if (!wall.isEastSideOpen()) {
                        return "Close";
                    }
                }
                if (map.get(x + 1).get(y).getClass().equals(wall.getClass())) {
                    wall = (Barrier) map.get(x + 1).get(y);
                    if (!wall.isWestSideOpen()) {
                        return "Close";
                    }
                }
                if (character.get(x + 1).get(y).getClass().equals(enemy.getClass())) {
                    System.out.println(character.get(x).get(y - 1).getClass());
                    System.out.println(enemy.getClass());
                    return "Death";
                }
                if (map.get(x + 1).get(y).getClass().equals(trap.getClass())) {
                    ScoreManage("Trap");
                }
                if (map.get(x + 1).get(y).getClass().equals(reward.getClass())) {
                    ScoreManage("Reward");
                    gamingProcessing --;
                    GameItems cell = new GameItems(new Position((x + 1) * 32, y * 32));
                    mainCharacter.setPosition((x + 1) * 32, y * 32);
                    map.get(x + 1).set(y, cell);
                    return "Reward";
                }
                if (map.get(x + 1).get(y).getClass().equals(bonus.getClass())) {
                    int index = FindIndexOfBonusByPosition(x + 1, y);
                    mainCharacter.setScore(mainCharacter.getScore() + bonusRewardList.get(index).getCurrentTicks());
                    GameItems cell = new GameItems(new Position((x + 1) * 32, y * 32));
                    mainCharacter.setPosition((x + 1) * 32, y * 32);
                    map.get(x + 1).set(y, cell);
                    bonusRewardList.set(index, BonusSetting());
                    return "Reward";
                }
            }else {
                return "Close";
            }
            mainCharacter.setPosition((x + 1) * 32, y * 32);
            return "Move";
        }
        return "Move";
    }

    /**
     * manage the score by getting the command from the CheckingItem function.
     * @param type String type, getting the command, there will be two commands:
     *             1. Trap, that means character fall into the trap, so Score will be minus by 50
     *             2. Reward, that means character get reward. so Score will be added 50;
     */
    public void ScoreManage(String type){
        if (type.equals("Trap")){
            mainCharacter.setScore(mainCharacter.getScore() - 50);
        }
        if (type.equals("Reward")){
            mainCharacter.setScore(mainCharacter.getScore() + 50);
        }
    }

    /**
     * Update the position of the enemy and return the enemy list to UI layer to display to the screen
     * @return Arraylist, contains all the enemy object which include the position.
     * this function will let enemy move, and update the position on the character map. and change the data of the position attribute.
     */
    public ArrayList<Enemy> EnemyUpdate(){
        ArrayList<Enemy> enemyList = new ArrayList<>();
        if (enemy1CurrentPos == 3) {
            enemy1CurrentPos = 0;
            enemy1.setPosition(enemy1Movement.get(enemy1CurrentPos).getX(), enemy1Movement.get(enemy1CurrentPos).getY());
        }else {
            enemy1CurrentPos ++;
            enemy1.setPosition(enemy1Movement.get(enemy1CurrentPos).getX(), enemy1Movement.get(enemy1CurrentPos).getY());
        }
        if (enemy2CurrentPos == 3) {
            enemy2CurrentPos = 0;
            enemy2.setPosition(enemy2Movement.get(enemy2CurrentPos).getX(), enemy2Movement.get(enemy2CurrentPos).getY());
        }else {
            enemy2CurrentPos ++;
            enemy2.setPosition(enemy2Movement.get(enemy2CurrentPos).getX(), enemy2Movement.get(enemy2CurrentPos).getY());
        }
        enemyList.add(enemy1);
        enemyList.add(enemy2);
        for (int i = 0; i < enemyList.size(); i++) {
        int x = enemyList.get(i).getPosition().getX() * 32;
        int y = enemyList.get(i).getPosition().getY() * 32;
            enemyList.get(i).setPosition(x, y);
        }
        return enemyList;
    }

    /**
     * check whether is available in map with x and y, it cannot be covered by barrier, trap, reward, enemy and character
     * @param x the x value of the items
     * @param y the y value of the items
     * @return boolean whether it's available on that cell
     */
    private boolean checkingItems(int x, int y){
        GameItems test = new GameItems(new Position(0,0));
        if (map.get(x).get(y).getClass().equals(test.getClass()) && !(character.get(x).get(y).getClass().equals(mainCharacter.getClass()) || character.get(x).get(y).getClass().equals(enemy1.getClass()))){
            return true;
        }
        return false;
    }

    /**
     * setting a new bonus randomly
     * @return a bonus which could place in anywhere on map
     */
    private BonusReward BonusSetting(){
        int times = ThreadLocalRandom.current().nextInt(3, 10);
        int point = ThreadLocalRandom.current().nextInt(51, 100 + 1);
        int x = ThreadLocalRandom.current().nextInt(0, 12 + 1);
        int y = ThreadLocalRandom.current().nextInt(1, 12 + 1);
        while(!checkingItems(x, y)){
            x = ThreadLocalRandom.current().nextInt(0, 12 + 1);
            y = ThreadLocalRandom.current().nextInt(1, 12 + 1);
        }
        BonusReward result = new BonusReward(times, point, x * 32, y * 32);
        map.get(x).set(y, result);
        return result;
    }

    /**
     * initialize for random bonus. create on the map and store on the list for UI layer to display on the screen
     */
    private void BonusRewardInit(){
        bonusRewardList = new ArrayList<>();
        int numbersOfBonus = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        for (int i = 0; i < numbersOfBonus; i++) {
            bonusRewardList.add(BonusSetting());
        }
    }

    /**
     * update the in formation of bonus. checking  remaining times, rolling a new bonus if there is a bonus whose time is turning to 0
     * replace on the map and update on the list.
     */
    public void BonusUpdate(){
        for (int i = 0; i < bonusRewardList.size(); i++) {
            bonusRewardList.get(i).setTimeExist(bonusRewardList.get(i).getTimeExist() - 1);
            if (bonusRewardList.get(i).getTimeExist() == 0){
                bonusRewardList.set(i, BonusSetting());
            }
        }
    }

    /**
     * find the index of the bonus by x and y, help to update, delete or add bonus on list and map.
     * @param x the x value of the position of the bonus
     * @param y the y value of the position of the bonus
     * @return the index of the bonus in the list
     */
    private int FindIndexOfBonusByPosition(int x, int y){
        for (int i = 0; i < bonusRewardList.size(); i++) {
            if (bonusRewardList.get(i).getPosition().getX() == x * 32 && bonusRewardList.get(i).getPosition().getY() == y * 32){
                return i;
            }
        }
        return 0;
    }

    /**
     * let UI layer to get the position of each bonus in order to display the bonus on the screen
     * @return the list of the bonus with position where the bonus should display on the map
     */
    public ArrayList<BonusReward> GetUpdatedBonus(){
        return bonusRewardList;
    }
}
