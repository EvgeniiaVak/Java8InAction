package lambdasinaction.chap5;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static lambdasinaction.chap5.PuttingIntoPractice.traders;
import static lambdasinaction.chap5.PuttingIntoPractice.transactions;

public class MyPuttingIntoPractice {
    public static void main(String[] args) {
        System.out.println("\nFind all transactions in the year 2011 and sort them by value (small to high).");
        transactions.stream()
                .filter(t-> t.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .forEachOrdered(System.out::println);

        System.out.println("\nWhat are all the unique cities where the traders work?");
        Set<String> cities = traders.stream()
                .map(Trader::getCity)
                .collect(Collectors.toSet());
        System.out.println(cities);

        System.out.println("\nFind all traders from Cambridge and sort them by name.");
        List<Trader> cambridgeTraders = traders.stream()
                .filter(t -> t.getCity().equalsIgnoreCase("Cambridge"))
                .sorted(comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(cambridgeTraders);

        System.out.println("\nReturn a string of all traders’ names sorted alphabetically.");
        String names = traders.stream()
                .map(Trader::getName)
                .sorted()
                .collect(joining(", "));
        System.out.println(names);

        System.out.println("\nAre any traders based in Milan?");
        System.out.println(traders.stream().anyMatch(t-> t.getCity().equalsIgnoreCase("Milan")));

        System.out.println("\nPrint all transactions’ values from the traders living in Cambridge.");
        String cambridgeTransactionValues = transactions.stream()
                .filter(t -> t.getTrader().getCity().equalsIgnoreCase("Cambridge"))
                .map(Transaction::getValue)
                .map(String::valueOf)
                .collect(joining(", "));
        System.out.println(cambridgeTransactionValues);

        System.out.println("\nWhat’s the highest value of all the transactions?");
        System.out.println(
                transactions.stream()
                        .map(Transaction::getValue)
                        .max(Integer::compareTo)
                        .orElse(null));

        System.out.println("\nFind the transaction with the smallest value.");
        Transaction transaction = transactions.stream()
                .min(comparing(Transaction::getValue))
                .orElse(null);
        System.out.println(transaction);

    }
}
