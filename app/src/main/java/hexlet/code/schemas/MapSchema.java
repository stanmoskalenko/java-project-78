package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map> {

    public MapSchema() {
        super(Map.class);
    }

    public MapSchema required() {
        schemas.add(value -> value != null && !value.isEmpty());
        return this;
    }

    private boolean checkMap(Map<?, BaseSchema> schemasProperty, Map<?, ?> data) {
        return data.values().stream()
                .allMatch(value -> {
                    if (value instanceof Map<?, ?> nestedMap) {
                        return checkMap(schemasProperty, nestedMap);
                    }
                    return schemasProperty.values().stream()
                            .filter(schema -> schema.type.isInstance(value))
                            .allMatch(schema -> schema.isValid(value));
                });
    }

    public MapSchema shape(Map<?, BaseSchema> schemasProperty) {
        schemas.add(data -> data == null || checkMap(schemasProperty, data));
        return this;
    }

    public MapSchema sizeof(int count) {
        schemas.add(value -> value == null || value.isEmpty() || value.size() == count);
        return this;
    }
}
