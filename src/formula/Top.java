package formula;

import java.util.ArrayList;

/**
 * Top.java
 * Purpose: Represents the propositional formula top, sometimes also called true.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */

public class Top extends Formula
{
	
	public Top()
	{
		
	}
	
	public String toString()
	{
		return "Top";
	}

	public boolean isLiteratl()
	{
		return false;
	}
	
	public ArrayList<Literal> getLiterals()
	{
		return new ArrayList<Literal>();
	}
	
	/*public Formula removeTopBottom()
	{
		return new Top();
	}
	
	public Formula nnfAfterReduce()
	{
		return new Top();
	}
	public Formula reducedConnectives()
	{
		return new Top();
	}*/

	@Override
	public Boolean getTruthValue() {
		return true;
	}
	
	@Override
	public boolean isCNF() {
		return true;
	}
}
