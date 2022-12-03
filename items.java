package vinylstore;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class items extends javax.swing.JFrame {

    
    String genre, ID, productName, author, publicationDate, musicStyle, sql;
    
    Integer row, span, shelf;
    
    int selectedIndex=0, quantity=0;
    
    double price=0.0;
    
    
    java.sql.Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement ps;
        
    String msAccDB = "vinylstore.accdb"; // path to the DB file
    String dbURL = "jdbc:ucanaccess://" + msAccDB;//Database url
   
    public items() {
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        initForm();
        updateTable();
                
    }

    private void getInsertData(){
        productName=productnameField.getText();
        author=authorField.getText();
        publicationDate=publicationdateField.getText();
        quantity = Integer.parseInt(quantityField.getText());
        price=Double.parseDouble(priceField.getText());
        musicStyle=genreComboBox.getSelectedItem().toString();
        row=Integer.parseInt(rowField.getText());
        span=Integer.parseInt(spanField.getText());
        shelf=Integer.parseInt(shelfField.getText());
        
    }
    
    private void addNewItem(){
        getInsertData();
        
        // Opening database connection
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();

            // Step 2.C: Executing SQL &amp; retrieve data into ResultSet
             
            sql = "INSERT INTO Items (Product_name, Author, Publication_date, Quantity, Price, Music_style, Row, Span, Shelf) VALUES (?,?,?,?,?,?,?,?,?)";
            
            System.out.println("name: "+productName+"\nAuthor: "+author+"\ndate: "+publicationDate
                    +"\nqnty: "+quantity+"\nprice: "+price+"\nstyle: "+musicStyle+"\nrow: "+row
                    +"\nspan: "+span+"\nshelf: "+shelf);
            
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, productName);
            ps.setString(2, author);
            ps.setString(3, publicationDate);
            ps.setInt(4, quantity);
            ps.setDouble(5, price);
            ps.setString(6, musicStyle);
            ps.setInt(7, row);
            ps.setInt(8, span);
            ps.setInt(9, shelf);

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
    
    private void initForm() {
        
          // Opening database connection
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();

            // Step 2.C: Executing SQL &amp; retrieve data into ResultSet
             
            sql = "SELECT * FROM MusicStyle";
            rs = st.executeQuery(sql);//execute the statement
            
            while(rs.next()){
                
                genre = rs.getString("Genre");
                genreComboBox.addItem(genre);
                
                System.out.println("genre: "+genre);
            }
            
            st.close();
            rs.close();
            conn.close();

        }
        catch(SQLException sqlex){
            System.err.println(sqlex.getMessage());
        }
        
        
        productnameField.setText("");
        authorField.setText("");
        publicationdateField.setText("");
        quantityField.setText("");
        priceField.setText("");
        rowField.setText("");
        spanField.setText("");
        shelfField.setText("");
    }
    
    
    private void updateTable(){
        
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);
            //Creating JDBC Statement
            st = conn.createStatement();
            
            sql = "SELECT * FROM Items";
            rs = st.executeQuery(sql);//execute the statement
            
            DefaultTableModel model = (DefaultTableModel)itemsTable.getModel();
            
            model.setRowCount(0);
         
            if(rs.next()){
                
                do{
                    ID = rs.getString("ID");
                    productName = rs.getString("Product_name");
                    author = rs.getString("Author");
                    publicationDate = rs.getString("Publication_date");
                    quantity = rs.getInt("Quantity");
                    price = rs.getDouble("Price");
                    musicStyle = rs.getString("Music_style");
                    row = rs.getInt("Row");
                    span = rs.getInt("Span");
                    shelf = rs.getInt("Shelf");
                
                    model.addRow(new Object[]{ID, productName, author, publicationDate, quantity, price, musicStyle, row, span, shelf});
                
                }while(rs.next());
                
            }
            
            rs.close();
            st.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void getItemFromTable() {
        initForm();
        
        DefaultTableModel model = (DefaultTableModel)itemsTable.getModel();        
                
        selectedIndex = itemsTable.getSelectedRow();
        
        try {
            productnameField.setText(model.getValueAt(selectedIndex, 1).toString());
            authorField.setText(model.getValueAt(selectedIndex, 2).toString());
            publicationdateField.setText(model.getValueAt(selectedIndex, 3).toString());
            quantityField.setText(model.getValueAt(selectedIndex, 4).toString());
            priceField.setText(model.getValueAt(selectedIndex, 5).toString());
            genreComboBox.setSelectedItem(model.getValueAt(selectedIndex, 6).toString());
            rowField.setText(model.getValueAt(selectedIndex, 7).toString());
            spanField.setText(model.getValueAt(selectedIndex, 8).toString());
            shelfField.setText(model.getValueAt(selectedIndex, 9).toString());
        } catch (Exception e) {
            System.out.println("error: "+e);
        }
    }
    
    private void editItems() {
        DefaultTableModel model = (DefaultTableModel)itemsTable.getModel();        
                
        selectedIndex = itemsTable.getSelectedRow();
        
        int id = Integer.parseInt(model.getValueAt(selectedIndex, 0).toString());
        
        getInsertData();
        
        // Opening database connection
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();

            // Step 2.C: Executing SQL &amp; retrieve data into ResultSet
             
            sql = "UPDATE Items SET Product_name=?,Author=?,Publication_date=?,Quantity=?,Price=?,Music_style=?,Row=?,Span=?,Shelf=? WHERE ID=?";
            
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, productName);
            ps.setString(2, author);
            ps.setString(3, publicationDate);
            ps.setInt(4, quantity);
            ps.setDouble(5, price);
            ps.setString(6, musicStyle);
            ps.setInt(7, row);
            ps.setInt(8, span);
            ps.setInt(9, shelf);
            ps.setInt(10, id);
            
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
                       
    
    private void deleteItems(){
        initForm();
        
        DefaultTableModel model = (DefaultTableModel)itemsTable.getModel();        
                
        selectedIndex = itemsTable.getSelectedRow();
        
        getItemFromTable();
        
        int id = Integer.parseInt(model.getValueAt(selectedIndex, 0).toString());
        
        // Opening database connection
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();

            // Step 2.C: Executing SQL &amp; retrieve data into ResultSet
             
            sql = "DELETE FROM Items WHERE ID=?";
            
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        mainItemPanel = new javax.swing.JPanel();
        rowField = new javax.swing.JTextField();
        spanField = new javax.swing.JTextField();
        shelfField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        deleteLocation1 = new javax.swing.JButton();
        editLocation1 = new javax.swing.JButton();
        addLocation1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        itemsTable = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        productnameField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        authorField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        publicationdateField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        quantityField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        priceField = new javax.swing.JTextField();
        genreComboBox = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
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
                .addGap(381, 381, 381)
                .addComponent(jLabel15)
                .addContainerGap(430, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel15)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, -1));

        mainItemPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rowField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowFieldActionPerformed(evt);
            }
        });
        mainItemPanel.add(rowField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 171, -1));

        spanField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spanFieldActionPerformed(evt);
            }
        });
        mainItemPanel.add(spanField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, 171, -1));

        shelfField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shelfFieldActionPerformed(evt);
            }
        });
        mainItemPanel.add(shelfField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 171, -1));

        jLabel4.setText("Row");
        mainItemPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 29, 22));

        jLabel5.setText("Span");
        mainItemPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 29, 22));

        deleteLocation1.setText("delete");
        deleteLocation1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deleteLocation1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteLocation1ActionPerformed(evt);
            }
        });
        mainItemPanel.add(deleteLocation1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 370, 70, -1));

        editLocation1.setText("edit");
        editLocation1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        editLocation1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editLocation1ActionPerformed(evt);
            }
        });
        mainItemPanel.add(editLocation1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 370, 60, 20));

        addLocation1.setText("Add");
        addLocation1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addLocation1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLocation1ActionPerformed(evt);
            }
        });
        mainItemPanel.add(addLocation1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 60, 20));

        itemsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Product Name", "Author", "Publication date", "Quantity", "Price", "Music Style", "Row", "Span", "Shelf"
            }
        ));
        itemsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemsTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                itemsTableMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(itemsTable);

        mainItemPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(313, 18, 730, 370));

        jLabel6.setText("Shelf");
        mainItemPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 41, 22));

        jLabel7.setText("Product Name");
        mainItemPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 18, 92, 22));

        productnameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productnameFieldActionPerformed(evt);
            }
        });
        mainItemPanel.add(productnameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 18, 158, -1));

        jLabel8.setText("Author");
        mainItemPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 92, 22));

        authorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorFieldActionPerformed(evt);
            }
        });
        mainItemPanel.add(authorField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 52, 158, -1));

        jLabel9.setText("Publication date");
        mainItemPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 86, 92, 22));

        publicationdateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                publicationdateFieldActionPerformed(evt);
            }
        });
        mainItemPanel.add(publicationdateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 86, 158, -1));

        jLabel10.setText("Quantity");
        mainItemPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 92, 22));

        quantityField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityFieldActionPerformed(evt);
            }
        });
        mainItemPanel.add(quantityField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 158, -1));

        jLabel11.setText("Music Style");
        mainItemPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 92, 22));

        priceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceFieldActionPerformed(evt);
            }
        });
        mainItemPanel.add(priceField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 160, -1));

        mainItemPanel.add(genreComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 171, -1));

        jLabel12.setText("Price");
        mainItemPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 92, 22));

        getContentPane().add(mainItemPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 1052, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rowFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rowFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rowFieldActionPerformed

    private void deleteLocation1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteLocation1ActionPerformed
        deleteItems();
    }//GEN-LAST:event_deleteLocation1ActionPerformed

    private void editLocation1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editLocation1ActionPerformed
        editItems();
    }//GEN-LAST:event_editLocation1ActionPerformed

    private void addLocation1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLocation1ActionPerformed
        addNewItem();
    }//GEN-LAST:event_addLocation1ActionPerformed

    private void itemsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemsTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_itemsTableMouseClicked

    private void spanFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spanFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_spanFieldActionPerformed

    private void shelfFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shelfFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_shelfFieldActionPerformed

    private void productnameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productnameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productnameFieldActionPerformed

    private void authorFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authorFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_authorFieldActionPerformed

    private void publicationdateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_publicationdateFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_publicationdateFieldActionPerformed

    private void quantityFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityFieldActionPerformed

    private void priceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceFieldActionPerformed

    private void itemsTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemsTableMousePressed
        getItemFromTable();
        
    }//GEN-LAST:event_itemsTableMousePressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.setVisible(false);
        new MainMenu();
    }//GEN-LAST:event_formWindowClosing



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addLocation1;
    private javax.swing.JTextField authorField;
    private javax.swing.JButton deleteLocation1;
    private javax.swing.JButton editLocation1;
    private javax.swing.JComboBox<String> genreComboBox;
    private javax.swing.JTable itemsTable;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel mainItemPanel;
    private javax.swing.JTextField priceField;
    private javax.swing.JTextField productnameField;
    private javax.swing.JTextField publicationdateField;
    private javax.swing.JTextField quantityField;
    private javax.swing.JTextField rowField;
    private javax.swing.JTextField shelfField;
    private javax.swing.JTextField spanField;
    // End of variables declaration//GEN-END:variables
}
