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

public class finalTests
{
	public static void main(String[]args)
	{
		RandomJSON.testInstance(20, 10,true,"testfile");
		Formula fNor = Parser.parse( "testfile" );
		Formula fRtc = fNor.removeTopBottom().reducedConnectives();
		Formula fnnf=  fRtc.nnf();
		Formula fDcnf= fNor.toDefinitionalCNF();
		Formula fMat = fnnf.matrixCNF();
		Formula fNai = fnnf.toNaiveCNF( fnnf );
		
		
		System.out.println("Normal              : "+fNor);
		System.out.println("Reduced Connectives : "+fRtc);
		System.out.println("NNF                 : "+fnnf);
		System.out.println("MATRIX              : "+fMat);
		System.out.println("Defintional CNF     : "+fDcnf);
		System.out.println("Naitve CNF          : "+fNai);
		System.out.println("Normal satisfiable  : "+FormulaComperator.satisfy( fNor ));
		System.out.println("Normal  = Reduced   : "+FormulaComperator.compare( fNor, fRtc ) );
		System.out.println("Reduced = NNF       : "+FormulaComperator.compare( fRtc, fnnf ) );
		System.out.println("NNF    sat  Def.CNF : "+(FormulaComperator.satisfy( fnnf ) == FormulaComperator.satisfy( fDcnf )));
		System.out.println("Naive = NNF         : "+FormulaComperator.compare( fNor, fNai ) );
		System.out.println("NNF    sat  Mat.CNF : "+(FormulaComperator.satisfy( fnnf ) == FormulaComperator.satisfy( fMat )));
		
		
		
		
		ArrayList<ArrayList <Literal> > lits = new ArrayList<ArrayList<Literal> >();
		for(Formula f: ((Conjunction) fDcnf).getFormulas())
		{
			ArrayList<Literal> literas =  f.getLiterals();
			lits.add( literas );	
		}
		HumanReadableCNF.humanReadableCNFConverter( lits, "hobbaLala" );
		
		
		
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