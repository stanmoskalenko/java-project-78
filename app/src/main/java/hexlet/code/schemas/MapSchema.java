package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map> {

    public MapSchema() {
        super(Map.class);
    }

    public MapSchema required() {
        validator.add(value -> value != null && !value.isEmpty());
        return this;
    }

    public MapSchema shape(Map<?, BaseSchema> schemasProperty) {
        validator.add(value -> {
            if (value == null) {
                return false;
            }
            return value.values().stream()
                    .allMatch(v -> schemasProperty.values().stream()
                            .anyMatch(i -> i.isValid(v)));
        });
        return this;
    }

    public MapSchema sizeof(Integer count) {
        validator.add(value -> value == null || value.size() == count);
        return this;
    }
}
