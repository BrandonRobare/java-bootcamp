# Module 1, Exercise 7

`Person person = new Person("Aman", 21);`

```text
        STACK                                  HEAP
  (per method call,                    (all objects made with new,
   pops on return)                      cleaned up by the GC)

  main() frame
  +---------------------+              +--------------------------+
  |  args   -> ref ---------- - - - -> |  String[] (empty)        |
  |                     |              +--------------------------+
  |  person -> ref ------------------> |  Person                  |
  +---------------------+              |    name -> ref --------------+
                                       |    age  =  21            |   |
                                       +--------------------------+   |
                                                                      v
                                       +--------------------------+
                                       |  String "Aman"           |
                                       +--------------------------+
```

- `person` is a **local variable** in `main`'s stack frame. It holds a
  *reference* (an arrow), not the object itself.
- The `Person` object lives on the **heap**. `new` allocated it and handed
  back the reference that `person` stores.
- `age` is an `int` stored **inside** the object on the heap.
  `name` is another reference, pointing at a separate `String` object.
- When `main` returns, its frame pops and `person` is gone. Nothing points at
  the `Person` any more, so it becomes eligible for garbage collection.


