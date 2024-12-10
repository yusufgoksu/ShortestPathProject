interface MyStack<T> {
    void push(T item);       // O(1)
    T pop();                 // O(1)
    T peek();                // O(1)
    boolean isEmpty();       // O(1)
    boolean contains(T item); // O(n)
}

class CustomStack<T> implements MyStack<T> {
    private Node<T> top;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public CustomStack() {
        this.top = null;
    }

    /**
     * Adds an element to the top of the stack.
     * Time Complexity: O(1)
     */
    @Override
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
    }

    /**
     * Removes and returns the top element from the stack.
     * Time Complexity: O(1)
     */
    @Override
    public T pop() {
        if (top == null) {
            return null;
        }
        T data = top.data;
        top = top.next;
        return data;
    }

    /**
     * Returns the top element without removing it.
     * Time Complexity: O(1)
     */
    @Override
    public T peek() {
        return (top == null) ? null : top.data;
    }

    /**
     * Checks if the stack is empty.
     * Time Complexity: O(1)
     */
    @Override
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Checks if the stack contains a specific element.
     * Time Complexity: O(n)
     */
    @Override
    public boolean contains(T item) {
        Node<T> current = top;
        while (current != null) {
            if (current.data.equals(item)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
