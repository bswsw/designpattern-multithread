rootProject.name = "designpattern-multithread"

include(
    "single-threaded-execution",
    "active-object",
    "pubsub",
    "workerthread",
    "common",
    "future",
    "twophasetermination"
)

makeProjectDir(rootProject, "subprojects")

fun makeProjectDir(project: ProjectDescriptor, group: String) {
    project.children.forEach {
        println("$group -> ${it.name}")
        it.projectDir = file("$group/${it.name}")
    }
}