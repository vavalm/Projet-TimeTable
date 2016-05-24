package node;

import org.jdom2.Element;

/**
 * Created by Quentin on 24/05/2016.
 */
public class Node {
    private Element node;

    public Node() {
        this.setNode(null);
    }

    public Node(Element node) {
        this.setNode(node);
    }

    public Element getNode() {
        return node;
    }

    public void setNode(Element node) {
        this.node = node;
    }
}
