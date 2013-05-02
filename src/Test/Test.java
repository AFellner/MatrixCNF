package Test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import formula.*;
import other.*;
import parse.*;


public class Test {

	public static void main(String args[])
	{
		Atom a = new Atom("a");
		Atom b = new Atom("b");
		Atom c = new Atom("c");
		Atom d = new Atom("d");
		Atom e = new Atom("e");
		Atom f = new Atom("f");
		Bottom bot = new Bottom();
		Top top = new Top();
		
		Disjunction aORb = new Disjunction(a,b);
		Disjunction bORc = new Disjunction(b,c);
		Disjunction aORc = new Disjunction(a,c);
		
		Conjunction aANDb = new Conjunction(a,b);
		Conjunction bANDc = new Conjunction(b,c);
		Conjunction aANDc = new Conjunction(a,c);
		
		Implication aIMPb = new Implication(a,b);
		Implication bIMPc = new Implication(b,c);
		Implication aIMPc = new Implication(a,c);
		
		Equivalence aEQb = new Equivalence(a,b);
		Equivalence bEQc = new Equivalence(b,c);
		Equivalence aEQc = new Equivalence(a,c);
		
		IfThenElse iAtBeC = new IfThenElse(a,b,c);
		IfThenElse iAtCeB = new IfThenElse(a,c,b);
		IfThenElse iBtAeC = new IfThenElse(b,a,c);
		
		/*********************************************************************************************
		 * Negation normal form Test
		 */
		//System.out.println(new Conjunction(iAtBeC.reducedConnectives(),new Top()));
		
		//System.out.println(new Equivalence(new Conjunction(new Negation(iAtBeC),d),aEQc).nnf());
		//System.out.println(new Equivalence(aEQc,aEQb).nnf());
		//System.out.println(new Implication(aIMPb,bIMPc).nnf());
		//Formula test = new Negation(new Disjunction(new Negation(a),b));
		//System.out.println(test);
		//System.out.println(test.nnf());
		
		//Formula test2 = new Disjunction(new Negation(new Negation(a)),b);
		//System.out.println(test2);
		//System.out.println(test2.nnf());
		
		//! (( ! (' 3 ')  | ' 4 ' ))
		//Formula toTest = not()
		
		
		/*********************************************************************************************
		 * Matrix Object Test
		 */
		
		/*Disjunction dnf = or(and(a,b),and(and(d,c),not(c)));
		//System.out.println(dnf.isDNF());
		System.out.println("dnf before flatteing " + dnf);
		dnf.flattenDNF();
		System.out.println("dnf after flattening: " + dnf);
		Matrix dnfm = dnf.dnfToMatrix();
		System.out.println("matrix before delete\n" + dnfm);
		dnfm.cancelTautologicalColumns();
		System.out.println("matrix after delete\n" + dnfm);
		ArrayList<Literal> toDelete = new ArrayList<Literal>();
		Iterator<Literal> i = dnfm.keySet().iterator();
		while (i.hasNext())
		{
			Literal lit = i.next();
			if ((lit.getAtom().getAtomName().equals("a"))||(lit.getAtom().getAtomName().equals("b"))) 
			{
				//System.out.println("trying to remove: " + lit + " is contained? " + dnfm.containsKey(lit));
				toDelete.add(lit);
			}
		}
		i = toDelete.iterator();
		while (i.hasNext())
		{
			dnfm.remove(i.next());
		}
		System.out.println("matrix after row deltee\n" + dnfm + "\nis 0 matrix?: " + dnfm.isZeroMatrix());
		ArrayList<ContradictPair> pairs = dnfm.getContradictPairs();
		Iterator<ContradictPair> z = pairs.iterator();
		while (z.hasNext())
		{
			System.out.println(z.next());
		}
				
		//System.out.println(dnf.dnfToMatrix().)
		
		int[] values1 = {1,1};
		int[] values2 = {0,0};
		Literal lit1 = new Literal(a,true);
		lit1.setRowOnes(0);
		Literal lit2 = new Literal(b,false);
		lit2.setRowOnes(2);
		Matrix tM = new Matrix();
		tM.put(lit1, values1);
		tM.put(lit2, values2);
		System.out.println(tM.keySet());
		System.out.println(lit1.compareTo(lit2));
		lit1.setRowOnes(3);
		System.out.println(tM.keySet());
		System.out.println(lit1.compareTo(lit2));
		
		int[] testints = new int[5];
		System.out.println(testints[1]);*/
		
		/*TreeMap<Literal,Integer> test = new TreeMap<Literal,Integer>();
		test.put(lit1, 5);
		System.out.println(test.get(lit1));*/
		
		/*ArrayList<Literal> p = new ArrayList<Literal>();
		Literal litS1 = new Literal(a,true,0);
		Literal litS2 = new Literal(b,false,3);
		Literal litS3 = new Literal(c,false,1);
		p.add(litS1);
		p.add(litS2);
		p.add(litS3);
		for (int k = 0; k < p.size(); k++)
		{
			System.out.println("before sort l1: " +p.get(k) + " # " + p.get(k).getRowOnes());
		}
		Collections.sort(p,new LiteralComperator());
		System.out.println("compare 1,2:" + litS1.compareTo(litS2));
		//Iterator<Literal> r = p.iterator();
		for (Literal lit: p)
		{
			//Literal lit = r.next();
			System.out.println("it - after sort l1: " +lit + " # " + lit.getRowOnes());
		}
		for (int k = 0; k < p.size(); k++)
		{
			System.out.println("for - after sort l1: " +p.get(k) + " # " + p.get(k).getRowOnes());
		}
		
		Disjunction dnfFresh = or(and(a,b),and(and(d,c),not(c)));
		Matrix cloneTest = dnfFresh.dnfToMatrix();
		System.out.println("initially: " + cloneTest.toString(true));
		Matrix clone2 = cloneTest.clone();
		clone2.delColumn(0);
		System.out.println("clone deleted " + clone2.toString(true));
		System.out.println("initial after: " + cloneTest.toString(true));
		*/
		
		
		/*********************************************************************************************
		 * Remove top - bottom Test
		 */
		/*
		Formula rtbTest = ite(a,and(bot,b),top);
		System.out.println(rtbTest);
		System.out.println(rtbTest.removeTopBottom());
		
		Formula rtbTest2 = and(bot,b);
		System.out.println(rtbTest2);
		System.out.println(rtbTest2.removeTopBottom());
		
		Formula rtbTest3 = or(top,b);
		System.out.println(rtbTest3);
		System.out.println(rtbTest3.removeTopBottom());
		
		Formula rtbTest4 = eq(rtbTest,rtbTest2);
		System.out.println(rtbTest4);
		System.out.println(rtbTest4.removeTopBottom());*/
		
		/*********************************************************************************************
		 * Random JSON - Parser Test
		 */
		
		/*System.out.println("Random JSON Test");
		
		RandomJSON.testInstance(10, 5,true,"testfile");
		Formula rndFormula = Parser.parse("testfile");
		//Formula rndFormula = not(not(a));
		Formula rmTB = rndFormula.removeTopBottom();
		Formula nnf = rndFormula.nnf();
		
		//Formula rdC = rndFormula.reducedConnectives();
		System.out.println("random formula: \n"+ rndFormula);
		//Negation rmTBtest = (Negation)rmTB;
		//System.out.println("random formula without top, bottom:\n" + rmTB);
		//System.out.println("random formula reduced connectives:\n" + rdC);
		System.out.println("random formula nnf with reduced top/bottom:\n" + nnf);
		
		//System.out.println("remove top bottom equivalent?: " + FormulaComperator.compare(rndFormula,rmTB));
		//System.out.println("reduced connectives equivalent?: " + FormulaComperator.compare(rndFormula,rdC));
		
		Atom a1 = new Atom(" 4 ");
		Atom b1 = new Atom(" 1 ");
		Atom c1 = new Atom(" 3 ");
		Atom c2 = new Atom(" 3 ");
		Atom d1 = new Atom(" 2 ");
		
		//Formula compareTest = or(or(ite(a1,b1,c1),c2),(imp(b1,d1)));
		//System.out.println(compareTest);
		System.out.println("equivalent?: " + FormulaComperator.compare(rndFormula,nnf));
		//System.out.println("End Random JSON Test");
		*/
		
		/*
		boolean equivalent = true;
		int tests = 100;
		for (int i = 0; i < tests; i++)
		{
			RandomJSON.testInstance(20, 20,true,"testfile");
			Formula rndi = Parser.parse("testfile");
			Formula nnfi = rndi.nnf();
			equivalent = FormulaComperator.compare(rndi,nnfi);
			if (!equivalent) break;
		}
		System.out.println("Ran " + tests + " tests. All Formulae were equivalent to their computed NNFs?: " + equivalent);
		*/
		
		
		/*********************************************************************************************
		 * Is CNF Is DNF Test
		 */
		
		/*Disjunction dnf = or(and(not(a),b),and(b,not(c)));
		System.out.println(dnf);
		System.out.println("DNF?: " + dnf.isDNF());
		Conjunction cnfFromDNF = dnf.dnfToMatrix().toCNF();
		System.out.println(cnfFromDNF);
		
		System.out.println("cnf equiv. to dnf?: " + FormulaComperator.compare(dnf,cnfFromDNF));
		
		Conjunction cnf = and(or(a,not(b)),and(or(a,b),or(c,d)));
		//Conjunction cnf = and(or(a,b),or(b,c));
		System.out.println(cnf);
		System.out.println("CNF?: " + cnf.isCNF());
		Matrix dnfMatrix = cnf.cnfToMatrix();
		System.out.println(dnfMatrix);
		Disjunction dnfFromCNF = dnfMatrix.toDNF();
		System.out.println(dnfFromCNF);
		
		System.out.println("dnf equiv. to cnf?: " + FormulaComperator.compare(cnf,dnfFromCNF));
		*/
		/*System.out.println(and(a,b).isDualClause());
		System.out.println(b.isLiteral());
		
		ArrayList<Formula> cnfList = new ArrayList<Formula>();
		ArrayList<Formula> clause1 = new ArrayList<Formula>();
		clause1.add(not(a));
		clause1.add(b);
		clause1.add(c);
		clause1.add(not(not(d)));
		cnfList.add(new Disjunction(clause1));
		ArrayList<Formula> clause2 = new ArrayList<Formula>();
		clause2.add(a);
		clause2.add(c);
		clause2.add(not(e));
		cnfList.add(new Disjunction(clause2));
		cnfList.add(a);
		cnfList.add(not(c));
		Conjunction cnf = new Conjunction(cnfList);
		System.out.println(cnf);
		System.out.println("CNF?: " + cnf.isCNF());*/
		
		
		/*********************************************************************************************
		 * Matrix CNF test
		 */
		
		/*RandomJSON.testInstance(10, 5,true,"testfile");
		//Formula rndFM = Parser.parse("cnftest");
		//Formula rndFM = and(or(and(a,b),and(b,c)),or(and(b,c),d));
		Formula rndFM = and(a,b);
		//System.out.println(rndFM+ " is dual clause?: " + rndFM.isDualClause() + " is in DNF?: " + rndFM.isDNF());
		Formula rndFMnnf = rndFM.nnf();
		Formula rndFMCNF = rndFM.toCNF(0);
		System.out.println("original formula in nnf: "+ rndFMnnf);
		System.out.println("CNF: " + rndFMCNF);
		*/
		
		/*********************************************************************************************
		 * Defenitional CNF Test
		 */
		/*RandomJSON.testInstance(10, 5,true,"testfile");
		Formula rndFM = Parser.parse("testfile");
		System.out.println("original: " + rndFM);
		System.out.println("definitional CNF: " + rndFM.toDefinitionalCNF());*/
		
		/*********************************************************************************************
		 * Transform algorithm Test
		 */
		
		/*
		Atom p = new Atom("p");
		Atom q = new Atom("q");
		Atom r = new Atom("r");
		Atom s = new Atom("s");
		
		Literal pl = new Literal(p,false);
		Literal ql = new Literal(q,false);
		Literal rl = new Literal(r,false);
		Literal npl = new Literal(p,true);
		Literal sl = new Literal(s,false);
		Literal nsl = new Literal(s,true);
		int[] pvec = {1,1,0,0};
		int[] qvec = {0,1,1,0};
		int[] npvec = {0,1,1,0,0};
		int[] rvec = {1,0,0,1};
		int[] svec = {0,0,1,1};
		int[] nsvec = {0,1,1,0,0,1};
		
		
		Conjunction dC1 = and(p,not(p));
		Conjunction dC2 = and(p,q);
		Conjunction dC3 = and(q,r);
		ArrayList<Formula> dnflist = new ArrayList<Formula>();
		dnflist.add(dC1);
		dnflist.add(dC2);
		dnflist.add(not(p));
		dnflist.add(dC3);
		Disjunction dnfToy = new Disjunction(dnflist);
		System.out.println("formula: " + dnfToy);
		//Matrix m = dnfToy.dnfToMatrix();
		Matrix m = new Matrix();
		m.put(pl, pvec);
		m.put(ql, qvec);
		//m.put(npl, npvec);
		m.put(rl, rvec);
		m.put(sl,svec);
		//m.put(nsl,nsvec);
		m.setRows();
		
		System.out.println("matrix:\n" + m.toString(true));
		m.exchangeColumns(0, 1);
		System.out.println("matrix:\n" + m.toString(true));
		Matrix minit = m.clone();
		Set<Literal> literals = m.keySet();
		minit.delColumn(0);
		Path initPath = new Path();
		initPath.add(pl);
		System.out.println(pl.hashCode());
		ArrayList<Path> paths = minit.generate(initPath);
		System.out.println(paths.size() + " paths generated");
		for (Path pt: paths)
		{
			System.out.println(pt);
		}
		m.setRows();
		System.out.println("originalMatrix after generate:\n" + m.toString(true));
		System.out.print("2nd column: ");
		int[] column2 = m.getColumn(1);
		for (int u = 0; u < column2.length; u++)
		{
			System.out.print(column2[u]);
		}
		System.out.print("\n");
		Matrix mc = m.clone();
		mc.cancelTautologicalColumns();
		mc.cancelAbsorbedColumns();
		System.out.println("mc:\n"+mc.toString(true));
		ArrayList<Path> testBla = m.allPaths();
		for (Path uga: testBla)
		{
			System.out.println("ugagagaga " + uga);
		}
		//System.out.println(plit.hashCode() + " ~ "+ plit.clone().hashCode());
		*/
		/*********************************************************************************************
		 * Random JSON - Parser Test
		 */
		/*
		System.out.println("Random JSON Test");
		
		RandomJSON.testInstance(10, 5,true,"nnfbug");
		Formula rndFormula = Parser.parse("nnfbug");
		//Formula rndFormula = not(not(a));
		Formula rmTB = rndFormula.removeTopBottom();
		Formula nnf = rmTB.reducedConnectives().nnfAfterReduce().nnf();
		
		//Formula rdC = rndFormula.reducedConnectives();
		System.out.println("random formula: \n"+ rndFormula);
		//Negation rmTBtest = (Negation)rmTB;
		//System.out.println("random formula without top, bottom:\n" + rmTB);
		//System.out.println("random formula reduced connectives:\n" + rdC);
		System.out.println("random formula nnf with reduced top/bottom:\n" + nnf);
		
		Formula cnf = nnf.distributeDisjunction();
		while(!cnf.isCNF()){
			System.out.println(cnf.toString());
			cnf = cnf.distributeDisjunction();
		}
		
		System.out.println("cnf:\n" + cnf);
		
		//System.out.println("remove top bottom equivalent?: " + FormulaComperator.compare(rndFormula,rmTB));
		//System.out.println("reduced connectives equivalent?: " + FormulaComperator.compare(rndFormula,rdC));
		
		Atom a1 = new Atom(" 4 ");
		Atom b1 = new Atom(" 1 ");
		Atom c1 = new Atom(" 3 ");
		Atom c2 = new Atom(" 3 ");
		Atom d1 = new Atom(" 2 ");
		
		//Formula compareTest = or(or(ite(a1,b1,c1),c2),(imp(b1,d1)));
		//System.out.println(compareTest);
		System.out.println("equivalent?: " + FormulaComperator.compare(rndFormula,cnf));
		//System.out.println("End Random JSON Test");*/
		
		/*********************************************************************************************
		 * Matrix DNF test
		 */
		/*RandomJSON.testInstance(20, 10,true,"testfile");
		Formula rndFormula = Parser.parse("testfile");
		//Formula rndFormula = and(a,not(a));
		Formula nnf = rndFormula.nnf();
		//System.out.println(nnf);
		Formula cnf = nnf.matrixCNF();
		System.out.println(cnf);
		System.out.println(cnf.isCNF());
		System.out.println("cnf equiv. to nnf?: " + FormulaComperator.compare(nnf,cnf));
		System.out.println("cnf equisat to nnf?" + (FormulaComperator.satisfy(nnf) == FormulaComperator.satisfy(cnf)));
		//System.out.println("End Random JSON Test");*/

		
		Formula rndFormula = Parser.parse("sudoku.json");
		System.out.println(rndFormula.matrixCNF());
	}
	
	private static IfThenElse ite(Formula a, Formula b, Formula c)
	{
		return new IfThenElse(a,b,c);
	}
	private static Disjunction or(Formula a, Formula b)
	{
		return new Disjunction(a,b);
	}
	private static Conjunction and(Formula a, Formula b)
	{
		return new Conjunction(a,b);
	}
	private static Implication imp(Formula a, Formula b)
	{
		return new Implication(a,b);
	}
	private static Equivalence eq(Formula a, Formula b)
	{
		return new Equivalence(a,b);
	}
	private static Negation not(Formula a)
	{
		return new Negation(a);
	}
}
