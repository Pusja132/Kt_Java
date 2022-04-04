import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Store {
    private final AtomicInteger time = new AtomicInteger();
    private final Cashier[] cashiers;
    private final int customersCount;

    public Store(int cashiersCount, int customersCount) {
        cashiers = IntStream.rangeClosed(1, cashiersCount)
                .mapToObj(no -> new Cashier(no, time))
                .toArray(Cashier[]::new);
        System.out.printf("Открыто %d касс\n", cashiers.length);

        this.customersCount = customersCount;
        System.out.printf("Появилось %d покупателей\n", customersCount);

        startSelling();
    }

    private void startSelling() {
        System.out.println("Начинаем продавать");

        var customerNo = 1;

        mainLoop: while (true) {
            time.incrementAndGet();
            System.out.printf("Time %d\n", time.get());

            for (var cashier : cashiers) {
                if (!cashier.open()) {
                    cashier.tryOpen();
                    if (!cashier.open())
                        continue;
                }

                cashier.serve(customerNo);
                customerNo++;

                if (customerNo == customersCount + 1)
                    break mainLoop;
            }
        }

        System.out.println("Все товары распроданы");
    }
}
