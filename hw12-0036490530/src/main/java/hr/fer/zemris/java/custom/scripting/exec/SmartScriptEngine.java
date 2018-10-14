package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstant;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.EndNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The class is used to perform semantic analysis and execute the smartscript document
 * e.g the parsed tree it obtains from {@link SmartScriptParser}.
 * If something goes wrong during semantic analysis a {@link SmartScriptParserException} is thrown.
 * @author Dorian Ivankovic
 *
 */
public class SmartScriptEngine {

	/**
	 * Root node of the smart script document' tree.
	 */
	private DocumentNode documentNode;
	
	/**
	 * Used for writing out result of executing the smartscript.
	 */
	private RequestContext requestContext;
	
	/**
	 * Used for semantic analysis and parameters during execution.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * Operations that can be performed on values.
	 */
	private Map<String, BiConsumer<ValueWrapper, ValueWrapper>> operations;
	
	/**
	 * Available functions.
	 */
	private Map<String, Consumer<Stack<ValueWrapper>>> functions;
	
	/**
	 * A visitor that executes each {@link Node}.
	 * A {@link TextNode} is executed by writing data to {@link RequestContext},
	 * a {@link ForLoopNode} is executed by executing all nodes inside it
	 * while the variable is different from end expression, changing it's value
	 * by step expression of the node, {@link EchoNode} performs given operations and
	 * functions, and the {@link DocumentNode} is executed by recursively
	 * executing all it's nodes.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			if(node instanceof EndNode) return;
			try {
				String text = node.getText();
				text = text.replaceAll(" +$", "");
				if(text.startsWith("\r\n")) {
					text = text.substring(2);
				}
				requestContext.write(text);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String varName = node.getVariable().getName();
			ValueWrapper start = new ValueWrapper(node.getStartExpression().asText());
			ValueWrapper step = new ValueWrapper(node.getStepExpression().asText());
			ValueWrapper end = new ValueWrapper(node.getEndExpression().asText());

			multistack.push(varName, start);

			while (start.numCompare(end.getValue()) <= 0) {
				for (Node n : node.getChildren()) {
					n.accept(this);
				}
				start.add(step.getValue());
			}

			multistack.pop(varName);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<ValueWrapper> tempStack = new Stack<>();

			for (Element element : node.getElements()) {
				
				if (element instanceof ElementConstant) {
					tempStack.push(new ValueWrapper(element.asText()));
				} else if (element instanceof ElementVariable) {
					tempStack.push(multistack.peek(((ElementVariable) element).getName()).copy());
				} else if (element instanceof ElementOperator) {
					ValueWrapper second = tempStack.pop();
					ValueWrapper first = tempStack.pop();

					String operator = ((ElementOperator) element).getOperator();
					BiConsumer<ValueWrapper, ValueWrapper> operation = operations.get(operator);
					
					if(operation!=null) {
						operation.accept(first, second);
					}else {
						throw new UnsupportedOperationException("Operation "+operator+" is not supported.");
					}
					
					tempStack.push(first);
				
				}else if(element instanceof ElementFunction) {
					
					String functionName = ((ElementFunction) element).getName();
					Consumer<Stack<ValueWrapper>> function = functions.get(functionName);
					
					if(function!=null) {
						function.accept(tempStack);
					}else {
						throw new UnsupportedOperationException("Function "+functionName+" is not supported.");
					}
				
				}
			}
			
			if(!tempStack.empty()) {
				List<ValueWrapper> elementsLeft = new ArrayList<>();
				
				while(!tempStack.empty()) {
					elementsLeft.add(0, tempStack.pop());
				}
				
				elementsLeft.forEach(element->{
					try {
						requestContext.write(element.getValue().toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
		
			try {
				for (Node n : node.getChildren()) {
					n.accept(this);
				}
			}catch(Exception ex) {
				//operations on wrong argument types, empty stack,... can cause the exception
				throw new SmartScriptEngineException(ex.getLocalizedMessage());
			}
			
		}

	};

	/**
	 * Creates a new {@link SmartScriptEngine} by using the root node
	 * of the parsed smartscript tree to execute, and a context to write output to.
	 * @param documentNode - root node of the smartscript tree to execute
	 * @param requestContext - {@link RequestContext} used for writing result data
	 * @throws NullPointerException if documentNode or requestContext is null
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {

		Objects.requireNonNull(documentNode, "Document must not be null.");
		Objects.requireNonNull(requestContext, "Request context must not be null.");
		this.documentNode = documentNode;
		this.requestContext = requestContext;

		createOperations();
		createFunctions();
	}
	
	/**
	 * Executes the smartScript by using {@link INodeVisitor}.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
	
	/**
	 * Creates supportedOperations(+, -, *, /).
	 */
	private void createOperations() {
		operations = new HashMap<>();
		operations.put("+", (a,b)->a.add(b.getValue()));
		operations.put("-", (a,b)->a.subtract(b.getValue()));
		operations.put("*", (a,b)->a.multiply(b.getValue()));
		operations.put("/", (a,b)->a.divide(b.getValue()));
	}
	
	/**
	 * Creates supported functions:
	 * <ul>
	 * 	<li>sin(x)- calculates the sinus of given argument</li>
	 * 	<li>decfmt(x,f) - formats decimal number using given format f</li>
	 * 	<li>dup() - duplicates current top value of the stack</li>
	 * 	<li>swap() - replaces the order of two topmost items on stack.</li>
	 * 	<li>setMimeType() - sets the myme type of the context's header</li>
	 * 	<li>paramGet() - gets the parameter from context</li>
	 * 	<li>pparamGet() - gets the persistent parameter from context</li>
	 * 	<li>pparamSet() - sets the persistent parameter in context</li>
	 * 	<li>pparamDel() - deletes the persistent parameter from context</li>
	 * 	<li>tparamGet() - gets the temporary parameter from context</li>
	 * 	<li>tparamSet() - sets the temporary parameter in context</li>
	 * 	<li>tparamDel() - deletes the temporary parameter from context</li>
	 * </ul>
	 */
	private void createFunctions() {
		functions = new HashMap<>();
		
		functions.put("sin", stack->{
			ValueWrapper value = stack.pop();
			stack.push(new ValueWrapper(Math.sin(Double.parseDouble(value.getValue().toString()))));
		});
		
		functions.put("decfmt", stack->{
			DecimalFormat format = new DecimalFormat(stack.pop().getValue().toString());
			Double number = Double.parseDouble(stack.pop().getValue().toString());
			
			stack.push(new ValueWrapper(format.format(number)));
		});
		
		functions.put("dup", stack->{
			stack.push(stack.peek().copy());
		});
		
		functions.put("swap", stack->{
			ValueWrapper a = stack.pop();
			ValueWrapper b = stack.pop();
			
			stack.push(a);
			stack.push(b);
		});
		
		functions.put("setMimeType", stack->{
			requestContext.setMimeType(stack.pop().getValue().toString());
		});
		
		BiConsumer<Stack<ValueWrapper>, Function<String, String>> paramGetter = (stack,getter)->{
			ValueWrapper defValue = stack.pop();
			String name = stack.pop().getValue().toString();
			
			String value = getter.apply(name);
			stack.push(value==null? defValue : new ValueWrapper(value));
		};
		
		BiConsumer<Stack<ValueWrapper>, BiConsumer<String, String>> paramSetter=(stack,setter)->{
			String name = stack.pop().getValue().toString();
			String value = stack.pop().getValue().toString();
			setter.accept(name, value);
		};
		
		BiConsumer<Stack<ValueWrapper>, Consumer<String>> paramDeletor = (stack, deleter)->{
			String name = stack.pop().getValue().toString();
			deleter.accept(name);
		};
		
		functions.put("paramGet", stack->{
			paramGetter.accept(stack, name->requestContext.getParameter(name));
		});
		
		functions.put("pparamGet", stack->{
			paramGetter.accept(stack, name->requestContext.getPersistentParameter(name));
		});
		
		functions.put("pparamSet", stack->{
			paramSetter.accept(stack, (name,value)->requestContext.setPersistentParameter(name, value));
		});
		
		functions.put("pparamDel", stack->{
			paramDeletor.accept(stack, name->requestContext.removePersistentParameter(name));
		});
		
		functions.put("tparamGet", stack->{
			paramGetter.accept(stack, name->requestContext.getTemporaryParameter(name));
		});
		
		functions.put("tparamSet", stack->{
			paramSetter.accept(stack, (name,value)->requestContext.setTemporaryParameter(name, value));
		});
		
		functions.put("tparamDel", stack->{
			paramDeletor.accept(stack, name->requestContext.removeTemporaryParameter(name));
		});
		
	}


}
