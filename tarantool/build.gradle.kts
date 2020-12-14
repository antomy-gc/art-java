/*
 * ART
 *
 * Copyright 2020 ART
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


dependencies {
    val zeroTurnaroundVersion: String by project
    val tarantoolConnectorVersion: String by project
    val log4jVersion: String by project
    val tarantoolCartridgeConnectorVersion = "0.3.3"

    implementation(project(":core"))
    implementation(project(":value"))
    implementation(project(":logging"))
    implementation(project(":server"))
    implementation(project(":template-engine"))

    api("org.zeroturnaround", "zt-exec", zeroTurnaroundVersion)
            .exclude("org.slf4j")
    api("org.tarantool", "connector", tarantoolConnectorVersion)
            .exclude("org.slf4j")
    api("org.apache.logging.log4j", "log4j-iostreams", log4jVersion)
            .exclude("org.apache.logging.log4j")
    api("io.tarantool", "cartridge-driver", tarantoolCartridgeConnectorVersion)

}


java {
    with(sourceSets.main.get().resources) {
        srcDir("src/main/templates")
        srcDir("src/main/lua")
    }
}
