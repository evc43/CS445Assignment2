/** ReorderInterface interface for CS 0445 Assignment 2
 * @author Sherif Khattab (Adapted  from Dr. John Ramirez's Spring 2017 CS 0445 Assignment 1 code)
 * Carefully read the specifications for the methods below and
 * implement them in your LinkedDS<T> class.  As with the SequenceInterface<T>
 * interface, don't worry as much about efficiency here as you do
 * about correctness.
 */
public interface ReorderInterface {
	/** Logically reverse the data in the ReorderInterface object so that the item
	 * that was logically first will now be logically last and vice
	 * versa.  The physical implementation of this can be done in
	 * many different ways.
	 * Runtime: O(n)
	 */
	public void reverse();

	/** Remove the logically last item and put it at the
	 * front.  As with reverse(), this can be done physically in
	 * different ways depending on the underlying implementation.
	 * Runtime: O(1)
	 */
	public void rotateRight();

	/** Remove the logically first item and put it at the
	 * end.  As above, this can be done in different ways.
	 * Runtime: O(1)
	 */
	public void rotateLeft();

	/** (EXTRA CREDIT) Shuffle the items according to the given permutation. The permutation is defined using two parallel arrays, one containing
	 * the original positions and the other containing the new positions. For example, if oldPositions[0] contains 5 then newPositions[0] contains the
	 * new position for the item at position 5.
	 * Runtime: O(n); You may use arrays in this method.
	 * 
	 * @param oldPositions the int[] array of old positions
	 * @param newPositions the int[] array of new positions
	 * @throws IndexOutOfBoundsException if any of the old or new positions is < 0 or > size-1
	 * @throws IllegalArgumentException if oldPositions.length != newPositions.length or if there are duplicate values in either oldPositions or newPositions
	 */
	public void shuffle(int[] oldPositions, int[] newPositions);
}
