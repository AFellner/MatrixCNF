package formula;

import java.util.ArrayList;

/**
 * Atom.java
 * Purpose: Represents a propositional atom, sometimes also called propositional variable.
 * Can also have a truth value assigned to it.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */

public class Atom extends Formula
{
	
	private Boolean truthValue;
	private String atomName;

	
	public Atom(String name)
	{
		setFormulaType( FORMULA_ATOM );
		atomName = name;
		
	}
	
	/**
	 * 
	 * @return String atom name surrounded with single apostrophes.
	 */
	public String toString()
	{
		return "'"+atomName+"'";
	}
	
	/**
	 * 
	 * @return String atom name
	 */
	public String getAtomName()
	{
		return atomName;
	}
	
	/**
	 * 
	 * @return boolean true, because every atom is a literal.
	 */
	public boolean isLiteral()
	{
		return true;
	}
	
	/**
	 * 
	 * @return boolean true, because every atom is a clause.
	 */
	public boolean isClause()
	{
		return true;
	}
	
	/**
	 * 
	 * @return boolean true, because every atom is a dual clause.
	 */
	public boolean isDualClause()
	{
		return true;
	}
	
	/**
	 * 
	 * @return boolean true, because every atom is in Disjunctive Normal Form.
	 */
	public boolean isDNF()
	{
		return true;
	}
	
	/**
	 * 
	 * @return boolean true, because every atom is in Conjunctive Normal Form.
	 */
	public boolean isCNF()
	{
		return true;
	}
	
	/**
	 * 
	 * @return ArrayList containing only this atom converted into a Literal.
	 */
	public ArrayList<Literal> getLiterals()
	{
		ArrayList<Literal> literal = new ArrayList<Literal>();
		literal.add( new Literal( this, false ) );
		return literal;
	}

	public Boolean getTruthValue() {
		return truthValue;
	}
	
	public void setTruthValue(boolean truthValue) {
		this.truthValue = truthValue;
	}
	
	/**
	 * Checks if this is equal to another object.
	 * Another object is considered to be equal to this atom, if it is of the class atom and the atom name
	 * of the other object is equal to the atom name of this atom.
	 * 
	 * @return boolean true if the atom names are equal, false otherwise
	 */
	public boolean equals(Object atom)
	{
		if (atom instanceof Atom)
		{
			Atom m = (Atom) atom;
			return m.getAtomName().equals(this.atomName);
		}
		else
		{
			return false;
		}
	}
	public ArrayList<Formula> getDefinitionalCNFClauses(Atom a)
	{
		Equivalence eq = new Equivalence( a, this );
		return ((Conjunction) a.reducedConnectives().nnf()).getFormulas();
//		ArrayList<Formula> f = new ArrayList<Formula>();
//		f.add( this );
//		return f;
	}
	
}
