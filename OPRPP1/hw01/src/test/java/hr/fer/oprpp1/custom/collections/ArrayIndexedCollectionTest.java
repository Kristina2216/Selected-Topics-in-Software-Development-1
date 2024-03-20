package hr.fer.oprpp1.custom.collections;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import hr.fer.oprpp1.custom.collections.*;
public class ArrayIndexedCollectionTest {
	@Test
	public void testKonstruktor1ThrowsTest() {
		try {
			ArrayIndexedCollection t=new ArrayIndexedCollection(0);
		}catch(IllegalArgumentException ex) {
			return;
		}
		fail();
	}
	@Test
	public void testKonstruktor2SizeTest() {
			ArrayIndexedCollection t=new ArrayIndexedCollection();
			t.add(1);
			t.add(2);
			t.add(3);
			ArrayIndexedCollection n=new ArrayIndexedCollection(t, 2);
			Assertions.assertArrayEquals(t.toArray(),n.toArray());
	}
	@Test
	public void containsFalseTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		t.add(2);
		t.add(3);
		Assertions.assertEquals(false, t.contains(1));
	}
	@Test
	public void containsTrueTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		t.add(2);
		t.add(3);
		Assertions.assertEquals(true, t.contains(2));
	}
	@Test
	public void removeTrueTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		t.add("2");
		t.add("3");
		Assertions.assertEquals(true,t.remove("2"));
	}
	@Test
	public void removeFalseTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		t.add("2");
		t.add("3");
		Assertions.assertEquals(false,t.remove("4"));
	}
	@Test
	public void toArrayTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		t.add("2");
		t.add("3");
		Object[] arr=new Object[]{"2","3"};
		Assertions.assertArrayEquals(arr, t.toArray());
	}
	@Test
	public void clearTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		t.add("2");
		t.add("3");
		t.clear();
		Assertions.assertEquals(0,t.size());
	}
	@Test
	public void insertOkTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		t.add("2");
		t.add("4");
		t.insert("3", 1);
		Assertions.assertEquals("3", t.get(1));
	}
	@Test 
	public void insertThrowsTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
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
	public void addTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		t.add("2");
		t.add("3");
		Assertions.assertEquals("3", t.get(1));
	}
	@Test
	public void addThrowsTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		try {
			t.add(null);
		}catch(NullPointerException e) {
			return;
		}
		fail();
	}
	public void getOkTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		t.add("2");
		Assertions.assertEquals("2", t.get(0));
	}
	@Test
	public void getThrowsTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		t.add("2");
		try {
			t.get(3);
		}catch(IndexOutOfBoundsException e) {
			return;
		}
		fail();
	}
	@Test
	public void indexOfOkTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		t.add("2");
		Assertions.assertEquals(0, t.indexOf("2"));
	}
	@Test
	public void indexOfNotFoundTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		t.add("2");
		Assertions.assertEquals(-1, t.indexOf("3"));
	}
	@Test
	public void removeOkTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		t.add("2");
		t.remove(0);
		Assertions.assertEquals(-1, t.indexOf("2"));
	}
	@Test
	public void removeThrowsTest() {
		ArrayIndexedCollection t=new ArrayIndexedCollection();
		try {
			t.remove(1);
		}catch(IndexOutOfBoundsException e) {
			return;
		}
		fail();
	}
}
