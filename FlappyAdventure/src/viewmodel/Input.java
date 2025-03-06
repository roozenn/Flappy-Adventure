package viewmodel;

import view.UpDown;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
    private UpDown upDown; // Referensi ke objek UpDown

    public Input(UpDown upDown) {
        this.upDown = upDown;

        // Menambahkan KeyListener ke objek UpDown untuk mendeteksi input keyboard
        upDown.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Metode ini dipanggil saat tombol ditekan dan dilepas, tidak digunakan dalam implementasi ini
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Mengatur aksi saat tombol ditekan
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            // Jika tombol panah atas atau 'W' ditekan, burung akan melompat ke atas
            upDown.getGame().getPlayer().setVelocityY(-15);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            // Jika tombol panah bawah atau 'S' ditekan, burung akan melompat ke atas
            upDown.getGame().getPlayer().setVelocityY(7);
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            // Jika tombol panah kiri atau 'A' ditekan, burung akan bergerak ke kiri
            upDown.getGame().getPlayer().setVelocityX(-12);
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            // Jika tombol panah kanan atau 'D' ditekan, burung akan bergerak ke kanan
            upDown.getGame().getPlayer().setVelocityX(8);
        }
        else if (e.getKeyCode() == KeyEvent.VK_R || e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Jika tombol 'R' atau 'Enter' ditekan dan permainan berakhir, permainan akan dimulai ulang
            if (upDown.getGame().getIsGameOver()) {
                upDown.getGame().Restart();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Mengatur aksi saat tombol dilepas
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            // Jika tombol panah kiri, 'A', panah kanan, atau 'D' dilepas, kecepatan burung di sumbu X diatur menjadi 0
            upDown.getGame().getPlayer().setVelocityX(0);
        }
    }
}
