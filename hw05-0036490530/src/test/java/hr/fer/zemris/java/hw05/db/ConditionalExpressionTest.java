package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class ConditionalExpressionTest {
	
	@Test
	public void testConditionalExpression(){
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 ComparisonOperators.LIKE,
				 "Bos*"
				);
		StudentRecord record = new StudentRecord("0020","Bossman","John",5);
		Assert.assertTrue(expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), expr.getLiteral()));

	}
}
