# Lab 2 - Short answers

Student Management console, package com.academy.student, built 2026-09-02.
Sources in examples/Lab2-JavaSyntax/src/com/academy/student/.

## Topics

**1. Why can't the package be src.com.academy.student?**

A package name mirrors the folder path from the source root down, and src is the source
root, so it is not part of the name. My first version had package src.com.academy.student
in all three files because IntelliJ had not been told src was a source root, so it treated
the project folder as the root and counted src as the first segment. The fix was changing
the declaration in all three files and marking src as Sources Root in the IDE, otherwise it
re-adds the prefix to every new file.

**2. Why does javac take slashes and java take dots?**

javac compiles files, so it takes a filesystem path: javac com/academy/student/*.java. The
java launcher loads a class by its fully qualified name, so it takes dots and works out the
path itself from the classpath: java com.academy.student.Main. Both are run from src,
because src is the root of the package tree.

**3. What did using a fixed array instead of a collection cost?**

Storage is Student[20] plus an int studentCount. The array cannot tell me how full it is.
students.length is always 20 whether I have zero students or nineteen, and whether a slot
holds null is a side effect of never having written there, not a fact about the roster.
Only studentCount knows, so every read of the array has to route through it: the loop bound
is studentCount, and printStudentTable is passed studentCount rather than students.length.
Passing length instead would printf a null and throw NullPointerException. I also had to
write the capacity check myself, because nothing stops an array write at index 20 except
ArrayIndexOutOfBoundsException. An ArrayList would have made all of this isEmpty and size,
with no capacity guard at all.

**4. Which validation case did I get wrong first?**

displayStudents. I guarded with students[0] == null instead of studentCount == 0. It passes
every test in this lab, because nothing ever removes a student, so slot zero is filled if
and only if the count is above zero. It is still the wrong condition: it tests a symptom
rather than the state. If I added a delete operation, deleting the first student would
either print "No students to display." over a full roster, or leave a stale object in slot
zero and print a table of it. Every other method in the file already guarded on
studentCount == 0, so mine was also the one a reader would stop and check.

**5. Why is == correct on the student ID but wrong on the name?**

getStudentId returns an int, a primitive, and == on primitives compares values. That is what
findStudentIndex does. getName returns a String, which is an object, and == on objects
compares references, not characters. A name the user typed at the Scanner is a different
object from any literal in the source, so == would be false even when the text matched.
Strings need .equals().

**6. What does students[studentCount++] do?**

It writes the new Student at the current value of studentCount, then increments the field.
Postfix ++ evaluates to the old value. With studentCount at 2 it writes to index 2 and
leaves the field at 3. Prefix ++studentCount would evaluate to 3 and write to index 3,
skipping slot 2. Both forms leave the variable at the same number; only the value of the
expression differs.

**7. Why does findStudentIndex return -1, and why an index rather than a Student?**

-1 is a sentinel for "not found", chosen because no valid array index is negative, so it
can never collide with a real answer. Callers check != -1 for found. Returning the index
rather than the object is the more useful shape, because an index also lets me overwrite or
remove that slot later. searchStudent then calls students[index].display(). One method
serves two callers: addStudent uses it to reject a duplicate ID before inserting, and
searchStudent uses it to find.

**8. What broke in calculateAverage?**

Three things. The loop was written as for (i = 0; ...) with i never declared, which does not
compile. There was no empty guard, and dividing a double by zero does not throw: it yields
NaN and would have printed "Average Marks : NaN" with no error. Only integer division by
zero throws ArithmeticException. And markSum had to be a double: with marks of 88 and 91, an
int sum gives 179 / 2 == 89 by truncation, so the average would be silently wrong by half a
point. Declaring markSum as double makes markSum / studentCount mix a double with an int,
which Java widens to double arithmetic, so 89.5 survives and prints as 89.50.

**9. What do %.2f and %n mean?**

In %.2f, the % starts a specifier, the dot says a precision follows, the 2 is the number of
digits after the decimal point, and the f is the type: fixed-point decimal, which consumes
a double argument. Each specifier consumes one argument, left to right. It affects display
only: 89.4567 prints as 89.46 by rounding, but the double still holds full precision. %n is
not a specifier for an argument and consumes nothing; it is a newline that emits whatever
the current platform uses, so it is portable where \n is not. The specifier has to match the
argument type or it throws IllegalFormatConversionException at runtime rather than failing
to compile: I used %-8d for the int ID, %-20s for the name and course, and %-8.2f for the
marks. The minus sign left-aligns within the field width, which is what makes the table
columns line up.

**10. Why does the switch need no break?**

It is the arrow form, which cannot fall through. That is the whole reason the form exists.
A traditional switch runs on into the following case unless every case ends in break. Braces
are only needed when a case body is more than one statement, which here is only case 5,
where the exit prints, closes the Scanner and returns. The bonus cases started as
case 6, 7, 8, 9, 10 -> sharing one branch, which is correct when several inputs do the same
thing, and were split into five separate cases once each called a different method.

## Prompts

**Validation, and where each check lives**

There are four separate concerns and they are handled in two different styles. Non-numeric
input is caught by try/catch on NumberFormatException, around Integer.parseInt in the menu
loop and inside the read helpers. Out-of-range values are rejected by an if inside those
helpers. Duplicate IDs are rejected by findStudentIndex(id) != -1 in addStudent before the
object is constructed. Capacity is checked by studentCount >= MAX_STUDENTS at the top of
addStudent.

The difference in style is deliberate. readValidMarks and readPositiveInt loop until the
input is valid, so the caller never has to handle failure. The duplicate ID check returns
to the menu instead, because after a duplicate the user probably wants to reconsider rather
than be asked for another ID immediately.

**An inconsistency in my own helpers**

The three input helpers do not agree on who prints the prompt. readNonEmptyLine takes the
prompt as an argument, readValidMarks prints its own, and readPositiveInt prints nothing at
all, so both callers have to print one first or the program looks frozen while it waits on
input. I also used println rather than print for the search prompt at first, which dropped
the cursor to the next line and made that one prompt look different from every other one in
the program. Both were fixed by matching the print("Student ID : ") form used in addStudent.

**Compile and run**

    cd ~/java-bootcamp/examples/Lab2-JavaSyntax/src
    javac com/academy/student/*.java
    java com.academy.student.Main

Clean rebuild, to prove the sources build from nothing:

    cd ~/java-bootcamp/examples/Lab2-JavaSyntax/src
    rm -f com/academy/student/*.class
    javac com/academy/student/*.java
    java com.academy.student.Main

**Menu**

Choices 1 to 5 are the core path: add, display, search by ID, average marks, exit. Choices
6 to 10 are the bonus set: top student, lowest marks, pass/fail report, sort by marks
descending, and class statistics. All ten are implemented and wired.

**Verified behaviour**

Adding a student with a duplicate ID is refused before any further prompts. Marks of 150 are
refused and re-prompted until a value in range is given. Typing abc at the menu prints the
invalid input message and returns to the menu rather than crashing. Display, average, top
and lowest all report that there are no students when the roster is empty, rather than
printing an empty table or NaN. With Alice 88, Bob 45 and Cara 91: the average is 74.67, the
top is Cara at 91.00, the lowest is Bob at 45.00, Bob is the only Fail at the 50 mark
boundary, and the sort lists Cara, Alice, Bob.
