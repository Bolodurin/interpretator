public class Node {
    private TypeOfNode typeOfNode;
    private String value;

    public Node(TypeOfNode typeOfNode, String value) {
        this.typeOfNode = typeOfNode;
        this.value = value;
    }

    public Node(Node node){
        this.typeOfNode = node.typeOfNode;
        this.value = node.value;
    }

    public TypeOfNode getTypeOfNode() {
        return typeOfNode;
    }

    public String getValue() {
        return value;
    }

    public void setTypeOfNode(TypeOfNode typeOfNode) {
        this.typeOfNode = typeOfNode;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
