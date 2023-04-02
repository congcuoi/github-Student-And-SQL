package Main;

import Expresstion.InvailEmailException;
import Expresstion.InvalidCCCDException;
import Expresstion.InvalididStudentException;
import Object.Subject;
import Object.Person;

import java.util.ArrayList;
import java.util.Scanner;

public class MainSubject {
    public static void main(String[] args) throws InvailEmailException {
        Subject pr = new Subject();
        /*try {
            pr.createObject();
        } catch (InvalididStudentException e) {
            e.getMessage();
        }*/
        pr.readObject();
    }
}
