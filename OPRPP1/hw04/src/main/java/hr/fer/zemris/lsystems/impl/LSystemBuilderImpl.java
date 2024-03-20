package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

public class LSystemBuilderImpl implements LSystemBuilder {
	
	Dictionary<String, Command> registeredActions;
	Dictionary<String, String> registeredProductions;
	double unitLength;
	double unitLengthDegreeScaler;
	Vector2D origin;
	double angle;
	String axiom;
	
	
	public LSystemBuilderImpl() {
		this.unitLength=0.1;
		this.unitLengthDegreeScaler=1;
		this.origin=new Vector2D(0,0);
		this.angle=0;
		this.axiom="";
		registeredActions=new Dictionary<String, Command>();
		registeredProductions=new Dictionary<String, String>();
	}
	
	public class LSystemImpl implements LSystem {
		
		public String generate(int level) {
				String build = "";
				if(level!=0) {
					for (int j = 0; j < axiom.length(); j++){
						char character = axiom.charAt(j);
						String replace = registeredProductions.get(String.valueOf(character));
						if(replace!=null) {
							build= build + replace;
						}else
							build=build+character;
					}
					axiom=build;
				}
		return axiom;
		}
		
		public void draw(int level, Painter painter) {
			Context ctx=new Context();
			ctx.pushState(new TurtleState(origin,
					new Vector2D(Math.cos(angle),Math.sin(angle)),Color.BLACK, unitLength*Math.pow(unitLengthDegreeScaler, level)));
			generate(level);
			for(int i=0;i<axiom.length();i++) {
				Command command=registeredActions.get(String.valueOf(axiom.charAt(i)));
				if(command!=null) {
					command.execute(ctx, painter);
				}
			}
		}
	}
	
	public LSystemImpl build() {
		return new LSystemImpl();
	}

	public LSystemBuilderImpl setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}
	
	
	public LSystemBuilderImpl setOrigin(double x, double y) {
		this.origin = new Vector2D(x,y);
		return this;
	}
	
	public LSystemBuilderImpl setAngle(double angle) {
		this.angle = angle;
		return this;
	}
	
	public LSystemBuilderImpl setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}
	
	public LSystemBuilderImpl setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
	
	public LSystemBuilderImpl registerProduction(char symbol, String production) {
		registeredProductions.put(String.valueOf(symbol), production);
		return this;
	}
	
	public LSystemBuilderImpl registerCommand(char symbol, String action) {
		String[] method = action.trim().split("\\s+");
		
		switch(method[0]){
		
			case "draw":
				registeredActions.put(String.valueOf(symbol), new DrawCommand(Double.parseDouble(method[1])));
				break;
				
			case "skip":
				registeredActions.put(String.valueOf(symbol), new SkipCommand(Double.parseDouble(method[1])));
				break;
				
			case "scale":
				registeredActions.put(String.valueOf(symbol), new ScaleCommand(Double.parseDouble(method[1])));
				break;
				
			case "rotate":
				registeredActions.put(String.valueOf(symbol), new RotateCommand(Double.parseDouble(method[1])));
				break;
				
			case "push":
				registeredActions.put(String.valueOf(symbol), new PushCommand());
				break;
				
			case "pop":
				registeredActions.put(String.valueOf(symbol), new PopCommand());
				break;
				
			case "color":
				String color=method[1];
				registeredActions.put(String.valueOf(symbol), new ColorCommand(Color.decode("#"+color)));
				break;
		}
		return this;
	}
	
	public LSystemBuilderImpl configureFromText(String[] lines) {
		
		for (String line: lines) {
			
			if(line!="") {
				String[] oneLine = line.trim().split("\\s+");
				
				switch(oneLine[0]) {
				
					case "origin":
						setOrigin(Double.parseDouble(oneLine[1]), Double.parseDouble(oneLine[2]));
						break;
						
					case "angle":
						setAngle(Double.parseDouble(oneLine[1]));
						break;
						
					case "unitLength":
						setUnitLength(Double.parseDouble(oneLine[1]));
						break;
						
					case "unitLengthDegreeScaler":
						Double expression=0.;
						String operator=null;
						for (int i=1;i<oneLine.length;i++) {
							if(oneLine[i]!=" ") {
								if(Character.isDigit(oneLine[i].charAt(0))) {
									if(operator!=null) {
										if(operator.equals("*")) {
											expression=expression*Double.parseDouble(oneLine[i]);
										}else if(operator.equals("/")) {
											expression=expression/Double.parseDouble(oneLine[i]);
										}
									}else expression=Double.parseDouble(oneLine[i]);
								}else operator=oneLine[i];
							}
						}
						setUnitLengthDegreeScaler(expression);
						break;
						
					case "axiom":
						setAxiom(oneLine[1]);
						break;
						
					case "command":
						if(oneLine[2].equals("push")||oneLine[2].equals("pop")) {
							String together = oneLine[2];
							registerCommand(oneLine[1].charAt(0), together);
						}else {
							String together = oneLine[2]+" "+oneLine[3];
							registerCommand(oneLine[1].charAt(0), together);
						}
						break;
						
					case "production":
						registerProduction(oneLine[1].charAt(0),oneLine[2]);
						break;
				}
			}
		}
		return this;
	}
	

}
