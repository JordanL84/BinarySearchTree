package implementation;

/**
 * Thrown when a tree is empty. 
 * 
 * @author Department of Computer Science, UMCP
 * 
 */
public class TreeIsEmptyException extends Exception {
	private static final long serialVersionUID = 1L;

	public TreeIsEmptyException(String message) {
		super(message);
	}
}
