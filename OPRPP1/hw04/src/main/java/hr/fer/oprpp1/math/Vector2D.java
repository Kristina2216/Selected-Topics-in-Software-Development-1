package hr.fer.oprpp1.math;
import java.lang.Math;
/**
 * Razred je implementacija
 * 2D vektora
 * 
 */

public class Vector2D {
	private double x;
	private double y;
	/**
	 * Konstruktor prima 2 vrijednosti
	 * 
	 * @param x komponenta x
	 * @param y komponenta y
	 */
	public Vector2D(double x, double y) {
		this.x=x;
		this.y=y;
	}
	/**
	 * Vraća x komponentu
	 * @return x komponenta vektora
	 */
	public double getX() {
		return x;
	}
	/**
	 * Vraća y komponentu
	 * @return y komponenta vektora
	 */
	public double getY() {
		return y;
	}
	/**
	 * Zbraja 2 vektora
	 * te modificira pozivajući
	 * @param offset drugi vektor
	 */
	public void add(Vector2D offset) {
		x=x+offset.x;
		y=y+offset.y;
	}
	/**
	 * Zbraja 2 vektora i vraća
	 * novi vektor koji je rezultat zbrajanja
	 * @param offset drugi vektor
	 * @return novi <code>Vector2D</code>
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(x+offset.x,y+offset.y);
	}
	/**
	 * Rotira pozivajući vektor za 
	 * <code>angle</code> stupnjeva
	 * @param angle kut rotacije
	 */
	public void rotate(double angle) {
		Double newX=Math.cos(angle)*x-Math.sin(angle)*y;
		Double newY=Math.sin(angle)*x+Math.cos(angle)*y;
		x=newX;
		y=newY;
	}
	/**
	 * Stvara novi vektor čije su komponente
	 * komponente pozivajućeg rotirane za
	 * <code>angle</code> stupnjeva
	 * @param angle kut rotacije
	 * @return novi <code>Vector2D</code>
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(Math.cos(angle)*x-Math.sin(angle)*y,Math.sin(angle)*x+Math.cos(angle)*y);
	}
	/**
	 * Množi pozivajući vektor 
	 * skalarom
	 * @param scaler skalar
	 */
	public void scale(double scaler) {
		x=x*scaler;
		y=y*scaler;
	}
	/**
	 * Stvara novi vektor čije
	 * su komponente jednake komponentama pozivajućeg
	 * skaliranog za scaler
	 * @param scaler skalar
	 * @return novi <code>Vector2D</code>
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x*scaler, y*scaler);
	}
	/**
	 * Stvara novi vektor čije su komponente
	 * jednake komponentama pozivajućeg
	 * @return novi <code>Vector2D</code>
	 */
	public Vector2D copy() {
		return new Vector2D(x,y);
	}

}
