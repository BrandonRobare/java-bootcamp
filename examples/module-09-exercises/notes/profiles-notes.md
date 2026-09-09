# Profiles notes (Exercise 2)

## Step 1 — The profile table

| Profile | Purpose | How activated |
| ------- | ------- | ------------- |
| dev | default for development without any real secrets | `activeByDefault` — plain `mvn package` selects it, no flag needed |
| prod | production build settings (endpoints, `app.env=prod`), secrets supplied from outside the POM | `mvn -Pprod package` |

| Question | Answer |
| -------- | ------ |
| Active on plain `mvn package` | `dev` (`activeByDefault`) |
| Activate prod | `mvn -Pprod package` |
| `dev` `app.env` | `dev` |
| `prod` `app.env` | `prod` |

## Step 2 — `activeByDefault` risks

activeByDefault can be risky because it is the profile used when not otherwise specified. So, it could cause a profile to be used
in a situation where it shouldn't and have unwanted effects like leaking secrets.

Two more traps:

- **Two profiles both `activeByDefault`** — Maven activates both, so whichever declares `app.env` last wins. The build silently depends on POM ordering instead of on an explicit choice.
- **`activeByDefault` is switched off by any `-P`** — activating an unrelated profile deactivates the default set, so `mvn -Preporting package` quietly loses the `dev` properties too.

## Step 3 — The secrets rule

Don't store real production secrets in pom.xml or version control like GitHub. Profile properties are committed files — anything in them is readable by everyone with repo access, forever. Real secrets come from environment variables, a CI secret store, or a vault at runtime.

## Step 4 — The activation rule

Keep `dev` as the laptop default.
Activate `prod` intentionally with `-Pprod`.
Never store real production secrets in `pom.xml` profiles.

## Other mistakes

- **Production passwords in the `dev` profile** — dev is the default, so every developer's build resolves production credentials, and they are in git history regardless.
- **`prod` marked `activeByDefault` on laptops** — every casual `mvn package` builds as production, and a mistake reaches a real endpoint.
- **Assuming profiles change Java package names** — they do not. Profiles change build configuration and properties; source layout and packages are the same in every profile.

## Pass check

- Four Q&A rows match the reference — Pass
- Profile mistakes flagged — Pass (five)
- Activation rule written — Pass
