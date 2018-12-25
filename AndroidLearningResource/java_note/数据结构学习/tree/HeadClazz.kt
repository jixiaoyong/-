/**
 *  Created by jixiaoyong1995@gmail.com
 *  Data: 2018/12/25.
 *  Description:
 *  # 堆
 *  是一种优先级队列
 *  是一个父节点比子节点关键值大的二叉树，用数组实现
 *  Me X Father (X-1)/2 sonLeft 2X+1 sonRight 2X+2
 *
 *  两个正确的子堆形成一个正确的堆
 *
 *  # 堆排序
 *  按照堆的特性（以下设定堆关键值大的优先级最高——在树的顶层）：先依次插入一组数，然后再依次推出根元素，就得到了一个有序数组
 *  一般的实现方式需要有数组来存储待排序数组和排序数组
 *
 *  **只用一个数组的堆排序：** （假设堆根节点关键值最大）
 *  每次插入时候只保存数据，不进行向上遍历
 *  每次移除数据时进行向下遍历，将当前剩余数据最大值选出来（其余数据仍然无序）从堆中移除根元素时都会在数组末尾空出一个位置，
 *  将该值存储在该位置即可，这样等完全插入、移除后就得到一个有序数组【从数组末尾开始依次减小】
 */
class HeadClazz(private val maxSize: Int) {
    private var headArray = arrayOfNulls<HeadNode>(maxSize)
    var size = 0
        private set

    fun insert(key: Int) {
        if (size < maxSize) {
            val bottom = HeadNode(key)
            headArray[size] = bottom
            checkUp(size++)
        }
    }

    fun pop(): HeadNode? {
        if (size > 0) {
            val top = headArray[0]
            checkDown(0)
            size--
            return top
        }
        return null
    }

    /**
     * 从上向下遍历
     * 如果遇到比当前值top大的就将其复制到当前位置toIndex，并记录下空出的位置为toIndex
     * 再以toIndex为起点向下比较，直到遇到top比父节点小，比子节点大的位置，或者叶子节点
     * 将top移动到该位置
     */
    private fun checkDown(index: Int) {
        var toIndex = index
        var top = headArray[size - 1]!!

        while (toIndex < size / 2) {//非叶子节点
            var leftIndex = 2 * toIndex + 1
            var rightIndex = 2 * toIndex + 2
            if (headArray[rightIndex] == null) {

                if (headArray[leftIndex]!!.key > top.key) {
                    headArray[toIndex] = headArray[leftIndex]
                    toIndex = leftIndex
                } else {
                    break
                }

            } else if (headArray[leftIndex] != null && headArray[rightIndex] != null) {
                if (headArray[leftIndex]!!.key >= headArray[rightIndex]!!.key) {
                    if (headArray[leftIndex]!!.key > top.key) {
                        headArray[toIndex] = headArray[leftIndex]
                        toIndex = leftIndex
                    } else {
                        break
                    }
                } else {
                    if (headArray[rightIndex]!!.key > top.key) {
                        headArray[toIndex] = headArray[rightIndex]
                        toIndex = rightIndex
                    } else {
                        break
                    }
                }
            }
        }

        headArray[toIndex] = top

    }

    /**
     * 从下向上遍历
     * 如果父节点比插入值小，就将父节点移动到插入值的位置，将toIndex指向空出的地方
     * 依次查找，直到查找到父节点比插入值大，子节点比插入值小的地方，或者指向了根节点
     */
    private fun checkUp(index: Int) {
        var bottom = headArray[index]
        var toIndex = index
        var father = (toIndex - 1) / 2
        while (toIndex > 0 && bottom!!.key > headArray[father]!!.key) {
            headArray[toIndex] = headArray[father]
            toIndex = father
            father = (toIndex - 1) / 2
        }
        headArray[toIndex] = bottom
    }

    fun display() {
        println(headArray.contentToString())
    }

}

class HeadNode(var key: Int) {
    override fun toString(): String {
        return "[$key]"
    }
}

fun main(args: Array<String>) {

    var head = HeadClazz(20)
    var array = arrayOf(12, 453, 21, 5, 32, 232, 53, 3, 313, 943)
    print("array: ${array.contentToString()}\n")
    array.toSet().map { head.insert(it) }
    head.display()
    for (x in 0 until array.size) {
        print("${head.pop()} ")
    }
}
