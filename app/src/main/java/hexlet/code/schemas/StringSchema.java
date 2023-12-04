package hexlet.code.schemas;


public final class StringSchema extends BaseSchema<String> {

    public StringSchema() {
        super(String.class);
    }

    public StringSchema required() {
        schemas.add(value -> value != null && !value.isBlank());
        return this;
    }

    public StringSchema minLength(int minLength) {
        schemas.add(value -> value == null || value.isBlank() || value.length() >= minLength);
        return this;
    }

    public StringSchema contains(String data) {
        schemas.add(value -> value == null || value.isBlank() || value.contains(data));
        return this;
    }
}
