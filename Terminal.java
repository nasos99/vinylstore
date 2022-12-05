
package vinylstore;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONObject;


public class Terminal extends javax.swing.JFrame {

    
    String genre, ID, productName, author, publicationDate, musicStyle, sql, accountNumber;
    
    String firstname,lastname,card,accountnumber,item2json,transaction;
            
    Double balance=0.0;
    
    Integer row, span, shelf;
    
    int selectedIndex=0, quantity=0, basketQty=0,rowCount=0;
    
    Double price=0.0,amount=0.0,totalAmount=0.0,newBalance=0.0;
    
    ArrayList<String> totalList = new ArrayList<>();
    
    JSONObject total_product = new JSONObject();
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    Date date;
    
    
    
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
        updateTable();// update item table from database
        showBasket();// get basket data from database
    }
    
    //get item data from database
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

    //select item from the items table
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
    
    //Insert the item into customer basket
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
            
            //check if the item exists in the basket
            if(rs.next()){
                
                //The products exist in the basket we recalculate the quantity and price
                
                System.out.println("we need update");
                
                basketQty = (rs.getInt("Quantity"))+1;
                
                price = rs.getDouble("Price");
                
                amount = price*basketQty;// calculate the item amount
                
                sql = "UPDATE Basket SET Quantity=?, Price=? ,Amount=? WHERE Product_Name LIKE ?";
                ps = conn.prepareStatement(sql);
                
                ps.setInt(1, basketQty);
                ps.setDouble(2, price);
                ps.setDouble(3, amount);
                ps.setString(4, productName);
                
            }else{
                
                
                //Insert product in customer basket
                System.out.println("insert the product");
                
                basketQty = 1;
                
                amount = price*basketQty; //calculate amount of items
                
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
    
    //get the data to basket from database
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
    
    //delete the item from customer basket
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
            System.out.println("item is deleted");

            showBasket();

        }
        catch(SQLException sqlex){
            System.err.println(sqlex.getMessage());
        }
    
    }
    
    // Clear out the basket
    private void clearBasket(){
        
        
        // Opening database connection
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();

            // Step 2.C: Executing SQL &amp; retrieve data into ResultSet
             
            sql = "DELETE FROM Basket";
            
            ps = conn.prepareStatement(sql);
            
            ps.executeUpdate();
            
            ps.close();
            st.close();
            conn.close();
            System.out.println("data was deleted");

            showBasket();

        }
        catch(SQLException sqlex){
            System.err.println(sqlex.getMessage());
        }
    }
    
    //Get total amount from customer basket
    private void getTotalAmount(){
        
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();

            // check if we have this item in customer basket
            sql = "SELECT Amount FROM Basket";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            rowCount = 0;
            
            while(rs.next()){
                
                amount = rs.getDouble("Amount");
                
                totalAmount += amount;
                
                ++rowCount;
            
            }
            
            System.out.println("total items: "+rowCount+"\ntotal amount: "+totalAmount);
            
        }catch(SQLException sqlex){
            System.err.println(sqlex.getMessage());
        }
    }
    
    //Start the payment form
    private void payment(){
        
        //get inserted account number from text field
        accountNumber = accnumber.getText();
        
        // Check if the account already exists on the database 
        if(getAccount(accountNumber)){
            
            // open account balance form
            Sell.setVisible(false);
            accountDetails.setVisible(true);
            accountDetails.setLocationRelativeTo(null);

            firstNameLabel.setText(firstname);
            lastNameLabel.setText(lastname);

            balanceLabel.setText(balance.toString()+" €");
            
            infoAmountLabel.setText(totalAmount+" €");


            System.out.println("total amount: "+totalAmount+"\nbalance: "+balance);

            
            // Check if customer balance is enough to make the payment otherwise add to more balance
            
            if(totalAmount <= balance){
                // enough money to make a transaction
                payNowButton.setVisible(true);
                rentButton.setVisible(true);
                increaseBalanceButton.setVisible(false);

            }else{
                // Must add more money to account
                payNowButton.setVisible(false);
                rentButton.setVisible(false);
                increaseBalanceButton.setVisible(true);
            }
        
        }else{
            JOptionPane.showMessageDialog(Sell, "Error account number");
            
            accnumber.setText(null);
        
        }

    }
    
    private void addMoneyInAccount(){
            
        accountDetails.setVisible(false);
        addBalance.setVisible(true);
        addBalance.setLocationRelativeTo(null);

    }
    
    
    
    private boolean getAccount(String accnum){
        
        boolean state = false;
    
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);
            //Creating JDBC Statement
            st = conn.createStatement();
            
            sql = "SELECT * FROM Accounts WHERE Account_Number LIKE ?";

            ps = conn.prepareStatement(sql);
            
            ps.setString(1, accnum);
            
            rs = ps.executeQuery();//execute the statement

            balance=0.0;

            if(rs.next()){
                
 
                ID = rs.getString("ID");
                firstname = rs.getString("First_Name");
                lastname = rs.getString("Last_Name");
                card = rs.getString("Card");
                accountnumber = rs.getString("Account_Number");
                balance = rs.getDouble("Balance");
                
                state = true;

            }
            
            rs.close();
            st.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return state;

    }
    
    private void createTransaction(Integer type){
        
        date = new Date(); // get current date

        createJSON();   // create a JSON array of items from basket
        
      
        // Insert transaction into transaction table
        
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();
            
            sql = "INSERT INTO Transactions (Products,Total_Price,Type,Create_at) VALUES (?,?,?,?)";
               
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, transaction);
            ps.setDouble(2, totalAmount);
            ps.setInt(3, type);
            ps.setString(4, formatter.format(date));

            ps.executeUpdate();
            
            ps.close();
            st.close();
            conn.close();
            
            
            recalculateAccount();
            
            Sell.setVisible(false);
            rent.setVisible(false);
            addBalance.setVisible(false);
            accountDetails.setVisible(false);
            
            clearBasket(); // clear customer basket
            

        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void createJSON(){
        
        totalList.clear();
        
        transaction = null;
        
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);
            //Creating JDBC Statement
            st = conn.createStatement();
            
            sql = "SELECT * FROM Basket";

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();//execute the statement
            
            
            JSONObject jo = new JSONObject();


            while(rs.next()){
                
                jo.put("product", rs.getString("Product_Name"));
                jo.put("quantity", rs.getString("Quantity"));
                jo.put("productPrice", rs.getString("Price"));
                jo.put("amount", rs.getString("Amount"));
                
                item2json = jo.toString();   // create string of json
            
                totalList.add(item2json);           // convert string to array
            }
            
            total_product.put("transaction", totalList);
            
            transaction = total_product.toString();
            
            
            rs.close();
            st.close();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("get transaction: "+total_product);
    
    }
    
    // Renting the items ---------------------------------------------------
    private void renting(){
        
        accountDetails.setVisible(false);
        rent.setVisible(true);
        rent.setLocationRelativeTo(null);
        
        selectedItemsLabel.setText(rowCount+" items");
        
        totalAmount = rowCount*5.0;
        
        payRentLabel.setText(totalAmount.toString()+" €");

    }
    
    // Recalculate customer account value
    
    private void recalculateAccount(){
        
        newBalance = balance - totalAmount; // recalculate customer ba;lance
        
        updateAccountBalance(); // update customer balance
    
    
    }
    
    private void updateAccountBalance(){
    
        // Opening database connection
        try {
            //Create and get connection using DriverManager class
            conn = DriverManager.getConnection(dbURL);

            //Creating JDBC Statement
            st = conn.createStatement();

            // Step 2.C: Executing SQL &amp; retrieve data into ResultSet
             
            sql = "UPDATE Accounts SET Balance=? WHERE Account_Number LIKE ? ";

            ps = conn.prepareStatement(sql);
    
            ps.setDouble(1, newBalance);
            ps.setString(2, accountnumber);


            ps.executeUpdate();
            
            ps.close();
            st.close();
            conn.close();
            System.out.println("data was inserted");
            
            addBalance.setVisible(false);

            payment();
            
            accountDetails.setVisible(true);
        }
        catch(SQLException sqlex){
            System.err.println(sqlex.getMessage());
        }
    
    }
  
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Sell = new javax.swing.JFrame();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        totalAmountLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        accnumber = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        payButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        accountDetails = new javax.swing.JFrame();
        jPanel5 = new javax.swing.JPanel();
        lastNameLabel = new javax.swing.JLabel();
        firstNameLabel = new javax.swing.JLabel();
        balanceLabel = new javax.swing.JLabel();
        payNowButton = new javax.swing.JButton();
        rentButton = new javax.swing.JButton();
        increaseBalanceButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        infoAmountLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        addBalance = new javax.swing.JFrame();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        newBalanceField = new javax.swing.JTextField();
        rent = new javax.swing.JFrame();
        jPanel7 = new javax.swing.JPanel();
        selectedItemsLabel = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        rentRateLabel = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        payRentLabel = new javax.swing.JLabel();
        rentNowButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        itemsTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        sellButton = new javax.swing.JButton();
        clearbasketButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        Sell.setAlwaysOnTop(true);
        Sell.setMinimumSize(new java.awt.Dimension(380, 350));
        Sell.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                SellWindowClosing(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Total Amount");
        jLabel3.setFocusable(false);
        jLabel3.setRequestFocusEnabled(false);
        jLabel3.setVerifyInputWhenFocusTarget(false);

        totalAmountLabel.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        totalAmountLabel.setForeground(new java.awt.Color(0, 0, 153));
        totalAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalAmountLabel.setText("0.00");
        totalAmountLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 0), 2, true));
        totalAmountLabel.setFocusable(false);
        totalAmountLabel.setRequestFocusEnabled(false);
        totalAmountLabel.setVerifyInputWhenFocusTarget(false);

        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("€");
        jLabel5.setFocusable(false);
        jLabel5.setRequestFocusEnabled(false);
        jLabel5.setVerifyInputWhenFocusTarget(false);

        accnumber.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        accnumber.setForeground(new java.awt.Color(0, 0, 153));
        accnumber.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        accnumber.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 0), 2, true));
        accnumber.setCaretColor(new java.awt.Color(255, 0, 0));
        accnumber.setFocusCycleRoot(true);
        accnumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accnumberActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Account No.");
        jLabel4.setFocusable(false);
        jLabel4.setRequestFocusEnabled(false);
        jLabel4.setVerifyInputWhenFocusTarget(false);

        payButton.setBackground(new java.awt.Color(0, 204, 204));
        payButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        payButton.setForeground(new java.awt.Color(255, 255, 255));
        payButton.setText("pay");
        payButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        payButton.setFocusable(false);
        payButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payButtonActionPerformed(evt);
            }
        });

        cancelButton.setBackground(new java.awt.Color(204, 0, 0));
        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setText("cancel");
        cancelButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel3))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jLabel4))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(totalAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(accnumber)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(payButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(44, 44, 44)
                                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(accnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(payButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SellLayout = new javax.swing.GroupLayout(Sell.getContentPane());
        Sell.getContentPane().setLayout(SellLayout);
        SellLayout.setHorizontalGroup(
            SellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        SellLayout.setVerticalGroup(
            SellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        accountDetails.setMinimumSize(new java.awt.Dimension(500, 300));
        accountDetails.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                accountDetailsWindowClosing(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 255, 204));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lastNameLabel.setFont(new java.awt.Font("Dubai Medium", 0, 18)); // NOI18N
        lastNameLabel.setText("last name");
        jPanel5.add(lastNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 110, -1));

        firstNameLabel.setFont(new java.awt.Font("Dubai Medium", 0, 18)); // NOI18N
        firstNameLabel.setText("first name");
        jPanel5.add(firstNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 100, -1));

        balanceLabel.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        balanceLabel.setForeground(new java.awt.Color(0, 0, 153));
        balanceLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        balanceLabel.setText("0.00 €");
        balanceLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 0), 2, true));
        jPanel5.add(balanceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 234, 53));

        payNowButton.setBackground(new java.awt.Color(51, 255, 204));
        payNowButton.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        payNowButton.setForeground(new java.awt.Color(255, 255, 255));
        payNowButton.setText("Pay");
        payNowButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        payNowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payNowButtonActionPerformed(evt);
            }
        });
        jPanel5.add(payNowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 100, 70));

        rentButton.setBackground(new java.awt.Color(153, 0, 153));
        rentButton.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        rentButton.setForeground(new java.awt.Color(255, 255, 255));
        rentButton.setText("Rent");
        rentButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        rentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentButtonActionPerformed(evt);
            }
        });
        jPanel5.add(rentButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 100, 70));

        increaseBalanceButton.setBackground(new java.awt.Color(153, 153, 0));
        increaseBalanceButton.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        increaseBalanceButton.setForeground(new java.awt.Color(255, 255, 255));
        increaseBalanceButton.setText("Increase Balance");
        increaseBalanceButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        increaseBalanceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                increaseBalanceButtonActionPerformed(evt);
            }
        });
        jPanel5.add(increaseBalanceButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 230, 70));

        jLabel6.setText("Account Name:");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, 30));

        infoAmountLabel.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        infoAmountLabel.setForeground(new java.awt.Color(0, 0, 153));
        infoAmountLabel.setText("0.00 €");
        jPanel5.add(infoAmountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 140, 30));

        jLabel8.setText("Account Balance");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, 20));

        jLabel9.setText("Total Amount: ");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, -1, 30));

        javax.swing.GroupLayout accountDetailsLayout = new javax.swing.GroupLayout(accountDetails.getContentPane());
        accountDetails.getContentPane().setLayout(accountDetailsLayout);
        accountDetailsLayout.setHorizontalGroup(
            accountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        accountDetailsLayout.setVerticalGroup(
            accountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        addBalance.setAlwaysOnTop(true);
        addBalance.setMinimumSize(new java.awt.Dimension(400, 300));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Add Money to Account");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, -1, -1));

        jButton5.setBackground(new java.awt.Color(0, 153, 153));
        jButton5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Add");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 300, 60));

        newBalanceField.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        newBalanceField.setForeground(new java.awt.Color(0, 0, 153));
        newBalanceField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        newBalanceField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 0), 2, true));
        newBalanceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBalanceFieldActionPerformed(evt);
            }
        });
        jPanel6.add(newBalanceField, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 300, 50));

        javax.swing.GroupLayout addBalanceLayout = new javax.swing.GroupLayout(addBalance.getContentPane());
        addBalance.getContentPane().setLayout(addBalanceLayout);
        addBalanceLayout.setHorizontalGroup(
            addBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );
        addBalanceLayout.setVerticalGroup(
            addBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        rent.setMinimumSize(new java.awt.Dimension(330, 300));

        jPanel7.setBackground(new java.awt.Color(204, 153, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        selectedItemsLabel.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        selectedItemsLabel.setForeground(new java.awt.Color(255, 0, 0));
        selectedItemsLabel.setText("0.00 €");
        jPanel7.add(selectedItemsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 90, 30));

        jLabel12.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 153));
        jLabel12.setText("Rent rate: ");
        jPanel7.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 183, 51));

        jLabel13.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 153));
        jLabel13.setText("Selected items: ");
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 171, 51));

        rentRateLabel.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        rentRateLabel.setForeground(new java.awt.Color(255, 0, 0));
        rentRateLabel.setText("5.00 €");
        jPanel7.add(rentRateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 90, 30));

        jLabel14.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 153));
        jLabel14.setText("Pay for rent:");
        jPanel7.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 171, 40));

        payRentLabel.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        payRentLabel.setForeground(new java.awt.Color(255, 0, 0));
        payRentLabel.setText("0.00 €");
        jPanel7.add(payRentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 90, 30));

        rentNowButton.setBackground(new java.awt.Color(0, 102, 0));
        rentNowButton.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        rentNowButton.setForeground(new java.awt.Color(255, 255, 255));
        rentNowButton.setText("Rent Now");
        rentNowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentNowButtonActionPerformed(evt);
            }
        });
        jPanel7.add(rentNowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 250, 60));

        javax.swing.GroupLayout rentLayout = new javax.swing.GroupLayout(rent.getContentPane());
        rent.getContentPane().setLayout(rentLayout);
        rentLayout.setHorizontalGroup(
            rentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
        );
        rentLayout.setVerticalGroup(
            rentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rentLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
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
                .addGap(351, 351, 351)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 788, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
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

        sellButton.setBackground(new java.awt.Color(0, 153, 153));
        sellButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sellButton.setForeground(new java.awt.Color(255, 255, 255));
        sellButton.setText("Sell");
        sellButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sellButtonActionPerformed(evt);
            }
        });

        clearbasketButton.setBackground(new java.awt.Color(153, 0, 0));
        clearbasketButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        clearbasketButton.setForeground(new java.awt.Color(255, 255, 255));
        clearbasketButton.setText("Clear Basket");
        clearbasketButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        clearbasketButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbasketButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(sellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(clearbasketButton, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(14, 14, 14))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearbasketButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
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
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(141, 141, 141))))
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

    private void sellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sellButtonActionPerformed
        
       
        // Get the total amount from the customer basket
        getTotalAmount();
        
        
        //Check if an item is in the customer basket, otherwise if the basket is empty transaction cannot be done
        if(totalAmount != 0.0){
            
            // Open sell form to insert customer account number
            Sell.setVisible(true);
            Sell.setLocationRelativeTo(null);
            
            totalAmountLabel.setText(totalAmount.toString());
            
            String accountInput = accnumber.getText();

            System.out.println("account input: "+accountInput);

            // Check if user hasn't inserted their account number
            if(accountInput.isEmpty()){

            payButton.setEnabled(false);
            
                //Check the inserted data from account field
                
                accnumber.addKeyListener(new KeyAdapter()
                    {
                        public void keyPressed(KeyEvent ke)
                        {
                            if(!(ke.getKeyChar()==27||ke.getKeyChar()==65535))//this section will execute only when user is editing the account number field
                            {
                                payButton.setEnabled(true); // If data is inserted the button will be enabled
                            }
                        }
                    });

            }
        
        }
        
        
            
    }//GEN-LAST:event_sellButtonActionPerformed

    private void customerTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerTableMousePressed
        
        // Option to delete selected item in customer basket
        int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to delete this Item?","Warning",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
          deleteItem();
        }
        
    }//GEN-LAST:event_customerTableMousePressed

    private void clearbasketButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbasketButtonActionPerformed
        clearBasket();
    }//GEN-LAST:event_clearbasketButtonActionPerformed

    private void accnumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accnumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accnumberActionPerformed

    private void payButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payButtonActionPerformed
        payment();
    }//GEN-LAST:event_payButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
                
        this.setVisible(false);
        new MainMenu();
        
    }//GEN-LAST:event_formWindowClosing

    private void increaseBalanceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_increaseBalanceButtonActionPerformed
        addMoneyInAccount();
    }//GEN-LAST:event_increaseBalanceButtonActionPerformed

    private void SellWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_SellWindowClosing
        totalAmount = 0.0;
    }//GEN-LAST:event_SellWindowClosing

    private void accountDetailsWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_accountDetailsWindowClosing
        totalAmount = 0.0;
    }//GEN-LAST:event_accountDetailsWindowClosing

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        newBalance = balance+Double.parseDouble(newBalanceField.getText());
        
        updateAccountBalance();
        
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void payNowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payNowButtonActionPerformed
        createTransaction(1);   // 1 - is a sale
    }//GEN-LAST:event_payNowButtonActionPerformed

    private void newBalanceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBalanceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newBalanceFieldActionPerformed

    private void rentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentButtonActionPerformed
        renting();
    }//GEN-LAST:event_rentButtonActionPerformed

    private void rentNowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentNowButtonActionPerformed
       
        createTransaction(2);

    }//GEN-LAST:event_rentNowButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        Sell.setVisible(false);
        totalAmount = 0.0;
        accnumber.setText(null);
    }//GEN-LAST:event_cancelButtonActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame Sell;
    private javax.swing.JTextField accnumber;
    private javax.swing.JFrame accountDetails;
    private javax.swing.JFrame addBalance;
    private javax.swing.JLabel balanceLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton clearbasketButton;
    private javax.swing.JTable customerTable;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JButton increaseBalanceButton;
    private javax.swing.JLabel infoAmountLabel;
    private javax.swing.JTable itemsTable;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JTextField newBalanceField;
    private javax.swing.JButton payButton;
    private javax.swing.JButton payNowButton;
    private javax.swing.JLabel payRentLabel;
    private javax.swing.JFrame rent;
    private javax.swing.JButton rentButton;
    private javax.swing.JButton rentNowButton;
    private javax.swing.JLabel rentRateLabel;
    private javax.swing.JLabel selectedItemsLabel;
    private javax.swing.JButton sellButton;
    private javax.swing.JLabel totalAmountLabel;
    // End of variables declaration//GEN-END:variables
}
