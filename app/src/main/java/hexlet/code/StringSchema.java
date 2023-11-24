package hexlet.code;

public final class StringSchema {
    private boolean required = false;
    private int minLen;
    private String substr = "";

    public StringSchema required() {
        this.required = true;
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

    public boolean isValid(Object data) {
        if (required && data == null) {
            return false;
        }

        if (data.getClass() == String.class) {
            var checkedStr = (String) data;
            return checkedStr.contains(substr) && checkedStr.length() >= minLen;
        } else {
            return false;
        }
    }
}
