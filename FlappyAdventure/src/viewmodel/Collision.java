package viewmodel;

import model.Obstacle;
import model.Player;
import view.UpDown;

public class Collision {
    private UpDown upDown; // Referensi ke objek UpDown
    private Game game; // Referensi ke objek Game

    public Collision(UpDown upDown, Game game) {
        this.upDown = upDown;
        this.game = game;
    }

    // Metode untuk menangani tabrakan antara pemain dan pipa
    public void collide(Player player, Obstacle obstacle) {
        if (checkCollision(player, obstacle)) { // Periksa apakah terjadi tabrakan
            // Hitung jarak antara pemain dan pipa dari berbagai arah
            float leftDistance = Math.abs((player.getPosX() + player.getWidth()) - obstacle.getPosX());
            float rightDistance = Math.abs(player.getPosX() - (obstacle.getPosX() + obstacle.getWidth()));
            float downDistance = Math.abs((player.getPosY() + player.getHeight()) - obstacle.getPosY());
            float upDistance = Math.abs(player.getPosY() - (obstacle.getPosY() + obstacle.getHeight()));

            // Tentukan arah tabrakan dan atur posisi serta kecepatan pemain
            if (leftDistance < rightDistance && leftDistance < downDistance && leftDistance < upDistance) {
                // Tabrakan dari kiri
                player.setVelocityX(-3);
                player.setPosX(obstacle.getPosX() - player.getWidth());
            } else if (rightDistance < downDistance && rightDistance < upDistance) {
                // Tabrakan dari kanan
                player.setVelocityX(0);
                player.setPosX(obstacle.getPosX() + obstacle.getWidth());
            } else if (downDistance < upDistance) {
                // Tabrakan dari bawah, PLAYER NEMPEL DI MULUT PIPA BAWAH
                player.setVelocityY(0);
                player.setVelocityX(-3);
                player.setPosY(obstacle.getPosY() - player.getHeight());

                // Tambahkan nilai skor hanya jika belum pernah dilewati
                if (!obstacle.getPassed()) {
                    int scoreValue = calculateScore(obstacle);
                    game.addScore(scoreValue);
                    game.addHit(scoreValue);
                    game.addDown(1);
                    obstacle.setPassed(true); // Tandai bahwa pipa telah dilewati
                }
            } else {
                // Tabrakan dari atas, PLAYER NEMPEL DI MULUT PIPA ATAS
                player.setVelocityY(-2);
                player.setVelocityX(-3);
                player.setPosY(obstacle.getPosY() + obstacle.getHeight());

                // Tambahkan nilai skor hanya jika belum pernah dilewati
                if (!obstacle.getPassed()) {
                    int scoreValue = calculateScore(obstacle);
                    game.addScore(scoreValue);
                    game.addHit(scoreValue);
                    game.addUp(1);
                    obstacle.setPassed(true); // Tandai bahwa pipa telah dilewati
                }
            }
        }
    }

    // Metode untuk menghitung skor berdasarkan tinggi pipa
    private int calculateScore(Obstacle obstacle) {
        int minObstacleHeight = 50;  // Tinggi minimal pipa
        int maxObstacleHeight = 300; // Tinggi maksimal pipa
        int minScore = 10;       // Skor minimal
        int maxScore = 60;       // Skor maksimal

        int obstacleHeight = obstacle.getHeight();

        // Pastikan tinggi pipa berada dalam rentang yang diharapkan
        if (obstacleHeight < minObstacleHeight) {
            obstacleHeight = minObstacleHeight;
        } else if (obstacleHeight > maxObstacleHeight) {
            obstacleHeight = maxObstacleHeight;
        }

        // Skala proporsional untuk menghitung skor antara minScore dan maxScore
        int score = minScore + ((maxObstacleHeight - obstacleHeight) * (maxScore - minScore)) / (maxObstacleHeight - minObstacleHeight);

        return score;
    }


    // Metode untuk memeriksa apakah terjadi tabrakan antara pemain dan pipa
    private boolean checkCollision(Player player, Obstacle obstacle) {
        return player.getPosX() < obstacle.getPosX() + obstacle.getWidth() &&
                player.getPosX() + player.getWidth() > obstacle.getPosX() &&
                player.getPosY() < obstacle.getPosY() + obstacle.getHeight() &&
                player.getPosY() + player.getHeight() > obstacle.getPosY();
    }
}
