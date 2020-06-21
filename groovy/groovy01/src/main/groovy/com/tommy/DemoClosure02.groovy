package com.tommy
// Closure
def c = {
    v ->
        print "hello ${v}"
}
method('tommy', c)
methodB('tommy', c)
// ---------------------------
method('tommy') {
    v ->
        print "hello ${v}"
}
methodB('tommy') {
    v ->
        print "hello ${v}"
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