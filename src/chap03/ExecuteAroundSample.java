package chap03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecuteAroundSample {

    public static void main(String[] args) throws IOException {

        // 순환 패턴
        // 자원 처리에 사용하는 순환 패턴은 자원을 열고, 처리를 하고, 자원을 닫는 순서로 이루어진다.
        // 자원을 열고 닫는 순서는 대부분 비슷하고 처리하는 코드만 달라진다.

        String result = processConsoleInput();
        System.out.println(result);

        // 4단계: 람다 전달
        // 콘솔로부터 두 줄 입력받아 한 줄로 만들기
        result = processConsoleInput((ConsoleInputProcessor) br -> br.readLine() + br.readLine());
        System.out.println(result);
    }

    // 콘솔로부터 한 줄을 입력받는 메소드
    // 초기화 또는 준비 코드 - 작업 - 정리 또는 마무리 코드
    // 작업 또는 처리 코드를 준비와 정리 코드가 감싸고 있는 형태 -> 실행 어라운드 패턴
    public static String processConsoleInput() throws IOException {
        // try-with-resources 구문 자원을 명시적으로 닫을 필요가 없어 간결한 코드 구성 가능
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            return br.readLine();
        }
    }

    // 1단계: 동작 파라미터화
    // 콘솔 입력을 처리하는 동작을 파라미터화한다.
    // processConsoleInput 메소드가 다른 동작을 수행할 수 있도록 동작을 전달해야 한다. -> 람다 사용가능

    // 2단계: 함수형 인터페이스를 이용해서 동작 전달
    // ConsoleInputProcessor 인터페이스 참고

    // 3단계: 동작 실행
    // 이제 ConsoleInputProcessor에 정의된 process의 시그니처와 일치하는 람다를 전달할 수 있다.
    public static String processConsoleInput(ConsoleInputProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            return p.process(br);
        }
    }
}
