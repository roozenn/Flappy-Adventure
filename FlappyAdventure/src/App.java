import view.UpDown; // Mengimpor kelas UpDown dari paket view
import view.MainMenu; // Mengimpor kelas MainMenu dari paket view

import javax.swing.*; // Mengimpor paket javax.swing untuk GUI
import java.awt.event.ActionEvent; // Mengimpor kelas ActionEvent untuk menangani aksi
import java.awt.event.ActionListener; // Mengimpor kelas ActionListener untuk mendengarkan aksi

public class App {
    public static void main(String[] args) {
        // Membuat JFrame untuk menu utama
        JFrame menuFrame = new JFrame("Up Down");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Mengatur operasi default saat aplikasi ditutup
        menuFrame.setSize(800, 640); // Mengatur ukuran frame
        menuFrame.setLocationRelativeTo(null); // Menempatkan frame di tengah layar
        menuFrame.setResizable(false); // Menonaktifkan pengubahan ukuran frame

        // Membuat instance dari MainMenu dan menambahkan panel utama ke frame
        MainMenu mainMenu = new MainMenu();
        menuFrame.add(mainMenu.getMainPanel());
        menuFrame.setVisible(true); // Menampilkan frame

        // Menambahkan ActionListener ke tombol play
        mainMenu.getPlayButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Menutup frame menu
                menuFrame.dispose();

                // Membuat JFrame baru untuk permainan
                JFrame gameFrame = new JFrame("Up Down");
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Mengatur operasi default saat aplikasi ditutup
                gameFrame.setSize(800, 640); // Mengatur ukuran frame
                gameFrame.setLocationRelativeTo(null); // Menempatkan frame di tengah layar
                gameFrame.setResizable(false); // Menonaktifkan pengubahan ukuran frame

                // Membuat objek JPanel dari kelas UpDown
                UpDown upDown = new UpDown();
                gameFrame.add(upDown); // Menambahkan panel UpDown ke frame
                gameFrame.pack(); // Mengatur ukuran frame agar sesuai dengan konten
                upDown.requestFocus(); // Meminta fokus pada panel UpDown
                gameFrame.setVisible(true); // Menampilkan frame permainan
            }
        });
    }
}
