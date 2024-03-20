package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class DrawCommand implements Command {
	
	double step;
	
	public DrawCommand(double step) {

		this.step=step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState previous=ctx.getCurrentState();
		painter.drawLine(previous.getPosition().getX(),
					previous.getPosition().getY(),
					previous.getPosition().getX()+step*previous.getDirection().getX()*previous.getLength(),
					previous.getPosition().getY()+step*previous.getDirection().getY()*previous.getLength(),
					previous.getColor(), 1f);
		ctx.pushState(new TurtleState(new Vector2D(previous.getPosition().getX()+step*previous.getDirection().getX()*previous.getLength(),
												previous.getPosition().getY()+step*previous.getDirection().getY()*previous.getLength()),
												previous.getDirection(), previous.getColor(),previous.getLength()));
	}

}
