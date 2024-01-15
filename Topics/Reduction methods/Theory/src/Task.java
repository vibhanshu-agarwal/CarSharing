// You can experiment here, it won’t be checked

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.summingLong;

public class Task {
  public static void main(String[] args) {
    // put your code here
//    LinkedList<Account> accounts = accountStream.collect(Collectors.toCollection(LinkedList::new));
//    long summary = accounts.stream()
//            .collect(summingLong(Account::getBalance));
//    double average = accounts.stream()
//            .collect(averagingLong(Account::getBalance));
//    String megaNumber = accountStream.collect(Collectors.reducing("",
//            account -> account.getNumber(),
//            (numbers, number) -> numbers.concat(number)
//    ));
    int result = Stream.of(-1, 2, -3, 4)
            .collect(Collectors.reducing(0, x -> x > 0 ? x : -x, (x, y) -> x + y));
    System.out.println(result);
    Collectors.toC
  }
}
