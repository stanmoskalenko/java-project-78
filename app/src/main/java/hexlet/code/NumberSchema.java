package hexlet.code;

public final class NumberSchema extends BaseSchema<NumberSchema> {
    private boolean pos = false;
    private int minValue = Integer.MIN_VALUE;
    private int maxValue = Integer.MAX_VALUE;

    public NumberSchema positive() {
        this.pos = true;
        return this;
    }

    public NumberSchema required() {
        super.required = true;
        return this;
    }

    public NumberSchema range(int origin, int bound) {
        this.minValue = origin;
        this.maxValue = bound;
        return this;
    }

    public boolean isValid(Object data) {
        if (super.required && data == null) {
            return false;
        }

        if (data == null) {
            data = 0;
        }

        if (data.getClass() == Integer.class) {
            var checkedInt = (Integer) data;

            if (pos && checkedInt < 0) {
                return false;
            }

            return checkedInt.compareTo(minValue) >= 0 && checkedInt.compareTo(maxValue) <= 0;
        } else {
            return false;
        }
    }
}
