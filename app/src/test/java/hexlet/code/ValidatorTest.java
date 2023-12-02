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
    void mapSchemaTest() {
        var numberSchema = new Validator().number().positive().range(ORIGIN, BOUND);
        var stringSchema = new Validator().string().required().contains("test").minLength(FIVE);

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("numbers", numberSchema);
        schemas.put("strings", stringSchema);

        Map<String, Object> testTwoEntry = new HashMap<>();
        testTwoEntry.put("numberValue", THREE);
        testTwoEntry.put("stringValue", "testValue");

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
        var testOneEntry = Map.of("numberValue", Integer.MAX_VALUE);
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
        assertFalse(validator.isValid(testEmptyMap));
        assertFalse(validator.isValid(testThreeEntry));
        assertFalse(validator.isValid(testOneEntry));

        validator.shape(schemas);
        assertTrue(validator.isValid(testTwoEntry));
        assertTrue(validator.isValid(testTwoEntryWithNullNumber));
        assertFalse(validator.isValid(testTwoInvalidNumberEntry));
        assertFalse(validator.isValid(testTwoInvalidStringEntry));

        validator.required();
        assertFalse(validator.isValid(testEmptyMap));
        assertFalse(validator.isValid(null));
    }
}
