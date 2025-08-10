package bank;

import bank.ExceptionHandling.InvalidAadharException;
import bank.ExceptionHandling.InvalidPhoneNumberException;
import bank.ExceptionHandling.UsernameNotValidException;

import java.sql.*;
import java.util.Scanner;

public class Customer {

    private int id;
    private long accountNo;
    private String name;
    private long aadharNo;
    private long phoneNo;
    private int age;
    private double balance;

    Scanner sc = new Scanner(System.in);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(long aadharNo) {
        this.aadharNo = aadharNo;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer(){
       this.id = 0;
       this.accountNo = 0;
       this.age = 0;
       this.name = "";
       this.phoneNo = 0;
       this.aadharNo = 0;
       this.balance =0;
    }

    public Customer(int id){
        this.id = id;
    }


    public void checkBalance(){
        String query = "select * from customer where id = "+ id;
        try{
            Connection con = ConnectionDao.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            System.out.println("Customer Id: " + rs.getInt(1));
            System.out.println("Account No: " + rs.getLong(2));
            System.out.println("Name: "+ rs.getString(3));
            System.out.println("Available Balance: "+ rs.getDouble(7));
            con.close();
        }catch (SQLException e){
            System.out.println("Exception Caught at Connecting to the Database" + e);
        }
    }

    public boolean isCustomer(int id){
        String query = "select * from customer where id = ?";
        boolean check = false;

        try{
            Connection con = ConnectionDao.getConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            if(rs.next())
                check = true;
        }catch (SQLException e){
            System.out.println("Exception Caught at Connecting to the Database" + e);
        }
        return check;
    }
    public double getBalance(int id){
        String query = "select balance from customer where id = ?";
        int balance =0;

        try{
            Connection con = ConnectionDao.getConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            balance = rs.getInt(1);
        }catch (SQLException e){
            System.out.println("Exception Caught at Connecting to the Database" + e);
        }
        return balance;
    }


    public boolean isCustomerAvailable(){
        String query = "select count(*) from customer";
        boolean check = false;

        try {
            Connection con = ConnectionDao.getConnection();
            Statement st = con.createStatement();
            ResultSet rt = st.executeQuery(query);
            rt.next();
            if(rt.getInt(1) > 0)
                check = true;
            con.close();

        }catch (SQLException e){
            System.out.println("Exception Caught at Connecting to the Database" + e);
        }
       return check;
    }

    public int getCustomerId(){
        String query = "select max(id) from customer";
        int lastId = 0;
        try {
            if(isCustomerAvailable()){
                Connection con = ConnectionDao.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                rs.next();
                lastId = rs.getInt(1);
                con.close();
            }
        }catch (SQLException e){
            System.out.println("Exception Caught at Connecting to the Database" + e);
        }
        return lastId;
    }

    public long getLastAccountNumber(){
        String query = "select max(account_no) from customer";
        long lastAccountNo = 0;
        try {
            if(isCustomerAvailable()){
                Connection con = ConnectionDao.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                rs.next();
                lastAccountNo = rs.getInt(1);
                con.close();
            }
        }catch (SQLException e){
            System.out.println("Exception Caught at Connecting to the Database" + e);
        }
        return lastAccountNo;
    }


    public void insertCustomer() throws Exception{
        //Get Username
        System.out.println("Enter Account Holder Name: ");
        this.name = sc.nextLine();
        Validations.isUsernameValid(this.name);   // Validation

        //Get Aadhar No
        try{
            System.out.println("Enter Account Holder Aadhar No(563456xxxxxx): ");
            this.aadharNo = sc.nextLong();
        } catch (Exception e) {
            throw new InvalidAadharException("Invalid Aadhar Number");
        }
        String aadhar = Long.toString(this.aadharNo);
        Validations.isAadharValid(aadhar);    // -- Validation

        //Get Phone Number
        try{
            System.out.println("Enter phone no: ");
            this.phoneNo = sc.nextLong();
        }catch (Exception e){
           throw new InvalidPhoneNumberException("Invalid Phone Number");
        }
        String strPhoneNumber = Long.toString(phoneNo);
        Validations.isPhoneNumberValid(strPhoneNumber);  // Validation


        //Get Account Holder Age
        System.out.println("Enter Account Holder Age: ");
        this.age = sc.nextInt();
        Validations.isUserAgeValid(this.age);

        //Set Initial Balance = 1000
        this.balance = 1000;

        String query = "insert into customer values(?,?,?,?,?,?,?)";
        int out =0;
        int id = 0;
        long tempAccountNo = 0;
        if(getCustomerId() != 0)
            id = getCustomerId() + 1;
        else
            id = 1;
        if(getLastAccountNumber() != 0)
            tempAccountNo = getLastAccountNumber() + 1;
        else
            tempAccountNo = 600001;

        try {
            Connection con = ConnectionDao.getConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,id);
            pst.setLong(2, tempAccountNo);
            pst.setString(3, name);
            pst.setLong(4,aadharNo);
            pst.setLong(5,phoneNo);
            pst.setInt(6,age);
            pst.setDouble(7,balance);
            out = pst.executeUpdate();
            pst.close();
            con.close();
        }catch (Exception e){
            System.out.println("Exception Caught at Connecting to the Database" + e);
        }
        if(out > 0){
            System.out.println("No of Rows Affected: "+ out);
        }else {
            System.out.println("No Rows Affected");
        }
    }
}
