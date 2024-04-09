/*Jordan Lin
* Spring, 2023
*/
package implementation;

import java.util.Comparator;
import java.util.*;

public class BinarySearchTree<K, V> {
	/*
	 * You may not modify the Node class and may not add any instance nor static
	 * variables to the BinarySearchTree class.
	 */
	private class Node {
		private K key;
		private V value;
		private Node left, right;

		private Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private Node root;
	private int treeSize, maxEntries;
	private Comparator<K> comparator;

	public BinarySearchTree(Comparator<K> comparator, int maxEntries) {
		this.comparator = comparator;
		this.maxEntries = maxEntries;
	}

	public BinarySearchTree<K, V> add(K key, V value) throws TreeIsFullException {
		if (root == null) {
			root = new Node(key, value);
			treeSize++;
		} else {
			addAux(key, value, root);
			treeSize++;
		}
		return this;
	}
	
	private boolean addAux(K key, V value, Node rootAux) {
		int comparison = comparator.compare(key, rootAux.key);

		if (comparison == 0) { //key already in tree
			rootAux.value = value; //updates value
			return false;
		} else if (comparison < 0) { //traverse left subtree
			if (rootAux.left == null) {
				rootAux.left = new Node(key, value);
				//treeSize++;
				return true;
			} else {
				return addAux(key, value, rootAux.left);
			}
		} else { //traverse right subtree
			if (rootAux.right == null) {
				rootAux.right = new Node(key, value);
				//treeSize++;
				return true;
			} else { //add leaf
				return addAux(key, value, rootAux.right);
			}
		}
	}

	public String toString() {
		if (root == null)
			return "EMPTY TREE";
		return toStringAux(root);
	}

	public String toStringAux(Node rootAux) {
		String str = "";
		if (rootAux.left != null)
			str += toStringAux(rootAux.left);
		str += "{" + rootAux.key + ":" + rootAux.value + "}";
		if (rootAux.right != null)
			str += toStringAux(rootAux.right);
		return str;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return treeSize;
	}

	
	public boolean isFull() {
		return treeSize == maxEntries;
	}

	public KeyValuePair<K, V> getMinimumKeyValue() throws TreeIsEmptyException {
		if (root == null)
			throw new TreeIsEmptyException("Empty Tree");
		return getMinimumKeyValueAux(root);
	}

	public KeyValuePair<K, V> getMinimumKeyValueAux(Node rootAux) throws TreeIsEmptyException {
		if (rootAux == null)
			throw new TreeIsEmptyException("Empty Tree");
		if (rootAux.left == null)
			return new KeyValuePair<K, V>(rootAux.key, rootAux.value);
		return getMinimumKeyValueAux(rootAux.left);
	}
	
	public KeyValuePair<K, V> getMaximumKeyValue() throws TreeIsEmptyException {
		if (root == null)
			throw new TreeIsEmptyException("Empty Tree");
		return getMaximumKeyValueAux(root);
	}
	
	public KeyValuePair<K, V> getMaximumKeyValueAux(Node rootAux) throws TreeIsEmptyException {
		if (root == null)
			throw new TreeIsEmptyException("Empty Tree");
		if (rootAux.right == null)
			return new KeyValuePair<K, V>(rootAux.key, rootAux.value);
		return getMaximumKeyValueAux(rootAux.right);
	}

	public KeyValuePair<K, V> find(K key) {
		return findAux(key, root);
	}
	
	public KeyValuePair<K, V> findAux(K key, Node rootAux) {
		if (rootAux == null)
			return null;
		int comparison = comparator.compare(key, rootAux.key);
		if (comparison == 0) //if key is found
			return new KeyValuePair<K, V>(key, rootAux.value);
		if (comparison < 0) {
			if (rootAux.left == null)
				return null;
			return findAux(key, rootAux.left);
		}
		if (rootAux.right == null)
			return null;
		return findAux(key, rootAux.right);
	}

	public BinarySearchTree<K, V> delete(K key) throws TreeIsEmptyException {
		if (root == null)
			throw new TreeIsEmptyException("Empty Tree");
		if (root.left == null && root.right == null)
			root = null;
		else {
			deleteAux(key, root);
			treeSize--;
		}
		return this;
	}
	
	public BinarySearchTree<K, V>.Node deleteAux(K key, Node rootAux) {
		int comparison = comparator.compare(key, rootAux.key);
		//Search key
		if (comparison > 0) { //key is greater than root
			if (rootAux.right == null)
				return rootAux;
			rootAux.right = deleteAux(key, rootAux.right);
		}
		else if (comparison < 0) {
			if (rootAux.left == null)
				return rootAux;
			rootAux.left = deleteAux(key, rootAux.left);
		}
		else { //if key is found
			if (rootAux.left != null) { //Look largest value on left subtree
				try {
					KeyValuePair<K,V> max = getMaximumKeyValueAux(rootAux.left);
					rootAux.key = max.getKey();
					rootAux.value = max.getValue();
					rootAux.left = deleteAux(max.getKey(), rootAux.left);
				} catch (TreeIsEmptyException e) {
					e.printStackTrace();
				}
			}
			//if no left subtree look smallest value on right subtree
			else if (rootAux.right != null){
				try {
					KeyValuePair<K,V> min = getMinimumKeyValueAux(rootAux.right);
					rootAux.key = min.getKey();
					rootAux.value = min.getValue();
					rootAux.right = deleteAux(min.getKey(), rootAux.right);
				} catch (TreeIsEmptyException e) {
					e.printStackTrace();
				}
			}
			else //if it's a leaf
				return null;
		}
		return rootAux;
	}

	public void processInorder(Callback<K, V> callback) {
		processInorderAux(callback, root);
	}
	
	public void processInorderAux(Callback<K, V> callback, Node rootAux) {
		if (rootAux.left != null)
			processInorderAux(callback, rootAux.left);
		callback.process(rootAux.key, rootAux.value);
		if (rootAux.right != null)
			processInorderAux(callback, rootAux.right);
	}

	public BinarySearchTree<K, V> subTree(K lowerLimit, K upperLimit) {
		return subTreeAux(lowerLimit, upperLimit, root);
	}
	
	public BinarySearchTree<K, V> subTreeAux(K lowerLimit, K upperLimit, Node rootAux) {
		BinarySearchTree<K, V> subTree = new BinarySearchTree<K, V>(comparator, maxEntries);
		if (rootAux == null)
			return subTree;
		if (comparator.compare(rootAux.key,  upperLimit) > 0) {
			return subTreeAux(lowerLimit, upperLimit, rootAux.left);
		}
		else if (comparator.compare(rootAux.key, lowerLimit) < 0) {
			return subTreeAux(lowerLimit, upperLimit, rootAux.right);
		}
		else if (comparator.compare(rootAux.key, lowerLimit) >= 0 &&
				comparator.compare(rootAux.key, upperLimit) <= 0) {
			subTree.root = new Node(rootAux.key, rootAux.value);
			subTree.root.left = subTreeAux(lowerLimit, upperLimit, rootAux.left).root;
			subTree.root.right = subTreeAux(lowerLimit, upperLimit, rootAux.right).root;
		}
		return subTree;
	}

	public TreeSet<V> getLeavesValues() {
		TreeSet<V> treeSet = new TreeSet<V>();
		if (root == null)
			return treeSet;
		getLeavesValuesAux(root, treeSet);
		return treeSet;
	}

	public void getLeavesValuesAux(Node rootAux, TreeSet<V> treeSet) {
		if (rootAux.left == null) 
			treeSet.add(rootAux.value);
		else
			getLeavesValuesAux(rootAux.left, treeSet);
		if (rootAux.right == null)
			treeSet.add(rootAux.value);
		else if (rootAux.left == null)
			getLeavesValuesAux(rootAux.right, treeSet);
	} 
}
