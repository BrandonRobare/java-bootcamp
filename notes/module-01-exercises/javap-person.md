# odule 1, Exercise 8

Ran `javap -c Person` on the `Person.class` from Exercise 7.

## 1. What the constructor bytecode does

`Person(String, int)` calls the superclass `Object` constructor first
(`invokespecial ... Object."<init>"`), then loads `this` and each parameter
onto the stack and writes them into the object's fields with two `putfield`
instructions - one for `name`, one for `age`.

## 2. What the `display` bytecode does

`display()` grabs `System.out` with `getstatic`, reads the `name` and `age`
fields off `this` with two `getfield` instructions, joins them and the literal
text into one `String` (`invokedynamic makeConcatWithConstants`), and passes
that string to `PrintStream.println` with `invokevirtual`.

## 3. Three opcodes I saw

| Opcode | Where                                                         | What it means |
| ------ |---------------------------------------------------------------| ------------- |
| `new` | `main`, offset 0 - `new #8 // class Person`                   | Allocates an uninitialized `Person` on the heap and pushes the reference. Note it does *not* run the constructor `invokespecial` at offset 8 does that. |
| `ldc` | `main`, offset 4 - `ldc #33 // String Aman`                   | "Load constant": pushes a value from the class's constant pool. My `"Aman"` literal is not built at run time; it was baked into the `.class` file by `javac`. |
| `invokevirtual` | `main`, offset 13 - `invokevirtual #38 // Method display:()V` | Calls an instance method, dispatched on the object's actual runtime type. Compare `invokespecial` (constructors, offset 8), which is *not* dispatched. |


