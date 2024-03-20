package ispit;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;


public class ExamLayoutManager implements LayoutManager2 {
	private Component[][] comp= new Component[2][2];
	public static String AREA1="AREA1";
	public static String AREA2="AREA2";
	public static String AREA3="AREA3";
	int percentage;
	
	public ExamLayoutManager(int percentage) {
		if (percentage>90 || percentage<10) {
			throw new IllegalArgumentException("Percentage must be within the range [10,90]");
		}
		this.percentage=percentage;
		
	}
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for(int i=0;i<2;i++) {
			for(int j=0;j<2;j++) {
				if(comp==this.comp[i][j])
					this.comp[i][j]=null;
			}
			
		}
		
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getDimension(parent);
	}
	
	public void setPercentage(int percentage, Container parent) {
		this.percentage=percentage;
		layoutContainer(parent);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getDimension(parent);
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getDimension(target);
	}
	
	public Dimension getDimension(Container parent) {
		return new Dimension(500, 500);
	}

	@Override
	public void layoutContainer(Container parent) {
		int width=parent.getWidth();
		int height=parent.getHeight(); 
		System.out.println(width+"x"+height);
		int[] heights=new int[2];
		int[] widths=new int[2];
		heights[0]=height*percentage/100;
		System.out.println(widths[0]+"x"+heights[0]);
		heights[1]=height*(1-(percentage/100));
		widths[0]=width*percentage/100;
		heights[1]=height*(1-(percentage/100));
		widths[1]=width*(1-(percentage/100));
		if(comp[0][1]!=null)
			comp[0][1].setBounds(0,0, width,10);
		if(comp[0][0]!=null)
		comp[0][0].setBounds(0,0, width,heights[0]);
		if(comp[1][0]!=null)
		comp[1][0].setBounds(0,heights[0], widths[0],heights[1]);
		if(comp[1][1]!=null)
		comp[1][1].setBounds(0,heights[0], widths[1],heights[1]);
		
	}
		

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(constraints==null) {
			this.comp[0][1]=comp;
			return;
		}
		if(!(constraints instanceof String)) {
			throw new IllegalArgumentException();
		}
		if(constraints==ExamLayoutManager.AREA1)
			this.comp[0][0]=comp;
		else if(constraints==ExamLayoutManager.AREA2)
			this.comp[1][0]=comp;
		else 
			this.comp[1][1]=comp;
		
	}


	@Override
	public float getLayoutAlignmentX(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
		
	}

}
