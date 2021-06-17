import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
}
