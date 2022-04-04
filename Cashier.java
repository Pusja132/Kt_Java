import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Cashier {
    private final static Random random = new Random();

    private final  int number;
    private final AtomicInteger time;
    private Integer openTime = null;

    private boolean open = true;

    public Cashier(int number, AtomicInteger time) {
        this.number = number;
        this.time = time;
    }

    public boolean open() {
        return open;
    }

    public void serve(int customerNo) {
        System.out.printf("Кассир #%d обсуживает клиента #%d\n",
                number, customerNo);

        if (random()) {
            open = false;
            openTime = null;
            System.out.printf("Касса #%d закрыта по причине технических шоколадок\n", number);
            return;
        }

        if (random()) {
            open = false;
            openTime = time.get() + 4;
            System.out.printf("Касса #%d закрыта для срочного вызова\n",
                    number);
        }
    }

    private boolean random() {
        return random.nextInt(100) < 10;
    }

    public void tryOpen() {
        if (openTime == null) {
            if (random()) {
                open = true;
                System.out.printf("Касса #%d открыта\n", number);
            }
            return;
        }

        if (openTime == time.get()) {
            open = true;
            System.out.printf("Касса #%d открылась после срочного вызова\n",
                    number);
        }
    }
}
