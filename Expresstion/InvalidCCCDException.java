package Expresstion;

public class InvalidCCCDException extends Exception{
    private String CCCD;

    public InvalidCCCDException() {
    }

    public InvalidCCCDException(String message, String CCCD) {
        super(message);
        this.CCCD = CCCD;
    }
}
