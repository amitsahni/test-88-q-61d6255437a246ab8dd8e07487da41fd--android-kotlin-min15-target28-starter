package org.codejudge.application

import org.junit.Before
import org.junit.Test
import java.util.*


/**
 * Created by Amit Singh.
 * Tila
 * asingh@tila.com
 */

class Tree {


    data class Node(
            val key: Int,
            var leftNode: Node? = null,
            var rightNode: Node? = null
    )

    private lateinit var tree: Node

    @Before
    fun setUp() {
        //tree = Tree()
        tree = Node(1)
        tree.leftNode = Node(2)
        tree.rightNode = Node(3)
        tree.leftNode?.leftNode = Node(4)
        tree.leftNode?.rightNode = Node(5)
    }

    @Test
    fun printPostOrder() {
        println("printPostOrder")
        postOrder(tree)
        println()
    }

    @Test
    fun printInOrder() {
        println("printInOrder")
        InOrder(tree)
        println()
    }

    @Test
    fun printPreOrder() {
        println("printPreOrder")
        PreOrder(tree)
        println()
    }

    @Test
    fun printBFS() {
        println("printBFS")
        if (tree == null) return
        val queue = LinkedList<Node?>()
        queue.add(tree)
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            print(node?.key)
            if (node?.leftNode != null)
                queue.add(node.leftNode)
            if (node?.rightNode != null)
                queue.add(node.rightNode)
        }
        println()

        val list = mutableListOf<Node?>()
        list.add(tree)
        while (list.isNotEmpty()) {
            val node = list.first()
            print(node?.key)
            list.removeFirst()
            if (node?.leftNode != null)
                list.add(node?.leftNode)
            if (node?.rightNode != null)
                list.add(node.rightNode)
        }
        println()
    }

    @Test
    fun BST() {
        tree = Node(10)
        tree.leftNode = Node(5)
        tree.rightNode = Node(12)
        tree.leftNode?.leftNode = Node(-4)
        tree.leftNode?.rightNode = Node(7)
        println("BST")
        search(tree, 7)
    }

    @Test
    fun insertBST() {
        tree = Node(10)
        tree.leftNode = Node(5)
        tree.rightNode = Node(12)
        tree.leftNode?.leftNode = Node(-4)
        tree.leftNode?.rightNode = Node(7)
        println("insertBST")
        insert(tree, 6)
    }

    @Test
    fun typeCasting(){
        val child = Child()
        child.show()
        child.eat()

        val parent : Parent = Child()
        parent.show()
        parent.eat()
    }

    private fun postOrder(node: Node?) {
        if (node == null) return
        postOrder(node.leftNode)
        postOrder(node.rightNode)
        print(node.key)
    }

    private fun InOrder(node: Node?) {
        if (node == null) return
        InOrder(node.leftNode)
        print(node.key)
        InOrder(node.rightNode)
    }

    private fun PreOrder(node: Node?) {
        if (node == null) return
        print(node.key)
        PreOrder(node.leftNode)
        PreOrder(node.rightNode)
    }

    private fun search(node: Node?, key: Int) {
        if (node == null) {
            print("Not Found")
            return
        }
        when {
            node.key == key -> {
                print("$key Found")
            }
            node.key < key -> {
                return search(node.rightNode, key)
            }
            node.key > key -> {
                return search(node.leftNode, key)
            }
        }
        println()
    }

    private fun insert(node: Node?, key: Int) {
        val newNode = Node(key)
        if (node == null) {
            newNode
            return
        }

        var parent : Node ?= null
        var current: Node? = node
        while (current != null){
            parent = current
            if (current.key < key){
                current = current.rightNode
            }else {
                current = current.leftNode
            }
        }

        if(parent!!.key < key){
            parent.rightNode = newNode
        }else{
            parent.leftNode = newNode
        }
        println(node)

    }


    open class Parent {
        open fun show() {
            println("Parent.Show")
        }

        fun eat() {
            println("Parent.Eat")
        }
    }

    class Child : Parent() {
        override fun show() {
            println("Child.Show")
        }

        fun sing() {
            println("Child.Sing")
        }
    }
}