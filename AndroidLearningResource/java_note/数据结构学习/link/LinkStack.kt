package shujujiegou.link

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: www.jixiaoyong.github.io
 * date: 2018/12/15
 * description: 用链表实现的栈
 */
class LinkStack {

    var first: Link? = null

    fun insert(link: Link) {
        link.next = first
        first = link
    }

    fun pop(): Link? {
        var temp = first
        first = first?.next
        return temp
    }

    fun peek(): Link? {
        return first
    }

    override fun toString(): String {
        var sBuilder = StringBuilder()
        var current = first
        while ( current!= null){
            sBuilder.append("$current,")
            current = current?.next
        }
        return sBuilder.toString()
    }
}

fun main(args: Array<String>) {
    var stack = LinkStack()
    stack.insert(Link(1.1, 1))
    stack.insert(Link(2.2, 2))
    print(stack.peek())
    print("\n")
    stack.insert(Link(3.3, 3))
    print(stack.pop())
    print("\n")
    print(stack)
}