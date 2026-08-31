## 1. What javac produced

`javac Hello.java` produced `Hello.class` which is bytecode. It is an instruction set for the JVM. I deleted `Hello.java` and `java Hello` still printed `Hello, Java!!`. The source file is not needed at run time; only the `.class` is.

## 2. What the java command does with it

`java Hello` starts a JVM, which loads `Hello.class`, verifies it, and executes the `main` method inside it. I did not run `javac` again before this. Compiling and running are two separate steps.

## 3. Why the same .class runs on any OS

The `.class` file is the same bytes on every platform. What is different is the JVM,
which is installed per-OS and translates that bytecode to the local machine's
instructions. My `WoraProbe` printed `Mac OS X` where the guide's sample printed
`Windows 11` with the same bytecode, so the program adapts without the source being recompiled.

## 4. Mistake I hit

`java Hello` failed with `Could not find or load main class Hello` /
`ClassNotFoundException: Hello`. The cause was that I was in a folder that had
`Hello.java` but no `Hello.class` I had never compiled in that directory.

## What I observed running `java WoraProbe.java`

On JDK 21 this did not fail. It printed the same two lines:

    Mac OS X
    Bytecode runs on: Mac OS X
