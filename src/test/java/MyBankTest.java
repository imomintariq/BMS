import junit.framework.TestCase;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
@FixMethodOrder(MethodSorters.DEFAULT)
public class MyBankTest {


   private static MyBank bank;

    @BeforeClass
    public static void initialize(){
        bank = new MyBank(2);
        System.out.println("Before");
    }

    @Test
    public void testCheckSavingsAccountCase1(){
        long accNum = bank.addCustomer("Momin Tariq", "House 1A, Street 11, Soan Gardens", "0310-5446436");
        double initBalance = 100000;
        bank.createSavingsAccount(accNum, initBalance);
        Assert.assertEquals(bank.savingsExists(accNum),true);
    }

    @Test
    public void testCheckSavingsAccountCase2(){
        //There are no 3 digit account numbers so Acc Number "123" doesnot exist
        Assert.assertEquals(bank.savingsExists(123),false);
    }
    @Test
    public void testCheckCheckingAccountCase1(){

        long accNum = bank.addCustomer("Ali Ahmed", "House 5, Street 10, Media Town", "0322-01201201");
        double initBalance = 150000;
        bank.createCheckingAccount(accNum, initBalance);
        Assert.assertEquals(bank.checkingExists(accNum),true);
    }
    @Test
    public void testCheckCheckingAccountCase2(){
        //There are no 3 digit account numbers so Acc Number "123" doesnot exist
        Assert.assertEquals(bank.checkingExists(456),false);
    }
    @Test
    public void testDeleteAccount(){

        long accNum = bank.addCustomer("Haseebullah", "Mardan", "11231134");
        double initBalance = 12345;
        bank.createCheckingAccount(accNum, initBalance);
        long accNum1 = bank.addCustomer("Salaar", "Islamabad", "12314322");
        double initBalance1 = 98765;
        bank.createSavingsAccount(accNum1, initBalance1);

        Assert.assertEquals(bank.deleteCheckingAccount(accNum), true);
        Assert.assertEquals(bank.deleteSavingsAccount(accNum1), true);
        Assert.assertEquals(bank.deleteCheckingAccount(123), false);
        Assert.assertEquals(bank.deleteCheckingAccount(123), false);
    }

    @Test
    public void testDepositAmount(){
        long accNum = bank.addCustomer("Haseebullah", "Mardan", "11231134");
        double initBalance = 12345;
        bank.createCheckingAccount(accNum, initBalance);
        long accNum1 = bank.addCustomer("Salaar", "Islamabad", "12314322");
        double initBalance1 = 98765;
        bank.createSavingsAccount(accNum1, initBalance1);
        Assert.assertEquals(bank.depositAmountToCheckingAccount(accNum,1234), true);
        Assert.assertEquals(bank.depositAmountToSavingsAccount(accNum1,9876), true);
        Assert.assertEquals(bank.depositAmountToSavingsAccount(123,12345), false);
        Assert.assertEquals(bank.depositAmountToSavingsAccount(123,9876), false);

    }

    @Test
    public void testWithdrawSavings(){
        long accNum = bank.addCustomer("Abu Bakar", "Sihaala", "12345");
        double initBalance = 10000;
        bank.createSavingsAccount(accNum,initBalance);
        Assert.assertEquals(bank.withdrawAmountFromSavingsAccount(accNum,10000),true);
        Assert.assertEquals(bank.withdrawAmountFromSavingsAccount(accNum,10000),false);

    }

    @Test
    public void testWithdrawChecking(){
        long accNum = bank.addCustomer("Abu Bakar", "Sihaala", "12345");
        double initBalance = 10000;
        bank.createCheckingAccount(accNum,initBalance);
        Assert.assertEquals(bank.withDrawAmountFromCheckingAccount(accNum,10000),true);
        Assert.assertEquals(bank.withDrawAmountFromCheckingAccount(accNum,10000),false);

    }
    @Test
    public void testCheckZakaat(){
        long accNum = bank.addCustomer("Maryam", "Rawalpindi", "1234567");
        double initBalance = 300000;
        bank.createSavingsAccount(accNum,initBalance);
        double actualZakaat = (300000*2.5)/100;
        Assert.assertEquals(bank.checkZakaat(accNum),actualZakaat,1 );
    }
    @Test
    public void testBankTransfer(){
        long accNum = bank.addCustomer("Mudassir", "Waah Cant", "9876543");
        double initBalance = 500000;
        bank.createSavingsAccount(accNum,initBalance);
        long accNum1 = bank.addCustomer("Kashif", "Waah Cant", "1234567");
        double initBalance1 = 300000;
        bank.createCheckingAccount(accNum1,initBalance1);
        boolean transfered = bank.bankTransferCtoS(accNum1, accNum, 1000);
        Assert.assertEquals(bank.bankTransferCtoS(accNum1, accNum, 1000),true);
        Assert.assertEquals(bank.bankTransferStoC(accNum, accNum1, 2),true);
        Assert.assertEquals(bank.bankTransferStoC(accNum1, accNum, 1000),false);
        Assert.assertEquals(bank.bankTransferCtoS(accNum, accNum1, 1000),false);
        Assert.assertEquals(bank.bankTransferCtoC(accNum1, accNum, 1000),false);
        Assert.assertEquals(bank.bankTransferStoS(accNum, accNum1, 1000),false);

    }
    @Test
    public void testUpdateInterestRate() {
        Assert.assertEquals(2,bank.getInterestRate(),0);
        bank.updateInterestRate(3);
        Assert.assertEquals(3,bank.getInterestRate(),0);
        Assert.assertNotEquals(2,bank.getInterestRate(),0);
    }

}