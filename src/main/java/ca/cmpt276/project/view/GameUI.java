package ca.cmpt276.project.view;

import ca.cmpt276.project.classes.classes.BonusReward;
import ca.cmpt276.project.classes.classes.Enemy;
import ca.cmpt276.project.classes.classes.Punishment;
import ca.cmpt276.project.classes.classes.Reward;
import ca.cmpt276.project.controller.GameSystem;
import ca.cmpt276.project.classes.helper.Position;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.ArrayList;



public class GameUI extends JFrame implements ActionListener {
    JPanel mainPanel = new JPanel();
    JPanel gamePanel = new JPanel();
    JLabel playerSprite = new JLabel();
    GameSystem system = new GameSystem();
    //Field to store audio file
    private Clip clip;

    private JButton startGameButton = new JButton("Start Game");
    private JButton exitGameButton = new JButton("Exit Game");

    ArrayList<Enemy> enemyList = new ArrayList<>();
    ArrayList<Reward> rewardList = new ArrayList<>();
    ArrayList<Punishment> trapList = new ArrayList<>();
    ArrayList<BonusReward> bonusRewardList = new ArrayList<>();

    //A position for the exit point
    private final Position exitPoint = new Position(14*32,3*32);

    /**
     * @param tittle A string contains the title of the game
     */

    public GameUI(String tittle) {
        super(tittle);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setContentPane(mainPanel);
        ImageIcon icon = new ImageIcon("gameIcon.png");
        this.setIconImage(icon.getImage());
        mainPanel.add(startGameButton);
        mainPanel.add(exitGameButton);

        //A helper method to print the instruction to the player
        showInstruction();

        startGameButton.addActionListener(this);
        exitGameButton.addActionListener(this);
        this.setVisible(true);
    }

    /**
     * @param e Get action when player pressed either the "Start Game" or "Exit Game" button
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        String option = e.getActionCommand();
        if (option.equalsIgnoreCase("start game")) {
            system.init();
            //Remove the main menu screen
            this.remove(mainPanel);

            //Add new scene as the main panel
            this.setContentPane(gamePanel);
            gamePanel.setLayout(null);
            gamePanel.setFocusable(true);
            gamePanel.requestFocusInWindow();

            //Display components, and UI of the main game
            startGame();

            //Repaint everything
            this.revalidate();
            this.repaint();
        }

        if (option.equalsIgnoreCase("exit game")) {
            this.dispose();
        }

        gamePanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            /**
             *
             * @param e KeyEvent action whenever player hit a key
             */

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                String result;
                if (key == KeyEvent.VK_W) {
                    result = system.CheckingItems("up");
                    system.BonusUpdate();
                    if (result.equals("Move")){
                        moveCharacterUp();
                    }
                    else if (result.equals("Reward")){
                        moveCharacterUp();
                    }
                    else if (result.equals("Death")){
                        //Game over
                        deathScreen();
                    }
                    system.BonusUpdate();
                }
                if (key == KeyEvent.VK_S) {
                    result = system.CheckingItems("down");
                    system.BonusUpdate();
                    if (result.equals("Move") || result.equals("Reward")){
                        moveCharacterDown();
                    }
                    else if (result.equals("Death")){
                        deathScreen();
                    }
                }
                if (key == KeyEvent.VK_D) {
                    result = system.CheckingItems("right");
                    system.BonusUpdate();
                    if (result.equals("Move") || result.equals("Reward")){
                        moveCharacterRight();
                    }
                    else if (result.equals("Death")){
                        deathScreen();
                    }

                }
                if (key == KeyEvent.VK_A) {
                    result = system.CheckingItems("left");
                    system.BonusUpdate();
                    if (result.equals("Move") || result.equals("Reward")){
                        moveCharacterLeft();
                    }
                    else if (result.equals("Death")){
                        deathScreen();
                    }
                }

                if (key == KeyEvent.VK_UP){
                    throwPokeball("up");
                }

                if (key == KeyEvent.VK_DOWN){
                    throwPokeball("down");
                }

                if (key == KeyEvent.VK_LEFT){
                    throwPokeball("left");
                }

                if (key == KeyEvent.VK_RIGHT){
                    throwPokeball("right");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    /**
     * Helper method to print the instruction image to the home screen of the game
     */

    private void showInstruction () {
        BufferedImage instruction = null;
        try {
            instruction = ImageIO.read(new File("instructionMenu.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JLabel instructionMenu = new JLabel(new ImageIcon(instruction));
        instructionMenu.setBounds(0,0,500,500);
        mainPanel.add(instructionMenu);
        this.add(instructionMenu);
    }

    /**
     * Helper method to populate components when player hit "Start Game" button
     */

    private void startGame() {
        //Play background music
        try {
            String soundName = "bgMusic.wav";
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        //Read player sprite data from a png to a JLabel and add it to the main scene
        playerSprite = displayPlayer(system.getMainCharacter().getPosition().getX(), system.getMainCharacter().getPosition().getY());
        add(playerSprite);
        gamePanel.add(playerSprite);
        //Loading game data
        loadingEnemyData();
        loadingRewardsData();
        loadingTrapData();

        //Display game data on map
        displayEnemyOnMap();
        displayRewardsOnMap();
        displayTrapOnMap();
        displayRandomRewardsOnMap();

        //Read the map data from a png to a JLabel and add it to the main scene
        JLabel mapScene = displayMap();
        add(mapScene);

        displayHUD();
        gamePanel.add(mapScene);
        gamePanel.setVisible(true);
    }

    private void loadingEnemyData() {
        enemyList.add(system.getEnemy1());
        enemyList.add(system.getEnemy2());

    }

    private void loadingRewardsData() {
        rewardList.add(system.getReward1());
        rewardList.add(system.getReward2());
        rewardList.add(system.getReward3());
        rewardList.add(system.getReward4());
        rewardList.add(system.getReward5());
    }

    private void loadingTrapData() {
        trapList.add(system.getTrap1());
        trapList.add(system.getTrap2());
    }
    /**
     * A helper method to read the map data from a png and convert it to a JLabel, so we can add to the container
     * @return A JLabel with the data from "map.png" in it
     */

    private JLabel displayMap() {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("map.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setBounds(0,0,480,480);
        return picLabel;
    }

    /**
     * A helper method to read the player sprite data from a png and convert it to a JLabel, so we can add to the container
     * @param x x coordinate of where the sprite should be
     * @param y  y coordinate of where the sprite should be
     * @return A JLabel with the data from "player.png" in it
     */

    private JLabel displayPlayer(int x, int y) {
        BufferedImage myPlayer = null;
        try {
            myPlayer = ImageIO.read(new File("playerSprite.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JLabel playerSprite = new JLabel(new ImageIcon(myPlayer));
        playerSprite.setBounds(x,y,32,32);
        return playerSprite;
    }

    /**
     * A helper method to read the enemy sprite data from a png and convert it to a JLabel, so we can add to the container
     * @param x x coordinate of where the sprite should be
     * @param y  y coordinate of where the sprite should be
     * @return A JLabel with the data from "zigzagoon.png" in it
     */

    private JLabel displayEnemy(int x, int y) {
        BufferedImage myEnemy = null;
        try {
            myEnemy = ImageIO.read(new File("zigzagoon.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JLabel enemySprite = new JLabel(new ImageIcon(myEnemy));
        enemySprite.setBounds(x, y, 32,32);
        return enemySprite;
    }

    /**
     * A helper method to read the mandatory reward sprite data from a png and convert it to a JLabel,
     * so we can add to the container
     * @param x x coordinate of where the sprite should be
     * @param y  y coordinate of where the sprite should be
     * @return A JLabel with the data from "reward.png" in it
     */

    private JLabel displayMandatoryCollectible(int x, int y) {
        BufferedImage myReward = null;
        try {
            myReward = ImageIO.read(new File("reward.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JLabel rewardSprite = new JLabel(new ImageIcon(myReward));
        rewardSprite.setBounds(x, y, 32,32);
        return rewardSprite;
    }

    /**
     * A helper method to read the trap sprite data from a png and convert it to a JLabel,
     * so we can add to the container
     * @param x x coordinate of where the sprite should be
     * @param y  y coordinate of where the sprite should be
     * @return A JLabel with the data from "trap.png" in it
     */

    private JLabel displayTrap (int x, int y) {
        BufferedImage myTrap = null;
        try {
            myTrap = ImageIO.read(new File("trap.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JLabel trapSprite = new JLabel(new ImageIcon(myTrap));
        trapSprite.setBounds(x, y, 32,32);
        return trapSprite;
    }

    /**
     * A helper method to read the exit waypoint sprite data from a png and convert it to a JLabel,
     * so we can add to the container
     * @param x x coordinate of where the sprite should be
     * @param y  y coordinate of where the sprite should be
     * @return A JLabel with the data from "unlockedWaypoint.png" in it
     */

    private JLabel displayExitWaypoint (int x, int y) {
        BufferedImage myExit = null;
        try {
            myExit = ImageIO.read(new File("unlockedWaypoint.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JLabel exitSprite = new JLabel(new ImageIcon(myExit));
        exitSprite.setBounds(x, y, 32,32);
        return exitSprite;
    }

    /**
     * A helper method to read the random rewards sprite data from a png and convert it to a JLabel,
     * so we can add to the container
     * @param x x coordinate of where the sprite should be
     * @param y  y coordinate of where the sprite should be
     * @return A JLabel with the data from "randomRewards.png" in it
     */

    private JLabel displayRandomRewards(int x, int y) {
        BufferedImage myRandomRewards = null;
        try {
            myRandomRewards = ImageIO.read(new File("randomRewards.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JLabel randomRewardsSprite = new JLabel(new ImageIcon(myRandomRewards));
        randomRewardsSprite.setBounds(x, y, 32,32);
        return randomRewardsSprite;
    }

    private JLabel displayPokeball(int x, int y){
        BufferedImage pokeball = null;
        try {
            pokeball = ImageIO.read(new File("pokeball.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JLabel pokeballSprite = new JLabel(new ImageIcon(pokeball));
        pokeballSprite.setBounds(x,y,32,32);
        return pokeballSprite;
    }
    private void displayEnemyOnMap() {
        //Read the enemy sprite data from a png to JLabel and add it to the main scene
        for (Enemy enemy : enemyList) {
            JLabel enemySprite = displayEnemy(enemy.getPosition().getX(), enemy.getPosition().getY());
            add(enemySprite);
            gamePanel.add(enemySprite);
        }
    }

    private void displayRewardsOnMap() {
        //Read the reward sprite data from a png to JLabel and add it to the main scene
        for (Reward reward : rewardList) {
            JLabel rewardSprite = displayMandatoryCollectible(reward.getPosition().getX(), reward.getPosition().getY());
            add(rewardSprite);
            gamePanel.add(rewardSprite);
        }
    }

    private void displayRandomRewardsOnMap() {
        //Read the bonus reward sprite data from a png to JLabel and add it to the main scene
        bonusRewardList = system.GetUpdatedBonus();
        for (BonusReward bonus : bonusRewardList) {
            JLabel bonusSprite = displayRandomRewards(bonus.getPosition().getX(), bonus.getPosition().getY());
            add(bonusSprite);
            gamePanel.add(bonusSprite);
        }
    }

    private void displayTrapOnMap() {
        //Read the punishment sprite data from a png to a JLabel and add it to the main scene
        for (Punishment trap : trapList) {
            JLabel trapSprite = displayTrap(trap.getPosition().getX(), trap.getPosition().getY());
            add(trapSprite);
            gamePanel.add(trapSprite);
        }
    }

    private void displayHUD() {
        //Display game's HUD for player
        JLabel scoreLabel = new JLabel("Score: " + system.getMainCharacter().getScore());
        scoreLabel.setBounds(5,5, 100,20);
        scoreLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        scoreLabel.setForeground(Color.white);
        gamePanel.add(scoreLabel);

        JLabel requirementsLabel = new JLabel("There are " + rewardList.size() + " credits you need to collect");
        requirementsLabel.setBounds(90, 5, 350,20);
        requirementsLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        requirementsLabel.setForeground(Color.white);
        gamePanel.add(requirementsLabel);
        //If player has finished collecting the mandatory rewards
        if (rewardList.size() == 0) {
            requirementsLabel.setText("You have finished with your degree. Go to the Convocation to claim it!");
        }
    }

    /**
     * Helper method to change the position of the character sprite up one cell and redraw everything so that
     * the main character remains on the top most layer
     */

    private void moveCharacterUp() {
        gamePanel.removeAll();
        JLabel playerSprite = displayPlayer(system.getMainCharacter().getPosition().getX(), (system.getMainCharacter().getPosition().getY()));
        add(playerSprite);
        gamePanel.add(playerSprite);
        rerenderOtherComponents();

        gamePanel.repaint();
    }

    /**
     * Helper method to change the position of the character sprite down one cell and redraw everything so that
     * the main character remains on the top most layer
     */

    private void moveCharacterDown() {
        gamePanel.removeAll();
        JLabel playerSprite = displayPlayer(system.getMainCharacter().getPosition().getX(), (system.getMainCharacter().getPosition().getY()));
        add(playerSprite);
        gamePanel.add(playerSprite);
        add(playerSprite);

        rerenderOtherComponents();

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    /**
     * Helper method to change the position of the character sprite to the left one cell and redraw everything so that
     * the main character remains on the top most layer
     */

    private void moveCharacterLeft() {
        gamePanel.removeAll();
        JLabel playerSprite = displayPlayer(system.getMainCharacter().getPosition().getX(),system.getMainCharacter().getPosition().getY());
        add(playerSprite);
        gamePanel.add(playerSprite);

        rerenderOtherComponents();

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    /**
     * Helper method to change the position of the character sprite to the right one cell and redraw everything so that
     * the main character remains on the top most layer
     */

    private void moveCharacterRight() {
        gamePanel.removeAll();
        JLabel playerSprite = displayPlayer((system.getMainCharacter().getPosition().getX()),system.getMainCharacter().getPosition().getY());
        add(playerSprite);
        gamePanel.add(playerSprite);

        rerenderOtherComponents();

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    /**
     * Helper method to create a Pokeball sprite depends on the x and y position of the main character
     */

    private void throwPokeball(String direction){
        //Fetching current position of the player
        int x = system.getMainCharacter().getPosition().getX();
        int y = system.getMainCharacter().getPosition().getY();
        switch (direction) {
            case "up":
                renderPokeball(x, y - 64);
                break;
            case "down":
                renderPokeball(x, y + 64);
                break;
            case "left":
                renderPokeball(x - 64, y);
                break;
            case "right":
                renderPokeball(x + 64, y);
                break;
        }
    }

    /**
     * Helper method to clean up the code, it can paint both the player sprite and the Pokeball sprite based on x-y coordination
     * @param x Current position of the pokeball on the x-axis
     * @param y Current position of the pokeball on the y-axis
     */
    private void renderPokeball(int x, int y){
        //Remove all current object on the game panel
        gamePanel.removeAll();
        //Redrawing playerSprite using their current position
        rerenderPlayerSprite();
        //Drawing Pokeball based on the parameter x and y coordination, this coordination is calculated in throwPokeball() method, so it can move up, down, left and right
        JLabel pokeballSprite = displayPokeball(x, y);
        add(pokeballSprite);
        gamePanel.add(pokeballSprite);
        //Redisplay other components in the game panel
        rerenderOtherComponents();
        gamePanel.repaint();
        ActionListener catchingPokemon = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                gamePanel.removeAll();
                //Redrawing playerSprite using their current position
                rerenderPlayerSprite();
                rerenderOtherComponents();
                gamePanel.repaint();
                System.out.println("Reading SMTP Info.");
            }
        };
        //A delay to simulate catching the Pokemon and delete the Pokeball sprite when it finished "catching"
        Timer timer = new Timer(200 ,catchingPokemon);
        timer.setRepeats(false);
        timer.start();
    }
    private void rerenderOtherComponents() {
        //Redraw everything so that the player remains on the top most layer
        if (system.getMainCharacter().getScore() <= 0) { //If player reached score 0 after moving then display death screen
            deathScreen();
            return;
        }
        enemyList = system.EnemyUpdate();
        //Check if the cell player just entered has any reward, if there is then that reward would be removed
        checkForReward();

        //Check if the player has fulfilled the requirement, if player has then the exit point would be unlocked
        checkToUnlockExit();

        //Check if the player has reached the exit point with the requirement fulfilled
        if (gameIsCompleted()) {
            return;
        }

        //Redisplay other components so that player would be the top layer if the game has not been completed
        int currentTimer = LocalDateTime.now().getSecond();
        //Display game's HUD for player
         displayHUD();

        //Updating the data list
        displayEnemyOnMap();
        displayRewardsOnMap();
        displayTrapOnMap();
        displayRandomRewardsOnMap();
        JLabel mapScene = displayMap();
        add(mapScene);
        gamePanel.add(mapScene);
    }

    private void rerenderPlayerSprite(){
        JLabel playerSprite = displayPlayer(system.getMainCharacter().getPosition().getX(), system.getMainCharacter().getPosition().getY());
        add(playerSprite);
        gamePanel.add(playerSprite);
    }
    /**
     * Helper method to check if player just entered a cell with a reward in it, if yes then remove that reward from
     * rewardList
     */

    private void checkForReward() {
        Reward rewardToBeRemoved = null;
        for (Reward currentReward : rewardList) {
            //If player is in the same cell as the reward, then that reward would be removed from the reward list
            if (system.getMainCharacter().getPosition().getX() == currentReward.getPosition().getX()
                    && system.getMainCharacter().getPosition().getY() == currentReward.getPosition().getY()) {
                rewardToBeRemoved = currentReward;
            }
        }
        rewardList.remove(rewardToBeRemoved);
    }

    /**
     * Check if player has completed the game by collecting all of the mandatory rewards
     */

    private void checkToUnlockExit () {
        if (rewardList.size() == 0) {
            JLabel exitPointSprite = displayExitWaypoint(exitPoint.getX(), exitPoint.getY());
            add(exitPointSprite);
            gamePanel.add(exitPointSprite);
        }
    }

    /**
     * Helper method to create a death screen when player's score dropped below 0 or touched a moving enemy
     */

    private void deathScreen() {
        gamePanel.removeAll();
        gamePanel.setBackground(Color.BLACK);
        JLabel deathAnnouncement = new JLabel("You Died!");
        deathAnnouncement.setBounds(175,210,135,30);
        deathAnnouncement.setFont(new Font("Helvetica Neue", Font.PLAIN, 30));
        deathAnnouncement.setForeground(Color.white);
        gamePanel.add(deathAnnouncement);
        //Set Window focus to false so that windows will no longer received key from KeyListener
        gamePanel.setFocusable(false);
        clip.close(); //Close background music file when died
        this.revalidate();
        this.repaint();
    }

    /**
     * Helper method create an endgame screen if player managed to finish the game
     * @return True if the game is finished and false if the game has not
     */

    private boolean gameIsCompleted () {
        if (rewardList.size() == 0 ) {
            /*If the player current position is the same as the exit point and all requirements has been fulfilled.
             Then display the end screen to the player */
            if (system.getMainCharacter().getPosition().getX() == exitPoint.getX()
                    && system.getMainCharacter().getPosition().getY() == exitPoint.getY()) {
                gamePanel.removeAll();
                gamePanel.setBackground(Color.WHITE);
                JLabel victoryAnnouncement = new JLabel("You Have Escaped From SFU. Congratulations");
                victoryAnnouncement.setBounds(100, 175, 500, 30);
                victoryAnnouncement.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
                victoryAnnouncement.setForeground(Color.RED);
                gamePanel.add(victoryAnnouncement);

                JLabel overallScore = new JLabel("Your score is: " + system.getMainCharacter().getScore());
                overallScore.setBounds(100, 200, 500, 30);
                overallScore.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
                overallScore.setForeground(Color.RED);
                gamePanel.add(overallScore);

                gamePanel.setFocusable(false);
                clip.close(); //Close background music when finished game
                this.revalidate();
                this.repaint();
                return true;
            }
        }
        return false;
    }
}
