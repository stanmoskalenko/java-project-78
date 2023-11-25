package hexlet.code.schemas;

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

    public MapSchema sizeof(int sizeNum) {
        size = sizeNum;
        return this;
    }

    @Override
    public boolean isValid(Object data) {
        if (super.required && (data == null || data instanceof Map<?, ?> checkedMap && checkedMap.isEmpty())) {
            return false;
        }

        if (data == null) {
            return true;
        } else if (data instanceof Map<?, ?> checkedMap) {

            if (checkedMap.isEmpty()) {
                return true;
            }

            if (size != 0 && checkedMap.size() != size) {
                return false;
            } else if (schemas != null) {
                return (checkedMap).values().stream()
                        .allMatch(value -> schemas.values().stream().anyMatch(schema -> schema.isValid(value)));
            }

            return true;
        }

        return false;
    }
}
