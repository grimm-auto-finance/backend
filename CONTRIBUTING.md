# Contributing

<!-- cspell:ignore aosp intelli jacoco deps -->

## Style

This project uses `google-java-format` with the `--aosp` flag for code formatting. Please see that project's [README](https://github.com/google/google-java-format#using-the-formatter) for usage instructions.

### Command Line

Download the latest `-all-deps.jar` version from the project's [releases page](https://github.com/google/google-java-format/releases) and place it in a folder where you can find it. Then you can use the following command (**from the project root, don't accidentally run this on other folders!**) to reformat all files in the project:

```bash
java -jar "$PATH_TO_JAR" --aosp -i (find . -name '*.java')
```

### IntelliJ Plugin

If you're using IntelliJ, the plugin can be found [here](https://plugins.jetbrains.com/plugin/8527-google-java-format). Be sure to enable it in settings and set the style to AOSP.

![2021-10-31T12:39:32,557256036-04:00](https://user-images.githubusercontent.com/36740602/139593711-d0588aa8-04da-4874-9c8d-f31b31d3d316.png)

Also, make sure you use <kbd>Ctrl+Alt+Shift+L</kbd> to open the reformat menu and check the following boxes.

After doing that once, you can format with <kbd>Ctrl+Alt+L</kbd> as usual.

## Code Coverage

After running `gradle build` or `gradle test`, a file containing code coverage details can be found at `build/reports/jacoco/test/html/index.html`.

## Commit Messages

This project uses the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0-beta.2/#summary) standard, please follow this standard with your commit messages.
