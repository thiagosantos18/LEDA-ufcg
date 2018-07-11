package adt.avltree;

import adt.bst.BSTNode;
import adt.bt.Util;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends AVLTreeImpl<T> implements AVLCountAndFill<T> {

   private int LLcounter;
   private int LRcounter;
   private int RRcounter;
   private int RLcounter;

   public AVLCountAndFillImpl() {

   }

   @Override
   public int LLcount() {
      return LLcounter;
   }

   @Override
   public int LRcount() {
      return LRcounter;
   }

   @Override
   public int RRcount() {
      return RRcounter;
   }

   @Override
   public int RLcount() {
      return RLcounter;
   }

   @Override
   protected void rebalance(BSTNode<T> node) {
      int balance = calculateBalance(node);
      boolean doubleRotation = false;
      if (balance < -1) {
         if (calculateBalance((BSTNode<T>) node.getRight()) > 0) {
            rightRotation((BSTNode<T>) node.getRight());
            doubleRotation = true;
         }
         leftRotation(node);
         if (doubleRotation)
            this.RLcounter++;
         else
            this.RRcounter++;

      } else if (balance > 1) {
         if (calculateBalance((BSTNode<T>) node.getLeft()) < 0) {
            leftRotation((BSTNode<T>) node.getLeft());
            doubleRotation = true;
         }
         rightRotation(node);
         if (doubleRotation)
            this.LRcounter++;
         else
            this.LLcounter++;
      }
   }

   @Override
   public void fillWithoutRebalance(T[] array) {
      quickInsert(array, 0, array.length - 1);
   }

   private void quickInsert(T[] array, int leftIndex, int rightIndex) {

      if (leftIndex < rightIndex) {
         int pivotIndex = partition(array, leftIndex, rightIndex);

         this.insert(array[pivotIndex]);

         quickInsert(array, leftIndex, pivotIndex - 1);
         quickInsert(array, pivotIndex + 1, rightIndex);
      }
   }

   private int partition(T[] array, int leftIndex, int rightIndex) {

      int inicio = leftIndex + 1;
      int fim = rightIndex;

      while (inicio <= fim) {

         if (array[inicio].compareTo(array[leftIndex]) <= 0)
            inicio++;
         else if (array[fim].compareTo(array[leftIndex]) > 0)
            fim--;
         else {
            Util.swap(array, inicio, fim);
            inicio++;
            fim--;
         }
      }
      Util.swap(array, leftIndex, fim);
      return fim;
   }
}
