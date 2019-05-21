import utils.SyntaxError;
import utils.ValidationException;

public class ParseExpression {
    private Tree builtTree;
    private char[] receivedCharArray;
    private int position;

    ParseExpression(String receivedString) throws ValidationException{
        this.position = 0;
        this.receivedCharArray = receivedString.toCharArray();
        builtTree = getExpression();

        assert(builtTree != null);
    }

    private Tree getExpression() throws ValidationException {
        char currChar = receivedCharArray[position];
        if(currChar == '('){

            position++;
            Tree leftBranch = getExpression();
            Node node = getBinaryOperator();
            Tree rightBranch = getExpression();

            if(receivedCharArray[position] != ')')
                throw new SyntaxError();
            assert((leftBranch != null) && (rightBranch != null) && (node != null));
            position++;

            return new Tree(null, leftBranch, rightBranch, node);
        }
        else if(currChar == '['){
            return getIfExpression();
        }
        else if(currChar == '-' ||
                (currChar >= '0' &&
                 currChar <= '9')){
            return getConstExpr();
        }
        else if((currChar >= 'a' &&
                 currChar <= 'z') ||
                (currChar >= 'A' &&
                 currChar <= 'Z') ||
                currChar == '_'){
            return getCallExpression();
        }
        return null;
    }

    private Tree getIfExpression() throws ValidationException{
        position++;
        Tree leftBranch = getExpression();

        assert(leftBranch != null);
        if(receivedCharArray[position] != ']')
            throw new SyntaxError();
        position++;
        if(receivedCharArray[position] != '?')
            throw new SyntaxError();
        position++;
        if(receivedCharArray[position] != '(')
            throw new SyntaxError();
        position++;

        Node thenNode = new Node(TypeOfNode.THEN, "");
        Tree rightLeftBranch = getExpression();
        Node elseNode = new Node(TypeOfNode.ELSE, "");

        assert(rightLeftBranch != null);
        if(receivedCharArray[position] != ')')
            throw new SyntaxError();
        position++;
        if(receivedCharArray[position] != ':')
            throw new SyntaxError();
        position++;
        if(receivedCharArray[position] != '(')
            throw new SyntaxError();
        position++;

        Tree rightRightBranch = getExpression();

        if(receivedCharArray[position] != ')')
            throw new SyntaxError();
        position++;
        assert(rightRightBranch != null);

        Tree thenBranch = new Tree(null, rightLeftBranch, rightRightBranch, elseNode);

        return new Tree(null, leftBranch, thenBranch, thenNode);
    }

    private Tree getCallExpression() throws ValidationException{

        Node identifier = getIdentifier();
        Tree leftBranch = new Tree(null, null, null, identifier);

        if (receivedCharArray[position] != '('){
            return new Tree(null, null, null, identifier);
        }
        position++;

        Tree rightBranch = getListArgument();

        if(receivedCharArray[position] != ')')
            throw new SyntaxError();
        position++;

        return new Tree(null, leftBranch, rightBranch,
                new Node(TypeOfNode.CALLING, ""));
    }

    private Tree getListArgument() throws ValidationException{
        Tree leftBranch = getExpression();

        assert(leftBranch != null);

        Node node = new Node(TypeOfNode.ARGUMENT, "");

        Tree rightBrach = null;
        if(receivedCharArray[position] == ','){
            position++;
            rightBrach = getListArgument();
        }

        return new Tree(null, leftBranch, rightBrach, node);
    }

    private Node getIdentifier() throws ValidationException{

        StringBuilder value = new StringBuilder();
        char currChar = receivedCharArray[position];

        while ((currChar >= 'a' &&
                currChar <= 'z') ||
               (currChar >= 'A' &&
                currChar <= 'Z') ||
               currChar == '_'){

            value.append(currChar);
            position++;
            currChar = receivedCharArray[position];
        }

        assert(value.length() != 0);
        if(value.toString().toCharArray()[0] == '_')
            throw new SyntaxError();

        return new Node(TypeOfNode.IDENTIFIER, value.toString());
    }

    private Node getBinaryOperator() throws ValidationException {

        TypeOfNode currType = null;
        String value;
        char currChar = receivedCharArray[position];

        if(currChar == '+') {
            currType = TypeOfNode.PLUS;
            value = Character.toString('+');
        }
        else if(currChar == '-') {
            currType = TypeOfNode.MINUS;
            value = Character.toString('-');
        }
        else if(currChar == '*') {
            currType = TypeOfNode.MULTIPLY;
            value = Character.toString('*');
        }
        else if(currChar == '/') {
            currType = TypeOfNode.DIVIDE;
            value = Character.toString('/');
        }
        else if(currChar == '%') {
            currType = TypeOfNode.REMAINDER;
            value = Character.toString('%');
        }
        else if(currChar == '=') {
            currType = TypeOfNode.EQUALLY;
            value = Character.toString('=');
        }
        else if(currChar == '>') {
            currType = TypeOfNode.MORE;
            value = Character.toString('>');
        }
        else if(currChar == '<') {
            currType = TypeOfNode.EQUALLY;
            value = Character.toString('<');
        }
        else throw new SyntaxError();
        position++;

        return new Node(currType, value);
    }

    private Tree getConstExpr() throws ValidationException{

        String value;

        if(receivedCharArray[position] == '-'){
            position++;
            value = Character.toString('-') + getNumber();
        }
        else   value = getNumber();

        assert(value.length() != 0);
        assert(!value.equals("-"));
        Node node = new Node(TypeOfNode.NUMBER, value);

        return new Tree(null, null, null, node);
    }

    private String getNumber() throws ValidationException{
        StringBuilder value = new StringBuilder();
        char currChar = receivedCharArray[position];

        while (currChar >= '0' && currChar <= '9'){
            value.append(currChar);
            position++;
            if(position < receivedCharArray.length)
                currChar = receivedCharArray[position];
            else   break;
        }


        assert(value.length() != 0);
        if(value.toString().toCharArray()[0] == '0' && value.length() > 1)
            throw new SyntaxError();

        return value.toString();
    }

    public Tree getBuiltTree(){
        return builtTree;
    }
}
