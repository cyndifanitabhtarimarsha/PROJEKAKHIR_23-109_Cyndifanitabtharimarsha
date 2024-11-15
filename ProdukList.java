/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectakhir;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;

public class ProdukList extends JFrame {
    public ProdukList() {
        setTitle("E-Commerce Product List");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Panel Kontainer Produk
        JPanel productContainer = new JPanel();
        productContainer.setLayout(new BoxLayout(productContainer, BoxLayout.Y_AXIS)); // Atur layout jadi BoxLayout secara vertikal
        productContainer.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        
        // Panel Grid dalam Kontainer
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        
        // Tambahkan beberapa produk ke dalam gridPanel
        for (int i = 0; i < 50; i++) { // Loop untuk tambah produk contoh
            gridPanel.add(new ProdukPanel("path/to/image.jpg", "Produk " + (i + 1), "100,000"));
        }
        
        productContainer.add(gridPanel); // Tambahkan grid ke dalam container utama
        
        // Tambahkan ke JScrollPane
        JScrollPane scrollPane = new JScrollPane(productContainer);
        scrollPane.setPreferredSize(new Dimension(750, 500));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new ProdukList();
    }
}
