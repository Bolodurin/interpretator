package utils;

public class ParameterNotFound extends ValidationException {
    public ParameterNotFound(String info){super("PARAMETER NOT FOUND " + info);}
}
