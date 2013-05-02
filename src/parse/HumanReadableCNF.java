package parse;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import nl.flotsam.xeger.Xeger;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;


import com.google.common.collect.HashBiMap;

import formula.Atom;
import formula.Literal;


public class HumanReadableCNF {

	public static String[] humanReadableCNFConverter(ArrayList<ArrayList<Literal>> array, String fileName) {
		StringBuffer res = new StringBuffer();
		StringBuffer latex = new StringBuffer();
		StringBuffer dimacs = new StringBuffer();
		StringBuffer totalOrder = new StringBuffer();
		
		HashBiMap<String, Integer> totalOrderMap = HashBiMap.create();
		int dimacsInt = 1;
		
		ArrayList<Literal> temp;
		String atomName;
		latex.append("$");
		for(int i = 0; i < array.size(); i++) {
			temp = new ArrayList<Literal>(array.get(i));
			
			res.append("(");
			latex.append("(");
			for(int j = 0; j < temp.size(); j++) {
				atomName = temp.get(j).getAtom().getAtomName();
				res.append(temp.get(j).negated()? "~" : "");
				res.append(atomName);
				
				latex.append(temp.get(j).negated()? " \\neg " : "");
				latex.append(atomName);
				
				if(!totalOrderMap.containsKey(atomName)) {
					totalOrderMap.put(atomName, dimacsInt);
					totalOrder.append(dimacsInt + " " + atomName + "\n");
					dimacsInt++;
				}
				
				dimacs.append(temp.get(j).negated()? "-" : "");
				dimacs.append(totalOrderMap.get(atomName));
				
				if(j < temp.size() - 1) {
					res.append(" | ");
					latex.append(" \\vee ");
					dimacs.append(" ");
				}
			}
			
			res.append(")");
			latex.append(")");
			dimacs.append(" 0");
			
			if(i < array.size() - 1) {
				res.append(" &\n");
				latex.append("\\; \\wedge \\\\");
				dimacs.append("\n");
			}
		}
		latex.append("$");
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName+"-readable.txt")));
			writer.write(res.toString());
			writer.flush();
			
			writer = new BufferedWriter(new FileWriter(new File(fileName+"-dimacs.txt")));
			writer.write(dimacs.toString());
			writer.flush();
			
			writer = new BufferedWriter(new FileWriter(new File(fileName+"-totalorder.txt")));
			writer.write(totalOrder.toString());
			writer.flush();
			
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		renderCNFInLatex(latex.toString(), fileName);
		
		return new String[]{res.toString(), dimacs.toString(), totalOrder.toString()};
	}
	
	private static void renderCNFInLatex(String latex, String imgFileName) {
		try {
			
			// create a formula
			TeXFormula formula = new TeXFormula(latex);
			
			// render the formla to an icon of the same size as the formula.
			TeXIcon icon = formula
					.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
			
			// insert a border 
			icon.setInsets(new Insets(5, 5, 5, 5));

			// now create an actual image of the rendered equation
			BufferedImage image = new BufferedImage(icon.getIconWidth(),
					icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = image.createGraphics();
			g2.setColor(Color.white);
			g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
			JLabel jl = new JLabel();
			jl.setForeground(new Color(0, 0, 0));
			icon.paintIcon(jl, g2, 0, 0);
			// at this point the image is created, you could also save it with ImageIO
			
			ImageIO.write(image, "png", new File(imgFileName + "-latex.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static ArrayList<ArrayList<Literal>> randomizeCNF(int maxLiteralNumberPerClause,
															 int clauseNumber) {
		ArrayList<ArrayList<Literal>> res = new ArrayList<ArrayList<Literal>>(clauseNumber);
		
		for(int i = 0; i < clauseNumber; i++) {
			ArrayList<Literal> temp = new ArrayList<Literal>();
			int maxLiteral = (int) Math.ceil(Math.random()*maxLiteralNumberPerClause);
			
			if(maxLiteral == 0) {
				temp.add(new Literal(new Atom("Bottom"), false));
				continue;
			}
			
			String regex = "[p-r]{1}[1-9]";
			Xeger generator = new Xeger(regex);
			for(int j = 0; j < maxLiteral; j++) {
				boolean negated = (Math.random() < 0.5);
				String literal = generator.generate();
				assert literal.matches(regex);
				temp.add(new Literal(new Atom(literal), negated));
			}
			
			res.add(new ArrayList<Literal>(temp));
			temp.clear();
		}
		
		return res;
	}
	
	public static String dimacsConverter() {
		StringBuffer res = new StringBuffer();
		
		return res.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<ArrayList<Literal>> theCNF = new ArrayList<ArrayList<Literal>>(randomizeCNF(5,10));
		String result[] = humanReadableCNFConverter(theCNF, "test1");
		
		System.out.println(result[0] + "\n\n" + result[1] + "\n\n" + result[2]);
		
	}

}
