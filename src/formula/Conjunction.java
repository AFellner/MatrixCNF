package formula;

import java.util.ArrayList;
import java.util.Iterator;

import other.Matrix;

/**
 * Conjunction.java
 * Purpose: Represents a propositional formula, which main connective is a conjunction.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */


public class Conjunction extends Formula
{
	private ArrayList<Formula> formulas;

	public Conjunction( Formula f1, Formula f2 )
	{
		ArrayList<Formula> con = new ArrayList<Formula>();
		con.add( f1 );
		con.add( f2 );
		setFormulaType( FORMULA_CONJUNCTION );
		breakDownConjunction( con );
	}

	public Conjunction( ArrayList<Formula> fs )
	{
		setFormulaType( FORMULA_CONJUNCTION );
		breakDownConjunction( fs );
	}

	public void breakDownConjunction( ArrayList<Formula> fs )
	{
		formulas = new ArrayList<Formula>();
		for( Formula f : fs )
		{
			if( f instanceof Conjunction )
			{
				Conjunction con = (Conjunction) f;
				for( Formula frmls : con.getFormulas() )
				{
					formulas.add( frmls );
				}
			}
			else
			{
				formulas.add( f );
			}
		}
	}

	/**
	 * 
	 * @return ArrayList of children formulae
	 */
	public ArrayList<Formula> getFormulas()
	{
		return formulas;
	}

	/**
	 * @param formulas sets the children formulae to the given List
	 */
	public void setFormulas( ArrayList<Formula> formulas )
	{
		this.formulas = formulas;
	}

	/**
	 * 
	 * @return String composed by the toString values of the children formulae, connected with '&' symbols and surrounded by [,] brackets.
	 */
	public String toString()
	{
		String s = "[";
		for( Formula f : formulas )
		{
			s += " " + f + " &";
		}
		s = s.substring( 0, s.length() - 1 );
		s += "]";
		return s;
	}

	public Formula removeTopBottom()
	{
		Iterator<Formula> i = formulas.iterator();
		ArrayList<Formula> newFormulas = new ArrayList<Formula>();
		while( i.hasNext() )
		{
			Formula aktFormula = i.next();
			Formula cleanedFormula = aktFormula.removeTopBottom();
			if( cleanedFormula instanceof Bottom )
			{
				return new Bottom();
			}
			else if( !(cleanedFormula instanceof Top) )
			{
				newFormulas.add( cleanedFormula );
			}
		}
		if( newFormulas.isEmpty() )
			return new Top();
		return new Conjunction( newFormulas );
	}

	public Formula nnfAfterReduce()
	{
		Iterator<Formula> i = formulas.iterator();
		ArrayList<Formula> newCon = new ArrayList<Formula>();
		while( i.hasNext() )
		{
			Formula next = i.next();
			newCon.add( next.nnfAfterReduce() );
		}
		return new Conjunction( newCon );
	}

	public Formula reducedConnectives()
	{
		Iterator<Formula> i = formulas.iterator();
		ArrayList<Formula> newCon = new ArrayList<Formula>();
		while( i.hasNext() )
		{
			Formula next = i.next();
			newCon.add( next.reducedConnectives() );
		}
		return new Conjunction( newCon );
	}

	public boolean isDualClause()
	{
		boolean out = true;
		Iterator<Formula> i = formulas.iterator();
		while( i.hasNext() )
		{
			Formula next = i.next();
			if( !(next.isLiteral()) )
			{
				if( next instanceof Conjunction )
				{
					if( !((Conjunction) next).isDualClause() )
					{
						out = false;
					}
				}
				else
				{
					out = false;
				}
			}
		}
		return out;
	}

	public boolean isDNF()
	{
		return isDualClause();
	}

	public boolean isCNF()
	{
		boolean out = true;
		Iterator<Formula> i = formulas.iterator();
		Formula aktFormula;
		while( i.hasNext() )
		{
			aktFormula = i.next();
			if( !(aktFormula.isClause() || aktFormula.isCNF()) )
				out = false;
		}
		return out;
	}

	public ArrayList<Literal> getLiterals()
	{
		ArrayList<Literal> allLiterals = new ArrayList<Literal>();
		for( Formula f : formulas )
		{
			allLiterals.addAll( f.getLiterals() );
		}
		return Literal.getLiteralsSet( allLiterals );
	}

	@Override
	public Boolean getTruthValue()
	{
		Boolean result = true;
		for( Formula f : formulas )
		{
			result = result && f.getTruthValue();
		}

		return result;
	}

	/**
	 * Flattens a Conjunction in CNF by removing nested Conjunctions.
	 * Does this by overwriting the list of children formulae.
	 * Only operates on formulae which are already in a CNF, else it does nothing and returns false.
	 * If this formula is in CNF its children formulae are either Disjunctions (clauses) or Conjunctions which are in CNF.
	 * Children formulae which are Disjunctions are added to the new list of children formulae.
	 * Children formulae in CNF are first flattened and then all their children are added to the new list of children formulae.
	 * 
	 * @return boolean true if this formula was flattened, false if nothing was done
	 */
	public boolean flattenCNF()
	{
		if( isCNF() )
		{

			ArrayList<Formula> newFormulas = new ArrayList<Formula>();
			Iterator<Formula> j = formulas.iterator();
			Formula aktFormula;
			while( j.hasNext() )
			{
				aktFormula = j.next();
				if( aktFormula instanceof Conjunction )
				{
					((Conjunction) aktFormula).flattenCNF();
					newFormulas.addAll( ((Conjunction) aktFormula).getFormulas() );
				}
				else
				{
					newFormulas.add( aktFormula );
				}
			}
			formulas = newFormulas;
			return true;
		}
		else
		{
			return false;
		}
	}


	public Formula matrixCNF()
	{
		Iterator<Formula> i = formulas.iterator();
		ArrayList<Formula> newFormula = new ArrayList<Formula>();
		while (i.hasNext())
		{
			Formula next = i.next();
			if (next != null)
				newFormula.add(next.matrixCNF());
		}
		Conjunction out = new Conjunction(newFormula);
		out.flattenCNF();
		return out;
	}
	public Formula matrixDNF()
	{
		Iterator<Formula> i = formulas.iterator();
		ArrayList<Formula> newFormula = new ArrayList<Formula>();
		while (i.hasNext())
		{
			Formula next = i.next();
			if (next != null)
				newFormula.add(next.matrixCNF());
		}
		Matrix cnfMatrix = new Conjunction(newFormula).cnfToMatrix();
		return cnfMatrix.toDNF();
	}

	public ArrayList<Formula> getDefinitionalCNFClauses( Atom a )
	{
		ArrayList<Equivalence> equivalences = new ArrayList<Equivalence>();
		ArrayList<Formula> literals = new ArrayList<Formula>();
		boolean flag = false;
		for( Formula f : formulas )
		{
			if( f.isLiteral() )
			{
				literals.add( f );
			}
			else
			{
				flag = true;
				Atom atom = new Atom( "p" + FORMULA_COUNTER++ );
				literals.add( atom );
				equivalences.add( new Equivalence( atom, f ) );
			}
		}
		
			Conjunction reultConj = new Conjunction( literals );
			Equivalence eq = new Equivalence( a, reultConj );
			Conjunction c = (Conjunction) eq.reducedConnectives().nnf();
			ArrayList<Formula> formulas = c.getFormulas();
			ArrayList<Formula> notClauseFormulas = new ArrayList<Formula>();
			ArrayList<Formula> tobeClauseFormulas = new ArrayList<Formula>();
			for( Formula f : formulas )
			{
				if( !f.isClause() )
				{
					notClauseFormulas.add( f );
					Formula cc = f.changeToClause();
					tobeClauseFormulas.add( cc );
				}
			}
			formulas.removeAll( notClauseFormulas );
			formulas.addAll( tobeClauseFormulas );
			for( Equivalence e : equivalences )
			{
				formulas.addAll( e.getFormula2().getDefinitionalCNFClauses( (Atom) e.getFormula1()) );
			}
			return formulas;
		
	}

	@Override
	public Formula distributeDisjunction()
	{
		ArrayList<Formula> firstDistributed = new ArrayList<Formula>();
		for( int i = 0; i < formulas.size(); i++ )
		{
			firstDistributed.add( formulas.get( i ).distributeDisjunction() );
		}
		return new Conjunction( firstDistributed );
	}
	
	/**
	 * Creates a matrix object from this formula if it is in CNF, else it returns null.
	 * If this formula is in CNF it is first flattened with the method flattenCNF.
	 * Then an object of the Matrix class is created with the keyset being the used literals of this formula.
	 * A column i of this matrix has the value true in the row indexed by a literal p if p appears in the i'th clause of this CNF.
	 * 
	 * @return Matrix corresponding this formula if this is in CNF, else null.
	 */
	public Matrix cnfToMatrix()
	{
		if (!(isCNF()))
		{
			return null;
		}
		else
		{
			flattenCNF();
			ArrayList<Literal> usedLiterals = getLiterals();
			Matrix matrix = new Matrix();
			Literal aktLit;
			Iterator<Literal> i = usedLiterals.iterator();
			while (i.hasNext())
			{
				boolean[] xvec = new boolean[formulas.size()];
				aktLit = i.next();
				int rowOnes = 0;
				for (int k = 0; k < formulas.size(); k++)
				{
					ArrayList<Literal> innerLit = formulas.get(k).getLiterals();
					if (innerLit.contains(aktLit))
					{
						xvec[k] = true;
						rowOnes++;
					}
				}
				aktLit.setRowOnes(rowOnes);
				matrix.put(aktLit, xvec);
			}
			return matrix;
		}
	}

}
