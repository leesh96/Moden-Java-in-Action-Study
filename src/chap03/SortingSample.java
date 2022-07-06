package chap03;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingSample {

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1028, "Lee", 25));
        students.add(new Student(1037, "Kim", 21));
        students.add(new Student(1014, "Kim", 25));
        students.add(new Student(1022, "Lee", 23));
        students.add(new Student(1019, "Park", 25));

        // 학생 리스트를 나이 순으로 정렬하기
        // 1단계. 코드 전달하기
        students.sort(new StudentComparator());
        System.out.println(students);

        // 2단계. 익명 클래스 사용하기
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getAge() - o2.getAge();
            }
        });
        System.out.println(students);

        // 3단계. 람다 표현식 사용하기
        students.sort((Student s1, Student s2) -> s1.getAge() - s2.getAge());
        System.out.println(students);

        // 3-1. 람다의 파라미터 형식 추론 - 람다 표현식이 사용된 컨텍스트 활용
        students.sort((s1, s2) -> s1.getAge() - s2.getAge());
        System.out.println(students);

        // 3-2. 가독성 향상시키기.
        // Comparator는 Comparable 키를 추출(KeyExtractor)해서 Comparator 객체로 만드는 Function 함수를 인수로 받는 정적 메소드 comparing을 포함
        students.sort(Comparator.comparing(student -> student.getAge()));
        System.out.println(students);

        // 4. 메소드 참조 사용하기
        students.sort(Comparator.comparing(Student::getAge));
        System.out.println(students);

        // 모두 같은 출력 결과

        // 업그레이드 - 여러 조건으로 정렬하기.
        // Comparator를 연결하면 된다.
        // 학생 리스트를 나이의 내림차순으로 정렬하고, 나이가 같으면 이름의 오름차순으로 정렬
        students.sort(Comparator.comparing(Student::getAge).reversed().thenComparing(Student::getName));
        System.out.println(students);
    }

    // 1단계.
    static class StudentComparator implements Comparator<Student> {

        @Override
        public int compare(Student o1, Student o2) {
            return o1.getAge() - o2.getAge();
        }
    }
}
