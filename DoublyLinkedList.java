
//Work in Progress

    import java.util.*;
    public class MyDLinkedList <E> implements Iterable <E> {
        //Implementation of a Doubly Linked List
        private static class DListNode<E> { //A node is required which stores the address (next, prev) and the data field
            private E data;
            private DListNode<E> next;
            private DListNode<E> previous;
        }

        private DListNode<E> nil; //Sentinel nil that will point to the end and front of the list, it is of a Node type
        private int size; //Our size that we will update as we add/ remove

        public MyDLinkedList() { //Empty constructor which sets everything initially
            nil = new DListNode<E>(); //Creating a new Node
            nil.previous = nil; //aka tail
            nil.next = nil; //aka head
            nil.data = null;
            size = 0;
        }

        private class DListIterator implements Iterator<E> { //Our Iterator class since we are implementing iterable
            private DListNode<E> pointer; // Just like the current, it will keep track of where we are at in the list

            public DListIterator() { //Iterator constructor which  initi
                if (nil.next == nil)
                    pointer = nil;
                else
                    pointer = nil.next;
            }

            public E next() {
                E old = pointer.data;
                pointer = pointer.next;
                return old;
            }

            public boolean hasNext() {
                return pointer != nil;
            }
        }

        private DListNode<E> findIndex(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException();
            }
            int count = 0;
            DListNode<E> temp = nil.next; //starting from the head
            while (count < index) {
                count++;
                temp = temp.next;
            }
            return temp;
        }


        public void addFirst(E elem) {  //adds elem to the front of the list
            DListNode<E> newNode = new DListNode<>();
            newNode.data = elem;
            newNode.previous = nil;
            newNode.next = nil.next;

            nil.next.previous = newNode;
            nil.next = newNode;
            size++;
        }

        public void push(E e) {
            addFirst(e);
        }

        public void addLast(E elem) {  //adds elem to the end of the list
            DListNode<E> newNode = new DListNode<>();
            newNode.data = elem;
            newNode.previous = nil.previous;
            newNode.next = nil;

            nil.previous.next = newNode;
            nil.previous = newNode;
            size++; //updating size
        }

        public boolean add(E elem) {  //adds elem to the end of the list
            addLast(elem);
            return true;
        }

        public void add(int index, E elem) {  //adds elem to the specified index of the list
            if (index < 0 || index >= size)
                throw new IndexOutOfBoundsException();
            if (index == 0)
                addFirst(elem);
            else if (index == size - 1)
                addLast(elem);
            else {
                DListNode<E> myIter = findIndex(index);
                myIter.data = elem;
                myIter.previous = nil.next;
                myIter.next = nil.previous;

                size++; //updating size
            }
        }

        public E getFirst() { //Retrieves the first element of the list
            if (nil.previous == nil)
                return null;

            E myNil = (E) nil.next.data;
            return myNil;
        }

        public E getLast() { //Retrieves the last element in the list
            if (nil.previous == nil)
                return null;

            E myNil = (E) nil.previous.data;
            return myNil;
        }

        public E removeFirst() { //Removes & returns the front element of the list
            E oldHead = (E) nil.next.data;
            nil.next = nil.next.next;
            nil.next.previous = nil;

            size--; //updating size
            return oldHead;
        }

        public E pop() {
            //Does the same as removeFirst
             return (E) removeFirst();
        }

        public E removeLast() { // Removes & returns the last element of the list
            E oldTail = (E) nil.previous.data;
            nil.previous = nil.previous.previous;
            nil.previous.next = nil;

            size--;
            return oldTail;
        }

        public void clear() { //Removes everything by restarting size and setting to nil
            nil.previous = nil;
            nil.next = nil;
            size = 0;
        }

        public E remove() {//Removes the first element at head
            if (size <= 0)
                throw new NoSuchElementException();
            return (E) removeFirst();
        }

        public E remove(int index) {//removes the node at the specified index
            if (index < 0 || index >= size)
                throw new IndexOutOfBoundsException();
            if (index == 0)
                return removeFirst();
            else if (index == size - 1)
                return removeLast();
            else {
                DListNode<E> myIter = findIndex(index);
                E old = myIter.data;
                myIter.next.previous = myIter.previous;
                myIter.previous.next = myIter.next;

                size--;

                return old;
            }

        }

        public boolean remove(Object obj) {// removes the given object from the list
            int index = indexOf(obj);
            if (index == -1)
                return false;
            else {
                remove(index);
                return true;
            }
        }

        public boolean addAll(int index, Collection<? extends E> c) { //Adds the collection into the given index
            add(index, (E) c);
            return true;
        }

        public boolean addAll(Collection<? extends E> c) { //Adds the elements in the collection to the end of the list
            add((E) c);
            return true;
        }

        public boolean offer(E e) { //Adds the specified element as the tail (last element) of this list.
            return add(e);
        }

        public boolean offerFirst(E e) { //Inserts the specified element at the front of this list.
            addFirst(e);
            return true;
        }

        public boolean offerLast(E e) { //Inserts the specified element at the end of this list.
            addLast(e);
            return true;

            // or offer(e) would also work
        }

        public E peek() { //Retrieves, but does not remove, the head (first element) of this list.
            if (size <= 0)
                throw new IndexOutOfBoundsException();

            E myHead = (E) nil.next.data;
            return myHead;
        }

        public E peekFirst() { //Retrieves, but does not remove, the first element of this list, or returns null if this list is empty.
            return (E) peek();
        }

        public E peekLast() { //Retrieves, but does not remove, the last element of this list, or returns null if this list is empty.
            return (E) getLast();
        }

        public E poll() {//Retrieves and removes the head (first element) of this list.
            return (E) removeLast();
        }

        public E pollFirst() {//Retrieves and removes the head (first element) of this list.
            if (size <= 0) {
                return null;
            }
            return (E) removeFirst();
        }

        public E pollLast() {//Retrieves and removes the tail (last element) of this list.
            if (size <= 0) {
                return null;
            }
            return (E) removeLast();
        }

        public E get(int index) { //Returns the value at “position” index which exists, else throw exception
            if (index < 0 || index >= size)
                throw new IndexOutOfBoundsException();

            int count = 0;
            DListNode temp = nil.next;

            while (count < index) {
                count++;
                temp = temp.next;
            }

            return (E) temp.data;
        }

        public E set(int index, E value) { //Changes the value at “position” index and returns the old value.
            if (index < 0 || index >= size)
                throw new IndexOutOfBoundsException();

            int count = 0;
            DListNode temp = nil.next;

            while (count < index) {
                count++;
                temp = temp.next;
            }
            Object old = temp.data;
            temp.data = value;
            return (E) old;
        }

        public E element() { //Retrieves, but does not remove, the head (first element) of this list.
            E myHead = (E) nil.next.data;
            return myHead;
        }

        public boolean contains(Object obj) { //Returns true if obj appears in the list and false otherwise
            return indexOf(obj) != -1;
        }

        public E clone() { //Returns a shallow copy of this LinkedList.
            if (size <= 0)
                throw new IndexOutOfBoundsException();

            int count = 0;
            DListNode temp = nil.next;

            while (count < size) {
                count++;
                temp = temp.next;
            }
            Object original = temp.data;
            return (E) original;
        }

        public int size() { //simply returning size of the list
            return size;
        }

        public int indexOf(Object obj) { // Returns the index of obj if it is in the list and -1 otherwise.
            int index = 0;
            DListNode<E> myIter = nil.next;

            while (myIter != nil) {
                if (myIter.data.equals(obj))
                    return index;
                index++;
                myIter = myIter.next;
            }
            return -1;
        }

        public int lastIndexOf(Object obj) { //Returns index of LAST occurrence of specified element in this list, or -1 if element not found
            int index = size - 1;
            DListNode<E> myIter = nil.previous;

            while (myIter != null) {
                if (myIter.data.equals(obj))
                    return index;
                index--;
                myIter = myIter.previous;
            }
            return -1;
        }

        public <T> T[] toArray(T[] arr) { //Takes a type T converting our list to an array
            if (size == 0)
                throw new NoSuchElementException();

            Iterator<E> myIter = new DListIterator();

            for (int i = 0; i < arr.length; i++) {
                if (myIter.hasNext()) {
                    arr[i] = (T) myIter.next();
                }
            }
            return arr;
        }
        public Object[] toArray() {//Takes an object converting our list to an array
            if (size == 0)
                throw new NoSuchElementException();

            Object[] myArray = new Object[size];
            Iterator<E> myIter = new DListIterator();

            for (int i = 0; i < myArray.length; i++) {
                if (myIter.hasNext()) {
                    myArray[i] = myIter.next();
                }
            }
            return myArray;
        }

        public boolean removeFirstOccurence(Object obj) { //Removes the first occurence of the given object in the list
            DListNode<E> head = nil.next;

            if (head == nil) {
                return false;
            }

            if (head.data.equals(obj)) {//If we find the first occurence in the 1st attempt starting at the head
                nil.next = head.next;
                return true;
            }
            DListNode<E> temp = nil;
            while (head != nil && head.data != nil && !head.data.equals(obj)) {
                temp = head;
                head = head.next; //removing
            }
            temp.next = head.next;
            head.next.previous = head.previous;

            size--;
            return true;
        }
        public boolean removeLastOccurence(Object obj) {//Removes the last occurence of the given object
            DListNode<E> tail = nil.previous;

            if (tail == nil) {
                return false;
            }
            if (tail.data.equals(obj)) {
                nil.previous = tail.previous;
                return true;
            }

            while (tail != nil && !tail.data.equals(obj)) { //Starting from tail
                tail = tail.previous; //removing
            }

            tail.next.previous = tail.previous; //updating our previous
            tail.previous.next = tail.next; //updating our next pointer

            size--;
            return true;
        }

        public Iterator<E> iterator() { //Returns an iterator over the list.
            return new DListIterator();
        }
    }
        /*

        public Iterator <E> descendingIterator() { //Returns an iterator over the elements in this deque in reverse sequential order.
            while (DListIterator.hasNext())
                return DListIterator.next();
            return
        }

        public ListIterator<E> listIterator(int index) {
                ListIterator<E> iterator= listIterator();
                for (int i = 0; i<index; i++) {
                    iterator.next();
                return iterator;

                 }
             }
        }
*/

