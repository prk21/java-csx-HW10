
/**
A class of bags whose entries are stored in a chain of linked nodes.
The bag is never full.
@author Frank M. Carrano
 * This code is from Chapter 4 of
 * Data Structures and Abstractions with Java 4/e
 *      by Carrano
 * 
 * The toString method is overwritten to give a nice display of the items in
 * the bag in this format Bag{Size:# [1] [2] [3] [4] }
 * //- * @version 4.0
 */
import java.util.Random;

public final class LinkedBag<T> implements BagInterface<T> {

    private Node firstNode; // Feference to first node
    private int numberOfEntries;

    public LinkedBag() {
        firstNode = null;
        numberOfEntries = 0;
    } // end default constructor

    /** Sees whether this bag is empty.
    @return true if the bag is empty, or false if not */
    public boolean isEmpty() {
        return numberOfEntries == 0;
    } // end isEmpty

    /** Gets the current number of entries in this bag.
    @return the integer number of entries currently in the bag */
    public int getCurrentSize() {
        return numberOfEntries;
    } // end getCurrentSize


    /** Adds a new entry to this bag.
    @param newEntry The object to be added as a new entry.
    @return True. */
    public boolean add(T newEntry) // OutOfMemoryError possible
    {
        // Add to beginning of chain:
        Node newNode = new Node(newEntry);
        newNode.next = firstNode; // Make new node reference rest of chain
        // (firstNode is null if chain is empty)
        firstNode = newNode; // New node is at beginning of chain
        numberOfEntries++;
        return true;
    } // end add

    /** Retrieves all entries that are in this bag.
    @return A newly allocated array of all the entries in the bag. */
    public T[] toArray() {
        // the cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[numberOfEntries]; // Unchecked cast
        int index = 0;
        Node currentNode = firstNode;
        while ((index < numberOfEntries) && (currentNode != null)) {
            result[index] = currentNode.data;
            index++;
            currentNode = currentNode.next;
        } // end while
        return result;
    } // end toArray

    /** Counts the number of times a given entry appears in this bag.
    @param anEntry The entry to be counted.
    @return The number of times anEntry appears in the bag. */
    public int getFrequencyOf(T anEntry) {
        int frequency = 0;
        int loopCounter = 0;
        Node currentNode = firstNode;
        while ((loopCounter < numberOfEntries) && (currentNode != null)) {
            if (anEntry.equals(currentNode.data)) {
                frequency++;
            }
            loopCounter++;
            currentNode = currentNode.next;
        } // end while
        return frequency;
    } // end getFrequencyOf
    
    public boolean contains(T anEntry) {
        boolean found = false;
        Node currentNode = firstNode;
        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.data)) 
                found = true;
             else 
                currentNode = currentNode.next;
        } // end while
        return found;
    } // end contains

    /** Removes one occurrence of a given entry from this bag, if possible.
    @param anEntry The entry to be removed.
    @return True if the removal was successful, or false otherwise. */
    public boolean remove(T anEntry) {
        boolean result = false;
        Node nodeN = getReferenceTo(anEntry);
        if (nodeN != null) {
            nodeN.data = firstNode.data; // Teplace located entry with entry
                // in first node
            firstNode = firstNode.next; // Remove first node
            numberOfEntries--;
            result = true;
        } // end if
        return result;
    } // end remove

// Locates a given entry within this bag.
// Returns a reference to the node containing the entry, if located,
// or null otherwise.
    private Node getReferenceTo(T anEntry) {
        boolean found = false;
        Node currentNode = firstNode;
        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.data)) {
                found = true;
            } else {
                currentNode = currentNode.next;
            }
        } // end while
        return currentNode;
    } // end getReferenceTo

    public void clear() {
        while (!isEmpty()) {
            remove();
        }
    } // end clear

    private class Node {

        private T data; // Entry in bag
        private Node next; // link to next node

        private Node(T dataPortion) {
            this(dataPortion, null);
        } // end constructor

        private Node(T dataPortion, Node nextNode) {
            data = dataPortion;
            next = nextNode;
        } // end constructor
    } // end Node

    /** Removes one unspecified entry from this bag, if possible.
    @return Either the removed entry, if the removal was successful,
    or null. */
    public T remove() {
        T result = null;
        if(numberOfEntries==0)
        	return result;

        //Generating a random number within range
        Random rn=new Random();
        int x=rn.nextInt(numberOfEntries);
        
        x=x%numberOfEntries;
        
        if(x!=0)
        {
        	Node curr=firstNode,prev=null;
        	for(int i=0;i<x;i++)
        	{
        		
        		prev=curr;
        		curr=curr.next;
        	}
        	//removing the data item from chain and putting it in front
        	prev.next=curr.next;
        	curr.next=firstNode;
        	firstNode=curr;
        }
        

        if (firstNode != null) {
            //storing data
        	result = firstNode.data;
        	// Remove first node from chain
            firstNode = firstNode.next; 
          //decreasing number of entries
            numberOfEntries--;
            if(numberOfEntries==0)
            	firstNode=null;
        } 


        return result;
    } // end remove

    /** Override the toString method so that we can inspect the contents of the bag.
     * @return A string representation of the contents of the bag. */
    public String toString() {

        String result = "Bag{Size:" + numberOfEntries + " ";

        Node scout = firstNode;

        for (int index = 0; index < numberOfEntries; index++) {
            result += "[" + scout.data + "] ";
            scout = scout.next;
        } // end for

        result += "}";
        return result;
    } // end toString

    /*********************************************************************
     * 
     * METHODS TO BE COMPLETED
     * 
     * 
     ************************************************************************/
    
    /** Check to see if two bags are equals.  
     * @param aBag Another object to check this bag against.
     * @return True if the two bags contain the same objects with the same frequencies.
     */
    public boolean equals(LinkedBag<T> aBag) {
        boolean result = false; // result of comparison of bags
 
        //Getting a dummy node to iterate through
        Node temp=this.firstNode;
        //getting number of entries of both the bags to be checked for equality
        int n=this.numberOfEntries;
        int n2=aBag.numberOfEntries;
        int i=0,j=0;
        //IF THE NO. OF ENTRIES DON'T MATCH THEN STRAIGHTAWAY RETURN FALSE
       if(n!=n2)
       {
    	   return result;
       }
       
           for( i=0;i< n;i++)
           {
        	//Counting the number of frequencies of each data item in both the bags 
           	int f1=this.getFrequencyOf(temp.data);
           	int f2=aBag.getFrequencyOf(temp.data);
           	//if they are unequal the result is false and is returned
           	if(f1!=f2)
           		return result;
           //iterating through the whole bag node by node
           	temp=temp.next;
           }
           //if loop completed successfully means all the entries match
           result=true;
        return result;
    }  // end equals

    /** Duplicate all the items in a bag.
     * @return True if the duplication is possible.
     */
    public boolean duplicateAll() {
        boolean success = true; // should always return true
                                // if there is a problem allocating nodes
                                // an exception will be thrown

        //creating a node variable to iterate through
        Node temp=this.firstNode;
        //getting total number of entries to loop
        int n=this.numberOfEntries;
        for(int i=0;i<n;i++)
        {
        	//adding the initial no of entries stored 
     	   this.add(temp.data);
     	   //moving forward
     	   temp=temp.next;
        }

        return success;
    }  // end duplicateAll

    /** Remove all duplicate items from a bag
     */
    public void removeDuplicates() {

        //Creating a new bag 
    	//generic so T Used
   	 LinkedBag<T> tester = new LinkedBag<T>();
   	 //getting number of entries
   	 int n=this.numberOfEntries;
   	 //creating dummy nkide for traversal
   	 Node temp=this.firstNode;
   	 if(n<=0)
   	 {
   		 return;
   	 }
   	 else
   	 {  //adding first node
   		 tester.add(temp.data);
       	 temp=temp.next; 
   	 for(int i=1;i<n;i++)
   	 {
   		 //checking whether the given data item is present in the new bag or not
   		 //if not then add
   			if(!tester.contains(temp.data))
   			{
   				tester.add(temp.data);
   			}
   		//traversing next node
   		temp=temp.next;
   	 }
   	 //now moving the reference to new list
       this.firstNode=tester.firstNode;
   	 this.numberOfEntries=tester.numberOfEntries;
   	 }

        return;
    }  // end removeDuplicates
}
