package folder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class CSVDumper {


    private String filePath;
    private String[] headers;

    public CSVDumper(String filePath, String[] headers) {
        this.filePath = filePath;
        this.headers = headers;
        createCSVFile();
    }

    private void createCSVFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write the headers to the file
            for (int i = 0; i < headers.length; i++) {
                writer.append(headers[i]);
                if (i != headers.length - 1) {
                    writer.append(';');
                }
            }
            writer.append('\n');
            System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToCSVFile(String[] data) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            // Write the data to the file
            for (int i = 0; i < data.length; i++) {
                writer.append(data[i]);
                if (i != data.length - 1) {
                    writer.append(';');
                }
            }
            writer.append('\n');
            System.out.println("Data written to CSV file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCSVFile() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                System.out.println("File already exists.");
                return;
            }
            if (file.createNewFile()) {
                System.out.println("CSV file saved successfully.");
            } else {
                System.out.println("Failed to save the CSV file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
