import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GradingTest {

    @Test
    void testDeterminismUsingSeed() {

        Random random = new Random(11111L);
        List<Integer> first = Grading.gradeGenerator(random);

        random = new Random(11111L); // need to reinitialize since it keeps state
        List<Integer> second = Grading.gradeGenerator(random);

        assertEquals(1000, first.size());
        assertEquals(1000, second.size());
        assertEquals(first, second);
    }

    @Test
    void testForGradeNormalization() {

        List<Integer> notNormalized = List.of(101, 102, Integer.MAX_VALUE, Integer.MIN_VALUE, -1, -2, 100, 0, 99, 1);
        List<Integer> normalized = List.of(100, 100, 100, 0, 0, 0, 100, 0, 99, 1);

        assertEquals(normalized, Grading.normalizeGrades(notNormalized));
    }

    @Test
    void testForCorrectLetterGrade() {

        List<Integer> sampleGrades = List.of(0, 59, 60, 69, 70, 79, 80, 89, 90, 100);
        List<Character> expectedLetterGrades = List.of('F', 'F', 'D', 'D', 'C', 'C', 'B', 'B', 'A', 'A');

        List<Character> actual = sampleGrades.stream().map(Grading::getLetterGrade).toList();

        assertEquals(expectedLetterGrades, actual);
    }

    @Test
    void testDisplayStatistics() {

        // we need to redirect the console printstream and capture the output
        PrintStream originalPrintStream = System.out; // capture the original so we can set it back

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream, true, StandardCharsets.UTF_8));

        List<Integer> sampleGrades = List.of(0, 59, 60, 69, 70, 79, 80, 89, 90, 100);

        // capture the default line separator, we will need it for perfect formatting of expected
        String lineSeparator = System.lineSeparator();
        String expectedOutput = "Minimum grade: 0" + lineSeparator +
                "Maximum grade: 100" + lineSeparator +
                "Perfect score count: 1" + lineSeparator +
                "Average grade: 69.6" + lineSeparator;

        Grading.displayStatistics(sampleGrades); // trigger

        assertEquals(expectedOutput, outputStream.toString());

        System.setOut(originalPrintStream); // make sure to set the output back to console.
    }

    @Test
    void testDisplayGradeCounts() {

        // we need to redirect the console printstream and capture the output
        PrintStream originalPrintStream = System.out; // capture the original so we can set it back

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream, true, StandardCharsets.UTF_8));

        List<Integer> sampleGrades = List.of(0, 59, 60, 69, 70, 79, 80, 89, 90, 100);

        // capture the default line separator, we will need it for perfect formatting of expected
        String lineSeparator = System.lineSeparator();
        String expectedOutput = "Letter grade A: 2 students" + lineSeparator +
                "Letter grade B: 2 students" + lineSeparator +
                "Letter grade C: 2 students" + lineSeparator +
                "Letter grade D: 2 students" + lineSeparator +
                "Letter grade F: 2 students" + lineSeparator;

        Grading.displayGradeCounts(sampleGrades); // trigger

        assertEquals(expectedOutput, outputStream.toString());

        System.setOut(originalPrintStream); // make sure to set the output back to console.
    }
}