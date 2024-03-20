package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.function.Function;
/**
 * Razred predstavlja implementaciju LayoutManagera
 * koji je kostur za prikaz Kalkulatora i njegovih
 * tipki
 * @author User
 *
 */
public class CalcLayout implements LayoutManager2{
	private Component[][] comp= new Component[5][7];
	int space;
	/**
	 * Prazni konstruktor promovira
	 * inicijalizaciju drugom, a vrijednost
	 * razmaka između elemenata stavlja na 0
	 */
	public CalcLayout() {
		this(0);
	}
	/**
	 * konstruktor u variablu space pohranjuje
	 * razmak između elemenata 
	 * @param space
	 */
	public CalcLayout(int space) {
		if (space<0)
			throw new IllegalArgumentException();
		this.space=space;
	}
	/**
	 * 
	 * @param text <code>String</code> formata "<code>int</code>, <code>int</code>"
	 * @return novi objekt tipa RCPosition
	 * @throws IllegalArgumentException ako je string neispravnog formata
	 */
	private static RCPosition parse(String text) {
		if(!text.matches("\\d{1},\\d{1}"))
			throw new IllegalArgumentException();
		String[] position=text.split(",");
		int row=Integer.parseInt(position[0]);
		int column=Integer.parseInt(position[1]);
		return new RCPosition(row, column);
	}

	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void layoutContainer(Container parent) {
		int width=parent.getWidth();
		int height=parent.getHeight(); 
		Insets ins=parent.getInsets();
		int[] heights=new int[5];
		int[] widths=new int[7];
		int elementGridHeight=height-ins.bottom-ins.top;
		int elementGridWidth=width-ins.right-ins.left;
		int oneElementSize=(elementGridHeight-space*4)/5;
		for(int i=0;i<5;i++) {
			heights[i]=oneElementSize;
		}
		int unfilled=(elementGridHeight-space*4)-oneElementSize*5;
		if(unfilled!=0) {
			int expanded=0;
			int j=0;
			while(true) {
				for(int i=j;i<5;i+=2) {
					heights[i]++;
					expanded++;
					if(expanded==unfilled)
						break;
				}if(expanded==unfilled)
					break;
				else
					j++;
			}
		}
		oneElementSize=(elementGridWidth-space*6)/7;
		for(int i=0;i<7;i++) {
			widths[i]=oneElementSize;
		}
		unfilled=(elementGridWidth-space*6)-oneElementSize*7;
		if(unfilled!=0) {
			int expanded=0;
			int j=0;
			while(true) {
				for(int i=j;i<7;i+=2) {
					widths[i]++;
					expanded++;
					if(expanded==unfilled)
						break;
				}if(expanded==unfilled)
					break;
				else
					j++;
			}
		}
		for(int i=0;i<7;i++) {
			widths[i]=(elementGridWidth-space*6)/7;
		}
		for(int i=0;i<heights.length;i++) {
			for(int j=0;j<widths.length;j++) {
					if(comp[i][j]!=null) {
						if(i==0&&j==0) {
							comp[i][j].setBounds((widths[j])*j+(space*(j-1)>0?space*(j-1):0)+ins.left,
									(heights[i])*i+space*(i-1)+ins.top, widths[j]*5+space*4,heights[i] );
							continue;
						}
						comp[i][j].setBounds((widths[j])*j+(space*(j-1)>0?space*(j-1):0)+ins.left,
								(heights[i])*i+space*(i-1)+ins.top, widths[j],heights[i] );
					}
			}
		}
		
		
	}

	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		return getDimension(arg0,Component->Component.getMinimumSize());
	}
	
	@Override
	public Dimension maximumLayoutSize(Container arg0) {
		return getDimension(arg0,Component->Component.getMaximumSize());
	}

	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		return getDimension(arg0,Component->Component.getPreferredSize());
	}

	@Override
	public void removeLayoutComponent(Component arg0) {
		for(int i=0;i<5;i++) {
			for(int j=0;j<7;j++) {
				if(arg0==comp[i][j])
					comp[i][j]=null;
			}
		}
		
	}

	@Override
	public void addLayoutComponent(Component arg0, Object arg1) {
		if(arg1==null)
			throw new NullPointerException();
		RCPosition position=null;
		if(arg1 instanceof String) {
			position=parse((String)arg1);
		}
		else if (arg1 instanceof RCPosition)
			position=(RCPosition)arg1;
		else
			throw new IllegalArgumentException();
		if(position.getRow()<1 || position.getRow()>5 ||
				position.getColumn() <1 || position.getColumn()>7||(position.getRow()==1 && position.getColumn()>1 &&position.getColumn()<6))
			throw new CalcLayoutException();
		if(comp[position.getRow()-1][position.getColumn()-1]!=null)
			throw new CalcLayoutException();
		comp[position.getRow()-1][position.getColumn()-1]=arg0;
		
		
		
	}
	
	public Dimension getDimension(Container parent, Function<Component,Dimension> getter) {
		int height=0;
		int width=0;
		for(int i=0;i<5;i++) {
			for(int j=0;j<7;j++) {
				if(comp[i][j]!=null) {
					Component child=comp[i][j];
					Dimension d;
					if ((d=getter.apply(child))!=null) {
						if(d.height>height)
							height=d.height;
						if(d.width>width)
							width=d.width;
					}
				}
			}
		}
		Insets ins=parent.getInsets();
		height=height*5+space*4+ins.bottom+ins.top;
		width=width*7+space*6+ins.right+ins.left;
		return new Dimension(width, height);
	}

	@Override
	public float getLayoutAlignmentX(Container arg0) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container arg0) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
