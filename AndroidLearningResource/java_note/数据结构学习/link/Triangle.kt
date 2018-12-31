package shujujiegou.link

import shujujiegou.link.Triangle.fullThePackage

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2018/12/15
 * description: todo
 */

object Triangle {

    /**
     * 三角数
     * 第n个数 == 第n-1个数 + n
     */
    fun triangleNum(num: Int): Int {
        if (num == 1) {
            return num
        }
        return num + triangleNum(num - 1)
    }

    /**
     * 递归
     * 第n个数 == 第n-1个数 * n
     */
    fun nStepMutliNum(num: Int): Int {
        if (num == 1) {
            return num
        }
        return num * nStepMutliNum(num - 1)
    }

    /**
     * 变位字
     * 一个英文单词所有字符可能的组合
     * @param charArray 英文单词字符列表
     * @param str 前面已经排列好的字符串
     */
    fun doRotateWord(charArray: CharArray, str: String = "") {
        var string = ""
        if (charArray.size == 1) {
            print(str + charArray[0] + " ")
        } else {
            charArray.forEachIndexed { index1, it ->
                string = str + it
                var newArr = CharArray(charArray.size - 1)
                var i = 0
                charArray.forEachIndexed { index2, it2 ->
                    if (index1 != index2) {
                        newArr[i++] = it2
                    }
                }
                doRotateWord(newArr, string)
            }
        }
    }

    /**
     * 汉诺塔问题
     * 将汉诺塔问题简化为3步：
     * 1/3 将最上层n-1项移动到过渡层
     * 2/3 将最底层n移动到目标层
     * 3/3 将n-1项移动到目标层
     * @param num 要移动的层数
     * @param from 所在层
     * @param inter 过渡层
     * @param to 目标层
     */
    var hanioStepNum = 0

    fun hanioTower(num: Int, from: String, inter: String, to: String) {
        if (num == 1) {
            println("move 1 to $to")
            hanioStepNum++
        } else {
            hanioTower(num - 1, from, to, inter)
            println("move $num to $to")
            hanioStepNum++
            hanioTower(num - 1, inter, from, to)
        }
    }

    /**
     * 归并排序
     * 归并排序占空间（多占一个排序数组的大小），排序快（N*LogN）
     * 思想是:
     * 1/2 将数组无限分成两份，直到两份数组都是有序的（每个数组只有一个元素）
     * 2/2 再对其进行归并
     * 小 -> 大
     * @param intArr 待排序的数组
     */
    fun mergeSort(intArr: IntArray): IntArray {
        var size = intArr.size
        if (size == 1) {
            return intArr
        } else {
            var half = 0
            if (size % 2 == 0) {
                half = size / 2
            } else {
                half = (size + 1) / 2
            }
            var arr1 = intArr.copyOfRange(0, half)
            val arr2 = intArr.copyOfRange(half, intArr.size)
            return merge(mergeSort(arr1), mergeSort(arr2))
        }
    }

    /**
     * 归并
     * 合并两个有序的数组为新的有序数组
     * 思想：
     * 1/2 相互比较两个数组每项大小，并将小的复制到新数组
     * 2/2 将剩余的数组全部复制到新数组
     * 小 -> 大
     * @param intArrA 有序数组1
     * @param intArrB 有序数组2
     */
    fun merge(intArrA: IntArray, intArrB: IntArray): IntArray {
        var resultArr = IntArray(intArrA.size + intArrB.size)

        var indexA = 0
        var indexB = 0
        var indexC = 0

        while (indexA < intArrA.size && indexB < intArrB.size) {
            if (intArrA[indexA] < intArrB[indexB]) {
                resultArr[indexC++] = intArrA[indexA++]
            } else {
                resultArr[indexC++] = intArrB[indexB++]
            }
        }

        while (indexA < intArrA.size) {
            resultArr[indexC++] = intArrA[indexA++]
        }
        while (indexB < intArrB.size) {
            resultArr[indexC++] = intArrB[indexB++]
        }

        return resultArr
    }

    /**
     * 计算某数的乘方，归并排序思路
     */
    fun power(num: Long, power: Int): Long {
        if (power == 0) {
            return 1
        } else if (power == 1) {
            return num
        } else {
            var half: Int
            if (power % 2 == 0) {
                half = power / 2
            } else {
                half = (power + 1) / 2
            }
            return power(num, half) * power(num, power - half)
        }
    }

    /**
     * 背包问题
     * 一批大小各异的物资，选择一组恰好达到背包承重量n的组合
     * 思路：
     * 1/2 先选出一个物资i，在剩下的物资中选择满足重量为n-i的物资
     * 2/2 没有找到的话重复该过程，直到找到或遍历完所有组合
     * @param array 剩余的物资
     * @param weight 要达到的重量
     * @param str 已经选择的物资
     */
    fun fullThePackage(array: IntArray, weight: Int, str: String) {

        if (array.size == 0) {
            return
        }

        for (i in 0 until array.size) {
            var nextStr = str

            if (array[i] == weight) {
                println("******end:******$nextStr [${array[i]}]")
                System.exit(0)//只需要找出一个组合
                break
            }

            if (weight - array[i] < 0) {
                continue
            }
            nextStr = "$str [${array[i]}]"
            fullThePackage(copyArrayExclusiveIndex(array, i), weight - array[i], nextStr)
        }
    }

    fun copyArrayExclusiveIndex(array: IntArray, index: Int): IntArray {
        if (array.size == 0) {
            return array
        }

        var newArray = IntArray(array.size - 1)
        var point = 0
        array.mapIndexed { i, it ->
            if (index != i) {
                newArray[point++] = it
            }
        }
        return newArray
    }

}

fun main(args: Array<String>) {

//    for (i in 1..10) {
//        print(" " + Triangle.nStepMutliNum(i))
//    }

//    doRotateWord("cat".toCharArray())
//    Triangle.hanioStepNum == 0
//    hanioTower(64, "A", "B", "C")
//    println("********hanioStepNum is $hanioStepNum*******")

//    var intArr = intArrayOf(6, 23, 1, 0, 34, 32334, 323, 42, 2, 3)
//    mergeSort(intArr).map {
//        print("$it,")
//    }

//    println(power(2, 30))

    fullThePackage(intArrayOf(11, 8, 2, 4, 7, 6, 5), 20, "")

}