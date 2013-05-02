package parse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import formula.Atom;
import formula.Bottom;
import formula.Conjunction;
import formula.Disjunction;
import formula.IfThenElse;
import formula.Implication;
import formula.Negation;
import formula.Formula;
import formula.Top;


public class Parser
{
	/**
	 * Parse method, takes a file name written in JSON format. Then it
	 * Return a PropositionalFormula object which can be used for further
	 * operations.
	 * @param fileName
	 * @return PropositionalFormula
	 */
	public static Formula parse(String fileName)
	{
		JSONParser parser = new JSONParser();
		try
		{
			Object mainObj = parser.parse( new FileReader(fileName) );
			JSONObject mainJSON = (JSONObject) mainObj;
			String propositionalFormulaExpr = (String) mainJSON.get( "type" );
			
			if(!propositionalFormulaExpr.equals( "propositionalFormula" ))
				throw new Exception("Error in file, Check propositional formula");
			
			JSONObject mainFormula = (JSONObject) mainJSON.get( "expression" );
			return makeFormula(mainFormula,new ArrayList<Atom>());
	
			 
		}
		catch( Exception e )
		{	
			e.printStackTrace();
			System.exit( 0 );
		}
		return null;
		
	}
	/**
	 * A recursive method that takes a JSON Object which presents the formula,
	 * then uses case analysis in order to know the formula type from the formula
	 * symbol.
	 * Afterwards, it does recursive calls on the JSON Object in order to retract the
	 * information of the subFormulae.
	 * 
	 * @param A JSON Object
	 * @param An array list of unique atoms as this method returns the same atom if
	 * it appeared before in the formula
	 * @return PropositionalFormula
	 */
	public static Formula makeFormula(JSONObject formulaObject, ArrayList<Atom> uniqueAtoms) throws Exception
	{
		String formulaType = (String) formulaObject.get( "type" );
		if(formulaType.equals( "atom" ))
		{
			String atomName = (String) formulaObject.get( "name" );
			JSONArray params = (JSONArray) formulaObject.get( "params" );
			if(params!=null)
			{
				atomName+="(";
				Iterator<String> iterator = params.iterator();
				atomName+=iterator.next();
				
				while( iterator.hasNext() )
				{
					atomName+=","+iterator.next();
				}
				atomName+=")";
			}
			
			Atom atom = new Atom(atomName);
			for(Atom a: uniqueAtoms)
			{
				if(a.equals( atom ))
				{
					return a;
				}
			}
			uniqueAtoms.add( atom );
			return atom;
			
			
		}
		else
		{
			String symbol = (String) formulaObject.get( "symbol" );
			if(symbol.equals( "0" ))
			{
				return new Bottom();
			}
			if(symbol.equals( "1" ))
			{
				return new Top();
			}
			if(symbol.equals( "!" ))
			{
				JSONArray params = (JSONArray) formulaObject.get( "params" );
				Iterator<JSONObject> iterator = params.iterator();
				return new Negation( makeFormula(iterator.next() , uniqueAtoms) );
			}
			if(symbol.equals( "->" ))
			{
				JSONArray params = (JSONArray) formulaObject.get( "params" );
				Iterator<JSONObject> iterator = params.iterator();
				return new Implication( makeFormula(iterator.next(), uniqueAtoms) ,makeFormula(iterator.next(), uniqueAtoms));
			}
			
			if(symbol.equals( "<-" ))
			{
				JSONArray params = (JSONArray) formulaObject.get( "params" );
				Iterator<JSONObject> iterator = params.iterator();
				JSONObject formula2 = iterator.next();
				JSONObject formula1 = iterator.next();
				return new Implication( makeFormula(formula1, uniqueAtoms) ,makeFormula(formula2, uniqueAtoms));
			}
			if(symbol.equals( "<->" ))
			{
				JSONArray params = (JSONArray) formulaObject.get( "params" );
				Iterator<JSONObject> iterator = params.iterator();
				return new Implication( makeFormula(iterator.next(), uniqueAtoms) ,makeFormula(iterator.next(), uniqueAtoms));
			}
			
			if(symbol.equals( "ite" ))
			{
				JSONArray params = (JSONArray) formulaObject.get( "params" );
				Iterator<JSONObject> iterator = params.iterator();
				JSONObject formula1 = iterator.next();
				JSONObject formula2 = iterator.next();
				if(iterator.hasNext())
				{
					JSONObject formula3 = iterator.next();
					return new IfThenElse( makeFormula( formula1 , uniqueAtoms) , makeFormula( formula2 , uniqueAtoms), makeFormula( formula3 , uniqueAtoms) );
				}
				else
				{
					return new IfThenElse( makeFormula( formula1 , uniqueAtoms) , makeFormula( formula2 , uniqueAtoms), null );
				}
			}
			
			if(symbol.equals( "&" ))
			{
				JSONArray params = (JSONArray) formulaObject.get( "params" );
				Iterator<JSONObject> iterator = params.iterator();
				ArrayList<Formula> formulas = new ArrayList<Formula>();
				while( iterator.hasNext() )
				{
					formulas.add( makeFormula(iterator.next(), uniqueAtoms) );
				}
				if(formulas.size()<2)
					throw new Exception("Conjunction with less than 2 formulas");
				else
					return new Conjunction( formulas );
			}
			
			if(symbol.equals( "|" ))
			{
				JSONArray params = (JSONArray) formulaObject.get( "params" );
				Iterator<JSONObject> iterator = params.iterator();
				ArrayList<Formula> formulas = new ArrayList<Formula>();
				while( iterator.hasNext() )
				{
					formulas.add( makeFormula(iterator.next(), uniqueAtoms) );
				}
				if(formulas.size()<2)
					throw new Exception("Disjunction with less than 2 formulas");
				else
					return new Disjunction( formulas );
			}
		}
		return new Atom( "LALA" );
	}
	
	public static void main(String [] args)
	{
		System.out.print( parse("case1") );
	}

}