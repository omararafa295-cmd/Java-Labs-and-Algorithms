
package task_data_structure;


public class LinearArrayStudents {
    Student [] arr;
    int size;
    

    public LinearArrayStudents() {
       size = 0;
        arr = new Student[10];
        for(int i=0;i<arr.length;i++){
        arr[i]=new Student();
        
        }
    } 
    
   
    public void insertFirst(Student student) {
      
        for (int i = size; i > 0; i--) {
                arr[i] = arr[i - 1];
         }
            arr[0] = student;
            size++;
        
    }
     public void insertLast(Student student) {
        
            arr[size] = student;
            size++;
        
        }
    
        
    
    public int linearSearch(int x){
    
    for(int i= 0; i<arr.length;i++){
    if(arr[i].getId()==x)
    return i;
    }
    return -1;    
    }
    
   public void delete(int studentId) {
        int index = linearSearch(studentId);
        if (index != -1) {
            for (int i = index; i < size - 1; i++) {
                arr[i] = arr[i + 1];
            }
            arr[size - 1] = null; 
            size--;
        } else {
            System.out.println("Student with ID " + studentId + " not found, cannot delete.");
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arr[i]).append("\n");
        }
        return sb.toString();
    
    }}
    
