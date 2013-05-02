package parse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

/**
 * Bottom.java
 * Purpose: Randomly creates propositional formulae according to the following JSON format.
 *
 * { "id": "http://unibz.it/cl-2013/propositional-formula#", "description":
 * "Representation of a propositional logic formula", "type": "object",
 * "properties": { "type": { "enum": [ "propositionalFormula" ], "required":
 * true }, "expression": { "type": "object", "oneOf": [ { "$ref":
 * "#/definitions/connective" }, { "$ref": "#/definitions/atom" } ], "required":
 * true } }, "additionalProperties": false, "definitions": { "atom": {
 * "properties": { "type": { "enum": [ "atom" ], "required": true }, "name": {
 * "type": "string", "required": true }, "params": { "type": "array", "items": {
 * "type": "string" }, "required": false } }, "additionalProperties": false },
 * "connective": { "properties": { "type": { "enum": [ "connective" ],
 * "required": true }, "symbol": { "enum": [ "|", "&", "!", "->", "<-", "<->",
 * "ite", "1", "0" ], "required": true }, "params": { "type": "array", "items":
 * { "type": "object", "oneOf": [ { "$ref": "#/definitions/connective" }, {
 * "$ref": "#/definitions/atom" } ] }, "required": false } },
 * "additionalProperties": false } } }
 *
 * @author Andreas Fellner, Radityo Eko Prasojo, Amr Hany Shehata Saleh
 * @version 1.0 17.04.2013
 */


public class RandomJSON
{

	static boolean debug = false;

	/**
	 * This method returns a random generated formula in which follows the above
	 * JSON schema. It uses connectives many connectives and the variables are
	 * in the range from 0 to variables
	 */
	public static String testInstance( int connectives, int variables ,  boolean noFirstTrivialConnective )
	{
		StringBuffer bufferFormula = new StringBuffer();
		bufferFormula.append( "{\n" );
		bufferFormula.append( "\"type\":\"propositionalFormula\", \n" );
		bufferFormula.append( "\"expression\":\n" );
		bufferFormula.append( generateConnective( connectives, variables , noFirstTrivialConnective) );
		bufferFormula.append( "}" );
		return bufferFormula.toString();
	}

	/**
	 * This method returns a a file containing
	 * random generated formula in which follows the above
	 * JSON schema. It uses connectives many connectives and the variables are
	 * in the range from 0 to variables
	 */
	public static void testInstance( int connectives, int variables ,  boolean noFirstTrivialConnective , String fileName )
	{
		try
		{
			BufferedWriter writer;
			writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( new File( fileName ) ) ) );
			StringBuffer bufferFormula = new StringBuffer();
			bufferFormula.append( "{\n" );
			bufferFormula.append( "\"type\":\"propositionalFormula\", \n" );
			bufferFormula.append( "\"expression\":\n" );
			bufferFormula.append( generateConnective( connectives, variables, noFirstTrivialConnective ) );
			bufferFormula.append( "}" );
			writer.write( bufferFormula.toString() );
			writer.flush();
			writer.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Randomly generates a formula in the JSON format written above.
	 * 
	 * @param connectives number of maximal connectives
	 * @param variables range of variable names
	 * @param noFirstTrivialConnective if true then the root of the formula is not an atom, top or bottom.
	 * @return String denoting the produced formula in the JSON format written above.
	 */
	public static String generateConnective( int connectives, int variables,  boolean noFirstTrivialConnective )
	{
		StringBuffer bufferFormula = new StringBuffer();
		bufferFormula.append( "{\n" );
		Random generator = new Random();
		int con = generator.nextInt( 9 );
		int random;
		int arity = 0;
		String symbol = "";
		if (noFirstTrivialConnective) con = 0;
		if( con < 7 && connectives > 0 )
		{
			bufferFormula.append( "\"type\":\"connective\", \n" );
			/*
			 * randomly choose the connective 0 - or 1 - and 2 - not 3 -
			 * implication 4 - reverse implication 5 - double implication 6 -
			 * ite 7 - true 8 - false
			 */
			random = generator.nextInt( 9 );
			while (noFirstTrivialConnective && random > 6) random = generator.nextInt( 9 );
			if( debug )
				System.out.println( "RANDOM: " + random );
			switch( random )
			{
				case 0:
					symbol = "|";
					arity = 2;
					break;
				case 1:
					symbol = "&";
					arity = 2;
					break;
				case 2:
					symbol = "!";
					arity = 1;
					break;
				case 3:
					symbol = "->";
					arity = 2;
					break;
				case 4:
					symbol = "<-";
					arity = 2;
					break;
				case 5:
					symbol = "->";
					arity = 2;
					break;
				case 6:
					symbol = "ite";
					arity = 3;
					break;
				case 7:
					symbol = "1";
					arity = 0;
					break;
				case 8:
					symbol = "0";
					arity = 0;
					break;
			}
			bufferFormula.append( "\"symbol\": \"" + symbol + "\" " );

			// Connective is not true of false
			if( random < 7 )
			{
				bufferFormula.append( "\n,\n" );
				if( debug )
					bufferFormula.append( "\n1st\n" );
				bufferFormula.append( "\"params\":[ \n" );
			}
			for( int i = 0; i < arity; i++ )
			{
				connectives--;
				bufferFormula.append( generateConnective( connectives, variables , false) );
				if( i < arity - 1 )
				{
					bufferFormula.append( "\n,\n" );
					if( debug )
						bufferFormula.append( "\n2nd\n" );
				}
			}
			if( random < 7 )
			{
				bufferFormula.append( "]" );
			}
		}
		else
		{
			bufferFormula.append( "\"type\":\"atom\", \n" );
			random = generator.nextInt( variables );
			if( random >= 0 )
			{
				random++;
			}
			bufferFormula.append( "\"name\": \" " + random + " \" \n" );
		}
		bufferFormula.append( "}" );
		return bufferFormula.toString();
	}
}
