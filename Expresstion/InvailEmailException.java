package Expresstion;

public class InvailEmailException extends Exception{
    private String Email;

    public InvailEmailException(String message, String email) {
        super(message);
        Email = email;
    }
} 
// push di
