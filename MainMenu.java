package vinylstore;

public class MainMenu extends javax.swing.JFrame {

    
    public MainMenu() {
        initComponents();
        //open the frame 
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
    
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        itemsLabel = new javax.swing.JLabel();
        genreLabel = new javax.swing.JLabel();
        terminalLabel = new javax.swing.JLabel();
        accountsLabel = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(606, 506));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));

        jLabel15.setFont(new java.awt.Font("Raleway Black", 0, 48)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Vinyl Store");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(jLabel15)
                .addContainerGap(176, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel15)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Users");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Items");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Genre");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Accounts");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 350, -1, -1));

        userLabel.setBackground(new java.awt.Color(255, 255, 255));
        userLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vinylstore/images/icons8-users-96.png"))); // NOI18N
        userLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        userLabel.setOpaque(true);
        userLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userLabelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                userLabelMousePressed(evt);
            }
        });
        jPanel2.add(userLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 140, 120));

        itemsLabel.setBackground(new java.awt.Color(255, 255, 255));
        itemsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemsLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vinylstore/images/icons8-vinyl-disc-100.png"))); // NOI18N
        itemsLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        itemsLabel.setOpaque(true);
        itemsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemsLabelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                itemsLabelMousePressed(evt);
            }
        });
        jPanel2.add(itemsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 140, 120));

        genreLabel.setBackground(new java.awt.Color(255, 255, 255));
        genreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        genreLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vinylstore/images/icons8-musical-notes-100.png"))); // NOI18N
        genreLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        genreLabel.setOpaque(true);
        genreLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                genreLabelMousePressed(evt);
            }
        });
        jPanel2.add(genreLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 140, 120));

        terminalLabel.setBackground(new java.awt.Color(255, 255, 255));
        terminalLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        terminalLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vinylstore/images/icons8-transaction-100.png"))); // NOI18N
        terminalLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        terminalLabel.setOpaque(true);
        terminalLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                terminalLabelMousePressed(evt);
            }
        });
        jPanel2.add(terminalLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 140, 120));

        accountsLabel.setBackground(new java.awt.Color(255, 255, 255));
        accountsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        accountsLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vinylstore/images/icons8-account-100.png"))); // NOI18N
        accountsLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        accountsLabel.setOpaque(true);
        accountsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                accountsLabelMousePressed(evt);
            }
        });
        jPanel2.add(accountsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 140, 120));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Terminal");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, 80, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 610, 410));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void userLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userLabelMouseClicked

    }//GEN-LAST:event_userLabelMouseClicked

    private void userLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userLabelMousePressed
        this.setVisible(false); // open user frame
        new Users();
    }//GEN-LAST:event_userLabelMousePressed

    private void genreLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_genreLabelMousePressed
        this.setVisible(false); // open genre frame
        new Genre();
    }//GEN-LAST:event_genreLabelMousePressed

    private void itemsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemsLabelMouseClicked
       
    }//GEN-LAST:event_itemsLabelMouseClicked

    private void itemsLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemsLabelMousePressed
        this.setVisible(false); // open items frame
        new items();
    }//GEN-LAST:event_itemsLabelMousePressed

    private void accountsLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountsLabelMousePressed
        this.setVisible(false); // open accounts frame
        new Accounts();
    }//GEN-LAST:event_accountsLabelMousePressed

    private void terminalLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_terminalLabelMousePressed
        this.setVisible(false); // open terminal frame
        new Terminal();
    }//GEN-LAST:event_terminalLabelMousePressed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accountsLabel;
    private javax.swing.JLabel genreLabel;
    private javax.swing.JLabel itemsLabel;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel terminalLabel;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
