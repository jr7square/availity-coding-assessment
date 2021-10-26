package csv.parser.exceptions;

public class InvalidCsvException extends Exception {

    public InvalidCsvException(String errorMessage) {
        super(errorMessage);
    }
}
