package hexlet.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    private static final int LEN = 5;

    @Nested
    class StringTest {
        @Test
        @DisplayName("String schema validate with full params is correctly")
        void fullParamsTest() {
            var valueWithSubstr = "Test 99 test java";
            var valueWithoutSubstr = "Test 99 f java";
            var valueLengthIsLessMinParams = "test";
            var validator = new Validator().string();
            validator.required().minLength(LEN).contains("test");

            assertTrue(validator.isValid(valueWithSubstr));
            assertFalse(validator.isValid(valueWithoutSubstr));
            assertFalse(validator.isValid(valueLengthIsLessMinParams));
            assertFalse(validator.isValid(null));
            assertFalse(validator.isValid(""));
        }

        @Test
        @DisplayName("String schema validate with only contains param is correctly")
        void onlyContainsTest() {
            var testValue = "T t";
            var valueWithoutSubstr = " T U G";
            var validator = new Validator().string();
            validator.contains("t");

            assertTrue(validator.isValid(testValue));
            assertFalse(validator.isValid(valueWithoutSubstr));
        }

        @Test
        @DisplayName("String schema validate with only required param is correctly")
        void onlyRequiredTest() {
            var testValue = "must be true";
            var validator = new Validator().string();
            validator.required();

            assertTrue(validator.isValid(testValue));
        }
    }
}
