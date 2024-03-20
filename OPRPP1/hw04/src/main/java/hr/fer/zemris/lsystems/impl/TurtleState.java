package hr.fer.zemris.lsystems.impl;
import java.awt.Color;

import hr.fer.oprpp1.math.*;

public class TurtleState {
	Vector2D position;
	Vector2D direction;
	Color color;
	double length;
	
	public TurtleState(Vector2D position, Vector2D direction, double length) {
		this(position, direction, Color.BLACK, length);
	}
	
	public TurtleState(Vector2D position, Vector2D direction, Color color, double length) {
		this.position=position;
		this.direction=direction; //angle=asin(y) || acos(x)
		this.color=color;
		this.length=length;
	}
	
	
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), color, length);
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public Vector2D getDirection() {
		return direction;
	}
	
	public Color getColor() {
		return color;
	}
	
	public double getLength() {
		return length;
	}
	
	public String toString() {
		return "Position: ("+position.getX()+", "+position.getY()+ ") Direction: "
				+direction.getX()+", "+direction.getY()+ ") Color: "+color.toString()
				+" Length: "+length;
	}
}
