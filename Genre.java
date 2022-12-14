
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


public class Genre extends javax.swing.JFrame {

   
    String genre, ID, Genre, sql;
    
    int selectedIndex=0;
    
    
    java.sql.Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement ps;
        
    String msAccDB = "vinylstore.accdb"; // path to the DB file
    String dbURL = "jdbc:ucanaccess://" + msAccDB;//Database url
    
    
    public Genre() {
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        updateTable(); // update the genre table from the database
    }

    // Add new genre record to database
    private void addGenre() {
        
        genre=nameofgenre.getText(); // get data from genre field
        
        // check if field is empty
        if(genre.isEmpty())JOptionPane.showMessageDialog(this, "Please insert the data");
        else{
            // Insert data to database
            try {
                //Create and get connection using DriverManager class
                conn = DriverManager.getConnection(dbURL);

                //Creating JDBC Statement
                st = conn.createStatement();

                // Step 2.C: Executing SQL &amp; retrieve data into ResultSet

                sql = "INSERT INTO MusicStyle (Genre) VALUES (?)";

                ps = conn.prepareStatement(sql);

                ps.setString(1, genre);

                ps.executeUpdate();

                ps.close();
                st.close();
                conn.close();
                System.out.println("data was inserted");

                nameofgenre.setText(""); // Empty the genre field

                updateTable(); // update genre table from database
            }
            catch(SQLException sqlex){
                System.err.println(sqlex.getMessage());
            }

        }

    }
            
    private void updateTable(){
        
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);
            //Creating JDBC Statement
            st = conn.createStatement();
            
            sql = "SELECT * FROM MusicStyle";
            rs = st.executeQuery(sql);//execute the statement
            
            //Create a table model
            DefaultTableModel model = (DefaultTableModel)MusicStyle.getModel();
            
            //Sets the number of rows in the model
            model.setRowCount(0);
         
            if(rs.next()){
                
                do{
                    ID = rs.getString("ID");
                    Genre = rs.getString("Genre");
                
                    model.addRow(new Object[]{ID,Genre});
                
                }while(rs.next());
                
            }
            
            rs.close();     // close resultset
            st.close();     // close statement
            conn.close();   // close the connection
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

    }        
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        nameofgenre = new javax.swing.JTextField();
        deleteButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        MusicStyle = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

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

        nameofgenre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameofgenreActionPerformed(evt);
            }
        });

        deleteButton.setText("delete");
        deleteButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        editButton.setText("edit");
        editButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        addButton.setText("Add");
        addButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        MusicStyle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Genre"
            }
        ));
        MusicStyle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MusicStyleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(MusicStyle);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(nameofgenre, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameofgenre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton)
                    .addComponent(editButton)
                    .addComponent(deleteButton))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nameofgenreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameofgenreActionPerformed
        addGenre();
    }//GEN-LAST:event_nameofgenreActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        addGenre();
    }//GEN-LAST:event_addButtonActionPerformed

    private void MusicStyleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MusicStyleMouseClicked
        
        // Implementation of table model
        DefaultTableModel model = (DefaultTableModel)MusicStyle.getModel();        
        // Select the row index        
        selectedIndex = MusicStyle.getSelectedRow();
        
        try {
            nameofgenre.setText(model.getValueAt(selectedIndex, 1).toString());
            
        } catch (Exception e) {
            System.out.println("error: "+e);
        }
        
    }//GEN-LAST:event_MusicStyleMouseClicked

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        
        // If statement to check if the field is empty
        if(nameofgenre.getText().isEmpty())JOptionPane.showMessageDialog(this, "Please insert the data");
        else{
            // Update genre in database
            DefaultTableModel model = (DefaultTableModel)MusicStyle.getModel();        
                
            selectedIndex = MusicStyle.getSelectedRow();

            int id = Integer.parseInt(model.getValueAt(selectedIndex, 0).toString());

            genre=nameofgenre.getText();

            // Opening database connection
            try {
                //Create and get connection using DriverManager class
                conn = DriverManager.getConnection(dbURL);

                //Creating JDBC Statement
                st = conn.createStatement();

                // Step 2.C: Executing SQL &amp; retrieve data into ResultSet

                sql = "UPDATE MusicStyle SET Genre=? WHERE ID=?";

                ps = conn.prepareStatement(sql);

                ps.setString(1, genre);

                ps.setInt(2, id);

                ps.executeUpdate();

                ps.close();
                st.close();
                conn.close();
                System.out.println("data was inserted");

                nameofgenre.setText("");

                updateTable();


            }
            catch(SQLException sqlex){
                System.err.println(sqlex.getMessage());
            }
        
        }
        
        
    }//GEN-LAST:event_editButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        deleteGenre();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
        this.setVisible(false);
        new MainMenu();
        
    }//GEN-LAST:event_formWindowClosed
    
    // delete genre record from the database
    private void deleteGenre(){
        // set field to null
        nameofgenre.setText("");
        
        DefaultTableModel model = (DefaultTableModel)MusicStyle.getModel();        
                
        selectedIndex = MusicStyle.getSelectedRow();
        
        try {
            nameofgenre.setText(model.getValueAt(selectedIndex, 1).toString());
            
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
             
            sql = "DELETE FROM MusicStyle WHERE ID=?";
            
            ps = conn.prepareStatement(sql);
            
            ps.setInt(1, id);

            ps.executeUpdate();
            
            ps.close();
            st.close();
            conn.close();
            System.out.println("data was inserted");
            
            nameofgenre.setText(""); 
            
            updateTable();
            

        }
        catch(SQLException sqlex){
            System.err.println(sqlex.getMessage());
        }
        
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable MusicStyle;
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameofgenre;
    // End of variables declaration//GEN-END:variables
}
