package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

public class SkipCommand implements Command {
	double step;
	
	public SkipCommand(double step) {
		this.step=step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState previous=ctx.getCurrentState();
		painter.drawLine(previous.getPosition().getX(),
					previous.getPosition().getY(),
					previous.getPosition().getX()+step*previous.getDirection().getX()*previous.getLength(),
					previous.getPosition().getY()+step*previous.getDirection().getY()*previous.getLength(),
					new Color(0,0,0,1), 0f);
		ctx.pushState(new TurtleState(new Vector2D(previous.getPosition().getX()+step*previous.getDirection().getX()*previous.getLength(),
												previous.getPosition().getY()+step*previous.getDirection().getY()*previous.getLength()),
												previous.getDirection(), previous.getColor(),previous.getLength()));
	}

}
