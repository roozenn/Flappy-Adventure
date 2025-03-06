package viewmodel;

import model.Obstacle;
import model.Player;
import view.UpDown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game implements ActionListener {
    private UpDown upDown; // Referensi ke objek UpDown
    private int playerStartPosX = 800 / 8; // Posisi awal pemain di sumbu X
    private int playerStartPosY = 640 / 2; // Posisi awal pemain di sumbu Y
    private int playerWidth = 34+17; // Lebar pemain
    private int playerHeight = 34+17; // Tinggi pemain
    private Player player; // Objek pemain
    private Timer gameLoop; // Timer untuk loop game
    private int gravity = 1; // Nilai gravitasi yang mempengaruhi pemain
    private boolean isGameOver = false; // Status permainan
    private float score = 0; // Skor pemain
    private float hit    = 0; // Skor hit
    private float up    = 0; // jumlah up
    private float down    = 0; // jumlah down

    public Game(UpDown upDown) {
        this.upDown = upDown;

        // Inisialisasi pemain dengan posisi awal, ukuran, dan gambar burung
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, upDown.getCharImage());

        // Inisialisasi timer dengan interval 1000/60 ms untuk loop game
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start(); // Mulai timer
    }

    public void draw(Graphics g) {
        // Menggambar latar belakang
        g.drawImage(upDown.getBackgroundImage(), 0, 0, 800, 640, null);

        // Menggambar pemain
        g.drawImage(upDown.getCharImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        // Menggambar pipa
        for (int i = 0; i < upDown.getObstacleMaker().getObstacles().size(); i++) {
            Obstacle obstacle = upDown.getObstacleMaker().getObstacles().get(i);
            g.drawImage(obstacle.getImage(), obstacle.getPosX(), obstacle.getPosY(), obstacle.getWidth(), obstacle.getHeight(), null);
        }

        // Menggambar skor dan status game over
        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.PLAIN, 24));

        int margin = 10;  // Margin dari tepi layar
        int yPosition = 35;  // Posisi vertikal untuk menampilkan teks

        if (isGameOver) {
            g.drawString("Game Over: " + (int) score, margin, yPosition);
        } else {
            g.drawString("Score: " + (int) score, margin, yPosition);
            g.drawString("Hit: " + (int) hit, margin, yPosition + 40);  // Menambah jarak antara teks "Score" dan "Hit"
            g.drawString("Up: " + (int) up, margin, yPosition + 80);  // Menambah jarak antara teks "Score" dan "Hit"
            g.drawString("Down: " + (int) down, margin, yPosition + 120);  // Menambah jarak antara teks "Score" dan "Hit"
        }

    }

    public void move() {
        // Update posisi dan kecepatan pemain
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosX(player.getPosX() + player.getVelocityX());
        player.setPosY(Math.max(player.getPosY(), 0)); // Pastikan pemain tidak keluar dari batas atas
        player.setPosX(Math.min(player.getPosX(), upDown.getFrameWidth() - player.getWidth())); // Pastikan pemain tidak keluar dari batas kanan

        // Update posisi pipa dan cek tabrakan
        for (int i = 0; i < upDown.getObstacleMaker().getObstacles().size(); i++) {
            Obstacle obstacle = upDown.getObstacleMaker().getObstacles().get(i);
            obstacle.setPosX(obstacle.getPosX() + obstacle.getVelocityX());

            // Cek tabrakan antara pemain dan pipa
            upDown.getCollision().collide(player, obstacle);
        }

        // Cek apakah pemain jatuh ke bawah atau keluar dari batas kiri
        if (player.getPosY() > upDown.getFrameHeight() || player.getPosX() < 0) {
            isGameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Panggil metode move dan repaint layar
        move();
        upDown.repaint();

        // Jika game over, hentikan game loop dan timer penempatan pipa
        if (isGameOver) {
            gameLoop.stop();
            upDown.getObstacleMaker().getObstaclesCooldown().stop();
        }
    }

    public void Restart() {
        // Atur ulang posisi, kecepatan pemain, dan status game
        player.setPosY(playerStartPosY);
        player.setPosX(playerStartPosX);
        player.setVelocityY(-6);
        player.setVelocityX(0);

        // Hapus semua pipa dan atur ulang skor
        upDown.getObstacleMaker().getObstacles().clear();
        score = 0;
        isGameOver = false;

        // Mulai ulang game loop dan timer penempatan pipa
        gameLoop.start();
        upDown.getObstacleMaker().getObstaclesCooldown().start();
    }

    public Player getPlayer() {
        return player; // Kembalikan objek pemain
    }

    public boolean getIsGameOver() {
        return isGameOver; // Kembalikan status game over
    }

    // Tambahkan metode untuk menambah skor
    public void addScore(int value) {
        score += value;
    }

    public void addHit(int value) {
        hit = value;
    }

    public void addUp(int value) {
        up += value;
    }

    public void addDown(int value) {
        down += value;
    }

}
