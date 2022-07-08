package chap05;

import java.util.*;
import java.util.stream.Collectors;

public class StreamPractice {

    public static void main(String[] args) {

        List<Transaction> transactions = Transaction.getTransactionList();

        // 1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
        List<Transaction> transactionsIn2011 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(transactionsIn2011);

        // 2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
        List<String> citiesOfTraders = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(citiesOfTraders);

        // 3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
        List<Trader> tradersLiveInCambridge = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(tradersLiveInCambridge);

        // 4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.
        List<String> namesOfTraders = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(namesOfTraders);

        // 5. 밀라노에 거래자가 있는가?
        boolean isMilanTraderExist = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(isMilanTraderExist);

        // 6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        // 7. 전체 트랜잭션 중 최댓값은 얼마인가?
        Optional<Integer> maxOfValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        maxOfValue.ifPresent(System.out::println);

        // 8. 전체 트랜잭션 중 최솟값은 얼마인가?
        // 7과 다른 방법
        Optional<Transaction> minOfValue = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue));
        minOfValue.ifPresent(System.out::println);
    }
}

class Trader {

    private final String name;

    private final String city;

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        hash = hash * 31 + (city == null ? 0 : city.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Trader)) {
            return false;
        }
        Trader o = (Trader) other;
        boolean eq = Objects.equals(name, o.getName());
        eq = eq && Objects.equals(city, o.getCity());
        return eq;
    }

    @Override
    public String toString() {
        return String.format("Trader{name=%s, city=%s}", name, city);
    }
}

class Transaction {

    private final Trader trader;

    private final int year;

    private final int value;

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("Transaction{trader=%s, year=%d, value=%d}", trader.toString(), year, value);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (trader == null ? 0 : trader.hashCode());
        hash = hash * 31 + year;
        hash = hash * 31 + value;
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Transaction)) {
            return false;
        }
        Transaction o = (Transaction) other;
        boolean eq = Objects.equals(trader, o.getTrader());
        eq = eq && year == o.getYear();
        eq = eq && value == o.getValue();
        return eq;
    }

    public static List<Transaction> getTransactionList() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        return Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }
}