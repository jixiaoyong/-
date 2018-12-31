package shujujiegou.tree

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2018/12/22
 * description: 哈夫曼编码
 *              对一段文本进行压缩，解压
 *              1/5.将字符按照出现的频次生成优先级队列
 *              2/5.依次取出两个最小的字符，为他们生成一个父节点（父节点频次为两个子节点之和），
 *              并将插入优先级队列中，依次循环直到优先级队列中只有一个节点——哈夫曼树的根节点
 *              3/5.从哈夫曼树的根开始，以向左为0，向右为1对其叶子节点上的字符赋予编码
 *              4/5.压缩：用字符的编码替代字符
 *              5/5.解压：用字符代替对应的编码
 */
class HuffmanCodeUtils {

    /**
     * 一段文本中字符和其出现的次数统计表
     */
    private var sourceCode = HashMap<Char, Int>()
    /**
     * 一段文本中字符和其出现的次数按照优先级排序后的表
     */
    private var priorityLine = PriorityTreeLine()
    /**
     * Huffman树
     */
    private var huffmanTree: HuffmanTree? = null

    private fun initSourceCode(str: String) {
        var charArray = str.toCharArray()
        charArray.map {
            val count = if (sourceCode[it] == null) 0 else sourceCode[it] as Int
            sourceCode.put(it, count + 1)
        }
        sourceCode.map {
            priorityLine.insert(HuffmanNode(CharObject(it.key, it.value)))
        }
        huffmanTree = HuffmanTree(priorityLine)
    }

    fun encode(str: String): String {
        initSourceCode(str)

        var stringBuilder = StringBuilder()
        str.toCharArray().map {
            stringBuilder.append(" " + huffmanTree?.alphaMap?.get(it))
        }
        return stringBuilder.toString().substring(1)//去掉之前多加的空格
    }

    fun decode(str: String): String {
        var charArray = str.split(" ")
        var stringBuilder = StringBuilder()
        charArray.map {
            stringBuilder.append(huffmanTree?.codeMap?.get(it))
        }
        return stringBuilder.toString()
    }
}

/**
 * Huffman树
 * 按照出现频次大小保存字母
 * 并保存有字母和编码映射表
 */
class HuffmanTree(priorityLine: PriorityTreeLine) {

    /**
     * 字母表
     * 字母及其对应编码
     */
    var alphaMap = HashMap<Char, String>()
    var codeMap = HashMap<String, Char>()
    /**
     * Huffman的根
     */
    var root: HuffmanNode? = null

    init {
        //建立树
        while (priorityLine.size() > 1) {
            root = HuffmanNode()
            var firstNode = priorityLine.pop()
            var lastNode = priorityLine.pop()
            if (firstNode!!.count < lastNode!!.count) {
                root!!.leftNode = firstNode
                root!!.rightNode = lastNode
            } else {
                root!!.leftNode = lastNode
                root!!.rightNode = firstNode
            }
            root!!.count = firstNode.count + lastNode.count
            priorityLine.insert(root!!)
        }

        //遍历树，获取字母编码映射表
        foreach(root, "")
    }

    /**
     * 遍历Huffman
     */
    private fun foreach(root: HuffmanNode?, str: String) {
        if (root == null) {
            return
        }
        if (root.leftNode == null && root.rightNode == null) {
            alphaMap[root.char!!] = str
            codeMap[str] = root.char!!
            return
        }
        foreach(root.leftNode, str + 0)
        foreach(root.rightNode, str + 1)
    }

}

/**
 * Huffman节点
 * 保存数据和左右节点
 */
class HuffmanNode(charObject: CharObject? = null, var leftNode: HuffmanNode? = null, var rightNode: HuffmanNode? = null) {
    var char: Char? = charObject?.char
    var count: Int = charObject?.count ?: 0
}

class CharObject(var char: Char?, var count: Int)


/**
 * 优先级队列
 * 小的优先
 */
class PriorityTreeLine {
    private var list = ArrayList<HuffmanNode>()

    fun insert(huffmanNode: HuffmanNode) {
        list.add(huffmanNode)

        if (list.size > 1) {
            for (x in list.size - 1 downTo 1) {
                if (list[x].count < list[x - 1].count) {
                    var temp = list[x]
                    list[x] = list[x - 1]
                    list[x - 1] = temp
                }
            }
        }
    }

    fun pop(): HuffmanNode? {
        if (list.size == 0) {
            return null
        }
        return list.removeAt(0)
    }

    fun size(): Int {
        return list.size
    }
}

fun main(args: Array<String>) {

    var str = "This is a string.Let's enjoy the magic of HuffmanTree!"
    var huffmanCode = HuffmanCodeUtils()
    var encodeStr = huffmanCode.encode(str)
    var decodeStr = huffmanCode.decode(encodeStr)
    println("str: $str \nencode: $encodeStr \ndecode: $decodeStr")
}