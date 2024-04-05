package com.example

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.html.*
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

fun main() {
    embeddedServer(Netty, port = 8080, watchPaths = listOf("classes", "resources")) {
        install(ContentNegotiation) {
            json()
        }
        module()
    }.start(wait = true)
}

@Suppress("unused")
fun Application.module() {
    install(Routing) {
        static {
            resources("static")
        }
        get("/") {
            call.respondText("Hello, World!")
        }
        //Way 2
        get("/2") {
            call.respondText("This is the path num 2, As you can see, this is another way to add a new path")
        }
        //Way 3
        get("/users/{username}") {
            val username = call.parameters["username"]
            val header = call.request.headers["connection"]
            if (username == "Admin") {
                call.response.header(name = "CustomHeader", value = "Admin")
                call.respond(message = "Hello $username, You are an administrator", status = HttpStatusCode.OK)
            }
            call.respondText("Hello again $username, And your header: $header ")
        }
        get("/user") {
            val name = call.request.queryParameters["name"]
            val age = call.request.queryParameters["age"]
            call.respondText("Hello $name, You are $age years old")
        }
        get("/person") {
            try {
                val person = Person(name = "Jam", age = 30)
                call.respond(message = person, status = HttpStatusCode.OK)
            } catch (e: Exception) {
                call.respond(message = "${e.message}", status = HttpStatusCode.BadRequest)
            }
        }
        get("/redirect") {
            call.respondRedirect(url = "/redirected", permanent = false)
        }
        get("/redirected") {
            call.respondText("You have been redirected to /redirected")
        }
        get("/welcome") {
            var name = call.request.queryParameters["name"]

            if (call.request.queryParameters.contains("name")) {
                name = call.request.queryParameters["name"]
            }

            call.respondHtml {
                head {
                    title { +"Welcome Page" }
                }
                body {
                    div {
                        style = "text-align: center;"
                        h3 {
                            if (name.isNullOrEmpty()) {
                                +"Welcome my friend"
                            } else {
                                +"Hello, $name!"
                            }
                        }
                        p { +"This is the body of the page." }
                        p { +"Your current time is: ${LocalDateTime.now()}" }
                        p { +"Your current directory is: ${System.getProperty("user.dir")}" }
                        img(src = "R.png") {
                            style = "display: block; margin: auto; max-width: 100%; height: auto;"
                        }
                        if (name.isNullOrEmpty()) {
                            form(action = "/welcome", method = FormMethod.get) {
                                style = "margin-top: 20px;"
                                label {
                                    htmlFor = "nameInput"
                                    +"Enter your name: "
                                }
                                input {
                                    id = "nameInput"
                                    name = "name"
                                    type = InputType.text
                                    required = true
                                }
                                button(type = ButtonType.submit) {
                                    style = "margin-left: 10px;"
                                    +"Submit"
                                }
                            }
                        }
                    }
                }
            }
        }


    }
}

@Serializable
data class Person(val name: String, val age: Int)




