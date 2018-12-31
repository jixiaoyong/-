package shujujiegou.link

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2018/12/15
 * description: todo
 */
class Link(var dVar: Double, var iVar: Int) {
    var next: Link? = null

    override fun toString(): String {
        return "{$dVar,$iVar}"
    }
}