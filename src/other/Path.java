package other;
import java.util.ArrayList;
import formula.*;
/**
 * Path.java
 * Purpose: Represents a list of Literals
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */
public class Path extends ArrayList<Literal>{

	public Path clone()
	{
		Path out = new Path();
		for (Literal lit: this)
		{
			out.add(lit);
		}
		return out;
	}
	public Path deepclone()
	{
		Path out = new Path();
		for (Literal lit: this)
		{
			out.add(lit.clone());
		}
		return out;
	}
	
	/**
	 * 
	 * @param other Path to be compared
	 * @return boolean true if this contains all literals which are stored in the other path, false otherwise.
	 */
	public boolean subsumes(Path other)
	{
		return this.containsAll(other);
	}
}
