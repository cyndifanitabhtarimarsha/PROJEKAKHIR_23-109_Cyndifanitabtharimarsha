/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectakhir;

/**
 *
 * @author ASUS
 */
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class DaftarBelanjaDialog extends JDialog {

    public DaftarBelanjaDialog(JFrame parent, ArrayList<panelProduk.Produk> keranjangBelanja) {
        super(parent, "Daftar Belanja", true);
        
        // Setup layout
        setLayout(new BorderLayout());
        
        // Panel untuk menampilkan daftar belanja
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        BigDecimal totalHarga = BigDecimal.ZERO;
        
        // Tampilkan setiap produk dalam keranjang
        for (panelProduk.Produk produk : keranjangBelanja) {
            JLabel label = new JLabel(produk.getNama() + " - " + produk.getHarga());
            listPanel.add(label);

            // Tambahkan harga produk ke total
            totalHarga = totalHarga.add(produk.getHarga());
        }
        
        // Panel untuk menampilkan total harga
        JLabel totalLabel = new JLabel("Total Harga: " + totalHarga);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Tambahkan listPanel dan totalLabel ke dialog
        add(new JScrollPane(listPanel), BorderLayout.CENTER);
        add(totalLabel, BorderLayout.SOUTH);

        setSize(300, 400);
        setLocationRelativeTo(parent);
    }
}

