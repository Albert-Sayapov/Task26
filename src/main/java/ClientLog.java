import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


public class ClientLog {
    protected String log = "ProductNumber,productAmount\n";

    public void log(int productNumber, int productAmount) {
        log += productNumber + "," + productAmount + "\n";
    }

    public void exportAsCSV(File csvFile) {
        try (Writer writer = new FileWriter(csvFile)) {
            writer.write(log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
