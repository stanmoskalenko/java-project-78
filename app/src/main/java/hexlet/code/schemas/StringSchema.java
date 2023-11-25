package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {
    private int minLen;
    private String substr;

    public StringSchema required() {
        super.required = true;
        return this;
    }

    public StringSchema minLength(int minLength) {
        this.minLen = minLength;
        return this;
    }

    public StringSchema contains(String data) {
        this.substr = data;
        return this;
    }

    @Override
    public boolean isValid(Object data) {
        if (super.required && (data == null || String.valueOf(data).isBlank())) {
            return false;
        }

        if (data == null) {
            return true;
        } else if (data instanceof String checkedStr) {

            if (checkedStr.isBlank()) {
                return true;
            }

            if (minLen != 0 && checkedStr.length() < minLen) {
                return false;
            }
            if (substr != null) {
                return checkedStr.contains(substr);
            }
        }

        return false;
    }
}
