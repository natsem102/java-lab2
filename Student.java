import java.util.Arrays;

public class Student {
    private String name;
    private int[] grades;

    public Student(String name, int... grades) {
        this.name = name;
        this.grades = grades;
    }

    public int[] getGrades() { return grades; }
    public void setGrades(int[] grades) { this.grades = grades; }

    public double getAverage() {
        if (grades.length == 0) return 0;
        double sum = 0;
        for (int g : grades) sum += g;
        return sum / grades.length;
    }

    public boolean isExcellent() {
        if (grades.length == 0) return false;
        for (int g : grades) {
            if (g != 5) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + ": " + Arrays.toString(grades);
    }
}
