# Maven layout notes (Exercise 1)

| File | Destination |
| ---- | ----------- |
| `Customer.java` | `src/main/java/com/northstar/crm/entity/` |
| `CustomerServiceTest.java` | `src/test/java/com/northstar/crm/service/` |
| `application.properties` | `src/main/resources/` |
| `sample-customers.json` (tests only) | `src/test/resources/` |
| `CODING-STANDARDS.md` | `docs/` |
| `Customer.class` | `target/classes/com/northstar/crm/entity/` — generated, not written by hand |

## Why `target/` is not committed

<!-- DONE: one or two sentences -->

`target/` is generated from source by Maven. It can be deleted and rebuilt with `mvn clean package`, so it belongs in `.gitignore` rather than in history — committing it adds binary churn and merge conflicts that carry no information.

## Mistakes to avoid

<!-- DONE: production code in test tree; secrets in properties; hand-editing target; -->

- **Production code in `src/test/java`** — the test tree is not packaged into the JAR, so the class exists at test time and vanishes at runtime.
- **Secrets in `application.properties`** — anything committed there is in git history forever, readable by everyone with repo access. Use environment variables or a secrets manager.
- **Hand-editing `target/classes`** — the next `mvn compile` overwrites it, and the change was never in source, so it is silently lost.
- **Test fixtures in `src/main/resources`** — ships test data in the production artifact and blurs which files the app actually reads at runtime.

## Someone committed `target/`

```bash
git rm -r --cached target
echo "target/" >> .gitignore
git commit -m "Stop tracking build output"
```

Deletes it from tracking, keeps it on disk, stops it coming back.

## Pass check

- Six files classified — Pass
- `target/` explained as generated and ignored — Pass
- Resources must not contain committed secrets — Pass
