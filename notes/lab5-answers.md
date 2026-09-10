# Lab 5 - Reflection Answers

## 1. When choose `List` over `Set`?

When order matters or duplicates are legitimate. `books` and `members` are
`ArrayList` because the catalogue is displayed in insertion order and two copies
of the same title are two real books; `borrowHistory` is a `List` for the same
reason — it is a log, and a log that silently drops repeats is broken. A `Set`
would be wrong for all three.

## 2. Why `HashSet` before inserting a book ID?

`bookIds.contains(id)` is O(1), so the duplicate check stays constant-time no
matter how large the catalogue grows; scanning the `ArrayList<Book>` for a
matching ID would be O(n) on every single add. The `HashSet` is a fast index over
data the `ArrayList` already holds — the two are kept in sync deliberately, not
by accident.

## 3. Why a `Map` for "currently borrowed" vs only a boolean?

A boolean answers whether a book is out; `HashMap<String, String>` answers *who
has it*, keyed by book ID, which is what `returnBook` and the borrower report both
need. The boolean cannot be interrogated — `borrowRecords.containsKey(bookId)`
replaces a flag lookup and a separate borrower search with one operation.

Worth flagging: this lab keeps **both**. `borrowRecords` and `Book.available` are
two sources of truth for the same fact, and they are updated in separate lines of
`borrowBook`. The summary report counts the map while menu 12 filters the flag, so
if one update is ever missed the two screens disagree. The map alone would be
enough.

## Note on scope

Taken on the timed path 2026-09-04. All eight collection fields came from the
starter — the four methods written by hand were `borrowBook`, `returnBook`,
`displaySummaryReport` and `findMostPopularCategory`. The field design these
questions ask about is therefore worth rebuilding cold; see [[Lab 5 Study Guide]].
