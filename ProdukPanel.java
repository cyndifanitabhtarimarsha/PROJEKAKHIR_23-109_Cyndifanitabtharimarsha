/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectakhir;

import javax.swing.*;
import java.awt.*;

public class ProdukPanel extends JPanel {
    public ProdukPanel(String imagePath, String productName, String productPrice) {
        setLayout(new BorderLayout());
        
        // Gambar Produk
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(imagePath)); // Ganti path sesuai dengan lokasi gambar
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Nama Produk
        JLabel nameLabel = new JLabel(productName, JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Harga Produk
        JLabel priceLabel = new JLabel("Rp " + productPrice, JLabel.CENTER);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceLabel.setForeground(Color.GRAY);
        
        // Tombol untuk tambah ke keranjang
        JButton addToCartButton = new JButton("Add to Cart");
        
        // Tambahkan komponen ke panel
        add(imageLabel, BorderLayout.CENTER);
        add(nameLabel, BorderLayout.NORTH);
        add(priceLabel, BorderLayout.SOUTH);
        add(addToCartButton, BorderLayout.PAGE_END);
        
        // Border
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }
}

