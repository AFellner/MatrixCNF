package formula;

import java.util.ArrayList;

/**
 * Implication.java
 * Purpose: Represents a propositional formula, which main connective is an implication.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */

public class Implication extends BinaryFormula
{
	public Implication(Formula f1, Formula f2)
	{
		setFormulaType( FORMULA_IMPLICATION );
		setFormula1( f1 );
		setFormula2( f2 );
	}
	
	/**
	 * 
	 * @return String composed by the toString values of the children formulae, connected with '->' symbols and surrounded by (,) brackets.
	 */
	public String toString()
	{
		String s = "("+getFormula1() + " -> " + getFormula2()+")";
		return s;
	}
	
	
	public Formula removeTopBottom()
	{
		Formula newF1 = formula1.removeTopBottom();
		Formula newF2 = formula2.removeTopBottom();
		if (newF1 instanceof Bottom)
		{
			return new Top();
		}
		else if(newF1 instanceof Top)
		{
			return newF2;
		}
		else if (newF2 instanceof Bottom)
		{
			return new Negation(newF1).removeTopBottom();
		}
		else if (newF2 instanceof Top)
		{
			return new Top();
		}
		else
		{
			return new Implication(newF1,newF2);
		}
	}
	
	public Formula reducedConnectives(){
		Negation newf1 = new Negation(this.getFormula1());
		return new Disjunction(newf1,formula2).reducedConnectives();
	}

	@Override
	public Boolean getTruthValue() {
		return (!(formula1.getTruthValue()) || formula2.getTruthValue());
	}
	public Formula nnfAfterReduce() {
		return null;
	}
}