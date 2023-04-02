package Expresstion;

public class InvalididStudentException extends Exception{
    private String id;

    public InvalididStudentException() {
    }

    public InvalididStudentException(String id) {
        this.id = id;
    }

    public InvalididStudentException(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
