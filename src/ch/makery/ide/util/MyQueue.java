package ch.makery.ide.util;

import java.util.ArrayList;
import java.util.List;

public class MyQueue<T> {
	private List<T> queue;
	private int size;

	public MyQueue(int size) {
		this.size = size;
		this.queue = new ArrayList<T>(this.size);
	}

	public List<T> getQueue() {
		return queue;
	}

	public void push(T t) {
		if (this.queue.size() + 1 > this.size) {
			this.queue.remove(0);
		}
		this.queue.add(this.queue.size(), t);
	}

}
