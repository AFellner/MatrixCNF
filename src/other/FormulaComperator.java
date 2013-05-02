package other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import parse.HumanReadableCNF;

import formula.Atom;
import formula.Conjunction;
import formula.Disjunction;
import formula.Formula;
import formula.Implication;
import formula.Literal;
import formula.Negation;

public class FormulaComperator {
	
	public static boolean compare(Formula a, Formula b) {
		boolean result = true;
		HashMap<Atom, Boolean> interpretation = new HashMap<Atom, Boolean>();
		
		Set<Atom> setOfAtom = new HashSet<Atom>();
		for(Literal l : a.getLiterals()) {
			setOfAtom.add(l.getAtom());
		}
		for(Literal l : b.getLiterals()) {
			setOfAtom.add(l.getAtom());
		}
		
		int remmant;
		for(int i = 0; i < Math.pow(2, setOfAtom.size()); i++) {
			remmant = i;
			for(Atom l : setOfAtom) {
				if(i == 0) {
					interpretation.put(l, false);
					continue;
				}
					
				if(remmant % 2 == 0)
					interpretation.put(l, false);
				else
					interpretation.put(l, true);
				
				remmant = remmant / 2; 
				if(remmant == 0) break;
			}
			
			if(!(verify(a, interpretation) == verify(b, interpretation))) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	public static boolean satisfy(Formula a) {
		boolean result = false;
		HashMap<Atom, Boolean> interpretation = new HashMap<Atom, Boolean>();
		
		Set<Atom> setOfAtom = new HashSet<Atom>();
		for(Literal l : a.getLiterals()) {
			setOfAtom.add(l.getAtom());
		}
		
		int remmant;
		for(int i = 0; i < Math.pow(2, setOfAtom.size()); i++) {
			remmant = i;
			for(Atom l : setOfAtom) {
				if(i == 0) {
					interpretation.put(l, false);
					continue;
				}
					
				if(remmant % 2 == 0)
					interpretation.put(l, false);
				else
					interpretation.put(l, true);
				
				remmant = remmant / 2; 
				if(remmant == 0) break;
			}
			
			if(verify(a, interpretation)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	private static boolean verify(Formula x, HashMap<Atom, Boolean> i) {
		
		 Iterator it = i.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
//		        System.out.print(pairs.getKey() + " = " + pairs.getValue() + " ");
//		        it.remove(); // avoids a ConcurrentModificationException
		    }
//		ArrayList<Literal> literalOfX = new ArrayList<Literal>(x.getLiterals());
		Set<Atom> setOfAtom = new HashSet<Atom>();
		for(Literal l : x.getLiterals()) {
			setOfAtom.add(l.getAtom());
		}
		
		for(Atom l : setOfAtom){
			l.setTruthValue(i.get(l));
		}
		
//		System.out.println(x.getTruthValue() + " | ");
		return x.getTruthValue();
	}
	
	public static void main(String[] args) {
		Atom p = new Atom("p");
		Atom q = new Atom("q");
		Implication ifPthenQ = new Implication(p, q);
		Negation notIfPthenQ = new Negation(ifPthenQ);
		Negation notP = new Negation(p);
		ArrayList<Formula> abc = new ArrayList<Formula>();
		ArrayList<Formula> def = new ArrayList<Formula>();
		def.add(notP);
		def.add(notIfPthenQ);
		def.add(q);
		Disjunction notPandifPthenQ = new Disjunction(def);
		abc.add(p);
		abc.add(q);
		abc.add(notPandifPthenQ);
		Conjunction abcCon = new Conjunction(abc);
		
		Formula x = abcCon.removeTopBottom().reducedConnectives().nnfAfterReduce();
		while(!x.isCNF()){
			System.out.println(x.toString());
			x = x.distributeDisjunction();
		}
		
		boolean check2 = compare(abcCon, x);
		
		System.out.println(x);
		System.out.println(check2);
		
		System.out.println(satisfy(new Conjunction(p, notP)));
		
		ArrayList<ArrayList<Literal>> literals = Literal.CNFFromConjunction((Conjunction) x);
		
		String result[] = HumanReadableCNF.humanReadableCNFConverter(literals, "realtest");
		
		System.out.println(result[0] + "\n\n" + result[1] + "\n\n" + result[2]);
	}
}
