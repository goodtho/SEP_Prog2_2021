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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class InputOutput {

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

    //Read by Char
    private static List<Character> readByChar(File file) throws IOException {
        BufferedReader buffReader = new BufferedReader(new FileReader(file));
        List<Character> result = new ArrayList<>();

        while (buffReader.ready()) {
            // Read and print characters one by one
            // by converting into character
            result.add((char)buffReader.read()); //read == -1 - 0xFFFF -> -1 = eof
        }
        return null;
    }
}
