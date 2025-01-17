import homework.RestaurantOrders;
import homework.domain.Order;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        RestaurantOrders orders = RestaurantOrders.read("orders_100.json");

        System.out.println("Все заказы:");
        orders.printOrders();

        System.out.println("\nТоп 3 заказа по общей сумме:");
        printTopOrders(orders.getTopOrdersByTotal(3));

        System.out.println("\nНижние 3 заказа по общей сумме:");
        printBottomOrders(orders.getBottomOrdersByTotal(3));

        System.out.println("\nЗаказы с доставкой на дом:");
        printHomeDeliveryOrders(orders.getHomeDeliveryOrders());

        System.out.println("\nСамый прибыльный заказ с доставкой:");
        printOrder(orders.getMostProfitableHomeDeliveryOrder());

        System.out.println("\nСамый убыточный заказ с доставкой:");
        printOrder(orders.getLeastProfitableHomeDeliveryOrder());

        System.out.println("\nЗаказы в пределах суммы от 50 до 200:");
        List<Order> filteredOrders = orders.getOrdersByTotalRange(50.0, 100.0);
        printOrdersInColumns(filteredOrders);

        System.out.println("\nСумма всех заказов: " + orders.calculateTotalSum());

        System.out.println("\nУникальные и отсортированные email адреса клиентов:");
        List<String> sortedUniqueEmails = orders.getSortedUniqueEmails();
        printSortedUniqueEmailsInColumns(sortedUniqueEmails);


        System.out.println("\nЗаказы, сгруппированные по клиентам:");
        printGroupedByCustomer(orders.getOrdersGroupedByCustomerName());

        System.out.println("\nСумма заказов по каждому клиенту:");
        Map<String, Double> customerSums = orders.getUniqueCustomersWithTotalOrderSum();
        printUniqueCustomersWithTotalOrderSumInColumns(customerSums);

        System.out.println("\nКлиент с максимальной суммой заказов: " + orders.getCustomerWithMaxOrderSum());
        System.out.println("Клиент с минимальной суммой заказов: " + orders.getCustomerWithMinOrderSum());

        System.out.println("\nТовары, сгруппированные по общему количеству:");
        Map<String, Integer> itemCounts = orders.getItemsGroupedByTotalAmount();
        printItemsGroupedByTotalAmountInThreeColumns(itemCounts);
    }

    private static void printTopOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("Нет топ заказов.");
        } else {
            orders.forEach(order -> {
                System.out.println(order.getCustomer().getFullName() + " - Total: " + order.getTotal());
            });
        }
    }

    private static void printOrdersInColumns(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("Нет заказов в указанном диапазоне.");
            return;
        }

        int columnCount = 3;
        int rowCount = (orders.size() + columnCount - 1) / columnCount;

        for (int i = 0; i < rowCount; i++) {
            StringBuilder row = new StringBuilder();

            for (int j = 0; j < columnCount; j++) {
                int index = i + j * rowCount;
                if (index < orders.size()) {
                    Order order = orders.get(index);
                    row.append(String.format("%-30s",
                            order.getCustomer().getFullName() + " (" + String.format("%.2f", order.getTotal()) + ")"));
                }
            }

            System.out.println(row.toString());
        }
    }

    private static void printSortedUniqueEmailsInColumns(List<String> emails) {
        if (emails.isEmpty()) {
            System.out.println("Нет email-адресов для отображения.");
            return;
        }

        int columnCount = 3;
        int rowCount = (emails.size() + columnCount - 1) / columnCount;

        for (int i = 0; i < rowCount; i++) {
            StringBuilder row = new StringBuilder();

            for (int j = 0; j < columnCount; j++) {
                int index = i + j * rowCount;
                if (index < emails.size()) {
                    String email = emails.get(index);
                    row.append(String.format("%-40s", email));
                }
            }

            System.out.println(row.toString());
        }
    }


    private static void printBottomOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("Нет заказов с минимальными суммами.");
        } else {
            orders.forEach(order -> {
                System.out.println(order.getCustomer().getFullName() + " - Total: " + order.getTotal());
            });
        }
    }

    private static void printUniqueCustomersWithTotalOrderSumInColumns(Map<String, Double> customerSums) {
        if (customerSums.isEmpty()) {
            System.out.println("Нет данных для отображения.");
            return;
        }

        List<String> formattedEntries = customerSums.entrySet().stream()
                .map(entry -> entry.getKey() + " (" + String.format("%.2f", entry.getValue()) + ")")
                .toList();

        int columnCount = 2;
        int rowCount = (formattedEntries.size() + columnCount - 1) / columnCount;

        for (int i = 0; i < rowCount; i++) {
            StringBuilder row = new StringBuilder();

            for (int j = 0; j < columnCount; j++) {
                int index = i + j * rowCount;
                if (index < formattedEntries.size()) {
                    String entry = formattedEntries.get(index);
                    row.append(String.format("%-40s", entry));
                }
            }

            System.out.println(row.toString());
        }
    }


    private static void printHomeDeliveryOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            System.out.println("Нет заказов с доставкой.");
        } else {
            orders.forEach(order -> {
                System.out.println(order.getCustomer().getFullName() + " - Total: " + order.getTotal());
            });
        }
    }

    private static void printOrder(Order order) {
        if (order != null) {
            System.out.println(order.getCustomer().getFullName() + " - Total: " + order.getTotal());
        } else {
            System.out.println("Нет заказов.");
        }
    }

    private static void printGroupedByCustomer(Map<String, List<Order>> groupedOrders) {
        groupedOrders.forEach((customer, orders) -> {
            System.out.println(customer + ": ");
            orders.forEach(order -> System.out.println("  - Order Total: " + order.getTotal()));
        });
    }

    private static void printItemsGroupedByTotalAmountInThreeColumns(Map<String, Integer> itemCounts) {
        if (itemCounts.isEmpty()) {
            System.out.println("Нет данных для отображения.");
            return;
        }

        List<String> formattedEntries = itemCounts.entrySet().stream()
                .map(entry -> entry.getKey() + " - " + entry.getValue())
                .toList();

        int columnCount = 3;
        int rowCount = (formattedEntries.size() + columnCount - 1) / columnCount;

        for (int i = 0; i < rowCount; i++) {
            StringBuilder row = new StringBuilder();

            for (int j = 0; j < columnCount; j++) {
                int index = i + j * rowCount;
                if (index < formattedEntries.size()) {
                    String entry = formattedEntries.get(index);
                    row.append(String.format("%-30s", entry));
                }
            }

            System.out.println(row.toString());
        }
    }
}

