package chap05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamFiltering {

    public static void main(String[] args) {

        List<Student> students = Student.getSampleList();

        // 필터링: 스트림의 요소를 선택하기

        // 1. 프레디케이트로 필터링
        // 학생 목록에서 1학년 학생들을 필터링해보자.
        List<Student> firstYearStudents = students.stream()
                .filter(student -> student.getAcademicYear() == 1)
                .collect(Collectors.toList());
        System.out.println(firstYearStudents);

        System.out.println();

        // 2. 고유 요소 필터링 - distinct
        // 중복 요소를 필터링한다. 고유 여부는 스트림에서 만든 객체의 hashCode와 equals로 결정
        List<Integer> numbers = Arrays.asList(1, 2, 1, 2, 3, 3, 4, 3, 1);
        numbers.stream()
                .filter(n -> n % 2 == 1)
                .distinct()
                .forEach(System.out::println);
    }
}
