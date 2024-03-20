package hr.fer.oprpp1.math;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import hr.fer.oprpp1.custom.collections.*;

public class Vector2DTest {
	@Test
	public void getX() {
		Vector2D v=new Vector2D(3.5,2.8);
		Assertions.assertTrue(Math.abs(3.5-v.getX())<1E-8);
	}
	@Test
	public void getY() {
		Vector2D v=new Vector2D(3.5,2.8);
		Assertions.assertTrue(Math.abs(2.8-v.getY())<1E-8);
	}
	@Test
	public void addTest() {
		Vector2D v1=new Vector2D(3.5,2.8);
		Vector2D v2=new Vector2D(1.5,4.2);
		v1.add(v2);
		Assertions.assertEquals(5.0f, v1.getX());
		Assertions.assertEquals(7.0f, v1.getY());
	}
	@Test
	public void addedTest() {
		Vector2D v1=new Vector2D(3.5,2.8);
		Vector2D v2=new Vector2D(1.5,4.2);
		Vector2D v3=v1.added(v2);
		Assertions.assertEquals(5.0f, v3.getX());
		Assertions.assertEquals(7.0f, v3.getY());
	}
	@Test
	public void rotateTest() {
		Vector2D v1=new Vector2D(0,1);
		v1.rotate(Math.PI/2);
		Assertions.assertEquals(-1.0, v1.getX());
		Assertions.assertTrue((0-v1.getY())<1E-8);
	}
	@Test
	public void rotatedTest() {
		Vector2D v1=new Vector2D(0,1);
		Vector2D v3=v1.rotated(Math.PI/2);
		Assertions.assertEquals(-1.0, v3.getX());
		Assertions.assertTrue((0-v3.getY())<1E-8);
	}
	@Test
	public void scaleTest() {
		Vector2D v1=new Vector2D(2,3);
		v1.scale(2);
		Assertions.assertEquals(4,v1.getX());
		Assertions.assertEquals(6,v1.getY());
	}
	@Test
	public void scaledTest() {
		Vector2D v1=new Vector2D(2,3);
		Vector2D v2=v1.scaled(2);
		Assertions.assertEquals(4,v2.getX());
		Assertions.assertEquals(6,v2.getY());
	}
	@Test
	public void copyTest() {
		Vector2D v1=new Vector2D(2,3);
		Vector2D v2=v1.copy();
		Assertions.assertEquals(v1.getX(),v2.getX());
		Assertions.assertEquals(v1.getY(),v2.getY());
	}
}
