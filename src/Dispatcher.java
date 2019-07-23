import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Класс Dispatcher принимает заявку от клиента(Passenger), ищет свободного водителя и создает заказ
 */

public class Dispatcher implements Observer{

    static List<Taxi> listOfTaxi = new ArrayList<>();
    List<Order> listOfOrders = new ArrayList<>();

    void createOrder(Passenger passenger) throws ExecutionException, InterruptedException {
        while (passenger.getStatus() != Order.Status.DONE) {
            for (Taxi taxi : listOfTaxi) {
                if (passenger.getTaxiClass() == taxi.getTaxiClass() && taxi.isFree() && passenger.getStatus() == Order.Status.NEW) {
                    Order order = new Order(passenger, taxi);
                    order.registerObserver(this);
                    order.executeOrder();
                    listOfOrders.add(order);
                }
            }
        }


    }

    @Override
    synchronized public void update(Order.Status status) {
    }
}
