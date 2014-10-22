package cs2110.collision;
import java.util.Iterator;

/** An instance is a 2D bounding box. */
public class BoundingBox {
	/** The corner of the bounding box with the smaller x,y coordinates. */
	public Vector2D lower; // (minX,minY)

	/** The corner of the bounding box with the larger x,y coordinates.	 */
	public Vector2D upper; // (maxX,maxY)

	/** Constructor: an instance is a copy of bounding box b.*/
	public BoundingBox(BoundingBox b) {
		lower = new Vector2D(b.lower);
		upper = new Vector2D(b.upper);
	}

	/** Constructor: An instance with lower as smaller coordinates and
	 * upper as larger coordinates.
	 * @param lower  Corner with smaller coordinates.
	 * @param upper  Corner with larger coordinates.
	 */
	public BoundingBox(Vector2D lower, Vector2D upper) {
		if (upper.x < lower.x)
			//throw new IllegalArgumentException("invalid bbox");
		if (upper.y < lower.y)
			throw new IllegalArgumentException("invalid bbox");

		this.lower = lower;
		this.upper = upper;
	}

	/** Return the width of this bounding box (along x-dimension).
	 * @return Width of this bounding box.
	 */
	public double getWidth() {
		return upper.x - lower.x;
	}

	/** Return the height of this bounding box (along y-dimension).
	 * @return Height of this bounding box.
	 */
	public double getHeight() {
		return upper.y - lower.y;
	}

	/** Return the larger of the width and height of this bounding box.
	 * @return Returns the dimension (width or height) of maximum length.
	 */
	public double getLength() {
		// TODO: Implement me.
		if (this.getWidth() > this.getHeight()){
			return this.getWidth();
		}
		return this.getHeight();
	}

	/** Return the center of this bounding box.
	 * @return The center of this bounding box.
	 */
	public Vector2D getCenter() {
		// TODO: Implement me.
		double xValue = (lower.x + upper.x)/2;
		double yValue = (lower.y + upper.y)/2;
		Vector2D center = new Vector2D(xValue, yValue);
		return center;
	}

	/** Return the result of displacing this bounding box by d.
	 * @param d
	 *            A displacement vector.
	 * @return The result of displacing this bounding box by vector d.
	 */
	public BoundingBox displaced(Vector2D d) {
		// TODO: Implement me
		Vector2D displaceX = new Vector2D
				(this.lower.x + d.x, this.lower.y + d.y);
		Vector2D displaceY = new Vector2D
				(this.upper.x + d.x, this.upper.y + d.y);
		BoundingBox displace = new BoundingBox(displaceX, displaceY); 
		return displace;
	}

	/** Return true iff this bounding box contains p.
	 * @param p A point.
	 * @return True iff this bounding box contains point p.
	 */
	public boolean contains(Vector2D p) {
		boolean inX = lower.x <= p.x && p.x <= upper.x;
		boolean inY = lower.y <= p.y && p.y <= upper.y;
		return inX && inY;
	}

	/** Return the area of this bounding box.
	 * @return The area of this bounding box.
	 */
	public double getArea() {
		// TODO: Implement me.
		return this.getHeight() * this.getWidth();
	}

	/** Return true iff this bounding box overlaps with box.
	 * @param box  A bounding box.
	 * @return True iff this bounding box overlaps with box.
	 */
	public boolean overlaps(BoundingBox box) {
		// TODO: Implement me.
		if (this.upper.x < box.lower.x){ //box toRight of this
			return false;
		}
		if (box.upper.x < this.lower.x){ //this toRight of box
			return false;
		}
		if (box.upper.y < this.lower.y) { //this isAbove box
			return false;
		}
		if (this.upper.y < box.lower.y) { //box isAbove this
			return false;
		}
		//else overlaps
		return true;
		
		/*Vector2D c1 = this.getCenter();
		Vector2D c2 = box.getCenter();

		return Vector2D.dist(c1, c2) < this.getWidth()/2 + box.getWidth()/2;*/
		
		/*Boolean boo = true;
		double thisDiagonal = (((this.getWidth()/2)*(this.getWidth()/2))
				+ ((this.getHeight()/2)*(this.getHeight()/2)));
		thisDiagonal = Math.sqrt(thisDiagonal);
		
		double boxDiagonal = (((box.getWidth()/2)*(box.getWidth()/2))
				+ ((box.getHeight()/2)*(box.getHeight()/2)));
		boxDiagonal = Math.sqrt(boxDiagonal);
		
		double centerDiagDist = thisDiagonal + boxDiagonal; 
		//boundary distance
		
		double centerX = this.getCenter().x - box.getCenter().x;
		double centerY = this.getCenter().y - box.getCenter().y;
		centerX = centerX * centerX;
		centerY = centerY * centerY;
		double centerDist = Math.sqrt(centerX + centerY); //actual distance
		
		if (centerDist > centerDiagDist){
			boo = false;
		}
		return boo;*/
	}

	/** Return the bounding box of blocks given by iter.
	 * @param iter   An iterator of blocks.
	 * @return The bounding box of the blocks given by the iterator.
	 */
	public static BoundingBox findBBox(Iterator<Block> iter) {
		// Do not modify the following "if" statement.
		if (!iter.hasNext()){
			throw new IllegalArgumentException("empty iterator");
		}
		// TODO: Implement me.
		//Vector2D low = new Vector2D(iter.next().getBBox().lower.x, iter.next().getBBox().lower.y);
		//Vector2D up = new Vector2D(iter.next().getBBox().upper.x, iter.next().getBBox().upper.y);
		/*BoundingBox b = null;
		Vector2D low = null;
		Vector2D up =  null;
		if (iter.hasNext()){
			b = new BoundingBox(iter.next().getBBox());
			low = new Vector2D(b.lower.x, b.lower.y);
			up =  new Vector2D(b.upper.x, b.upper.y);
		}
		while (iter.hasNext()){
			//System.out.println("X: "+(low.x<up.x));
			//System.out.println("Y: "+(low.y<up.y));
			double LowX = 0.0;
			double LowY = 0.0;
			double UpX = 0.0;
			double UpY = 0.0;
			BoundingBox box = new BoundingBox(iter.next().getBBox());
			if(box.contains(low)){
				low = new Vector2D(box.lower.x, box.lower.y);
			}
			else{
				if (box.lower.x <= low.x){
					LowX = box.lower.x;
				}
				else{
					LowX = low.x;
				}
				if (box.lower.y <= low.y){
					LowY = box.lower.y;
				}
				else{
					LowY = low.y;
				}
				low = new Vector2D(LowX, LowY);	
			}
			if(box.contains(up)){
				up = new Vector2D(box.upper.x, box.upper.y);
			}
			else{
				if (box.upper.x <= up.x){
					UpX = up.x;
				}
				else{
					UpX = box.upper.x;
				}
				if (box.upper.y <= up.y){
					UpY = up.y;
				}
				else{
					UpY = box.upper.y;
				}
				up = new Vector2D(UpX, UpY);
			}
			/*if(iter.hasNext()){
				boo=true;
			}
			else{
				boo=false;
			}
		}
		//System.out.println("LOW: "+low);
		//System.out.println("UP: "+up);
		BoundingBox b2 = new BoundingBox(low, up);
		return b2;*/
		Block block = iter.next();
		Vector2D low = block.getBBox().lower;
		Vector2D up = block.getBBox().upper;
		
		while(iter.hasNext()){
			block = iter.next();
			if (block.getBBox().lower.x < low.x){
				low.x = block.getBBox().lower.x;
			}
			if (block.getBBox().lower.y < low.y){
				low.y = block.getBBox().lower.y;
			}
			if (block.getBBox().upper.x > up.x){
				up.x = block.getBBox().upper.x;
			}
			if (block.getBBox().upper.y > up.y){
				up.y = block.getBBox().upper.y;
			}
		}
		BoundingBox box = new BoundingBox(low,up);
		return box;
	}

	/** Return a representation of this bounding box. */
	public String toString() {
		return lower + " -- " + upper;
	}
}
