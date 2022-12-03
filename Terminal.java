
package vinylstore;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Terminal extends javax.swing.JFrame {

    
    String genre, ID, productName, author, publicationDate, musicStyle, sql;
    
    Integer row, span, shelf;
    
    int selectedIndex=0, quantity=0, basketQty=0;
    
    Double price=0.0,amount=0.0;
    
    java.sql.Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement ps;
        
    String msAccDB = "vinylstore.accdb"; // path to the DB file
    String dbURL = "jdbc:ucanaccess://" + msAccDB;//Database url
    
    public Terminal() {
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        updateTable();
        showBasket();
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

   
    private void selectItems(){
        DefaultTableModel model = (DefaultTableModel)itemsTable.getModel();        
                
        selectedIndex = itemsTable.getSelectedRow();
        
        try {
            productName = model.getValueAt(selectedIndex, 1).toString();
            author = model.getValueAt(selectedIndex, 2).toString();
            publicationDate = model.getValueAt(selectedIndex, 3).toString();
            quantity = Integer.parseInt(model.getValueAt(selectedIndex, 4).toString());
            price = Double.parseDouble(model.getValueAt(selectedIndex, 5).toString());
            musicStyle = model.getValueAt(selectedIndex, 6).toString();
            row = Integer.parseInt(model.getValueAt(selectedIndex, 7).toString());
            span = Integer.parseInt(model.getValueAt(selectedIndex, 8).toString());
            shelf = Integer.parseInt(model.getValueAt(selectedIndex, 9).toString());
        } catch (Exception e) {
            System.out.println("error: "+e);
        }
    }
    
    private void putInBasket(){
        
        System.out.println("product name: "+productName);
        
        // Opening database connection
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();

            // check if we have this item in customer basket
            sql = "SELECT * FROM Basket WHERE Product_Name LIKE ?";

            ps = conn.prepareStatement(sql);
            
            ps.setString(1, productName);
            
            rs = ps.executeQuery();
            
//            rs.close();
//            ps.close();
            


            if(rs.next()){
                System.out.println("we need update");
                
                basketQty = (rs.getInt("Quantity"))+1;
                
                price = rs.getDouble("Price");
                
                amount = price*basketQty;
                
                sql = "UPDATE Basket SET Quantity=?, Price=? ,Amount=? WHERE Product_Name LIKE ?";
                ps = conn.prepareStatement(sql);
                
                ps.setInt(1, basketQty);
                ps.setDouble(2, price);
                ps.setDouble(3, amount);
                ps.setString(4, productName);
                
            }else{
                System.out.println("insert the product");
                
                basketQty = 1;
                
                amount = price*basketQty;
                
                sql = "INSERT INTO Basket (Product_name, Quantity, Price, Amount) VALUES (?,?,?,?)";
                ps = conn.prepareStatement(sql);
                
                ps.setString(1, productName);
                ps.setInt(2, basketQty);
                ps.setDouble(3, price);
                ps.setDouble(4, amount);

            
            }
            
            ps.executeUpdate();

            rs.close();
            ps.close();
            conn.close();
            
            showBasket();

        }
        catch(SQLException sqlex){
            System.err.println("error: "+sqlex.getMessage());
        }
    }
    
    private void showBasket(){
        
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);
            //Creating JDBC Statement
            st = conn.createStatement();
            
            sql = "SELECT * FROM Basket";
            rs = st.executeQuery(sql);//execute the statement
            
            DefaultTableModel model = (DefaultTableModel)customerTable.getModel();
            
            model.setRowCount(0);
         
            if(rs.next()){
                
                do{
                    // ID = rs.getString("ID");
                    productName = rs.getString("Product_name");

                    quantity = rs.getInt("Quantity");
                    price = rs.getDouble("Price");
                    amount = rs.getDouble("Amount");

                    customerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    customerTable.getColumnModel().getColumn(0).setPreferredWidth(210);
                    customerTable.getColumnModel().getColumn(1).setPreferredWidth(60);
                    customerTable.getColumnModel().getColumn(2).setPreferredWidth(60);
                    customerTable.getColumnModel().getColumn(3).setPreferredWidth(60);
                    
                    model.addRow(new Object[]{productName, quantity, price, amount});
                
                }while(rs.next());
                
            }
            
            rs.close();
            st.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    private void deleteItem(){
        
        DefaultTableModel model = (DefaultTableModel)customerTable.getModel();        
                
        selectedIndex = customerTable.getSelectedRow();

        productName = model.getValueAt(selectedIndex, 0).toString();
        
        // Opening database connection
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();

            // Step 2.C: Executing SQL &amp; retrieve data into ResultSet
             
            sql = "DELETE FROM Basket WHERE Product_Name=?";
            
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, productName);

            ps.executeUpdate();
            
            ps.close();
            st.close();
            conn.close();
            System.out.println("data was inserted");

            showBasket();

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
        jScrollPane2 = new javax.swing.JScrollPane();
        itemsTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                .addGap(462, 462, 462)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel15)
                .addContainerGap(22, Short.MAX_VALUE))
        );

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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                itemsTableMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(itemsTable);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Vinyl Discs");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(351, 351, 351)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 788, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product Name", "Quantity", "Price", "Amount"
            }
        ));
        customerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                customerTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(customerTable);

        jButton1.setText("Buy");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Rent");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton3.setText("Cancel");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Customer Basket");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(126, 126, 126))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemsTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemsTableMousePressed
        selectItems();
        putInBasket();
    }//GEN-LAST:event_itemsTableMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void customerTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerTableMousePressed
        
        int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to delete this Item?","Warning",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
          deleteItem();
        }

        
        
    }//GEN-LAST:event_customerTableMousePressed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable customerTable;
    private javax.swing.JTable itemsTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
