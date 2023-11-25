package hexlet.code.schemas;

public abstract class BaseSchema {
    protected boolean required = false;

    public abstract boolean isValid(Object data);
}
