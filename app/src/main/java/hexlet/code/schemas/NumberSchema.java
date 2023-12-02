package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Number> {


    public NumberSchema() {
        super(Number.class);
    }

    public NumberSchema positive() {
        validator.add(value -> value == null || value.doubleValue() >= 0);
        return this;
    }

    public NumberSchema required() {
        validator.add(Objects::nonNull);
        return this;
    }

    public NumberSchema range(int origin, int bound) {
        validator.add(value -> value == null || value.doubleValue() >= origin && value.doubleValue() <= bound);
        return this;
    }
}
