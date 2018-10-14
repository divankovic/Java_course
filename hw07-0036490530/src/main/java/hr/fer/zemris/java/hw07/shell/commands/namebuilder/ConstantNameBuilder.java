package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

/**
 * Used for building constant parts in new name expression i.e parts not bound with ${}.
 * @author Dorian Ivankovic
 *
 */
public class ConstantNameBuilder implements NameBuilder {

	/**
	 * Constant to write into <code>NameBuilderInfo</code> once {@link #execute(NameBuilderInfo) is called
	 */
	private String constant;
	
	/**
	 * Constructs a new <code>ConstantNameBuilder</code> using 
	 * a constant that needs to be writtes into <code>NameBuilderInfo</code>.
	 * @param constant - string contant
	 */
	public ConstantNameBuilder(String constant) {
		this.constant = constant;
	}
	
	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(constant);
	}

}
