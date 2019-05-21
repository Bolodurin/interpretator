public class Tree {
    private Tree parent;
    public Tree left;
    public Tree right;
    private Node content;

    Tree(Tree parent, Tree left, Tree right, Node content){
        this.parent = parent;
        this.left = left;
        this.right = right;
        this.content = content;
    }

    public Integer decideTree(){
        if(this.content.getTypeOfNode() == TypeOfNode.NUMBER)
            return Integer.parseInt(this.content.getValue());

        else if(this.content.getTypeOfNode() == TypeOfNode.PLUS)
            return this.left.decideTree() + this.right.decideTree();

        else if(this.content.getTypeOfNode() == TypeOfNode.MINUS)
            return this.left.decideTree() - this.right.decideTree();

        else if(this.content.getTypeOfNode() == TypeOfNode.MULTIPLY)
            return this.left.decideTree() * this.right.decideTree();

        else if(this.content.getTypeOfNode() == TypeOfNode.DIVIDE)
            return this.left.decideTree() / this.right.decideTree();

        else if(this.content.getTypeOfNode() == TypeOfNode.REMAINDER)
            return this.left.decideTree() % this.right.decideTree();

        else if(this.content.getTypeOfNode() == TypeOfNode.EQUALLY){
            if(this.left.decideTree() == this.right.decideTree())
                return 1;
            else    return 0;
        }
        else if(this.content.getTypeOfNode() == TypeOfNode.MORE){
            if(this.left.decideTree() > this.right.decideTree())
                return 1;
            else    return 0;
        }
        else if(this.content.getTypeOfNode() == TypeOfNode.LESS){
            if(this.left.decideTree() < this.right.decideTree())
                return 1;
            else    return 0;
        }
        else if(this.content.getTypeOfNode() == TypeOfNode.THEN){
            if(this.left.decideTree() == 0)
                return this.right.right.decideTree();
            else    return this.right.left.decideTree();
        }

        return null;
    }

    public Node getContent() {
        return this.content;
    }

    public void findParents() {
        if(this.left != null){
            this.left.parent = this;
            this.left.findParents();
        }
        if(this.right != null){
            this.right.parent = this;
            this.right.findParents();
        }
    }

    public void setContent(Node j) {
        if(j == null)
            content = null;
        else
            content = new Node(j);
    }
}
