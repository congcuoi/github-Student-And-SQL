package InterFaceStudent;

import Expresstion.InvalidCCCDException;
import Expresstion.InvalididStudentException;

public interface ActionStudent {
    void createObject() throws InvalididStudentException;

    void readObject();

    int UpdateObjectInSQL(String search, String ValueName);

    void getOnlyObject(String search);

    void SortObject(String valueName, String sort);

    int deleteObject(String search);

    void SearchObject(String column, String search) throws InvalidCCCDException;
}
