import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code Grading} provides demo functionality to generate, normalize,
 * and analyze a list of student grades. The demo serves as an exercise in the
 * use of the Java Streams API.
 * The class will generate a list of 1000 grades using a lognormal distribution,
 * clip the grades to a range of 0-100, and display various statistics.
 */
public class Grading {


    /**
     * Generates 1000 lognormal grades with a mean of ~ 86.
     *
     * @param random The Random generator used to generate random numbers
     * @return A List of 1000 grades, not normalized
     */
    public static List<Integer> gradeGenerator(Random random) {

        // use provided expression to generate 1000 grades
        return Stream.generate(() -> (int) (100.0d - Math.exp(random.nextGaussian() * 0.25 + 3.5) + 20))
                .limit(1000).toList();
    }


    /**
     * Normalizes a List of grades to a range of 0-100.
     *
     * @param grades A List of grades
     * @return A List of normalized grades such that grades < 0 = 0 and grades > 100 = 100
     */
    static List<Integer> normalizeGrades(List<Integer> grades) {
        // default access modifiers for testing purposes

        // normalize grades below 0 or above 100 to 0 or 100
        return grades.stream().map(g -> {
            if (g > 100) g = 100; // internal mutation where required is still acceptable for the functional paradigm
            if (g < 0) g = 0;
            return g;
        }).toList();
    }


    /**
     * Parses a List of normalized (0-100) grades and displays minimum, maximum, and average grades
     * to the console.
     *
     * @param clippedGrades A list of grades that have been normalized to a range 0-100.
     */
    static void displayStatistics(List<Integer> clippedGrades) {

        // `summaryStatistics()` has all the info we need
        IntSummaryStatistics statistics = clippedGrades.stream().mapToInt(Integer::intValue).summaryStatistics();
        System.out.println("Minimum grade: " + statistics.getMin());
        System.out.println("Maximum grade: " + statistics.getMax());
        System.out.println("Average grade: " + statistics.getAverage());
    }


    /**
     * Parses a List of normalized (0-100) grades and displays the perfect score (100) count.
     *
     * @param clippedGrades A list of grades that have been normalized to a range 0-100.
     */
    static void displayPerfectCount(List<Integer> clippedGrades) {

        // use filter to select perfect grades
        long perfectCount = clippedGrades.stream().filter(g -> g == 100).count();
        System.out.println("Perfect score count: " + perfectCount);
    }


    /**
     * Parses a List of normalized (0-100) grades and displays the grade count by letter grade.
     * A = 90-100, B = 80-89, C = 70-79, D = 60-69, F = 0-59
     *
     * @param clippedGrades A list of grades that have been normalized to a range 0-100.
     */
    static void displayGradeCounts(List<Integer> clippedGrades) {

        // partition the grades using `getLetterGrade` helper method as a classifier for the groupingBy function.
        // A treeMap is preferred over the default hashMap for ordering purposes.
        // Adding the Collectors.counting() downstream collector gives us the total count of grades in each partition.
        clippedGrades.stream()
                .collect(Collectors.groupingBy(Grading::getLetterGrade, TreeMap::new, Collectors.counting()))
                .forEach((letterGrade, count) -> System.out.printf("Letter grade %s: %d students%n", letterGrade, count));
    }


    /**
     * Associates a normalized (0-100) percent grade and a letter grade.
     * A = 90-100, B = 80-89, C = 70-79, D = 60-69, F = 0-59
     *
     * @param grade The normalized (0-100) percent grade
     * @return the associated letter grade
     */
    static Character getLetterGrade(Integer grade) {

        // short circuit grade classification helper
        if (grade > 89) return 'A';
        if (grade > 79) return 'B';
        if (grade > 69) return 'C';
        if (grade > 59) return 'D';
        return 'F';
    }


    public static void main(String[] args) {

        System.out.println("\nGRADE GENERATOR");

        System.out.println("This demo generates 1000 random grades of lognormal distribution " +
                "and then displays basic statistics of the grade distribution.\n");

        System.out.println("------");

        Random random = new Random(); // not inherently functional as it will maintain state

        List<Integer> grades = gradeGenerator(random);
        List<Integer> clippedGrades = normalizeGrades(grades);
        displayStatistics(clippedGrades);
        displayPerfectCount(clippedGrades);
        displayGradeCounts(clippedGrades);

        System.out.println("------\n");
    }
}
