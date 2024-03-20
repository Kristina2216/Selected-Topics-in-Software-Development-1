package hr.fer.zemris.java.gui.charts;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Font;

public class BarChartComponent extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BarChart chart;
	private Font f;
	
	public BarChartComponent(BarChart chart) {
		this.chart=chart;
		f=new Font("Arial", Font.BOLD, 16);
	}
	
	@Override
	public void paintComponent(Graphics gr) {
		Graphics2D graphics=(Graphics2D) gr;
		AffineTransform defaultAT = graphics.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		graphics.setTransform(at);
		graphics.setFont(new Font("Arial", Font.BOLD, 24));
		float relativeSize=graphics.getFontMetrics(f).stringWidth(chart.getYDescription());
		graphics.drawString(chart.getYDescription(), -this.getHeight()/2-relativeSize*2, 30);
		graphics.setTransform(defaultAT);
		graphics.setFont(f);
		relativeSize=graphics.getFontMetrics(f).stringWidth(chart.getXDescription());
		int scaleX=(int)(getHeight()*0.025);
		graphics.drawString(chart.getXDescription(), this.getWidth()/2-relativeSize/2, getHeight()-scaleX);
		int numberOfSpaces=chart.getMaxY()-chart.getMinY();
		System.out.println(numberOfSpaces);
		numberOfSpaces/=chart.getSpace();
		numberOfSpaces++;
		int space=getHeight()/(numberOfSpaces+2);
		int b;
		int scaleY=graphics.getFontMetrics(f).stringWidth(((Integer)chart.getMaxY()).toString());
		if(chart.getMaxY()>100)
			scaleY*=2;
		else scaleY*=3;
		int scale=(int)(getHeight()*0.125);
		int height=space-(int)(getHeight()*0.05);
		int one=(getWidth()-scaleY*2-20)/chart.getXYList().size();
		//draw y values
		for(int i=0;i<numberOfSpaces;i++) {
			int sub=0;
			b=(Integer)(chart.getMinY()+chart.getSpace()*i);
			while(b/10>=1) {
				b=b/10;
				sub++;
			}
			graphics.drawString(((Integer)(chart.getMinY()+chart.getSpace()*i)).toString(),scaleY-sub*9,(int) ((numberOfSpaces-i)*space+0.007*getHeight()));
			if(i!=numberOfSpaces) {
				graphics.setColor(Color.yellow);
				graphics.drawLine(scaleY+20, (numberOfSpaces-i)*space, (scaleY+20)+(chart.getXYList().size())*one+20,(numberOfSpaces-i)*space);
				graphics.setColor(Color.black);
			}
		}
		//draw x values
		for(int i=0;i<chart.getXYList().size();i++) {
			graphics.setColor(Color.yellow);
			graphics.drawLine((scaleY+20)+i*one,height,(scaleY+20)+i*one, getHeight()-scale);
			graphics.setColor(Color.black);
			graphics.drawString(((Integer)chart.getXYList().get(i).getX()).toString(),(scaleY+20)+i*one+one/2,getHeight()-(int)(scaleX*3));
			/*graphics.drawLine((scaleY+20)+i*one,getHeight()-scale,(scaleY+20)+i*one,((chart.getMaxY()-chart.getXYList().get(i).getY())/chart.getSpace()+1)*space);
			graphics.drawLine((scaleY+20)+(i+1)*one,getHeight()-scale,(scaleY+20)+(i+1)*one,((chart.getMaxY()-chart.getXYList().get(i).getY())/chart.getSpace()+1)*space);
			graphics.drawLine((scaleY+20)+(i)*one,((chart.getMaxY()-chart.getXYList().get(i).getY())/chart.getSpace()+1)*space,(scaleY+20)+(i+1)*one,((chart.getMaxY()-chart.getXYList().get(i).getY())/chart.getSpace()+1)*space);*/
			graphics.setColor(Color.orange);
			graphics.fillRect((scaleY+20)+(i)*one ,((chart.getMaxY()-chart.getXYList().get(i).getY())/chart.getSpace()+1)*space, one, getHeight()-scale-((chart.getMaxY()-chart.getXYList().get(i).getY())/chart.getSpace()+1)*space);
			graphics.setColor(Color.BLACK);
		}
		scaleX=(scaleY+20)+(chart.getXYList().size())*one+20;
		//draw the lines and arrows
		graphics.drawLine(scaleY+20,height,scaleY+20, getHeight()-scale);
		graphics.drawLine(scaleY+15,height,scaleY+25,height);
		graphics.drawLine(scaleY+25,height,scaleY+20,height-10);
		graphics.drawLine(scaleY+15,height,scaleY+20,height-10);
		graphics.drawLine(scaleY+20, getHeight()-scale, scaleX,getHeight()-scale);
		graphics.drawLine(scaleX,getHeight()-scale-5,scaleX,getHeight()-scale+5);
		graphics.drawLine(scaleX,getHeight()-scale-5,scaleX+10,getHeight()-scale);
		graphics.drawLine(scaleX,getHeight()-scale+5,scaleX+10,getHeight()-scale);
	}
	
	
	
	
	
	
	

}
