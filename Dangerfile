detekt_task = "detekt"
test_task = "test"

# Sometimes it's a README fix, or something like that - which isn't relevant for
# including in a project's CHANGELOG for example
declared_trivial = github.pr_title.include? "#trivial"

# Warn when there is a big PR
warn("Big PR") if git.lines_of_code > 500

danger.import_plugin("https://raw.githubusercontent.com/NFesquet/danger-kotlin_detekt/master/lib/kotlin_detekt/plugin.rb")
danger.import_plugin("https://raw.githubusercontent.com/orta/danger-junit/master/lib/junit/plugin.rb")

kotlin_detekt.gradle_task = detekt_task
kotlin_detekt.report_file = "build/reports/detekt/detekt.xml"
kotlin_detekt.detekt

system "./gradlew #{test_task}"

junit.parse_files(Dir["./build/test-results/test/*.xml"])
junit.report
