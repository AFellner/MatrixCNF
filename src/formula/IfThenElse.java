package formula;

import java.util.ArrayList;

/**
 * IfThenElse.java
 * Purpose: Represents a propositional formula, which main connective is an If then else.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */

public class IfThenElse extends Formula
{
	Formula formula1;
	Formula formula2;
	Formula formula3;

	public IfThenElse(Formula f1, Formula f2, Formula f3)
	{
		setFormulaType( FORMULA_IFTHENELSE );
		this.formula1 = f1;
		this.formula2 = f2;
		this.formula3 = f3;
		
	}
	
	/**
	 * 
	 * @return String composed by the toString values of the children formulae, connected with 'If' and 'then' if only two childrenformulae are set
	 * or with 'If', 'then' and 'else' if three childrenformulae are set.
	 */
	public String toString()
	{
		String s;
		if(formula3 == null)
			s = "(If ("+formula1+") then (" +formula2+"))";
		else
			s = "(If ("+formula1+") then (" +formula2+") else ("+formula3+"))";
		
		return s;
	}

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

	/**
	 *
	 *@return Formula denoting the third of the children formulae
	 */
	public Formula getFormula3()
	{
		return formula3;
	}

	/**
	 * @param formula3 sets the third of the children formulae to the this parameter
	 */
	public void setFormula3( Formula formula3 )
	{
		this.formula3 = formula3;
	}
	
	public Formula removeTopBottom()
	{
		Formula newF1 = formula1.removeTopBottom();
		Formula newF2 = formula2.removeTopBottom();
		Formula imp1 = new Implication(newF1,newF2).removeTopBottom();
		if (formula3 == null)
		{
			return imp1;
		}
		else
		{
			Formula newF3 = formula3.removeTopBottom();
			Formula imp2 = new Implication(new Negation(newF1),newF3).removeTopBottom();
			Formula out = new Conjunction(imp1,imp2).removeTopBottom();
			return out;
		}
	}
	
	public Formula reducedConnectives()
	{
		Implication imp1 = new Implication(this.getFormula1(),this.getFormula2());
		if (formula3 == null)
		{
			return imp1.reducedConnectives();
		}
		else
		{
			Implication imp2 = new Implication(new Negation(this.getFormula1()),this.getFormula3());
			return new Conjunction(imp1.reducedConnectives(),imp2.reducedConnectives());
		}
	}
	
	public ArrayList<Literal> getLiterals()
	{
		ArrayList<Literal> allLiterals = new ArrayList<Literal>();
		allLiterals.addAll( formula1.getLiterals() );
		allLiterals.addAll( formula2.getLiterals() );
		if(formula3!=null)
		{
			allLiterals.addAll( formula3.getLiterals() );
		}
		
		return Literal.getLiteralsSet( allLiterals );
	}

	@Override
	public Boolean getTruthValue() {
		if(formula3 == null)
			return (!(formula1.getTruthValue()) || formula2.getTruthValue());
		else
			return (!(formula1.getTruthValue()) || formula2.getTruthValue()) &&
					(formula1.getTruthValue() || formula3.getTruthValue());
		
	}

	public Formula nnfAfterReduce() {
		return null;
	}
}
