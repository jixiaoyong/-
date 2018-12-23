package shujujiegou.tree

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: www.jixiaoyong.github.io
 * date: 2018/12/22
 * description: 红黑树
 *              红黑树的四个规则：
 *              1/4 每个节点不是红色就是黑色
 *              2/4 根总是黑色
 *              3/4 若节点是红色，则子节点必须是黑色
 *              4/4 根到叶节点或者空子节点的每条路径都必须有相同数目的黑色节点
 *              * 空子节点 指 非叶节点但是可以接叶节点的位置，空子节点默认为黑色
 *
 *              修复方式：
 *              1/2 改变节点颜色：该节点变色，其子节点变色
 *              2/2 旋转：左旋或右旋，
 *
 *              修复时机：
 *              插入情况分为
 *              1. 插入的为根节点 -> ✅
 *              2. 父节点为根 -> ✅
 *              3. 父节点为黑色 -> ✅
 *              4. 父节点 红色；叔节点 红色 -> ❌ 将父节点，叔节点涂黑，将祖父节点涂红，当前节点指向祖父
 *              5. 父节点 红色；叔节点 黑色 ； 为父节点的左节点 -> ❌ 父节点 涂黑 ；祖父节点 涂红，以祖父节点为支点，右旋，涂黑根节点
 *              【特殊】如果祖父节点没有左节点(内侧子孙节点)，则旋转两次：规则5，6翻转，需要对父先右旋，再以父为新插入的的点，判断得出需要祖左旋
 *              6. 父节点 红色；叔节点 黑色 ； 为父节点的右节点 -> ❌ 父节点为支点，左旋
 *
 *              删除情况分为：
 *              1. 可以不删除节点，而是给节点增加一个属性，标记是否被删除
 *              2. 实际删除节点：//TODO 红黑树删除
 *
 *
 *
 *              【注意】旋转
 *              以某个点为支点旋转（右旋为例，旋转时注意更新各个节点的父节点）：
 *              1/2 将该点a的右节点b放到b的右节点的位置，再将该点a放到a的右节点的位置，依次类推
 *              2/2 特殊的，将该点a的内侧孙子（a的左子节点c的右子节点d）断开与其父节点c的连接，转而连接到a上，成为a的左子节点
 *
 *              【资源】
 *              在线红黑树 https://sandbox.runjs.cn/show/2nngvn8w
 *              参考资料：http://www.cnblogs.com/skywang12345/p/3245399.html#a1
 *
 */
class RedBlackTree {

    private var root: RedBlackNode? = null

    fun insert(node: RedBlackNode) {
        if (root == null) {
            node.isRed = false
            root = node
            return
        }
        insert(root!!, node)
        checkRules(node)
    }

    /**
     * 检查规则要注意
     * 没有节点时可以当做黑色节点分析
     */
    private fun checkRules(node: RedBlackNode) {
        var parent = node.parent
        var grandParent = node.parent?.parent

        if (node.parent == null) {
            //root
            node.isRed = false
        }

        if (parent == null || !parent.isRed || grandParent == null) {
            // parent is black
            return
        }
        var uncleNode: RedBlackNode? = if (parent.id < grandParent.id) {
            grandParent.right
        } else {
            grandParent.left
        }

        if (uncleNode != null && uncleNode.isRed) {
            grandParent.right!!.isRed = false
            grandParent.left!!.isRed = false
            grandParent.isRed = true
            checkRules(grandParent)
        } else {

            if (node.id < parent.id) {
                //left
                if (grandParent.left == null) {
                    doSpecialRevolve(parent)
                } else {
                    parent.isRed = false
                    grandParent.isRed = true
                    revolveRight(grandParent)
                    checkRules(grandParent)
                }

            } else {
                //right
                revolveLeft(parent)
                checkRules(parent)
            }
        }
    }

    /**
     * 如果父红叔黑，为左，但是祖的左子节点为null
     * 将之前的规则5，6反过来
     * 就需要对父先右旋，再以父为新插入的的点，判断得出需要祖左旋
     * 32,3,53,13,983,[137],237,83,483,43,183
     */
    private fun doSpecialRevolve(node: RedBlackNode) {
        revolveRight(node)
        node.parent?.isRed=false
        node.parent?.parent?.isRed=true
        revolveLeft(node.parent!!.parent!!)
    }

    fun display() {
        display(root)
    }

    private fun display(node: RedBlackNode?) {
        if (node == null) {
            return
        }
        display(node.left)
        print(" " + node)
        display(node.right)
    }

    /**
     * 左旋,旋转时候注意更新父节点
     */
    private fun revolveLeft(node: RedBlackNode) {

        var right = node.right!!
        var rightSLeft = right.left
        var nodeSParent = node.parent

        right.left = node
        node.parent = right

        node.right = rightSLeft
        rightSLeft?.parent = node
        when {
            nodeSParent == null -> root = right
            node.id < nodeSParent.id -> nodeSParent.left = right
            else -> nodeSParent.right = right
        }

        right.parent = nodeSParent

    }

    /**
     * 右旋
     */
    private fun revolveRight(node: RedBlackNode) {
        var left = node.left!!
        var leftSRight = left.right
        var nodeSparent = node.parent

        node.left = left.right
        leftSRight?.parent = node

        left.right = node
        node.parent = left
        when {
            nodeSparent == null -> root = left
            node.id < nodeSparent.id -> nodeSparent.left = left
            else -> nodeSparent.right = left
        }
        left.parent = nodeSparent
    }

    private fun insert(parent: RedBlackNode, node: RedBlackNode) {
        if (node.id < parent.id) {
            if (parent.left == null) {
                node.parent = parent
                parent.left = node
            } else {
                insert(parent.left!!, node)
            }
        } else {
            if (parent.right == null) {
                node.parent = parent
                parent.right = node
            } else {
                insert(parent.right!!, node)
            }
        }
    }
}

class RedBlackNode(var id: Int, var isRed: Boolean = true, var left: RedBlackNode? = null
                   , var right: RedBlackNode? = null, var parent: RedBlackNode? = null) {
    override fun toString(): String {
        return "[$id,${if (isRed) "RED" else "BLACK"}]"
    }
}

fun main(args: Array<String>) {
    var rbTree = RedBlackTree()
//    rbTree.insert(RedBlackNode(32))
//    rbTree.insert(RedBlackNode(3))
//    rbTree.insert(RedBlackNode(53))
//    rbTree.insert(RedBlackNode(13))
//    rbTree.insert(RedBlackNode(983))
//    rbTree.insert(RedBlackNode(137))
//    rbTree.insert(RedBlackNode(237))
//    rbTree.insert(RedBlackNode(83))
//    rbTree.insert(RedBlackNode(483))
//    rbTree.insert(RedBlackNode(43))
//    rbTree.insert(RedBlackNode(183))
//    rbTree.insert(RedBlackNode(23))
//    rbTree.insert(RedBlackNode(1))
//    rbTree.insert(RedBlackNode(10))
//    rbTree.display()

    var arr = arrayOf(32,3,53,13,983,137,237,83,483,43,183)
    arr.map {
        rbTree.insert(RedBlackNode(it))
    }
    rbTree.display()
}

//32,3,53,13,983,137,237,83,483,43,183