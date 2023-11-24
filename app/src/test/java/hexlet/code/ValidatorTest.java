package hexlet.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {
    @Nested
    class StringTest {
        private static final int LEN = 5;

        @Test
        @DisplayName("String schema validate with full params is correctly")
        void fullParamsTest() {
            var valueWithSubstr = "Test 99 test java";
            var valueWithoutSubstr = "Test 99 f java";
            var valueLengthIsLessMinParams = "test";
            var validator = new Validator().string()
                    .required()
                    .minLength(LEN)
                    .contains("test");

            assertTrue(validator.isValid(valueWithSubstr));
            assertFalse(validator.isValid(valueWithoutSubstr));
            assertFalse(validator.isValid(valueLengthIsLessMinParams));
            assertFalse(validator.isValid(null));
            assertFalse(validator.isValid(LEN));
            assertFalse(validator.isValid(""));
        }

        @Test
        @DisplayName("String schema validate with only 'contains' param is correctly")
        void onlyContainsTest() {
            var testValue = "T t";
            var valueWithoutSubstr = " T U G";
            var validator = new Validator().string().contains("t");

            assertTrue(validator.isValid(testValue));
            assertFalse(validator.isValid(valueWithoutSubstr));
        }

        @Test
        @DisplayName("String schema validate with only 'required' param is correctly")
        void onlyRequiredTest() {
            var testValue = "must be true";
            var validator = new Validator().string().required();

            assertTrue(validator.isValid(testValue));
        }
    }

    @Nested
    class NumbersTest {
        private static final int ORIGIN = -5;
        private static final int BOUND = 5;
        private static final int BETWEEN_RANGE = 3;
        private static final int LARGE_VALUE = 99;

        @Test
        @DisplayName("Number schema validate with full params is correctly")
        void fullParamsTest() {
            var validator = new Validator().number()
                    .required()
                    .positive()
                    .range(ORIGIN, BOUND);

            assertTrue(validator.isValid(BETWEEN_RANGE));
            assertFalse(validator.isValid(LARGE_VALUE));
            assertFalse(validator.isValid(ORIGIN));
            assertFalse(validator.isValid(null));
            assertFalse(validator.isValid(""));
        }


        @Test
        @DisplayName("Number schema validate with only 'range' param is correctly")
        void onlyRangeTest() {
            var validator = new Validator().number().range(ORIGIN, BOUND);

            assertTrue(validator.isValid(BETWEEN_RANGE));
            assertTrue(validator.isValid(BOUND));
            assertFalse(validator.isValid(LARGE_VALUE));
        }

        @Test
        @DisplayName("Number schema validate with only 'positive' param is correctly")
        void onlyPositiveTest() {
            var validator = new Validator().number().positive();

            assertTrue(validator.isValid(BETWEEN_RANGE));
            assertTrue(validator.isValid(BOUND));
            assertTrue(validator.isValid(null));
            assertFalse(validator.isValid(ORIGIN));
        }
    }
}
