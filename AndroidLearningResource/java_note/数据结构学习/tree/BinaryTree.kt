package shujujiegou.tree

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: www.jixiaoyong.github.io
 * date: 2018/12/18
 * description: todo
 */

/**
 * 二叉搜索树
 * 最大值在最右端，最小值在最左端
 */
class BinaryTree {

    var root: BinaryNode? = null

    fun find(iId: Int): BinaryNode? {

        var current = root
        while (current?.iId != iId) {
            if (current == null) {
                return null
            }

            current = if (iId > current.iId) {
                current.right
            } else {
                current.left
            }
        }
        return current
    }

    fun insert(iId: Int, dData: Double) {
        var aNode = BinaryNode(iId, dData)

        if (root == null) {
            root = aNode
            return
        }

        var current = root
        var parent: BinaryNode = current!!
        while (current?.iId != iId) {
            if (current == null) {
                break
            }
            parent = current
            current = if (iId > current.iId) {
                current.right
            } else {
                current.left
            }
        }

        if (iId > parent.iId) {
            parent.right = aNode
        } else {
            parent.left = aNode
        }
    }

    fun delete(iId: Int) {
        //查找iId对应的节点
        var current = root
        var parent = root
        while (current?.iId != iId) {
            if (current == null) {
                return
            }
            parent = current
            current = if (iId > current.iId) {
                current.right
            } else {
                current.left
            }
        }
        if (parent == null) {
            return
        }

        /**
         * 按照要删除的节点子节点数目的不同，分为3种情况
         * 1/3 要删除的节点是叶节点 将其父节点的指向设为null即可
         * 2/3 要删除的节点有且只有一个节点 将其父节点指向其子节点
         * 3/3 要删除的节点有两个子节点 这时候可以找该子节点的右子树中最小的（或者左子树中最大的）节点并替换掉要删除的节点，
         *     与此同时如果这个节点有右子节点（或对应的左子节点）则按照2/3的规则处理，这样就能保证这个树的结构不会出错
         *     下面采用的是找该节点的右子树最小值，即右子节点或者右子节点的最后一个左子节点
         *     找到后用该子节点的值替换掉要删除的节点值，如果该子节点还有右子节点，将该子节点的父节点指向其右子节点
         */
        if (current.left != null && current.right != null) {
            //双子节点

            // 当前点右子节点的左子节点为null
            if (current.right!!.left == null) {
                if (current.iId > parent.iId) {
                    parent.right = current.right
                } else {
                    parent.left = current.right
                }
                return
            }

            var cChildNode = current.right
            var cParentNode = current!!

            while (cChildNode?.left != null) {
                cParentNode = cChildNode
                cChildNode = cChildNode.left
            }

            //后继节点
            cParentNode.left = cChildNode!!.right
            cChildNode!!.right = current.right
            cChildNode!!.left = current.left
//            current = cChildNode
            if (current.iId > parent.iId) {
                parent.right = cChildNode
            } else {
                parent.left = cChildNode
            }

        } else if (current.left == null && current.right == null) {
            //叶子节点
            if (current.iId > parent.iId) {
                parent.right = null
            } else {
                parent.left = null
            }
        } else if (current.left == null) {
            if (current.iId > parent.iId) {
                parent.right = current.right
            } else {
                parent.left = current.right
            }
        } else if (current.right == null) {
            if (current.iId > parent.iId) {
                parent.right = current.left
            } else {
                parent.left = current.left
            }
        }

    }

    companion object {
        /**
         * 中序遍历法
         * 使所有节点的关键值按照升序被访问
         */
        fun inTraversing(node: BinaryNode?) {
            if (node == null) {
                return
            }
            inTraversing(node.left)
            print("$node,")
            inTraversing(node.right)
        }

        /**
         * 前序遍历法
         * 按照树的样子依次打印
         */
        fun beforeTraversing(node: BinaryNode?) {
            if (node == null) {
                return
            }
            print("$node,")
            beforeTraversing(node.left)
            beforeTraversing(node.right)
        }

        /**
         * 后序遍历法
         * 将树倒过来
         */
        fun afterTraversing(node: BinaryNode?) {
            if (node == null) {
                return
            }
            afterTraversing(node.left)
            afterTraversing(node.right)
            print("$node,")
        }
    }
}

class BinaryNode(val iId: Int, val dData: Double, var left: BinaryNode? = null, var right: BinaryNode? = null) {
    override fun toString(): String {
        return "{$iId,$dData}"
    }
}

fun main(args: Array<String>) {
    var binaryTree = BinaryTree()
    binaryTree.insert(1, 1.1)
    binaryTree.insert(9, 1.9)
    binaryTree.insert(2, 1.2)
    BinaryTree.inTraversing(binaryTree.root)
    binaryTree.insert(8, 1.8)
    binaryTree.insert(3, 1.3)
    binaryTree.insert(6, 1.6)
    binaryTree.insert(20, 1.6)
    binaryTree.insert(18, 1.6)
    binaryTree.insert(40, 1.6)
    binaryTree.insert(25, 1.6)
    binaryTree.insert(26, 1.6)
    println()
    BinaryTree.afterTraversing(binaryTree.root)

    binaryTree.delete(20)
    println("\n***-20****")
    BinaryTree.afterTraversing(binaryTree.root)

}