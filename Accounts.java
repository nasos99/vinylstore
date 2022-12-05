
package vinylstore;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Accounts extends javax.swing.JFrame {

    String sql,ID,firstname,lastname,dob,address,card,accountnumber;
    Double balance;
    int selectedIndex=0;
    
    java.sql.Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement ps;
        
    String msAccDB = "vinylstore.accdb"; // path to the DB file
    String dbURL = "jdbc:ucanaccess://" + msAccDB;//Database url
    
    
    public Accounts() {
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        initForm(); // init the account field form
        updateTable(); // update table data from database
    }

    // Initialization of form fields
    private void initForm() {
        
        firstnameField.setText("");
        lastnameField.setText("");
        dobField.setText("");
        addressField.setText("");
        cardField.setText("");
        accnumberField.setText("");
        balanceField.setText("");
    }
    
    //update main table from database
    private void updateTable(){
        
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);
            //Creating JDBC Statement
            st = conn.createStatement();
            
            sql = "SELECT * FROM Accounts";
            rs = st.executeQuery(sql);//execute the statement
            
            DefaultTableModel model = (DefaultTableModel)accountsTable.getModel();
            
            model.setRowCount(0);
         
            if(rs.next()){
                
                do{
                    ID = rs.getString("ID");
                    firstname = rs.getString("First_Name");
                    lastname = rs.getString("Last_Name");
                    dob = rs.getString("Date_of_birth");
                    address = rs.getString("Address");
                    card = rs.getString("Card");
                    accountnumber = rs.getString("Account_Number");
                    balance = rs.getDouble("Balance");
                   
                
                    model.addRow(new Object[]{ID, firstname, lastname, dob, address, card, accountnumber, balance});
                
                }while(rs.next());
                
            }
            
            rs.close();
            st.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     // Get data from the form
     private void getInsertData(){
        firstname=firstnameField.getText();
        lastname=lastnameField.getText();
        dob=dobField.getText();
        address = addressField.getText();
        card=cardField.getText();
        accountnumber=accnumberField.getText();
        
        if(balanceField.getText().isEmpty())balance = 0.0;
        else balance=Double.parseDouble(balanceField.getText());
        
    }
    
    // Insert new account record into database
    private void addAccounts(){
        getInsertData();
        
        // Check if the form fields are empty
        if(firstname.isEmpty()||lastname.isEmpty()||dob.isEmpty()||address.isEmpty()||card.isEmpty()||accountnumber.isEmpty()){
            JOptionPane.showMessageDialog(this, "Insert the account details");
        }
        
        else{
             // Insert new account into database
             // Opening database connection
            try {
                //Create and get connection using DriverManager class
                conn = DriverManager.getConnection(dbURL);

                //Creating JDBC Statement
                st = conn.createStatement();

                // Step 2.C: Executing SQL &amp; retrieve data into ResultSet

                sql = "INSERT INTO Accounts (First_Name, Last_Name, Date_of_birth, Address, Card, Account_Number, Balance) VALUES (?,?,?,?,?,?,?)";

                ps = conn.prepareStatement(sql);

                ps.setString(1, firstname);
                ps.setString(2, lastname);
                ps.setString(3, dob);
                ps.setString(4, address);
                ps.setString(5, card);
                ps.setString(6, accountnumber);
                ps.setDouble(7, balance);

                ps.executeUpdate();

                ps.close();
                st.close();
                conn.close();
                System.out.println("data was inserted");

                initForm(); // init the form

                updateTable(); // Update the account table from the database


            }
            catch(SQLException sqlex){
                System.err.println(sqlex.getMessage());
            }
        }
        
       
    }
    
    // Get data from selected row of the account table
    private void getAccountFromTable() {
        initForm(); 
        
        DefaultTableModel model = (DefaultTableModel)accountsTable.getModel();        
                
        selectedIndex = accountsTable.getSelectedRow();
        
        try {
            firstnameField.setText(model.getValueAt(selectedIndex, 1).toString());
            lastnameField.setText(model.getValueAt(selectedIndex, 2).toString());
            dobField.setText(model.getValueAt(selectedIndex, 3).toString());
            addressField.setText(model.getValueAt(selectedIndex, 4).toString());
            cardField.setText(model.getValueAt(selectedIndex, 5).toString());
            accnumberField.setText(model.getValueAt(selectedIndex, 6).toString());
            balanceField.setText(model.getValueAt(selectedIndex, 7).toString());
        } catch (Exception e) {
            System.out.println("error: "+e);
        }
    }
    
    
    
    
    
    // Update account details to database
    private void editAccounts() {
        DefaultTableModel model = (DefaultTableModel)accountsTable.getModel();        
                
        selectedIndex = accountsTable.getSelectedRow();
        
        int id = Integer.parseInt(model.getValueAt(selectedIndex, 0).toString());
        
        getInsertData();
        
        // Opening database connection
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();

            // Step 2.C: Executing SQL &amp; retrieve data into ResultSet
             
            sql = "UPDATE Accounts SET First_Name=?,Last_Name=?,Date_of_birth=?,"
                    + "Address=?,Card=?,Account_Number=?,Balance=? WHERE ID=?";
            
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, dob);
            ps.setString(4, address);
            ps.setString(5, card);
            ps.setString(6, accountnumber);
            ps.setDouble(7, balance);
            ps.setInt(8, id);
            
            ps.executeUpdate();
            
            ps.close();
            st.close();
            conn.close();
            System.out.println("data was updated");
            
            initForm(); // init the form
            
            updateTable(); // update account table from database
            

        }
        catch(SQLException sqlex){
            System.err.println(sqlex.getMessage());
        }
    }
    
    
    // delete account record from database
    private void removeAccount(){
        initForm();
        
        DefaultTableModel model = (DefaultTableModel)accountsTable.getModel();        
                
        selectedIndex = accountsTable.getSelectedRow();
        
        getAccountFromTable();
        
        int id = Integer.parseInt(model.getValueAt(selectedIndex, 0).toString());
        
        // Opening database connection
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();

            // Step 2.C: Executing SQL &amp; retrieve data into ResultSet
             
            sql = "DELETE FROM Accounts WHERE ID=?";
            
            ps = conn.prepareStatement(sql);
            
            ps.setInt(1, id);

            ps.executeUpdate();
            
            ps.close();
            st.close();
            conn.close();
            System.out.println("data was inserted");
            
            initForm(); // init the form
            
            updateTable(); // update account table
            

        }
        catch(SQLException sqlex){
            System.err.println(sqlex.getMessage());
        }
        
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        firstnameField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lastnameField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cardField = new javax.swing.JTextField();
        accnumberField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        balanceField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        dobField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        addressField = new javax.swing.JTextArea();
        editButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        accountsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Raleway Black", 0, 48)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Vinyl Store");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 24, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 100));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        firstnameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstnameFieldActionPerformed(evt);
            }
        });
        jPanel2.add(firstnameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 158, -1));

        jLabel7.setText("First Name");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, 92, 22));

        jLabel8.setText("Last Name");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 32, 92, 22));

        lastnameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastnameFieldActionPerformed(evt);
            }
        });
        jPanel2.add(lastnameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 34, 158, -1));

        jLabel9.setText("Date of birth");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 68, 92, 22));

        cardField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardFieldActionPerformed(evt);
            }
        });
        jPanel2.add(cardField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 158, -1));

        accnumberField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accnumberFieldActionPerformed(evt);
            }
        });
        jPanel2.add(accnumberField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 158, -1));

        jLabel10.setText("Card");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 92, 22));

        balanceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceFieldActionPerformed(evt);
            }
        });
        jPanel2.add(balanceField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, 160, -1));

        jLabel12.setText("Balance");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 92, 22));

        addButton.setText("Add");
        addButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        jPanel2.add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 80, 20));

        removeButton.setText("Remove");
        removeButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        jPanel2.add(removeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, 80, 20));

        jLabel11.setText("Account Number");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 92, 22));

        jLabel13.setText("Address");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 92, 22));

        dobField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dobFieldActionPerformed(evt);
            }
        });
        jPanel2.add(dobField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 68, 158, -1));

        addressField.setColumns(20);
        addressField.setRows(5);
        jScrollPane2.setViewportView(addressField);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 190, 100));

        editButton.setText("Edit");
        editButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        jPanel2.add(editButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 80, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 118, 320, 430));

        accountsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "First Name", "Last Name", "Date of birth", "Address", "Card", "Account Number", "Balance"
            }
        ));
        accountsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                accountsTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(accountsTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(331, 118, 720, 330));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        
        addAccounts();
    }//GEN-LAST:event_addButtonActionPerformed

    private void firstnameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstnameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstnameFieldActionPerformed

    private void lastnameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastnameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastnameFieldActionPerformed

    private void cardFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cardFieldActionPerformed

    private void accnumberFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accnumberFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accnumberFieldActionPerformed

    private void balanceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_balanceFieldActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.setVisible(false);
        new MainMenu();
    }//GEN-LAST:event_formWindowClosing

    private void dobFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dobFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dobFieldActionPerformed

    private void accountsTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accountsTableMousePressed
        getAccountFromTable();
    }//GEN-LAST:event_accountsTableMousePressed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        editAccounts();
    }//GEN-LAST:event_editButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        removeAccount();
    }//GEN-LAST:event_removeButtonActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField accnumberField;
    private javax.swing.JTable accountsTable;
    private javax.swing.JButton addButton;
    private javax.swing.JTextArea addressField;
    private javax.swing.JTextField balanceField;
    private javax.swing.JTextField cardField;
    private javax.swing.JTextField dobField;
    private javax.swing.JButton editButton;
    private javax.swing.JTextField firstnameField;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lastnameField;
    private javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables
}
