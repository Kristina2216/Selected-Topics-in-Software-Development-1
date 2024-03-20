package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class ScaleCommand implements Command{
	
	double factor;
	
	public ScaleCommand(double factor) {
		this.factor=factor;
	}
	public void execute(Context ctx, Painter painter) {
		TurtleState previous = ctx.getCurrentState();
		ctx.popState();
		double newLength=previous.getLength()*factor;
		ctx.pushState(new TurtleState(previous.getPosition(), previous.getDirection(), previous.getColor(),newLength));
	}


}
