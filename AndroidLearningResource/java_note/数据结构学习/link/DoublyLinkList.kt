package shujujiegou.link

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2018/12/15
 * description: 双向链表
 */
class DoublyLinkList {

    var first:DoublyLinkItem? = null
    var last:DoublyLinkItem? = null

    fun insertFirst(doublyLinkItem: DoublyLinkItem){
        if (first == null) {
            first = doublyLinkItem
            last = doublyLinkItem
        }else{
            first!!.previous = doublyLinkItem
            doublyLinkItem.next = first
            first = doublyLinkItem
        }
    }

    fun insertLast(doublyLinkItem: DoublyLinkItem){
        if (last == null) {
            first = doublyLinkItem
            last = doublyLinkItem
        }else{
            last!!.next = doublyLinkItem
            doublyLinkItem.previous = last
            last = doublyLinkItem
        }
    }

    fun deleteFirst():DoublyLinkItem?{
        var temp = first
        first?.next?.previous = null
        first = first?.next
        return temp
    }

    fun deleteLast():DoublyLinkItem?{
        var temp = last
        last?.previous?.next = null
        last = last?.previous
        return temp
    }

    override fun toString(): String {
        var sBuilder = StringBuilder()
        var current = first
        while ( current!= null){
            sBuilder.append("$current,")
            current = current.next
        }
        return sBuilder.toString()
    }
}

fun main(args: Array<String>) {
    var doublyLinkList = DoublyLinkList()
    doublyLinkList.insertFirst(DoublyLinkItem(1.1,2))
    doublyLinkList.insertFirst(DoublyLinkItem(1.2,2))
    doublyLinkList.insertFirst(DoublyLinkItem(1.3,2))
    println("First " + doublyLinkList.deleteFirst())
    println(doublyLinkList)

    doublyLinkList.insertLast(DoublyLinkItem(1.4,2))
    doublyLinkList.insertLast(DoublyLinkItem(1.5,2))
    println(doublyLinkList)
    println("Last " + doublyLinkList.deleteLast())
}