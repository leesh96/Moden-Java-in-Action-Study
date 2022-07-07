package chap04;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAndCollection {

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1033, 21, Gender.MALE, "Kim", 2));
        students.add(new Student(1045, 23, Gender.FEMALE, "Lee", 4));
        students.add(new Student(1011, 22, Gender.FEMALE, "Kim", 3));
        students.add(new Student(1028, 20, Gender.MALE, "Park", 1));

        // 외부 반복 - 반복자 사용하기
        List<String> names = new ArrayList<>();
        Iterator<Student> studentIterator = students.iterator();
        while (studentIterator.hasNext()) {
            Student student = studentIterator.next();
            names.add(student.getName());
        }
        System.out.println(names);

        // 외부 반복 - for-each 루프 사용하기
        names = new ArrayList<>();
        for (Student student : students) {
            names.add(student.getName());
        }
        System.out.println(names);

        // 스트림은 내부 반복
        Stream<Student> studentStream = students.stream();
        List<String> namesByStream = studentStream.sorted(Comparator.comparing(Student::getName))
                .map(Student::getName)
                .collect(Collectors.toList());
        System.out.println(namesByStream);

        // 스트림은 한 번만 사용 가능
        // 아래 코드는 컴파일 에러 - IllegalStateException: stream has already been operated upon or closed
        /*List<String> youngStudents = studentStream.filter(student -> student.getAge() <= 23)
                .map(Student::getName)
                .collect(Collectors.toList());*/

        // 다시 새로운 스트림을 만들어야 한다.
        List<String> youngStudents = students.stream()
                .filter(student -> student.getAge() <= 23)
                .map(Student::getName)
                .collect(Collectors.toList());
        System.out.println(youngStudents);
    }
}
