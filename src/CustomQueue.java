interface MyQueue<T> {
    void offer(T item); //O(1)
    T poll(); //O(1)
    T peek(); //O(1)
    boolean isEmpty(); //O(1)
    int size(); //O(n)

    boolean contains(T item); //O(n)
}

class CustomQueue<T> implements MyQueue<T> {
    private Node<T> front, rear;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public CustomQueue() {
        this.front = this.rear = null;
    }

    @Override
    public void offer(T item) {
        Node<T> newNode = new Node<>(item);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    @Override
    public T poll() {
        if (front == null) {
            return null;
        }
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        return data;
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return front == null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(T item) {
        Node<T> current = front;
        while (current != null) {
            if (current.data.equals(item)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
