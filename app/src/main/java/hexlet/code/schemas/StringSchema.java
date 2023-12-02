package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema() {
        super(String.class);
    }

    public StringSchema required() {
        validator.add(value -> value != null && !value.isBlank());
        return this;
    }

    public StringSchema minLength(int minLength) {
        validator.add(value -> value == null || value.isBlank() || value.length() >= minLength);
        return this;
    }

    public StringSchema contains(String data) {
        validator.add(value -> value == null || value.isBlank() || value.contains(data));
        return this;
    }
}
