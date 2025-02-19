package implementation;

/**
 * Thrown when a tree is full. 
 * 
 * @author Department of Computer Science, UMCP
 * 
 */
public class TreeIsFullException extends Exception {
	private static final long serialVersionUID = 1L;

	public TreeIsFullException(String message) {
		super(message);
	}
}
