package viewmodel;

import model.Obstacle;
import view.UpDown;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ObstacleMaker {
    private UpDown upDown;
    private int obstacleStartPosX = 800; // Initial X position of the obstacle
    private ArrayList<Obstacle> obstacles; // List of obstacles in the game
    private Timer obstaclesCooldown; // Timer to manage the interval for placing new obstacles
    private boolean lastObstacleUpper = false; // Track last placed obstacle type
    private int obstacleWidth = 128; // Width of the obstacle

    public ObstacleMaker(UpDown upDown) {
        this.upDown = upDown;
        obstacles = new ArrayList<>();

        // Initialize the timer with a 2500 ms interval to add new obstacles
        obstaclesCooldown = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeObstacles(); // Call method to place a new obstacle
            }
        });
        obstaclesCooldown.start(); // Start the timer
    }

    public void placeObstacles() {
        int obstacleHeight = 50 + (int) (Math.random() * 250); // Random obstacle height between 300 and 500

        if (lastObstacleUpper) {
            // Create lower obstacle and add to the list
            int lowerObstaclePosY = upDown.getHeight() - obstacleHeight;
            Obstacle lowerObstacle = new Obstacle(obstacleStartPosX, lowerObstaclePosY, obstacleWidth, obstacleHeight, upDown.getLowerObstacleImage());
            obstacles.add(lowerObstacle);
            lastObstacleUpper = false; // Set the last placed obstacle to lower
        } else {
            // Create upper obstacle and add to the list
            int upperObstaclePosY = 0; // Top of the screen
            Obstacle upperObstacle = new Obstacle(obstacleStartPosX, upperObstaclePosY, obstacleWidth, obstacleHeight, upDown.getUpperObstacleImage());
            obstacles.add(upperObstacle);
            lastObstacleUpper = true; // Set the last placed obstacle to upper
        }
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles; // Return the list of obstacles
    }

    public Timer getObstaclesCooldown() {
        return obstaclesCooldown; // Return the timer for obstacle placement
    }
}
