package hexlet.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {
    private static final int ORIGIN = -5;
    private static final int BOUND = 5;
    private static final int BETWEEN_RANGE = 3;
    private static final int LARGE_VALUE = 99;
    private static final int LEN = 5;
    @Nested
    class StringTest {
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
            assertFalse(validator.isValid(""));
        }
    }

    @Nested
    class NumbersTest {
        private static final int ZERO = 0;
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
            assertFalse(validator.isValid(null));
            assertFalse(validator.isValid(ORIGIN));
            assertFalse(validator.isValid(ZERO));
        }
    }

    @Nested
    class MapTest {
        private static final int SIZE = 2;

        @Test
        @DisplayName("Map schema validate with 'required' and 'sizeOf' params is correctly")
        void fullParamsTest() {
            var testValueWithOneEntry = Map.of("firstKey", "firstValue");
            var testValueWithTwoEntry = Map.of(
                    "firstKey", "firstValue",
                    "secondKey", "secondValue");
            var testValueWithEmptyMap = Map.of();
            var testValueWithTThreeEntry = Map.of(
                    "firstKey", "firstValue",
                    "secondKey", "secondValue",
                    "thirdKey", "thirdValue");

            var validator = new Validator().map().required().sizeof(SIZE);

            assertTrue(validator.isValid(testValueWithTwoEntry));
            assertFalse(validator.isValid(testValueWithOneEntry));
            assertFalse(validator.isValid(testValueWithEmptyMap));
            assertFalse(validator.isValid(testValueWithTThreeEntry));
            assertFalse(validator.isValid(null));
            assertFalse(validator.isValid("some string"));
        }

        @Test
        @DisplayName("Map schema validate with only 'sizeOf' params is correctly")
        void onlySizeOfTest() {
            var testValueWithOneEntry = Map.of("firstKey", "firstValue");
            var testValueWithTwoEntry = Map.of(
                    "firstKey", "firstValue",
                    "secondKey", "secondValue");
            var testValueWithEmptyMap = Map.of();
            var testValueWithTThreeEntry = Map.of(
                    "firstKey", "firstValue",
                    "secondKey", "secondValue",
                    "thirdKey", "thirdValue");

            var validator = new Validator().map().sizeof(SIZE);

            assertTrue(validator.isValid(testValueWithTwoEntry));
            assertFalse(validator.isValid(null));
            assertFalse(validator.isValid(testValueWithOneEntry));
            assertFalse(validator.isValid(testValueWithEmptyMap));
            assertFalse(validator.isValid(testValueWithTThreeEntry));
            assertFalse(validator.isValid("some string"));
        }

        @Test
        @DisplayName("Map schema validate with only 'required' params is correctly")
        void onlyRequiredTest() {
            var testValueWithOneEntry = Map.of("firstKey", "firstValue");
            var testValueWithTwoEntry = Map.of(
                    "firstKey", "firstValue",
                    "secondKey", "secondValue");
            var testValueWithEmptyMap = Map.of();
            var testValueWithTThreeEntry = Map.of(
                    "firstKey", "firstValue",
                    "secondKey", "secondValue",
                    "thirdKey", "thirdValue");

            var validator = new Validator().map().required();

            assertTrue(validator.isValid(testValueWithEmptyMap));
            assertFalse(validator.isValid(testValueWithTwoEntry));
            assertFalse(validator.isValid(testValueWithOneEntry));
            assertFalse(validator.isValid(testValueWithTThreeEntry));
            assertFalse(validator.isValid(null));
            assertFalse(validator.isValid("some string"));
        }

        @Test
        @DisplayName("Map schema validate with only 'schema' params is correctly")
        void nestedValidationTest() {
            var numberSchema = new Validator().number().positive().range(ORIGIN, BOUND);
            var stringSchema = new Validator().string().required().contains("test").minLength(LEN);
            var schemas = Map.of("numbers", numberSchema, "strings", stringSchema);

            var testValidEntry = Map.of(
                    "numberValue", BETWEEN_RANGE,
                    "stringValue", "testValue");
            var testInvalidNumber = Map.of(
                    "numberValue", LARGE_VALUE,
                    "stringValue", "testValue");
            var testInvalidString = Map.of(
                    "numberValue", BOUND,
                    "stringValue", "value");
            var testInvalidNumberAndString = Map.of(
                    "numberValue", ORIGIN,
                    "stringValue", "value");
            var testInvalidEmptyMap = Map.of();

            var validator = new Validator().map().required().sizeof(SIZE).shape(schemas);

            assertTrue(validator.isValid(testValidEntry));
            assertFalse(validator.isValid(testInvalidEmptyMap));
            assertFalse(validator.isValid(testInvalidNumber));
            assertFalse(validator.isValid(testInvalidString));
            assertFalse(validator.isValid(testInvalidNumberAndString));
            assertFalse(validator.isValid(null));
            assertFalse(validator.isValid("some string"));
        }
    }
}
