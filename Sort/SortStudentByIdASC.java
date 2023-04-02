package Sort;

import Object.Student;
import java.util.Comparator;


// Tôi nghĩ là nếu dữ liệu mà xuất ra từ List thì nó
// sẽ sắp xếp bằng cách implement Comparator,
// còn mà dữ liệu xuất ra từ SQL thì nó làm đéo được . OK
public class SortStudentByIdASC implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o2.getIdStudent().compareTo(o1.getIdStudent());
    }
}
