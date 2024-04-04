package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(Routing) {
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
    }
}



