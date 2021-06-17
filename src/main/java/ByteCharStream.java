import java.io.*;

public class ByteCharStream {
    public static void main(String[] args) throws IOException {

        /* Schreiben Sie ein Program welches Kopien von Dateien in einem Verzeichnis erstellt.
         * Das Quell-Verzeichnis soll als Konsolenargument uebergeben und auf Korrektheit ueberprueft werden.
         * Korrekt bedeutet, dass das Verzeichnis existiert und ausser zwei Dateien mit den Namen rmz450.jpg
         * und rmz450-spec.txt nichts weiter enthaelt.
         * Jede Datei soll zweimal kopiert werden, einmal zeichen-orientiert und einmal byte-orientiert.
         * Dazu soll die jeweilige Datei geoeffnet und Element fuer Element gelesen und ebenso wieder geschrieben werden.
         * Die Kopien sollen so benannt werden, damit aus dem Dateinamen hervorgeht, mit welcher Methode sie erstellt wurde.
         * Oeffnen Sie die Kopien anschliessend mit einem entsprechenden Programm und erklaeren Sie die entsprechenden Effekte.
         * Oeffnen Sie die Kopien anschliessend mit einem HEX-Editor und erklaeren Sie die Gruende fuer die Effekte.
         */

        /* Teilaufgabe a - Verzeichnisstruktur */
        String sourceDirPath = args.length >= 1 ? args[0] : "./files";
        File soureDir = new File(sourceDirPath);
        if (soureDir.isDirectory()) {
            System.out.println("Directory " + sourceDirPath + " exists");
        } else {
            System.out.println("Directory " + sourceDirPath + " does not exist. Terminating program.");
            return;
        }

        boolean rmz450jpgExists = false;
        boolean rmz450spectxtExists = false;

        File[] files = soureDir.listFiles(); // get all items within the directory
        for (File file : files) {
            if (file.isDirectory()) {
                // Content within directory must not be another directory
                System.out.println("Directory within " + sourceDirPath + " detected. Terminating program.");
            } else {
                switch (file.getName()) {
                    case "rmz450.jpg":
                        rmz450jpgExists = true;
                        break;
                    case "rmz450-spec.txt":
                        rmz450spectxtExists = true;
                        break;
                    default:
                        System.out.println("Directory " + sourceDirPath + " contains invalid element. Terminating program.");
                        return;
                }
            }
        }
        if (rmz450jpgExists && rmz450spectxtExists) {
            System.out.println("Directory structure test passed successfully");
        } else {
            System.out.println("Directory " + sourceDirPath + " does not comply with predefined structure. Terminating program.");
            return;
        }

        /* Teilaufgabe b - Kopieren von Dateien */
        System.out.println("Initiating file copies.");
        for (File file : files) {
            try (FileInputStream fis = new FileInputStream(file);
                 FileOutputStream fos = new FileOutputStream("copy-bin-" + file.getName());
                 FileReader frin = new FileReader(file);
                 FileWriter frout = new FileWriter("copy-char-" + file.getName()))
            {
                int b;
                System.out.println("Binary copy.");
                while ((b = fis.read()) != -1) {
                    fos.write(b);
                }
                int c;
                System.out.println("Character-oriented copy.");
                while ((c = frin.read()) != -1) {
                    frout.write(c);
                }
            } catch (IOException ioe) {
                System.err.println("IOException. Terminating program.");
            } finally {
                System.out.println("Closing program correctly.");
            }
        }
    }
}
