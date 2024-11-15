/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package projectakhir;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class BelanjaDialog extends javax.swing.JDialog {
    private Connection conn;
    private JTextField bayarField;
    private JLabel kembalianLabel;
    private BigDecimal totalHarga;
    private Home homeInstance;
    /**
     * Creates new form BelanjaDialog
     */
    public BelanjaDialog(java.awt.Frame parent, boolean modal, ArrayList<panelProduk.Produk> keranjangBelanja, Home homeInstance) {
        super(parent,"Daftar Belanja" ,modal);
        initComponents();
        this.homeInstance = homeInstance;
        
        int idUser = Home.idUser;
        
        conn = Koneksi.getConnection();
        totalHarga = BigDecimal.ZERO;
        Map<String, Integer> productQuantities = new HashMap<>();
        
        // Tampilkan setiap produk dalam keranjang
        for (panelProduk.Produk produk : keranjangBelanja) {
            int idBarang = produk.getId();
            String namaBarang = produk.getNama();
            String gambarProduk = produk.getGambar();
            System.out.println(gambarProduk + " telah ditambahkan ke keranjang.");
            BigDecimal hargaBarang = produk.getHarga();
            
            // Check if the product already exists in the map
            if (productQuantities.containsKey(namaBarang)) {
                // Increase the quantity if the product is already in the cart
                productQuantities.put(namaBarang, productQuantities.get(namaBarang) + 1);
            } else {
                // Add the product to the map with quantity 1 if it doesn't exist
                productQuantities.put(namaBarang, 1);
            }
            
            layoutGridPanel.add(new BelanjaPanel(namaBarang, hargaBarang, productQuantities.get(namaBarang), gambarProduk));

            // Tambahkan harga produk ke total
            totalHarga = totalHarga.add(produk.getHarga());
        }
        
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String formattedTotal = format.format(totalHarga);
        // Panel untuk menampilkan total harga
        JLabel totalLabel = new JLabel("Total Harga: " + formattedTotal);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Text field for the payment amount
        bayarField = new JTextField(10);
        bayarField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Label to display the change
        kembalianLabel = new JLabel("Kembalian: ");
        kembalianLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Button to process the payment
        JButton bayarButton = new JButton("Bayar");
        bayarButton.setFont(new Font("Arial", Font.BOLD, 14));
           
        // Panel untuk menampilkan total harga dan form pembayaran
        JPanel bayarPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        bayarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        bayarPanel.add(new JLabel("Total Harga:"));
        bayarPanel.add(totalLabel);
        bayarPanel.add(new JLabel("Bayar:"));
        bayarPanel.add(bayarField);
        bayarPanel.add(bayarButton);
        bayarPanel.add(kembalianLabel);
        
        // Layout untuk dialog
        setLayout(new BorderLayout());
        add(new JScrollPane(layoutGridPanel), BorderLayout.CENTER);
        add(bayarPanel, BorderLayout.SOUTH);
        // Set dialog size and position
        setSize(400, 500);
        setLocationRelativeTo(parent);
        
        // Listen to changes in the bayarField to update the kembalian (change)
        bayarField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                updateKembalian(totalHarga);
            }

            // Method to update the change automatically
            private void updateKembalian(BigDecimal totalHarga) {
                try {
                    // Sanitize input to ensure only numbers are included (remove commas or other non-numeric chars)
                    String bayarText = bayarField.getText().replaceAll("[^0-9]", "");

                    // Ensure the bayarText is not empty
                    if (!bayarText.isEmpty()) {
                        // Parse bayar to BigDecimal
                        BigDecimal bayar = new BigDecimal(bayarText);

                        // Calculate the change
                        BigDecimal kembalian = bayar.subtract(totalHarga);

                        // Format the kembalian to currency format
                        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

                        // Update the kembalian label with formatted change value
                        kembalianLabel.setText("Kembalian: " + format.format(kembalian));
                    } else {
                        // If the field is empty, clear the kembalian label
                        kembalianLabel.setText("Kembalian: ");
                    }
                } catch (NumberFormatException e) {
                    // In case of invalid input (non-numeric), reset the kembalian label
                    kembalianLabel.setText("Kembalian: ");
                }
            }

        });
        // Event listener untuk tombol bayar
        bayarButton.addActionListener(e -> simpanTransaksi(keranjangBelanja));
    }
        // Method to update the change automatically
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boxLayoutPanel = new javax.swing.JPanel();
        layoutGridPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        boxLayoutPanel.setLayout(new javax.swing.BoxLayout(boxLayoutPanel, javax.swing.BoxLayout.Y_AXIS));

        layoutGridPanel.setLayout(new java.awt.GridLayout(0, 1));
        boxLayoutPanel.add(layoutGridPanel);

        getContentPane().add(boxLayoutPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel boxLayoutPanel;
    private javax.swing.JPanel layoutGridPanel;
    // End of variables declaration//GEN-END:variables

    private void simpanTransaksi(ArrayList<panelProduk.Produk> keranjangBelanja) {
        try {
            conn.setAutoCommit(false); // Mulai transaksi
            int idUser = Home.idUser;

            // Simpan transaksi ke tabel pesanan
            String sqlPesanan = "INSERT INTO pesanan (user_id, total, tanggal) VALUES (?, ?, ?)";
            PreparedStatement psPesanan = conn.prepareStatement(sqlPesanan, Statement.RETURN_GENERATED_KEYS);
            psPesanan.setInt(1, idUser);
            psPesanan.setBigDecimal(2, totalHarga);
            psPesanan.setTimestamp(3, new Timestamp(new Date().getTime()));
            psPesanan.executeUpdate();
            
            // Dapatkan pesanan_id yang dihasilkan
            ResultSet rs = psPesanan.getGeneratedKeys();
            int pesananId = -1;
            if (rs.next()) {
                pesananId = rs.getInt(1);
            }
            rs.close();
            psPesanan.close();

            // Simpan setiap produk ke detail_pesanan
            String sqlDetailPesanan = "INSERT INTO detail_pesanan (pesanan_id, produk_id, jumlah, harga) VALUES (?, ?, ?, ?)";
            PreparedStatement psDetailPesanan = conn.prepareStatement(sqlDetailPesanan);

            for (panelProduk.Produk produk : keranjangBelanja) {
                psDetailPesanan.setInt(1, pesananId);
                psDetailPesanan.setInt(2, produk.getId()); // Asumsikan produk memiliki metode getId()
                psDetailPesanan.setInt(3, 1); // Asumsikan 1 untuk setiap item
                psDetailPesanan.setBigDecimal(4, produk.getHarga());
                psDetailPesanan.addBatch();
            }

            psDetailPesanan.executeBatch();
            psDetailPesanan.close();

            conn.commit(); // Komit transaksi

            JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            homeInstance.getData(idUser);
            dispose(); // Tutup dialog setelah transaksi berhasil

        } catch (Exception e) {
            try {
                conn.rollback(); // Rollback jika ada error
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                conn.setAutoCommit(true); // Kembalikan ke auto-commit
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
