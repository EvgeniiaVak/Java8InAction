package lambdasinaction.chap5;

import java.util.Arrays;
import java.util.stream.Stream;

public class FibonacciIteration {
    public static void main(String[] args) {

        Stream.iterate(Arrays.asList(0, 1), a -> Arrays.asList(a.get(1), a.get(0) + a.get(1)))
                .limit(20)
                .mapToInt(a->a.get(0))
                .forEach(System.out::println);

    }
}
