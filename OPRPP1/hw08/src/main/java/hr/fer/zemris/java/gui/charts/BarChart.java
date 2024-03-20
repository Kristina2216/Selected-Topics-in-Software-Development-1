package hr.fer.zemris.java.gui.charts;

import java.util.List;

public class BarChart {
	private List<XYValue> list;
	private String xDescription;
	private String yDescription;
	private int minY;
	private int maxY;
	private int space;
	
	public BarChart(List<XYValue> list, String xDescription, String yDescription, int minY, int maxY, int space) {
		if(minY<0 || maxY<=minY)
			throw new IllegalArgumentException();
		while((maxY-minY)%space!=0) {
			maxY++;
		}
		for(XYValue val:list) {
			if (val.getY()<minY) 
				throw new IllegalArgumentException();
		}
		this.list=list;
		this.xDescription=xDescription;
		this.yDescription=yDescription;
		this.minY=minY;
		this.maxY=maxY;
		this.space=space;
	}
	
	public List<XYValue> getXYList(){
		return list;
	}
	
	public String getXDescription(){
		return xDescription;
	}
	
	public String getYDescription(){
		return yDescription;
	}
	
	public int getMinY() {
		return minY;
	}
	
	public int getMaxY() {
		return maxY;
	}
	
	public int getSpace() {
		return space;
	}
}
