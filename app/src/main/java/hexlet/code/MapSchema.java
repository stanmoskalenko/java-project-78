package hexlet.code;

import java.util.Map;

public final class MapSchema extends BaseSchema<MapSchema> {

    private int size;

    @Override
    public MapSchema required() {
        super.required = true;
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
            return ((Map<?, ?>) data).size() == size;
        }

        return false;
    }
}
