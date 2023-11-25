package hexlet.code;

import java.util.Map;

public final class MapSchema extends BaseSchema {

    private int size;

    private Map<String, BaseSchema> schemas;

    public MapSchema required() {
        super.required = true;
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemasProperty) {
        schemas = schemasProperty;
        return this;
    }

    public MapSchema sizeOf(int sizeNum) {
        size = sizeNum;
        return this;
    }

    @Override
    public boolean isValid(Object data) {
        if (super.required && data == null) {
            return false;
        }
        if (data instanceof Map) {
            if (((Map<?, ?>) data).size() != size) {
                return false;
            } else if (schemas != null) {
                return ((Map<?, ?>) data).values().stream()
                        .allMatch(value -> schemas.values().stream().anyMatch(schema -> schema.isValid(value)));
            } else {
                return true;
            }
        }

        return false;
    }
}
