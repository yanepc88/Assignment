package com.rabobank.assignment.crosscutting;

import com.rabobank.assignment.repository.entity.*;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.nio.charset.*;
import java.time.*;
import java.util.*;

public class Util {
    private static final String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

    public static List<User> csvToUsers(InputStream inputStream) {
        var users = new ArrayList<User>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.builder().setHeader().setDelimiter(",").build())) {


            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                User tutorial = new User(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("First Name"),
                        csvRecord.get("Last Name"),
                        Integer.parseInt(csvRecord.get("Issue Count")),
                        csvRecord.get("Date of Birth"));

                users.add(tutorial);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
