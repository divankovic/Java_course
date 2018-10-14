package hr.fer.zemris.java.gui.prim;

import javax.swing.JList;

import org.junit.Assert;
import org.junit.Test;


public class PrimListModelTest {
	static PrimListModel model = new PrimListModel();
	
	
	@Test
	public void testPrimList() {
		JList<Integer> list = new JList<>(model);
		
		Assert.assertEquals(1, list.getModel().getSize());
		Assert.assertEquals(1, list.getModel().getElementAt(0).intValue());
		
		model.next();
		model.next();
		
		Assert.assertEquals(3, list.getModel().getSize());
		Assert.assertEquals(1, list.getModel().getElementAt(0).intValue());
		Assert.assertEquals(2, list.getModel().getElementAt(1).intValue());
		
		for(int i = 0;i<100;i++) {
			model.next();
		}
		
		Assert.assertEquals(103, list.getModel().getSize());
		Assert.assertEquals(557, list.getModel().getElementAt(102).intValue());
		
	}
}
