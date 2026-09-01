package bank;   // second class in the bank package to test bank.*

public class Interest {
    public static double simple(double balance, double rate, int years) {
        return balance * rate * years;
    }
}
