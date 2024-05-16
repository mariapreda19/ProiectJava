package CSVFileWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CSVFileWriter {
    private static final String FILE_PATH = "database_actions.csv";
    private static final String CSV_SEPARATOR = ",";

    public static void writeDatabaseAction(String actionName) {
        LocalDateTime timestamp = LocalDateTime.now();
        try (FileWriter writer = new FileWriter("CSVFileWriter/AuditFile.txt", true)) {
            StringBuilder sb = new StringBuilder();
            sb.append(actionName);
            sb.append(CSV_SEPARATOR);
            sb.append(timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            sb.append("\n");
            writer.append(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
