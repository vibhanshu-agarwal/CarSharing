// You can experiment here, it won’t be checked

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Task {
  public static void main(String[] args) {
    // put your code here
//    List<Integer> famousNumbers = List.of(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
//    Stream<Integer> numbersStream = famousNumbers.stream();
//
//    Set<String> usefulConcepts = Set.of("functions", "lazy", "immutability");
//    Stream<String> conceptsStream = usefulConcepts.stream();
//
//    Stream<Double> doubleStream = Arrays.stream(new Double[]{ 1.01, 1d, 0.99, 1.02, 1d, 0.99 });
//
//    Stream<Integer> empty1 = Stream.of();
//    Stream<Integer> empty2 = Stream.empty();

    List<String> companies = List.of(
            "Google", "Amazon", "Samsung",
            "GOOGLE", "amazon", "Oracle"
    );

    companies.stream()
            .map(String::toUpperCase) // transform each name to the upper case
            .distinct() // intermediate operation: keep only unique words
            .forEach(System.out::println); // print every company
  }
}
