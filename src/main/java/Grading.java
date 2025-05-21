import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grading {

    static Random random = new Random();

    public static List<Integer> gradeGenerator() {

        // use provided expression to generate 1000 grades
        return Stream.generate(() -> (int) (100.0d - Math.exp(random.nextGaussian() * 0.25 + 3.5) + 20))
                .limit(1000).toList(); // TODO: needs to be immutable
    }

    private static List<Integer> normalizeGrades(List<Integer> grades) {

        // normalize grades below 0 or above 100 to 0 or 100
        return grades.stream().map(g -> {
            if (g > 100) g = 100;
            if (g < 0) g = 0;
            return g;
        }).toList();
    }

    private static void displayStatistics(List<Integer> clippedGrades) {

        // use Integer comparator to determine min and max and print to console
        clippedGrades.stream().min(Integer::compare).ifPresent(min -> System.out.println("Minimum grade: " + min));
        clippedGrades.stream().max(Integer::compare).ifPresent(max -> System.out.println("Maximum grade: " + max));

        // use Collectors built in method to calculate the average grade as a double
        double avg = clippedGrades.stream().collect(Collectors.averagingInt(g -> g));
        System.out.println("Average grade: " + avg);
    }

    public static void main(String[] args) {

        // generate grades
        List<Integer> grades = gradeGenerator();
        System.out.println("grades = " + grades);

        // clip grades to 0-100
        List<Integer> clippedGrades = normalizeGrades(grades);
        System.out.println("clippedGrades = " + clippedGrades);

        // display data
        // stats
        displayStatistics(clippedGrades);

        // letter grade count
        // perfect count

    }
}
