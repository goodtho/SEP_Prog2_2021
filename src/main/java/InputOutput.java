import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class InputOutput {

    protected static final String DELIMITER = ";";
    protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    protected static final Charset CHARSET = StandardCharsets.UTF_8;

    private final DateFormat df = new SimpleDateFormat(DATE_FORMAT);

    private final Path actualFile;
    private final Path tempFile;
    private static final Logger log = Logger.getLogger(InputOutput.class.getCanonicalName());

    //Creates 2 files: temp and actualFile. tempfile is to write and actualFile is to read.
    public InputOutput(String filepath) throws IOException {
        try {
            this.actualFile = Path.of(filepath).normalize();
            if (!Files.exists(actualFile)) {
                Files.createFile(actualFile);
                log.log(Level.INFO,"Created file.", actualFile);
            } else {
                log.log(Level.FINE, "File already exists.", actualFile);
            }
            this.tempFile = Files.createTempFile("temp-", null);
        } catch (IOException e) {
            throw new IOException("Error: File not generated.", e);
        }
    }

/*
    public void insert(Picture picture) {
        try(BufferedReader in = Files.newBufferedReader(actualFile, CHARSET);
            BufferedWriter out = Files.newBufferedWriter(tempFile, CHARSET)) {
            String row = ".";
            int maxId = 0;

            while ((row = in.readLine()) != null && !row.isBlank())    {
                int id = getId(row);
                maxId = Math.max(id, maxId);
                out.write(row);
                out.newLine();
            }
            picture.setId(maxId + 1);
            out.write(createRecord(picture));
            out.newLine();

            overwriteActualFile();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }
*/
    private void overwriteActualFile() throws IOException {
        try{
            Files.move(tempFile, actualFile, REPLACE_EXISTING);
        }
        catch(IOException e){
            log.log(Level.WARNING, "Error: Could not overwrite", e);
        }
    }
    // Read by Line and creating temp file to replace original
    private static void ReadByLine(File originFile, String prefix) throws IOException {
        // Verify arguments, prepare filtered dir and output file object [3P]
        if(prefix == null || "".equals(prefix)) return;
        if(originFile.exists() && !originFile.isDirectory()) return;
        File tempFile = new File(originFile.getAbsolutePath() + ".tmp");

        // Open files, copy content line by line filtering those beginning with the prefix [6P]
        try (BufferedReader reader = new BufferedReader(new FileReader(originFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String lineRead = null;
            while ((lineRead = reader.readLine()) != null) {
                if (lineRead.startsWith(prefix)) continue;

                writer.write(lineRead);
                writer.newLine();
            }

            if (originFile.delete()) {
                System.out.println("Could not delete file");
                if (!tempFile.renameTo(originFile)) {
                    System.out.println("Could not rename file");
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
