package ca.cmpt276.project;

import ca.cmpt276.project.view.GameUI;

import javax.swing.*;


public class MazeGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameUI("Escape from SFU v.0.0.1"));
    }
}
