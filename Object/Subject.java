package Object;

import Expresstion.InvalididStudentException;
import InterFaceStudent.ActionStudent;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Subject implements ActionStudent {
    private int idSubject;
    private String nameSubject;
    private int numberSubject;
    private String typeSubject;
    private String idStudent;

    private static final String SubjectURL = "jdbc:sqlserver://MSI\\SQLEXPRESS:7423;" +
            "user=sa;password=7423;databaseName=StudentJava;encrypt=false";
    private static final Scanner in = new Scanner(System.in);

    public Subject() {
    }

    public Subject(String nameSubject, int numberSubject,
                   String typeSubject, String idStudent) {
        this.nameSubject = nameSubject;
        this.numberSubject = numberSubject;
        this.typeSubject = typeSubject;
        this.idStudent = idStudent;
    }

    public Subject(int idSubject, String nameSubject, int numberSubject,
                   String typeSubject, String idStudent) {
        this.idSubject = idSubject;
        this.nameSubject = nameSubject;
        this.numberSubject = numberSubject;
        this.typeSubject = typeSubject;
        this.idStudent = idStudent;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public int getNumberSubject() {
        return numberSubject;
    }

    public void setNumberSubject(int numberSubject) {
        this.numberSubject = numberSubject;
    }

    public String getTypeSubject() {
        return typeSubject;
    }

    public void setTypeSubject(String typeSubject) {
        this.typeSubject = typeSubject;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) throws InvalididStudentException {
        Student pr = new Student();
        pr.setIdStudent(idStudent);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "idSubject=" + idSubject +
                ", nameSubject='" + nameSubject + '\'' +
                ", numberSubject=" + numberSubject +
                ", typeSubject='" + typeSubject + '\'' +
                '}';
    }

    private void showListObject(List<Subject> Subject) {
        System.out.printf("%-20s%-25s%-25s%-25s%-15s\n", "Mã Môn Học",
                "Tên Môn Học", "Số tiết", "Loại Môn", "Mã Sinh Viên");
        for (var pr: Subject) {
            System.out.printf("%-20s%-25s%-25s%-25s%-15s\n", pr.getIdSubject(),
                    pr.getNameSubject(), pr.getNumberSubject(), pr.getTypeSubject(), pr.getIdStudent());
        }
    }

    private void showObject(Subject sub) {
        System.out.printf("%-20s%-25s%-25s%-25s%-15s\n", "Mã Môn Học",
                "Tên Môn Học", "Số tiết", "Loại Môn", "Mã Sinh Viên");
        System.out.printf("%-20s%-25s%-25s%-25s%-15s\n", sub.getIdSubject(),
                sub.getNameSubject(), sub.getNumberSubject(), sub.getTypeSubject(), sub.getIdStudent());
    }


    /*------------------ Phương Thức Môn Học------------------*/

    @Override
    public void createObject() throws InvalididStudentException {
        System.out.print("Nhập Tên Môn Học: ");
        var name = in.nextLine();
        System.out.print("Nhập số tiết học: ");
        var number = in.nextInt();
        in.nextLine();
        System.out.print("Nhập loại môn học: ");
        var option = in.nextLine();
        System.out.print("Nhập Mã Sinh viên: ");
        var idStudent = in.nextLine();
        checkIDStudent(idStudent);
        Subject pr = new Subject(name, number, option, idStudent);

        var Insert = "INSERT INTO SubjectJava(nameSubject, numberSubject, typeSubject, IdStudent) " +
                "VALUES(" + "'" + pr.getNameSubject() + "'," + "'" + pr.getNumberSubject()
                + "'," + "'" + pr.getTypeSubject() + "'," + "'"
                + pr.getIdStudent()+ "')";
        try (var conn = DriverManager.getConnection(SubjectURL)){
            // Tạo đối tượng statement
            var statement = conn.createStatement();
            statement.executeUpdate(Insert);
            System.out.println("Tạo Môn Học Thành Công");
        } catch (SQLException e) {
            System.out.println("Mã Sinh Viên Không có trong ");
            e.getMessage();
        }
    }

    @Override
    public void readObject() {
        List<Subject> listSub = new ArrayList<>();
            try(var conn = DriverManager.getConnection(SubjectURL)){
                var Subject = "SELECT * FROM SubjectJava ";
                var statement = conn.createStatement();
                var resulSet = statement.executeQuery(Subject);
                while (resulSet.next()){
                    var id = resulSet.getInt(1);
                    var name = resulSet.getString(2);
                    var number = resulSet.getInt(3);
                    var type = resulSet.getString(4);
                    var idStudent = resulSet.getString(5);

                    Subject pr = new Subject(id, name, number, type, idStudent);
                    listSub.add(pr);
                }
                showListObject(listSub);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public int UpdateObjectInSQL(String search, String ValueName) {
        try(var conn = DriverManager.getConnection(SubjectURL)){
            var update = "UPDATE SubjectJava SET nameSubject = '" + ValueName +
                    "' WHERE IdSubject = " + search;
            var statement = conn.createStatement();
            return statement.executeUpdate(update);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getOnlyObject(String search) {
        try(var conn = DriverManager.getConnection(SubjectURL)){
            var Subject = "SELECT * FROM SubjectJava WHERE IdSubject = "+ search;
            var statement = conn.createStatement();
            var resulSet = statement.executeQuery(Subject);
            while (resulSet.next()){
                var id = resulSet.getInt(1);
                var name = resulSet.getString(2);
                var number = resulSet.getInt(3);
                var type = resulSet.getString(4);
                var idStudent = resulSet.getString(5);

                Subject pr = new Subject(id, name, number, type, idStudent);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void SortObject(String valueName, String sort) {
        List<Subject> ListSub = new ArrayList<>();
        try (var conn = DriverManager.getConnection(SubjectURL)) {
            var SORT = "SELECT * FROM Subject ORDER BY " + valueName + " " +  sort;
            var statement = conn.createStatement();
            var resulSet = statement.executeQuery(SORT);
            while (resulSet.next()){
                var id = resulSet.getInt(1);
                var name = resulSet.getString(2);
                var number = resulSet.getInt(3);
                var type = resulSet.getString(4);
                var idStudent = resulSet.getString(5);
                Subject pr = new Subject(id, name, number, type, idStudent);
                ListSub.add(pr);
            }
            showListObject(ListSub);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public int deleteObject(String search) {
        try(var conn = DriverManager.getConnection(SubjectURL)){
            var Subject = "DELETE * FROM SubjectJava WHERE IdSubject = "+ search;
            var statement = conn.createStatement();
            return statement.executeUpdate(Subject);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void SearchObject(String column, String search) {
        List<Subject> listSub = new ArrayList<>();
        try(var conn = DriverManager.getConnection(SubjectURL)) {
            var Like = "SELECT * FROM SubjectJava WHERE " + column + " LIKE '%" + search + "%'";
            var statement = conn.prepareStatement(Like);
            var resulSet =  statement.executeQuery();
            while (resulSet.next()){
                var id = resulSet.getInt(1);
                var name = resulSet.getString(2);
                var number = resulSet.getInt(3);
                var type = resulSet.getString(4);
                var idStudent = resulSet.getString(5);

                Subject pr = new Subject(id, name, number, type, idStudent);
                listSub.add(pr);
            }

            if(listSub.size() > 0){
                showListObject(listSub);
            }else {
                System.out.println("Không tìm thấy Sinh Viên");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /** chưa Xong
     * @param
     * @param
     */
    public void checkIDStudent(String IdStud){
        try(var conn = DriverManager.getConnection(SubjectURL)){
            var IdSt = "SELECT IdStudent FROM StudentInformation WHERE IdStudent = '"+ IdStud +"'";
            var statement = conn.createStatement();
            statement.executeQuery(IdSt);
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
