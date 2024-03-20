package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

public interface Command {
	public abstract void execute(Context ctx, Painter painter);
}