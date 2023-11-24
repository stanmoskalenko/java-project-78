package hexlet.code;

public abstract class BaseSchema<T extends BaseSchema<T>> {
    protected boolean required = false;

    public abstract T required();

    public abstract boolean isValid(Object data);
}
