package view;

import viewmodel.*;
import javax.swing.*;
import java.awt.*;

public class UpDown extends JPanel {
    private Game game;
    private ObstacleMaker obstacleMaker;
    private Input input;
    private Collision collision;

    private int frameWidth = 800;
    private int frameHeight = 640;

    // image attributes
    private Image backgroundImage;
    private Image charImage;
    private Image lowerObsImage;
    private Image upperObsImage;

    public UpDown() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);

        backgroundImage = new ImageIcon(getClass().getResource("../assets/background.png")).getImage();
        charImage = new ImageIcon(getClass().getResource("../assets/character.png")).getImage();
        lowerObsImage = new ImageIcon(getClass().getResource("../assets/lower.png")).getImage();
        upperObsImage = new ImageIcon(getClass().getResource("../assets/upper.png")).getImage();

        game = new Game(this);
        obstacleMaker = new ObstacleMaker(this);
        input = new Input(this);
        collision = new Collision(this, game);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.draw(g);
    }

    public int getFrameWidth() { return frameWidth; }
    public int getFrameHeight() { return frameHeight; }
    public Game getGame() { return game; }
    public ObstacleMaker getObstacleMaker() { return obstacleMaker; }
    public Collision getCollision() { return collision; }
    public Image getBackgroundImage() { return backgroundImage; }
    public Image getCharImage() { return charImage; }
    public Image getLowerObstacleImage() { return lowerObsImage; }
    public Image getUpperObstacleImage() { return upperObsImage; }
}
