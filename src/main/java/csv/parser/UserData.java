package csv.parser;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserData {


    String userId;
    String firstName;
    String lastName;
    String version;
    String insuranceCompany;

    public static UserData mapRowToUserData(String row) {
        String[] rowData = row.split(",");
        return UserData.builder()
                .userId(rowData[0].trim())
                .firstName(rowData[1].trim())
                .lastName(rowData[2].trim())
                .version(rowData[3].trim())
                .insuranceCompany(rowData[4].trim())
                .build();
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s", userId, firstName, lastName, version, insuranceCompany);
    }

}
