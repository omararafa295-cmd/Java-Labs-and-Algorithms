
package task_data_structure;

public class Task_Data_structure {

    public static void main(String[] args) {
     LinearArrayStudents studentsArray = new LinearArrayStudents();
        Student student1 = new Student("Omar", "Arafa", 1);
        Student student2 = new Student("Ali", "Mohamed", 2);
        Student student3 = new Student("Mariam", "Youssef", 3);

        studentsArray.insertLast(student1);
        studentsArray.insertLast(student2);
        studentsArray.insertFirst(student3);
         System.out.println(studentsArray); 
        System.out.println("Index of Omar Arafa is: " + studentsArray.linearSearch(1)); 
        studentsArray.delete(1); 
        System.out.println(studentsArray);

    }
    
}