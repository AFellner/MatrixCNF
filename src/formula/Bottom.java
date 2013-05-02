package formula;

import java.util.ArrayList;

/**
 * Bottom.java
 * Purpose: Represents the propositional formula bottom, sometimes also called false.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */


public class Bottom extends Formula
{
	
	public Bottom()
	{
		
	}
	
	/**
	 * 
	 * @return String "Bottom"
	 */
	public String toString()
	{
		return "Bottom";
	}


	/**
	 * 
	 * @return ArrayList which is empty
	 */
	public ArrayList<Literal> getLiterals()
	{
		return new ArrayList<Literal>();
	}

	public Boolean getTruthValue() {
		return false;
	}
	
	
	/**
	 * 
	 * @return true, because bottom is considered to be the empty Conjunction which is in CNF
	 */
	public boolean isCNF() {
		return true;
	}
}
