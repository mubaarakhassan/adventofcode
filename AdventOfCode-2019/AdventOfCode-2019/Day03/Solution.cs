using System;
using System.Collections.Generic;
using System.Text;

namespace AdventOfCode_2019.Day03
{
    
    //Definition for singly-linked list.
    public class ListNode {
        public int val;
        public ListNode next;
        public ListNode(int val=0, ListNode next=null) {
            this.val = val;
           this.next = next;
        }
    }
   
    public class Solution
    {
        public ListNode ReverseList(ListNode head)
        {
            // base case
            if (head.next == null)
            {
                return head;
            }

            // Loop through the array until you find the base case
            var curr = ReverseList(head.next);  // example -> 5 -> 4 -> null ---- 3 
            


            ListNode prev = new ListNode(curr.val, curr.next);
            
            // Swap the values if the next pointer is null. If it is not keep iterating.
            while (prev.next != null ) 
            {
                prev = prev.next; // 5 -4-null
            }

            if(prev.next == null)
            {
                curr.next = new ListNode(head.val, prev);

            } 
            else
            {
                curr.next = new ListNode(head.val, prev.next);
            }

            return curr;

        } //5 -> 4 -> 3 -> null
    }
}
