package chap03;

import java.util.ArrayList;
import java.util.List;

public class LambdaSample {

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1028, "Lee", 25));
        students.add(new Student(1037, "Kim", 21));
        students.add(new Student(1014, "Kim", 25));
        students.add(new Student(1022, "Lee", 23));
        students.add(new Student(1019, "Park", 25));

        // 이름 오름차순 정렬
        // Comparator<Student>에 람다 표현식 사용
        students.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));
        System.out.println(students);
    }
}
