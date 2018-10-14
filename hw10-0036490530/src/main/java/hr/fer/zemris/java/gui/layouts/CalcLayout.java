package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Custom layout which lays out it's components in a 5*7 grid.<br>
 * The calculator screen is located at position 1,1 in this layout and expands
 * over 5 grid locations taking up locations 1,1-1,5.
 * <p>
 * Legal layout positions are (x,y) where x is in range [1,5] (only whole
 * numbers), y is in range [1,7] with the exception of the first row where legal
 * positions are (1,1), (1,6), (1,7) as stated earlier.
 * </p>
 * 
 * @author Dorian Ivankovic
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Rows in this layout.
	 */
	public static final int rows = 5;

	/**
	 * Columsn in this layout.
	 */
	public static final int columns = 7;

	/**
	 * Gap(in pixels) between layout elements.
	 */
	private int gap;

	/**
	 * Containes component mapped to their position in the layout.
	 */
	private HashMap<Component, RCPosition> elements;

	/**
	 * Constructs a new {@link CalcLayout} with gap between elements of 0.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructs a new {@link CalcLayout} with gap set to <code>gap</code>.
	 * 
	 * @param gap
	 *            - gap between the elements in the layout
	 * @throws CalcLayoutException
	 *             - if gap is &lt;0
	 *             
	 */
	public CalcLayout(int gap) {
		if (gap < 0)
			throw new CalcLayoutException("Gap must be >=0, was :" + gap);
		this.gap = gap;
		elements = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(String name, Component component) {
	}

	@Override
	public void layoutContainer(Container container) {

		Insets insets = container.getInsets();
		int componentCount = container.getComponentCount();

		if (componentCount == 0)
			return;

		int realWidth = container.getWidth() - (insets.left + insets.right);
		int componentWidth = (realWidth - (columns - 1) * gap) / columns;
		int extraWidth = (realWidth - componentWidth * columns - gap * (columns - 1)) / 2;

		int realHeight = container.getHeight() - (insets.top + insets.bottom);
		int componentHeight = (realHeight - (rows - 1) * gap) / rows;
		int extraHeight = (realHeight - componentHeight * rows - gap * (rows - 1)) / 2;

		int xInitial = insets.left + extraWidth;
		int yInitial = insets.top + extraHeight;

		for (int i = 0; i < componentCount; i++) {

			Component component = container.getComponent(i);
			RCPosition position = elements.get(component);
			int row = position.getRow();
			int column = position.getColumn();

			if (row == 1 && column == 1) {
				component.setBounds(xInitial, yInitial, componentWidth * 5 + 4 * gap, componentHeight);
			} else {
				component.setBounds(xInitial + (column - 1) * (componentWidth + gap),
						yInitial + (row - 1) * (componentHeight + gap), componentWidth, componentHeight);
			}
		}

	}

	@Override
	public Dimension minimumLayoutSize(Container container) {
		return calculateDimension(container, (x, y) -> x>y, component -> component.getMinimumSize());
	}

	@Override
	public Dimension preferredLayoutSize(Container container) {
		return calculateDimension(container, (x, y) -> x>y, component -> component.getPreferredSize());
	}

	/**
	 * Calculates the specific dimension of the container (minimimum, maximum,
	 * preffered), using <code>determinator</code> which is used to determining size
	 * and <code>dimensionGetter</code> that gets the specified dimension from all
	 * components in the container.
	 * 
	 * @param container
	 *            - the dimension calculation is done for this container
	 * @param determinator
	 *            - used for determining whether or not to change the size of the
	 *            container,<br>
	 *            - for minimimum and preferred size the size changes when the
	 *            component't size is larger than current, and<br>
	 *            - for maximum size the current size changes when the component't
	 *            size is less than current
	 * @param dimensionGetter
	 *            - gets the specified dimension(minimum, maximum, preffered) from
	 *            all components in the container
	 * @return correct dimension of the <code>container</code>
	 */
	private Dimension calculateDimension(Container container, BiFunction<Integer, Integer, Boolean> determinator,
			Function<Component, Dimension> dimensionGetter) {
		
		Insets insets = container.getInsets();
		int componentsCount = container.getComponentCount();

		int width = 0;
		int height = 0;

		for (int i = 0; i < componentsCount; i++) {
			Component component = container.getComponent(i);
			Dimension dim = dimensionGetter.apply(component);

			if (dim == null)
				continue;

			RCPosition pos = elements.get(component);
			if (pos.getRow() == 1 && pos.getColumn() == 1) {
				width = (int) Math.ceil((dim.width - 4 * gap) / 5.0);
				height = dim.height;
				continue;
			}
			if (determinator.apply(dim.width, width))
				width = dim.width;
			if (determinator.apply(dim.height, height))
				height = dim.height;
		}

		int totalWidth = columns * width + (columns - 1) * gap + insets.left + insets.right;
		int totalHeight = rows * height + (rows - 1) * gap + insets.top + insets.bottom;
		return new Dimension(totalWidth, totalHeight);
	}

	@Override
	public void removeLayoutComponent(Component component) {
		elements.remove(component);
	}

	/**
	 * Adds the specified component to the layout, using the specified constraint
	 * object.<br>
	 * Constraint can be either {@link RCPosition} or a {@link String} that can be
	 * parsed to <code>RCPosition</code>, that is in format "x,y" where x and y are
	 * whole numbers representing the row and the column in the layout.<br>
	 * Legal positions of the layout are described in {@link CalcLayout}.
	 * 
	 * @param component
	 *            - component to add to the layout
	 * @param constraint
	 *            - constraint specifying where in the layout to add the component
	 * @throws CalcLayoutException
	 *             if the position in the layout is illegal or
	 *             <code>constraint</code> is not an instance of the specified
	 *             classes
	 */
	@Override
	public void addLayoutComponent(Component component, Object constraint) {
		if (!(constraint instanceof RCPosition) && !(constraint instanceof String)) {
			throw new CalcLayoutException("Illegal constraint argument, RCPosition expected");
		}

		RCPosition position;
		if (constraint instanceof RCPosition) {
			position = (RCPosition) constraint;
		} else {
			String pos = (String) constraint;

			try {
				String[] coordinates = pos.split(",");
				if (coordinates.length != 2)
					throw new CalcLayoutException("Two coordinates separated by ',' expected.");

				int x = Integer.parseInt(coordinates[0]);
				int y = Integer.parseInt(coordinates[1]);

				position = new RCPosition(x, y);

			} catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
				throw new CalcLayoutException("Illegal RCPosition string format, expectd \"x,y\" where x and y are positive whole numbers, was : "+ pos);
			}
		}

		if (elements.containsValue(position))
			throw new CalcLayoutException("Component at position " + position + " already exists.");
		if (!legalPosition(position.getRow(), position.getColumn()))
			throw new CalcLayoutException("Illegal position, (1-5, 1-7) allowed, without (1,2)-(1,5). Was " + position);

		elements.put(component, position);
	}

	/**
	 * The method checks if the position at (row,column) is legal for this layout,
	 * legal positions are described in {@link CalcLayout}.
	 * 
	 * @param row
	 *            - row of the position
	 * @param column
	 *            - column of the position
	 * @return true if the position is legal, false otherwise
	 */
	private boolean legalPosition(int row, int column) {
		
		if (row <= 0 || row > 5)
			return false;
		
		if (column <= 0 || column > 7)
			return false;
		
		if (row == 1 && column > 1 && column < 6)
			return false;
		
		return true;
	}

	@Override
	public float getLayoutAlignmentX(Container container) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container container) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container container) {
	}

	@Override
	public Dimension maximumLayoutSize(Container container) {
		return calculateDimension(container, (x, y) -> x < y, component -> component.getMaximumSize());
	}

}
