public enum TypeOfNode {
    NUMBER((short) 1),
    IDENTIFIER((short) 2),
    CONDITION((short) 3),
    PLUS((short) 4),
    MINUS((short) 5),
    MULTIPLY((short) 6),
    DIVIDE((short) 7),
    EQUALLY((short) 8),
    MORE((short) 9),
    LESS((short) 10),
    REMAINDER((short) 11),
    CALLING((short) 12),
    ARGUMENT((short) 13),
    THEN((short) 14),
    ELSE((short) 15),
    FUNCTION((short) 16),
    PARAMETER((short) 17);

    private short type;

    TypeOfNode(short type){
        this.type = type;
    }

    public short getType() {
        return type;
    }

    public static TypeOfNode parse(short type){
        TypeOfNode typeOfNode = null;
        for(TypeOfNode iter : TypeOfNode.values()){
            if(iter.getType() == type) {
                typeOfNode = iter;
                break;
            }
        }
        return typeOfNode;
    }
}
