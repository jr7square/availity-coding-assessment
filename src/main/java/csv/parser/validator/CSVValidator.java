package csv.parser.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CSVValidator {

    private static final String USER_ID = "USER ID";
    private static final String FIRST_NAME = "FIRST NAME";
    private static final String LAST_NAME = "LAST NAME";
    private static final String VERSION = "VERSION";
    private static final String INSURANCE_COMPANY = "INSURANCE COMPANY";
    private static final int NUM_COLUMNS = 5;

    public static boolean validateCsv(List<String> rows, boolean withHeaders) {
        return withHeaders ? validateWithHeaders(rows) : validate(rows);
    }

    private static boolean validateWithHeaders(List<String> rows) {
        if(rows.size() >= 2) {
            return validate(rows.subList(1, rows.size())) && validateHeaders(rows.get(0));
        }
        return rows.isEmpty() || validateHeaders(rows.get(0));
    }

    private static boolean validateHeaders(String headers) {
        var h = Arrays.stream(headers.split(","))
                .map(String::trim).map(String::toUpperCase)
                .collect(Collectors.toList());
        if(h.size() == NUM_COLUMNS) {
            return h.get(0).equals(USER_ID) &&
                    h.get(1).equals(FIRST_NAME) &&
                    h.get(2).equals(LAST_NAME) &&
                    h.get(3).equals(VERSION) &&
                    h.get(4).equals(INSURANCE_COMPANY);
        }
        return false;
    }

    private static boolean validate(List<String> rows) {
        return rows.stream()
                .noneMatch(validateRow.negate());
    }

    private static final Predicate<String> validateRow = row -> {
        String[] r = row.split(",");
        if(r.length == NUM_COLUMNS) {
            return StringUtils.isNotBlank(r[0]) && StringUtils.isNotBlank(r[1]) &&
                    StringUtils.isNotBlank(r[2]) && StringUtils.isNumeric(r[3]) && StringUtils.isNotBlank(r[4]);
        }
        return false;
    };
}
