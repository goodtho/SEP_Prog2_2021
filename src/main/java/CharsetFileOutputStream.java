import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CharsetFileOutputStream {


        public static void main(String[] args) {


            /* Teilaufgabe a
             * In der Vorlesung haben Sie gelernt, dass Java-Klassen fuer Unicode entworfen wurden.
             * Nun ist Unicode aber nicht der einzige Zeichensatz und Java unterstuetz durchaus Alternativen.
             * Welche Zeichensaetze auf einem System konkret unterstuetzt werden haengt von der Konfiguration des Betriebssystems JVM ab.
             * Schreiben Sie ein Programm, welches alle Unterstuetzten Zeichensaetze auf der Konsole (System.out) ausgibt,
             * zusammen mit dem Standardzeichensatz.
             * https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html
             */

            // ToDo: Print default character set
            System.out.println("Default Charset = " + Charset.defaultCharset());

            // Todo: Print all available character sets
            System.out.println("Available Charsets:");
            for (Charset charset : Charset.availableCharsets().values()) {
                System.out.println("- " + charset);
            }
            /* Ende Teilaufgabe a */


            /* Teilaufgabe b
             * Schreiben Sie ein Program welches im Standardzeichensatz einzele Zeichen (also Zeichen fuer Zeichen) von der
             * Konsole einliest und ebenso im Zeichensatz US_ASCII in eine Datei schreibt.
             * Die Eingabe des Zeichens 'q' soll das Program ordentlich beenden.
             * Die Datei soll "CharSetEvaluation.txt" genannt werden und wird entweder erzeugt oder wenn Sie bereits
             * existiert, einfach geoeffnet und der Inhalt uebeschrieben werden.
             * Lesen von der Konsole und Schreiben in die Datei soll leistungsoptimiert geschehen, also vom jeweiligen
             * Input-/Output-Medium entkoppelt.
             * Testen Sie Ihr Program mit den folgenden Eingabereihenfolge und Zeichen: a b c d € f g q
             * Oeffnen Sie die Textdatei nach Durchfuehrung des Programs mit einem Texteditor und erklaeren Sie das Ergebnis.
             * Oeffnen Sie die Datei anschliessend mit einem HEX-Editor und vergleichen Sie.
             */

            char c;
            try (FileOutputStream fosDefault = new FileOutputStream("CharSetEvaluation_Default.txt");
                 FileOutputStream fosAscii = new FileOutputStream("CharSetEvaluation_ASCII.txt");
                 BufferedWriter bwdefault = new BufferedWriter(new OutputStreamWriter(fosDefault));
                 BufferedWriter bwascii = new BufferedWriter(new OutputStreamWriter(fosAscii, StandardCharsets.US_ASCII));
                 BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
            {
                System.out.println("Enter characters, 'q' to quit."); // read characters
                boolean reading = true;
                while (reading) {
                    c = (char) br.read();
                /* returns character read, as an integer (32bit) in the range 0 to 65535
 		         (0x00-0xffff, 16bit), or -1 (0xffff-ffff) if the end of the stream has been reached */
                    if (c == '\n') {
                        continue;
                    }
                    if (c == 'q') {
                        System.out.println("The End");
                        reading = false;
                    } else {
                        System.out.println("== Output using Default Encoding");
                        bwdefault.write(c);
                        bwdefault.write("\n");
                        bwdefault.flush();
                        System.out.println("== Output using ASCII Encoding");
                        bwascii.write(c);
                        bwascii.write("\n");
                        bwascii.flush();
                    }
                    //int dummy = br.read(); //clear CRNL
                }
            } catch (IOException e) {
                System.err.println("Abbruch wegen IO-Exception");
                e.printStackTrace();
            }
        }

}
