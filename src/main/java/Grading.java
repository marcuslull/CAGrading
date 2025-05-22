import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grading {


    public static List<Integer> gradeGenerator(Random random) {

        // use provided expression to generate 1000 grades
        return Stream.generate(() -> (int) (100.0d - Math.exp(random.nextGaussian() * 0.25 + 3.5) + 20))
                .limit(1000).toList();
    }


    // default access modifiers for testing purposes
    static List<Integer> normalizeGrades(List<Integer> grades) {

        // normalize grades below 0 or above 100 to 0 or 100
        return grades.stream().map(g -> {
            if (g > 100) g = 100; // internal mutation where required is still acceptable for the functional paradigm
            if (g < 0) g = 0;
            return g;
        }).toList();
    }


    static void displayStatistics(List<Integer> clippedGrades) {

        // `summaryStatistics()` has all the info we need
        IntSummaryStatistics statistics = clippedGrades.stream().mapToInt(Integer::intValue).summaryStatistics();
        System.out.println("Minimum grade: " + statistics.getMin());
        System.out.println("Maximum grade: " + statistics.getMax());
        System.out.println("Average grade: " + statistics.getAverage());
    }


    static void displayPerfectCount(List<Integer> clippedGrades) {

        // use filter to select perfect grades
        long perfectCount = clippedGrades.stream().filter(g -> g == 100).count();
        System.out.println("Perfect score count: " + perfectCount);
    }


    static void displayGradeCounts(List<Integer> clippedGrades) {

        // partition the grades using `getLetterGrade` helper method as a classifier for the groupingBy function.
        // A treeMap is preferred over the default hashMap for ordering purposes.
        // Adding the Collectors.counting() downstream collector gives us the total count of grades in each partition.
        clippedGrades.stream()
                .collect(Collectors.groupingBy(Grading::getLetterGrade, TreeMap::new, Collectors.counting()))
                .forEach((letterGrade, count) -> System.out.printf("Letter grade %s: %d students%n", letterGrade, count));
    }


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
