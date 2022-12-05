package vinylstore;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Users extends javax.swing.JFrame {
    
    int selectedIndex=0;
    
    String ID,uname,usurname,udob,uaddress,ucard,ustatus,userName,password,sql;
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    Date date;
    
    //declare the variables
    //declare connections
    java.sql.Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement ps;
        
    String msAccDB = "vinylstore.accdb"; // path to the DB file
    String dbURL = "jdbc:ucanaccess://" + msAccDB;//Database url
  
    public Users() {
        initComponents();
        
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        updateTable();
    }
    
    
    
    private void updateTable(){
        
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);
            //Creating JDBC Statement
            st = conn.createStatement();
            
            sql = "SELECT * FROM Users";
            rs = st.executeQuery(sql);//execute the statement
            
            DefaultTableModel model = (DefaultTableModel)usersTable.getModel();
            
            model.setRowCount(0);
         
            if(rs.next()){
                
                do{
                    ID = rs.getString("ID");
                    uname = rs.getString("Name");
                    usurname = rs.getString("Surname");
                    udob = rs.getString("Date_of_birth");
                    uaddress = rs.getString("Address");
                    ucard = rs.getString("Card");
                    ustatus = rs.getString("Status");
                    userName = rs.getString("username");
                    password = rs.getString("password");
                
                    model.addRow(new Object[]{ID,uname,usurname,udob,uaddress,ucard,ustatus,userName,password});
                
                }while(rs.next());
                
            }
            
            rs.close();
            st.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        usersurname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        dob = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        address = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        card = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        status = new javax.swing.JComboBox<>();
        submitButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        editButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Surname", "DOB", "Address", "Card", "Status", "username", "password"
            }
        ));
        usersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usersTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(usersTable);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 650, 510));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 670, 530));

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));

        jLabel8.setFont(new java.awt.Font("Raleway Black", 0, 48)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Vinyl Store");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(375, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(366, 366, 366))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 80));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("User");

        jLabel3.setText("Name");

        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });

        jLabel2.setText("Surname");

        usersurname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersurnameActionPerformed(evt);
            }
        });

        jLabel5.setText("Date of birth");

        dob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dobActionPerformed(evt);
            }
        });

        jLabel4.setText("Address");

        address.setColumns(20);
        address.setRows(5);
        jScrollPane1.setViewportView(address);

        jLabel6.setText("Card");

        card.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardActionPerformed(evt);
            }
        });

        jLabel7.setText("Status ");

        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Manager", "Seller", "Customer" }));

        submitButton.setBackground(new java.awt.Color(204, 102, 0));
        submitButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        submitButton.setForeground(new java.awt.Color(255, 255, 255));
        submitButton.setText("Add");
        submitButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        deleteButton.setBackground(new java.awt.Color(153, 0, 0));
        deleteButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setText("Delete");
        deleteButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        jLabel9.setText("username");

        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });

        jLabel10.setText("password");

        editButton.setBackground(new java.awt.Color(0, 153, 153));
        editButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        editButton.setForeground(new java.awt.Color(255, 255, 255));
        editButton.setText("Edit");
        editButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(20, 20, 20)))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(username)
                            .addComponent(usersurname)
                            .addComponent(dob)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                            .addComponent(card)
                            .addComponent(status, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usersurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 320, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
//        uname = username.getText();
    }//GEN-LAST:event_usernameActionPerformed

    private void usersurnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersurnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usersurnameActionPerformed

    private void cardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cardActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
    
        //Get data from the username and surname fields
        uname = username.getText();
        usurname = usersurname.getText();
        
        //Check if the fields aren't empty
        if(uname.isEmpty() && usurname.isEmpty()){
            
            JOptionPane.showMessageDialog(this, "Insert the user data!");
            
        }else{
            addUser();  //Add user data
        
            saveToDB(); //Save user data to database
        }
        
        
        
    }//GEN-LAST:event_submitButtonActionPerformed

    private void dobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dobActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dobActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeWindow();//close the window
    }//GEN-LAST:event_formWindowClosing

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFieldActionPerformed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFieldActionPerformed

    private void usersTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usersTableMouseClicked
        
        //Select the user from the table
        initForm();
        
        DefaultTableModel model = (DefaultTableModel)usersTable.getModel();        
                
        selectedIndex = usersTable.getSelectedRow();
        
        try {
            username.setText(model.getValueAt(selectedIndex, 1).toString());
            usersurname.setText(model.getValueAt(selectedIndex, 2).toString());
            dob.setText(model.getValueAt(selectedIndex, 3).toString());
            address.setText(model.getValueAt(selectedIndex, 4).toString());
            card.setText(model.getValueAt(selectedIndex, 5).toString());
            status.setSelectedItem(model.getValueAt(selectedIndex, 6).toString());
            usernameField.setText(model.getValueAt(selectedIndex, 7).toString());
            passwordField.setText(model.getValueAt(selectedIndex, 8).toString());
        } catch (Exception e) {
            System.out.println("error: "+e);
        }

    }//GEN-LAST:event_usersTableMouseClicked

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        
        DefaultTableModel model = (DefaultTableModel)usersTable.getModel();        
                
        selectedIndex = usersTable.getSelectedRow();    // select row index
        
        int id = Integer.parseInt(model.getValueAt(selectedIndex, 0).toString());
        
        addUser(); // add the user from the table
        
        if(uname.isEmpty() && usurname.isEmpty()){
            
            JOptionPane.showMessageDialog(this, "The data from table is not selected");
            
            
        }else{
            // Update user data into the database
            // Opening database connection
            try {
                //Create and get connection using DriverManager class
                conn = DriverManager.getConnection(dbURL);

                //Creating JDBC Statement
                st = conn.createStatement();

                // Step 2.C: Executing SQL &amp; retrieve data into ResultSet

                sql = "UPDATE Users SET Name=?,Surname=?,Date_of_birth=?,Address=?,Card=?,Status=?,username=?,password=? WHERE ID=?";

                ps = conn.prepareStatement(sql);

                ps.setString(1, uname);
                ps.setString(2, usurname);
                ps.setString(3, udob);
                ps.setString(4, uaddress);
                ps.setString(5, ucard);
                ps.setString(6, ustatus);

                ps.setString(7, userName);
                ps.setString(8, password);
                ps.setInt(9, id);

                ps.executeUpdate();

                ps.close();
                st.close();
                conn.close();
                System.out.println("data was inserted");

                initForm(); // init the form

                updateTable(); // update table after inserting the data


            }
            catch(SQLException sqlex){
                System.err.println(sqlex.getMessage());
            }
            
            
        }
        
        
        
        
        
        
        
    }//GEN-LAST:event_editButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        
        deleteUser();
        
    }//GEN-LAST:event_deleteButtonActionPerformed

   
    
    // Delete user from the database
    private void deleteUser(){
        
        initForm();
        
        DefaultTableModel model = (DefaultTableModel)usersTable.getModel();        
                
        selectedIndex = usersTable.getSelectedRow();
        
        try {
            username.setText(model.getValueAt(selectedIndex, 1).toString());
            usersurname.setText(model.getValueAt(selectedIndex, 2).toString());
            dob.setText(model.getValueAt(selectedIndex, 3).toString());
            address.setText(model.getValueAt(selectedIndex, 4).toString());
            card.setText(model.getValueAt(selectedIndex, 5).toString());
            status.setSelectedItem(model.getValueAt(selectedIndex, 6).toString());
            usernameField.setText(model.getValueAt(selectedIndex, 7).toString());
            passwordField.setText(model.getValueAt(selectedIndex, 8).toString());
        } catch (Exception e) {
            System.out.println("error: "+e);
        }
        
        int id = Integer.parseInt(model.getValueAt(selectedIndex, 0).toString());
        
        // Opening database connection
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();

            // Step 2.C: Executing SQL &amp; retrieve data into ResultSet
             
            sql = "DELETE FROM Users WHERE ID=?";
            
            ps = conn.prepareStatement(sql);
            
            ps.setInt(1, id);

            ps.executeUpdate();
            
            ps.close();
            st.close();
            conn.close();
            System.out.println("data was inserted");
            
            initForm(); // init the form
            
            updateTable();
            

        }
        catch(SQLException sqlex){
            System.err.println(sqlex.getMessage());
        }
        
        
        
    }
    
    private void addUser(){
        
        
        uname = username.getText();
        usurname = usersurname.getText();
        udob = dob.getText();
        uaddress = address.getText();
        ucard = card.getText();
        ustatus = status.getSelectedItem().toString();
        userName = usernameField.getText();
        password = passwordField.getText();
        
        
    
    }
    
    

    
    private void saveToDB(){
        
        date = new Date();
        
        System.out.println("username: "+uname+"\n surname: "+usurname+"\n dob: "+udob+"\n address: "+uaddress+"\n card: "+ucard+"\n status: "+ustatus);
        
        
        // Opening database connection
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();

            // Step 2.C: Executing SQL &amp; retrieve data into ResultSet
             
            String sql = "INSERT INTO Users (Name,Surname,Date_of_birth,Address,Card,Status,Date_created,username,password) VALUES (?,?,?,?,?,?,?,?,?)";
            
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, uname);
            ps.setString(2, usurname);
            ps.setString(3, udob);
            ps.setString(4, uaddress);
            ps.setString(5, ucard);
            ps.setString(6, ustatus);
            ps.setString(7, formatter.format(date));
            ps.setString(8, userName);
            ps.setString(9, password);

            ps.executeUpdate();
            
            ps.close();
            st.close();
            conn.close();
            System.out.println("data was inserted");
            
            initForm(); // init the fields
            
            updateTable(); // update table from database
            

        }
        catch(SQLException sqlex){
            System.err.println(sqlex.getMessage());
        }
  
    }
    
    // Initialize the form fields
    private void initForm(){
        
        username.setText("");
        usersurname.setText("");
        dob.setText("");
        address.setText("");
        card.setText("");
        usernameField.setText("");
        passwordField.setText("");
        
    }
    
    // close the window
    private void closeWindow(){
        this.setVisible(false);
        new MainMenu(); // come back to the main menu
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea address;
    private javax.swing.JTextField card;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField dob;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField passwordField;
    private javax.swing.JComboBox<String> status;
    private javax.swing.JButton submitButton;
    private javax.swing.JTextField username;
    private javax.swing.JTextField usernameField;
    private javax.swing.JTable usersTable;
    private javax.swing.JTextField usersurname;
    // End of variables declaration//GEN-END:variables
}
