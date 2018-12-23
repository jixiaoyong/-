package shujujiegou.tree

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: www.jixiaoyong.github.io
 * date: 2018-12-23
 * description: 2-3-4树
 *  2-3-4树
 *  每个节点可以有1，2，3个数据，对应分别有2，3，4个子节点。
 *
 *  不存在空节点——即没有数据项的情况，不允许只有一个子节点的情况，子节点数只能是2，3，4.
 *  同一级节点大小分布与二叉树相同
 *  此外，  数据项：   [0]  [1]  [2]
 *     子节点链接：  ↑    ↑   ↑    ↑
 *                0    1   2    3
 *
 *  一个节点的子节点关系：
 *              节点0所有数据 < 数据项[0] < 节点1所有数据
 *              节点1所有数据 < 数据项[1] < 节点2所有数据
 *                  …………
 *  所有叶节点在同一层
 */
class TwoThreeFourTree{

}