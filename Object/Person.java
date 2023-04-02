package Object;

import Expresstion.InvailEmailException;
import Expresstion.InvalidCCCDException;
import jdk.jfr.MemoryAddress;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
    private String CCCD;
    private String name;
    private Date birthDay;
    private String address;
    private String email;
    private String numberPhone;


    public Person() {

    }

    public Person(String CCCD) throws InvalidCCCDException {
        this.setCCCD(CCCD);
    }

    public Person(String CCCD, String name, Date birthDay, String address,
                  String email, String numberPhone) throws InvailEmailException {
        this.CCCD = CCCD;
        this.name = name;
        this.birthDay = birthDay;
        this.address = address;
        this.setEmail(email);
        this.numberPhone = numberPhone;
    }

    public String getCCCD() {
        return CCCD;
    }
    public void setCCCD(String CCCD) throws InvalidCCCDException {
        var regex = "[0-9]{12}";
        Pattern patten = Pattern.compile(regex);
        Matcher matcher = patten.matcher(CCCD);
        if(matcher.matches()){
            this.CCCD = CCCD;
        }else {
            this.CCCD = null;
            var msg = "Mã Căn Cước Công Dân không đúng đinh dạng";
            throw new InvalidCCCDException(msg, CCCD);
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) throws InvailEmailException {
        var regexEmail = "^[a-z]+[0-9]+[\\\\w._]*@gmail.com$";
        Pattern patten = Pattern.compile(regexEmail, Pattern.CASE_INSENSITIVE);
        Matcher matcher = patten.matcher(email);
        if(matcher.matches()) {
            this.email = email.toLowerCase();
        }
        else {
            var msg = "Email không đúng định dạng";
            throw new InvailEmailException(msg, email);
        }
    }

    public String getNumberPhone() {
        return numberPhone;
    }
    public void setNumberPhone(String numberPhong) {
        this.numberPhone = numberPhong;
    }
}
