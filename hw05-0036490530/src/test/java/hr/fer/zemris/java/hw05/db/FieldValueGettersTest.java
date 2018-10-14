package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class FieldValueGettersTest {
	
	@Test
	public void testGetters() {
		StudentRecord record = new StudentRecord("0000000001","Horvat","Joža",5);
		
		Assert.assertEquals(FieldValueGetters.FIRST_NAME.get(record),"Joža");
		Assert.assertEquals(FieldValueGetters.LAST_NAME.get(record),"Horvat");
		Assert.assertEquals(FieldValueGetters.JMBAG.get(record),"0000000001");
	}
	
	
}
