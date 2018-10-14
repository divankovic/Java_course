package hr.fer.zemris.java.gui.layouts;
import java.awt.Dimension;


import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class CalcLayoutTest {

	public static CalcLayout layout;
	
	
	@Test(expected = CalcLayoutException.class)
	public void illegalRowTest1() {
		layout = new CalcLayout();
		layout.addLayoutComponent(null, "0,2");
	}
	
	@Test(expected = CalcLayoutException.class)
	public void illegalRowTest2() {
		layout = new CalcLayout();
		layout.addLayoutComponent(null, "6,2");
	}
	
	@Test(expected = CalcLayoutException.class)
	public void illegalColumnTest1() {
		layout = new CalcLayout();
		layout.addLayoutComponent(null, "2,-1");
	}
	
	@Test(expected = CalcLayoutException.class)
	public void illegalColumnTest2() {
		layout = new CalcLayout();
		layout.addLayoutComponent(null, "2,8");
	}
	
	@Test(expected = CalcLayoutException.class)
	public void illegalFirstElementTest1() {
		layout = new CalcLayout();
		layout.addLayoutComponent(null, "1,4");
	}
	
	@Test(expected = CalcLayoutException.class)
	public void mulitpleComponentsOnSamePositionTest() {
		layout = new CalcLayout();
		layout.addLayoutComponent(new JLabel("A"), "1,3");
		layout.addLayoutComponent(new JLabel("B"), "1,3");
	}
	
	@Test
	public void dimensionTest1() {
		layout = new CalcLayout(2);
		JPanel p = new JPanel(layout);
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		Assert.assertEquals(152, dim.width);
		Assert.assertEquals(158, dim.height);
	}
	
	@Test
	public void dimensionTest2() {
		layout = new CalcLayout(2);
		JPanel p = new JPanel(layout);
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		Assert.assertEquals(152, dim.width);
		Assert.assertEquals(158, dim.height);
	}
	
	@Test
	public void dimensionTest3() {
		layout=new CalcLayout(2);
		JPanel p = new JPanel(layout);
		
		JLabel l1 = new JLabel("");
		l1.setMaximumSize(new Dimension(128, 20));
		
		JLabel l2 = new JLabel("");
		l2.setMaximumSize(new Dimension(30, 30));
		
		JLabel l3 = new JLabel("");
		l3.setMaximumSize(new Dimension(35, 15));
		
		p.add(l1, "1,1");
		p.add(l2, "2,1");
		p.add(l3, "2,2");
		
		Dimension dim = p.getMaximumSize();
		
		Assert.assertEquals(180, dim.width);
		Assert.assertEquals(83, dim.height);
	}
	
}
