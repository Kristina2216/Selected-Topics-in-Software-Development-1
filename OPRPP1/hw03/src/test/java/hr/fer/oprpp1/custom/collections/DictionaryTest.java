package hr.fer.oprpp1.custom.collections;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import hr.fer.oprpp1.custom.collections.*;

public class DictionaryTest {
	
	@Test
	public void testPairKonstruktorThrows() {
		try {
			Dictionary<Integer,String> dic=new Dictionary<>();
			Integer n=null;
			dic.put(n, "value");
		}catch(NullPointerException ex) {
			return;
		}
		fail();
	}
	@Test
	public void isEmptyTest() {
			Dictionary<Integer,String> dic=new Dictionary<>();
			Assertions.assertTrue(dic.isEmpty());
	
	}
	@Test
	public void isNotEmptyTest() {
		Dictionary<Integer,String> dic=new Dictionary<>();
		dic.put(1, "one");
		Assertions.assertFalse(dic.isEmpty());

	}
	@Test
	public void sizeTest() {
		Dictionary<Integer,String> dic=new Dictionary<>();
		dic.put(1, "one");
		dic.put(2, "two");
		Assertions.assertEquals(2,dic.size());

	}
	@Test
	public void clearTest() {
		Dictionary<Integer,String> dic=new Dictionary<>();
		dic.put(1, "one");
		dic.put(2, "two");
		dic.clear();
		Assertions.assertEquals(true, dic.isEmpty());
	}
	@Test
	public void putNewTest() {
		Dictionary<Integer,String> dic=new Dictionary<>();
		dic.put(1, "one");
		Assertions.assertEquals("one",dic.get(1));
	}
	@Test
	public void putExsistingTest() {
		Dictionary<Integer,String> dic=new Dictionary<>();
		dic.put(1, "one");
		Assertions.assertEquals("one",dic.put(1, "new"));
	}
	@Test
	public void getExsistingTest() {
		Dictionary<Integer,String> dic=new Dictionary<>();
		dic.put(1, "new");
		Assertions.assertEquals("new",dic.get(1));
	}
	@Test
	public void getNonExsistingTest() {
		Dictionary<Integer,String> dic=new Dictionary<>();
		dic.put(1, "new");
		Assertions.assertEquals(null,dic.get(2));
	}
	@Test
	public void removeTest() {
		Dictionary<Integer,String> dic=new Dictionary<>();
		dic.put(1, "new");
		Assertions.assertEquals("new",dic.remove(1));
	}
}