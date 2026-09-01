# Lab 1 - Short answers

## Topics

**1. What does javac do, and what does it produce?**

It compiles .java source into one .class file per class, holding JVM bytecode. It runs
nothing and produces no native executable.

**2. What is bytecode, and why isn't it machine code?**

Bytecode is the instruction set of the JVM, not of the CPU. javap -c Calculator shows add
as iload_0, iload_1, iadd, istore_2, ireturn: stack machine instructions with no registers
or addresses, so no processor runs them directly. The JVM interprets them and the JIT
compiles the hot ones.

**3. What does WORA mean, and what makes it possible?**

Write Once, Run Anywhere. The compile target is the JVM, not the hardware, so the
platform-specific piece is the JVM itself. My Calculator.class from this Mac would run
unchanged on the Windows laptops in the cohort.

**4. What is the JVM's role at runtime?**

Load classes, verify bytecode, execute it, manage memory. Verification rejects malformed
bytecode. Memory management is why I never free the 100,000 Employee objects myself.

**5. What lives on the stack vs the heap? (what goes where)**

Each method call gets a frame holding its locals and primitives. In Calculator.main, x, y,
and sum are ints in that frame, per istore_1 / istore_2 / istore_3. The add method gets its
own frame, discarded on ireturn. Objects always live on the heap: in MemoryDemo, employees
is a reference on the stack, while the ArrayList and all 100,000 Employee objects sit on
the heap.

**6. What lives on the stack vs the heap? (tracing one statement)**

For Employee emp = new Employee(101, "Aman"), new allocates the object on the heap. The
emp variable is a reference in the stack frame holding its address. The 101 is copied into
the id field on the heap. The string is a separate heap object, and name holds a reference
to it. One statement, one stack slot, two heap objects.

**7. When and how does a class get loaded?**

Lazily, on first active use. Running java -verbose:class Employee shows Employee loading
well after the java.lang classes the JVM needs for itself. Each loader delegates upward to
its parent first, so bootstrap handles java.lang.String and the application loader handles
Employee from the classpath.

## Prompts

**Step 6, stack and heap for Calculator**

The x and y variables are ints in the frame of main. The a and b parameters in add are
separate copies in a separate frame, so changing them could not affect x and y. The result
variable is another primitive in that frame, gone once add returns. The string from
"Sum = " + sum is a heap object built by the invokedynamic makeConcatWithConstants call at
offset 16.

**Step 10, JVM flags observed**

InitialHeapSize is 536870912 (512 MB), MaxHeapSize is 8589934592 (8 GB), UseG1GC is true.
All three are marked ergonomic, so the JVM sized them from this laptop's RAM rather than me
setting them. Roughly 1/64 of RAM initial, 1/4 max.

**Checkpoint A, why java did not need the .java file**

Because java runs bytecode, and after javac succeeded everything it needs is in
HelloWorld.class. The source has no role at runtime.

**Checkpoint C, CRM services on the same JDK**

A production CRM service is the same three things scaled up: bytecode the JVM loads and
verifies, a heap holding its objects, and threads executing frames.

