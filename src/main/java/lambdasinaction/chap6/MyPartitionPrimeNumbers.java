package lambdasinaction.chap6;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

public class MyPartitionPrimeNumbers implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

    public static void main(String[] args) {
        System.out.println(
                IntStream.range(2, 100).boxed()
                .collect(new MyPartitionPrimeNumbers())
        );
    }

    private static List<Integer> primeNumbers = new CopyOnWriteArrayList<>(Arrays.asList(2, 3));

    public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
        int i = 0;
        for (A item : list) {
            if (!p.test(item)) {
                return list.subList(0, i);
            }
            i++;
        }
        return list;
    }

    public static boolean isPrime(int candidate){
        int candidateRoot = (int) Math.sqrt((double) candidate);
        boolean isPrime = takeWhile(primeNumbers, i -> i <= candidateRoot)
                .stream()
                .noneMatch(p -> candidate % p == 0);
        if (isPrime && candidate != 1) {
            primeNumbers.add(candidate);
        }
        return isPrime;
    }

    /**
     * A function that creates and returns a new mutable result container.
     *
     * @return a function which returns a new, mutable result container
     */
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return HashMap::new;
    }

    /**
     * A function that folds a value into a mutable result container.
     *
     * @return a function which folds a value into a mutable result container
     */
    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {

        return (m,i) -> {
            if (isPrime(i)) {
                m.computeIfAbsent(Boolean.TRUE, k -> new ArrayList<>());
                m.get(Boolean.TRUE).add(i);
            } else {
                m.computeIfAbsent(Boolean.FALSE, k -> new ArrayList<>());
                m.get(Boolean.FALSE).add(i);
            }
        };

    }

    /**
     * A function that accepts two partial results and merges them.  The
     * combiner function may fold state from one argument into the other and
     * return that, or may return a new result container.
     *
     * @return a function which combines two partial results into a combined
     * result
     */
    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
//        throw new UnsupportedOperationException();
//        even though the book says you can throw this exception here (and it's even better to do this)
//        this causes the exception actually being thrown even with the sequential stream

        return null;
    }

    /**
     * Perform the final transformation from the intermediate accumulation type
     * {@code A} to the final result type {@code R}.
     *
     * <p>If the characteristic {@code IDENTITY_FINISH} is
     * set, this function may be presumed to be an identity transform with an
     * unchecked cast from {@code A} to {@code R}.
     *
     * @return a function which transforms the intermediate result to the final
     * result
     */
    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    /**
     * Returns a {@code Set} of {@code Collector.Characteristics} indicating
     * the characteristics of this Collector.  This set should be immutable.
     *
     * @return an immutable set of collector characteristics
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
    }
}
