package chap05;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamSlicing {

    public static void main(String[] args) {

        List<Student> students = Student.getSampleList();

        // 스트림 슬라이싱: 스트림의 요소를 선택하거나 스킵하는 방법

        // 1. 프레디케이트를 이용한 슬라이싱
        // 2학년 이하의 학생들을 필터링한다고 가정하자. filter를 사용하면 전체 스트림을 반복하면서 각 요소에 프레디케이트를 적용한다.
        // 학생 목록이 학년 순으로 정렬되어 있다면? 1학년보다 고학년의 학생이 나왔을 때 반복을 중단할 수 있다. -> 성능 개선 가능
        students.sort(Comparator.comparing(Student::getAcademicYear));
        List<Student> lowYearStudents = students.stream()
                .takeWhile(student -> student.getAcademicYear() <= 2)
                .collect(Collectors.toList());
        System.out.println(lowYearStudents);

        System.out.println();

        // 3학년 이상의 학생들로 요구사항이 바꼈다면? = 나머지 요소를 선택
        List<Student> highYearStudents = students.stream()
                .dropWhile(student -> student.getAcademicYear() <= 2)
                .collect(Collectors.toList());
        System.out.println(highYearStudents);

        System.out.println();

        students = Student.getSampleList();

        // 2. 스트림 축소
        // 학생 목록의 앞에서 3명만 출력해보자.
        students.stream()
                .limit(3) // 주어진 값 이하의 크기를 갖는 새로운 스트림 반환. 주어진 값이 스트림의 요소 개수보다 크면 전체 스트림 반환
                .forEach(System.out::println);

        System.out.println();

        // 반대로 학생 목록의 앞에서 3명을 건너뛰고 출력해보자.
        students.stream()
                .skip(3) // 처음 n개 요소를 제외한 새로운 스트림 반환. 주어진 값이 스트림의 요소 개수보다 크면 빈 스트림 반환
                .forEach(System.out::println);

        System.out.println();

        // 도전. 학생 목록에서 처음 등장하는 3명의 남학생 필터링
        List<Student> firstThreeManStudents = students.stream()
                .filter(student -> student.getGender().equals(Gender.MALE))
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(firstThreeManStudents);
    }
}
