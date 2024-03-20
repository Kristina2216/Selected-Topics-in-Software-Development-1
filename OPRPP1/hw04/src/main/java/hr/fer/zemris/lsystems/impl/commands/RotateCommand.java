package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class RotateCommand implements Command {
	
	double angle;
	
	public RotateCommand(double angle) {
		this.angle=angle;
	}
	public void execute(Context ctx, Painter painter) {
		TurtleState previous = ctx.getCurrentState();
		ctx.popState();
		previous.getDirection().rotate(angle/360*2*Math.PI);
		ctx.pushState(previous);
	}

}
