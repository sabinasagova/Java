package cz.cuni.mff.java.example03;

import java.util.Iterator;
import java.util.Stack;

public class BinTree implements Iterable{
    @Override
    public Iterator iterator() {
        return new Iterator() {

            private Stack<Node> stack;

            {
                stack = new Stack<Node>();
                smallest(root);
            }

            public boolean hasNext() {
                return !stack.empty();
            }

            public Object next() {
                if(!hasNext())
                    return null;

                Node current = stack.pop();
                smallest(current.right);
                return current.value;
            }

            private void smallest(Node node) {
                while(node!=null) {
                    stack.push(node);
                    node = node.left;
                }
            }
        };
    }

    private class Node {
        int value;
        Node left;
        Node right;
        Node parent;
        public Node(int value) {
            this.value = value;
        }
    }
    private Node root;

    public void add(int a){
        if (root == null){
            root = new Node(a);
        } else {
            Node n = findPlace(root, a);
            if (n.value != a){
                if (n.value > a){
                    n.left = new Node(a);
                    n.left.parent = n;
                } else {
                    n.right = new Node(a);
                    n.right.parent = n;
                }
                //rotations here
            }
        }

    }
    private Node findPlace (Node n, int a){
        if (n.value == a){
            return n;
        } else {
            if (n.value > a){
                if (n.left == null){
                    return n;
                }
                return findPlace(n.left, a);
            } else{
                if (n.right == null){
                    return n;
                }
                return findPlace(n.right, a);
            }
        }
    }

    public boolean find(int a){
        return findInTree(root, a);
    }

    private boolean findInTree(Node n, int a){
        if (n == null){
            return false;
        }
        if (n.value == a){
            return true;
        }
        if (n.value > a){
            return findInTree(n.left, a);
        } else{
            return findInTree(n.right, a);
        }
    }
    public static void main(String[] args){
        BinTree bt = new BinTree();
        try {
            for (String arg : args) {
                bt.add(Integer.parseInt(arg));
            }
            for (Object o : bt){
                System.out.println(o);
            }
        } catch (Exception e) {
            System.out.println("INPUT ERROR");
        }
    }
}
