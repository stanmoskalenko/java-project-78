package hexlet.code.schemas;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected final Class<T> type;

    protected BaseSchema(Class<T> type) {
        this.type = type;
    }

    protected final Set<Predicate<T>> schemas = new HashSet<>();

    public final boolean isValid(Object data) {
        if (data != null && !type.isInstance(data)) {
            return false;
        }

        return schemas.stream().allMatch(schema -> schema.test((T) data));
    }
}
