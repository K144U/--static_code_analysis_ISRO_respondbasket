abstract class Example{
	public Example() {
        // Violates abstract_class_without_abstract_method
    }

    public Example(int x) {
        // Violates protected_constructor_in_abstract_class
    }

    public void doSomething() {
        // Violates avoid_reassigning_loop_variables
        for (int i = 0; i < 10; i++) {
            i = i + 1;
        }
        
        // Violates avoid_reassigning_exception_variable
        try {
            // Some code that might throw an exception
        } catch (Exception e) {
            e = new Exception(); // Reassigning exception variable
        }
        
        String serverURL = "http://192.168.1.100:8080/api/data";
    }
    
	public static void main(String[] args) {
		int x,y; // This line should violate the rule
        String name = "John";
        double price = 25.5; // This line is fine
        int total=0, count=0;
        
        int month = 13;
        String monthName,s;

        switch (month) {
          case 1:
            monthName = "January";
            break;
          case 2:
            monthName = "February";
            break;
          case 3:
            monthName = "March";
            break;
          case 4:
            monthName = "April";
            break;
          case 5:
            monthName = "May";
            break;
          case 6:
            monthName = "June";
            break;
          default:
              monthName = "Invalid month";
          case 7:
            monthName = "July";
            break;
          case 8:
            monthName = "August";
            break;
          case 9:
            monthName = "September";
            break;
          case 10:
            monthName = "October";
            break;
          case 11:
            monthName = "November";
            break;
          case 12:
            monthName = "December";
            break;
        }

        System.out.println("Month: " + monthName);
	
//"D:\\Java Parser\\java_dom_parser\\src\\java_dom_parser\\My_Maven\\src\\main\\java\\Example.java"
//"D:\\Java Parser\\java_dom_parser\\src\\java_dom_parser\\My_Maven\\src\\main\\java\\trial.xml"
}
}
