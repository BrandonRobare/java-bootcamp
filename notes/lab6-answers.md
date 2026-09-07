# Lab 6 - Short answers

## Reflection questions

**1. How would a future CRM use filter / map / groupingBy on customers the same way this lab uses them on employees?**

The shapes carry over with the nouns swapped: filter(c -> c.isActive()) for the current
book of business, map(Customer::getAccountValue) to project one field before summing, and
groupingBy(Customer::getRegion, summarizingDouble(Customer::getLifetimeValue)) for
count, average, max and min per region in one pass. That last one is getDepartmentStatistics
with Region in place of Department. No CRM exists here; the point is only that the collector
shapes do not depend on the domain.

**2. What are the advantages of Streams over loops?**

The pipeline states the result rather than the mechanics, so it still reads clearly later.
groupingBy(getDepartment, summarizingDouble(getSalary)) says what it produces in one line,
where the loop version is a mutable map, a nested block and several statements you have to
trace to see the same thing. Streams also leave the source alone, so displayReductions can
run four separate pipelines over the same 25 employees without any of them interfering.

**3. When should Streams be preferred?**

When the work is a read-only transform of a collection into a value, a list or a map:
filter, project, group, aggregate. A plain loop is better when the body mutates something
outside itself, needs to break out early, or walks two collections by index, because the
stream version of those is either impossible or harder to read than the loop it replaced.
