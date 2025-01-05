package homework;

import homework.domain.Order;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class RestaurantOrders {
    // Этот блок кода менять нельзя! НАЧАЛО!
    private List<Order> orders;

    private RestaurantOrders(String fileName) {
        var filePath = Path.of("data", fileName);
        Gson gson = new Gson();
        try {
            orders = List.of(gson.fromJson(Files.readString(filePath), Order[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RestaurantOrders read(String fileName) {
        var ro = new RestaurantOrders(fileName);
        ro.getOrders().forEach(Order::calculateTotal);
        return ro;
    }

    public List<Order> getOrders() {
        return orders;
    }
    // Этот блок кода менять нельзя! КОНЕЦ!

    //----------------------------------------------------------------------
    //------   Реализация ваших методов должна быть ниже этой линии   ------
    //----------------------------------------------------------------------

    public void printOrders() {
        orders.forEach(order -> {
            System.out.println("Customer: " + order.getCustomer().getFullName());
            System.out.println("Email: " + order.getCustomer().getEmail());
            System.out.println("Total: " + order.getTotal());
            System.out.println("Items:");
            order.getItems().forEach(item ->
                    System.out.println("- " + item.getName() + ", Price: " + item.getPrice() + ", Amount: " + item.getAmount())
            );
            System.out.println("--------------------------------------------");
        });
    }

    public List<Order> getTopOrdersByTotal(int n) {
        return orders.stream()
                .sorted((o1, o2) -> Double.compare(o2.getTotal(), o1.getTotal()))
                .limit(n)
                .toList();
    }

    public List<Order> getBottomOrdersByTotal(int n) {
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::getTotal))
                .limit(n)
                .toList();
    }

    public List<Order> getHomeDeliveryOrders() {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .toList();
    }

    public Order getMostProfitableHomeDeliveryOrder() {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .max(Comparator.comparingDouble(Order::getTotal))
                .orElse(null);
    }

    public List<Order> getOrdersByTotalRange(double minOrderTotal, double maxOrderTotal) {
        return orders.stream()
                .filter(order -> order.getTotal() >= minOrderTotal && order.getTotal() <= maxOrderTotal)
                .toList();
    }


    // Наполните этот класс решением домашнего задания.
    // Вам необходимо создать все необходимые методы
    // для решения заданий из домашки :)
    // вы можете добавлять все необходимые imports
    //
}
