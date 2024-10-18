public class LinkedDS<T extends Comparable<? super T>> implements SequenceInterface<T>, ReorderInterface, Comparable<LinkedDS<T>> {
    Node firstNode = null;
    Node lastNode = null;
    int numberOfEntries = 0;

    public class Node {
        T item = null;
        Node next = null;
        public Node(T item) {
            this.item = item;
        }
    }

    public LinkedDS() {

    }

    public LinkedDS(LinkedDS<T> ll) {
        Node curr = new Node(ll.first());
        this.firstNode = curr;
        Node pointer = ll.firstNode;
        for (int i = 0; i < ll.size()-1; i++) {
            curr.next = pointer.next;
            curr = curr.next;
            pointer = pointer.next;
        }
        this.numberOfEntries = ll.numberOfEntries;
        this.lastNode = curr;
    }

    @Override
    public String toString() {
        String result = "";
        Node curr = firstNode;
        for (int i = 0; i < numberOfEntries; i++) {
            result += curr.item;
            curr = curr.next;
        }
        result = result.trim();
        result += "";

        return result;
    }

    @Override
    public void append(T item) {
        this.numberOfEntries++;
        if (this.firstNode == null) {
            this.firstNode = new Node(item);
            return;
        }
        if (this.firstNode.next == null) {
            this.firstNode.next = new Node(item);
            return;
        }
        Node curr = this.firstNode;
        for (int i = 0; i < this.size()-2; i++) {
            curr = curr.next;
        }
        curr.next = new Node(item);
        this.lastNode = curr.next;
    }

    @Override
    public void prefix(T item) {
        Node tmp = this.firstNode;
        this.firstNode = new Node(item);
        this.firstNode.next = tmp;
        this.numberOfEntries++;
    }

    @Override
    public void insert(T item, int position) {
        this.numberOfEntries++;
        if (this.numberOfEntries == 0 && position == 0) {
            Node tmp = new Node(item);
            this.firstNode = tmp;
            this.lastNode = tmp;
            return;
        }
        Node curr = this.firstNode;
        for (int i = 0; i < position-2; i++) {
            curr = curr.next;
        }
        Node tmp = curr;
        curr.next = new Node(item);
        curr.next.next = tmp;
        if (position == numberOfEntries-1) {
            lastNode = curr.next;
        }
    }

    @Override
    public T itemAt(int position) {
        if (position < 0 || position > (this.size() - 1))
		{
			throw new IndexOutOfBoundsException();
		}
		Node current = firstNode;
		for (int i=0; i<position; i++)
		{
			current = current.next;
		}

		return current.item;

	/**
	 * Runtime: O(1)
	 * @return true if the SequenceInterface<T> is empty, and false otherwise
	 */
    }

    @Override
    public boolean isEmpty() {
        return this.firstNode == null;
    }

    @Override
    public int size() {
        return this.numberOfEntries;
    }

    @Override
    public T first() {
        if (this.firstNode == null) {
            throw new EmptySequenceException("Empty List");
        }
        return this.firstNode.item;
    }

    @Override
    public T last() {
        if (this.firstNode == null) {
            throw new EmptySequenceException("Empty List");
        }
        Node current = this.firstNode;
        while (current.next != null) {
            current = current.next;
        }
        return current.item;
    }

    @Override
    public T predecessor(T item) {
        if (this.firstNode == null) {
            throw new EmptySequenceException("Empty List");
        }
        if (this.size() == 1) {
            return null;
        }
        Node current = this.firstNode;
        while (current.next != null && current.next.item != item) { 
            current = current.next;
        }
        if (current.next == null) {
            return null;
        }
        return current.item;
    }

    @Override
    public int getFrequencyOf(T item) {
        Node current = this.firstNode;
        int counter = 0;
        while (current != null) {
            if (current.item == item) {
                counter++;
            }
            current = current.next;
        }
        return counter;
    }

    @Override
    public void clear() {
        this.firstNode = null;
        this.lastNode = null;
        this.numberOfEntries = 0;
    }

    @Override
    public int lastOccurrenceOf(T item) {
        Node current = this.firstNode;
        int counter = 0;
        int idx = 0;
        while (current != null) {
            if (current.item == item) {
                idx = counter;
            }
            counter++;
            current = current.next;
        }
        return idx;
    }

    @Override
    public T deleteHead() {
        if (this.size() == 0) {
            throw new EmptySequenceException("Empty");
        }
        this.numberOfEntries--;
        T tmp = this.firstNode.item;
        this.firstNode = this.firstNode.next;
        if (this.firstNode == null) {
            this.lastNode = null;
        }
        return tmp;
    }

    @Override
    public T deleteTail() {
        if (this.firstNode == null) {
            throw new EmptySequenceException("Empty List");
        }
        this.numberOfEntries--;
        if (this.firstNode.next == null) {
            T rem = this.firstNode.item;
            this.firstNode = null;
            this.lastNode = null;
            return rem;
        }
        Node curr = firstNode;
        for (int i = 0; i < this.size()-2; i++) {
            curr = curr.next;
        }
        T tmp = curr.next.item;
        curr.next = null;
        this.lastNode = curr;
        return tmp;
    }

    @Override
    public boolean trim(int numItems) {
        if (this.size() < numItems) {
            return false;
        }
        for (int i = 0; i < numItems; i++) {
            deleteTail();
        }
        return true;
    }

    @Override
    public boolean cut(int start, int numItems) {
        this.numberOfEntries -= numItems;
        return false;
    }

    @Override
    public SequenceInterface<T> slice(T item) {
       return null;
    }

    @Override
    public SequenceInterface<T> slice(int start, int numItems) {
        return null;
    }

    @Override
    public void reverse() {
        return;
    }

    @Override
    public void rotateRight() {
        return;
    }

    @Override
    public void rotateLeft() {
        return;
    }

    @Override
    public void shuffle(int[] oldPositions, int[] newPositions) {
        return;
    }

    @Override
    public int compareTo(LinkedDS<T> o) {
        return 0;
    }
    
}
