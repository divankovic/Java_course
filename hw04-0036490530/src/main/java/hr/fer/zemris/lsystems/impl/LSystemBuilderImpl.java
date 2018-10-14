package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Arrays;
import java.util.Objects;

import hr.fer.zemris.java.hw04.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * A builder of the {@link LSystem} used to draw the structure
 * genereted by the productions of the <code>LSystem</code>.	
 * For more information about lsystems please see <a href=L-system>https://en.wikipedia.org/wiki/L-system</a>. 
 * @author Dorian Ivankovic
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Maximum angle used in rotation, all larger angles are scaled
	 * to [0, 360].
	 */
	public static final double MAX_ANGLE = 360;

	/**
	 * Current {@link Context} of the <code>LSystem</code>.
	 */
	private Context context;
	
	/**
	 * Dictionary of {@link Command}'s used in <code>LSystem</code>
	 * structure drawing.
	 */
	private Dictionary commands;
	
	/**
	 * Dictionary of productions used to generate the <code>LSystem</code>'s structure.
	 */
	private Dictionary productions;

	/**
	 * Length of the one unit step used when drawing lsystem's structure.
	 */
	private double unitLength;
	
	/**
	 * Used to scale the <code>unitLength</code> when using more levels of
	 * the productions using formula unitLength = unitLength*(unitLengthDegreeScaler^d)
	 * where d is number of levels in the production.
	 */
	private double unitLengthDegreeScaler;
	
	/**
	 * Starting point in building the lsystem represented as
	 * a {@link Vector2D}.
	 */
	private Vector2D origin;
	
	/**
	 * Curent direction (angle) of drawing lines when constructing the lsystem.
	 */
	private double angle;
	
	/**
	 * Starting symbol used for productions.
	 */
	private String axiom;


	/**
	 * Default constructor for the <code>LSystemBuilderImpl</code>
	 */
	public LSystemBuilderImpl() {
		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
		commands = new Dictionary();
		productions = new Dictionary();
	}

	/**
	 * Starts building the lsystem by creating an instance of {@link LSystem}.
	 */
	public LSystem build() {
		return new TurtleLSystem();
	}

	
	/**
	 * Configures the parameters of the {@link LSystemBuilder} from
	 * text. Valid directives are :
	 * <ul>
	 * 	<li>origin - used to initialise the origin of the <code>LSystemBuilderImpl</code></li>
	 * 	<li>angle - used to initialise the angle of the <code>LSystemBuilderImpl</code>, the value is scaled to [0, 360]</li>
	 * 	<li>unitLength - used to initialise the unitLength of the <code>LSystemBuilderImpl</code></li> 
	 * 	<li>unitLenghtDegreeScalar - used to initialise the unitLengthDegreeScaler in the <code>LSystemBuilderImpl</code></li>
	 * 	<li>command - add's a new command
	 * 		Valid commands are : <ul>
	 * 			<li>draw s - moves the turtle in the current direction of the turtle and updates the positon of the turtle</li>
	 * 			<li>skip s - like draw, but doesn't leave a coloured trail behind the turtle</li>
	 * 			<li>scale s - updates the effective unitLength size by multiplying by factor s</li>
	 * 			<li>rotate a - rotates the direction of the turtle by a - in degrees</li>
	 * 			<li>push - pushes the current {@link TurtleState} on the top of {@link Context}</li>
	 * 			<li>pop - returns the last {@link TurtleState} stored in {@link Context}</li>
	 * 			<li>color rrggbb - sets the color used when drawing</li>
	 * 		</ul>
	 * 	<li>axiom - used to initialise the axiom of the <code>LSystemBuilderImpl</code></li>
	 * 	<li>production - defines the string replacing the symbol, must be unique for each symbol - ex. production F F+F
	 * 		replaces "F" with "F+F"</li>
	 * </ul>
	 * Example usage : 
	 * <br> "origin 0.05 0.4",
	 * <br> "angle 0",
	 * <br> "unitLength 0.9",
	 * <br> "unitLengthDegreeScaler 1.0 / 3.0",
	 * <br> "",
	 * <br> "command F draw 1",
	 * <br> "command + rotate 60",
	 * <br> "command - rotate -60",
	 * <br> "",
	 * <br> "axiom F",
	 * <br> "",
	 * <br> "production F F+F--F+F"
	 * 
	 * @throws IllegalArgumentException - if the arguments are not in specified format
	 * 			NumberFormatException if specific arguments can't be parsed to numeric values
	 */
	public LSystemBuilder configureFromText(String[] lines) {

		for (String line : lines) {
			if (!line.isEmpty()) {
				String[] elements = line.split("\\s++");
				String field = elements[0];

				switch (field.toLowerCase()) {
				case "origin":
					parseOrigin(elements);
					break;
				case "angle":
					parseAngle(elements);
					break;
				case "unitlength":
					parseUnitLength(elements);
					break;
				case "unitlengthdegreescaler":
					parseUnitLengthScaler(elements);
					break;
				case "command":
					parseCommand(elements);
					break;
				case "axiom":
					if (elements.length != 2) {
						throw new IllegalArgumentException("No argument for axiom");
					}
					axiom = elements[1];
					break;
				case "production":
					parseProduction(elements);
					break;
				default:
					throw new IllegalArgumentException(field + " directive couldn't be recognised");
				}

			}

		}
		return this;
	}

	/**
	 * Parses the command and adds it to commands.
	 * @param elements - elements of the String
	 * * @throws IllegalArgumentException - if the arguments are not in specified format
	 *
	 */
	private void parseCommand(String[] elements) {
		if (elements.length < 3) {
			throw new IllegalArgumentException("Illegal production specification, symbol must be followed by command.");
		}
		String symbol = elements[1];
		if (commands.get(symbol) != null)
			throw new IllegalArgumentException("Command for " + symbol + " already exists.");

		Command command = getCommand(Arrays.copyOfRange(elements, 2, elements.length));		
	
		commands.put(symbol.charAt(0),command);

	}

	/**
	 * Returns the specific {@link Command}.
	 * @param elements - elements of the String
	 * @return new command of specific type as explained in {@link #configureFromText(String[])}
	 * @throws IllegalArgumentException - if the arguments are not in specified format
	 * 			NumberFormatException if specific arguments can't be parsed to numeric values
	 *
	 */
	private Command getCommand(String[] elements) {
		Command command = null;
		String action = elements[0];
		
		if (elements.length == 1) {

			switch (action) {
			case "push":
				command = new PushCommand();
				break;
			case "pop":
				command = new PopCommand();
				break;
			}

		} else if (elements.length == 2) {

			double argument=0;
			int color=0;
			try {
				if (!action.equals("color")) {
					argument = Double.parseDouble(elements[1]);
				} else {
					color = Integer.parseInt(elements[1], 16);
				}
			} catch (NumberFormatException ex) {
				throw new NumberFormatException(elements[1] +"is not a valid command argument.");
			}
			
			switch (action) {
			case "draw":
				command = new DrawCommand(argument);
				break;
			case "skip":
				command = new SkipCommand(argument);
				break;
			case "scale":
				command = new ScaleCommand(argument);
				break;
			case "rotate":
				command = new RotateCommand(argument);
				break;
			case "color":
				command = new ColorCommand(new Color(color));
				break;
			}

		} else {
			throw new IllegalArgumentException(
					"Too many parameters, command must have 1 or 2 - command name + parameter.");
		}
		
		return command;
	}

	/**
	 * Parses the String elements to production as explained in {@link #configureFromText(String[])}
	 * @param elements - input String elements
	 * @throws IllegalArgumentException - if the arguments are not in specified format
	 *
	 */
	private void parseProduction(String[] elements) {
		if (elements.length < 3) {
			throw new IllegalArgumentException(
					"Illegal production specification, symbol must be followed by an expression.");
		}
		String symbol = elements[1];
		if (productions.get(symbol) != null)
			throw new IllegalArgumentException("Production for " + symbol + " already exists.");

		StringBuilder production = new StringBuilder();
		for (int i = 2; i < elements.length; i++) {
			production.append(elements[i]);
		}

		productions.put(symbol.charAt(0), production.toString());
	}

	/**
	 * Parses the unitLengthScaler from String elements.
	 * @param elements - values to parse
	 * @throws IllegalArgumentException - if the arguments are not in specified format
	 * 			NumberFormatException if specific arguments can't be parsed to numeric values
	 */
	private void parseUnitLengthScaler(String[] elements) {
		if (elements.length < 2)
			throw new IllegalArgumentException(
					"unitLenghtDegreeScaler must be a decimal number or an expression a/b, where a and be are decimal numbers.");

		StringBuilder expressionBuilder = new StringBuilder();
		for (int i = 1; i < elements.length; i++) {
			expressionBuilder.append(elements[i]);
		}

		String expression = expressionBuilder.toString();
		String[] factors = expression.split("/");

		try {
			double a = Double.parseDouble(factors[0]);
			double b = 1;
			if (factors.length > 1) {
				b = Double.parseDouble(factors[1]);
			}

			this.unitLengthDegreeScaler = a / b;

		} catch (NumberFormatException ex) {
			throw new NumberFormatException(expression + " couldn't be parsed into a scaler value.");

		}

	}

	
	/**
	 * Parses the unitLength from String elements.
	 * @param elements - values to parse
	 * @throws IllegalArgumentException - if the arguments are not in specified format
	 * 			NumberFormatException if specific arguments can't be parsed to numeric values
	 *
	 */
	private void parseUnitLength(String[] elements) {
		if (elements.length != 2) {
			throw new IllegalArgumentException("unitLength argument must be one decimal number");
		}

		try {
			double unitLength = Double.parseDouble(elements[1]);
			this.unitLength = unitLength;
		} catch (NumberFormatException ex) {
			throw new NumberFormatException(elements[1] + " can't be parsed to a valid unitLength.");
		}
	}

	/**
	 * Parses the angle as specified in {@link #configureFromText(String[])}.
	 * @param elements
	 * @throws IllegalArgumentException - if the arguments are not in specified format
	 * 			NumberFormatException if specific arguments can't be parsed to numeric values
	 *
	 */
	private void parseAngle(String[] elements) {
		if (elements.length != 2) {
			throw new IllegalArgumentException("angle argument must be one decimal number");
		}

		try {
			double angle = Double.parseDouble(elements[1]) % MAX_ANGLE;
			this.angle = angle;
		} catch (NumberFormatException ex) {
			throw new NumberFormatException(elements[1] + " can't be parsed to a valid angle.");
		}
	}

	/**
	 * Parses the origin as specified in {@link #configureFromText(String[])}.
	 * @param elements
	 * @throws IllegalArgumentException - if the arguments are not in specified format
	 * 			NumberFormatException if specific arguments can't be parsed to numeric values
	 * 
	 */
	private void parseOrigin(String[] elements) {
		if (elements.length != 3) {
			throw new IllegalArgumentException("Origin must have 2 arguments - x,y of the origin");
		}

		try {
			double x = Double.parseDouble(elements[1]);
			double y = Double.parseDouble(elements[2]);

			if (x > 1 || y > 1) {
				throw new IllegalArgumentException("Origin point is out of screen, x and y must be <1");
			}

			this.origin = new Vector2D(x, y);
		} catch (NumberFormatException ex) {
			throw new NumberFormatException(elements[1] + " and " + elements[2] + " could't be parsed into double!");
		}
	}

	
	/**
	 * Registers a new command in commands. See {@link #configureFromText(String[])} for command formatting.
	 * @throws IllegalArgumentException - if the arguments are not in specified format
	 * 			NumberFormatException if specific arguments can't be parsed to numeric values
	 */
	public LSystemBuilder registerCommand(char symbol, String command) {
		Command com = getCommand(command.split("\\s+"));
		commands.put(symbol, com);
		return this;
	}

	
	/**
	 * Registers a new production in productions.
	 * See {@link #configureFromText(String[])} for production formatting.
	 * @throws IllegalArgumentException - if the arguments are not in specified format
	 * 			NumberFormatException if specific arguments can't be parsed to numeric values
	 */
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}

	/**
	 * Sets the current angle of the turtle.
	 * @param angle - new angle of the turtle
	 * @return current LSystemBuilder
	 */
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Sets the current axiom.
	 * @param axiom - new axiom of the turtle
	 * @return current LSystemBuilder
	 */
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	
	/**
	 * Sets the current origin of the turtle.
	 * @param x - new x  origin of the turtle
	 * @param y - new y origin of the turtle
	 * @return current LSystemBuilder
	 */
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	
	/**
	 * Sets the current unitLength of the turtle.
	 * @param unitLength - new unitLength of the turtle
	 * @return current LSystemBuilder
	 */
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	
	/**
	 * Sets the current unitLengthDegreeScaler of the turtle.
	 * @param unitLengthDegreeScaler - new unitLengthDegreeScaler of the turtle
	 * @return current LSystemBuilder
	 */
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

	
	/**
	 * Class is used to draw the current <code>LSystem</code>'s structure
	 * and calculate the productions.
	 * @author Dorian Ivankovic
	 *
	 */
	private class TurtleLSystem implements LSystem {

		/**
		 * Draws the structure of the current <code>LSystem</code> using
		 * {@link Painter}.
		 * @param level - level of productions
		 * @param painter - painter used for drawing
		 * @throws NullPointerException if painter is null
		 */
		public void draw(int level, Painter painter) {
			Objects.requireNonNull(painter);
			context = new Context();
			
			TurtleState startState = new TurtleState(origin,Vector2D.normalisedVectorFromAngle(angle),
					Color.BLACK, unitLength*Math.pow(unitLengthDegreeScaler, level));
			context.pushState(startState);
			
			String productions = generate(level);
			char[] productionElements = productions.toCharArray();
			
			for(char c : productionElements) {
				Command command = (Command) commands.get(c);
				if(command!=null) {
					command.execute(context, painter);
				}
			}
			
		}

		/**
		 * Generates the String represenation of the result when level number
		 * of productions are used on axiom.
		 * @param level - level of productions
		 * @return result of level productions
		 */
		public String generate(int level) {
			
			String start = axiom;
			while(level>0) {
				char[] startElements = start.toCharArray();
				StringBuilder productionBuilder = new StringBuilder();
				
				for(char c : startElements) {
					String production = (String) productions.get(c);
					if(production!=null) {
						productionBuilder.append(production);
					}else {
						productionBuilder.append(c);
					}
				}
				
				start = productionBuilder.toString();
				level--;
			}
			
			return start;
			
			
			
		}

	}

}
