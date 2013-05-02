package formula;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Negation.java
 * Purpose: Represents a propositional formula, which main connective is a Negation.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */

public class Negation extends Formula
{

	Formula formula1;


	public Negation(Formula formula)
	{
		setFormulaType( FORMULA_NEGATION );
		this.formula1 = formula;
	}
	
	public Formula getFormula1()
	{
		return formula1;
	}

	public void setFormula1( Formula formula1 )
	{
		this.formula1 = formula1;
	}
	
	public String toString()
	{
		String s = "! ("+formula1 + ") ";
		return s;
	}
	
	public Formula removeTopBottom()
	{
		//setFormula1(formula1.removeTopBottom());
		Formula newF1 = formula1.removeTopBottom();
		if (newF1 instanceof Top)
		{
			return new Bottom();
		}
		else if(newF1 instanceof Bottom)
		{
			return new Top();
		}
		else
		{
			return new Negation(newF1);
		}
	}
	
	public Formula nnfAfterReduce()
	{
		//Delete double negation
		if (formula1 instanceof Negation)
		{
			return ((Negation) formula1).getFormula1().nnfAfterReduce();
		}
		else if (formula1 instanceof Conjunction)
		{
			
			ArrayList<Formula> conjuncts = ((Conjunction) formula1).getFormulas();
			Iterator<Formula> i = conjuncts.iterator();
			ArrayList<Formula> disjuncts = new ArrayList<Formula>();
			while (i.hasNext())
			{
				Formula aktFormula = i.next();
				Formula negAktnnf = new Negation(aktFormula).nnfAfterReduce();
				disjuncts.add(negAktnnf);
			}
			Formula out = new Disjunction(disjuncts).nnfAfterReduce();
			//System.out.println("not conjunction found -> push inside: replace " + formula1 + " with " + out);
			return out;
		}
		else if (formula1 instanceof Disjunction)
		{
			ArrayList<Formula> conjuncts = new ArrayList<Formula>();
			ArrayList<Formula> disjuncts = ((Disjunction) formula1).getFormulas();
			Iterator<Formula> i = disjuncts.iterator();
			
			while (i.hasNext())
			{
				Formula aktFormula = i.next();
				Formula negAktnnf = new Negation(aktFormula).nnfAfterReduce();
				conjuncts.add(negAktnnf);
			}
			Formula out = new Conjunction(conjuncts).nnfAfterReduce();
			//System.out.println("not disjunction found -> push inside: replace " + formula1 + " with " + out);
			return out;
		}
		else
		{
		//	System.out.println("here: " + this);
			return new Negation(formula1);
		}
	}
	public Formula reducedConnectives()
	{
		return new Negation(formula1.reducedConnectives());
	}
	
	public boolean isLiteral()
	{
		return (formula1 instanceof Atom);
	}
	public boolean isClause()
	{
		return isLiteral();
	}
	public boolean isDualClause()
	{
		return isLiteral();
	}
	
	public boolean isDNF()
	{
		return isLiteral();
	}
	public boolean isCNF()
	{
		return isLiteral();
	}

	@Override
	public ArrayList<Literal> getLiterals()
	{
		if(formula1 instanceof Atom)
		{
			ArrayList<Literal> list = new ArrayList<Literal>();
			list.add( new Literal((Atom) formula1,true) );
			return list;
		}
		else
		{
			return formula1.getLiterals();
		}
	}

	@Override
	public Boolean getTruthValue() {
		return !(formula1.getTruthValue());
	}
	

	public ArrayList<Formula> getDefinitionalCNFClauses(Atom a)
	{
		if(formula1 instanceof Atom)
		{
			Equivalence eq = new Equivalence( a, this );
			return((Conjunction) eq.reducedConnectives()).getFormulas();
			
		}
		else
		{
			Atom pp = new Atom("p"+FORMULA_COUNTER);
			Negation a2 = new Negation(pp);
			FORMULA_COUNTER ++;
			Equivalence eq = new Equivalence( a, a2 );
			Conjunction c = (Conjunction)eq.reducedConnectives().nnf();
			ArrayList<Formula> formulas = c.getFormulas();
			formulas.addAll( formula1.getDefinitionalCNFClauses( pp ) );
			return formulas;
			
		}
	}
}
