package Q1;

import java.util.Arrays;

public class ArrayStack<T> {
	private T[] S;
	private int top = -1;

	@SuppressWarnings("unchecked")
	public ArrayStack(int capacity) {
		S = (T[]) new Object[capacity];
	}

	public T pop() {
		if (isEmpty())
			return null;
		T temp = S[top];
		S[top] = null;
		top = top - 1;
		return temp;
	}

	public boolean isEmpty() {
		return top < 0;
	}

	public int size() {
		return top + 1;
	}

	public void push(T value) {
		if (top == S.length - 1) {
			throw new IllegalStateException();
		} else {
			top = top + 1;
			S[top] = value;
		}
	}

	public T top() {
		if (isEmpty())
			return null;
		return S[top];
	}

	public String toString() {
		return Arrays.toString(Arrays.copyOfRange(S, 0, top+1));
	}
}
