// Practice with Java variables
// 9/1/26

// dataType variableName = value;
int age = 25;
double salary = 75000.50;
char grade = 'A';
boolean isJavaFun = true;
String name = "Alice";


//Local Variable
public void show() {
    int age = 25;
    System.out.println(age);
}

//Instance Variable
class Student {
    String name; //instance variable
    int age;
}

// Static (Class) variable
class Student {
    static int count = 0; //static variable
}