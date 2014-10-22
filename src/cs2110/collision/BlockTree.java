package cs2110.collision;
import java.util.*;

//import javax.vecmath.Point2d;

/** An instance is a non-empty collection of points organized in a hierarchical
 * binary tree structure. */
public class BlockTree {
	private BoundingBox box; // bounding box of the blocks contained in this tree.

	private int numBlocks; // Number of blocks contained in this tree.

	private BlockTree leftTree; // left subtree --null if this is a leaf

	private BlockTree rightTree; // right subtree --null iff this is a leaf

	private Block block; //The block of a leaf node (null if not a leaf)
	
	public ArrayList<Block> listOfBlocks;
	
	public BoundingBox left;
	public BoundingBox right;
	
	//public ArrayList<Block> leftSide = new ArrayList<Block>();
	//public ArrayList<Block> rightSide = new ArrayList<Block>();
	
	public Node<BoundingBox> root;
	
	// REMARK:
	// Leaf node: left, right == null && block != null
	// Intermediate node: left, right != null && block == null

	/** Constructor: a binary tree containing blocks.
	 * Precondition: The tree has to be non-empty,
	 * i.e. it must contain at least one block.	 */
	public BlockTree(ArrayList<Block> blocks) { 
		
		this.listOfBlocks = blocks;
		
		if (blocks == null)
			throw new IllegalArgumentException("blocks null");
		if (blocks.size() == 0)
			throw new IllegalArgumentException("no blocks");
		
		numBlocks = blocks.size();

		Iterator<Block> iterator = blocks.iterator(); 
		
		box = BoundingBox.findBBox(iterator);
		//System.out.println("Area: "+box.getArea());
		root = new Node<BoundingBox>(box);
		/*System.out.println("BBox Width: "+box.getWidth());
		System.out.println("BBox Height: "+box.getHeight());
		System.out.println("BBox Lower: "+box.lower);
		System.out.println("BBox Upper: "+box.upper);
		System.out.println("numBlocks: "+numBlocks);*/
		
		/*int i = 1;
		for (Block b: blocks) {
			System.out.println("Block " + i + ": " + b.getPosition());
			i++;
		}*/
		
		//double largerLine = box.getLength();
		
		
		/***Construct the binary tree**/
		
		//Only one node in tree
		if(numBlocks == 1){
			//root.leftNode = null;
			//root.rightNode = null;
			leftTree = null;
			rightTree = null;
			block = blocks.get(0);
			return;
		}
		
		else{
			ArrayList<Block> leftSide = new ArrayList<Block>();
			ArrayList<Block> rightSide = new ArrayList<Block>();
			//Horizontal rectangle
			if(box.getWidth() >= box.getHeight()){
				
				Vector2D topCenter = new Vector2D((box.upper.x + box.lower.x)/2.0 , box.upper.y);
				Vector2D bottomCenter = new Vector2D((box.upper.x + box.lower.x)/2.0, box.lower.y);
				//System.out.println("Top: "+topCenter);
				//System.out.println("Bottom: "+bottomCenter);
				left = new BoundingBox(box.lower, topCenter);
				right = new BoundingBox(bottomCenter, box.upper);
				//System.out.println("RightCenter: "+right.getCenter());
				//System.out.println("LeftCenter: "+left.getCenter());
				/*Block b = null;
				while(iterator.hasNext()){ 
					b = iterator.next();
					
					//Put in right Bounding box
					if((b.getPosition().x > (box.upper.x + box.lower.x)/2.0)){
						rightSide.add(b);
						System.out.println("RightSide: " + rightSide);
					}
					
					else{
						leftSide.add(b);
						System.out.println("LeftSide: " + leftSide);
					}
				}*/
				for (int j=0; j < blocks.size(); j++){
					if (right.contains(new Vector2D(blocks.get(j).getPosition()))){
						rightSide.add(blocks.get(j));
						//System.out.println("RightSide: " + rightSide);
					}
					else{
						leftSide.add(blocks.get(j));
						//System.out.println("LeftSide: " + leftSide);
					}
				}
				
				
			}
			
			//Vertical rectangle
			else{
				Vector2D leftCenter = new Vector2D(box.lower.x, (box.upper.y + box.lower.y)/2.0);
				Vector2D rightCenter = new Vector2D(box.upper.x, (box.upper.y + box.lower.y)/2.0);
				
				right = new BoundingBox(box.lower, rightCenter);
				left = new BoundingBox(leftCenter, box.upper);
				//System.out.println("RightCenter: "+right.getCenter());
				//System.out.println("LeftCenter: "+left.getCenter());
				/*Block b2 = null;
				while(iterator.hasNext()){
					b2 = iterator.next();
					
						//Put in right Bounding box
						if((b2.getPosition().y < (box.upper.y + box.lower.y)/2.0)){
							rightSide.add(b2);
							System.out.println("RightSide: " + rightSide);
						}
						
						else{
							leftSide.add(b2);
							System.out.println("LeftSide: " + leftSide);
						}
				}*/
				for (int j=0; j < blocks.size(); j++){
					if (right.contains(new Vector2D(blocks.get(j).getPosition()))){
						rightSide.add(blocks.get(j));
						//System.out.println("RightSide: " + rightSide);
					}
					else{
						leftSide.add(blocks.get(j));
						//System.out.println("LeftSide: " + leftSide);
					}
				}
			}
			//System.out.println("LeftSide: " + leftSide);
			//System.out.println("RightSide: " + rightSide);
			leftTree = new BlockTree(leftSide);	
			rightTree = new BlockTree(rightSide);
		}
		/*System.out.println("BBox1 Width: "+left.getWidth());
		System.out.println("BBox1 Height: "+left.getHeight());
		System.out.println("BBox1 Lower: "+left.lower);
		System.out.println("BBox1 Upper: "+left.upper);*/
		
		
		
	}
	


	/** Return the bounding box of the collection of blocks.
	 * @return The bounding box of this collection of blocks.
	 */
	public BoundingBox getBox() {
		return box;
	}

	/** Return true iff this is a leaf node.
	 * @return true iff this is a leaf node.
	 */
	public boolean isLeaf() {
		return (block != null);
	}

	/** Return true iff this is an intermediate node.
	 * @return true iff this is an intermediate node.
	 */
	public boolean isIntermediate() {
		return !isLeaf();
	}

	/** Return the number of blocks contained in this tree.
	 * @return Number of blocks contained in this tree.
	 */
	public int getNumBlocks() {
		return numBlocks;
	}

	/** Return true iff this collection of blocks contains  point p.
	 * @return True iff this collection of blocks contains  point p.
	 */
	public boolean contains(Vector2D p) {
		
		Iterator<Block> iterator = listOfBlocks.iterator();
		
		while(iterator.hasNext()){
			Block b = iterator.next();
			
			if(b.contains(p)){
				return true;
			}
		}
		
		return false;
	}

	/** Return true iff (this tree displaced by thisD) and (tree t 
	 * displaced by d) overlap.
	 * @param thisD
	 *            Displacement of this tree.
	 * @param t
	 *            A tree of blocks.
	 * @param d
	 *            Displacement of tree t.
	 * @return True iff this tree and tree t overlap (account for
	 *         displacements).
	 */
	public boolean overlaps(Vector2D thisD, BlockTree t, Vector2D d) {
		// TODO: Implement me
		/*if (root == null){
			return false;
		}*/
		/*if(t.getNumBlocks() == 1){
			t.leftSide = null;
			t.rightSide = null;
			return (this.getBox().displaced(thisD).overlaps(t.getBox().displaced(d)));
		}
		this.overlaps(thisD, t.leftTree, d);
		this.overlaps(thisD, t.rightTree, d);*/
		if (this.getBox().displaced(thisD).overlaps(t.getBox().displaced(d))){
			if ((this.isLeaf()) && (t.isLeaf())){
				return true;
			}
			if ((this.isIntermediate())&&(t.isLeaf())){
				return (this.leftTree.overlaps(thisD, t, d) 
						|| this.rightTree.overlaps(thisD, t, d));
			}
			if ((this.isLeaf())&&(t.isIntermediate())){
				return (t.leftTree.overlaps(d, this, thisD) 
						|| t.rightTree.overlaps(d, this, thisD));
			}
			return (this.overlaps(thisD, t.leftTree, d) 
					|| this.overlaps(thisD, t.rightTree, d));
			
					//|| this.rightTree.overlaps(thisD, t.leftTree, d) 
					//|| this.rightTree.overlaps(thisD, t.rightTree, d));
		}
		return false;
	}

	/** Return a representation of this instance. */
	public String toString() {
		return toString(new Vector2D(0, 0));
	}

	/** Return a represenation of d
	 * 
	 * @param d  Displacement vector.
	 * @return String representation of this tree (displaced by d).
	 */
	public String toString(Vector2D d) {
		return toStringAux(d, "");
	}

	/** Useful for creating appropriate indentation for function toString.  */
	private static final String indentation = "   ";

	/** Return a representation of this instance displaced by d, with
	 * indentation indent.
	 * @param d Displacement vector.
	 * @param indent  Indentation.
	 * @return String representation of this tree (displaced by d).
	 */
	private String toStringAux(Vector2D d, String indent) {
		String str = indent + "Box: ";
		str += "(" + (box.lower.x + d.x) + "," + (box.lower.y + d.y) + ")";
		str += " -- ";
		str += "(" + (box.upper.x + d.x) + "," + (box.upper.y + d.y) + ")";
		str += "\n";

		if (isLeaf()) {
			String vStr = "(" + (block.position.x + d.x) + "," + (block.position.y + d.y)
					+ ")" + block.halfwidth;
			str += indent + "Leaf: " + vStr + "\n";
		} else {
			String newIndent = indent + indentation;
			str += leftTree.toStringAux(d, newIndent);
			str += rightTree.toStringAux(d, newIndent);
		}

		return str;
	}

	public class Node<E>
	{
	   public E data;
	   public Node<E> leftNode, rightNode;
	
	   public Node(E data, Node<E> left, Node<E> right)
	   {
	      leftNode = left; 
	      rightNode = right;
	      this.data = data;
	   }
	
	   public Node(E data)
	   {
	      this(data, null, null);
	   }
	
	   public String toString()
	   {
	      return data.toString();
	   }
	} 

}