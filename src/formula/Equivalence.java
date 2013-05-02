package formula;
import java.util.ArrayList;

/**
 * Equivalence.java
 * Purpose: Represents a propositional formula, which main connective is an equivalence.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */

public class Equivalence extends BinaryFormula
{
	public Equivalence(Formula f1, Formula f2)
	{
		setFormulaType( FORMULA_EQUIVALENCE );
		setFormula1( f1 );
		setFormula2( f2 );
	}
	
	/**
	 * 
	 * @return String composed by the toString values of the children formulae, connected with '<->' symbols and surrounded by (,) brackets.
	 */
	public String toString()
	{
		String s = "("+getFormula1() + " <-> " + getFormula2()+")";
		return s;
	}
	
	public Formula reducedConnectives()
	{
		Implication imp1 = new Implication(this.getFormula1(),this.getFormula2());
		Implication imp2 = new Implication(this.getFormula2(),this.getFormula1());
		return new Conjunction(imp1.reducedConnectives(),imp2.reducedConnectives());
	}
	
	public Formula removeTopBottom()
	{
		Formula newF1 = formula1.removeTopBottom();
		Formula newF2 = formula2.removeTopBottom();
		if(newF1 instanceof Top)
		{
			return newF2;
		}
		else if(newF1 instanceof Bottom)
		{
			Negation neg = new Negation(newF2);
			return neg.removeTopBottom();
		}
		else if (newF2 instanceof Top)
		{
			return newF1;
		}
		else if (newF2 instanceof Bottom)
		{
			Negation neg = new Negation(newF1);
			return neg.removeTopBottom();
		}
		else
		{
			return new Equivalence(newF1,newF2);
		}
	}

	public Boolean getTruthValue() {
		return (formula1.getTruthValue() == formula2.getTruthValue());
	}
	public Formula nnfAfterReduce() {
		return null;
	}
	
}