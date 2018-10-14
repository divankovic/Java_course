package hr.fer.zemris.java.gui.layouts;

/**
 * Represents one element's position in the {@link CalcLayout}.
 * @author Dorian Ivankovic
 *
 */
public class RCPosition {
	
	/**
	 * Row of the position.
	 */
	private int row;
	
	/**
	 * Column of the position.
	 */
	private int column;
	
	/**
	 * Constructs a new {@link RCPosition} using row and column.
	 * @param row - row of the position
	 * @param column - column of the position
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns the row of this position.
	 * @return row of this position
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column of this position.
	 * @return column of this position.
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + column;
		result = prime * result + row;
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		RCPosition other = (RCPosition) obj;
		
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		return "("+row+", "+column+")";
	}
	
	

}
