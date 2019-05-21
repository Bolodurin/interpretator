package utils;

public class ArgsNumMismatch extends ValidationException{
    public ArgsNumMismatch(String info){super("ARGUMENT NUMBER MISMATCH " + info);}
}
