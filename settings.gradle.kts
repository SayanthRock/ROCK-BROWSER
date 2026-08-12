pluginManagement { repositories { google(); mavenCentral(); gradlePluginPortal() } }
dependencyResolutionManagement { repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS); repositories { google(); mavenCentral() } }
rootProject.name = "RockBrowser"
include(":app", ":core:common", ":core:database", ":core:datastore", ":core:network", ":core:security", ":core:designsystem", ":browser", ":feature:home", ":feature:tabs", ":feature:bookmarks", ":feature:history", ":feature:downloads", ":feature:private", ":feature:settings", ":feature:github", ":feature:about")
