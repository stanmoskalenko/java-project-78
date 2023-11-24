package hexlet.code;

public final class Validator {
    StringSchema string() {
        return new StringSchema();
    }

    NumberSchema number() {
        return new NumberSchema();
    }
}
