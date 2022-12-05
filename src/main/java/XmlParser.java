import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {
    private static String node = "";
    private static Node mainNode = null;

    public static Node readMainNode(NodeList nodeList, String nameNode) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if (nodeList.item(i).getNodeName().equals(nameNode)) {
                mainNode = nodeList.item(i);
            }
        }
        return mainNode;
    }

    public static String readMainNodeChild(NodeList loadChilds, String nameNode) {
        for (int i = 0; i < loadChilds.getLength(); i++) {
            if (loadChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if (loadChilds.item(i).getNodeName().equals(nameNode)) {
                node = loadChilds.item(i).getTextContent();
            }
        }
        return node;
    }
}
