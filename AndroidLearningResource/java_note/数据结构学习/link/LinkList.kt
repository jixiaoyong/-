package shujujiegou.link

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2018/12/15
 * description: 单链表
 */
class LinkList {
    var first: Link? = null

    fun insertFirst(dVar: Double, iVar: Int) {
        val link = Link(dVar, iVar)
        link.next = first
        first = link
    }

    fun deleteFirst(): Link? {
        val temp = first
        first = first?.next
        return first
    }

    override fun toString():String{
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
    var linkList = LinkList()
    linkList.insertFirst(1.2,3)
    linkList.insertFirst(2.2,3)
    linkList.insertFirst(3.2,3)
    linkList.insertFirst(4.2,3)
    linkList.insertFirst(5.2,3)
    linkList.deleteFirst()
    print(linkList.toString())
}