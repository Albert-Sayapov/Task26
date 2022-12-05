import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Scanner;

public class Main {

    private static boolean loadEnable = false;
    private static String loadFileName = "";
    private static FileFormat loadFileFormat = FileFormat.TXT;

    private static boolean saveEnable = false;
    private static String saveFileName = "";
    private static FileFormat saveFileFormat = FileFormat.TXT;

    private static boolean logEnable = false;
    private static String logFileName = "";
    private static String xmlFile = "shop.xml";

    public static void main(String[] args) throws Exception {

        loadSettings();

        File loadFile = new File(loadFileName);
        File saveFile = new File(saveFileName);
        File logFile = new File(logFileName);

        ClientLog clientLog = new ClientLog();

        Scanner scanner = new Scanner(System.in);

        String[] products = {"Хлеб", "Сахар", "Чай"};
        int[] price = {40, 80, 100};

        Basket basket = new Basket(products, price);

        System.out.println("Список доступных для покупки продуктов: " + "\n");
        for (int i = 0; i < products.length & i < price.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " - " + price[i] + " руб/шт");
        }

        if (loadEnable && loadFile.exists()) {
            System.out.println("\n" + "Обнаружен сохранённый спок продуктов");
            System.out.println("Продолжить? Введите да или нет");
        }
        while (true) {
            String input = scanner.nextLine();

            int productNumber = 0;
            int productAmount = 0;

            if (input.equals("да")) {
                if (loadFileFormat.equals(FileFormat.JSON)) {
                    basket = Basket.loadFromJsonFile(loadFile);
                } else {
                    basket = Basket.loadFromTextFile(loadFile);
                }

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

        basket.printCart();
        if (saveEnable) {
            if (saveFileFormat.equals(FileFormat.JSON)) {
                basket.saveJson(saveFile);
            } else {
                basket.saveTxt(saveFile);
            }
        }
        if (logEnable) {
            clientLog.exportAsCSV(logFile);
        }
    }

    private static Document buildDoc() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        return dbf.newDocumentBuilder().parse(new File(xmlFile));
    }

    public static void loadSettings() throws Exception {

        Document doc = buildDoc();

        Node rootNode = doc.getFirstChild();
        NodeList nodeList = rootNode.getChildNodes();

        Node loadNode = XmlParser.readMainNode(nodeList, "load");
        Node saveNode = XmlParser.readMainNode(nodeList, "save");
        Node logNode = XmlParser.readMainNode(nodeList, "log");
        ;

        NodeList loadChilds = loadNode.getChildNodes();
        loadEnable = Boolean.parseBoolean(XmlParser.readMainNodeChild(loadChilds, "enabled"));
        loadFileName = XmlParser.readMainNodeChild(loadChilds, "fileName");
        loadFileFormat = FileFormat.valueOf(XmlParser.readMainNodeChild(loadChilds, "format")
                .toUpperCase());

        NodeList saveChilds = saveNode.getChildNodes();
        saveEnable = Boolean.parseBoolean(XmlParser.readMainNodeChild(saveChilds, "enabled"));
        saveFileName = XmlParser.readMainNodeChild(saveChilds, "fileName");
        saveFileFormat = FileFormat.valueOf(XmlParser.readMainNodeChild(saveChilds, "format")
                .toUpperCase());

        NodeList logChilds = logNode.getChildNodes();
        logEnable = Boolean.parseBoolean(XmlParser.readMainNodeChild(logChilds, "enabled"));
        logFileName = XmlParser.readMainNodeChild(logChilds, "fileName");
    }
}



