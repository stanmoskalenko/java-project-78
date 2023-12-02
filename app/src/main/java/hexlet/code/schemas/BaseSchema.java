package hexlet.code.schemas;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {

    private final Class<T> type;

    protected BaseSchema(Class<T> type) {
        this.type = type;
    }

    public final Set<Predicate<T>> validator = new HashSet<>();

    public  final boolean isValid(Object data) {
        if (data != null && !type.isInstance(data)) {
            return false;
        }

        if (validator.isEmpty()) {
            return true;
        }

        return validator.stream().allMatch(schema -> schema.test((T) data));

    }
}
