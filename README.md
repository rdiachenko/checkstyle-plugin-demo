# Checkstyle config bundled in a Gradle plugin

Demonstrates a custom Gradle plugin that applies Checkstyle and ships its own
`checkstyle.xml` and `suppressions.xml` inside the plugin jar. Projects that
apply the plugin get Checkstyle configured with no `config/checkstyle` directory.

Requires JDK 21 (Checkstyle 14.x).

```
./gradlew checkstyleMain
```

Expected result: the build fails with one `MemberName` violation in `App.java`.
The `MethodName` violation in `Legacy.java` is suppressed by the bundled `suppressions.xml`,
which proves `${config_loc}` resolves to the extracted plugin config.
