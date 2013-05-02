package formula;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * BinaryFormula.java
 * Purpose: Represents a propositional formula which main connective is a binary one.
 * Because Conjunction and Disjunction are viewed as n-ary connectives, binary are only Implication and Equivalence.
 * All those implement this abstract class.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */


public abstract class BinaryFormula extends Formula
{
	
	protected Formula formula1;
	protected Formula formula2;

	public abstract String toString();

	/**
	 *
	 *@return Formula denoting the first of the children formulae
	 */
	public Formula getFormula1()
	{
		return formula1;
	}

	/**
	 * @param formula1 sets the first of the children formulae to the this parameter
	 */
	public void setFormula1( Formula formula1 )
	{
		this.formula1 = formula1;
	}

	/**
	 *
	 *@return Formula denoting the second of the children formulae
	 */
	public Formula getFormula2()
	{
		return formula2;
	}

	/**
	 * @param formula2 sets the second of the children formulae to the this parameter
	 */
	public void setFormula2( Formula formula2 )
	{
		this.formula2 = formula2;
	}
	
	public ArrayList<Literal> getLiterals()
	{
		ArrayList<Literal> f1Literals = formula1.getLiterals();
		ArrayList<Literal> f2Literals = formula2.getLiterals();
		
		f1Literals.addAll( f2Literals );
		
		ArrayList<Literal> tobeReturned = Literal.getLiteralsSet( f1Literals );
		
		return tobeReturned;
	}

}
