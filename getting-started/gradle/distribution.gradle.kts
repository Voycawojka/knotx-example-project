/*
 * Copyright (C) 2019 Knot.x Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

val downloadDir = file("${buildDir}/download")
val distributionDir = file("${buildDir}/out")
val stackName = "knotx"
val stackDistribution = "knotx-stack-${version}.zip"
val knotxVersion = project.property("knotx.version")

configurations {
    register("dist")
    register("zipped")
}

dependencies {
    "zipped"(group = "io.knotx", name = "knotx-stack", version = "${knotxVersion}", ext = "zip")
}

val cleanDistribution = tasks.register<Delete>("cleanDistribution") {
    delete(listOf(distributionDir, downloadDir))
}

val unzipStack = tasks.register<Copy>("unzipStack") {
    val zipPath = "${buildDir}/download/knotx-stack-${knotxVersion}.zip"

    from(zipTree(zipPath))
    into("${distributionDir}")
}

val copyConfigs = tasks.register<Copy>("copyConfigs") {
    from(file("src/main/packaging"))
    into(file("${distributionDir}/${stackName}"))
}

val downloadDeps = tasks.register<Copy>("downloadDeps") {
    from(configurations.named("dist"))
    into("${distributionDir}/${stackName}/lib")
}


val downloadStack = tasks.register<Copy>("downloadStack") {
    from(configurations.named("zipped"))
    into("${downloadDir}")
}


val assembleDistribution = tasks.register<Zip>("assembleDistribution") {
    archiveName = stackDistribution
    from(distributionDir)
}

assembleDistribution {
    dependsOn(copyConfigs, downloadDeps)
}

copyConfigs { dependsOn(downloadStack, unzipStack) }

tasks.named("build") { finalizedBy(assembleDistribution) }
tasks.named("clean") { dependsOn(cleanDistribution) }