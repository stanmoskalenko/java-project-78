package hexlet.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Validator tests")
class ValidatorTest {
    private static final int ORIGIN = -5;
    private static final int BOUND = 5;
    private static final int BETWEEN_RANGE = 3;
    private static final int LEN = 5;
    private static final int SIZE = 2;

    @Test
    @DisplayName("Number schema validate is correctly")
    void numbersSchemaTest() {
        var validator = new Validator().number();
        assertTrue(validator.isValid(Integer.MAX_VALUE));
        assertTrue(validator.isValid(Integer.MIN_VALUE));
        assertTrue(validator.isValid(null));

        validator.positive();
        assertTrue(validator.isValid(Integer.MAX_VALUE));
        assertFalse(validator.isValid(Integer.MIN_VALUE));
        assertTrue(validator.isValid(null));

        validator.required();
        assertTrue(validator.isValid(Integer.MAX_VALUE));
        assertFalse(validator.isValid(Integer.MIN_VALUE));
        assertFalse(validator.isValid(null));

        validator.range(ORIGIN, BOUND);
        assertTrue(validator.isValid(BETWEEN_RANGE));
        assertTrue(validator.isValid(BOUND));
        assertFalse(validator.isValid(Integer.MIN_VALUE));
        assertFalse(validator.isValid(Integer.MAX_VALUE));
        assertFalse(validator.isValid(null));
        assertFalse(validator.isValid(""));
    }

    @Test
    @DisplayName("String schema validate is correctly")
    void stringsSchemaTest() {

        var validator = new Validator().string();
        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid("test string schema"));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid(Integer.MAX_VALUE));

        validator.minLength(LEN);
        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid(Integer.MAX_VALUE));

        validator.contains("test");
        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid("test string schema"));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid("test"));
        assertFalse(validator.isValid(Integer.MAX_VALUE));

        validator.required();
        assertTrue(validator.isValid("test string schema"));
        assertFalse(validator.isValid("test"));
        assertFalse(validator.isValid(null));
        assertFalse(validator.isValid(""));
        assertFalse(validator.isValid(Integer.MAX_VALUE));
    }

    @Test
    @DisplayName("Map schema validate is correctly")
    void mapSchemaTest() {
        var numberSchema = new Validator().number().positive().range(ORIGIN, BOUND);
        var stringSchema = new Validator().string().required().contains("test").minLength(LEN);
        var mapSchema = new Validator().map().required().sizeof(SIZE);
        var schemas = Map.of(
                "numbers", numberSchema,
                "strings", stringSchema,
                "maps", mapSchema);

        var testOneEntry = Map.of("numberValue", Integer.MAX_VALUE);
        var testTwoEntry = Map.of(
                "numberValue", BETWEEN_RANGE,
                "stringValue", "testValue");
        var testTwoEntryWithNullNumber = new HashMap<>();
        testTwoEntryWithNullNumber.put("numberValue", null);
        testTwoEntryWithNullNumber.put("stringValue", "testValue");

        var testTwoInvalidStringEntry = Map.of(
                "numberValue", BETWEEN_RANGE,
                "stringValue", "value value");
        var testTwoInvalidNumberEntry = Map.of(
                "numberValue", Integer.MAX_VALUE,
                "stringValue", "test value");
        var testThreeEntry = Map.of(
                "numberValue", BETWEEN_RANGE,
                "stringValue", "testValue",
                "numberValue2", Integer.MAX_VALUE);
        var testEmptyMap = Map.of();

        var testNestedEmptyMap = Map.of("map", Map.of());
        var testNestedOneEntryMap = Map.of("map", testOneEntry);

        var testMultipleValidEntry = Map.of(
                "numberValue", BETWEEN_RANGE,
                "maps", testTwoEntry);

        var testMultipleInvalidEntry = Map.of(
                "numberValue", BETWEEN_RANGE,
                "maps", testTwoInvalidNumberEntry);

        var validator = new Validator().map();
        assertTrue(validator.isValid(testTwoEntry));
        assertTrue(validator.isValid(testThreeEntry));
        assertTrue(validator.isValid(testEmptyMap));
        assertTrue(validator.isValid(null));
        assertFalse(validator.isValid(Integer.MAX_VALUE));
        assertTrue(validator.isValid(testNestedEmptyMap));

        validator.sizeof(SIZE);
        assertTrue(validator.isValid(testTwoEntry));
        assertTrue(validator.isValid(testEmptyMap));
        assertTrue(validator.isValid(null));
        assertFalse(validator.isValid(testThreeEntry));
        assertFalse(validator.isValid(testOneEntry));

        validator.shape(schemas);
        assertTrue(validator.isValid(testTwoEntry));
        assertTrue(validator.isValid(testMultipleValidEntry));
        assertTrue(validator.isValid(testTwoEntryWithNullNumber));
        assertFalse(validator.isValid(testMultipleInvalidEntry));
        assertFalse(validator.isValid(testTwoInvalidNumberEntry));
        assertFalse(validator.isValid(testTwoInvalidStringEntry));
        assertFalse(validator.isValid(testNestedOneEntryMap));


        validator.required();
        assertFalse(validator.isValid(testEmptyMap));
        assertFalse(validator.isValid(null));
    }
}
