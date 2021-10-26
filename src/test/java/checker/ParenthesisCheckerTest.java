package checker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParenthesisCheckerTest {

    ParenthesisChecker testObj;

    @BeforeEach
    void setup() {
        testObj = new ParenthesisChecker();
    }

    @Test
    void happyPath() {
        var hello = "(/ 10 (- 5 3))";
        assertTrue(testObj.validate(hello));
    }

    @Test
    void invalidParenthesisRight() {
        var invalidStringInput = "(+ (* 5 6) 9) )";
        assertFalse(testObj.validate(invalidStringInput));
    }

    @Test
    void invalidParenthesisLeft() {
        var invalidStringInput = "(+ ((* 5 6) 9)";
        assertFalse(testObj.validate(invalidStringInput));
    }

    @Test
    void multipleLeft() {
        var invalidStringInput = "((((+ ((* 5 6) 9)";
        assertFalse(testObj.validate(invalidStringInput));
    }

    @Test
    void noParenthesis() {
        var noParenthesis = "+ 9 5";
        assertTrue(testObj.validate(noParenthesis));
    }
}