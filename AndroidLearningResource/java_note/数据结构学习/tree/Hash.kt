package shujujiegou.tree

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: https://jixiaoyong.github.io
 * date: 2018-12-23
 * description: 哈希表
 * 将数据保存在通过hash函数计算得到的下标中，查找很快，但是如果存储到接近hash表容量时就会变得很慢
 * hash表存储的方式有两种：
 *  1. 开放地址法
 *      开放地址法，当数据无法直接保存在hash计算的地址中（已经被占用）时，通过一段算法找到另外一个空位并保存
 *      开放地址法寻找下一个空位的算法有三种：线性探测，二次探测和再hash法
 *      * 线性探测
 *      线性的依次查找下一个空位，比如hash后地址是1，但已经被占用了，那就依次找2，3，4……直到找到一个空位
 *      存储达到容量2/3以上时候读写性能会很差
 *      * 二次探测
 *      每次查找的步长是步数的平方
 *      比如第一次在x+1的地方找，那么下一次就是x+4,x+9……这样当前几次找不到之后就会很恐慌，步长越来越大到后面无法继续下去
 *      不是很常用
 *      * 再hash法
 *      将关键字用不同的hash算法再做一次hash，用这个作为这个关键字的步长，开始寻找空位
 *      对于这个hash算法要求：
 *          1. 与第一次hash输出不同  2. 不能输出0
 *       已经有一个比较好的二次hash函数：
 *              //stepSize = constant * (key % constant)
 *              stepSize = constant - (key % constant)
 *              如：stepSize = 5 - (key % 5)
 *              * constant 是小于数组容量的质数
 *
 *      小型hash表中，再hash法比二次探测好；但如果容量充足，并且容量大小不再变化时，二次探测效果好，在装填因子小于0.5时几乎没有性能损失
 *  2. 链地址法
 *      链地址法，创建保存数据的数组，该数组中不直接保存数据，而是保存一个用来存储这些数据的链表，将数据项直接存储的链表中
 *
 *  hash表容器大小未知时，用链地址法比较好
 *  当装填因子变得很大时，开放地址法性能下降很快，但链地址法只是线性下降
 *
 */

/**
 * 开放地址法的hash表
 */
class ChangeStepSizeHash(var size: Int) {
    private var hashArr = arrayOfNulls<DataItem>(size)
    private var deletedItem = DataItem(-1)

    fun insert(dataItem: DataItem) {
        var index = hashFunc(dataItem.key)
        if (hashArr[index] == null) {
            hashArr[index] = dataItem
        } else {
            if (hashArr[index]!!.key == dataItem.key) {
                hashArr[index] = dataItem
                return
            }
            index += stepSize(dataItem.key)
            while (index < size) {
                if (hashArr[index] == null || hashArr[index]!!.key == -1) {
                    hashArr[index] = dataItem
                    return
                }
                index += stepSize(dataItem.key)
            }
        }
    }

    fun delete(key: Int): DataItem? {
        var index = findIndex(key)
        if (index != -1) {
            var t = hashArr[index]
            hashArr[index] = deletedItem
            return t
        }
        return null
    }

    fun findData(key: Int): DataItem? {
        var index = findIndex(key)
        if (index == -1) {
            return null
        }
        return hashArr[index]
    }

    fun findIndex(key: Int): Int {
        var index = hashFunc(key)
        if (hashArr[index] == null) {
            return -1
        } else {
            if (hashArr[index]!!.key == key) {
                return index
            }
            index += stepSize(key)
            while (index < size) {
                if (hashArr[index] == null) {
                    return -1
                } else if (hashArr[index]!!.key == key) {
                    return index
                }
                index += stepSize(key)
            }
            return -1
        }
    }

    fun hashFunc(key: Int): Int {
        return key % size
    }

    fun display(): String {
        var sb = StringBuilder()
        hashArr.map {
            if (it != null && it.key != -1) {
                sb.append("$it ")
            }
        }
        return sb.toString()
    }

    fun stepSize(key: Int): Int {
        //线性探测
        //return 1
        //二次探测 num是查找的次数
        //return num*num
        //再hash法
        return secondHashFunc(key)
    }

    fun secondHashFunc(key: Int): Int {
        return 5 - (key % 5)
    }
}

/**
 * 链地址法
 */
class LinkHash(var size: Int) {
    private var hashArr = arrayOfNulls<HashLinkNode>(size)

    fun insert(dataItem: DataItem) {
        var index = hashFunc(dataItem.key)
        if (hashArr[index] == null) {
            hashArr[index] = HashLinkNode(dataItem)
        } else {
            var hashLink = hashArr[index]!!.next
            var parent = hashArr[index]
            while (hashLink != null && hashLink!!.dataItem.key != -1) {
                parent = hashLink
                hashLink = hashLink.next
            }
            parent!!.next = HashLinkNode(dataItem)
        }
    }

    fun find(key: Int): DataItem? {
        var index = hashFunc(key)
        if (hashArr[index] == null) {
            return null
        } else {
            if (hashArr[index]!!.dataItem.key == key) {
                return hashArr[index]!!.dataItem
            }

            var hashLink = hashArr[index]!!.next
            while (hashLink != null) {
                if (hashLink.dataItem.key == key) {
                    return hashLink.dataItem
                }
                hashLink = hashLink.next
            }
            return null
        }
    }

    fun delete(key: Int): DataItem? {
        var index = hashFunc(key)
        if (hashArr[index] == null) {
            return null
        } else {
            if (hashArr[index]!!.dataItem.key == key) {
                var t = hashArr[index]!!.dataItem
                hashArr[index]!!.dataItem.key = -1
                return t
            }

            var hashLink = hashArr[index]!!.next
            while (hashLink != null) {
                if (hashLink.dataItem.key == key) {
                    var t = hashArr[index]!!.dataItem
                    hashArr[index]!!.dataItem.key = -1
                    return t
                }
                hashLink = hashLink.next
            }
            return null
        }
    }

    private fun hashFunc(key: Int): Int {
        return key % size
    }

    fun display(): String {
        var sb = StringBuilder()
        hashArr.map {
            if (it != null && it.dataItem.key != -1) {
                var next = it
                do {
                    sb.append(" ${next!!.dataItem}")
                    next = next?.next
                } while (next != null && next.dataItem.key != -1)
            }
        }
        return sb.toString()
    }
}

class HashLinkNode(var dataItem: DataItem, var next: HashLinkNode? = null)

class DataItem(var key: Int, var data: String? = "") {
    override fun toString(): String {
        return "[$key,$data]"
    }
}


fun main(args: Array<String>) {
//    var lineHash = ChangeStepSizeHash(20)
    var lineHash = LinkHash(20)
    lineHash.insert(DataItem(2, "hedllo"))
    lineHash.insert(DataItem(1, "heallo"))
    lineHash.insert(DataItem(5, "helaslo"))
    lineHash.insert(DataItem(5, "hedllo"))
    lineHash.insert(DataItem(2, "helsdlo"))
    lineHash.insert(DataItem(43, "heladlo"))
    lineHash.insert(DataItem(2323, "hesdllo"))
    lineHash.insert(DataItem(24, "hesdllo"))
    lineHash.insert(DataItem(43, "hesdllo"))
    lineHash.insert(DataItem(332, "hesdllo"))
    lineHash.insert(DataItem(312, "hesdllo"))
    lineHash.insert(DataItem(23332, "hesdllo"))
    lineHash.insert(DataItem(3232, "hesdllo"))
    lineHash.insert(DataItem(46, "hesdllo"))
    lineHash.insert(DataItem(221, "hesdllo"))

    println("1:" + lineHash.display())
    lineHash.delete(5)
    println("2:" + lineHash.display())

//    println("find 43:" + lineHash.findData(43)?.data)
    println("find 43:" + lineHash.find(43)?.data)

}