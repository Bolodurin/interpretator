import utils.*;

import java.util.ArrayList;

public class Program {
    private ArrayList<Tree> functionDefinition;
    private int numOfString;
    private int position;
    private String[] textOfProgram;
    private Tree expression;
    private char[] currentLine;

    public Program(String[] textOfProgram) throws ValidationException{
        this.numOfString = 0;
        this.position = 0;
        this.textOfProgram = textOfProgram;
        functionDefinition = getProgram();
    }

    private ArrayList<Tree> getProgram() throws ValidationException {
        ArrayList<Tree> ret = new ArrayList<>();
        while (true){
            currentLine = textOfProgram[numOfString].toCharArray();
            Tree newFunc = getFunctionDefinition();
            if (newFunc == null)   break;
            ret.add(newFunc);
            numOfString++;
            position = 0;
        }

        ParseExpression parserExpr = new ParseExpression(new String(currentLine));
        expression = parserExpr.getBuiltTree();

        return ret;
    }

    private Tree getFunctionDefinition() throws ValidationException{
        String nameFunc = getIdentifier();
        if(nameFunc == null)    return null;

        if(currentLine[position] == '(')
            throw new SyntaxError();
        position++;

        Tree leftBranch = getListParams();
        Node node = new Node(TypeOfNode.FUNCTION, nameFunc);

        //assert(currentLine[position] == ')');
        position++;
        if(currentLine[position] != '='){
            position = 0;
            return null;
        }
        position++;
        if(currentLine[position] == '{')
            throw new SyntaxError();
        position++;

        StringBuilder expr = new StringBuilder();
        while(currentLine[position] != '}'){
            expr.append(currentLine[position]);
            position++;
        }

        Tree rightBranch = (new ParseExpression(expr.toString())).getBuiltTree();

        return new Tree(null, leftBranch, rightBranch, node);
    }

    private Tree getListParams() {

        Node node = new Node(TypeOfNode.PARAMETER, getIdentifier());

        Tree leftBranch = null;
        if(currentLine[position] == ','){
            position++;
            leftBranch = getListParams();
        }

        return new Tree(null, leftBranch, null, node);
    }

    private String getIdentifier(){
        StringBuilder value = new StringBuilder();
        char currChar = currentLine[position];

        while ((currChar >= 'a' &&
                currChar <= 'z') ||
                (currChar >= 'A' &&
                currChar <= 'Z') ||
                currChar == '_'){

            value.append(currChar);
            position++;
            currChar = currentLine[position];
        }

        //assert(value.toString().charAt(0) != '_');

        if(value.length() > 0)   return value.toString();
        return  null;
    }

    public int decideProgram() throws ValidationException{
        Integer ret = decideExpr(this.expression, null);
        assert(ret != null);
        return ret;
    }

    private Integer decideExpr(Tree expression, Tree currentFunc)
            throws ValidationException{

        if (expression.getContent().getTypeOfNode() == TypeOfNode.NUMBER)
            return Integer.parseInt(expression.getContent().getValue());

        else if (expression.getContent().getTypeOfNode() == TypeOfNode.PLUS)
            return decideExpr(expression.left, currentFunc) +
                    decideExpr(expression.right, currentFunc);

        else if (expression.getContent().getTypeOfNode() == TypeOfNode.MINUS)
            return decideExpr(expression.left, currentFunc) -
                    decideExpr(expression.right, currentFunc);

        else if (expression.getContent().getTypeOfNode() == TypeOfNode.MULTIPLY)
            return decideExpr(expression.left, currentFunc) *
                    decideExpr(expression.right, currentFunc);

        else if (expression.getContent().getTypeOfNode() == TypeOfNode.DIVIDE) {
            Integer divided = decideExpr(expression.right, currentFunc);
            Integer dividing = decideExpr(expression.left, currentFunc);
            assert(divided != null && dividing != null);
            if(divided == 0)
                throw new RuntimeException("("+dividing.toString()+
                        "/"+divided.toString()+"):" + Integer.toString(numOfString));
            return dividing / divided;
        }

        else if (expression.getContent().getTypeOfNode() == TypeOfNode.REMAINDER)
            return decideExpr(expression.left, currentFunc) %
                    decideExpr(expression.right, currentFunc);

        else if (expression.getContent().getTypeOfNode() == TypeOfNode.EQUALLY) {
            if (decideExpr(expression.left, currentFunc)
                    .equals(decideExpr(expression.right, currentFunc)))
                return 1;
            else return 0;
        }
        else if (expression.getContent().getTypeOfNode() == TypeOfNode.MORE) {
            if (decideExpr(expression.left, currentFunc) >
                    decideExpr(expression.right, currentFunc))
                return 1;
            else return 0;
        }
        else if (expression.getContent().getTypeOfNode() == TypeOfNode.LESS) {
            if (decideExpr(expression.left, currentFunc) <
                    decideExpr(expression.right, currentFunc))
                return 1;
            else return 0;
        }
        else if (expression.getContent().getTypeOfNode() == TypeOfNode.THEN) {
            if (decideExpr(expression.left, currentFunc) == 0)
                return decideExpr(expression.right.right, currentFunc);
            else return decideExpr(expression.right.left, currentFunc);
        }
        else if (expression.getContent().getTypeOfNode() == TypeOfNode.CALLING){
            Tree findingFunc = null;
            for(Tree func : functionDefinition){
                if(func.getContent().getValue()
                        .equals(expression.left.getContent().getValue())){
                    findingFunc = func;
                }
            }

            if(findingFunc == null){
                throw new FunctionNotFound(expression.left.getContent().getValue() +
                        ":" + Integer.toString(numOfString));
            }

            Tree param = findingFunc.left;
            Tree arg = expression.right;
            ArrayList<Node> currentArgs = new ArrayList<>();
            while (param != null || arg != null){
                if(param == null || arg == null){
                    throw new ArgsNumMismatch(findingFunc.getContent().getValue() +
                            ":" + Integer.toString(numOfString));
                }

                if(param.right == null){
                    currentArgs.add(null);
                }
                else{
                    currentArgs.add(param.right.getContent());
                }

                Integer decidedResOfArg = decideExpr(arg.left, currentFunc);
                assert(decidedResOfArg != null);
                Node node = new Node(TypeOfNode.NUMBER, decidedResOfArg.toString());
                param.right = new Tree(param, null, null, node);

                arg = arg.right;
                param = param.left;
            }


            Integer ret = decideExpr(findingFunc.right, findingFunc);
            param = findingFunc.left;
            for (Node j : currentArgs){
                param.right.setContent(j);
                param = param.left;
            }
            return ret;
        }
        else if(expression.getContent().getTypeOfNode() == TypeOfNode.IDENTIFIER){
            if(currentFunc == null){
                throw new ParameterNotFound(expression.getContent().getValue() +
                        ":" + Integer.toString(numOfString));
            }

            Tree argOfCurrFunc = currentFunc.left;
            String nameOfParam = expression.getContent().getValue();

            while (argOfCurrFunc != null){

                if(argOfCurrFunc.getContent().getTypeOfNode() == TypeOfNode.PARAMETER){

                    if(nameOfParam.equals(argOfCurrFunc.getContent().getValue())){
                        assert(argOfCurrFunc.right != null);
                        return Integer.parseInt(argOfCurrFunc.right.getContent().getValue());
                    }
                    else argOfCurrFunc = argOfCurrFunc.left;
                }
                else    throw new SyntaxError();
            }

            if(argOfCurrFunc == null){
                throw new ParameterNotFound(nameOfParam +
                        ":" + Integer.toString(numOfString));
            }
            throw new SyntaxError();
        }
        throw new SyntaxError();
    }
}
