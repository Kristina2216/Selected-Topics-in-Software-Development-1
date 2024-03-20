package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class ColorCommand implements Command {
	
	Color color;
	
	public ColorCommand(Color color) {
		this.color=color;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState previous = ctx.getCurrentState();
		ctx.popState();
		ctx.pushState(new TurtleState(previous.getPosition(), previous.getDirection(), this.color,previous.getLength()));
	}
}
