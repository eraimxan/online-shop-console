import java.util.ArrayList;
import java.util.Scanner;

class Product {
    private String name;
    private double price;
    private int quantity;
    private String description;

    public Product(String name, double price, int quantity, String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class User {
    private int id;
    private String name;
    private double balance;
    private ArrayList<Order> orders;

    public User(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.orders = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

class Order {
    private int userId;
    private String productName;
    private int quantity;
    private double totalSum;

    public Order(int userId, String productName, int quantity, double totalSum) {
        this.userId = userId;
        this.productName = productName;
        this.quantity = quantity;
        this.totalSum = totalSum;
    }

    public int getUserId() {
        return userId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalSum() {
        return totalSum;
    }
}

class OnlineShop {
    private ArrayList<User> users;
    private ArrayList<Product> products;
    private ArrayList<Order> orderHistory;

    public OnlineShop() {
        this.users = new ArrayList<>();
        this.products = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
    }

    public void showProductList() {
        System.out.println("Product List:");
        for (Product product : products) {
            System.out.println(product.getName() + " - $" + product.getPrice() + " - Quantity: " + product.getQuantity());
        }
    }

    public void addProduct(String name, double price, int quantity, String description) {
        Product product = new Product(name, price, quantity, description);
        products.add(product);
        System.out.println("Product added successfully!");
    }

    public void addUser(int id, String name, double balance) {
        User user = new User(id, name, balance);
        users.add(user);
        System.out.println("User added successfully!");
    }

    public void buyProduct(int userId, String productName, int quantity) {
        User user = findUserById(userId);
        Product product = findProductByName(productName);

        if (user != null && product != null) {
            double totalCost = product.getPrice() * quantity;
            if (user.getBalance() >= totalCost && product.getQuantity() >= quantity) {
                user.setBalance(user.getBalance() - totalCost);
                product.setQuantity(product.getQuantity() - quantity);

                Order order = new Order(userId, productName, quantity, totalCost);
                user.getOrders().add(order);
                orderHistory.add(order);

                System.out.println("Purchase successful!");
            } else {
                System.out.println("Insufficient balance or product quantity!");
            }
        } else {
            System.out.println("User or product not found!");
        }
    }

    public void returnProduct(int userId, String productName, int quantity) {
        User user = findUserById(userId);
        Product product = findProductByName(productName);

        if (user != null && product != null) {
            Order orderToRemove = findOrderToRemove(user, productName, quantity);

            if (orderToRemove != null) {
                user.setBalance(user.getBalance() + orderToRemove.getTotalSum());
                product.setQuantity(product.getQuantity() + quantity);

                user.getOrders().remove(orderToRemove);
                orderHistory.remove(orderToRemove);

                System.out.println("Return successful!");
            } else {
                System.out.println("Order not found for return!");
            }
        } else {
            System.out.println("User or product not found!");
        }
    }

    public void showAllUsers() {
        System.out.println("User List:");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + " - Name: " + user.getName() + " - Balance: $" + user.getBalance());
        }
    }

    public void showUserOrders(int userId) {
        User user = findUserById(userId);
        if (user != null) {
            System.out.println("Orders for User " + user.getName() + ":");
            for (Order order : user.getOrders()) {
                System.out.println("Product: " + order.getProductName() + " - Quantity: " + order.getQuantity() + " - Total: $" + order.getTotalSum());
            }
        } else {
            System.out.println("User not found!");
        }
    }

    private User findUserById(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    private Product findProductByName(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    private Order findOrderToRemove(User user, String productName, int quantity) {
        for (Order order : user.getOrders()) {
            if (order.getProductName().equals(productName) && order.getQuantity() == quantity) {
                return order;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("\nHello! You have the following available functions:");
            System.out.println("1) To show products list;");
            System.out.println("2) To add a product;");
            System.out.println("3) To add a new user;");
            System.out.println("4) To buy product;");
            System.out.println("5) To return a product;");
            System.out.println("6) To show all users;");
            System.out.println("7) To show the certain user’s orders.");
            System.out.println("0) Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    onlineShop.showProductList();
                    break;
                case 2:
                    System.out.print("Enter product name: ");
                    scanner.nextLine(); // Consume the newline character
                    String productName = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double productPrice = scanner.nextDouble();
                    System.out.print("Enter product quantity: ");
                    int productQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter product description: ");
                    String productDescription = scanner.nextLine();
                    onlineShop.addProduct(productName, productPrice, productQuantity, productDescription);
                    break;
                case 3:
                    System.out.print("Enter user ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter user name: ");
                    String userName = scanner.nextLine();
                    System.out.print("Enter user balance: ");
                    double userBalance = scanner.nextDouble();
                    onlineShop.addUser(userId, userName, userBalance);
                    break;
                case 4:
                    System.out.print("Enter user ID: ");
                    int buyUserId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter product name to buy: ");
                    String buyProductName = scanner.nextLine();
                    System.out.print("Enter quantity to buy: ");
                    int buyQuantity = scanner.nextInt();
                    onlineShop.buyProduct(buyUserId, buyProductName, buyQuantity);
                    break;
                case 5:
                    System.out.print("Enter user ID: ");
                    int returnUserId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter product name to return: ");
                    String returnProductName = scanner.nextLine();
                    System.out.print("Enter quantity to return: ");
                    int returnQuantity = scanner.nextInt();
                    onlineShop.returnProduct(returnUserId, returnProductName, returnQuantity);
                    break;
                case 6:
                    onlineShop.showAllUsers();
                    break;
                case 7:
                    System.out.print("Enter user ID to show orders: ");
                    int showOrdersUserId = scanner.nextInt();
                    onlineShop.showUserOrders(showOrdersUserId);
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
}
