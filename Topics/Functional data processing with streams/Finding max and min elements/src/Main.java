import java.util.*;
import java.util.function.*;
import java.util.stream.*;


class MinMax {

    public static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order,
            BiConsumer<? super T, ? super T> minMaxConsumer) {

        // your implementation here
        List<T> list = stream.collect(Collectors.toList());
        if (list.size() == 0) {
            minMaxConsumer.accept(null, null);
        } else {
            T min = list.get(0);
            T max = list.get(0);
            for (T t : list) {
                if (order.compare(t, min) < 0) {
                    min = t;
                }
                if (order.compare(t, max) > 0) {
                    max = t;
                }
            }
            minMaxConsumer.accept(min, max);
        }
    }
}