import java.util.AbstractList;

public class CustomList<T> extends AbstractList<T> {
    private Object[] elements; // Array to store the elements of the list
    private int size; // Current number of elements in the list

    // Constructor to initialize the list with a default capacity of 10
    public CustomList() {
        elements = new Object[10];
        size = 0;
    }

    /**
     * Retrieves the element at the specified index.
     * Time Complexity: O(1)
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) { // Check for invalid index
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index]; // Return the element at the index
    }

    /**
     * Returns the current size of the list.
     * Time Complexity: O(1)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Replaces the element at the specified index with the given element.
     * Time Complexity: O(1)
     */
    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size) { // Check for invalid index
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T old = (T) elements[index]; // Store the old element
        elements[index] = element; // Replace with the new element
        return old; // Return the old element
    }

    /**
     * Adds an element at the specified index and shifts subsequent elements.
     * Time Complexity: O(n), where n is the number of elements after the index
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) { // Check for invalid index
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (size == elements.length) { // Resize the array if needed
            resize();
        }
        // Shift elements to the right to make space for the new element
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element; // Insert the new element
        size++; // Increment the size
    }

    /**
     * Removes the element at the specified index and shifts subsequent elements.
     * Time Complexity: O(n), where n is the number of elements after the index
     */
    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) { // Check for invalid index
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T removed = (T) elements[index]; // Store the element to be removed
        // Shift elements to the left to fill the gap
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null; // Decrease size and set the last element to null
        return removed; // Return the removed element
    }

    /**
     * Doubles the capacity of the internal array when it runs out of space.
     * Time Complexity: O(n), where n is the current size of the list
     */
    private void resize() {
        Object[] newElements = new Object[elements.length * 2]; // Create a new array with double the size
        System.arraycopy(elements, 0, newElements, 0, elements.length); // Copy elements to the new array
        elements = newElements; // Replace the old array with the new one
    }

    /**
     * Joins the elements of a CustomList<String> into a single string separated by a given separator.
     * Time Complexity: O(n), where n is the size of the list
     */

}
