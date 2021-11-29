import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class
import java.io.*;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.Object;

import static oracle.jdbc.OracleDriver.getJDBCVersion;

public class Main
{

    public static void createFile(String filename) {
        try {
            File myObj = new File(filename);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void WriteToFile(String filename, String _string) {

        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.append(_string);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void appendStrToFile(String fileName,
                                       String str)
    {
        try(FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(str);
            out.close();
            System.out.println("Data Written Successfully");
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }
    public static void readFile(String filename) {
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void updateInterestRate(double _newRate, List<Customers> _customersList){
        for(int i = 0; i < _customersList.size(); i++) {
            for(int j = 0; j < _customersList.get(i).accountList.size(); j++)
            {
                if(_customersList.get(i).accountList.get(j) instanceof SavingsAccount)
                {
                    ((SavingsAccount) _customersList.get(i).accountList.get(j)).interestRate = _newRate;
                }
            }

        }
    }
    public static long generateRandomAccNum(List<Customers> _customerList){
        Random r = new Random();
        long accountNum=r.nextInt(10000000)+89999999;
        for(int i = 0; i < _customerList.size(); i++){
            if(accountNum==_customerList.get(i).getAccountNumber()){
                return generateRandomAccNum(_customerList);
            }
        }
        return accountNum;
    }
    public static void createAccount(List<Customers> _customerList, Customers _customer, double interestRate){
        boolean accountCreated = false;
        for(int i = 0; i < _customerList.size(); i++){
            if(_customerList.get(i).getAccountNumber()==_customer.getAccountNumber())
            {
                System.out.println("1. Savings Account");
                System.out.println("2. Checking Account");

                Scanner input = new Scanner(System.in);
                int choice = input.nextInt();
                double initBalance;
                switch (choice){
                    case 1:
                        System.out.println("Enter initial Balance");
                        initBalance = input.nextInt();
                        _customerList.get(i).createSavingsAccount(initBalance, interestRate);

                        accountCreated =true;
                        break;
                    case 2:
                        System.out.println("Enter initial Balance");
                        initBalance = input.nextInt();
                        _customerList.get(i).createCheckingAccount(initBalance);
                        accountCreated =true;
                        break;

                }
            }
        }

        if(accountCreated == true){
            System.out.println("Account has been Created");
            accountCreated =false;
        }
        else{
            System.out.println("Account Number does not exist");
        }

    }

    public static void deleteAccount(List<Customers> _customerList, Customers _customer, double interestRate){
        boolean accountDeleted = false;
        for(int i = 0; i < _customerList.size(); i++){
            if(_customerList.get(i).getAccountNumber()==_customer.getAccountNumber())
            {
                System.out.println("1. Savings Account");
                System.out.println("2. Checking Account");

                Scanner input = new Scanner(System.in);
                int choice = input.nextInt();
                double initBalance;
                switch (choice){
                    case 1:
                        for(int j = 0 ; j <_customerList.get(i).accountList.size(); j++){
                            if(_customerList.get(i).accountList.get(j) instanceof SavingsAccount)
                            {
                                _customerList.get(i).deleteSavingsAccount();
                                accountDeleted = true;
                            }
                        }
                        break;
                    case 2:
                        for(int j = 0 ; j <_customerList.get(i).accountList.size(); j++){
                            if(_customerList.get(i).accountList.get(j) instanceof CheckingAccount)
                            {
                                _customerList.get(i).deleteCheckingAccount();
                                accountDeleted = true;
                            }
                        }
                        break;

                }
            }
        }

        if(accountDeleted == true){
            System.out.println("Account has been Deleted");
            accountDeleted =false;
        }
        else{
            System.out.println("Account Number does not exist");
        }

    }

    public static void depositAmount(List<Customers> _customerList, Customers _customer, double interestRate){
        boolean amountDeposited = false;
        for(int i = 0; i < _customerList.size(); i++){
            if(_customerList.get(i).getAccountNumber()==_customer.getAccountNumber())
            {
                System.out.println("1. Savings Account");
                System.out.println("2. Checking Account");

                Scanner input = new Scanner(System.in);
                int choice = input.nextInt();
                double initBalance;
                switch (choice){
                    case 1:
                        for(int j = 0 ; j <_customerList.get(i).accountList.size(); j++){
                            if(_customerList.get(i).accountList.get(j) instanceof SavingsAccount)
                            {
                                System.out.println("Enter Amount: ");
                                double amount = input.nextDouble();
                                _customerList.get(i).accountList.get(j).makeDeposit(amount);
                                amountDeposited = true;
                            }
                        }
                        break;
                    case 2:
                        for(int j = 0 ; j <_customerList.get(i).accountList.size(); j++){
                            if(_customerList.get(i).accountList.get(j) instanceof CheckingAccount)
                            {
                                System.out.println("Enter Amount: ");
                                double amount = input.nextDouble();
                                _customerList.get(i).accountList.get(j).makeDeposit(amount);
                                amountDeposited = true;
                            }
                        }
                        break;

                }
            }
        }

        if(amountDeposited == true){
            System.out.println("Amount has been deposited");
            amountDeposited =false;
        }
        else{
            System.out.println("Account Number does not exist");
        }

    }

    public static void withdrawAmount(List<Customers> _customerList, Customers _customer, double interestRate){
        boolean amountWithdrawn = false;
        for(int i = 0; i < _customerList.size(); i++){
            if(_customerList.get(i).getAccountNumber()==_customer.getAccountNumber())
            {
                System.out.println("1. Savings Account");
                System.out.println("2. Checking Account");

                Scanner input = new Scanner(System.in);
                int choice = input.nextInt();
                double initBalance;
                switch (choice){
                    case 1:
                        for(int j = 0 ; j <_customerList.get(i).accountList.size(); j++){
                            if(_customerList.get(i).accountList.get(j) instanceof SavingsAccount)
                            {
                                System.out.println("Enter Amount: ");
                                double amount = input.nextDouble();
                                _customerList.get(i).accountList.get(j).makeWithdrawal(amount);
                                amountWithdrawn = true;
                            }
                        }
                        break;
                    case 2:
                        for(int j = 0 ; j <_customerList.get(i).accountList.size(); j++){
                            if(_customerList.get(i).accountList.get(j) instanceof CheckingAccount)
                            {
                                System.out.println("Enter Amount: ");
                                double amount = input.nextDouble();
                                _customerList.get(i).accountList.get(j).makeWithdrawal(amount);
                                amountWithdrawn = true;
                            }
                        }
                        break;

                }
            }
        }

        if(amountWithdrawn == true){
            System.out.println("Amount has been Withdrawn");
            amountWithdrawn =false;
        }
        else{
            System.out.println("Account Number does not exist");
        }

    }
    public static void checkZakaat(List<Customers> _customerList, Customers _customer, double interestRate){
        boolean ZakaatCalculated = false;
        for(int i = 0; i < _customerList.size(); i++){
            if(_customerList.get(i).getAccountNumber()==_customer.getAccountNumber())
            {


                        for(int j = 0 ; j <_customerList.get(i).accountList.size(); j++){
                            if(_customerList.get(i).accountList.get(j) instanceof SavingsAccount)
                            {
                                ((SavingsAccount) _customerList.get(i).accountList.get(j)).calculateZakat();
                                ZakaatCalculated = true;
                            }
                        }

            }
        }

        if(ZakaatCalculated == true){
            System.out.println("Zakaat has been Calculated");
            ZakaatCalculated =false;
        }
        else{
            System.out.println("Account Number does not exist");
        }

    }

    public static void bankTransfer(List<Customers> _customerList, Customers _customer, double interestRate){
        boolean amountTransfered = false;
        for(int i = 0; i < _customerList.size(); i++){
            if(_customerList.get(i).getAccountNumber()==_customer.getAccountNumber())
            {
                System.out.println("1. Savings Account");
                System.out.println("2. Checking Account");

                Scanner input = new Scanner(System.in);
                int choice = input.nextInt();
                double initBalance;
                switch (choice){
                    case 1:
                        for(int j = 0 ; j <_customerList.get(i).accountList.size(); j++){
                            if(_customerList.get(i).accountList.get(j) instanceof SavingsAccount)
                            {
                                System.out.println("Enter Amount: ");
                                double amount = input.nextDouble();
                                System.out.println("Enter the account you want to tranfer to");
                                long _tAccNum = input.nextLong();
                                int[] _tIndex = {-1};
                                if(isAccountPresent(_tAccNum, _tIndex, _customerList)){
                                    _customerList.get(i).accountList.get(j).makeWithdrawal(amount);
                                    _customerList.get(_tIndex[0]).accountList.get(0).makeDeposit(amount);
                                    amountTransfered = true;
                                }



                            }
                        }
                        break;
                    case 2:
                        for(int j = 0 ; j <_customerList.get(i).accountList.size(); j++){
                            if(_customerList.get(i).accountList.get(j) instanceof CheckingAccount)
                            {
                                System.out.println("Enter Amount: ");
                                double amount = input.nextDouble();
                                System.out.println("Enter the account you want to tranfer to");
                                long _tAccNum = input.nextLong();
                                int[] _tIndex = {-1};
                                if(isAccountPresent(_tAccNum, _tIndex, _customerList)){
                                    _customerList.get(i).accountList.get(j).makeWithdrawal(amount);
                                    _customerList.get(_tIndex[0]).accountList.get(0).makeDeposit(amount);
                                    amountTransfered = true;
                                }
                            }
                        }
                        break;

                }
            }
        }

        if(amountTransfered == true){
            System.out.println("Amount has been transfered");
            amountTransfered =false;
        }
        else{
            System.out.println("Account Number does not exist");
        }

    }


    public static boolean isAccountPresent(long accNum, int [] index, List<Customers> _customersList){
        for(int i = 0; i < _customersList.size(); i++){
            if(accNum == _customersList.get(i).getAccountNumber()){
                index[0] = i;
                return true;
            }
        }
        return false;
    }



    public static void main(String[] args){
        List<Customers> customersList = new ArrayList<Customers>();
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream

        double interestRate = 0.5;

        Scanner input = new Scanner(System.in);
        boolean mainLoop = true;
        int choice;
        while(true){
            System.out.println("1. SignUp(New Customer)");//Open a New Account, Close an account
            System.out.println("2. Login to your account(Existing Customer)");//Login to a specific account by providing the unique account number//Perform account operations as mentioned earlier
            System.out.println("3. Specify the Interest Rate");//Specify the Interest Rate, applicable to all Saving Accounts
            System.out.println("4. Display all account details, including the bank owner details");//Display all account details, including the bank owner details
            System.out.println("5. Display all accounts deductions along with account details");//Display all accounts deductions along with account details
            System.out.println("6. Save Data");
            System.out.println("7. Exit Program");
            choice = input.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Enter Your Name");
                    String name= sc.nextLine();

                    System.out.println("Enter Your Address");
                    String address= sc.nextLine();

                    System.out.println("Enter Your Phone Number");
                    String phone =  sc.nextLine();

                    long accNumber = generateRandomAccNum(customersList);
                    System.out.println("Your Account Number is: " + accNumber);
                    Customers customer = new Customers(accNumber, name,address, phone);
                    customersList.add(customer);
                    System.out.println("You are now a registered Bank Customer");

                    System.out.println("Where do you want to save your data");
                    System.out.println("1. File");
                    System.out.println("2. Oracle");
                    System.out.println("3. MySql");
                    int db = input.nextInt();
                    if(db<1 || db > 3){
                        System.out.println("Invalid Input");
                    }
                    else if(db == 1){
                        saveInFile();
                    }
                    else if(db == 2){
                        saveInOracle(customer);
                    }
                    else if(db == 3){
                        saveInMySql();
                    }

                    System.out.println("1. Create an Account");
                    System.out.println("2. Back to Menu");
                    int opt = input.nextInt();
                    switch(opt){
                        case 1:
                            createAccount(customersList,customer,interestRate);
                            break;
                        case 2:
                            break;
                        default:
                            System.out.println("incorrect Option");
                            break;

                }
                    break;
                case 2:
                    System.out.println("Login:");
                    System.out.println("provide your account number");
                    int index[] = {-1};
                    int number = sc.nextInt();
                    if(isAccountPresent(number,index, customersList)){
                        System.out.println("1. Create an Account");
                        System.out.println("2. Delete an Account");
                        System.out.println("3. Deposit");
                        System.out.println("4. Withdraw");
                        System.out.println("5. Bank Transfer");
                        System.out.println("6. Calculate Zakaat");
                        System.out.println("7. Check Balance");
                        System.out.println("8. Return to Menu");

                        int opt1 = input.nextInt();
                        switch (opt1){
                            case 1:
                                createAccount(customersList,customersList.get(index[0]),interestRate);
                                break;
                            case 2:
                                deleteAccount(customersList,customersList.get(index[0]),interestRate);
                                break;
                            case 3:
                                depositAmount(customersList,customersList.get(index[0]),interestRate);
                                break;
                            case 4:
                                withdrawAmount(customersList,customersList.get(index[0]),interestRate);
                                break;
                            case 5:
                                bankTransfer(customersList,customersList.get(index[0]),interestRate);
                                break;
                            case 6:
                                checkZakaat(customersList,customersList.get(index[0]),interestRate);
                                break;
                            case 7:
                                customersList.get(index[0]).checkBalance();
                                break;
                            case 8:
                                break;
                            default:
                                System.out.println("invalid Option");
                                break;

                        }
                    }
                    else{
                        System.out.println("Invalid Account Number");
                    }
                    break;
                case 3:
                    //Specify the Interest Rate
                    System.out.println("Enter New Interest Rate");
                    double newInterest = sc.nextDouble();
                    updateInterestRate(newInterest, customersList);
                    interestRate = newInterest;
                    break;
                case 4:
                    for(int i = 0; i < customersList.size() ;i++){
                        customersList.get(i).displayAllAccounts();
                    }
                    break;
                case 5:
                    for(int i = 0; i < customersList.size() ;i++){
                        customersList.get(i).displayAllDeductions();
                    }
                    break;

                case 6:
                    System.out.println("Exiting Program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input...");
                    break;
            }
        }
    }

    private static void saveInMySql() {
    }

    private static void saveInOracle(Customers customer) {
     /*   try{

            System.out.println(getJDBCVersion());
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver Loaded");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1523:mominn","scott", "tiger");
            System.out.println("Connection Established");
            String sql = "INSERT INTO SCOTT.ACCOUNT_HOLDER (NAME, ADDRESS, PHONE_NUMBER, ACCOUNT_NUMBER) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, customer.getFullName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPhoneNumber());
            statement.setLong(4, customer.getAccountNumber());
            int rowInserted = statement.executeUpdate();
            if(rowInserted> 0){
                System.out.println("Inserted into Oracle");
            }
        }catch(Exception e){
            System.out.println("Exception Thrown");
        }*/
    }

    private static void saveInFile() {
    }
}

