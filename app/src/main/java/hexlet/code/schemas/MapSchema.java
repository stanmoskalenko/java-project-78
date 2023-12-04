package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public final class MapSchema extends BaseSchema<Map> {

    public MapSchema() {
        super(Map.class);
    }

    public MapSchema required() {
        schemas.add(Objects::nonNull);
        return this;
    }

    private boolean checkMap(Map<?, BaseSchema> schemasProperty, Map<?, ?> data) {
        return data.values().stream()
                .allMatch(value -> {
                    if (value instanceof Map<?, ?> nestedMap) {
                        return checkMap(schemasProperty, nestedMap);
                    }
                    return schemasProperty.values().stream()
                            .filter(schema -> schema.getType().isInstance(value))
                            .allMatch(schema -> schema.isValid(value));
                });
    }

    public MapSchema shape(Map<?, BaseSchema> schemasProperty) {
        schemas.add(data -> data == null || checkMap(schemasProperty, data));
        return this;
    }

    public MapSchema sizeof(int count) {
        schemas.add(value -> value == null || value.size() == count);
        return this;
    }
}
