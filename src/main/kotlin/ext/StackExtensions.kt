package ext

import java.util.Stack

fun <T> stackOf(collection: Collection<T>): Stack<T> {
    val stack = Stack<T>()
    stack.addAll(collection)
    return stack
}

fun <T> stackOf(vararg items: T): Stack<T> {
    return stackOf(items.toList())
}


// Can't call this for some reason?
fun <T> Stack<T>.of(collection: Collection<T>): Stack<T> {
    val stack = Stack<T>()
    stack.addAll(collection)
    return stack
}
