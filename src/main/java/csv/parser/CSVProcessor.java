package csv.parser;

import csv.parser.exceptions.InvalidCsvException;
import csv.parser.validator.CSVValidator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CSVProcessor {

    public Map<String, List<String>> process(String csvFilePath) throws InvalidCsvException {
        return process(csvFilePath, true);
    }

    public Map<String, List<String>> process(String csvFilePath, boolean withHeaders) throws InvalidCsvException {
            List<String> rows = readFile(csvFilePath);
            boolean validCsv = CSVValidator.validateCsv(rows, withHeaders);
            List<String> dataRows = withHeaders ? rows.subList(1, rows.size()) : rows;
            if(validCsv) {
                var userData = parseDataByInsuranceCompany(dataRows)
                        .entrySet().stream()
                        .map(this::prepareDataForSaving)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                userData.entrySet().forEach(entry -> writeToFile(entry.getKey(), entry.getValue()));
                return userData;
            }
            else {
                throw new InvalidCsvException("The provided file is not a csv file or is not formatted correctly");
            }
    }

    private List<String> readFile(String filePath) {
        try {
            var path = Paths.get(getClass().getClassLoader().getResource(filePath).toURI());
            Stream<String> lines = Files.lines(path);
            return lines.collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            System.out.println("File not found or cannot be loaded from path provided.");
            return Collections.emptyList();
        }
    }

    private Map<String, List<UserData>>  parseDataByInsuranceCompany(List<String> dataRows) {
        return dataRows.stream()
                .map(UserData::mapRowToUserData)
                .collect(Collectors.groupingBy(UserData::getInsuranceCompany));
    }

    private void writeToFile(String insuranceCompany, List<String> dataRows) {
        try {
            var fileWriter = new FileWriter(String.format("%s-output.csv", insuranceCompany));
            var printWriter = new PrintWriter(fileWriter);
            var headers = "User Id,First Name,Last Name,Version,Insurance Company";
            printWriter.println(headers);
            dataRows.forEach(printWriter::println);
            printWriter.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private Map.Entry<String, List<String>> prepareDataForSaving(Map.Entry<String, List<UserData>> userRows) {
        var existingIds = new HashSet<String>();
       var prepData = userRows.getValue().stream().sorted(Comparator.comparing(UserData::getLastName)
                .thenComparing(UserData::getFirstName)
       .thenComparing(UserData::getVersion, Comparator.reverseOrder()))
                .filter(userData1 -> existingIds.add(userData1.getUserId()))
                .map(UserData::toString).collect(Collectors.toList());
       return Map.entry(userRows.getKey(), prepData);
    }
}
