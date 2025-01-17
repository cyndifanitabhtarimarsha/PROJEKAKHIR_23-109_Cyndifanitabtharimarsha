/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projectakhir;

import config.Koneksi;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author ASUS
 */
public class Register extends javax.swing.JFrame {

    private Connection conn;
    
    /**
     * Creates new form Login
     */
    public Register() {
        initComponents();
        showPass.setVisible(false);
        conn = Koneksi.getConnection();
//        getData();
           
    }
    
//    private void getData() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
    
    
    // Tampilan Konten
    void bersih(){
        t_nama.setText("nama");
        t_username.setText("username");
        t_email.setText("email");
        t_pass.setText("password");
    }
    
    public boolean cekUsername(String username)
    {
        PreparedStatement ps;
        ResultSet rs;
        boolean cekUser = false;
        String sql = "SELECT * FROM users WHERE username =?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            
            rs = ps.executeQuery();
            if(rs.next()){
                cekUser = true;
            }
        } catch (Exception e) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE,null, e);
        }
        return cekUser;
        
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
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        showPass = new javax.swing.JLabel();
        hidePass = new javax.swing.JLabel();
        btn_register = new javax.swing.JButton();
        t_nama = new javax.swing.JTextField();
        t_username = new javax.swing.JTextField();
        t_email = new javax.swing.JTextField();
        t_role = new javax.swing.JComboBox<>();
        t_pass = new javax.swing.JPasswordField();
        t_jk = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/login.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("R E G I S T E R");
        jLabel2.setToolTipText("");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, -1, -1));

        jLabel1.setText("Sudah Punya Akun ?");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 400, -1, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Login");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 400, -1, -1));

        showPass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        showPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/buka.png"))); // NOI18N
        showPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showPassMouseClicked(evt);
            }
        });
        jPanel1.add(showPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, -1, 30));

        hidePass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hidePass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/hidden.png"))); // NOI18N
        hidePass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hidePassMouseClicked(evt);
            }
        });
        jPanel1.add(hidePass, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, -1, 30));

        btn_register.setBackground(new java.awt.Color(51, 51, 255));
        btn_register.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_register.setForeground(new java.awt.Color(255, 255, 255));
        btn_register.setText("REGISTER");
        btn_register.setBorder(null);
        btn_register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registerActionPerformed(evt);
            }
        });
        jPanel1.add(btn_register, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, 200, 30));

        t_nama.setForeground(new java.awt.Color(0, 51, 204));
        t_nama.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_nama.setText("nama");
        t_nama.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        t_nama.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_namaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_namaFocusLost(evt);
            }
        });
        jPanel1.add(t_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 200, 30));

        t_username.setForeground(new java.awt.Color(0, 51, 204));
        t_username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_username.setText("username");
        t_username.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        t_username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_usernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_usernameFocusLost(evt);
            }
        });
        t_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_usernameActionPerformed(evt);
            }
        });
        jPanel1.add(t_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 200, 30));

        t_email.setForeground(new java.awt.Color(0, 51, 204));
        t_email.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_email.setText("email");
        t_email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        t_email.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_emailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_emailFocusLost(evt);
            }
        });
        jPanel1.add(t_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 200, 30));

        t_role.setForeground(new java.awt.Color(0, 51, 204));
        t_role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "pemilik_toko", "pelanggan" }));
        t_role.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        jPanel1.add(t_role, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 200, 30));

        t_pass.setForeground(new java.awt.Color(0, 51, 204));
        t_pass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_pass.setText("password");
        t_pass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        t_pass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_passFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_passFocusLost(evt);
            }
        });
        jPanel1.add(t_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 200, 30));

        t_jk.setForeground(new java.awt.Color(0, 51, 204));
        t_jk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "laki-laki", "perempuan" }));
        t_jk.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        jPanel1.add(t_jk, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 200, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 460));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registerActionPerformed
        // TODO add your handling code here:
        String nama = t_nama.getText();
        String jk = t_jk.getSelectedItem().toString();
        String role = t_role.getSelectedItem().toString();
        String username = t_username.getText();
        String email = t_email.getText();
        String password = String.valueOf(t_pass.getPassword());
        
        if(nama.isEmpty() || jk.isEmpty() || role.isEmpty() || username.isBlank() || email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this, "Semua Kolom Harus Diisi !", "Validasi", JOptionPane.ERROR_MESSAGE);
            return;
        }else if(cekUsername(username)){
            JOptionPane.showMessageDialog(this, "Username Sudah Ada !", "Validasi", JOptionPane.ERROR_MESSAGE);
        }
        try{
            String sql = "INSERT INTO users(nama, jk, username, email, password, role) VALUES (?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, nama);
            st.setString(2, jk);
            st.setString(3, username);
            st.setString(4, email);
            st.setString(5, password);
            st.setString(6, role);
            
            int rowInserted = st.executeUpdate();
            
            if(rowInserted > 0 ){
                JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan !");
                resetForm();
            }
            st.close();
        }catch(Exception e){
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE,null, e);
        }
    }//GEN-LAST:event_btn_registerActionPerformed
    
    private void resetForm() {
        t_nama.setText("");
        t_username.setText("");
        t_email.setText("");
        t_pass.setText("");     
        
    }
    
    private void t_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_usernameActionPerformed

    private void t_namaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_namaFocusGained
        // TODO add your handling code here:
        String nama = t_nama.getText();
        if(nama.equals("nama")){
            t_nama.setText("");
            
        }
    }//GEN-LAST:event_t_namaFocusGained

    private void t_namaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_namaFocusLost
        // TODO add your handling code here:
        String nama = t_nama.getText();
        if(nama.equals("") || nama.equals("nama")){
            t_nama.setText("nama");
        }
    }//GEN-LAST:event_t_namaFocusLost

    private void t_usernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_usernameFocusGained
        // TODO add your handling code here:
        String username = t_username.getText();
        if(username.equals("username")){
            t_username.setText("");            
        }
    }//GEN-LAST:event_t_usernameFocusGained

    private void t_usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_usernameFocusLost
        // TODO add your handling code here:
        String username = t_username.getText();
        if(username.equals("") || username.equals("username")){
            t_username.setText("username");
        }
    }//GEN-LAST:event_t_usernameFocusLost

    private void t_passFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_passFocusGained
        // TODO add your handling code here:
        String pass = String.valueOf(t_pass.getPassword());
        if(pass.equals("password")){
            t_pass.setText("");            
        }
    }//GEN-LAST:event_t_passFocusGained

    private void t_passFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_passFocusLost
        // TODO add your handling code here:
        String pass = String.valueOf(t_pass.getPassword());
        if(pass.equals("") || pass.equals("password")){
            t_pass.setText("password");
        }
    }//GEN-LAST:event_t_passFocusLost

    private void showPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showPassMouseClicked
        // TODO add your handling code here:
        showPass.setVisible(false);
        hidePass.setVisible(true);
        t_pass.setEchoChar((char) 0);
    }//GEN-LAST:event_showPassMouseClicked

    private void hidePassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hidePassMouseClicked
        // TODO add your handling code here:
        hidePass.setVisible(false);
        showPass.setVisible(true);        
        t_pass.setEchoChar('*');
    }//GEN-LAST:event_hidePassMouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        Login lgn = new Login();
        lgn.setVisible(true);
        lgn.pack();
        this.dispose();        
    }//GEN-LAST:event_jLabel3MouseClicked

    private void t_emailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_emailFocusLost
        // TODO add your handling code here:
        String email = t_email.getText();
        if(email.equals("") || email.equals("email")){
            t_email.setText("email");
        }
    }//GEN-LAST:event_t_emailFocusLost

    private void t_emailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_emailFocusGained
        // TODO add your handling code here:
        String email = t_email.getText();
        if(email.equals("email")){
            t_email.setText("");
        }
    }//GEN-LAST:event_t_emailFocusGained

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
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Register().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_register;
    private javax.swing.JLabel hidePass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel showPass;
    private javax.swing.JTextField t_email;
    private javax.swing.JComboBox<String> t_jk;
    private javax.swing.JTextField t_nama;
    private javax.swing.JPasswordField t_pass;
    private javax.swing.JComboBox<String> t_role;
    private javax.swing.JTextField t_username;
    // End of variables declaration//GEN-END:variables




}
