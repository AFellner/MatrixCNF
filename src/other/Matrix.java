package other;

import java.util.*;

import formula.*;

/**
 * Matrix.java
 * Purpose: Represents a matrix belonging to a propositional formula in either Conjunctive Normal Form or Disjunctive Normal Form.
 * Has methods implemented to calculate paths through this matrix.
 * From these paths Conjunctive- or Disjunctive Normal Forms can be calculated.
 * It is maintained as a HashMap, where literals are the key objects and their values are boolean arrays.
 * One boolean array denotes one row in the matrix.
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */

public class Matrix extends HashMap<Literal,boolean[]> {
       
	/**
	 * Deletes one column of the matrix.
	 * If the matrix has no column anymore the clear method is called.
	 * 
	 * @param column denotes the column to be deleted.
	 */
    public void delColumn(int column)
    {
    	if (getXDim() == 1 && column == 0)
    	{
    		this.clear();
    	}
    	else
    	{
	    	Iterator<Literal> i = this.keySet().iterator();
	    	while (i.hasNext())
	    	{
	    		Literal lit = i.next();
	    		boolean[] vec = this.get(lit);
	    		if (column < vec.length)
	    		{
	    			boolean[] tmp = vec;
	    			vec = new boolean[vec.length-1];
	    			for (int k = 0; k < column; k++)
	    			{
	    				vec[k] = tmp[k];
	    			}
	    			for (int j = column +1; j < tmp.length; j++)
	    			{
	    				vec[j-1] = tmp[j];
	    			}
	    			if (tmp[column]) lit.setRowOnes(lit.getRowOnes()-1);
	    			this.put(lit, vec);
	    		}
	    	}
    	}
    }
    
    /**
     * Cross checks all literals in the keyset of this matrix and returns the pairs of contradicting ones.
     * For example if the rows of this matrix are denoted by the literals p,q,-p,r,-q then the pairs [p,-p],[q,-q] are returned.
     * 
     * @return ArrayList of contradicting literals.
     */
    public ArrayList<ContradictPair> getContradictPairs()
    {
    	ArrayList<ContradictPair> pairs = new ArrayList<ContradictPair>();
    	Literal[] lits = new Literal[this.keySet().size()];
    	Iterator<Literal> k = this.keySet().iterator();
    	int f = 0;
    	while (k.hasNext())
    	{
    		lits[f] = k.next();
    		f++;
    	}
    	for (int i = 0; i < lits.length; i++)
    	{
    		Literal lit1 = lits[i]; 
    		for (int j = 0; j < i; j++)
    		{
    			Literal lit2 = lits[j];
    			if (lit1.contradicts(lit2)) pairs.add(new ContradictPair(lit1,lit2));
    		}
    	}
    	return pairs;
    }
    
    /**
     * 
     * @return int length of the array of the first entry in the keyset.
     */
    public int getXDim()
    {
    	return this.get(this.keySet().toArray()[0]).length;
    }
    
    /**
     * Deletes tautological columns.
     * A column is tautological if for a literal and its negated version the matrix has the value true stored in this column for both of them.
     */
    public void cancelTautologicalColumns()
    {
    	if (!this.isEmpty())
    	{
    		ArrayList<ContradictPair> pairs = getContradictPairs();
    		int columns = getXDim();
    		for (int i = 0; i < columns; i++)
    		{
    			Iterator<ContradictPair> j = pairs.iterator();
    			while (j.hasNext())
    			{
    				ContradictPair pair = j.next();
    				Literal lit1 = pair.getLit1();
    				Literal lit2 = pair.getLit2();
    				//System.out.println(lit1 + " has value: " + this.get(lit1, i) + " " + lit2 + " has value " + this.get(lit2, i));
    				if (this.get(lit1, i) && this.get(lit2, i))
    				{
    					//System.out.println("so we have to delete column " + i);
    					delColumn(i);
    					i--;
    					columns--;
    				}
    			}
    		}
    	}
    }
    
    /**
     * 
     * @return boolean true if this matrix contains only the values false, false if there is at least one true entry.
     */
    public boolean isZeroMatrix()
    {
    	if (isEmpty()){return true;}
    	Iterator<Literal> i = this.keySet().iterator();
    	while (i.hasNext())
    	{
    		Literal lit = i.next();
    		boolean[] xvec = this.get(lit);
    		for (int k = 0; k < xvec.length; k++)
    		{
    			if (xvec[k]) return false;
    		}
    	}
    	return true;
    }
    
    /**
     * Returns the truth value stored for in the row denoted by the literal lit and the column x.
     * 
     * @param lit denotes the desired row
     * @param x denotes the desired column
     * @return boolean stored value in the matrix for row lit and column x
     */
    public boolean get(Literal lit, int x) {
        return x >= this.get(lit).length ? false : this.get(lit)[x];
    }
    

    /**
     * 
     * @return String denoting this matrix row by row with the toString value of the row literal followed by the stored boolean array.
     */
    public String toString()
    {
    	String out = "";
    	Iterator<Literal> i = this.keySet().iterator();
    	while (i.hasNext())
    	{
    		Literal lit = i.next();
    		out = out + lit + " : ";
    		boolean[] xvec = this.get(lit);
    		//System.out.println("lit " + lit + " xvec " + xvec);
    		for (int k = 0; k < xvec.length-1; k++)
    		{
    			out = out + xvec[k] + " , ";
    		}
    		out = out + xvec[xvec.length-1];
    		if (i.hasNext()) out = out+ "\n";
    	}
    	return out;
    }
    
    /**
     * 
     * @param detail denotes if more details should be returned
     * @return String denoting this matrix row by row with the toString value of the row literal followed by the stored boolean array.
     *         If detail was set to true, for each row the number of true values and the hashcode of the literal are printed.
     */
    public String toString(boolean detail)
    {
    	String out = "";
    	Iterator<Literal> i = this.keySet().iterator();
    	while (i.hasNext())
    	{
    		Literal lit = i.next();
    		out = out + lit + " : ";
    		boolean[] xvec = this.get(lit);
    		//System.out.println("lit " + lit + " xvec " + xvec);
    		for (int k = 0; k < xvec.length-1; k++)
    		{
    			out = out + xvec[k] + " , ";
    		}
    		out = out + xvec[xvec.length-1];
    		if (detail) out = out+" || ones: "+lit.getRowOnes() + " hC: " + lit.hashCode();
    		if (i.hasNext()) out = out+ "\n";
    	}
    	return out;
    }
    
    /**
     * Updates the number of true values in a row for each literal in the keyset.
     */
    public void setRows()
    {
    	Iterator<Literal> i = this.keySet().iterator();
    	while (i.hasNext())
    	{
    		Literal lit = i.next();
    		boolean[] xvec = this.get(lit);
    		int rowOnes = 0;
    		if (xvec == null)
    		{
    			xvec = new boolean[0];
    		}
    		else
    		{
    			for (int k = 0; k < xvec.length; k++)
    			{
    				if (xvec[k]) rowOnes++;
    			}
    		}
    		lit.setRowOnes(rowOnes);
    	}
    }
    
    /**
     * Generates a list of paths through matrix which are predeccessed by a path q.
     * It implements the generate algorithm described in {@link http://link.springer.com/article/10.1007/BF00249017}.
     * 
     * @param q is the path which is the initial part of all the computed paths through this matrix.
     * @return ArrayList of paths through this matrix.
     */
    public ArrayList<Path> generate(Path q)
    {
    	ArrayList<Path> out = new ArrayList<Path>();
    	Matrix m = this.clone();
    	//System.out.println("generate call on");
    	//System.out.println(m);
    	//System.out.println("with initial path " + q);
    	/*
    	 * from the paper:
    	 * 1.  If  i  is  greater  than  the  last  column  of  M  then  return  {Q}. 
    	 * 
    	 * implemented: always take column 0; delete the other ones -> i is greater than the last column means
    	 * the last column was deleted last step -> m is empty
    	 */
    	if (m.isEmpty())
    	{
    		out.add(q);
    		return out;
    	}
    	else
    	{
    		
    		/*
    		 * 2.  If  there  is  a  p  in  P(Q)  such  that  M(p,  i)  =  1  then  i,=i  +  1;  to  to  1. 
    		 */
    		Iterator<Literal> i = q.iterator();
    		while (i.hasNext())
    		{
    			Literal aktP = i.next();
    			//System.out.println(m.toString(true));
    			//System.out.println("has null for literal: " + aktP +" hC: " + aktP.hashCode() + " in the first column; lit is contained in keyset: " + m.keySet().contains(aktP));
    			if (m.get(aktP, 0)) 
    			{
    				//I dont need to copy the matrix since i dont need the column anymore
    				//System.out.println("skip column 0");
    				m.delColumn(0);
    				return m.generate(q);
    			}
    				
    		}
    		/*
    		 * 3.  out:=empty.  Let  V = the set of literals in the matrix, for which the dual literal is not in the path yet
    		 *                           and m has a 1 entry for this variable in the current column
    		 */
    		Set<Literal> vall = m.keySet();
    		ArrayList<Literal> vgood = new ArrayList<Literal>();
    		Iterator<Literal> k = vall.iterator();
    		while (k.hasNext())
    		{
    			Literal lit = k.next();
    			//System.out.println("literal: " + lit + " good/bad");
    			if (m.get(lit, 0)) 
    			{
    				Iterator<Literal> f = q.iterator();
    				boolean nocontradictions = true;
    				while (f.hasNext())
    				{
    					Literal pathlit = f.next();
    					if (pathlit.contradicts(lit)) nocontradictions = false; //System.out.println(lit + " is bad");
    				}
    				if (nocontradictions) 
    				{
    					vgood.add(lit);
    					//System.out.println(lit + " is good");
    				}
    			}
    		}
    		/*
    		 *   Sort  V  according
    		 *   to  the  number  of  l-entries  in  the  columns  i  +  1  to  k,  such  that  the 
    		 *   variable  with  the  highest  number  of  1-entries  in  M  becomes  the  first element. 
    		 *   	For  all  q~  Vdo 
    		 *   		3.1  Generate  all  paths  of  M  with  initial  part  Q  @  (q)  at  column  i  +  1;
    		 *   		3.2  Add  these  paths  to  ~;
    		 *   		3.3  Cancel  the  row  q  from  M.
    		 */
    		m.delColumn(0);
    		if (vgood.isEmpty())
    		{
    			Path clonepath = q.clone();
    			ArrayList<Path> newPaths = m.generate(clonepath);
    			//2
    			for (Path newP: newPaths)
    			{
    				out.add(newP);
    			}
    		}
    		setRows();
    		Collections.sort(vgood,new LiteralComperator());
    		for (Literal lit: vgood)
    		{
    			//1
    			//Matrix newm = m.clone();
    			//newm.delColumn(0);
    			Path clonepath = q.clone();
    			clonepath.add(lit);
    			ArrayList<Path> newPaths = m.generate(clonepath);
    			//2
    			for (Path newP: newPaths)
    			{
    				out.add(newP);
    			}
    			//3
    			m.remove(lit);
    		}
    		
    		//Collections.sort(vgood);
    	}
    	return out;
    }
    
    /**
     * 
     * @return Matrix which is a copy of this matrix. Used literals are not copied.
     */
    public Matrix clone()
    {
    	Matrix clone = new Matrix();
    	Iterator<Literal> i = this.keySet().iterator();
    	while (i.hasNext())
    	{
    		Literal lit = i.next();
    		boolean[] vec = this.get(lit).clone();
    		clone.put(lit, vec);
    	}
    	return clone;
    }
    
    /**
     * 
     * @return Matrix which is a copy of this matrix. Used literals are copied.
     */
    public Matrix deepclone()
    {
    	Matrix clone = new Matrix();
    	Iterator<Literal> i = this.keySet().iterator();
    	while (i.hasNext())
    	{
    		Literal lit = i.next();
    		boolean[] vec = this.get(lit).clone();
    		clone.put(lit.clone(), vec);
    	}
    	return clone;
    }
    
    /**
     * 
     * @param column denotes the column index to be returned
     * @return boolean[] Array of truth values in the desired column for all rows in this matrix.
     */
    public boolean[] getColumn(int column)
    {
    	boolean[] out = new boolean[this.size()];
    	int i = 0;
    	for (Literal lit: this.keySet())
    	{
    		out[i] = this.get(lit, column);
    		i++;
    	}
    	return out;
    }
    
    /**
     * Checks if one column of this matrix absorbs another.
     * Column1 absorbs column2 if where ever column1 has the value true, column2 has the value true aswell.
     * 
     * @param col1 index of the first column to be compared
     * @param col2 index of the second column to be compared
     * @return boolean true if wherever col2 absorbs col1
     */
    public boolean colAbsorb(boolean[] col1, boolean[] col2)
    {
    	if (col1.length != col2.length)
    	{
    		return false;
    	}
    	else
    	{
    		boolean out = true;
    		for (int i= 0; i < col1.length; i++)
    		{
    			if (col2[i] && !col1[i]) out = false;
    		}
    		return out;
    	}
    }
    
    /**
     * Deletes all absorbed columns from this matrix.
     * Does this by cross checking all columns if they absorb each other by using the colAbsorb method.
     * 
     * @return boolean true if the resulting matrix is a zero matrix, false otherwise.
     */
    public boolean cancelAbsorbedColumns()
    {
    	if (isEmpty())
    	{
    		return true;
    	}
    	int xdim = getXDim();
    	TreeSet<Integer> columnsToDelete = new TreeSet<Integer>();
    	for (int i = 0; i < xdim; i++)
    	{
    		boolean[] col1 = getColumn(i);
    		for (int j = 0; j < i; j++)
    		{
    			boolean[] col2 = getColumn(j);
    			if (colAbsorb(col1,col2)) 
    			{
    				//System.out.println("column " + j + " absorbs col " + i);
    				columnsToDelete.add(i);
    			}
    			else if (colAbsorb(col2,col1))
    			{
    				//System.out.println("column " + i + " absorbs col " + j);
    				columnsToDelete.add(j);
    			}
    		}
    	}
    	int deleted = 0;
    	for (int del: columnsToDelete)
    	{
    		//System.out.println("delete col: " + (del - deleted));
    		delColumn(del-deleted);
    		deleted++;
    	}
    	//System.out.println("after delete\n" + this);
    	setRows();
    	return isZeroMatrix();
    }
    
    /**
     * Calculates a list of paths through this matrix.
     * These paths denote so called prime implicants of the underlying formula.
     * It implements the transform algorithm described in {@link http://link.springer.com/article/10.1007/BF00249017}.
     * 
     * @return ArrayList of all paths through this matrix.
     */
    public ArrayList<Path> allPaths()
    {
    	ArrayList<Path> paths = new ArrayList<Path>();
    	//System.out.println(this);
    	cancelTautologicalColumns();
    	while (!(cancelAbsorbedColumns()))
    	{
    		setRows();
    		ArrayList<Literal> literals = new ArrayList<Literal>();
    		literals.addAll(this.keySet());
    		Collections.sort(literals,new LiteralComperator());
    		Literal maxlit = literals.get(0);
    		int i = 0;
    		//System.out.println("halli hallo\n" + this + "\nspalte 1 absorbed?: " + colAbsorb(getColumn(1),getColumn(0)));
    		while (!get(maxlit,i))
    		{
    			i++;
    		}
    		exchangeColumns(0,i);
    		Path maxlitpath = new Path();
    		maxlitpath.add(maxlit);
    		Matrix mcopy = this.clone();
    		mcopy.delColumn(0);
    		paths.addAll(mcopy.generate(maxlitpath));
    		this.remove(maxlit);
    	}
    	ArrayList<Path> cleanedPaths = new ArrayList<Path>();
    	cleanedPaths.addAll(paths);
    	for (int i = 0; i < paths.size(); i++)
    	{
    		Path p1 = paths.get(i);
    		for (int j = 0; j < i; j++)
    		{
    			Path p2 = paths.get(j);
    			if (p1.subsumes(p2))
    			{
    				cleanedPaths.remove(p2);
    			}
    			else if (p2.subsumes(p1))
    			{
    				cleanedPaths.remove(p1);
    			}
    		}
    	}
    	return cleanedPaths;
    }
    
    /**
     * 
     * @return Conjunction formed by first disjunctively connecting all literals which have true stored in this column and then disjunctively connecting these disjunctions.
     */
    public Conjunction toCNF()
    {
    	ArrayList<Path> paths = allPaths();
    	if (paths.isEmpty())
    	{
    		Atom bla = new Atom("emptyCNF");
    		ArrayList<Formula> blabla = new ArrayList<Formula>();
    		blabla.add(bla);
    		return new Conjunction(blabla);
    	}
    	ArrayList<Formula> conjuncts = new ArrayList<Formula>();
		for (Path p: paths)
		{
			ArrayList<Formula> disjuncts = new ArrayList<Formula>();
			for (Literal lit: p)
			{
				disjuncts.add(lit.toFormula());
			}
			conjuncts.add(new Disjunction(disjuncts));
		}
		return new Conjunction(conjuncts);
    }
    
    /**
     * 
     * @return Disjunction formed by first conjunctively connecting all literals which have true stored in this column and then conjunctively connecting these conjunctions.
     */
    public Disjunction toDNF()
    {
    	ArrayList<Path> paths = allPaths();
    	if (paths.isEmpty())
    	{
    		Atom bla = new Atom("emptyDNF");
    		ArrayList<Formula> blabla = new ArrayList<Formula>();
    		blabla.add(bla);
    		return new Disjunction(blabla);
    	}
    	ArrayList<Formula> disjuncts = new ArrayList<Formula>();
		for (Path p: paths)
		{
			//System.out.println(p);
			ArrayList<Formula> conjuncts = new ArrayList<Formula>();
			for (Literal lit: p)
			{
				conjuncts.add(lit.toFormula());
			}
			disjuncts.add(new Conjunction(conjuncts));
		}
		return new Disjunction(disjuncts);
    }
    /**
     *
     * @param col1 first column to be exchanged
     * @param col2 second column to be exchanged
     */
    public void exchangeColumns(int col1, int col2)
    {
    	for (Literal lit: this.keySet())
    	{
    		boolean[] xvec = this.get(lit);
    		boolean tmp = xvec[col1];
    		xvec[col1] = xvec[col2];
    		xvec[col2] = tmp;
    		this.put(lit, xvec);
    	}
    }
}