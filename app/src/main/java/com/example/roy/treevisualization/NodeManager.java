package com.example.roy.treevisualization;

import java.util.Random;

/**
 * Created by roy on 23/11/16.
 */
public class NodeManager {


    private Node head;
    public NodeManager(Node head){
        this.head = head;
        this.head = null;
    }


    public static float radius = 30.0f; //hardcoded for test

    private Random rand = new Random();

    public void populateTree(Node child, int depth){


        radius = RenderView.screenSize.x / 30;

        if(head == null || child == null){return;}

        for(int i = 0; i < depth; i++) {

            int leftVal = rand.nextInt(child.value / 2) + 1;
            int rightVal = rand.nextInt(child.value * 5) + child.value * 2;

            Node childLeft = insert(head, leftVal);
            Node childRight = insert(head, rightVal);
        }
}



    public Node findByValue(Node leaf, int key, boolean reset){

        if(leaf != null){

                leaf.isHighlightedInPath = !reset;

            if(key == leaf.value){
                return leaf;
            }

            else if(key > leaf.value){
               return findByValue(leaf.childRight, key, reset);
            }

            else if(key < leaf.value) {
                 return findByValue(leaf.childLeft, key, reset);
            }
        }

        return null;
    }


    public Node insert(Node leaf, int key){
        if(leaf==null){
            head = new Node(0, 400 / 2, 40, NodeManager.radius); //hard coded for test
            head.type = Node.NODE_TYPE_HEAD;
            head.pos_x -= head.radius;
        }
        if(key < leaf.value) {

            if(leaf.childLeft != null){
                insert(leaf.childLeft, key);
            }

            else{

                int offset_lx = Node.NODE_LEFT_lX_OFFSET(leaf.radius) * 3;


                if(leaf.type.equals(Node.NODE_TYPE_LEFT_CHILD)){
                    offset_lx = Node.NODE_LEFT_lX_OFFSET(leaf.radius) - (int)leaf.radius;

                }

                else if(leaf.type.equals(Node.NODE_TYPE_RIGHT_CHILD)){
                    offset_lx = Node.NODE_RIGHT_lX_OFFSET(leaf.radius) -(int)leaf.radius / 3;
                }


                leaf.childLeft = new Node(key, leaf.pos_x + offset_lx, leaf.pos_y + Node.NODE_Y_OFFSET(radius), radius );
                leaf.childLeft.type = Node.NODE_TYPE_LEFT_CHILD;

                return leaf.childLeft;
            }

        }

        else if(key > leaf.value) {

            if (leaf.childRight != null) {
                insert(leaf.childRight, key);
            } else {

                /*** PLACE HOLDER **/

                int offset_rx = Node.NODE_RIGHT_rX_OFFSET(leaf.radius) * 3;

                if (leaf.type.equals(Node.NODE_TYPE_LEFT_CHILD)) {

                    offset_rx = Node.NODE_LEFT_rX_OFFSET(leaf.radius) + (int) leaf.radius / 3;
                } else if (leaf.type.equals(Node.NODE_TYPE_RIGHT_CHILD)) {


                    offset_rx = Node.NODE_RIGHT_rX_OFFSET(leaf.radius) + (int) leaf.radius;
                }

                leaf.childRight = new Node(key, leaf.pos_x + offset_rx, leaf.pos_y + Node.NODE_Y_OFFSET(radius), radius);
                leaf.childRight.type = Node.NODE_TYPE_RIGHT_CHILD;

                return leaf.childRight;
            }
        }

        return null;

    }

    public Node FindMin (Node leaf){
        if(leaf == null)
            return null;
        else
        if(leaf.childLeft == null)
            return leaf;
        else
            return FindMin(leaf.childLeft);
    }

    public Node FindMax (Node leaf){
        if(leaf == null)
            return null;
        else
        if(leaf.childRight == null)
            return leaf;
        else
            return FindMax(leaf.childRight);
    }

    public Node Delete (Node leaf,int key){
        Node temp;


        if(key < leaf.value) {
            leaf.childLeft=Delete(leaf.childLeft,key);
        }
        else if(key > leaf.value){
            leaf.childRight=Delete(leaf.childRight, key);
        }
        else if(leaf.childLeft!=null && leaf.childRight!=null){
            temp = FindMin(leaf.childRight);
            leaf.value = temp.value;
            leaf.childRight=Delete(leaf.childRight,leaf.value);
        }
        else{

            if (leaf.childLeft == null && leaf.childRight!=null) {
                /*leaf = leaf.childRight;*/
                temp = FindMin(leaf.childRight);
                leaf.value = temp.value;
                leaf.childRight=Delete(leaf.childRight,leaf.value);
            }
            else if (leaf.childRight == null && leaf.childLeft!=null) {
                temp = FindMax(leaf.childLeft);
                leaf.value = temp.value;
                leaf.childLeft=Delete(leaf.childLeft,leaf.value);
            }
            else if(leaf.childLeft==null && leaf.childRight==null){
                leaf=null;
            }
        }
      return leaf;
    }

}
