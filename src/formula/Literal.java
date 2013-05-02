package formula;

import java.util.ArrayList;

/**
 * Literal.java
 * Purpose: Represents a literal, being either an atom or a negated atom.
 * It implements comparable to make Lists of Literals sortable.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */

public class Literal implements Comparable {
	
	private boolean negated;
	private Atom atom;
	
	private int rowOnes;
	
	public Literal(Atom atom, boolean negated, int rowOnes) {
		this.atom = atom;
		this.negated = negated;
		this.rowOnes = rowOnes;
	}
	
	public Literal(Atom atom, boolean negated) {
		this.atom = atom;
		this.negated = negated;
		this.rowOnes = 0;
	}
	
	/**
	 * 
	 * @return boolean true if this is a negative literal, false otherwise.
	 */
	public boolean negated()
	{
		return negated;
	}
	
	/**
	 * 
	 * @return String name of the atom used in this literal.
	 */
	public Atom getAtom()
	{
		return atom;
	}
	
	/**
	 * 
	 * @return String composed by an '!' symbol if this literal is negated, followed by the name of the atom used.
	 */
	public String toString()
	{
		if (negated) return "!" + atom.getAtomName();
		else return " " +atom.getAtomName();
	}
	

	/**
	 * 
	 * @return boolean true if the two atoms are equal and they are either both negated or both not negated, false otherwise.
	 */
	public boolean equals(Object Object)
	{
		Literal lit = (Literal) Object;
		if(this.atom.equals( lit.getAtom() ) && this.negated == lit.negated())
		{
			return true;
		}
		else
			return false;
	}
	
	
	public static ArrayList<Literal> getLiteralsSet(ArrayList<Literal> literals)
	{
		ArrayList<Literal> result = new ArrayList<Literal>();
		
		for(Literal l : literals)
		{
			if(!result.contains( l ))
			{
				result.add( l );
			}
		}
		return result;
		
	}
	
	/**
	 * Returns an object of the Formula class representing this literal.
	 * This is either simply the atom used in the not negated case or a Negation with the atom used as its children formula in the negated case.
	 * 
	 * @return Formula representing this literal.
	 */
	public Formula toFormula()
	{
		if (negated) return new Negation(atom);
		else return atom;
	}
	
	/**
	 * 
	 * @return int number of true values a row of a matrix.
	 */
	public int getRowOnes()
	{
		return rowOnes;
	}
	
	/**
	 * 
	 * @param to sets the number of true values in a row of a matrix to this parameter.
	 */
	public void setRowOnes(int to)
	{
		this.rowOnes = to;
	}
	
	/**
	 * Compares this literal with another object.
	 * Compares the rowOnes values, which always refer to a matrix.
	 * 
	 * @param other The other object this is compared to.
	 * @return 0 if they are equal or if the other is not a literal
	 * 		   -1 if this has more true values set to true in the current matrix
	 *         1 if this has less
	 *         the result of comparing the two atom names if they have the same number of true values
	 */
	public int compareTo(Object other) {
		if (other instanceof Literal)
		{
			if (other.equals(this)) return 0;
			else if (rowOnes > ((Literal) other).getRowOnes()) return -1;
			else if (rowOnes < ((Literal) other).getRowOnes()) return 1;
			else return ((Literal) other).getAtom().getAtomName().compareTo(this.getAtom().getAtomName());
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * Checks if one literal is the negated version of this literal.
	 * 
	 * @param other the literal which has to be checked against this one.
	 * @return true if the atom used in both literals equals and the negated value differs, false otherwise.
	 */
	public boolean contradicts(Literal other)
	{
		if (this.atom.equals(other.atom) && this.negated() != other.negated()) return true;
		else return false;
	}
	
	/**
	 * 
	 * @return Literal new literal which is a copy of this one.	
	 */
	public Literal clone()
	{
		return new Literal(atom,negated,rowOnes);
	}
	
	public static ArrayList<ArrayList<Literal>> CNFFromConjunction(Formula x) {
		ArrayList<Literal> temp;
		ArrayList<ArrayList<Literal>> literals = new ArrayList<ArrayList<Literal>>();
		
		if(x instanceof Conjunction) {
		ArrayList<Formula> formulas = ((Conjunction) x).getFormulas();
		for(int j = 0; j < formulas.size(); j++) {
			temp = new ArrayList<Literal>();
			if(!(formulas.get(j) instanceof Disjunction)) {
				if(formulas.get(j) instanceof Negation)
					temp.add(new Literal(((Atom)((Negation)formulas.get(j)).getFormula1()), true));
				else
					temp.add(new Literal(((Atom)formulas.get(j)), false));
			}
			else {
				Disjunction dis = (Disjunction) formulas.get(j);
				for(int k = 0; k < dis.getFormulas().size(); k++) {
					if(dis.getFormulas().get(k) instanceof Negation) 
						temp.add(new Literal(((Atom)((Negation)dis.getFormulas().get(k)).getFormula1()), true));
					else
						temp.add(new Literal(((Atom)dis.getFormulas().get(k)), false));
				}
			}
			literals.add(temp);
		}
		}
		else {
			temp = new ArrayList<Literal>();
			if(x instanceof Top) {
				temp.add(new Literal(new Atom("Top"), false));
			}
			else if(x instanceof Bottom) {
				temp.add(new Literal(new Atom("Bottom"), false));
			}
			literals.add(temp);
		}
		
		return literals;
	}
}
