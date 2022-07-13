package chap06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectorExample {

    public static void main(String[] args) {

        List<Transaction> transactions = Transaction.transactions;

        // 통화별로 트랜잭션 그룹화하기 - 명령형 버전
        Map<Currency, List<Transaction>> transactionsByCurrencies = new HashMap<>();

        for (Transaction transaction : transactions) {
            Currency currency = transaction.getCurrency();
            List<Transaction> transactionsForCurrency = transactionsByCurrencies.get(currency);
            if (transactionsForCurrency == null) {
                transactionsForCurrency = new ArrayList<>();
                transactionsByCurrencies.put(currency, transactionsForCurrency);
            }
            transactionsForCurrency.add(transaction);
        }
        System.out.println(transactionsByCurrencies);

        // 통화별로 트랜잭션 그룹화하기 - 스트림 사용(함수형)
        transactionsByCurrencies = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCurrency));
        System.out.println(transactionsByCurrencies);

        // Collector 인터페이스 구현은 스트림의 요소를 어떤 식으로 도출할지 지정한다.
        // groupingBy는 그룹화를 수행할 때 사용할 수 있다.
        // 다수준으로 그룹화를 수행할 때 명령형 프로그래밍과 함수형 프로그래밍의 차이점이 크게 나타난다.
        // 명
    }
}
