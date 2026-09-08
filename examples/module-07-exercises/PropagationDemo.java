public class PropagationDemo {
    // DONE: throws InsufficientFundsException
    static void accountLayer() throws InsufficientFundsException {
        // Deepest layer creates the domain failure.
        // DONE: throw new InsufficientFundsException(100.00, 150.00)
        throw new InsufficientFundsException(100.00, 150.00);
    }

    static void serviceLayer() throws InsufficientFundsException { // DONE: throws InsufficientFundsException
        // No recovery here, so declare and let it propagate.
        accountLayer();
    }

    static void menuLayer() throws InsufficientFundsException{ // DONE: throws InsufficientFundsException
        // Still no recovery action; keep the contract.
        serviceLayer();
    }

    public static void main(String[] args) {
        try {
            menuLayer();
        } catch (InsufficientFundsException ex) { // DONE: catch InsufficientFundsException
            // DONE: print "Caught at main: " + ex.getMessage()
            System.out.println("Caught at main: " + ex.getMessage());
            // DONE: ex.printStackTrace(System.out)
            ex.printStackTrace(System.out);
        }
    }
}
