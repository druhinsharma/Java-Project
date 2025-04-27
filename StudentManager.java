import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private ArrayList<Student> studentList = new ArrayList<>();

    public void addStudent(Student student) throws IllegalArgumentException {
        for (Student s : studentList) {
            if (s.getId().equalsIgnoreCase(student.getId())) {
                throw new IllegalArgumentException("Duplicate ID! Student already exists.");
            }
        }
        studentList.add(student);
        System.out.println("✅ Student added successfully!");
    }

    public void deleteStudent(String id) {
        Student toRemove = findStudent(id);
        if (toRemove != null) {
            studentList.remove(toRemove);
            System.out.println("✅ Student deleted.");
        } else {
            System.out.println("❌ Student not found.");
        }
    }

    public void viewStudents() {
        if (studentList.isEmpty()) {
            System.out.println("❌ No students found.");
        } else {
            for (Student student : studentList) {
                System.out.println(student);
            }
        }
    }

    public void searchStudent(String id) {
        Student student = findStudent(id);
        if (student != null) {
            System.out.println("✅ Found: " + student);
        } else {
            System.out.println("❌ Not found.");
        }
    }

    public Student findStudent(String id) {
        for (Student s : studentList) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return studentList;
    }

    public int getTotalStudents() {
        return studentList.size();
    }
}
