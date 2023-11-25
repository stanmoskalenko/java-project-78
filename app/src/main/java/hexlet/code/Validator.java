package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

public final class Validator {
    StringSchema string() {
        return new StringSchema();
    }

    NumberSchema number() {
        return new NumberSchema();
    }

    MapSchema map() {
        return new MapSchema();
    }
}
