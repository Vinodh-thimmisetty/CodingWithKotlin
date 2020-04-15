package coursera;

import java.util.Arrays;

public class HeapSortAlgo {

	private int capacity = 10;
	private int size = 0;
	int[] items = new int[capacity];

	int getParentIndex(int childIndex) {
		return (childIndex - 1) / 2;
	}

	int getLeftChildIndex(int parentIndex) {
		return 2 * parentIndex + 1;
	}

	int getRightChildIndex(int parentIndex) {
		return 2 * parentIndex + 2;
	}

	int getParent(int childIndex) {
		return items[getParentIndex(childIndex)];
	}

	int getLeftChild(int parentIndex) {
		return items[getLeftChildIndex(parentIndex)];
	}

	int getRightChild(int parentIndex) {
		return items[getRightChildIndex(parentIndex)];
	}

	boolean hasParent(int childIndex) {
		return getParentIndex(childIndex) >= 0;
	}

	boolean hasLeftChild(int parentIndex) {
		return getLeftChildIndex(parentIndex) < size;
	}

	boolean hasRightChild(int parentIndex) {
		return getRightChildIndex(parentIndex) < size;
	}

	int getTreeSize() {
		return size;
	}

	void ensureTreeCapacity() {
		if (size == capacity) {
			items = Arrays.copyOf(items, 2 * capacity);
			capacity *= 2;
		}
	}

	void swap(int p, int q) {
		int temp = items[p];
		items[p] = items[q];
		items[q] = temp;
	}

	int peek() {
		if (size == 0) throw new IllegalStateException();
		return items[0];
	}

	void add(int item) {
		ensureTreeCapacity();
		items[size++] = item;
		heapifyUp();
	}

	void remove() {
		if (size == 0) throw new IllegalStateException();
		swap(0, --size);
		heapifyDown();
	}

	void heapifyUp() {
		int index = size - 1;
		while (hasParent(index) &&
				items[index] < getParent(index)) {
			swap(getParentIndex(index), index);
			index = getParentIndex(index);
		}
	}

	void heapifyDown() {
		int index = 0;
		while (hasLeftChild(index)) {
			int smallChildIndex = getLeftChildIndex(index);
			if (hasRightChild(index) && getRightChild(index) < getLeftChild(index)) {
				smallChildIndex = getRightChildIndex(index);
			}
			if (items[index] < items[smallChildIndex]) break;
			else swap(smallChildIndex, index);
			index = smallChildIndex;
		}
	}
}
