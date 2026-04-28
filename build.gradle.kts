plugins {
    id("me.lucyydotp.tinsel") version "0.2.2"
}

tinsel.fonts {
    create("sg:font") {
        offsets = setOf(8, 0, -2, -12)
        addedBitmapSpacing = 12
    }

    create("tinsel:default") {
        offsets = setOf(8, 0, -2, -12)
        addedBitmapSpacing = 12
    }
}

val packsquashPrepare = tasks.register<Copy>("packsquashPrepare") {
    destinationDir = project.layout.buildDirectory.dir("packsquashWork").get().asFile
    from(zipTree(tasks.assembleResourcePack.map { it.outputs.files.singleFile }))
}

tasks.register<Exec>("packsquash") {
    standardInput = System.`in`
    standardOutput = System.`out`

    inputs.files(packsquashPrepare)
    outputs.files(project.layout.buildDirectory.dir("packsquash/SG.zip"))
    executable = "packsquash"
    args("packsquash.toml")
}