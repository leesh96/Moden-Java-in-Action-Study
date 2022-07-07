package chap04;

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
}
