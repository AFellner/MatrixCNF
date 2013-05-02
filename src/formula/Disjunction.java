package formula;
import java.util.ArrayList;
import java.util.Iterator;
import other.*;

/**
 * Disjunction.java
 * Purpose: Represents a propositional formula, which main connective is a disjunction.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */

public class Disjunction extends Formula
{
	private ArrayList<Formula> formulas;
	
	public Disjunction(Formula f1, Formula f2)
	{
		ArrayList<Formula> dis = new ArrayList<Formula>();
		dis.add(f1);
		dis.add(f2);
		setFormulaType( FORMULA_DISJUNCTION );
		breakDownDisjunction(dis);
	}

	public Disjunction( ArrayList<Formula> fs )
	{
		setFormulaType( FORMULA_DISJUNCTION );
		breakDownDisjunction(fs);
	}
	
	public void breakDownDisjunction( ArrayList<Formula> fs )
	{
		formulas = new ArrayList<Formula>();
		for(Formula f : fs) 
		{
			if(f instanceof Disjunction) 
			{
				Disjunction dis = (Disjunction) f;
				for(Formula frmls : dis.getFormulas()) 
				{
					formulas.add(frmls);
				}
			} 
			else 
			{
				formulas.add(f);
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
	 * @return String composed by the toString values of the children formulae, connected with '|' symbols and surrounded by (,) brackets.
	 */
	public String toString()
	{
		String s = "(";
		for( Formula f : formulas )
		{
			s += /*" ( " +*/" " + f +/* " ) " +*/ " |";
		}
		s = s.substring( 0, s.length() - 1 );
		s += ")";
		return s;
	}
	public Formula removeTopBottom()
	{
		Iterator<Formula> i = formulas.iterator();
		ArrayList<Formula> newFormulas = new ArrayList<Formula>();
		while(i.hasNext())
		{
			Formula aktFormula = i.next();
			Formula cleanedFormula = aktFormula.removeTopBottom();
			if (cleanedFormula instanceof Top)
			{
				return new Top();
			}
			else if (!(cleanedFormula instanceof Bottom))
			{
				newFormulas.add(cleanedFormula);
				//System.out.println("füge " + cleanedFormula + " zu disj hinzu; ist instance of top: " + (cleanedFormula instanceof Top) + " sondern: " + cleanedFormula.getClass());
			}
		}
		if (newFormulas.isEmpty()) return new Bottom();
		return new Disjunction(newFormulas);
	}
	
	public Formula nnfAfterReduce()
	{
		Iterator<Formula> i = formulas.iterator();
		ArrayList<Formula> newDis = new ArrayList<Formula>();
		while (i.hasNext())
		{
			Formula next = i.next();
			newDis.add(next.nnfAfterReduce());
		}
		return new Disjunction(newDis);
	}
	
	public Formula reducedConnectives(){
		Iterator<Formula> i = formulas.iterator();
		ArrayList<Formula> newDis = new ArrayList<Formula>();
		while (i.hasNext())
		{
			Formula next = i.next();
			newDis.add(next.reducedConnectives());
		}
		return new Disjunction(newDis);
	}
	
	public boolean isCNF()
	{
		return isClause();
	}
	
	public boolean isDNF()
	{
		boolean out = true;
		Iterator<Formula> i = formulas.iterator();
		Formula aktFormula;
		while (i.hasNext())
		{
			aktFormula = i.next();
			//System.out.println(aktFormula + " is dual clause: " + aktFormula.isDualClause());
			if (!(aktFormula.isDualClause() || aktFormula.isDNF())) out = false;
		}
		return out;
	}
	
	public boolean isClause()
	{
		boolean out = true;
		Iterator<Formula> i = formulas.iterator();
		while (i.hasNext())
		{
			Formula next = i.next();
			if (!(next.isLiteral()))
			{
				if (next instanceof Disjunction)
				{
					if (!(((Disjunction)next).isClause())) out = false;
				}
				else
				{
					out = false;
				}
			}
		}
		return out;
	}

	
	/**
	 * Flattens a Disjunction in DNF by removing nested Disjunctions.
	 * Does this by overwriting the list of children formulae.
	 * Only operates on formulae which are already in a DNF, else it does nothing and returns false.
	 * If this formula is in DNF its children formulae are either Conjunctions (dual clauses) or Disjunctions which are in DNF.
	 * Children formulae which are Conjunction are added to the new list of children formulae.
	 * Children formulae in DNF are first flattened and then all their children are added to the new list of children formulae.
	 * 
	 * @return boolean true if this formula was flattened, false if nothing was done
	 */
	public void flattenDNF()
	{
		if (isDNF())
		{
			
			ArrayList<Formula> newFormulas = new ArrayList<Formula>();
			Iterator<Formula> j = formulas.iterator();
			Formula aktFormula;
			while (j.hasNext())
			{
				aktFormula = j.next();
				//System.out.println("***flatten disjunction " + aktFormula);
				if (aktFormula instanceof Disjunction)
				{
					((Disjunction) aktFormula).flattenDNF();
					newFormulas.addAll(((Disjunction) aktFormula).getFormulas());
				}
				else if (aktFormula instanceof Conjunction)
				{
					//System.out.println("flatten " + aktFormula);
					((Conjunction) aktFormula).flattenCNF();
					//System.out.println("results in " + aktFormula);
					newFormulas.add(aktFormula);
				}
				else
				{
					//System.out.println("add" + aktFormula);
					newFormulas.add(aktFormula);
				}
			}
			formulas = newFormulas;
		}
	}
	
	/**
	 * Creates a matrix object from this formula if it is in DNF, else it returns null.
	 * If this formula is in DNF it is first flattened with the method flattenDNF.
	 * Then an object of the Matrix class is created with the keyset being the used literals of this formula.
	 * A column i of this matrix has the value true in the row indexed by a literal p if p appears in the i'th clause of this DNF.
	 * 
	 * @return Matrix corresponding this formula if this is in DNF, else null.
	 */
	public Matrix dnfToMatrix()
	{
		if (!(isDNF()))
		{
			return null;
		}
		else
		{
			flattenDNF();
			ArrayList<Literal> usedLiterals = getLiterals();
			Matrix matrix = new Matrix();
			Formula aktCon;
			ArrayList<Literal> conjLit;
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

	public Formula matrixDNF()
	{
		Iterator<Formula> i = formulas.iterator();
		ArrayList<Formula> newFormula = new ArrayList<Formula>();
		while (i.hasNext())
		{
			Formula next = i.next();
			if (next != null)
				newFormula.add(next.matrixDNF());
		}
		Disjunction out = new Disjunction(newFormula);
		out.flattenDNF();
		return out;
	}
	
	public Formula matrixCNF()
	{
		Iterator<Formula> i = formulas.iterator();
		ArrayList<Formula> newFormula = new ArrayList<Formula>();
		while (i.hasNext())
		{
			Formula next = i.next();
			if (next != null)
				newFormula.add(next.matrixDNF());
		}
		Matrix dnfMatrix = new Disjunction(newFormula).dnfToMatrix();
		return dnfMatrix.toCNF();
	}

	public ArrayList<Literal> getLiterals()
	{
		ArrayList<Literal> allLiterals = new ArrayList<Literal>();
		for(Formula f: formulas)
		{
			allLiterals.addAll( f.getLiterals() );
		}
		return Literal.getLiteralsSet( allLiterals );
	}
	
	@Override
	public Boolean getTruthValue() {
		Boolean result = false;
		for(Formula f : formulas) {
			result = result || f.getTruthValue();
		}
		
		return result;
	}
	
	
	public ArrayList<Formula> getDefinitionalCNFClauses(Atom a)
	{
		ArrayList<Equivalence> equivalences = new ArrayList<Equivalence>();
		ArrayList<Formula> literals = new ArrayList<Formula>();
		for(Formula f: formulas)
		{
			if(f.isLiteral())
			{
				literals.add( f );
			}
			else
			{
				Atom atom = new Atom("p"+FORMULA_COUNTER++);
				literals.add( atom );
				equivalences.add( new Equivalence(atom,f) );
			}
		}
		Disjunction reultConj =  new Disjunction( literals );
		Equivalence eq = new Equivalence( a, reultConj);
		Conjunction c = (Conjunction)eq.reducedConnectives().nnf();
		ArrayList<Formula> formulas = c.getFormulas();
		ArrayList<Formula> notClauseFormulas = new ArrayList<Formula>();
		ArrayList<Formula> tobeClauseFormulas = new ArrayList<Formula>();
		for(Formula f : formulas)
		{
			if(!f.isClause())
			{
				notClauseFormulas.add( f );
				Formula cc = f.changeToClause();
				tobeClauseFormulas.add( cc );
			}
		}
		formulas.removeAll( notClauseFormulas );
		formulas.addAll( tobeClauseFormulas );
		for(Equivalence e: equivalences)
		{
			formulas.addAll( e.getFormula2().getDefinitionalCNFClauses( (Atom) e.getFormula1() ));
		}
		return formulas;
	}
	
	
	
	public Formula changeToClause()
	{
		ArrayList<Formula> toBeReturned = new ArrayList<Formula>();
		Conjunction theConjunction = null;
		for(Formula f:formulas)
		{
			if(f instanceof Conjunction)
			{
				theConjunction = (Conjunction) f;
				break;
			}
		}
		formulas.remove( theConjunction );
		for(Formula f: theConjunction.getFormulas())
		{
			ArrayList<Formula> formulea = Formula.cloneList( formulas );
			formulea.add( f );
			Disjunction d = new Disjunction(formulea);
			toBeReturned.add( d );
		}
		return new Conjunction(toBeReturned);
	}
	
	@Override
	public Formula distributeDisjunction() {
		Formula f1;
		Formula f2;
		ArrayList<Formula> firstDistributed = new ArrayList<Formula>();
		ArrayList<Formula> secondDistributed = new ArrayList<Formula>();
		for(int i = 0; i < formulas.size(); i++) {
			firstDistributed.add(formulas.get(i).distributeDisjunction());
		}
		for(int i = 0; i < firstDistributed.size(); i++) {
			f1 = firstDistributed.get(i);
			
			if(i == firstDistributed.size() - 1) {
				secondDistributed.add(f1);
				continue;
			}
			
			f2 = firstDistributed.get(i+1);
			
			ArrayList<Formula> newConjuncts = new ArrayList<Formula>();
			if(!f2.isLiteral() && f1.isLiteral()) {
				Conjunction conF2 = (Conjunction)f2;
				
				for(Formula f : conF2.getFormulas()) {
					newConjuncts.add(new Disjunction(f1, f));
				}
				
				secondDistributed.add(new Conjunction(newConjuncts));
				i++;
			}
			else if(!f2.isLiteral() && !f1.isLiteral()) {
				Conjunction conF1 = (Conjunction)f1;
				Conjunction conF2 = (Conjunction)f2;
				
				for(Formula f : conF2.getFormulas()) {
					newConjuncts.add(new Disjunction(conF1, f));
				}
				
				secondDistributed.add(new Conjunction(newConjuncts));
				i++;
			}
			else if(f2.isLiteral() && !f1.isLiteral()) {
				Conjunction conF1 = (Conjunction)f1;
				
				for(Formula f : conF1.getFormulas()) {
					newConjuncts.add(new Disjunction(f, f2));
				}
				
				secondDistributed.add(new Conjunction(newConjuncts));
				i++;
			}
			else {
				secondDistributed.add(f1);
			}
			
			
				
		}
		
		if(secondDistributed.size() > 1) {
			Disjunction returned = new Disjunction(secondDistributed);
			returned.flattenDNF();
			return returned;
		} else {
			Conjunction returned = new Conjunction(secondDistributed);
			return returned;
		}
			
		
//		return new Conjunction(secondDistributed);
	}

}