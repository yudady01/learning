package com.tommy.clouser
// Closure
def c = {
    v ->
        println "hello ${v}"
}
method('tommy', c)
methodB('tommy', c)
// ---------------------------
method('tommy') {
    v ->
        println "hello ${v}"
}
methodB('tommy') {
    v ->
        println "hello ${v}"
}
// ---------------------------
// method
def method(para, Closure closure) {
    closure(para)
}
// method
def methodB(para, Closure closure) {
    closure(para)
}