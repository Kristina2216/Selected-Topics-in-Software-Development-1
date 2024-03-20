package hr.fer.oprpp1.custom.collections;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import hr.fer.oprpp1.custom.collections.*;

public class LinkedListIndexedCollectionTest {
	@Test
	public void testKonstruktorThrowsTest() {
			LinkedListIndexedCollection t=new LinkedListIndexedCollection();
			t.add(1);
			t.add(2);
			t.add(3);
			LinkedListIndexedCollection c=new LinkedListIndexedCollection(t);
			Assertions.assertArrayEquals(t.toArray(),c.toArray());
	}
	@Test
	public void containsFalseTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add(2);
		t.add(3);
		Assertions.assertEquals(false, t.contains(1));
	}
	@Test
	public void containsTrueTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add(2);
		t.add(3);
		Assertions.assertEquals(true, t.contains(2));
	}
	@Test
	public void removeTrueTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add("2");
		t.add("3");
		Assertions.assertEquals(true,t.remove("2"));
	}
	@Test
	public void removeFalseTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add("2");
		t.add("3");
		Assertions.assertEquals(false,t.remove("4"));
	}
	@Test
	public void toArrayTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add("2");
		t.add("3");
		Object[] arr=new Object[]{"2","3"};
		Assertions.assertArrayEquals(arr, t.toArray());
	}
	@Test
	public void addOkTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add("2");
		t.add("3");
		Assertions.assertEquals("3", t.get(1));
	}
	@Test
	public void addThrowsTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		try {
			t.add(null);
		}catch(NullPointerException e) {
			return;
		}
		fail();
	}
	@Test
	public void getOkTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add("2");
		Assertions.assertEquals("2", t.get(0));
	}
	@Test
	public void getThrowsTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add("2");
		try {
			t.get(3);
		}catch(IndexOutOfBoundsException e) {
			return;
		}
		fail();
	}
	@Test
	public void clearTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add("2");
		t.add("3");
		t.clear();
		Assertions.assertEquals(0,t.size());
	}
	@Test
	public void insertOkTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add("2");
		t.add("4");
		t.insert("3", 1);
		Assertions.assertEquals("3", t.get(1));
	}
	@Test 
	public void insertThrowsTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add("2");
		t.add("4");
		try {
			t.insert("3",3);
		}catch(IndexOutOfBoundsException e){
			return;
		}
		fail();
	}
	@Test
	public void indexOfOkTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add("2");
		Assertions.assertEquals(0, t.indexOf("2"));
	}
	@Test
	public void indexOfNotFoundTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add("2");
		Assertions.assertEquals(-1, t.indexOf("3"));
	}
	@Test
	public void removeOnlyTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add("2");
		t.remove(0);
		Assertions.assertEquals(-1, t.indexOf("2"));
	}
	@Test
	public void removeFirstTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		t.add("2");
		t.add("3");
		t.remove(0);
		Assertions.assertEquals(-1, t.indexOf("2"));
		Assertions.assertEquals("3", t.get(0));
	}
	@Test
	public void removeThrowsTest() {
		LinkedListIndexedCollection t=new LinkedListIndexedCollection();
		try {
			t.remove(1);
		}catch(IndexOutOfBoundsException e) {
			return;
		}
		fail();
	}
}
