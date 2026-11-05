import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    public static void main(String[] args) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
        System.out.println("Лабораторная работа 2. Вариант 9");

        // Задание 1.1 (точка координат)
        System.out.println("\n Задание 1.1 Точка координат");
        System.out.println("Введите координаты трёх точек (точки должны быть разными)");
        Point[] points = new Point[3];
        for (int i = 0; i < 3; i++) {
            while (true) {
                System.out.println("Точка " + (i + 1) + ":");
                Point p = new Point(readDouble("  X: "), readDouble("  Y: "));
                boolean same = false;
                for (int j = 0; j < i; j++) {
                    if (points[j].getX() == p.getX() && points[j].getY() == p.getY()) same = true;
                }
                if (!same) {
                    points[i] = p;
                    break;
                }
                System.out.println("  Ошибка! Такая точка уже есть");
            }
        }
        for (Point p : points) System.out.println(p);

        // Задание 2.1 (прямая)
        System.out.println("\nЗадание 2.1 Прямая");
        System.out.println("Линия 1 (по заданию от {1;3} до {23;8}):");
        Line line1 = new Line(readPoint("  Начало:"), readPoint("  Конец:"));
        System.out.println("Линия 2, горизонтальная (по заданию высота 10, от 5 до 25):");
        double h = readDouble("  Высота (Y): ");
        double xFrom = readDouble("  X начала: ");
        double xTo = readDouble("  X конца: ");
        Line line2 = new Line(new Point(xFrom, h), new Point(xTo, h));
        Line line3 = new Line(line1.getStart(), line2.getEnd());
        printLines(line1, line2, line3);

        System.out.println("Изменяем начало линии 1 и конец линии 2 (линия 3 должна измениться):");
        Point p4 = readPoint("  Новое начало линии 1:");
        line1.getStart().setX(p4.getX());
        line1.getStart().setY(p4.getY());
        p4 = readPoint("  Новый конец линии 2:");
        line2.getEnd().setX(p4.getX());
        line2.getEnd().setY(p4.getY());
        printLines(line1, line2, line3);

        System.out.println("Изменяем начало линии 1 так, чтобы линия 3 не изменилась:");
        line1.setStart(readPoint("  Новое начало линии 1:"));
        printLines(line1, line2, line3);

        // Задание 3.1 (Студент)
        System.out.println("\n Задание 3.1 Студент ");
        int[] g = readGrades("Оценки Васи через пробел (по заданию 3 4 5): ", false);
        Student vasya = new Student("Вася", g);
        Student petya = new Student("Петя");
        petya.setGrades(vasya.getGrades());       
        petya.getGrades()[0] = 5;
        System.out.println(vasya);
        System.out.println(petya);
        Student andrey = new Student("Андрей", vasya.getGrades().clone());   
        vasya.getGrades()[0] = readInt("Новая первая оценка Васи (2-5): ", 2, 5);
        System.out.println(vasya);
        System.out.println(andrey + "  (не изменился)");

        // Задание 4.1 (создаем точку )
        System.out.println("\n Задание 4.1 Создаем Точку ");
        System.out.println("Введите координаты точек (по заданию 3;5, 25;6, 7;8):");
        for (int i = 1; i <= 3; i++) {
            Point p = readPoint("Точка " + i + ":");
            System.out.println(p);
        }

        // Задание 4.7 (создаем студента)
        System.out.println("\n Задание 4.7 Создаем Студента");
        Student s1 = new Student("Вася", readGrades("Оценки Васи через пробел (по заданию 3 4 5): ", false));
        Student s2 = new Student("Максим");
        System.out.println(s1);
        System.out.println(s2);

        // Задание 5.6 (студент отличник)
        System.out.println("\n Задание 5.6 Студент отличник ");
        Student[] st = {
            new Student("Вася", readGrades("Оценки Васи (по заданию 3 4 5 4): ", false)),
            new Student("Петя", readGrades("Оценки Пети (по заданию 5 5 5 5): ", false))
        };
        for (Student s : st) {
            System.out.println(s);
            System.out.println(String.format(Locale.ROOT, "  Средний балл: %.2f", s.getAverage()));
            System.out.println("  Отличник: " + (s.isExcellent() ? "да" : "нет"));
        }
    }

    private static void printLines(Line l1, Line l2, Line l3) {
        System.out.println("Линия 1: " + l1);
        System.out.println("Линия 2: " + l2);
        System.out.println("Линия 3: " + l3);
    }

    // ввод с проверкой 

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                double v = Double.parseDouble(readLine(prompt).replace(',', '.'));
                if (Double.isFinite(v)) return v;
            } catch (NumberFormatException ignored) {
            }
            System.out.println("  Ошибка: введите число.");
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                int v = Integer.parseInt(readLine(prompt));
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException ignored) {
            }
            System.out.println("  Ошибка: введите целое число от " + min + " до " + max + ".");
        }
    }

    private static Point readPoint(String label) {
        System.out.println(label);
        return new Point(readDouble("    X: "), readDouble("    Y: "));
    }

    private static int[] readGrades(String prompt, boolean allowEmpty) {
        while (true) {
            String line = readLine(prompt);
            if (line.isEmpty() && allowEmpty) return new int[0];
            String[] parts = line.split("[\\s,;]+");
            int[] res = new int[parts.length];
            boolean ok = !line.isEmpty();
            for (int i = 0; ok && i < parts.length; i++) {
                try {
                    res[i] = Integer.parseInt(parts[i]);
                    if (res[i] < 2 || res[i] > 5) ok = false;
                } catch (NumberFormatException e) {
                    ok = false;
                }
            }
            if (ok) return res;
            System.out.println("  Ошибка! Введите оценки (целые от 2 до 5) через пробел.");
        }
    }
}
