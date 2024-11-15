/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projectakhir;

import config.Koneksi;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class Home extends javax.swing.JFrame {
    private Connection conn;
    public static int idUser;
    private ArrayList<panelProduk.Produk> keranjangBelanja = new ArrayList<>();
    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        conn = Koneksi.getConnection();
        
        userLogin();
        setupScrollBarang();
        getData(idUser);
    }
    
    private void setupScrollBarang() {
        gridPanel.setLayout(new GridLayout(0, 4, 10, 10));

        PreparedStatement ps;
        ResultSet rs;
        String sql = "SELECT * FROM produk"; // Modify this query as per your database structure

        try {
            // Query the database to get product data
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            // Loop through the result set and add products to the gridPanel
            while (rs.next()) {
                int idProduct = rs.getInt("produk_id"); // Assuming the column storing image path is 'image_path'
                String imageName = rs.getString("gambar"); // Assuming the column storing image path is 'image_path'
                String productName = rs.getString("nama_produk"); // Assuming the column storing product name is 'product_name'
                String price = rs.getString("harga"); // Assuming the column storing price is 'price'

                // Construct the image path from the file name (images are in 'src/img/')
                String imagePath = "src/img/" + imageName;
                String imageDefault = "src/img/Amazon Music.png";

                // Check if the image exists in the src/img/ folder
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    // Add the product with the image to the gridPanel
                    gridPanel.add(new panelProduk(idProduct, imagePath, productName, price, keranjangBelanja));
                } else {
                    // Handle the case where the image doesn't exist (optional)
                    gridPanel.add(new panelProduk(idProduct, imageDefault, productName, price, keranjangBelanja));
                }
            }

            boxPanel.add(gridPanel);

            // Wrap boxPanel in JScrollPane
            JScrollPane scrollPane = new JScrollPane(boxPanel);
            scrollPane.setPreferredSize(new Dimension(588, 295)); // Adjust dimensions as needed
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            // Add JScrollPane to absolutPanel
            absolutPanel.add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 21, 588, 295));

        } catch (Exception e) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void userLogin() {
        PreparedStatement ps;
        ResultSet rs;
        String sql = "SELECT * FROM users WHERE id = ?";
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idUser); // Menggunakan idUser sebagai parameter

            rs = ps.executeQuery();
            if (rs.next()) { // Memeriksa apakah ada hasil
                String nama = rs.getString("nama");
                String username = rs.getString("username");
                String email = rs.getString("email");
                nm_user.setText(nama);
                profilNama.setText(nama);
                profilUsername.setText(username);
                profilEmail.setText(email);
            } else {
                nm_user.setText("User tidak ditemukan");
                profilNama.setText("User tidak ditemukan");
                profilUsername.setText("Username tidak ditemukan");
                profilEmail.setText("Email tidak ditemukan");
            }
        }catch(Exception e){
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE,null, e);
        }
    }
    
    public void getData(int idUser) {
           
        DefaultTableModel model = (DefaultTableModel) transaksiData.getModel();
        model.setRowCount(0);
        PreparedStatement ps;
        ResultSet rs;

        try {
            String sql = "SELECT p.pesanan_id, p.total, p.tanggal, u.nama " +
                            "FROM pesanan p " +
                            "JOIN users u ON p.user_id = u.id " +
                            "WHERE u.id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idUser);
            rs = ps.executeQuery();

            while (rs.next()) {
                
                int idPesanan = rs.getInt("pesanan_id");
                String namaUser = rs.getString("nama");
                String tanggalPesanan = rs.getString("tanggal");
                BigDecimal totalPesanan = rs.getBigDecimal("total");
                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                String formattedHarga = format.format(totalPesanan);

                Object[] rowData = {idPesanan, namaUser, tanggalPesanan, formattedHarga};
                model.addRow(rowData);
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            Logger.getLogger(HomePemilik.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPelanggan = new javax.swing.JTabbedPane();
        absolutPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        boxPanel = new javax.swing.JPanel();
        gridPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transaksiData = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        profilNama = new javax.swing.JLabel();
        profilUsername = new javax.swing.JLabel();
        profilEmail = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        welcome = new javax.swing.JLabel();
        cariBarang = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nm_user = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        logoutPelanggan = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabbedPelanggan.setBackground(new java.awt.Color(0, 51, 255));
        tabbedPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        tabbedPelanggan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        absolutPanel.setBackground(new java.awt.Color(248, 247, 247));
        absolutPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        absolutPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("DAFTAR BARANG E-COMMERCE");
        absolutPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 570, -1));

        boxPanel.setLayout(new javax.swing.BoxLayout(boxPanel, javax.swing.BoxLayout.Y_AXIS));

        gridPanel.setBackground(new java.awt.Color(249, 249, 249));
        gridPanel.setLayout(new java.awt.GridLayout(1, 0));
        boxPanel.add(gridPanel);

        absolutPanel.add(boxPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 21, 588, 295));

        tabbedPelanggan.addTab("Barang", absolutPanel);

        jPanel6.setBackground(new java.awt.Color(248, 247, 247));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("RIWAYAT TRANSAKSI");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, -1, -1));

        transaksiData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "Nama", "Tanggal", "Total Pembelian"
            }
        ));
        transaksiData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transaksiDataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(transaksiData);

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 550, 250));

        tabbedPelanggan.addTab("Transaki", jPanel6);

        jPanel3.setBackground(new java.awt.Color(249, 249, 249));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/man9_117995.png"))); // NOI18N
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 290, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setText("Nama");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        jLabel8.setText("Username");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        jLabel9.setText("Email");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        profilNama.setText("Nama");
        jPanel4.add(profilNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        profilUsername.setText("Username");
        jPanel4.add(profilUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, -1, -1));

        profilEmail.setText("Email");
        jPanel4.add(profilEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, -1, -1));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 290, 250));

        tabbedPelanggan.addTab("Profil", jPanel3);

        getContentPane().add(tabbedPelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 590, 350));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        welcome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        welcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcome.setText("E-COMMERCE");
        jPanel1.add(welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, 30));

        cariBarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cariBarang.setText("pencarian...");
        cariBarang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        cariBarang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cariBarangFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cariBarangFocusLost(evt);
            }
        });
        cariBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cariBarangKeyTyped(evt);
            }
        });
        jPanel1.add(cariBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 170, 30));

        jLabel2.setText("Cari");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, 30));

        nm_user.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nm_user.setText("nama_user");
        jPanel1.add(nm_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 110, 30));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(1, 3, 5, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/username.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel1);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-shoppingcart.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel3);

        logoutPelanggan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoutPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout.png"))); // NOI18N
        logoutPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutPelangganMouseClicked(evt);
            }
        });
        jPanel2.add(logoutPelanggan);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 80, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 440));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logoutPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutPelangganMouseClicked
        // TODO add your handling code here:
        Login lg = new Login();
        lg.setVisible(true);
        lg.pack();
        this.dispose();
    }//GEN-LAST:event_logoutPelangganMouseClicked

    private void cariBarangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariBarangKeyTyped
                                 
        gridPanel.setLayout(new GridLayout(0, 4, 10, 10));
        gridPanel.removeAll(); // Clear existing components to avoid duplication

        String keyword = cariBarang.getText(); 
        String sql = "SELECT * FROM produk WHERE nama_produk LIKE ?";
        String imageDefault = "src/img/Amazon Music.png";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idProduct = rs.getInt("produk_id"); 
                    String imageName = rs.getString("gambar"); 
                    String productName = rs.getString("nama_produk"); 
                    String price = rs.getString("harga"); 

                    String imagePath = "src/img/" + imageName;
                    File imgFile = new File(imagePath);

                    // Use default image if specified image does not exist
                    String finalImagePath = imgFile.exists() ? imagePath : imageDefault;

                    // Add product panel to gridPanel
                    gridPanel.add(new panelProduk(idProduct,finalImagePath, productName, price, keranjangBelanja));
                }
            }

            // Refresh gridPanel UI after adding new components
            gridPanel.revalidate();
            gridPanel.repaint();

            // Clear and re-add gridPanel to boxPanel
            boxPanel.removeAll();
            boxPanel.add(gridPanel);
            boxPanel.revalidate();
            boxPanel.repaint();

            // Create JScrollPane around boxPanel only if not already added
            if (absolutPanel.getComponentCount() == 0) {
                JScrollPane scrollPane = new JScrollPane(boxPanel);
                scrollPane.setPreferredSize(new Dimension(588, 295));
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                // Add JScrollPane to absolutPanel with specified layout constraints
                absolutPanel.add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 21, 588, 295));
            }

            // Refresh absolutPanel UI
            absolutPanel.revalidate();
            absolutPanel.repaint();

        } catch (Exception e) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, e);
        }
           
    }//GEN-LAST:event_cariBarangKeyTyped

    private void cariBarangFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cariBarangFocusGained
        // TODO add your handling code here:
        String cari = cariBarang.getText();
        if(cari.equals("pencarian...")){
            cariBarang.setText("");            
        }
    }//GEN-LAST:event_cariBarangFocusGained

    private void cariBarangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cariBarangFocusLost
        // TODO add your handling code here:
        // TODO add your handling code here:
        String cari = cariBarang.getText();
        if(cari.equals("") || cari.equals("pencarian....")){
            cariBarang.setText("pencarian...");
        }
    }//GEN-LAST:event_cariBarangFocusLost

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
//        DaftarBelanjaDialog dialog = new DaftarBelanjaDialog(this, keranjangBelanja);
        BelanjaDialog dialog = new BelanjaDialog(this,true, keranjangBelanja, this);
        dialog.setVisible(true);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void transaksiDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transaksiDataMouseClicked
        // TODO add your handling code here:
        int selectedRow = transaksiData.getSelectedRow();
        if (selectedRow != -1) {
            // Ambil data lain yang sudah ada di tabel
            int idPesanan = Integer.parseInt(transaksiData.getValueAt(selectedRow, 0).toString());
            String namaUser = transaksiData.getValueAt(selectedRow, 1).toString();
            String totalPesanan = transaksiData.getValueAt(selectedRow, 3).toString();
            String tanggalPesanan = transaksiData.getValueAt(selectedRow, 2).toString();
            
            // Membuka dialog untuk menampilkan detail pesanan berdasarkan idPesanan
            showDetailPesanan(idPesanan, namaUser, totalPesanan, tanggalPesanan);
            
        }

    }//GEN-LAST:event_transaksiDataMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        tabbedPelanggan.setSelectedIndex(2);
    }//GEN-LAST:event_jLabel1MouseClicked
    
    // Method untuk menampilkan detail pesanan dalam dialog
    private void showDetailPesanan(int idPesanan, String namaUser, String totalPesanan, String tanggalPesanan) {
        try {
            // Query untuk mengambil detail pesanan berdasarkan idPesanan
            String sql = "SELECT dp.produk_id, p.nama_produk, dp.jumlah, dp.harga " +
                         "FROM detail_pesanan dp " +
                         "JOIN produk p ON dp.produk_id = p.produk_id " +
                         "WHERE dp.pesanan_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idPesanan);
            ResultSet rs = ps.executeQuery();

            // Dialog untuk menampilkan detail pesanan
            JDialog dialog = new JDialog(this, "Detail Pesanan", true);
            dialog.setLayout(new BorderLayout());

            // Panel untuk menampilkan detail pesanan
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            // Buat tabel untuk menampilkan detail pesanan
            String[] columnNames = {"Produk", "Jumlah", "Harga"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            JTable table = new JTable(model);

            // Menambahkan data ke dalam tabel
            while (rs.next()) {
                String namaProduk = rs.getString("nama_produk");
                int jumlah = rs.getInt("jumlah");
                BigDecimal harga = rs.getBigDecimal("harga");

                // Format harga ke mata uang
                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                String formattedHarga = format.format(harga);

                // Menambahkan row ke model tabel
                model.addRow(new Object[]{namaProduk, jumlah, formattedHarga});
            }

            // Menambahkan tabel ke dalam panel
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane, BorderLayout.CENTER);

            // Menambahkan informasi pesanan
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new GridLayout(0, 1));
            infoPanel.add(new JLabel("ID Pesanan: " + idPesanan));
            infoPanel.add(new JLabel("Nama: " + namaUser));
            infoPanel.add(new JLabel("Tanggal: " + tanggalPesanan));
            infoPanel.add(new JLabel("Total Pesanan: " + totalPesanan));

            // Menambahkan informasi ke dalam panel utama
            dialog.add(infoPanel, BorderLayout.NORTH);
            dialog.add(panel, BorderLayout.CENTER);

            // Set dialog properties
            dialog.setSize(400, 400);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mengambil detail pesanan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel absolutPanel;
    private javax.swing.JPanel boxPanel;
    private javax.swing.JTextField cariBarang;
    private javax.swing.JPanel gridPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel logoutPelanggan;
    private javax.swing.JLabel nm_user;
    private javax.swing.JLabel profilEmail;
    private javax.swing.JLabel profilNama;
    private javax.swing.JLabel profilUsername;
    private javax.swing.JTabbedPane tabbedPelanggan;
    private javax.swing.JTable transaksiData;
    public javax.swing.JLabel welcome;
    // End of variables declaration//GEN-END:variables


}
