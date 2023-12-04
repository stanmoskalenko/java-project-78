package hexlet.code;

import hexlet.code.schemas.BaseSchema;
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
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FIVE = 5;

    @Test
    @DisplayName("Map schema validate is correctly")
    void mapSchemaTest() {
        var numberSchema = new Validator().number().positive().range(ORIGIN, BOUND);
        var stringSchema = new Validator().string().required().contains("test").minLength(FIVE);

        Map<String, BaseSchema> testSchemas = new HashMap<>();
        testSchemas.put("numbers", numberSchema);
        testSchemas.put("strings", stringSchema);

        var testOneEntry = Map.of("numberValue", Integer.MAX_VALUE);
        var testTwoEntry = Map.of(
                "numberValue", THREE,
                "stringValue", "testValue");
        var testTwoEntryWithNullNumber = new HashMap<>();
        testTwoEntryWithNullNumber.put("numberValue", null);
        testTwoEntryWithNullNumber.put("stringValue", "testValue");

        var testTwoInvalidStringEntry = Map.of(
                "numberValue", THREE,
                "stringValue", "value value");
        var testTwoInvalidNumberEntry = Map.of(
                "numberValue", Integer.MAX_VALUE,
                "stringValue", "test value");
        var testThreeEntry = Map.of(
                "numberValue", THREE,
                "stringValue", "testValue",
                "numberValue2", Integer.MAX_VALUE);
        var testEmptyMap = Map.of();


        var validator = new Validator().map();
        assertTrue(validator.isValid(testTwoEntry));
        assertTrue(validator.isValid(testThreeEntry));
        assertTrue(validator.isValid(testEmptyMap));
        assertTrue(validator.isValid(null));
        assertFalse(validator.isValid(Integer.MAX_VALUE));

        validator.sizeof(TWO);
        assertTrue(validator.isValid(testTwoEntry));
        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(testEmptyMap));
        assertFalse(validator.isValid(testThreeEntry));
        assertFalse(validator.isValid(testOneEntry));

        validator.shape(testSchemas);
        assertTrue(validator.isValid(testTwoEntry));
        assertTrue(validator.isValid(testTwoEntryWithNullNumber));
        assertFalse(validator.isValid(testTwoInvalidNumberEntry));
        assertFalse(validator.isValid(testTwoInvalidStringEntry));

        validator.required();
        assertFalse(validator.isValid(testEmptyMap));
        assertFalse(validator.isValid(null));
    }

    @Test
    @DisplayName("Map schema validate  with nested map is correctly")
    void nestedMapSchemaTest() {
        var numberSchema = new Validator().number().positive().range(ORIGIN, BOUND);
        var stringSchema = new Validator().string().required().contains("test").minLength(FIVE);
        var mapSchema = new Validator().map().required().sizeof(THREE);

        Map<String, BaseSchema> testSchemas = new HashMap<>();
        testSchemas.put("numbers", numberSchema);
        testSchemas.put("strings", stringSchema);
        testSchemas.put("maps", mapSchema);

        var testOneEntry = Map.of("numberValue", Integer.MAX_VALUE);
        var testTwoEntry = Map.of(
                "numberValue", THREE,
                "stringValue", "testValue");
        var testTwoEntryWithNullNumber = new HashMap<>();
        testTwoEntryWithNullNumber.put("numberValue", null);
        testTwoEntryWithNullNumber.put("stringValue", "testValue");

        var testTwoInvalidStringEntry = Map.of(
                "numberValue", THREE,
                "stringValue", "value value");
        var testTwoInvalidNumberEntry = Map.of(
                "numberValue", Integer.MAX_VALUE,
                "stringValue", "test value");
        var testThreeEntry = Map.of(
                "numberValue", THREE,
                "stringValue", "testValue",
                "numberValue2", Integer.MAX_VALUE);

        var testEmptyMap = Map.of();

        var testNestedEmptyMap = Map.of("map", Map.of());
        var testNestedOneEntryMap = Map.of("map", testOneEntry);

        var testMultipleValidEntry = Map.of(
                "numberValue", THREE,
                "maps", testTwoEntry);

        var testMultipleInvalidEntry = Map.of(
                "numberValue", THREE,
                "maps", testTwoInvalidNumberEntry);

        var validator = new Validator().map();
        assertTrue(validator.isValid(testNestedEmptyMap));
        assertTrue(validator.isValid(testTwoEntryWithNullNumber));

        validator.sizeof(TWO);
        assertTrue(validator.isValid(testTwoEntry));
        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(testEmptyMap));
        assertFalse(validator.isValid(testThreeEntry));
        assertFalse(validator.isValid(testOneEntry));

        validator.shape(testSchemas);
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
