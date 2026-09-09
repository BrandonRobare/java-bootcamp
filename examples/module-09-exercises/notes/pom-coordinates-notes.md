# POM coordinates notes (Exercise 1)

| Field | Value | Meaning |
| ----- | ----- | ------- |
| groupId | `com.northstar` | The organization / product namespace, reverse-domain notation |
| artifactId | `customer-service` | The module name inside that group |
| version | `0.1.0-SNAPSHOT` | The release or snapshot label, semantic versioning |
| packaging | `jar` | The output type Maven builds — `jar` here, others are `war`, `pom`, `ear` |

Full GAV

`com.northstar:customer-service:0.1.0-SNAPSHOT`

## SNAPSHOT vs release

A `-SNAPSHOT` version means the artifact is still under active development and may change without a new release number. A release version (`0.1.0`) is immutable — once published, that exact bytes-for-coordinates pairing never changes again.

## Coordinate mistakes

- Setting `groupId` to `com.example` is wrong because the Northstar CRM project uses the `com.northstar` namespace, and the Java packages are `com.northstar.crm`.
- Setting `artifactId` to `CustomerService` is wrong because Maven artifact IDs should use a stable lowercase, hyphenated module name like `customer-service`.
- Omitting `<packaging>` and assuming WAR is wrong because this plain Java library/app should build as a JAR. (Omitted packaging actually defaults to `jar`, so the real mistake is *assuming* it is a WAR.)
- Using a different `version` on every laptop is wrong because teammates and CI need one agreed version so they resolve the same artifact.

## Debug / design challenge

Two artifacts with the same `artifactId` but different `groupId` values are not the same library. Maven identifies an artifact by the full coordinates, so `com.northstar:customer-service` and `com.acme:customer-service` are unrelated and can both sit on the classpath at once.

## Predict the Output / Behavior

`0.1.0-SNAPSHOT` signals to consumers that the artifact is a work-in-progress build and may change before an official release.

## Pass check

- Five coordinate answers match the reference — Pass
- `-SNAPSHOT` explained — Pass
- Four coordinate mistakes identified — Pass
