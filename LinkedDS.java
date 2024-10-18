public class LinkedDS<T extends Comparable<? super T>> implements SequenceInterface<T>, ReorderInterface, Comparable<LinkedDS<T>> {
    Node firstNode = null;
    int numberOfEntries = 0;

    public class Node {
        T item = null;
        Node next = null;
        Node prev = null;
        public Node(T item) {
            this.item = item;
        }
    }

    public LinkedDS() {

    }

    /* Linked list constructor */
    public LinkedDS(LinkedDS<T> ll) {
        Node pointer = ll.firstNode;
        for (int i = 0; i < ll.size(); i++) {
            this.append(pointer.item);
            pointer = pointer.next;
        }
    }

    /* Provides the string representation of the linkedlist */
    @Override
    public String toString() {
        String result = "";
        Node curr = firstNode;
        for (int i = 0; i < this.numberOfEntries; i++) {
            result += curr.item;
            curr = curr.next;
        }
        result = result.trim();

        return result;
    }

    /* Append a new item to the linked list */
    @Override
    public void append(T item) {
        if (this.isEmpty()) {
            this.firstNode = new Node(item);
            this.firstNode.next = this.firstNode;
            this.firstNode.prev = this.firstNode;
        } else { // not empty
            Node lastNode = firstNode.prev;
            lastNode.next = new Node(item);
            lastNode.next.prev = lastNode;
            lastNode.next.next = this.firstNode;
            this.firstNode.prev = lastNode.next;
        }
        this.numberOfEntries++;
    }

    /* Prefix item at start of linked list */
    @Override
    public void prefix(T item) {
        if (isEmpty()) {
            append(item);
            return;
        }
        Node lastNode = this.firstNode.prev;
        Node newNode = new Node(item);
        newNode.next = firstNode;
        newNode.prev = lastNode;
        this.firstNode.prev = newNode;
        this.firstNode = newNode;
        this.numberOfEntries++;
    }

    /* Insert at given position of linked list */
    @Override
    public void insert(T item, int position) {
        if (position < 0 || position > this.numberOfEntries) {
            throw new IndexOutOfBoundsException();
        }
        if (position == numberOfEntries) { // inserting at end
            this.append(item);
            return;
        } else {
            if (position == 0) { // adding at front
                prefix(item);
                return;
            } else { // adding in middle
                Node nodeBefore = nodeAt(position - 1);
                Node nodeAfter = nodeBefore.next;
                Node newNode = new Node(item);
                newNode.next = nodeAfter;
                nodeBefore.next = newNode;
                nodeAfter.prev = newNode;
                newNode.prev = nodeBefore;
                this.numberOfEntries++;
            }
        }
    }

    private Node nodeAt(int position) {
        if (position < 0 || position > (this.size() - 1))
		{
			throw new IndexOutOfBoundsException();
		}
		Node current = firstNode;
		for (int i=0; i<position; i++)
		{
			current = current.next;
		}

		return current;
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
        return this.numberOfEntries == 0;
    }

    @Override
    public int size() {
        return this.numberOfEntries;
    }

    @Override
    public T first() {
        if (this.firstNode == null) {
            return null;
        }
        return this.firstNode.item;
    }

    @Override
    public T last() {
        if (this.firstNode == null) {
            return null;
        }
        return this.firstNode.prev.item;
    }

    @Override
    public T predecessor(T item) {
        if (this.firstNode == null) {
            return null;
        }
        Node current = this.firstNode;
        for (int i = 1; i < this.numberOfEntries; i++) { // skip first entry
            current = current.next;
            if (current.item == item) {
                return current.prev.item;
            }
        }
        return null;
    }

    @Override
    public int getFrequencyOf(T item) {
        Node current = this.firstNode;
        int counter = 0;
        for (int i = 0; i < this.numberOfEntries; i++) {
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
        this.numberOfEntries = 0;
    }

    @Override
    public int lastOccurrenceOf(T item) {
        Node current = this.firstNode;
        int idx = -1;
        for (int i = 0; i < this.numberOfEntries; i++) {
            if (current.item == item) {
                idx = i;
            }
            current = current.next;
        }
        return idx;
    }

    @Override
    public T deleteHead() {
        if (this.isEmpty()) {
            throw new EmptySequenceException("Empty");
        }

        T value = firstNode.item;
        if (this.numberOfEntries == 1) {
            this.firstNode = null;
            this.numberOfEntries = 0;
            return value;
        }

        Node lastNode = firstNode.prev;
        firstNode = firstNode.next;
        lastNode.next = firstNode;
        firstNode.prev = lastNode;

        this.numberOfEntries--;
        return value;
    }

    @Override
    public T deleteTail() {
        if (this.isEmpty()) {
            throw new EmptySequenceException("Empty List");
        }
        if (this.numberOfEntries == 1) {
            return deleteHead();
        }
        Node lastNode = firstNode.prev;
        T result = lastNode.item;
        Node before = lastNode.prev;
        before.next = firstNode;
        firstNode.prev = before;

        this.numberOfEntries--;
        return result;
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
        if (this.numberOfEntries < start + numItems) {
            return false;
        }
        if (start == 0) {
            for (int i = 0; i < numItems; i++) {
                deleteHead();
            }
            return true;
        }
        if (start + numItems == this.numberOfEntries) {
            return trim(numItems);
        }
        Node startNode = this.nodeAt(start - 1);
        Node endNode = this.nodeAt(start + numItems);
        startNode.next = endNode;
        endNode.prev = startNode;
        this.numberOfEntries -= numItems;
        return true;
    }

    @Override
    public LinkedDS<T> slice(T item) {
        LinkedDS<T> newLL = new LinkedDS<>();

        Node curr = this.firstNode;
        for (int i = 0; i < this.numberOfEntries; i++) {
            if (curr.item.compareTo(item) <= 0) {
                newLL.append(item);
            } 
            curr = curr.next;
        }
        return newLL;
    }

    @Override
    public SequenceInterface<T> slice(int start, int numItems) {
        LinkedDS<T> newLL = new LinkedDS<>();

        Node curr = this.nodeAt(start);
        for (int i = 0; i < numItems; i++) {
            if (curr.item.compareTo(curr.item) <= 0) {
                newLL.append(curr.item);
            } 
            curr = curr.next;
        }
        return newLL;
    }

    @Override
    public void reverse() {
        if (this.numberOfEntries <= 1) {
            return;
        }
        Node curr = this.firstNode;
        Node tmp = null;
        for (int i = 0; i < this.numberOfEntries; i++) {
            tmp = curr.prev;
            curr.prev = curr.next;
            curr.next = tmp;
            curr = curr.prev;
        }
        this.firstNode = tmp.prev;
        return;
    }

    @Override
    public void rotateRight() {
        if (!this.isEmpty()) {
            this.firstNode = this.firstNode.prev;
        }
    }

    @Override
    public void rotateLeft() {
        if (!this.isEmpty()) {
            this.firstNode = this.firstNode.next;
        }
    }

    @Override
    public void shuffle(int[] oldPositions, int[] newPositions) {
        Node[] oldNums = new Node[this.numberOfEntries];
        Node curr = this.firstNode;
        for (int i = 0; i < this.numberOfEntries; i++) {
            oldNums[i] = curr.item;
            curr = curr.next;
        }
        return;
    }

    @Override
    public int compareTo(LinkedDS<T> o) {
        Node firstPtr = this.firstNode;
        Node secondPtr = o.firstNode;
        for (int i = 0; i < this.numberOfEntries; i++) {
            if (i >= o.numberOfEntries) {
                return 1;
            } else {
                if (firstPtr.item.compareTo(secondPtr.item) != 0) {
                    return firstPtr.item.compareTo(secondPtr.item);
                } 
                firstPtr = firstPtr.next;
                secondPtr = secondPtr.next;
            }
        }
        return 0;
    }
    
}
