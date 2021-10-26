package csv.parser.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVValidatorTest {


    @Test
    void happyPath() {
        var testData = List.of("User Id,First Name,Last Name,Version,Insurance Company",
                "testId1,John,Doe,1,Aetna",
                "testId2,Bill,Smith,1,Aetna");
        assertTrue(CSVValidator.validateCsv(testData, true));
    }

    @Test
    void happyPathWithNoHeaders() {
        var testData = List.of("testId1,John,Doe,1,Aetna",
                "testId2,Bill,Smith,1,Aetna",
                "testId3,David,Jones,1,Aetna");
        assertTrue(CSVValidator.validateCsv(testData, false));
    }

    @Test
    void invalidHeadersFormat() {
        var testData = List.of("id,last-name,version,Company",
                "testId1,John,Doe,1,Aetna",
                "testId2,Bill,Smith,1,Aetna");
        assertFalse(CSVValidator.validateCsv(testData, true));
    }

    @Test
    void invalidUserData() {
        var testData = List.of("User Id,First Name,Last Name,Version,Insurance Company",
                "John,Doe,1,Aetna",
                "testId2,Bill,Smith,one,Aetna");
        assertFalse(CSVValidator.validateCsv(testData, true));
    }

    @Test
    void emptyRows() {
        var testData = List.of("User Id,First Name,Last Name,Version,Insurance Company");
        assertTrue(CSVValidator.validateCsv(testData, true));
    }

}