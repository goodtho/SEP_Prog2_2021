import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ZeugnisTest {
    @Test
    public void calculateAverageTest() {
        // Erstellen eine Instanz der Klasse die Sie testen wollen. [0.5P]
        String studentID = "studentID";
        Student student = new Student(studentID);

        // Erstellen Sie ein Mock-Objekt [0.5 P]
        ZeugnisSystem zeugnisSystem =  Mockito.mock(ZeugnisSystem.class);

        // Spezifizieren Sie das Mock-Objekt so, dass die Notenliste die Werte [4.0, 5.0] enthält [1 P]
        Mockito.when(zeugnisSystem.getGrades(student.id)).thenReturn(new ArrayList<>() {{
            add(4.0);
            add(5.0);
        }});

        // Berechnen sie den Durchschnitts Note mit Hilfe der Testinstanz
        double average = student.calculateAverage(zeugnisSystem);
        // und verifizieren Sie den korrekten Wert. [1P]
        assertEquals(4.5, average);

        // Verifizieren Sie das Verhalten Ihres Systems
        // Die Funktion getGrades() muss genau ein Mal aufgerufen werden worden sein. [1 P]
        verify(zeugnisSystem, Mockito.times(1)).getGrades(any(String.class));

        // Die Methode getCurrentSemester() darf nicht aufgerufen worden sein [1P]
        verify(zeugnisSystem, Mockito.times(0)).getCurrentSemester(any(String.class));

        // calculateAverage wirft eine IllegalArgumentException
        when(zeugnisSystem.getGrades(studentID)).thenThrow(new IllegalStateException());
        assertThrows(IllegalStateException.class, () -> zeugnisSystem.getGrades(studentID));
    }
}

class Student {
    String id;

    public Student (String id) { this.id = id; }

    public double calculateAverage(ZeugnisSystem system) throws IllegalStateException {
        return system.getGrades(id).stream()
                .mapToDouble(Double::doubleValue)
                .average().orElseThrow(() -> new IllegalStateException("No grades"));
    }
}

interface ZeugnisSystem {
    List<Double> getGrades(String studentId);
    int getCurrentSemester(String studentId);
}