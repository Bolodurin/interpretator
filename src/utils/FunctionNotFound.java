package utils;

public class FunctionNotFound extends ValidationException{
    public FunctionNotFound(String info){super("FUNCTION NOT FOUND " + info);}
}
