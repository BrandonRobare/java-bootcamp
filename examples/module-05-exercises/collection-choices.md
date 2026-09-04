# Collection choices

| # | Scenario | Need (order / unique / key→value / sorted) | Interface | Implementation | Why |
| - | -------- | ------------------------------------------ | --------- | -------------- | --- |
| 1 | Ordered catalog; duplicate titles allowed | index order, duplicates allowed | `List<Book>` | `ArrayList<>` | A library owns several copies of one title, so duplicates are data, not error. Indexed sequence. |
| 2 | Unique registered book IDs | uniqueness, membership tests | `Set<String>` | `HashSet<>` | Registration rejects an ID already present. `contains` is the operation, order is never read. |
| 3 | Book ID → current borrower ID | key→value | `Map<String, String>` | `HashMap<>` | One borrower per book. The Map declaration states that rule; two parallel Lists would not. |
| 4 | Alphabetically sorted categories | uniqueness + sorted iteration | `Set<String>` | `TreeSet<>` | Each category appears once, and it is displayed alphabetically. Sorting on every render is wasted work. |
| 5 | Category → count, sorted by category | key→value + sorted keys | `Map<String, Integer>` | `TreeMap<>` | A report keyed by category that must print in a stable order. HashMap order is not a contract. |
| 6 | Checkout history in event order | insertion order, duplicates allowed | `List<BorrowRecord>` | `ArrayList<>` | History is append-then-iterate. The same member may borrow the same book twice; both events are real. |

## Ambiguous requirements

**1. Unique IDs must also preserve registration order.**
`LinkedHashSet`. Still rejects duplicates, but iterates in the order elements were first
added. `HashSet` gives no order and `TreeSet` gives alphabetical order - neither is
registration order.

**2. Borrower lookup must preserve insertion order for display.**
`LinkedHashMap`. Same key→value lookup as `HashMap`, plus iteration in the order keys
were first inserted. `TreeMap` would sort by book ID, which is not the same thing.

**3. Many insertions and removals in the middle - is `LinkedList` automatically best?**
No. Its insert is O(1) only once you already hold the position; reaching the middle
costs an O(n) walk, and every element is a separate node, so traversal misses cache
constantly. `ArrayList`'s middle insert is O(n) but the shift is one contiguous memory
move, which hardware does very fast. `ArrayList` usually wins in practice. Measure the
real access pattern rather than reasoning from big-O alone.

## Why not the obvious wrong picks

- `Set` for the catalog (#1) - "books should be unique" collapses under *unique by what*.
  Three copies of the same title are three books. Uniqueness belongs to the ID, not the title.
- `List` for ID→borrower (#3) - a list of pairs works but hides the intent and turns every
  lookup into a scan. The Map says one-borrower-per-book in the type itself.
- `HashMap` for #5 - lookup is fine, but the report needs sorted keys and HashMap makes no
  ordering promise.
- Claiming everything is O(1) - complexity is per operation per implementation. `ArrayList`
  `get` is O(1); `ArrayList` `contains` is O(n).
