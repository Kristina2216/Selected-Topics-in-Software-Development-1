package hr.fer.oprpp1.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class Glavni3 {
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
