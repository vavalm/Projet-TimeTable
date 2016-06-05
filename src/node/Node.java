package node;

import org.jdom2.Element;

/**
 * Classe possédant seulement un élément d'un document xml
 * Toutes les classes du modèle de la base de données héritent de cette classe,
 * ainsi, tous les objets possèdent une référence vers leur élément xml
 * @author Valentin Maupin et Quentin Solard
 * @version 06/2016
 */
public class Node {

    /**
     * Un élément xml
     */
    private Element node;

    /**
     * Le constructeur de Node
     */
    public Node() {
        this.setNode(null);
    }

    /**
     * Le constructeur de Node
     * @param node
     *      L'élément xml du Node
     */
    public Node(Element node) {
        this.setNode(node);
    }

    /**
     * Getter de Node
     * @return
     *      L'élément xml du Node
     */
    public Element getNode() {
        return node;
    }

    /**
     * Setter de Node
     * @param node
     *      L'élément xml du Node
     */
    public void setNode(Element node) {
        this.node = node;
    }
}
