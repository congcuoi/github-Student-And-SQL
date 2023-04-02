package Object;

import Expresstion.InvalidCCCDException;
import Expresstion.InvalididStudentException;
import InterFaceStudent.ActionStudent;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Student extends Person
        implements ActionStudent {
    private String idStudent;
    private String classStudent;
    private String specialized;
    private String schoolYear;

    public Student() {

    }

    public Student(String idStudent) throws InvalididStudentException {
        setIdStudent(idStudent);
    }

    public Student(String idStudent, String classStudent, String specialized,
                   String schoolYear)
            throws InvalididStudentException {
        this(idStudent);
        this.classStudent = classStudent;
        this.specialized = specialized;
        this.schoolYear = schoolYear;
    }

    public Student(String idStudent, String classStudent, String specialized,
                   String schoolYear , String CCCD)
            throws InvalididStudentException, InvalidCCCDException {
        super(CCCD);
        this.idStudent = idStudent;
        this.classStudent = classStudent;
        this.specialized = specialized;
        this.schoolYear = schoolYear;

    }

    public String getIdStudent() {
        return idStudent;
    }
    public void setIdStudent(String idStudent) throws InvalididStudentException {
        var regex = "^(a|b|c){1}\\d{2}[a-z]{4}\\d{3}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        // Pattern : Tạo một đối tượng , có chuẩn regex đã định nghĩa
        Matcher matcher = pattern.matcher(idStudent);
        // Matcher : so sánh đối tượng pattern và đầu vào có khớp không
        if(matcher.matches()){
            this.idStudent = idStudent;
        }else {
            this.idStudent = null;
            var msg = "Mã Sinh Viên Nhập Không hợp lệ";
            throw new InvalididStudentException(msg, idStudent);
        }

    }

    public String getClassStudent() {
        return classStudent;
    }
    public void setClassStudent(String classStudent) {
        this.classStudent = classStudent;
    }

    public String getSpecialized() {
        return specialized;
    }
    public void setSpecialized(String specialized) {
        this.specialized = specialized;
    }

    public String getSchoolYear() {
        return schoolYear;
    }
    public void setSchoolYear(String schoolYear) {
        var formatDate = "yyyy/MM/dd";
        SimpleDateFormat date = new SimpleDateFormat(formatDate);
        try{
            date.parse(schoolYear);
            this.schoolYear = schoolYear;
        } catch (ParseException e) {
            System.out.println("Vui lòng nhập ngày sinh đúng mã định dạng");
            e.getMessage();
        }
    }


    /*------------------------- Các Phương thức ------------------------*/

    private static final String connurl = "jdbc:sqlserver://MSI\\SQLEXPRESS:7423;" +
            "user=sa;password=7423;databaseName=StudentJava;encrypt=false";
    private static final Scanner in = new Scanner(System.in);



    /** Hiện thị danh sách sinh viên
     * @param
     * @param
     */
    public void showListObject(List<Student> students) {
        System.out.printf("%-20s%-25s%-25s%-25s%-15s\n", "Mã SV",
                "Họ Tên", "Chuyên Ngành", "Niên Khóa", "Căn Cước Công Dân");
        for (var pr : students) {
            System.out.printf("%-20s%-25s%-25s%-25s%-15s\n", pr.getIdStudent(),
                    pr.getClassStudent(), pr.getSpecialized(), pr.getSchoolYear(), pr.getCCCD());
        }
    }

    public static void showObject(Student pr){
        System.out.printf("%-20s%-25s%-25s%-25s%-15s\n", "Mã SV",
                "Tên Lớp", "Chuyên Ngành", "Niên Khóa", "Căn Cước Công Dân");
        System.out.printf("%-20s%-25s%-25s%-25s%-15s\n", pr.getIdStudent(),
                pr.getClassStudent(), pr.getSpecialized(), pr.getSchoolYear(), pr.getCCCD());
    }


    /** Tạo một đối tượng Student
     * @param
     * @param
     */
    @Override
    public void createObject() throws InvalididStudentException {
        System.out.print("Nhập Mã Sinh Viên: ");
        var id = in.nextLine();
        System.out.print("Nhập Tên Lớp: ");
        var name = in.nextLine();
        System.out.print("Nhập Chuyên Ngành: ");
        var spec = in.nextLine();
        System.out.print("Nhập Niên Khóa: ");
        var years = in.nextLine();

        Student pr = new Student(id, name, spec, years);
        var insert = "INSERT INTO StudentInformation(IdStudent, className, " +
                "specialized, schoolYear)" +
                "VALUES(" + "'" + pr.getIdStudent() + "'," + "'" + pr.getClassStudent() + "'," + "'"
                + pr.getSpecialized() + "'," + "'" + pr.getSchoolYear() + "')";
        try (var conn = DriverManager.getConnection(connurl)){
            var stt = conn.createStatement();
            stt.executeUpdate(insert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Đọc danh sanh Student trong SQL
     * @param
     * @param
     */
    @Override
    public void readObject() {
        List<Student> students = new ArrayList<>();
        try (var conn = DriverManager.getConnection(connurl)){   // làm vc giữa java vs SQL , Tạo ra statement ,
            // PreparedStatement, và DatabaseMetaData
            var SQL = "SELECT * FROM StudentInformation";
            var ps = conn.prepareStatement(SQL); // Đối tượng preprareStatement : quét qua dữ liệu xong lưu trữ
            // trong một đống tượng preprareStatement
            var resulSet = ps.executeQuery();  // Lấy đối tượng resulSet
            while (resulSet.next()){
                var id = resulSet.getString(1);
                var name = resulSet.getString(2);
                var specia = resulSet.getString(3);
                var schoolyear = resulSet.getString(4);
                var CCCD = resulSet.getString(5);

                Student pr = new Student(id, name, specia, schoolyear, CCCD);
                students.add(pr);
            }
            showListObject(students);
        } catch ( InvalididStudentException | SQLException | InvalidCCCDException e) {
            throw new RuntimeException(e);
        }
    }

    /** Phương thức sửa Thông tin
     * @param
     * @param
     */
    @Override
    public int UpdateObjectInSQL(String search, String ValueName) {
        try(var conn = DriverManager.getConnection(connurl)) //Mở Cổng URL và tạo luôn đối tượng Connection
        {
            var editStudent = "UPDATE StudentInformation SET className ='" + ValueName + "' WHERE IdStudent = '"
                    + search + "'";
            var createStatement = conn.createStatement();
            return createStatement.executeUpdate(editStudent);
            //executeUpdate : sửa thông tin sinh viên
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** lấy ra một đối tượng Student theo yêu cầu tìm kiếm
     * @param
     * @param
     */
    @Override
    public void getOnlyObject(String search) {
        try(var conn = DriverManager.getConnection(connurl)) //Mở Cổng URL và tạo luôn đối tượng Connection
        {
            var select = "SELECT * FROM StudentInformation WHERE IdStudent = '" + search + "'";
            var paSta = conn.prepareStatement(select);  // quét qua dữ liệu là lấy ra toàn bộ thông tin
            // của sinh viên cần tìm
            var resulSet = paSta.executeQuery(); // Lấy đối tượng
            while (resulSet.next()){
                var id = resulSet.getString(1);
                var name = resulSet.getString(2);
                var specia = resulSet.getString(3);
                var schoolyear = resulSet.getString(4);
                var CCCD = resulSet.getString(5);

                Student pr = new Student(id, name, specia, schoolyear, CCCD);
                showObject(pr);
            }
        } catch (SQLException | InvalididStudentException e) {
            throw new RuntimeException(e);
        } catch (InvalidCCCDException e) {
            throw new RuntimeException(e);
        }
    }

    /** Sắp xếp Sinh viên
     * @param
     * @param
     */
    @Override
    public void SortObject(String valueName, String sort) {
        List<Student> students = new ArrayList<>();
        try (var conn = DriverManager.getConnection(connurl)) {
            var ASC = "SELECT * FROM StudentInformation ORDER BY " + valueName + " " +  sort;
            var statement = conn.createStatement();
            var resulSet = statement.executeQuery(ASC);
            while (resulSet.next()){
                var id = resulSet.getString(1);
                var name = resulSet.getString(2);
                var specia = resulSet.getString(3);
                var schoolyear = resulSet.getString(4);
                var CCCD = resulSet.getString(5);
                Student pr = new Student(id, name, specia, schoolyear, CCCD);
                students.add(pr);
            }
            showListObject(students);
        } catch (SQLException | InvalididStudentException e) {
            throw new RuntimeException(e);
        } catch (InvalidCCCDException e) {
            throw new RuntimeException(e);
        }
    }

    /** Xóa Sinh Viên
     * @param
     * @param
     */
    @Override
    public int deleteObject(String search) {
        try(var conn = DriverManager.getConnection(connurl)) {
            var deleteStudent =  "DELETE FROM StudentInformation WHERE IdStudent = '" + search + "'";
            var statement = conn.createStatement();
            return statement.executeUpdate(deleteStudent);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** Tìm Sinh Viên
     * @param
     * @param
     */
    @Override
    public void SearchObject(String column, String search)
            throws InvalidCCCDException {
        List<Student> students = new ArrayList<>();
        try(var conn = DriverManager.getConnection(connurl)) {
            var Like = "SELECT * FROM StudentInformation WHERE " + column + " LIKE '%" + search + "%'";
            var statement = conn.prepareStatement(Like);
            var resulSet =  statement.executeQuery();
            while (resulSet.next()){
                var id = resulSet.getString(1);
                var name = resulSet.getString(2);
                var specia = resulSet.getString(3);
                var schoolyear = resulSet.getString(4);
                var CCCD = resulSet.getString(5);

                Student pr = new Student(id, name, specia, schoolyear, CCCD);
                students.add(pr);
            }

            if(students.size() > 0){
                showListObject(students);
            }else {
                System.out.println("Không tìm thấy Sinh Viên");
            }

        } catch (SQLException | InvalididStudentException e) {
            throw new RuntimeException(e);
        } catch (InvalidCCCDException e) {
            throw new RuntimeException(e);
        }
    }
}
