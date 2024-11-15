/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectakhir;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GambarInput extends JFrame {
    private JButton pilihButton;
    private JLabel labelGambar;

    public GambarInput() {
        // Set up JFrame
        setTitle("Input Gambar");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Membuat JButton untuk memilih gambar
        pilihButton = new JButton("Pilih Gambar");
        add(pilihButton);

        // Membuat JLabel untuk menampilkan gambar
        labelGambar = new JLabel();
        labelGambar.setPreferredSize(new Dimension(300, 300)); // Menetapkan ukuran label untuk gambar
        add(labelGambar);

        // Action listener untuk tombol pilihButton
        pilihButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Membuka JFileChooser untuk memilih gambar
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    
                    // Menampilkan gambar di JLabel sebagai cover
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                    Image image = imageIcon.getImage();
                    
                    // Menyesuaikan gambar agar memenuhi label tanpa merusak rasio aspek
                    Image scaledImage = image.getScaledInstance(labelGambar.getWidth(), labelGambar.getHeight(), Image.SCALE_SMOOTH);
                    labelGambar.setIcon(new ImageIcon(scaledImage));

                    // Membuat nama file unik (menggunakan UUID atau timestamp)
                    String uniqueFileName = generateUniqueFileName(selectedFile.getName());
                    
                    // Menyimpan gambar ke folder img dengan nama baru
                    String targetPath = "src/img/" + uniqueFileName;
                    File targetFile = new File(targetPath);
                    try {
                        // Salin gambar ke folder img
                        copyFile(selectedFile, targetFile);
                        
                        // Simpan nama file ke database
                        saveImageToDatabase(targetFile.getName());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Gagal menyimpan gambar: " + ex.getMessage());
                    }
                }
            }
        });
    }

    private String generateUniqueFileName(String originalFileName) {
        // Ekstrak ekstensi file
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        
        // Gunakan UUID atau timestamp untuk membuat nama file unik
        String uniqueName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return uniqueName + extension; // Contoh: gambar_20241111_153210.jpg
    }

    private void copyFile(File source, File destination) throws IOException {
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(destination)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

    private void saveImageToDatabase(String imageName) {
        String url = "jdbc:mysql://localhost:3306/coba"; // Ganti dengan URL database kamu
        String user = "root"; // Ganti dengan username database kamu
        String password = ""; // Ganti dengan password database kamu

        String sql = "INSERT INTO gambar (path) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameter untuk nama gambar
            stmt.setString(1, imageName);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Gambar berhasil disimpan!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Menjalankan aplikasi
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GambarInput().setVisible(true);
            }
        });
    }
}
