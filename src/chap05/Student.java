package chap05;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private final int id;

    private final int age;

    private final Gender gender;

    private final String name;

    private final int academicYear;

    public Student(int id, int age, Gender gender, String name, int academicYear) {
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.name = name;
        this.academicYear = academicYear;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, age=%d, gender=%s, name=%s, 학년=%d}", id, age, gender, name, academicYear);
    }

    public static List<Student> getSampleList() {
        List<Student> ret = new ArrayList<>();
        ret.add(new Student(1033, 21, Gender.MALE, "Kim", 2));
        ret.add(new Student(1045, 23, Gender.FEMALE, "Lee", 4));
        ret.add(new Student(1011, 22, Gender.FEMALE, "Kim", 3));
        ret.add(new Student(1028, 20, Gender.MALE, "Park", 1));
        ret.add(new Student(1030, 25, Gender.MALE, "Lee", 3));
        ret.add(new Student(1014, 24, Gender.FEMALE, "Lee", 3));
        ret.add(new Student(1026, 27, Gender.MALE, "Park", 4));
        ret.add(new Student(1024, 21, Gender.MALE, "Kim", 1));
        ret.add(new Student(1036, 22, Gender.FEMALE, "Kim", 2));

        return ret;
    }
}
