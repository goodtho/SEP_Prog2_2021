import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Streams {


    public static long factorial(long n) {
        return LongStream.rangeClosed(1L,n).reduce(1L, (a, b) -> a*b);
    }


    public long SumOfOddNumbers(int start, int end){
        return LongStream.rangeClosed(start, end).filter(n -> n%2 == 1).sum();
    }


    // product of squares
    static {
        List<Integer> numbers = new ArrayList<>();
        long val = numbers.stream().collect(Collectors.reducing(1, (a, b) -> a * b*b));
    }

   /*
    public void totalSumOfTransactions {
        List<Transaction> transactions = new ArrayList<>();
        Map<String, Long> totalSumOfTransByEachAccount = new HashMap<>();
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                transaction -> transaction.getAccount().getNumber(),
                                Collectors.summingLong(Transaction::getSum)));
    }

    */

    /* public class ComposingPredicate {


         * The method represents a disjunct operator for a list of predicates.
         * For an empty list it returns the always false predicate.

        public static IntPredicate disjunctAll(List<IntPredicate> predicates) {
            return predicates.stream().reduce(x -> false, (a, b) -> a.or(b));
        }


         * Using anyMatch to reduce compute time if possible

        public static IntPredicate disjunctAllFaster(List<IntPredicate> predicates) {
            return i -> predicates.stream().anyMatch(p -> p.test(i));
        }


         * Classical implementation provided by lecturer to help you solve this exercise.
         * <p>
         * This solution works, but you have to search a solution using streams which will lead you
         * to a solution with less lines of code.

        public static IntPredicate disjunctAllNoStream(List<IntPredicate> predicates) {
            IntPredicate disjunct = x -> false;
            for (IntPredicate currentPredicate : predicates) {
                disjunct = disjunct.or(currentPredicate);
            }
            return disjunct;
        }
    }
*/
}
