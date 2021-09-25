import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MyBank {
    private List<Customers> customersList;
    double interestRate;

    //Setters And Getters
    public List<Customers> getCustomersList() {
        return customersList;
    }

    public void setCustomersList(List<Customers> customersList) {
        this.customersList = customersList;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }



    public MyBank() {
        customersList = new ArrayList<>();
        interestRate = 0;
    }

    public MyBank(double _interestRate) {
        customersList = new ArrayList<>();
        interestRate = _interestRate;
    }

    public long addCustomer(String fullName, String address, String phoneNumber){
        long accountNumber = generateRandomAccNum();
        Customers customer = new Customers(accountNumber, fullName, address, phoneNumber);
        customersList.add(customer);
        return accountNumber;
    }
    public void updateInterestRate(double _newRate){
        interestRate = _newRate;
        for(int i = 0; i < customersList.size(); i++) {
            for(int j = 0; j < customersList.get(i).accountList.size(); j++)
            {
                if(customersList.get(i).accountList.get(j) instanceof SavingsAccount)
                {
                    ((SavingsAccount) customersList.get(i).accountList.get(j)).interestRate = _newRate;
                }
            }

        }
    }
    public long generateRandomAccNum(){
        Random r = new Random();
        long accountNum=r.nextInt(10000000)+89999999;
        for(int i = 0; i < customersList.size(); i++){
            if(accountNum==customersList.get(i).getAccountNumber()){
                return generateRandomAccNum();
            }
        }
        return accountNum;
    }
    public void createSavingsAccount(long accNum, double initBalance){

        for(int i = 0; i < customersList.size(); i++) {
            if (customersList.get(i).getAccountNumber() == accNum) {

                customersList.get(i).createSavingsAccount(initBalance, interestRate);

            }
        }

    }
    public void createCheckingAccount(long accNum, double initBalance){

        for(int i = 0; i < customersList.size(); i++) {
            if (customersList.get(i).getAccountNumber() == accNum) {

                customersList.get(i).createCheckingAccount(initBalance);

            }
        }
    }

    public boolean deleteSavingsAccount(long accNum){

        for(int i = 0; i < customersList.size(); i++) {
            if (customersList.get(i).getAccountNumber() == accNum) {

                customersList.get(i).deleteSavingsAccount();
                return true;

            }
        }
        return false;
    }
    public boolean deleteCheckingAccount(long accNum){

        for(int i = 0; i < customersList.size(); i++) {
            if (customersList.get(i).getAccountNumber() == accNum) {

                customersList.get(i).deleteCheckingAccount();
                return true;
            }
        }
        return false;
    }

    public boolean depositAmountToSavingsAccount(long accNum, double _amount){
        for(int i = 0; i < customersList.size(); i++) {
            if (customersList.get(i).getAccountNumber() == accNum) {

                for(int j = 0 ; j < customersList.get(i).accountList.size(); j++) {
                    if (customersList.get(i).accountList.get(j) instanceof SavingsAccount) {
                        customersList.get(i).accountList.get(j).makeDeposit(_amount);
                        return true;
                    }
                }

            }
        }
        return false;
    }
    public boolean depositAmountToCheckingAccount(long accNum, double _amount){
        for(int i = 0; i < customersList.size(); i++) {
            if (customersList.get(i).getAccountNumber() == accNum) {

                for(int j = 0 ; j < customersList.get(i).accountList.size(); j++) {
                    if (customersList.get(i).accountList.get(j) instanceof CheckingAccount) {
                        customersList.get(i).accountList.get(j).makeDeposit(_amount);
                        return true;
                    }
                }

            }
        }
        return false;
    }
    public boolean withdrawAmountFromSavingsAccount(long accNum, double _amount){
        for(int i = 0; i < customersList.size(); i++) {
            if (customersList.get(i).getAccountNumber() == accNum) {

                for(int j = 0 ; j < customersList.get(i).accountList.size(); j++) {
                    if (customersList.get(i).accountList.get(j) instanceof SavingsAccount) {
                        boolean _return = customersList.get(i).accountList.get(j).makeWithdrawal(_amount);
                        return _return;
                    }
                }

            }
        }
        return false;
    }
    public boolean withDrawAmountFromCheckingAccount(long accNum, double _amount){
        for(int i = 0; i < customersList.size(); i++) {
            if (customersList.get(i).getAccountNumber() == accNum) {

                for(int j = 0 ; j < customersList.get(i).accountList.size(); j++) {
                    if (customersList.get(i).accountList.get(j) instanceof CheckingAccount) {
                        boolean _ret = customersList.get(i).accountList.get(j).makeWithdrawal(_amount);
                        return _ret;
                    }
                }

            }
        }
        return false;
    }
    public double checkZakaat(long accNum){
        boolean ZakaatCalculated = false;
        double zakaat= 0;
        for(int i = 0; i < customersList.size(); i++){
            if(customersList.get(i).getAccountNumber()== accNum)
            {


                for(int j = 0 ; j <customersList.get(i).accountList.size(); j++){
                    if(customersList.get(i).accountList.get(j) instanceof SavingsAccount)
                    {
                        zakaat = ((SavingsAccount) customersList.get(i).accountList.get(j)).calculateZakat();
                        ZakaatCalculated = true;
                    }
                }

            }
        }
        return zakaat;
    }

    public boolean bankTransferStoS(long senderAccNum, long recipientAccNum, double _amount){
        int[] senderId={-1};
        int[] senderAccId={-1};
        int[] recipientId={-1};
        int[] recipientAccId={-1};

        if(savingsExists(senderAccNum,senderId,senderAccId) && savingsExists(recipientAccNum,recipientId,recipientAccId)){
            customersList.get(senderId[0]).accountList.get(senderAccId[0]).makeWithdrawal(_amount);
            customersList.get(recipientId[0]).accountList.get(recipientAccId[0]).makeDeposit(_amount);
            return true;
        }
        return false;
    }

    public boolean bankTransferStoC(long senderAccNum, long recipientAccNum, double _amount){
        int[] senderId={-1};
        int[] senderAccId={-1};
        int[] recipientId={-1};
        int[] recipientAccId={-1};

        if(savingsExists(senderAccNum,senderId,senderAccId) && checkingExists(recipientAccNum,recipientId,recipientAccId)){
            customersList.get(senderId[0]).accountList.get(senderAccId[0]).makeWithdrawal(_amount);
            customersList.get(recipientId[0]).accountList.get(recipientAccId[0]).makeDeposit(_amount);
            return true;
        }

        return false;
    }
    public boolean bankTransferCtoC(long senderAccNum, long recipientAccNum, double _amount){
        int[] senderId={-1};
        int[] senderAccId={-1};
        int[] recipientId={-1};
        int[] recipientAccId={-1};

        if(checkingExists(senderAccNum,senderId,senderAccId) && checkingExists(recipientAccNum,recipientId,recipientAccId)){
            customersList.get(senderId[0]).accountList.get(senderAccId[0]).makeWithdrawal(_amount);
            customersList.get(recipientId[0]).accountList.get(recipientAccId[0]).makeDeposit(_amount);

            return true;
        }
        return false;
    }

    public boolean bankTransferCtoS(long senderAccNum, long recipientAccNum, double _amount){
        int[] senderId={-1};
        int[] senderAccId={-1};
        int[] recipientId={-1};
        int[] recipientAccId={-1};

        if(checkingExists(senderAccNum,senderId,senderAccId) && savingsExists(recipientAccNum,recipientId,recipientAccId)){
            customersList.get(senderId[0]).accountList.get(senderAccId[0]).makeWithdrawal(_amount);
            customersList.get(recipientId[0]).accountList.get(recipientAccId[0]).makeDeposit(_amount);
            return true;
        }

        return false;
    }
    public boolean savingsExists(long accNum, int [] customerId, int [] accId){
        for(int i = 0; i < customersList.size(); i++){
            if(accNum == customersList.get(i).getAccountNumber()){
                customerId[0] = i;

                for(int j = 0 ; j <customersList.get(i).accountList.size(); j++) {
                    if (customersList.get(i).accountList.get(j) instanceof SavingsAccount) {
                        accId[0] = j;
                        return true;
                    }
                }

            }
        }
        return false;
    }
    public boolean savingsExists(long accNum){
        for(int i = 0; i < customersList.size(); i++){
            if(customersList.get(i).getAccountNumber() == accNum){

                for(int j = 0 ; j <customersList.get(i).accountList.size(); j++) {
                    if (customersList.get(i).accountList.get(j) instanceof SavingsAccount) {
                        return true;
                    }
                }

            }
        }
        return false;
    }
    public boolean checkingExists(long accNum, int [] customerId, int [] accId){
        for(int i = 0; i < customersList.size(); i++){
            if(accNum == customersList.get(i).getAccountNumber()){
                customerId[0] = i;

                for(int j = 0 ; j <customersList.get(i).accountList.size(); j++) {
                    if (customersList.get(i).accountList.get(j) instanceof CheckingAccount) {
                        accId[0] = j;
                        return true;
                    }
                }

            }
        }
        return false;
    }
    public boolean checkingExists(long accNum){
        for(int i = 0; i < customersList.size(); i++){
            if(accNum == customersList.get(i).getAccountNumber()){

                for(int j = 0 ; j <customersList.get(i).accountList.size(); j++) {
                    if (customersList.get(i).accountList.get(j) instanceof CheckingAccount) {
                        return true;
                    }
                }

            }
        }
        return false;
    }
}
