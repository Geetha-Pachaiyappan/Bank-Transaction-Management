package bank;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Transaction extends Customer{
    private int id;
    private Timestamp date;
    private String type;
    private int amount;
    private int customer_id;

    Scanner sc =  new Scanner(System.in);

    public Transaction(){
        System.out.println("Enter your customer id: ");
        customer_id = sc.nextInt();
        System.out.println("Enter the Amount: ");
        amount = sc.nextInt();

        date = Timestamp.valueOf(LocalDateTime.now());
    }

    public boolean isTransactionAvailable(){
        String query = "select count(*) from transaction_history";
        boolean check = false;
        try{
            Connection con = ConnectionDao.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            if(rs.getInt(1) > 0)
                check = true;
            con.close();
        }catch (SQLException e){
            System.out.println("Exception Caught at Connecting to the Database" + e);
        }
        return check;
    }

    public int lastTransactionId(){
        String query = "select max(id) from transaction_history";
        int lastTransactionId = 0;
        if(isTransactionAvailable()){
            try{
                Connection con = ConnectionDao.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                rs.next();
                lastTransactionId = rs.getInt(1);
            }catch (SQLException e){
                System.out.println("Exception Caught at Connecting to the Database" + e);
            }
        }
        return lastTransactionId;
    }

    public String withdrawal(){
        int id = 0;
        double balance;
        if(isTransactionAvailable())
           id = lastTransactionId() + 1;
        else
            id = 1;

        if(!super.isCustomer(customer_id))
            return "Invalid Customer Id";

        if(super.getBalance(customer_id) < amount)
            return "Insufficient Balance";
        else if(amount <= 0)
            return "Enter Valid Amount";
        else
            balance = super.getBalance(customer_id)- amount;


        String customerQuery = "update customer set balance = "+balance+"where id = "+ customer_id;
        String transactionQuery = "insert into transaction_history values(?,?,?,?,?)";

        try{
            Connection con = ConnectionDao.getConnection();
            con.setAutoCommit(false);
            Statement st = con.createStatement();
            int rs = st.executeUpdate(customerQuery);

            PreparedStatement pst = con.prepareStatement(transactionQuery);
            pst.setInt(1,id);
            pst.setTimestamp(2,date);
            pst.setString(3, "Withdrawal");
            pst.setInt(4, amount);
            pst.setInt(5,customer_id);
            int rs1 = pst.executeUpdate();
            pst.close();


            if(rs > 0 && rs1 >0){
                con.commit();
            }else {
                con.rollback();
            }
            con.close();

        }catch (SQLException e){
            
            return "Exception during database connection" + e;
        }

        return "Withdrawal Successful!";
    }

    // deposit


    public String deposit(){
        int id = 0;
        double balance = 0;
        if(isTransactionAvailable())
            id = lastTransactionId() + 1;
        else
            id = 1;

        if(!super.isCustomer(customer_id))
            return "Invalid Customer Id";

        if(amount <= 0)
            return "Enter Valid Amount";
        else
            balance  = super.getBalance(customer_id) + amount;

        String customerQuery = "update customer set balance = "+balance+ "where id = "+ customer_id;
        String transactionQuery = "insert into transaction_history values(?,?,?,?,?)";

        try{
            Connection con = ConnectionDao.getConnection();
            con.setAutoCommit(false);
            Statement st = con.createStatement();
            int rs = st.executeUpdate(customerQuery);

            PreparedStatement pst = con.prepareStatement(transactionQuery);
            pst.setInt(1,id);
            pst.setTimestamp(2,date);
            pst.setString(3, "Deposit");
            pst.setInt(4, amount);
            pst.setInt(5,customer_id);
            int rs1 = pst.executeUpdate();
            pst.close();


            if(rs > 0 && rs1 >0){
                con.commit();
            }else {
                con.rollback();
            }
            con.close();


        }catch (SQLException e){
            System.out.println("Exception caught during deposit" + e);
        }
        return "Deposit Successful!";
    }

}
