package com.example.repo

import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.atomic.AtomicInteger

object PersonRepo {
    private val idCounter = AtomicInteger()
    private val persons = CopyOnWriteArraySet<Person>()
    fun add(p: Person): Person {
       if(persons.contains(p)) {
           return persons.find { it == p }!! // if false => throw an exception
       }
        p.id = idCounter.incrementAndGet()
        persons.add(p)
        return p
    }
    fun get(id: String): Person = persons.find { it.id.toString() == id } ?: throw IllegalArgumentException("Invalid identifier")
    fun get(id: Int): Person = get(id.toString())
    fun getAll(): List<Person> = persons.toList()
    fun remove(p: Person): Boolean {
        if(!persons.contains(p)){
            throw IllegalArgumentException("Person does not exist")
        }
        return persons.remove(p)
    }
    fun remove(id: String): Boolean = persons.remove(get(id))
    fun remove(id: Int): Boolean = persons.remove(get(id.toString()))
    fun clear() = persons.clear()
}