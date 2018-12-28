package shujujiegou.graph

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: www.jixiaoyong.github.io
 * date: 2018-12-28
 * description: 有向图
 */
class Digraph<T> {

    private var hashMap = hashMapOf<T, GraphicNode<T>?>()

    fun insert(key: T) {
        if (!hashMap.containsKey(key)) {
            hashMap[key] = GraphicNode(key)
        }
    }

    fun addEdge(start: T, end: T) {
        if (hashMap.containsKey(start) && hashMap.containsKey(end)) {
            var sindex = hashMap[start]
            while (sindex?.next != null) {
                sindex = sindex.next
            }
            sindex!!.next = GraphicNode(hashMap[end]!!.data)
        }
    }

    /**
     * 拓扑排序
     */
    fun topologicalSort() {
        if (hashMap.size == 0) {
            return
        }
        var displayList = ArrayList<T?>()

        while (hashMap.size > 0) {
            var successorKey = getSuccessorNode()
            if (successorKey == null) {
                println("图中有环")
                break
            } else {
                hashMap.remove(successorKey)
                displayList.add(successorKey)
            }
        }
        print("\n ${displayList.reversed()} \n")
    }

    /**
     * 遍历图，查找没有后继点的顶点
     * @return -1表示没有这样的点 否则返回该点key
     */
    fun getSuccessorNode(): T? {
        val result: T? = null
        var ketSet = hashMap.keys

        ketSet.map {
            var node: GraphicNode<T>? = hashMap[it]?.next ?: return it
            while (node != null) {
                var realNode = hashMap[node.data]
                if (realNode != null) {
                    return@map
                }
                node = node.next
            }
            return it
        }

        return result
    }

}

class GraphicNode<T>(var data: T, var next: GraphicNode<T>? = null) {
    override fun toString(): String {
        return "[$data]"
    }
}

fun main(args: Array<String>) {
    var testArr = arrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H')
    var digraph = Digraph<Char>()
    testArr.map {
        digraph.insert(it)
    }
    digraph.addEdge('A', 'D')
    digraph.addEdge('A', 'E')
    digraph.addEdge('D', 'G')
    digraph.addEdge('B', 'E')
    digraph.addEdge('E', 'G')
    digraph.addEdge('C', 'F')
    digraph.addEdge('G', 'H')
    digraph.addEdge('F', 'H')
    //产生环
    digraph.addEdge('D', 'E')
    digraph.addEdge('D', 'A')

    digraph.topologicalSort()
}