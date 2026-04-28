
package gradeanalyzer;
import java.util.*;

class Student implements Comparable<Student> {
    private String name;
    private int grade;
    
    public Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }
    
    public String getName() { return name; }
    public int getGrade() { return grade; }
    
    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.grade, other.grade);
    }
    
    @Override
    public String toString() {
        return name + ": " + grade;
    }
}

class QuickSort {
    public static void sort(List<Student> students, boolean byName) {
        if (students.size() <= 1) return;
        quickSort(students, 0, students.size()-1, byName);
    }
    
    private static void quickSort(List<Student> students, int low, int high, boolean byName) {
        if (low < high) {
            int pi = partition(students, low, high, byName);
            quickSort(students, low, pi-1, byName);
            quickSort(students, pi+1, high, byName);
        }
    }
    
    private static int partition(List<Student> students, int low, int high, boolean byName) {
        Student pivot = students.get(high);
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            boolean condition = byName ? 
                students.get(j).getName().compareTo(pivot.getName()) < 0 :
                students.get(j).getGrade() < pivot.getGrade();
            
            if (condition) {
                i++;
                Collections.swap(students, i, j);
            }
        }
        Collections.swap(students, i+1, high);
        return i+1;
    }
}

class MergeSort {
    public static void sort(List<Student> students, boolean byName) {
        mergeSort(students, 0, students.size()-1, byName);
    }
    
    private static void mergeSort(List<Student> students, int l, int r, boolean byName) {
        if (l < r) {
            int m = l + (r - l)/2;
            mergeSort(students, l, m, byName);
            mergeSort(students, m+1, r, byName);
            merge(students, l, m, r, byName);
        }
    }
    
    private static void merge(List<Student> students, int l, int m, int r, boolean byName) {
        List<Student> temp = new ArrayList<>();
        int i = l, j = m+1;
        
        while (i <= m && j <= r) {
            boolean condition = byName ?
                students.get(i).getName().compareTo(students.get(j).getName()) < 0 :
                students.get(i).getGrade() < students.get(j).getGrade();
            
            if (condition) temp.add(students.get(i++));
            else temp.add(students.get(j++));
        }
        
        while (i <= m) temp.add(students.get(i++));
        while (j <= r) temp.add(students.get(j++));
        
        for (i = l; i <= r; i++) {
            students.set(i, temp.get(i - l));
        }
    }
}

class RadixSort {
    public static void sort(List<Student> students) {
        int max = students.stream().mapToInt(Student::getGrade).max().orElse(0);
        
        for (int exp = 1; max/exp > 0; exp *= 10) {
            countSort(students, exp);
        }
    }
    
    private static void countSort(List<Student> students, int exp) {
        Student[] output = new Student[students.size()];
        int[] count = new int[10];
        Arrays.fill(count, 0);
        
        for (Student s : students) {
            count[(s.getGrade()/exp)%10]++;
        }
        
        for (int i = 1; i < 10; i++) {
            count[i] += count[i-1];
        }
        
        for (int i = students.size()-1; i >= 0; i--) {
            int index = (students.get(i).getGrade()/exp)%10;
            output[count[index]-1] = students.get(i);
            count[index]--;
        }
        
        for (int i = 0; i < students.size(); i++) {
            students.set(i, output[i]);
        }
    }
}

class StatisticsCalculator {
    public static double calculateMean(List<Student> students) {
        return students.stream()
            .mapToInt(Student::getGrade)
            .average()
            .orElse(0.0);
    }
    
    public static double calculateMedian(List<Student> students) {
        List<Student> sorted = new ArrayList<>(students);
        MergeSort.sort(sorted, false);
        
        int size = sorted.size();
        if (size % 2 == 0) {
            return (sorted.get(size/2 - 1).getGrade() + sorted.get(size/2).getGrade()) / 2.0;
        } else {
            return sorted.get(size/2).getGrade();
        }
    }
    
    public static List<Integer> calculateMode(List<Student> students) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (Student s : students) {
            int grade = s.getGrade();
            frequency.put(grade, frequency.getOrDefault(grade, 0) + 1);
        }
        
        int maxFreq = Collections.max(frequency.values());
        List<Integer> modes = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() == maxFreq) {
                modes.add(entry.getKey());
            }
        }
        return modes;
    }
}


  

public class GradeAnalyzer {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Ali", 85),
            new Student("Sara", 92),
            new Student("Mohamed", 76),
            new Student("Mahmoud", 85),
            new Student("Kamel", 56),
            new Student("Omar", 92)
        );
        
        System.out.println("Original List: " + students);
        
        List<Student> quickSorted = new ArrayList<>(students);
        QuickSort.sort(quickSorted, false);
        System.out.println("\nQuickSort by Grade: " + quickSorted);
        
        List<Student> mergeSorted = new ArrayList<>(students);
        MergeSort.sort(mergeSorted, true);
        System.out.println("\nMergeSort by Name: " + mergeSorted);
        
        List<Student> radixSorted = new ArrayList<>(students);
        RadixSort.sort(radixSorted);
        System.out.println("\nRadixSort by Grade: " + radixSorted);
        
        System.out.println("\nStatistics:");
        System.out.printf("Mean: %.2f%n", StatisticsCalculator.calculateMean(students));
        System.out.println("Median: " + StatisticsCalculator.calculateMedian(students));
        System.out.println("Mode: " + StatisticsCalculator.calculateMode(students));
        
       
    }
}