package shujujiegou.sort

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2018-12-31
 * description: 简单排序
 * * 冒泡排序，比较n次，有大的则交换，每次选出最大的元素放到队尾
 */

object BasicSort {

    /**
     * 冒泡排序
     * 1. 每次比较前1~（n-i）个元素（i是排序次数），每次有大的就【移动】，
     * 这样子一轮比赛完毕最大的就在后面了
     * 2. 这样子比较n次就可以完成排序
     */
    fun bubbleSort(intArray: IntArray): IntArray {
        var result = intArray
        var size = result.size
        for (index in 0 until size) {
            for (x in 0 until size - index - 1) {
                if (result[x] > result[x + 1]) {
                    var temp = result[x]
                    result[x] = result[x + 1]
                    result[x + 1] = temp
                }
            }
        }
        return result
    }

    /**
     * 选择排序
     * 1. 每次比较前1~（n-i）个元素（i是排序次数），如果有大的就记录下位置，一轮比较完毕后交换他和最后一位的位置
     * 2. 这样子比较n次就可以完成排序
     */
    fun selectSort(intArray: IntArray): IntArray {
        var result = intArray
        var size = result.size
        for (index in 0 until size) {
            var max = 0//假设arr[0]最大
            for (x in 0 until size - index - 1) {
                if (result[max] < result[x + 1]) {//将max与每一项比较，注意这里参与比较的是max
                    max = x + 1//遇到比max大的则记录下其位置
                }
            }
            //在每轮比较完毕后max就是这轮比较出来的最大值位置，将其放到对应位置
            var temp = result[size - index - 1]
            result[size - index - 1] = result[max]
            result[max] = temp
        }
        return result
    }

    /**
     * 插入排序
     * 假设右端数组是有序的，依次从左端数组取出元素比较，插入到右边的有序数组
     */
    fun insertSort(intArray: IntArray): IntArray {

        var result = intArray
        var size = result.size

        for (insertIndex in 1 until size ) {//假设arr[0]已经是有序的，所以从1开始
            var insertPoint = result[insertIndex]
            for (index in insertIndex - 1 downTo  0) {
                if (insertPoint < result[index]) {//默认要插入的点是有序的，如果有比插入点大的，则将该点和插入点交换
                    result[index + 1] = result[index]
                    result[index] = insertPoint
                } else {//因为左边的数组是有序的，只要有比插入点小的元素，则剩下的肯定都小于该元素，不用再比较了
                    break
                }
            }
        }

        return result
    }

}

fun main(args: Array<String>) {
    var array = intArrayOf(0, 12, 2, 434, 21, 32, 4, 5, 1, 1)
//    println("bubbleSort:\n${BasicSort.bubbleSort(array).contentToString()}")
//    println("selectSort:\n${BasicSort.selectSort(array).contentToString()}")
    println("insertSort:\n${BasicSort.insertSort(array).contentToString()}")
}