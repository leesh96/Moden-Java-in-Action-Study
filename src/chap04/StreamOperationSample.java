package chap04;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamOperationSample {

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1033, 21, Gender.MALE, "Kim1", 2));
        students.add(new Student(1030, 25, Gender.MALE, "Lee1", 3));
        students.add(new Student(1045, 23, Gender.FEMALE, "Lee2", 4));
        students.add(new Student(1014, 24, Gender.FEMALE, "Lee3", 3));
        students.add(new Student(1011, 22, Gender.FEMALE, "Kim2", 3));
        students.add(new Student(1028, 20, Gender.MALE, "Park1", 1));
        students.add(new Student(1026, 27, Gender.MALE, "Park2", 4));
        students.add(new Student(1024, 21, Gender.MALE, "Kim3", 1));
        students.add(new Student(1036, 22, Gender.FEMALE, "Kim4", 2));

        List<String> list = students.stream()
                .filter(student -> {
                    System.out.println("filtering: " + student.getName());
                    return student.getAge() <= 23;
                })
                .map(student -> {
                    System.out.println("mapping: " + student.getName());
                    return student.getName();
                })
                .limit(3)
                .collect(Collectors.toList());
        // filter와 map는 서로 다른 중간 연산이지만 한 과정으로 병합된다. -> 루프 퓨전
        // 23세 이하의 학생은 여러명이지만, 처음 3명만 선택되었음. -> limit 연산과 쇼트 서킷

        System.out.println(list);
    }
}
