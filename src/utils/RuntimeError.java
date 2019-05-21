package utils;

public class RuntimeError extends ValidationException {
    public RuntimeError(String info){super("RUNTIME ERROR " + info);}
}
