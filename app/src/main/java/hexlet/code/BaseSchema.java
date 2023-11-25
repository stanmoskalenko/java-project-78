package hexlet.code;

public abstract class BaseSchema {
    protected boolean required = false;

    public abstract boolean isValid(Object data);
}
