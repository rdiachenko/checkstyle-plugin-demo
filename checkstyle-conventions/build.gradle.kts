plugins {
    `java-gradle-plugin`
}

group = "com.example"
version = "1.0.0"

gradlePlugin {
    plugins {
        create("checkstyleConventions") {
            id = "com.example.checkstyle-conventions"
            implementationClass = "com.example.conventions.CheckstyleConventionsPlugin"
        }
    }
}
