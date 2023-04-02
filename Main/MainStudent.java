package Main;

import Expresstion.InvalidCCCDException;
import Expresstion.InvalididStudentException;

import Object.Student;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.Scanner;

public class MainStudent {
    //Viết ra Url
    private static final String connurl = "jdbc:sqlserver://MSI\\SQLEXPRESS:7423;" +
            "user=sa;password=7423;databaseName=StudentJava;encrypt=false";

    public static void main(String[] args) throws InvalididStudentException, InvalidCCCDException {
        Scanner in = new Scanner(System.in);
        do {
            System.out.print("" +
                    "1. Thêm mới sinh viên vào CSDL.\n" +
                    "2. Sửa thông tin sinh viên được chọn trừ mã sinh viên.\n" +
                    "3. Xóa thông tin sinh viên được chọn khỏi danh sách và CSDL.\n" +
                    "4. Hiển thị danh sách sinh viên hiện có ra màn hình.\n" +
                    "5. Sắp xếp danh sách sinh viên theo:\n" +
                    "6. Tìm kiếm sinh viên theo\n");
            System.out.print("Nhập chức năng: ");
            var n = in.nextInt();
            in.nextLine();
            Student pr = new Student();
            switch (n){
                case 1:{
                    pr.createObject();
                    //createStudentNew(in);
                }break;
                case 2:{
                    System.out.print("Nhập ID sinh viên cần tìm: ");
                    var search = in.nextLine();
                    pr.getOnlyObject(search);
                    System.out.print("Nhập Tên Lớp cần sửa: ");
                    var editClassName = in.nextLine();
                    pr.UpdateObjectInSQL(search, editClassName);
                    pr.getOnlyObject(search);
                }break;
                case 3:{
                    System.out.print("Nhập ID sinh viên cần xóa: ");
                    var search = in.nextLine();
                    pr.getOnlyObject(search);
                    if(pr.deleteObject(search) > 0){
                        System.out.println("Xóa Thành Công");
                    }else {
                        System.out.println("Xóa thất bại, Không tìm thấy Sinh Viên");
                    }
                } break;
                case 4:{
                    pr.readObject();
                } break;
                case 5:{
                    do{
                        System.out.print("" +
                                "Sắp xếp danh sách sinh viên theo:\n" +
                                "   1. Tên tăng dần a-z\n" +
                                "   2. Tên giảm dần z-a\n" +
                                "   3. Mã sinh viên tăng dần a-z\n");
                        System.out.print("Chọn chức năng: ");
                        var options = in.nextInt();
                        switch (options){
                            case 1:{
                                var sort = "ASC";
                                var valueName = "className";
                                pr.SortObject(valueName, sort);
                            } break ;
                            case 2: {
                                var sort = "DESC";
                                var valueName = "className";
                                pr.SortObject(valueName, sort);
                            } break;
                            case 3: {
                                var sort = "ASC";
                                var valueName = "IdStudent";
                                pr.SortObject(valueName, sort);
                            } break;
                        }
                    } while (true);
                }
                case 6: {
                    do{
                        System.out.print("Tìm kiếm sinh viên theo\n" +
                                "   1. Tên lớp gần đúng\n" +
                                "   2. Mã sinh viên gần đúng\n" +
                                "   3. Chuyên ngành\n");
                        System.out.print("Nhập chức năng: ");
                        var option = in.nextInt();
                        in.nextLine();
                        switch (option){
                            case 1:{
                                System.out.print("Nhập tên lớp tìm kiếm: ");
                                var search = in.nextLine();
                                var column = "className";
                                pr.SearchObject(column, search);
                            } break;
                            case 2:{
                                System.out.print("Nhập mã Sinh Viên tìm kiếm: ");
                                var search = in.nextLine();
                                var column = "IdStudent";
                                pr.SearchObject(column, search);
                            } break;
                            case 3:{
                                System.out.print("Nhập chuyên ngành tìm kiếm: ");
                                var search = in.nextLine();
                                var column = "specialized";
                                pr.SearchObject(column, search);
                            } break;
                        }
                    }while (true);
                }
            }
        }while (true);


    }
}
