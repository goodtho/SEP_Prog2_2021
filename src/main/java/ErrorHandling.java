public class ErrorHandling {
    /**
     * Method that throws an exception
     * @throws IllegalArgumentException if key is null
     */
    public void asdf(String key) {
        if(key== null) {
            throw new IllegalArgumentException("key is null");
        }
    }

    /**
     * Call exception throwing method
     */
    public void xyz() {
        try {
            asdf("1234");
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Recovery
     */
    public void qwer() {
        boolean succes = false;
        int attempts = 0;
        int maxAttempts = 5;
        do {
            try {
                asdf("1234");
                succes = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                attempts++;
            }
        } while (!succes && attempts<maxAttempts);
    }
}
