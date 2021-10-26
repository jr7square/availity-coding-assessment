package csv.parser;

import csv.parser.exceptions.InvalidCsvException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVProcessorTest {

    CSVProcessor testObj;

    @BeforeEach
    void setup() {
        testObj = new CSVProcessor();
    }

    @Test
    void happyPath() throws InvalidCsvException {
        var expectedData = Map.ofEntries(
                entry("Aetna", List.of("testId1,John,Doe,1,Aetna", "testId5,Mark,Sanchez,2,Aetna","testId2,Bill,Smith,1,Aetna")),
                entry("Molina", List.of("testId4,Jose,Hernandez,1,Molina","testId3,David,West,3,Molina"))
        );

        var actualData = testObj.process("valid_data.csv");
        expectedData.keySet().forEach(key -> {
            var expectedList = expectedData.get(key);
            var actualList = actualData.getOrDefault(key, Collections.emptyList());
            assertEquals(expectedList, actualList);
        });
    }

    @Test
    void invalidFile() {
        Exception e = assertThrows(InvalidCsvException.class, () -> testObj.process("invalid_data.csv"));
        String expectedMessage = "The provided file is not a csv file or is not formatted correctly";
        assertEquals(e.getMessage(), expectedMessage);
    }
}