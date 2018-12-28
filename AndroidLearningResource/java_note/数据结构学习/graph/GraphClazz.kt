/**
 *  Created by jixiaoyong1995@gmail.com
 *  Data: 2018/12/25.
 *  Description:图
 *  由顶点 边 组成
 *  树有两种表示形式：
 *  1. 邻接矩阵
 *      一个n * n的数组。1-两个顶点邻接 ，0-两个顶点不邻接
 *      比如，m和n邻接，则需要更新arr[m][n]和arr[n][m]为1
 *  2. 领接表
 *      一个保存有n个链表的数组，每个链表保存和这些点邻接的顶点
 *
 *  两种搜索方式：
 *  1. 深度优先搜索（DFS）
 *      用栈实现，会先往深处遍历完一条路径，再遍历下一条。每个顶点只访问一次
 *      规则：
 *      1/3 访问一个邻接的未访问顶点，访问并标记，将其压入栈中；
 *      2/3 当规则1不能满足时，如果栈不为空，从栈中弹出一个顶点；
 *      3/3 1,2都无法满足时，搜索结束；
 *  2. 广度优先搜索（BFS）
 *      用队列实现，会先遍历完本层所有的顶点，然后再移向下一层
 *      规则：
 *      1/3 先访问当前顶点的所有邻接顶点，标记，并插入到队列
 *      2/3 如果没有可以访问的邻接点，且队列不为空，从队列头取出一个顶点[此处又用到了一次该点]，使其成为当前顶点，重复1
 *      3/3 如果2不能满足，搜索结束
 *
 *
 *  # 最小生成树MST
 *  > 生成树（Template:Lang-en-short）是具有 G 的全部顶点，但边数最少的连通子图.
 *
 *  带权图的生成树中，总权重最小的称为最小生成树。最小生成树边比顶点树小1
 *  当图的每一条边的权值都相同时，该图的所有生成树都是最小生成树。
 *  如果图的每一条边的权值都互不相同，那么最小生成树将只有一个。
 *
 *  无向不带权图中，只需要找出最小数量的边即可。用DFS比较好实现
 *
 *
 *  # 拓扑排序
 */
class GraphClazz(val maxSize: Int) {

    private var hashMap = hashMapOf<Int, GraphicNode>()

    fun insert(key: Int) {
        if (!hashMap.containsKey(key)) {
            hashMap[key] = GraphicNode(key)
        }
    }

    fun addEdge(start: Int, end: Int) {
        if (hashMap.containsKey(start) && hashMap.containsKey(end)) {
            var sindex = hashMap[start]
            var eindex = hashMap[end]

            while (sindex?.next != null) {
                sindex = sindex.next
            }
            sindex!!.next = GraphicNode(hashMap[end]!!.data)
            while (eindex?.next != null) {
                eindex = eindex.next
            }
            eindex!!.next = GraphicNode(hashMap[start]!!.data)
        }
    }

    fun display(type: Int) {
        if (hashMap.size == 0) {
            return
        }
        when (type) {
            SEARCH_TYPE_DFS -> dfs()
            SEARCH_TYPE_BFS -> bfs()
        }
    }

    private fun dfs() {
        var stacks = DfsStacks(hashMap.size)
        var keyArr = hashMap.keys.toIntArray()
        stacks.push(keyArr[0])
        hashMap[keyArr[0]]?.isVisited = true
        var index = hashMap[keyArr[0]]
        while (stacks.size > 0) {
            var availableKey = getAvailableNode(index!!.data)
            if (availableKey != -1) {
                index = hashMap[availableKey]//深度优先搜索，会先顺着一个邻接点一直走到头
                index!!.isVisited = true
                stacks.push(availableKey)
            } else {
                var pop = stacks.pop()
                print("$pop ")
                index = hashMap[stacks.peek()]//如果一个邻接点再没有未访问的邻接点，那么去访问下一个未访问的邻接点
            }
        }
    }

    private fun bfs() {
        var queue = BfsQueue()
        var keyArr = hashMap.keys.toIntArray()
        queue.push(keyArr[0])
        hashMap[keyArr[0]]!!.isVisited = true
        var index = hashMap[keyArr[0]]
        while (queue.size > 0) {
            var availableKey = getAvailableNode(index!!.data)
            if (availableKey != -1) {
                var current = hashMap[availableKey]!!//广度优先搜索，优先将一个节点的所有邻接点依次访问
                current.isVisited = true
                queue.push(current.data)
            } else {
                var pop = queue.pop()
                print("$pop ")
                if (queue.peek() == -1) {
                    break
                }
                index = hashMap[queue.peek()]//如果该点没有未访问的邻接点，就选择去访问该点邻接点的邻接点
            }
        }
    }

    /**
     * 查找某个点未访问的邻接点
     */
    private fun getAvailableNode(key: Int): Int {
        var index = hashMap[key]
        while (index != null) {
            if (!hashMap[index.data]!!.isVisited) {
                return index.data
            }
            index = index.next
        }
        return -1
    }

    /**
     * 清除访问标记
     */
    fun resetVisit() {
        hashMap.keys.map {
            hashMap[it]?.isVisited = false
        }
    }

    companion object {
        const val SEARCH_TYPE_DFS = 0
        const val SEARCH_TYPE_BFS = 1
    }
}

class GraphicNode(var data: Int, var isVisited: Boolean = false, var next: GraphicNode? = null) {
    override fun toString(): String {
        return "[$data]"
    }
}

class DfsStacks(private val maxSize: Int) {

    private val dataArr = IntArray(maxSize)
    var size = 0
        private set

    fun push(key: Int) {
        if (dataArr.contains(key)) {
            return
        }
        if (size < maxSize) {
            dataArr[size++] = key
        }
    }

    fun pop(): Int {
        if (size > 0) {
            return dataArr[--size]
        }
        return -1
    }

    fun peek(): Int {
        if (size > 0) {
            return dataArr[size - 1]
        }
        return -1
    }
}

class BfsQueue {
    private var head: GraphicNode? = null
    private var foot: GraphicNode? = null
    var size = 0
        private set

    fun push(key: Int) {
        if (head == null) {
            head = GraphicNode(key)
            foot = head
        } else {
            foot!!.next = GraphicNode(key)
            foot = foot!!.next
        }
        size++
    }

    fun pop(): Int {
        if (head == null) {
            return -1
        }
        var result = head!!.data
        head = head!!.next
        size--
        return result
    }

    fun peek(): Int {
        return head?.data ?: -1
    }

}

fun main(args: Array<String>) {
    var testArr = arrayOf(20, 323, 12, 43, 1,33)
    var graphClazz = GraphClazz(testArr.size)
    testArr.map {
        graphClazz.insert(it)
    }
    graphClazz.addEdge(20, 323)
    graphClazz.addEdge(20, 1)
    graphClazz.addEdge(323, 1)
    graphClazz.addEdge(323, 12)
    graphClazz.addEdge(43, 12)
    graphClazz.addEdge(33, 1)

    println("BFS:")
    graphClazz.display(GraphClazz.SEARCH_TYPE_BFS)
    println("\nDFS:")
    graphClazz.resetVisit()
    graphClazz.display(GraphClazz.SEARCH_TYPE_DFS)
}