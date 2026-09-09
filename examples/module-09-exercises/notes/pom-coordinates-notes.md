# POM coordinates notes (Exercise 1)

| Field | Value / meaning |
| ----- | --------------- |
| groupId | the name of the organization using the reverse domain notation|
| artifactId | the project or artifact name |
| version | the release version that uses sematic versioning |
| packaging | the pom.xml file that includes core config for Maven |

Full GAV

`com.northstar:customer-service:0.1.0-SNAPSHOT`

## SNAPSHOT vs release

A `-SNAPSHOT` version means the artifact is still under active development and may change without a new release number.

## Coordinate mistakes

- Setting `groupId` to `com.example` is wrong because the Northstar CRM project uses the `com.northstar` namespace, and the Java packages are `com.northstar.crm`.
- Setting `artifactId` to `CustomerService` is wrong because Maven artifact IDs should use a stable lowercase, hyphenated module name like `customer-service`.
- Omitting `<packaging>` and assuming WAR is wrong because this plain Java library/app should build as a JAR.
- Using a different `version` on every laptop is wrong because teammates and CI need one agreed version so they resolve the same artifact.

## Debug / design challenge

Two artifacts with the same `artifactId` but different `groupId` values are not the same library. Maven uses the full coordinates, especially `groupId`, `artifactId`, and `version`, to identify the artifact.

## Predict the Output / Behavior

`0.1.0-SNAPSHOT` signals to consumers that the artifact is a work-in-progress build and may change before an official release.
