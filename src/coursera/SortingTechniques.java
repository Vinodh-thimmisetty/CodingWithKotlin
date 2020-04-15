package coursera;


import java.util.Arrays;
import java.util.Random;

import static java.util.Arrays.copyOf;
import static java.util.Arrays.copyOfRange;

/**
 * Time vs Space Complexity
 * InPlace[Additional space required]
 * Stability[Preserving the previous Sort order]
 * Internal(RAM) vs External(Disk)
 * Recursive vs Non-Recursive
 */
public class SortingTechniques {

	public static void main(String[] args) {
		int[] A = {-2, 7, -4, 1, 5, 3, -42, 20, 40, 100, 4, 6, -3, 2, 10, -8, -20, -45};
		int N = A.length;

		long startTime = System.currentTimeMillis();
		selectionSort(copyOf(A, N), N);
		System.out.println("Selection Sort Took " + (System.currentTimeMillis() - startTime) + " ms");
		startTime = System.currentTimeMillis();
		bubbleSort(copyOf(A, N), N);
		System.out.println("Bubble Sort Took " + (System.currentTimeMillis() - startTime) + " ms");
		startTime = System.currentTimeMillis();
		insertionSort(copyOf(A, N), N);
		System.out.println("Insertion Sort Took " + (System.currentTimeMillis() - startTime) + " ms");
		startTime = System.currentTimeMillis();
		int[] mergeSortArray = copyOf(A, N);
		mergeSort(mergeSortArray);
		System.out.println(Arrays.toString(mergeSortArray));
		System.out.println("Merge Sort Took " + (System.currentTimeMillis() - startTime) + " ms");
		startTime = System.currentTimeMillis();
		int[] quickSortArray = copyOf(A, N);
		quickSort(quickSortArray, 0, N - 1);
		System.out.println(Arrays.toString(quickSortArray));
		System.out.println("Quick Sort Took " + (System.currentTimeMillis() - startTime) + " ms");
		startTime = System.currentTimeMillis();
		heapSort(copyOf(A, N));
		System.out.println("Heap Sort Took " + (System.currentTimeMillis() - startTime) + " ms");
	}

	static void selectionSort(int[] A, int N) {

		for (int i = 0; i < N - 1; i++) {
			// C1 * (N-1)
			int currentMin = i;
			boolean isMinExists = false;
			for (int j = i + 1; j < N; j++) {
				// C2 * (N-1) * (N)
				if (A[j] < A[currentMin]) {
					currentMin = j;
					isMinExists = true;
				}
			}
			// C3 * (N-1)
			if (isMinExists) swap(A, i, currentMin);
		}
		System.out.println(Arrays.toString(A));
	}

	static void bubbleSort(int[] A, int N) {
		for (int i = 0; i < N; i++) {
			// C1 * (N-1)
			boolean isSwapped = false;
			for (int j = i + 1; j < N; j++) {
				// C2 * (N-1) * (N)
				if (A[j] < A[i]) {
					swap(A, i, j);
					isSwapped = true;
				}
			}
			// C3 * (N-1)
			if (!isSwapped) break;
		}
		System.out.println(Arrays.toString(A));

	}

	static void insertionSort(int[] A, int N) {
		for (int i = 0; i < N; i++) {
			// C1 * (N-1)
			int tempVal = A[i];
			int temp = i;
			while (temp > 0 &&
					A[temp - 1] > tempVal) {
				// C2 * (N-1) * (i) -> C2 * (N-1) * (N-1)
				A[temp] = A[temp - 1];
				temp--;
			}
			// C3 * (N-1)
			A[temp] = tempVal;
		}
		System.out.println(Arrays.toString(A));
	}

	static void mergeSort(int[] A) {
		int N = A.length;
		if (N < 2) return;
		int mid = N / 2;
		int[] left = copyOfRange(A, 0, mid); // C1 * N/2
		int[] right = copyOfRange(A, mid, N); // C2 * N/2
		mergeSort(left); // T(N/2)
		mergeSort(right); // T(N/2)
		merge(left, right, A); // C3 * N
	}

	static void merge(int[] left, int[] right, int[] A) {
		int lN = left.length;
		int rN = right.length;
		int leftPos = 0, rightPos = 0, auxPos = 0;
		while (leftPos < lN && rightPos < rN) {
			if (left[leftPos] <= right[rightPos]) A[auxPos++] = left[leftPos++];
			else A[auxPos++] = right[rightPos++];
		}
		while (leftPos < lN) {
			A[auxPos++] = left[leftPos++];
		}
		while (rightPos < rN) {
			A[auxPos++] = right[rightPos++];
		}
//		System.out.println(Arrays.toString(left) + " + " + Arrays.toString(right) + " == " + Arrays.toString(A));

	}

	static void quickSort(int[] A, int start, int end) {
		if (start < end) {
			int pivotalIndex = partition(A, start, end); // C1 * N
			quickSort(A, start, pivotalIndex - 1); // T(i-1)
			quickSort(A, pivotalIndex + 1, end); // T(N-i)
			// ( T(i-1) + T(N-1) / N ) + C1.N <-- Complexity
		}
	}

	// All left values to Pivot value are LESSER
	// All right values to Pivot value are GREATER
	static int partition(int[] A, int start, int end) {
		int pivot = randomizedPivot(start, end);
		// always the last element is pivot.
		swap(A, pivot, start);
		int pivotVal = A[start];
//		System.out.println(start + "<>" + end + " :: <" + pivot + ">" + A[pivot] + " --> " + Arrays.toString(A));
		/**
		 * loop
		 *  : start
		 *      Increment until A[low] > pivotVal
		 *      Decrement until A[high] <= pivotVal
		 *      swap(low,high)
		 *  :end
		 *  swap(first, high)
		 */
		int low = start;
		int high = end;

		while (low < high) {
			while (low <= end && A[low] <= pivotVal) {
				low++;
			}
			while (high >= start && A[high] > pivotVal) {
				high--;
			}
			if (low <= high) swap(A, low, high);
		}

		swap(A, high, start);
//		System.out.println(start + "<" + high + ">" + end + " :: <" + pivot + ">" + pivotVal + " :: " + Arrays.toString(A));
		return high;
	}

	// avoid O(N^2) for already sorted array.
	private static int randomizedPivot(int start, int end) {
		return new Random().nextInt(end - start) + start;
	}

	static void heapSort(int[] A) {
		HeapSortAlgo heapSortAlgo = new HeapSortAlgo();
		// O(N*logN)
		for (int item : A) { // O(N)
			heapSortAlgo.add(item); // 0(logN)
		}
		// O(N*logN)
		int item = 0;
		while (heapSortAlgo.getTreeSize() > 0) {  // O(N)
			A[item++] = heapSortAlgo.peek(); // 0(1)
			heapSortAlgo.remove(); // 0(logN)
		}
		System.out.println(Arrays.toString(A));
	}

	private static void swap(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}
}
