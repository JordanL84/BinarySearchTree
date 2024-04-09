package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.TreeSet;

import org.junit.Test;

import implementation.BinarySearchTree;
import implementation.GetDataIntoArrays;
import implementation.KeyValuePair;
import implementation.TreeIsEmptyException;
import implementation.TreeIsFullException;

/* The following directive executes tests in sorted order */

public class StudentTests {
	
	/* Remove the following test and add your tests */
	@Test
	public void test1() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 10;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, 
												Integer>(comparator, maxEntries);
		try {
			bst.add("Jordan", 9).add("Gracie", 6).add("Abby", 7);
		} catch (TreeIsFullException e) {
			System.out.println("full tree");
		}
		String answer = "{Abby:7}{Gracie:6}{Jordan:9}";
		assertEquals(answer, bst.toString());
		try {
			KeyValuePair<String, Integer> maximum = bst.getMaximumKeyValue();
			KeyValuePair<String, Integer> minimum = bst.getMinimumKeyValue();
			assertTrue(9 == maximum.getValue());
			assertTrue(7 == minimum.getValue());
		} catch (TreeIsEmptyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 10;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, 
												Integer>(comparator, maxEntries);
		assertThrows(TreeIsEmptyException.class, () -> bst.getMaximumKeyValue());
		assertThrows(TreeIsEmptyException.class, () -> bst.getMinimumKeyValue());
	}
	
	@Test
	public void test3() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 10;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, 
												Integer>(comparator, maxEntries);
		try {
			bst.add("Jordan", 9).add("Gracie", 6).add("Abby", 7);
		} catch (TreeIsFullException e) {
			System.out.println("Tree is full");
		}
		assertEquals(false, bst.isEmpty());
		assertEquals(3, bst.size());
		try {
			bst.delete("Jordan");
		} catch (TreeIsEmptyException e) {
			System.out.println("Empty Tree");
		}
		String answer = "{Abby:7}{Gracie:6}";
		assertEquals(answer, bst.toString());
		try {
			bst.delete("Jordan");
			bst.delete("Gracie");
			bst.delete("Abby");
		} catch (TreeIsEmptyException e) {
			System.out.println("Empty Tree");
		}
		assertEquals(0, bst.size());
		assertEquals("EMPTY TREE", bst.toString());
		assertThrows(TreeIsEmptyException.class, () -> bst.delete("Gracie"));
	}
	
	@Test
	public void test4() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 10;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, 
												Integer>(comparator, maxEntries);
		try {
			bst.add("Gracie", 6).add("Jordan", 9).add("Abby", 7);
		} catch (TreeIsFullException e) {
			System.out.println("Tree is full");
		}
		KeyValuePair<String, Integer> find = bst.find("Jordan");
		assertEquals("Jordan", find.getKey());
		assertTrue(9 == find.getValue());
		assertEquals(null, bst.find("Bob"));
		assertEquals(false, bst.isEmpty());
		assertEquals(false, bst.isFull());
		
		GetDataIntoArrays<String, Integer> callback = new GetDataIntoArrays<String, Integer>();
		bst.processInorder(callback);
		assertEquals("[Abby, Gracie, Jordan]", callback.getKeys().toString());
		assertEquals("[7, 6, 9]", callback.getValues().toString());
		BinarySearchTree<String, Integer> subTree = bst.subTree("Gracie", "Jordan");
		assertEquals("{Gracie:6}{Jordan:9}", subTree.toString());
		TreeSet<Integer> leavesValuesSet = bst.getLeavesValues();
		System.out.println(leavesValuesSet.toString());
		assertEquals("[7]", leavesValuesSet.toString());
	}
	
	@Test
	public void test5() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 10;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, 
												Integer>(comparator, maxEntries);
		try {
			bst.add("Jordan", 9).add("Gracie", 6).add("Abby", 7);
		} catch (TreeIsFullException e) {
			System.out.println("Tree is full");
		}
		BinarySearchTree<String, Integer> subTree = bst.subTree("Abby", "Gracie");
		assertEquals("{Abby:7}{Gracie:6}", subTree.toString());
		//System.out.println(subTree.toString());
	}
}
