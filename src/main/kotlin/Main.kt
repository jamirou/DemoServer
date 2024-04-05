package com.example

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.Serializable

fun main() {
    embeddedServer(Netty, port = 8080) {
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
    }
}
@Serializable
data class Person(val name: String, val age: Int)




