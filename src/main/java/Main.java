import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        File jsonFile = new File("Basket.json");
        File logFile = new File("log.csv");
        
        ClientLog clientLog = new ClientLog();

        Scanner scanner = new Scanner(System.in);

        String[] products = {"Хлеб", "Сахар", "Чай"};
        int[] price = {40, 80, 100};

        Basket basket = new Basket(products, price);

        System.out.println("Список доступных для покупки продуктов: " + "\n");
        for (int i = 0; i < products.length & i < price.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " - " + price[i] + " руб/шт");
        }

        if (jsonFile.exists()) {
            System.out.println("\n" + "Обнаружен сохранённый спок продуктов");
            System.out.println("Продолжить? Введите да или нет");
        }
        while (true) {
            String input = scanner.nextLine();

            int productNumber = 0;
            int productAmount = 0;

            if (input.equals("да")) {
                basket = Basket.loadFromJsonFile(jsonFile);
                continue;
            } else if (input.equals("нет")) {
                System.out.println("Введите номер продукта и количество через пробел");
                continue;
            }

            if (input.equals("end")) {
                break;
            }

            String[] parts = input.split(" ");
            productNumber = Integer.parseInt(parts[0]);
            productAmount = Integer.parseInt(parts[1]);
            clientLog.log(productNumber, productAmount);

            basket.addToCart(productNumber, productAmount);
        }
        basket.saveJson(jsonFile);
        basket.printCart();
        clientLog.exportAsCSV(logFile);
    }
}

