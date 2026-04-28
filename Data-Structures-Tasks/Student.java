
package task_data_structure;


public class Student {
    String first_name ;
    String last_name;
    int id ;

    public Student() {
    }
    
    public Student(String f , String l, int id){
    
        first_name=f;
        last_name=l;
        this.id=id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getId() {
        return id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setId(int id) {
        this.id = id;
    }
     @Override
    public String toString() {
        return "Student{" + "firstname=" + first_name + ", lastname=" + last_name + ", id=" + id + '}';
    }
     

    
}
