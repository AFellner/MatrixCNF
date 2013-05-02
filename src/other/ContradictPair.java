package other;
import formula.*;

/**
 * ContradictPair.java
 * Purpose: Stores two literals, initialised at the creation. They can be parsed with getter methods.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */
public class ContradictPair {
	private Literal lit1;
	private Literal lit2;

	public ContradictPair(Literal lit1, Literal lit2)
	{
		this.lit1 = lit1;
		this.lit2 = lit2;
	}
	public Literal getLit1()
	{
		return lit1;
	}
	public Literal getLit2()
	{
		return lit2;
	}
	public String toString()
	{
		return lit1 + " ~ " + lit2;
	}
}
