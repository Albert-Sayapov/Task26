import java.io.*;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String[] products;
    protected int[] price;
    protected int[] amount;
    protected int totalSum;
    protected int totalAmount;
    protected int currentPrice;

    public Basket(String[] products, int[] price) {
        this.products = products;
        this.price = price;
        this.amount = new int[products.length];
    }

    public Basket(String[] products, int[] price, int[] amount) {
        this.products = products;
        this.price = price;
        this.amount = amount;
    }

    public void addToCart(int productNumber, int productCount) {
        this.amount[productNumber - 1] += productCount;
        this.currentPrice = price[productNumber - 1];
        this.totalAmount = productCount * currentPrice;
        this.totalSum += totalAmount;
    }

    public void printCart() {
        System.out.println("Ваша корзина: ");
        for (int i = 0; i < price.length; i++)
            if (amount[i] != 0) {
                System.out.println(products[i] + " " + amount[i] + " шт " + price[i] + " руб/шт " + (amount[i] * price[i]) + " р в сумме");
            }
        System.out.println("Итого: " + totalSum);
    }

    public void saveTxt(File textFile) throws IOException {
        try (Writer writer = new FileWriter(textFile)) {
            for (String product : products) {
                writer.write(product + " ");
            }
            writer.write("\n");
            for (int value : price) {
                writer.write(value + " ");
            }
            writer.write("\n");
            for (int value : amount) {
                writer.write(value + " ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromTextFile(File textFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String[] products = reader.readLine().split(" ");
            String[] priceString = reader.readLine().split(" ");
            String[] amountString = reader.readLine().split(" ");
            int[] price = new int[priceString.length];
            int[] amount = new int[amountString.length];
            for (int i = 0; i < price.length; i++) {
                price[i] = Integer.parseInt(priceString[i]);
            }

            for (int i = 0; i < amount.length; i++) {
                amount[i] = Integer.parseInt(amountString[i]);
            }
            return new Basket(products, price, amount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBin(File binFile) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(binFile))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromBinFile(File binFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(binFile))) {
            return (Basket) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
