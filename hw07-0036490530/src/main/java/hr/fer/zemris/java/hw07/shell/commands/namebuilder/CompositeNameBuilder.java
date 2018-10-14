package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * A special case of {@link NameBuilder} that contains other <code>NameBuilder</code>'s
 * and performs its <code>execute</code> method by invoking all contained <code>NameBuilder</code>'s {@link #execute(NameBuilderInfo)}
 * method
 * @author Dorian Ivankovic
 *
 */
public class CompositeNameBuilder implements NameBuilder {

	/**
	 * Collection of <code>NameBuilder</code>'s used in execute method.
	 */
	private List<NameBuilder> builders;
	
	/**
	 * Constructs a new <code>CompositeNameBuilder</code> using builders whose
	 * execute method is invoked in this execute method.
	 * @param builders - notified by invoking execute on all of them once execute on this object is called
	 */
	public CompositeNameBuilder(List<NameBuilder> builders) {
		this.builders = new ArrayList<>();
		this.builders.addAll(builders);
	}
	
	@Override
	public void execute(NameBuilderInfo info) {
		builders.forEach(builder -> builder.execute(info));
	}

}
