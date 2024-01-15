// You can experiment here, it won’t be checked

import java.util.List;
import java.util.stream.IntStream;

public class Task {
  public static void main(String[] args) {
    // put your code here
    List<Integer> transactions = List.of(20, 40, -60, 5);
//    transactions.stream().reduce((sum, transaction) -> sum + transaction);
    transactions.stream().reduce(0, (sum, transaction) -> sum + transaction);
  }
}
