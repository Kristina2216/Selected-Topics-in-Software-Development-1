package hr.fer.oprpp1.hw01;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import hr.fer.oprpp1.hw01.*;

public class ComplexNumberTest {
	@Test
	public void fromRealTest() {
		ComplexNumber n=ComplexNumber.fromReal(3.0);
		Assertions.assertEquals("3.0", n.toString());
	}
	@Test
	public void fromImaginaryTest() {
		ComplexNumber n=ComplexNumber.fromImaginary(-3.0);
		Assertions.assertEquals("-3.0i", n.toString());
	}
	@Test
	public void fromMagnitudeAndAngleTest() {
		ComplexNumber n=ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(13),Math.atan2(3, 2));
		Assertions.assertEquals("2.0+3.0i",n.toString());
	}
	
	@Test
	public void parseTest() {
		ComplexNumber c1=ComplexNumber.parse("-3i");
		ComplexNumber c2=ComplexNumber.parse("2i+4");
		ComplexNumber c3=ComplexNumber.parse("+4");
		Assertions.assertEquals("-3.0i", c1.toString());
		Assertions.assertEquals("4.0+2.0i", c2.toString());
		Assertions.assertEquals("4.0", c3.toString());
	}
	@Test
	public void getRealTest() {
		ComplexNumber c1=ComplexNumber.parse("-3i");
		ComplexNumber c2=new ComplexNumber(3,4);
		Assertions.assertEquals(0, c1.getReal());
		Assertions.assertEquals(3.0, c2.getReal());
	}
	@Test
	public void getImaginaryTest() {
		ComplexNumber c1=ComplexNumber.parse("-3i");
		ComplexNumber c2=new ComplexNumber(3,4);
		Assertions.assertEquals(-3.0, c1.getImaginary());
		Assertions.assertEquals(4.0, c2.getImaginary());
	}
	@Test
	public void getMagnitudeTest() {
		ComplexNumber c1=ComplexNumber.parse("-4i+3");
		Assertions.assertEquals(5.0, c1.getMagnitude());
	}
	@Test
	public void getAngleTest() {
		ComplexNumber c1=ComplexNumber.parse("-4i+3");
		Assertions.assertTrue(Math.abs(5.355890089-c1.getAngle())<1E-8);
	}
	@Test
	public void addTest() {
		ComplexNumber c1=new ComplexNumber(3,-2);
		ComplexNumber c2=new ComplexNumber(3,-2);
		Assertions.assertEquals("6.0-4.0i", c1.add(c2).toString());
	}
	@Test
	public void subTest() {
		ComplexNumber c1=new ComplexNumber(3,-2);
		ComplexNumber c2=new ComplexNumber(3,-2);
		Assertions.assertEquals("0", c1.sub(c2).toString());
	}
	@Test
	public void mulTest() {
		ComplexNumber c1=new ComplexNumber(1,3);
		ComplexNumber c2=new ComplexNumber(2,1);
		Assertions.assertEquals("-1.0+7.0i", c1.mul(c2).toString());
	}
	@Test
	public void divTest() {
		ComplexNumber c1=new ComplexNumber(4,2);
		ComplexNumber c2=new ComplexNumber(3,-1);
		ComplexNumber c3=c1.div(c2);
		Assertions.assertTrue(Math.abs(1-c3.getReal())<1E-8);
		Assertions.assertTrue(Math.abs(1-c3.getImaginary())<1E-8);
	}
	@Test
	public void powOkTest(){
		ComplexNumber c1=new ComplexNumber(1,2);
		ComplexNumber c2=c1.power(2);
		ComplexNumber c3=c1.power(3);
		Assertions.assertTrue(Math.abs(-3-c2.getReal())<1E-8);
		Assertions.assertTrue(Math.abs(4-c2.getImaginary())<1E-8);
		Assertions.assertTrue(Math.abs(-11-c3.getReal())<1E-8);
		Assertions.assertTrue(Math.abs(-2-c3.getImaginary())<1E-8);
	}
	@Test
	public void powThrowTest() {
		ComplexNumber c1=new ComplexNumber(1,2);
		try {
			ComplexNumber c2=c1.power(-2);
		}catch(IllegalArgumentException e) {
			return;
		}
		fail();
	}
	public void rootOkTest() {
		ComplexNumber c1=new ComplexNumber(13,3);
		ComplexNumber[] c2=c1.root(2);
		Assertions.assertTrue(Math.abs(3.629-c2[0].getReal())<1E-8);
		Assertions.assertTrue(Math.abs(0.413-c2[0].getImaginary())<1E-8);
	}
	@Test
	public void rootThrowTest() {
		ComplexNumber c1=new ComplexNumber(1,2);
		try {
			ComplexNumber[] c2=c1.root(0);
		}catch(IllegalArgumentException e) {
			return;
		}
		fail();
	}
	@Test
	public void toStringTest() {
		ComplexNumber c1=new ComplexNumber(1,2);
		ComplexNumber c2=ComplexNumber.parse("-2i+3");
		ComplexNumber c3=ComplexNumber.fromReal(0);
		Assertions.assertEquals("1.0+2.0i", c1.toString());
		Assertions.assertEquals("3.0-2.0i", c2.toString());
		Assertions.assertEquals("0", c3.toString());
	}
}
