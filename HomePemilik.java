/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projectakhir;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ASUS
 */
public class HomePemilik extends javax.swing.JFrame {
    private Connection conn;
    public static int idUser;
    /**
     * Creates new form Home
     */
    public HomePemilik() {
        initComponents();
        conn = Koneksi.getConnection();
        
        userLogin();
        getData(idUser);
        getDataPesan(idUser);
    }
    
    private void userLogin() {
        PreparedStatement psUser, psToko;
        ResultSet rsUser, rsToko;
        String sqlUser = "SELECT * FROM users WHERE id = ?";
        String sqlToko = "SELECT * FROM toko WHERE pemilik_id = ?";

        try {
            // Query untuk tabel users
            psUser = conn.prepareStatement(sqlUser);
            psUser.setInt(1, idUser); // Menggunakan idUser sebagai parameter

            rsUser = psUser.executeQuery();
            if (rsUser.next()) { // Memeriksa apakah ada hasil
                String nama = rsUser.getString("nama");
                String username = rsUser.getString("username");
                String email = rsUser.getString("email");
                String role = rsUser.getString("role");
                nm_user.setText(nama);
                Lnama.setText(nama);
                Luname.setText(username);
                Lemail.setText(email);
                Lrole.setText(role);
            } else {
                nm_user.setText("User tidak ditemukan");
            }

            // Query untuk tabel toko
            psToko = conn.prepareStatement(sqlToko);
            psToko.setInt(1, idUser); // Menggunakan idUser sebagai parameter

            rsToko = psToko.executeQuery();
            if (rsToko.next()) { // Memeriksa apakah ada hasil
                String namaToko = rsToko.getString("nama_toko");
                String deskripsiToko = rsToko.getString("deskripsi");
                String logoToko = rsToko.getString("logo");
                ttoko.setText(namaToko);
                tdeskripsi.setText(deskripsiToko);
                if(logoToko != null){
                    String imagePath = "src/img/" + logoToko; // Path gambar di folder 'img/'

                    // Menampilkan gambar di JLabel
                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    Image image = imageIcon.getImage();
                    Image scaledImage = image.getScaledInstance(tgambar.getWidth(), tgambar.getHeight(), Image.SCALE_SMOOTH);
                    tgambar.setIcon(new ImageIcon(scaledImage));             
                }
                
            } else {
                ttoko.setText("Toko tidak ditemukan");
            }

            // Tutup ResultSet dan PreparedStatement
            rsUser.close();
            psUser.close();
            rsToko.close();
            psToko.close();

        } catch (Exception e) {
            Logger.getLogger(HomePemilik.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
        }

    }
    // Tampilan Konten
    void bersih(){
        t_produk.setText("nama produk");
        t_harga.setText("harga");
        t_stok.setText("stok");
        t_kategori.setText("kategori");
        t_deskripsi.setText("deskripsi");
    }
    
    private void getData(int idUser) {
        DefaultTableModel model = (DefaultTableModel) tData.getModel();
        model.setRowCount(0);
        PreparedStatement ps;
        ResultSet rs;

        try {
            String sql = "SELECT p.produk_id, p.nama_produk, p.harga, p.kategori, p.stok, t.nama_toko " +
                         "FROM produk p " +
                         "JOIN toko t ON p.toko_id = t.toko_id " +
                         "JOIN users u ON t.pemilik_id = u.id " +
                         "WHERE u.id = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, idUser);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idProduk = rs.getInt("produk_id");
                String namaToko = rs.getString("nama_toko");
                String namaProduk = rs.getString("nama_produk");
                String hargaProduk = rs.getString("harga");
                String kategoriProduk = rs.getString("kategori");
                int stokProduk = rs.getInt("stok");

                Object[] rowData = {idProduk, namaToko, namaProduk, kategoriProduk, hargaProduk, stokProduk};
                model.addRow(rowData);
            }

            ps.close();
            rs.close();
        } catch (Exception e) {
            Logger.getLogger(HomePemilik.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void getDataPesan(int idUser) {
        DefaultTableModel model = (DefaultTableModel) pesanData.getModel();
        model.setRowCount(0);
        PreparedStatement ps;
        ResultSet rs;

        try {
            String sql = "SELECT dp.detail_id, dp.jumlah, dp.harga, u.nama, p.nama_produk " +
                         "FROM detail_pesanan dp " +
                         "JOIN pesanan psn ON dp.pesanan_id = psn.pesanan_id " +
                         "JOIN users u ON psn.user_id = u.id " +
                         "JOIN produk p ON dp.produk_id = p.produk_id " +
                         "JOIN toko t ON p.toko_id = t.toko_id " +
                         "JOIN users pemilik ON t.pemilik_id = pemilik.id " +
                         "WHERE pemilik.id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idUser);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idDetail = rs.getInt("detail_id");
                String jumlahpesan = rs.getString("jumlah");
                String harga = rs.getString("harga");
                String namapesan = rs.getString("nama");
                String namaproduk = rs.getString("nama_produk");

                Object[] rowData = {idDetail, namapesan, namaproduk, jumlahpesan, harga};
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

        jPanel1 = new javax.swing.JPanel();
        tbdPemilik = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tData = new javax.swing.JTable();
        t_kategori = new javax.swing.JTextField();
        t_harga = new javax.swing.JTextField();
        t_deskripsi = new javax.swing.JTextField();
        t_stok = new javax.swing.JTextField();
        welcome = new javax.swing.JLabel();
        t_produk = new javax.swing.JTextField();
        btnHapusProduk = new javax.swing.JButton();
        btnTambahProduk = new javax.swing.JButton();
        btnEditProduk = new javax.swing.JButton();
        tcari = new javax.swing.JTextField();
        btnGambarProduk = new javax.swing.JButton();
        tGambarProduk = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Lnama = new javax.swing.JLabel();
        Luname = new javax.swing.JLabel();
        Lemail = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Lrole = new javax.swing.JLabel();
        tdeskripsi = new javax.swing.JTextField();
        ttoko = new javax.swing.JTextField();
        tgambar = new javax.swing.JLabel();
        pilihGambar = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pesanData = new javax.swing.JTable();
        btnUser = new javax.swing.JLabel();
        nm_user = new javax.swing.JLabel();
        welcome1 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbdPemilik.setBackground(new java.awt.Color(0, 51, 255));
        tbdPemilik.setForeground(new java.awt.Color(255, 255, 255));
        tbdPemilik.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tbdPemilik.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(248, 247, 247));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Toko", "Nama Produk", "Kategori", "Harga", "Stok"
            }
        ));
        tData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tDataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tData);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 460, 130));

        t_kategori.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_kategori.setText("kategori");
        t_kategori.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        t_kategori.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_kategoriFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_kategoriFocusLost(evt);
            }
        });
        t_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_kategoriActionPerformed(evt);
            }
        });
        jPanel2.add(t_kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 170, 30));

        t_harga.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_harga.setText("harga");
        t_harga.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        t_harga.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_hargaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_hargaFocusLost(evt);
            }
        });
        jPanel2.add(t_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 100, 30));

        t_deskripsi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_deskripsi.setText("deskripsi");
        t_deskripsi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        t_deskripsi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_deskripsiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_deskripsiFocusLost(evt);
            }
        });
        t_deskripsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_deskripsiActionPerformed(evt);
            }
        });
        jPanel2.add(t_deskripsi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 170, 30));

        t_stok.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_stok.setText("stok");
        t_stok.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        t_stok.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_stokFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_stokFocusLost(evt);
            }
        });
        t_stok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_stokActionPerformed(evt);
            }
        });
        jPanel2.add(t_stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 60, 30));

        welcome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        welcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcome.setText("BARANG");
        jPanel2.add(welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 110, 30));

        t_produk.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_produk.setText("nama produk");
        t_produk.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        t_produk.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_produkFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_produkFocusLost(evt);
            }
        });
        jPanel2.add(t_produk, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 170, 30));

        btnHapusProduk.setBackground(new java.awt.Color(0, 0, 255));
        btnHapusProduk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHapusProduk.setForeground(new java.awt.Color(255, 255, 255));
        btnHapusProduk.setText("Hapus");
        btnHapusProduk.setBorder(null);
        btnHapusProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusProdukActionPerformed(evt);
            }
        });
        jPanel2.add(btnHapusProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, 90, 30));

        btnTambahProduk.setBackground(new java.awt.Color(0, 0, 255));
        btnTambahProduk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTambahProduk.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahProduk.setText("Tambah");
        btnTambahProduk.setBorder(null);
        btnTambahProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahProdukActionPerformed(evt);
            }
        });
        jPanel2.add(btnTambahProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 90, 30));

        btnEditProduk.setBackground(new java.awt.Color(0, 0, 255));
        btnEditProduk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditProduk.setForeground(new java.awt.Color(255, 255, 255));
        btnEditProduk.setText("Edit");
        btnEditProduk.setBorder(null);
        btnEditProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditProdukActionPerformed(evt);
            }
        });
        jPanel2.add(btnEditProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, 90, 30));

        tcari.setText("Pencarian...");
        tcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tcariKeyTyped(evt);
            }
        });
        jPanel2.add(tcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 460, -1));

        btnGambarProduk.setText("Upload Gambar");
        btnGambarProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGambarProdukActionPerformed(evt);
            }
        });
        jPanel2.add(btnGambarProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 150, 160, -1));
        jPanel2.add(tGambarProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 100, 40));

        tbdPemilik.addTab("Barang", jPanel2);

        jPanel4.setBackground(new java.awt.Color(249, 249, 249));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/man9_117995.png"))); // NOI18N
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, -1));

        Lnama.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Lnama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lnama.setText("Nama");
        jPanel4.add(Lnama, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 60, -1));

        Luname.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Luname.setText("username");
        jPanel4.add(Luname, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 60, 30));

        Lemail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Lemail.setText("email");
        jPanel4.add(Lemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 60, 30));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("|");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 20, 60));

        Lrole.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lrole.setText("role");
        jPanel4.add(Lrole, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 150, 20));

        tdeskripsi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tdeskripsi.setText("deskripsi");
        tdeskripsi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        tdeskripsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tdeskripsiActionPerformed(evt);
            }
        });
        jPanel4.add(tdeskripsi, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, 240, 40));

        ttoko.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ttoko.setText("nama toko");
        ttoko.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        jPanel4.add(ttoko, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 240, 40));

        tgambar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tgambar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Amazon Music.png"))); // NOI18N
        jPanel4.add(tgambar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 100, 100));

        pilihGambar.setText("Pilih Gambar");
        pilihGambar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihGambarActionPerformed(evt);
            }
        });
        jPanel4.add(pilihGambar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, -1, -1));

        btnSimpan.setBackground(new java.awt.Color(0, 0, 255));
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("Simpan");
        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSimpanMouseClicked(evt);
            }
        });
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel4.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 350, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Detail Toko");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, -1, -1));

        tbdPemilik.addTab("Profil & Toko", jPanel4);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("PESANAN");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        pesanData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "Nama", "Produk", "Jumlah", "Harga"
            }
        ));
        jScrollPane2.setViewportView(pesanData);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 270));

        tbdPemilik.addTab("Pesanan", jPanel5);

        jPanel1.add(tbdPemilik, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 590, 350));

        btnUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/username.png"))); // NOI18N
        btnUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserMouseClicked(evt);
            }
        });
        jPanel1.add(btnUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, -1, -1));

        nm_user.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nm_user.setText("nama_user");
        jPanel1.add(nm_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 110, 20));

        welcome1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        welcome1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcome1.setText("E-COMMERCE");
        jPanel1.add(welcome1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 110, 30));

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout.png"))); // NOI18N
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });
        jPanel1.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, -1, -1));

        jPanel3.setBackground(new java.awt.Color(0, 51, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 110, 350));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 440));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void t_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_kategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_kategoriActionPerformed

    private void t_deskripsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_deskripsiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_deskripsiActionPerformed

    private void t_stokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_stokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_stokActionPerformed

    private void t_produkFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_produkFocusGained
        // TODO add your handling code here:
        String produk = t_produk.getText();
        if(produk.equals("nama produk")){
            t_produk.setText("");            
        }
    }//GEN-LAST:event_t_produkFocusGained

    private void t_produkFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_produkFocusLost
        // TODO add your handling code here:
        String produk = t_produk.getText();
        if(produk.equals("") || produk.equals("nama produk")){
            t_produk.setText("nama produk");
        }
    }//GEN-LAST:event_t_produkFocusLost

    private void t_hargaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_hargaFocusGained
        // TODO add your handling code here:
        String harga = t_harga.getText();
        if(harga.equals("harga")){
            t_harga.setText("");            
        }
    }//GEN-LAST:event_t_hargaFocusGained

    private void t_hargaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_hargaFocusLost
        // TODO add your handling code here:
        String harga = t_harga.getText();
        if(harga.equals("") || harga.equals("harga")){
            t_harga.setText("harga");
        }
    }//GEN-LAST:event_t_hargaFocusLost

    private void t_stokFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_stokFocusGained
        // TODO add your handling code here:
        String stok = t_stok.getText();
        if(stok.equals("stok")){
            t_stok.setText("");            
        }
    }//GEN-LAST:event_t_stokFocusGained

    private void t_stokFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_stokFocusLost
        // TODO add your handling code here:
        String stok = t_stok.getText();
        if(stok.equals("") || stok.equals("stok")){
            t_stok.setText("stok");
        }
    }//GEN-LAST:event_t_stokFocusLost

    private void t_kategoriFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_kategoriFocusGained
        // TODO add your handling code here:
        String kategori = t_kategori.getText();
        if(kategori.equals("kategori")){
            t_kategori.setText("");            
        }
    }//GEN-LAST:event_t_kategoriFocusGained

    private void t_kategoriFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_kategoriFocusLost
        // TODO add your handling code here:
        String kategori = t_kategori.getText();
        if(kategori.equals("") || kategori.equals("kategori")){
            t_kategori.setText("kategori");
        }
    }//GEN-LAST:event_t_kategoriFocusLost

    private void t_deskripsiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_deskripsiFocusGained
        // TODO add your handling code here:
        String deskripsi = t_deskripsi.getText();
        if(deskripsi.equals("deskripsi")){
            t_deskripsi.setText("");            
        }
    }//GEN-LAST:event_t_deskripsiFocusGained

    private void t_deskripsiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_deskripsiFocusLost
        // TODO add your handling code here:
        String deskripsi = t_deskripsi.getText();
        if(deskripsi.equals("") || deskripsi.equals("deskripsi")){
            t_deskripsi.setText("deskripsi");
        }
    }//GEN-LAST:event_t_deskripsiFocusLost

    private void btnUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserMouseClicked
        // TODO add your handling code here:
        tbdPemilik.setSelectedIndex(1);
    }//GEN-LAST:event_btnUserMouseClicked

    private void tdeskripsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tdeskripsiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tdeskripsiActionPerformed

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
                                      
    // Ambil nilai dari input teks
    String nama_toko = ttoko.getText();
    String deskripsi_toko = tdeskripsi.getText();

    // Validasi untuk memastikan semua kolom diisi
    if (nama_toko.isEmpty() || deskripsi_toko.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua Kolom Harus Diisi!", "Validasi", JOptionPane.ERROR_MESSAGE);
        return;
    } 

    // Periksa apakah idUser tidak null
    if (idUser != 0) {
        try {
            // Query untuk memeriksa apakah data toko sudah ada
            String checkSql = "SELECT * FROM toko WHERE pemilik_id = ?";
            PreparedStatement checkSt = conn.prepareStatement(checkSql);
            checkSt.setInt(1, idUser);

            ResultSet rs = checkSt.executeQuery();
            if (rs.next()) {
                // Jika data toko sudah ada, lakukan update
                String updateSql = "UPDATE toko SET nama_toko = ?, deskripsi = ? WHERE pemilik_id = ?";
                PreparedStatement updateSt = conn.prepareStatement(updateSql);
                updateSt.setString(1, nama_toko);
                updateSt.setString(2, deskripsi_toko);
                updateSt.setInt(3, idUser);

                int rowsUpdated = updateSt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Data Berhasil Diperbarui!");
                    getData(idUser);
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal memperbarui data.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                updateSt.close();
            } else {
                // Jika data toko belum ada, lakukan insert
                String insertSql = "INSERT INTO toko(nama_toko, pemilik_id, deskripsi) VALUES (?, ?, ?)";
                PreparedStatement insertSt = conn.prepareStatement(insertSql);
                insertSt.setString(1, nama_toko);
                insertSt.setInt(2, idUser);
                insertSt.setString(3, deskripsi_toko);

                int rowsInserted = insertSt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan!");
                    getData(idUser);
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menyimpan data.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                insertSt.close();
            }

            // Tutup ResultSet dan PreparedStatement untuk query cek
            rs.close();
            checkSt.close();

        } catch (Exception e) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "ID pengguna tidak ditemukan. Tidak dapat menyimpan data.", "Error", JOptionPane.ERROR_MESSAGE);
    }


    }//GEN-LAST:event_btnSimpanMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnTambahProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahProdukActionPerformed
                                               
        String nmProduk = t_produk.getText();
        String kategoriProduk = t_kategori.getText();
        String hargaProduk = t_harga.getText();
        String stokProduk = t_stok.getText();
        String deskripsiProduk = t_deskripsi.getText();

        // Ambil nama gambar dari JLabel (menggunakan toolTipText)
        String imageFileName = tGambarProduk.getToolTipText();

        // Validasi untuk memastikan semua kolom diisi
        if (nmProduk.isEmpty() || kategoriProduk.isEmpty() || hargaProduk.isEmpty() || stokProduk.isEmpty() || deskripsiProduk.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua Kolom Harus Diisi!", "Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (idUser != 0) {
            try {
                // Query untuk memeriksa apakah data toko sudah ada untuk pengguna dengan idUser
                String checkSql = "SELECT toko_id FROM toko WHERE pemilik_id = ?";
                PreparedStatement checkSt = conn.prepareStatement(checkSql);
                checkSt.setInt(1, idUser);

                ResultSet rs = checkSt.executeQuery();

                if (rs.next()) {
                    int tokoId = rs.getInt("toko_id");  // Ambil toko_id dari hasil query

                    // Query untuk menyimpan data produk
                    String sql = "INSERT INTO produk (toko_id, nama_produk, kategori, harga, stok, deskripsi, gambar) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, tokoId);               // toko_id diambil dari hasil query sebelumnya
                    ps.setString(2, nmProduk);           // Nama produk
                    ps.setString(3, kategoriProduk);     // Kategori produk
                    ps.setString(4, hargaProduk);        // Harga produk
                    ps.setInt(5, Integer.parseInt(stokProduk)); // Stok produk
                    ps.setString(6, deskripsiProduk);    // Deskripsi produk
                    ps.setString(7, imageFileName);      // Menyimpan nama gambar

                    int rowsInserted = ps.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(this, "Produk berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        resetForm();  // Mengosongkan form setelah penyimpanan
                        getData(idUser);
                    }

                    ps.close();  // Menutup PreparedStatement untuk produk
                } else {
                    JOptionPane.showMessageDialog(this, "Toko tidak ditemukan untuk pengguna ini. Tidak dapat menyimpan produk.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                checkSt.close();  // Menutup PreparedStatement untuk pengecekan toko
                rs.close();       // Menutup ResultSet

            } catch (Exception e) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, e);
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data produk!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "ID pengguna tidak ditemukan. Tidak dapat menyimpan data.", "Error", JOptionPane.ERROR_MESSAGE);
        }




    }//GEN-LAST:event_btnTambahProdukActionPerformed

    private void tDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tDataMouseClicked

        int selectedRow = tData.getSelectedRow();
        if (selectedRow != -1) {
            // Ambil produk_id dari baris yang dipilih
            int produkId = Integer.parseInt(tData.getValueAt(selectedRow, 0).toString());

            // Ambil data lain yang sudah ada di tabel
            String namaProduk = tData.getValueAt(selectedRow, 2).toString();
            String kategoriProduk = tData.getValueAt(selectedRow, 3).toString();
            String hargaProduk = tData.getValueAt(selectedRow, 4).toString();
            String stokProduk = tData.getValueAt(selectedRow, 5).toString();

            // Menampilkan data ke form input
            t_produk.setText(namaProduk);
            t_kategori.setText(kategoriProduk);
            t_harga.setText(hargaProduk);
            t_stok.setText(stokProduk);

            try {
                // Query untuk mendapatkan deskripsi berdasarkan produk_id
                String sql = "SELECT deskripsi, gambar FROM produk WHERE produk_id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, produkId);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String GambarProduk = rs.getString("gambar");
                    // Set deskripsi pada text field
                    t_deskripsi.setText(rs.getString("deskripsi"));
                    if(GambarProduk != null){
                        String imagePath = "src/img/" + GambarProduk; // Path gambar di folder 'img/'

                        // Menampilkan gambar di JLabel
                        ImageIcon imageIcon = new ImageIcon(imagePath);
                        Image image = imageIcon.getImage();
                        Image scaledImage = image.getScaledInstance(tgambar.getWidth(), tgambar.getHeight(), Image.SCALE_SMOOTH);
                        tGambarProduk.setIcon(new ImageIcon(scaledImage));
                    }
                } else {
                    t_deskripsi.setText("");  // Kosongkan jika tidak ditemukan
                    JOptionPane.showMessageDialog(this, "Deskripsi tidak ditemukan untuk produk ini.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Tutup resource setelah selesai
                rs.close();
                ps.close();
            } catch (Exception e) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, e);
                JOptionPane.showMessageDialog(this, "Gagal mengambil deskripsi produk.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_tDataMouseClicked

    private void btnEditProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProdukActionPerformed
                                             
        // TODO add your handling code here:
        int selectedRow = tData.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih Data Yang Ingin Diedit !!!.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String idProduk = tData.getValueAt(selectedRow, 0).toString();
        String nmProduk = t_produk.getText();
        String kategoriProduk = t_kategori.getText();
        String hargaProduk = t_harga.getText();
        String stokProduk = t_stok.getText();
        String deskripsiProduk = t_deskripsi.getText();

        // Nama file gambar baru
        String newImageFileName = tGambarProduk.getToolTipText(); // Retrieve the new image file name

        // Validasi untuk memastikan semua kolom diisi
        if (nmProduk.isEmpty() || kategoriProduk.isEmpty() || hargaProduk.isEmpty() || stokProduk.isEmpty() || deskripsiProduk.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua Kolom Harus Diisi!", "Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (idUser != 0) {
            try {
                // Ambil toko_id untuk pengguna yang sedang login
                String checkSql = "SELECT toko_id FROM toko WHERE pemilik_id = ?";
                PreparedStatement checkSt = conn.prepareStatement(checkSql);
                checkSt.setInt(1, idUser);
                ResultSet rs = checkSt.executeQuery();

                if (rs.next()) {
                    int tokoId = rs.getInt("toko_id");

                    // Ambil nama file gambar lama dari database
                    String oldImageFileName = null;
                    String getImageSql = "SELECT gambar FROM produk WHERE produk_id = ?";
                    PreparedStatement psGetImage = conn.prepareStatement(getImageSql);
                    psGetImage.setString(1, idProduk);
                    ResultSet rsImage = psGetImage.executeQuery();
                    if (rsImage.next()) {
                        oldImageFileName = rsImage.getString("gambar");
                    }
                    rsImage.close();
                    psGetImage.close();

                    // Jika gambar baru dipilih dan berbeda dengan gambar lama, hapus gambar lama dari direktori
                    if (newImageFileName != null && !newImageFileName.isEmpty() && !newImageFileName.equals(oldImageFileName)) {
                        if (oldImageFileName != null && !oldImageFileName.isEmpty()) {
                            File oldImageFile = new File("src/img/" + oldImageFileName);
                            if (oldImageFile.exists() && oldImageFile.isFile()) {
                                oldImageFile.delete();
                            }
                        }
                    }

                    // Update data produk termasuk gambar baru jika ada
                    String sql = "UPDATE produk SET toko_id = ?, nama_produk = ?, kategori = ?, harga = ?, stok = ?, deskripsi = ?, gambar = ? WHERE produk_id = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, tokoId);
                    ps.setString(2, nmProduk);
                    ps.setString(3, kategoriProduk);
                    ps.setString(4, hargaProduk);
                    ps.setInt(5, Integer.parseInt(stokProduk));
                    ps.setString(6, deskripsiProduk);

                    // Jika ada gambar baru, set gambar baru, jika tidak biarkan gambar lama
                    if (newImageFileName != null && !newImageFileName.isEmpty()) {
                        ps.setString(7, newImageFileName);
                    } else {
                        ps.setNull(7, java.sql.Types.VARCHAR);
                    }

                    ps.setString(8, idProduk);

                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(this, "Produk berhasil diperbaharui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        resetForm();  // Mengosongkan form setelah penyimpanan
                        getData(idUser);  // Update data produk setelah perubahan
                    }

                    ps.close();
                } else {
                    JOptionPane.showMessageDialog(this, "Toko tidak ditemukan untuk pengguna ini. Tidak dapat menyimpan produk.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                checkSt.close();
                rs.close();

            } catch (Exception e) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, e);
                JOptionPane.showMessageDialog(this, "Gagal memperbaharui data produk!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "ID pengguna tidak ditemukan. Tidak dapat menyimpan data.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    
    }//GEN-LAST:event_btnEditProdukActionPerformed

    private void btnHapusProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusProdukActionPerformed
        // TODO add your handling code here:
        int selectedRow = tData.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih Data Yang Ingin Dihapus !!!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda Yakin Akan Menghapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String idProduk = tData.getValueAt(selectedRow, 0).toString();

            try {
                // Ambil nama file gambar dari database berdasarkan idProduk
                String sqlSelect = "SELECT gambar FROM produk WHERE produk_id = ?";
                PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
                psSelect.setString(1, idProduk);
                ResultSet rs = psSelect.executeQuery();

                String namaFileGambar = null;
                if (rs.next()) {
                    namaFileGambar = rs.getString("gambar");  // Kolom "gambar" berisi nama file gambar
                }
                rs.close();
                psSelect.close();

                // Jika nama file gambar tidak kosong, hapus file dari direktori
                if (namaFileGambar != null && !namaFileGambar.isEmpty()) {
                    File fileGambar = new File("src/img/" + namaFileGambar);
                    if (fileGambar.exists() && fileGambar.isFile()) {
                        fileGambar.delete();
                    }
                }

                // Query untuk menghapus data produk
                String sqlDelete = "DELETE FROM produk WHERE produk_id=?";
                PreparedStatement psDelete = conn.prepareStatement(sqlDelete);
                psDelete.setString(1, idProduk);

                int rowDelete = psDelete.executeUpdate();

                if (rowDelete > 0) {
                    JOptionPane.showMessageDialog(this, "Produk berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    resetForm();  // Mengosongkan form setelah penyimpanan
                    getData(idUser);
                }
                psDelete.close();

            } catch (Exception e) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, e);
                JOptionPane.showMessageDialog(this, "Gagal Menghapus data produk!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnHapusProdukActionPerformed

    private void tcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tcariKeyTyped
                                      
        DefaultTableModel model = (DefaultTableModel) tData.getModel();
        model.setRowCount(0);
        PreparedStatement ps;
        ResultSet rs;

        String cari = tcari.getText();
        int idUser = this.idUser;

        try {
            String sql = "SELECT p.produk_id, p.nama_produk, p.harga, p.kategori, p.stok, t.nama_toko " +
                         "FROM produk p " +
                         "JOIN toko t ON p.toko_id = t.toko_id " +
                         "JOIN users u ON t.pemilik_id = u.id " +
                         "WHERE u.id = ? AND (p.nama_produk LIKE ? OR t.nama_toko LIKE ? OR p.kategori LIKE ?)";
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idUser);
            ps.setString(2, "%" + cari + "%");
            ps.setString(3, "%" + cari + "%");
            ps.setString(4, "%" + cari + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                int idProduk = rs.getInt("produk_id");
                String namaToko = rs.getString("nama_toko");
                String namaProduk = rs.getString("nama_produk");
                String hargaProduk = rs.getString("harga");
                String kategoriProduk = rs.getString("kategori");
                int stokProduk = rs.getInt("stok");

                Object[] rowData = {idProduk, namaToko, namaProduk, kategoriProduk, hargaProduk, stokProduk};
                model.addRow(rowData);
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            Logger.getLogger(HomePemilik.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_tcariKeyTyped

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        Login lg = new Login();
        lg.setVisible(true);
        lg.pack();
        this.dispose();        
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void pilihGambarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihGambarActionPerformed
        // Membuka JFileChooser untuk memilih gambar
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Menampilkan gambar di JLabel
            ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            Image image = imageIcon.getImage();

            // Menyesuaikan gambar agar memenuhi label tanpa merusak rasio aspek
            Image scaledImage = image.getScaledInstance(tgambar.getWidth(), tgambar.getHeight(), Image.SCALE_SMOOTH);
            tgambar.setIcon(new ImageIcon(scaledImage));

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
    }//GEN-LAST:event_pilihGambarActionPerformed

    private void btnGambarProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGambarProdukActionPerformed

        // Membuka JFileChooser untuk memilih gambar
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Menampilkan gambar di JLabel
            ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
            Image image = imageIcon.getImage();

            // Menyesuaikan gambar agar memenuhi label tanpa merusak rasio aspek
            Image scaledImage = image.getScaledInstance(tGambarProduk.getWidth(), tGambarProduk.getHeight(), Image.SCALE_SMOOTH);
            tGambarProduk.setIcon(new ImageIcon(scaledImage));

            // Membuat nama file unik (menggunakan UUID atau timestamp)
            String uniqueFileName = generateUniqueFileName(selectedFile.getName());

            // Menyimpan gambar ke folder img dengan nama baru
            String targetPath = "src/img/" + uniqueFileName;
            File targetFile = new File(targetPath);
            try {
                // Salin gambar ke folder img
                copyFile(selectedFile, targetFile);
                tGambarProduk.setToolTipText(targetFile.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan gambar: " + ex.getMessage());
            }
        }
        
    }//GEN-LAST:event_btnGambarProdukActionPerformed
    
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
        int idUser = this.idUser;
        try{
            String sql = "UPDATE toko SET logo=? WHERE pemilik_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set parameter untuk nama gambar
            stmt.setString(1, imageName);
            stmt.setInt(2, idUser);
            int gambar = stmt.executeUpdate();
            if(gambar > 0){
                JOptionPane.showMessageDialog(null, "Gambar berhasil disimpan!");
                userLogin();                
            }    
            stmt.close();            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
        }
    }
    
    private void resetForm() {
        // Reset form input setelah data disimpan
        t_produk.setText("");
        t_kategori.setText("");
        t_harga.setText("");
        t_stok.setText("");
        t_deskripsi.setText("");
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
            java.util.logging.Logger.getLogger(HomePemilik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePemilik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePemilik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePemilik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePemilik().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Lemail;
    private javax.swing.JLabel Lnama;
    private javax.swing.JLabel Lrole;
    private javax.swing.JLabel Luname;
    private javax.swing.JButton btnEditProduk;
    private javax.swing.JButton btnGambarProduk;
    private javax.swing.JButton btnHapusProduk;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambahProduk;
    private javax.swing.JLabel btnUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel nm_user;
    private javax.swing.JTable pesanData;
    private javax.swing.JButton pilihGambar;
    private javax.swing.JTable tData;
    private javax.swing.JLabel tGambarProduk;
    private javax.swing.JTextField t_deskripsi;
    private javax.swing.JTextField t_harga;
    private javax.swing.JTextField t_kategori;
    private javax.swing.JTextField t_produk;
    private javax.swing.JTextField t_stok;
    private javax.swing.JTabbedPane tbdPemilik;
    private javax.swing.JTextField tcari;
    private javax.swing.JTextField tdeskripsi;
    private javax.swing.JLabel tgambar;
    private javax.swing.JTextField ttoko;
    public javax.swing.JLabel welcome;
    public javax.swing.JLabel welcome1;
    // End of variables declaration//GEN-END:variables

}
