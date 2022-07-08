package chap05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMapping {

    public static void main(String[] args) {

        List<Student> students = Student.getSampleList();

        // 맵핑: 특정 객체에서 특정 데이터 선택하기? 스트림의 객체 변환하기?

        // 1. 스트림의 각 요소에 함수 적용하기
        // 학생 목록에서 이름만 선택하기
        List<String> studentNames = students.stream()
                .map(Student::getName) // Stream<String> 형식이 된다.
                .collect(Collectors.toList()); // List<String>이 최종 형식
        System.out.println(studentNames);

        System.out.println();

        // 도전. 영어 단어 목록에서 각 단어가 포함하는 글자 수의 리스트를 반환
        List<String> words = Arrays.asList("Modern", "Java", "in", "Action");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println(wordLengths);

        System.out.println();

        // 2. 스트림 평면화
        // 위의 영어 단어 목록에서 각 단어가 포함하는 문자들의 고유 문자로 이루어진 리스트를 반환
        words.stream()
                .map(word -> word.split("")) // Stream<String[]> 형식
                .distinct() // 중복이 필터링되지 않는다. 스트림의 각 요소가 String 배열이기 때문
                .forEach(System.out::println);

        // Stream<String[]> 대신 Stream<Stream<String>>이 필요하다.
        words.stream()
                .map(word -> word.split(""))
                .map(Arrays::stream) // Arrays.stream() 메소드는 배열을 스트림으로 만들어준다.
                .distinct() // 여전히 중복이 필터링되지 않는다. Stream<Stream<String>> 형식이기 때문
                .forEach(System.out::println);

        // 각 단어의 개별 문자열 스트림을 하나로 합쳐야 한다.
        List<String> uniqueChars = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream) // 스트림의 각 값을 다른 스트림으로 만든 다음에 모든 스트림을 하나의 스트림으로 연결한다.
                .distinct()
                .collect(Collectors.toList());
        System.out.println(uniqueChars);

        System.out.println();

        // 도전 1. 숫자 리스트가 주어졌을 때 각 숫자의 제곱근으로 이루어진 리스트 만들기
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());
        System.out.println(squares);

        System.out.println();

        // 도전 2. 두 개의 숫자 리스트가 있을 때 모든 숫자 쌍의 리스트를 만들기
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> numbers2 = Arrays.asList(10, 20, 30);
        List<List<Integer>> numberPairs = numbers1.stream()
                .flatMap(n1 -> numbers2.stream().map(n2 -> Arrays.asList(n1, n2))) // [n1의 숫자, n2의 숫자]로 이루어진 스트림이 만들어진다.
                .collect(Collectors.toList());
        System.out.println(numberPairs);

        System.out.println();

        // 도전 3. 2의 숫자 쌍에서 두 수의 합이 3의 배수인 쌍의 리스트 만들기
        List<List<Integer>> pairs = numbers1.stream()
                .flatMap(n1 -> numbers2.stream().map(n2 -> {
                    System.out.println("map: " + n1 + ", " + n2);
                    return Arrays.asList(n1, n2);
                })) // 모든 가능한 숫자 쌍을 만들고
                .filter(p -> {
                    System.out.println("filter: " + p);
                    return (p.get(0) + p.get(1)) % 3 == 0;
                }) // 필터링을 위해 평면화한 스트림의 값을 모두 반복
                .collect(Collectors.toList());
        System.out.println(pairs);

        System.out.println();

        pairs = numbers1.stream()
                .flatMap(n1 -> numbers2.stream().filter(n2 -> {
                    System.out.println("filter: " + n1 + ", " + n2);
                    return (n1 + n2) % 3 == 0;
                }).map(n2 -> {
                    System.out.println("map: " + n1 + ", " + n2);
                    return Arrays.asList(n1, n2);
                }))
                .collect(Collectors.toList()); // 모든 가능한 숫자 쌍을 만들 때 먼저 3의 배수로 필터링 -> 두 과정 또한 하나로 병합된다.
        System.out.println(pairs);

        // 아래 코드가 더 효율적이고 가독성 좋은 코드
        // 출력된 로그를 확인해도 처리 과정이 적은 것을 확인할 수 있다.
    }
}
