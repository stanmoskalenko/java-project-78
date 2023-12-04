package hexlet.code.schemas;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    private final Class<T> dataType;

    protected BaseSchema(Class<T> type) {
        this.dataType = type;
    }

    protected final Class<T> getDataType() {
        return dataType;
    }

    protected final Set<Predicate<T>> schemas = new HashSet<>();

    public final boolean isValid(Object data) {
        if (data != null && !dataType.isInstance(data)) {
            return false;
        }

        return schemas.stream().allMatch(schema -> schema.test((T) data));
    }
}
