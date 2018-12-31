package shujujiegou.sort

import shujujiegou.sort.SortUtils.quickArray
import shujujiegou.sort.SortUtils.quickSort1

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2018/12/16
 * description: 高级排序：希尔排序，快速排序，基数排序
 */
object SortUtils {

    /**
     * 希尔排序
     * 思路:
     * 1/3 将待排序数组分为h个间隔为h的小数组，
     * 2/3 对这些小数组进行插入排序,将排序结果写入原待排序数组
     * 3/3 按照 h=3*h+1 的算法减小h在此进行希尔排序，直至h为1
     * --将大数组分为较小的数组，拍完序后再对这些"有序"的小数组进行排序
     * 小 - > 大
     */
    fun shellSort(intArray: IntArray, h: Int): IntArray {
        if (h == 1) {
            return insertSort(intArray)
        } else {
            //间隔排序
            for (x in 0 until h) {

                var list = arrayListOf<Int>()
                intArray.forEachIndexed { index, i ->
                    if ((index + x) % h == 0) {
                        list.add(i)
                    }
                }
                var partSortArr = insertSort(list.toIntArray())
                var listIndex = 0
                intArray.forEachIndexed { index, i ->
                    if ((index + x) % h == 0) {
                        intArray[index] = partSortArr[listIndex++]
                    }
                }
            }

            return shellSort(intArray, (h - 1) / 3)
        }
    }

    /**
     * 获取希尔排序间隔
     * 对排序速度影响较大，要求互质，计算方式不唯一
     */
    fun getShellSortH(range: Int): Int {
        if (range < 5) {
            return 1
        }
        var h = 1
        while (h < range) {
            h = 3 * h + 1
        }
        return (h - 1) / 3
    }


    /**
     * 插入排序
     * 思路：
     * 1/2 先假设第一个数是已经排好序的
     * 2/2 将后面的数字依次与其比较，并插入到对应位置
     * small -> big
     */
    fun insertSort(intArray: IntArray): IntArray {

        for (i in 1 until intArray.size) {
            for (j in 0 until i) {
                if (intArray[i] < intArray[j]) {
                    val temp = intArray[i]
                    for (x in i downTo j) {
                        if (x - 1 < 0) {
                            break
                        }
                        intArray[x] = intArray[x - 1]
                    }
                    intArray[j] = temp
                }
            }
        }

        return intArray
    }

    /**
     * 划分算法
     * 提出一个阈值，并以此将数组划分为两部分
     * 左边都小于枢纽，右边都大于枢纽
     * @param array 待划分数组
     * @param n 枢纽
     */
    fun devideArrayByN(array: IntArray, n: Int): IntArray {

        var leftIndex = -1
        var rightIndex = array.size

        while (leftIndex < rightIndex) {
            while (leftIndex < rightIndex && array[++leftIndex] < n) {
            }
            while (leftIndex < rightIndex && array[--rightIndex] > n) {
            }
            val temp = array[leftIndex]
            array[leftIndex] = array[rightIndex]
            array[rightIndex] = temp
        }

        println("Left:$leftIndex Right:$rightIndex")
        return array
    }

    /**
     * 快速排序所用的数组，使用前先初始化
     */
    lateinit var quickArray: IntArray

    /**
     * 快速排序算法
     * #1 选择数组最右端元素作为枢纽
     * 思想是
     * 1/2 选出一个枢纽，先将其按大小划分为左右两部分
     * 2/2 在划分好的两个数组中，分别再找一个枢纽，重复步骤1
     */
    fun quickSort1(left: Int, right: Int) {
        if (right - left <= 0) {
            return
        }
        /**
         * n 这个枢纽的取法很关键，决定了算法的速度
         * TODO 除过这里用到的取法之外，还可以有"三数据项取中"法，在数组首、尾、中取数排序，选中间的数作为枢纽。这样排序数组要>3
         * 对于这些小于3的数组可以用插入排序法进行排序
         */
        val n = quickArray[right]
        val nIndex = devideArrayByN1(left, right, n)
        if (nIndex > 0) {
            quickSort1(left, nIndex - 1)
        }
        quickSort1(nIndex + 1, right)
    }

    /**
     * 划分算法决定了排序的准确性
     */
    private fun devideArrayByN1(left: Int, right: Int, n: Int): Int {
        var leftIndex = left - 1
        var rightIndex = right

        while (leftIndex < rightIndex) {
            while (leftIndex < rightIndex && quickArray[++leftIndex] < n) {
            }
            while (leftIndex < rightIndex && quickArray[--rightIndex] > n) {
            }
            val temp = quickArray[leftIndex]
            quickArray[leftIndex] = quickArray[rightIndex]
            quickArray[rightIndex] = temp
        }

        for (i in right downTo rightIndex) {
            if (i < 1) {
                break
            }
            quickArray[i] = quickArray[i - 1]
        }
        quickArray[rightIndex] = n
        return rightIndex
    }

}

fun main(args: Array<String>) {
    var intArray = intArrayOf(2, 23, 3, 55, 6, 786,43,5,3,435,32,54,64,23,213,5554, 3, 2)
    println("at begin:" + intArray.contentToString())
//    println(insertSort(intArray).contentToString())

//    var h = getShellSortH(intArray.size)
//    println(shellSort(intArray, h).contentToString())

//    println("30 devideArrayByN:" + devideArrayByN(intArray, 5).contentToString())


    quickArray = intArray
    quickSort1(0, quickArray.size - 1)
    println(quickArray.contentToString())
}