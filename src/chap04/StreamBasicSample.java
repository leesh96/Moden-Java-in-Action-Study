package chap04;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamBasicSample {

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1033, 21, Gender.MALE, "Kim", 2));
        students.add(new Student(1045, 23, Gender.FEMALE, "Lee", 4));
        students.add(new Student(1011, 22, Gender.FEMALE, "Kim", 3));
        students.add(new Student(1028, 20, Gender.MALE, "Park", 1));
        students.add(new Student(1030, 25, Gender.MALE, "Lee", 3));
        students.add(new Student(1014, 24, Gender.FEMALE, "Lee", 3));
        students.add(new Student(1026, 27, Gender.MALE, "Park", 4));
        students.add(new Student(1024, 21, Gender.MALE, "Kim", 1));
        students.add(new Student(1036, 22, Gender.FEMALE, "Kim", 2));

        // 요구사항: 학생 목록에서 나이 23세 이하의 학생을 이름 순으로 정렬해서 이름을 반환
        // 컬렉션 사용
        List<Student> youngStudents = youngStudents(students);
        youngStudents.sort(Comparator.comparing(Student::getName));
        List<String> youngStudentNames = mapStudentsToName(youngStudents);
        System.out.println(youngStudentNames);

        // 스트림 맛보기
        youngStudentNames = students.stream()
                .filter(student -> student.getAge() <= 23) // 람다를 인수로 받아 스트림에서 특정 요소를 제외(선택)한다.
                .sorted(Comparator.comparing(Student::getName)) // 람다를 인수로 받아 해당 조건으로 스트림의 요소를 정렬한다.
                .map(Student::getName) // 람다를 이용해서 한 요소를 다른 요소로 변환하거나 정보를 추출한다.
                .collect(Collectors.toList()); // 스트림을 다른 형식으로 변환한다.(코드는 리스트로 변환) 최종 연산자이다.
        System.out.println(youngStudentNames);

        // filter, sorted, map은 중간 연산이다.
        // collect는 최종 연산이다.

        // 스트림을 사용하지 않으면?
        // 데이터를 처리하는 메소드 구현, 각 단계의 중간 결과값을 저장, 요구사항의 변화에 유연하게 대응할 수 없음

        // 스트림을 사용하면?
        // 선언형으로 코드를 구현할 수 있다. -> if, 루프의 제어 구조가 필요하지 않음
        // 여러 연산자를 연결해서 복잡한 데이터 처리 파이프라인을 만들 수 있다. -> 이렇게 해도 뛰어난 가독성과 명확성
    }

    // 컬렉션 사용
    static List<Student> youngStudents(List<Student> students) {
        List<Student> ret = new ArrayList<>();

        for (Student student : students) {
            if (student.getAge() <= 23) {
                ret.add(student);
            }
        }

        return ret;
    }

    static List<String> mapStudentsToName(List<Student> students) {
        List<String> ret = new ArrayList<>();

        for (Student student : students) {
            ret.add(student.getName());
        }

        return ret;
    }
}
