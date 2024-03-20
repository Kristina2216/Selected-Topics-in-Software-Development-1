package hr.fer.zemris.java.gui.charts;


import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;



public class BarChartDemo extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BarChart bar;
	
	public BarChartDemo(BarChart bar) {
		 super();
	     setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	     setTitle("BarChart");
	     setSize(500, 500);
	     setLocation(100, 100);
	     this.bar=bar;
	    
	     initGUI();
	}
	void initGUI() {
		getContentPane().setBackground(Color.WHITE);
		BarChartComponent drawn=new BarChartComponent(bar);
	    getContentPane().add(drawn);
	}
	
	public static void main(String[] args) {
		if(args.length==0) {
			System.out.println("Invalid argument number!");
			System.exit(1);
		}
		try {
			List<String> file= Files.readAllLines(Paths.get(args[0]));
			String[] numbers= file.get(2).split(" ");
			List<XYValue> points = new ArrayList<>();
			 for (int i = 0; i < numbers.length; i++) {
		            String[] point = numbers[i].split(",");
		            if (point.length != 2) {
		                throw new IOException();
		            }
		            points.add(new XYValue(Integer.parseInt(point[0]), Integer.parseInt(point[1])));
		        }
			BarChart bar=new BarChart(points, file.get(0), file.get(1), Integer.valueOf(file.get(3)),
	                Integer.valueOf(file.get(4)), Integer.valueOf(file.get(5)));
			SwingUtilities.invokeLater(() -> {
	            BarChartDemo graph = new BarChartDemo(bar);
	            graph.setVisible(true);
	        });

		}
		catch(IOException e) {
			System.out.println("Wrong path to file");
		}

	}

}
