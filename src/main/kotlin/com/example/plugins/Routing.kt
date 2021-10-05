package com.example.plugins

import com.example.repo.Person
import com.example.repo.PersonRepo
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.serialization.*
import kotlinx.serialization.json.*

fun Application.configureRouting() {

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("API up and running")
        }
        get("/persons") {
            call.respond(PersonRepo.getAll())
        }
        get("/persons/{id}") {
            val id: String? = call.parameters["id"]
            val person: Person = if(id != null) {
                PersonRepo.get(id)
            }else {
               throw IllegalArgumentException("Invalid Identifier")
            }
            call.respond(person)
        }
        post("/persons") {
            val person = call.receive<Person>()
            PersonRepo.add(person)
            call.respondText("Person added correctly", status = HttpStatusCode.Created)
        }
    }
}