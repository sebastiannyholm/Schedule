package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PriorityQueue<T> {
	private ArrayList<T> elements = new ArrayList<T>();
	private ArrayList<Calendar> keys = new ArrayList<Calendar>();
	private HashMap<T, Calendar> map = new HashMap<T, Calendar>();

	/**
	 * Inserts an element with a given key.
	 * @param element The element to insert.
	 * @param key The associated key.
	 */
	public void insert(T element, Calendar key) {
		if (map.containsKey(element)) {
			throw new RuntimeException("The PriorityQueue already contains the element: " + element);
		}
		
		elements.add(element);
		keys.add(key);
		map.put(element, key);
		
		bubbleUp(keys.size() - 1);
	}

	/**
	 * Returns the element with the lowest associated key.
	 * @return The element with the lowest associated key.
	 */
	public T extractMin() {
		if (elements.size() == 0) {
			throw new RuntimeException("The PriorityQueue is empty");
		}
	
		T max = elements.get(0);
		
		swap(0, keys.size() - 1);
		
		keys.remove(keys.size() - 1);
		elements.remove(elements.size() - 1);
		
		bubbleDown(0);
		map.remove(max);
		
		return max;
	}

	/**
	 * Changes the key of the element to newKey.
	 * @param element The element to change.
	 * @param newKey The new key associated to the element.
	 */
	public void changeKey(T element, Calendar newKey) {
		if (!map.containsKey(element)) {
			throw new RuntimeException("The PriorityQueue does not contain the element: " + element);
		}

		int index = elements.indexOf(element);
		keys.set(index, newKey);
		bubbleUp(index);
		bubbleDown(index);
	}
	
	/**
	 * Returns whether the PriorityQueue is empty or not.
	 * @return True if the PriorityQueue is empty, otherwise false.
	 */
	public boolean isEmpty() {
		return keys.size() == 0;
	}
	
	private void bubbleUp(int node) {
		while (node > 0 && keys.get(node).before(keys.get(parent(node)))) {
			swap(parent(node), node);
			node = parent(node);
		}
	}

	private void bubbleDown(int node) {		
		while (left(node) < keys.size()) {		
			int min;
			
			if (right(node) >= keys.size() || keys.get(left(node)).before(keys.get(right(node))))
				min = left(node);
			else 
				min = right(node);

			if (keys.get(node).after(keys.get(min))) {
				swap(node, min);			
				node = min;
			}
			else {
				break;
			}
		}
	}
	
	private int parent(int node) {
		return (node - 1) / 2;
	}
	
	private int left(int node) {
		return node * 2 + 1;
	}
	
	private int right(int node) {
		return node * 2 + 2;
	}
	
	private void swap(int i, int j) {
		Calendar tKey = keys.get(i);
		keys.set(i, keys.get(j));
		keys.set(j, tKey);
		
		T tElement = elements.get(i);
		elements.set(i, elements.get(j));
		elements.set(j, tElement);

		map.put(elements.get(i), keys.get(i));
		map.put(elements.get(j), keys.get(j));
	}
}
