package chap05;

import java.util.List;
import java.util.Optional;

public class StreamSearchingAndMatching {

    public static void main(String[] args) {

        List<Student> students = Student.getSampleList();

        // 스트림 검색과 매칭: 특정 값이 스트림에 존재하는지 여부를 검색
        // 결과를 반환하면 스트림을 닫는 최종 연산이다.

        // 1. 프레디케이트가 적어도 한 요소와 일치하는지 확인
        boolean isLeeExist = students.stream().anyMatch(student -> student.getName().equals("Lee"));
        System.out.println(isLeeExist); // true

        // 2. 프레디케이트가 모든 요소와 일치하는지 확인
        boolean isAllUnder30 = students.stream().allMatch(student -> student.getAge() < 30);
        System.out.println(isAllUnder30); // true

        // noneMatch: 주어진 프레디케이트와 일치하는 요소가 없는지 확인
        isAllUnder30 = students.stream().noneMatch(student -> student.getAge() >= 30);
        System.out.println(isAllUnder30); // true

        // 쇼트서킷(short-circuting) 평가: 전체를 평가하거나 모든 요솔르 처리할 필요가 없는 경우.
        // 대표적인 예가 && 연산자와 || 연산자. &&의 경우 연결된 표현식에서 하나라도 거짓이 나오면 이후 나머지의 결과와 관계없이 전체 결과가 거짓이 된다.
        // 스트림의 allMatch, noneMatch, findFirst, findAny와 같은 연산도 모든 스트림의 요소를 처리하지 않고 결과를 반환할 수 있다.
        // limit 연산자도 쇼트서킷 연산이다.

        // 3. findAny: 현재 스트림에서 임의의 요소를 반환한다.
        // 남자이면서 이름이 "Lee"인 임의의 학생 찾기
        Optional<Student> result = students.stream()
                .filter(student -> student.getGender().equals(Gender.MALE) && student.getName().equals("Lee"))
                .findAny();
        System.out.println(result);

        // Optional<T>?
        // 값의 존재나 부재 여부를 표현하는 컨테이너 클래스이다.
        // findAny는 아무런 요소를 반환하지 않을 수 있다.(null) null은 안전하지 않기 때문에 자바 8에서 Optional<T>를 추가했다.

        System.out.println(result.isPresent()); // Optional이 값을 포함하는지?
        System.out.println(result.isEmpty()); // Optional이 값을 포함하지 않는지?
        result.ifPresent(System.out::println); // Optional이 값을 포함하면 주어진 코드 블록 실행 -> Consumer 함수형 인터페이스
        // get은 값이 존재하면 값 반환, 없으면 NoSuchElementException
        // or~~은 값이 있으면 값 반환, 없으면 기본값 또는 값을 만들거나, 예외를 던지는 것을 할 수 있다.

        // 4. findFirst: 첫 번째 요소 찾기 -> 리스트나 정렬된 연속 데이터로부터 생성된 스트림과 같이 일부 스트림에는 논리적인 요소 순서가 존재
        Optional<Student> result2 = students.stream()
                .filter(student -> student.getAge() == 23 && student.getAcademicYear() == 4)
                .findFirst();
        result2.ifPresent(System.out::println);

        // findAny와 findFirst가 왜 구분?
        // 병렬성 때문. 병렬 실행 중에는 첫 번째 요소를 찾기가 어렵다. 요소의 반환 순서가 상관없다면 병렬 스트림에서는 findAny를 사용
    }
}
