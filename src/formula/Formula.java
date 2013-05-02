package formula;

import java.util.ArrayList;

/**
 * Formula.java
 * Purpose: Represents a propositional formula.
 * Is a generic type. Actual formulae are of one of the 7 types listed below.
 * All the implementations for those classes inherit from this class.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */

public abstract class Formula
{
	public static final int FORMULA_ATOM = 1;
	public static final int FORMULA_NEGATION = 2;
	public static final int FORMULA_CONJUNCTION = 3;
	public static final int FORMULA_DISJUNCTION = 4;
	public static final int FORMULA_EQUIVALENCE = 5;
	public static final int FORMULA_IMPLICATION = 6;
	public static final int FORMULA_IFTHENELSE = 7;
	
	public static int FORMULA_COUNTER = 0;
	
	private int formulaType;
	
	/**
	 * Returns the formula in a String representation
	 * 
	 * @return String Formula as a String including connective symbols and variable names
	 */
	public abstract String toString();
	
	/** 
	 * @return ArrayList List containing all the literals used in the formula
	 */
	public abstract ArrayList<Literal> getLiterals();

	public abstract Boolean getTruthValue();
	
	/**
	 * @return int Type of the formula
	 */
	public int getFormulaType()
	{
		return formulaType;
	}

	/**
	 * 
	 * @param formulaType number between 1 and 7 denoting the new formula type
	 */
	public void setFormulaType( int formulaType )
	{
		this.formulaType = formulaType;
	}
	
	/**
	 * Removes the logical constants Top and Bottom from the formula if possible.
	 * Accepts formulae with all the supported connectives (including equivalence, if then else, ...).
	 * Does this by using semantic equivalences, for example Top or F is equivalent to F.
	 * Returns Top or Bottom if these equivalences reduce it to it, for example Top and Bottom is reduced to Bottom.
	 * If it is not overwritten it just returns this object.
	 * 
	 * @return Formula being Top or Bottom, or not having these two types of formulae as subformula.
	 */
	public Formula removeTopBottom()
	{
		return this;
	}
	
	/**
	 * Converts this formula into Negation Normal Form.
	 * Requires this formula to be in the reduced form including at most the connectives and, or, negation, top, bottom, atom.
	 * 
	 * @return Formula equivalent to this formula in Negation Normal Form
	 */
	public Formula nnfAfterReduce()
	{
		return this;
	}
	
	/**
	 * Converts this formula into an equi- satisfiable Conjunctive Normal Form.
	 * Redirects to a specified form of computing the CNF.
	 * 
	 * @param method denotes the desired method of CNF conversion.
	 *        0 - Matrix CNF
	 *        1 - Defenitional CNF
	 *        2 - Naive CNF
	 * @return Formula in Conjunctive Normal Form equi- satisfiable to this.
	 */
	public Formula toCNF(int method)
	{
		Formula out = this;
		if (method == 0)
		{
			out = this.nnf();
			out.matrixCNF();
			//System.out.println("here");
		}
		return out;
	}
	
	/**
	 * Computes a CNF of this formula using paths through a matrix.
	 * 
	 * @return Formula in CNF computed using the matrix method.
	 */
	public Formula matrixCNF()
	{
		return this;
	}
	
	/**
	 * Computes a Disjunctive Normal Form of this formula using paths through a matrix.
	 * 
	 * @return Formula in DNF computed using the matrix method.
	 */
	public Formula matrixDNF()
	{
		return this;
	}
	
	/**
	 * Computes the Negation Normal Form of this formula.
	 * Calls the removeTopBottom, reduceConnectives, nnfAfterReduce methods one after the other.
	 * This procedure is fixed and therefore the method cannot be overwritten by the inherited classes.
	 * 
	 * @return Formula in Negation Normal Form equivalent to this formula.
	 */
	public final Formula nnf()
	{
		Formula rTB = this.removeTopBottom();
		Formula red = rTB.reducedConnectives();
		Formula nnf = red.nnfAfterReduce();
		return nnf;
	}
	
	/**
	 * Reduces the connectives of this formula to and, or, negation, top, bottom, atom.
	 * 
	 * @return Formula equivalent to the this formula with reduced connectives.
	 */
	public Formula reducedConnectives()
	{
		return this;
	}
	
	/**
	 * @return boolean true if this formula denotes a literal (atom or negated atom), false otherwise.
	 */
	public boolean isLiteral()
	{
		return false;
	}
	
	/**
	 * @return boolean true if this formula is a clause (disjunction of literals), false otherwise.
	 */
	public boolean isClause()
	{
		return false;
	}
	
	/**
	 * @return boolean true if this formula is a dual clause (conjunction of literals), false otherwise.
	 */
	public boolean isDualClause()
	{
		return false;
	}
	
	/**
	 * @return boolean true if this formula is in Disjunctive Normal Form (Disjunction of dual clauses), false otherwise.
	 */
	public boolean isDNF()
	{
		return false;
	}
	
	/**
	 * @return boolean true if this formula is in Conjunctive Normal Form (Conjunction of clauses), false otherwise.
	 */
	public boolean isCNF()
	{
		return false;
	}
	
	public Formula toDefinitionalCNF()
	{
		Formula fTB= this.removeTopBottom();
		Formula fRC= fTB.reducedConnectives();
		Formula fNNf = fRC.nnf();
		return fNNf.getDefinitionalCNF();
	}
	
	public Formula getDefinitionalCNF()
	{
		if(this instanceof Atom || this instanceof Top || this instanceof Bottom)
		{
			return this;
		}
		else
		{
			ArrayList<Formula> formulas = new ArrayList<Formula>();
			Atom p1 = new Atom("p1");
			FORMULA_COUNTER = 2;
		
			formulas.add( p1 );
			formulas.addAll(getDefinitionalCNFClauses(p1));
			Conjunction conj = new Conjunction( formulas );
			conj.flattenCNF();
			return conj;
		}
	}
	
	public ArrayList<Formula> getDefinitionalCNFClauses(Atom a)
	{
		return new ArrayList<Formula>();
	}

	public Formula changeToClause()
	{
		return this;
	}
	
	public static ArrayList<Formula> cloneList(ArrayList<Formula> list) {
	    ArrayList<Formula> clone = new ArrayList<Formula>();
	    for(Formula item: list)
	    {
	    	if(item instanceof Conjunction)
	    		clone.add( new Conjunction( ((Conjunction)item ).getFormulas() ));
	    	if(item instanceof Disjunction)
	    		clone.add( new Disjunction( ((Disjunction)item ).getFormulas() ));
	    	if(item instanceof Negation)
	    		clone.add( new Negation( ((Negation)item ).getFormula1() ));
	    	if(item instanceof Atom)
	    		clone.add( item );
	    	
	    		
	    }
	    return clone;
	}
	
	public Formula distributeDisjunction()
	{
		return this;
	}
	
	public Formula toNaiveCNF(Formula x)
	{
		if(x instanceof Top || x instanceof Bottom || x instanceof Atom) {
			return x;
		}
		
		while(!x.isCNF()){
			x = x.distributeDisjunction();
		}
		
		return x;
	}
	
}