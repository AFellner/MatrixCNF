package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import formula.*;
import other.*;
import parse.*;

public class MainFrame
{

	private JFrame frame;
	JCheckBox chckbxMatrixCnf;
	JCheckBox chckbxDefinitionalCnf;
	JCheckBox chckbxNaiveCnf;
	FileChooserDemo fileChooserDemo;

	/**
	 * Launch the application.
	 */
	public static void main( String[] args )
	{
		EventQueue.invokeLater( new Runnable()
		{
			public void run()
			{
				try
				{
					MainFrame window = new MainFrame();
					window.frame.setVisible( true );
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
			}
		} );
	}

	/**
	 * Create the application.
	 */
	public MainFrame()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout( new BorderLayout( 0, 0 ) );
		frame.setBounds(100,100,433,170);
		frame.setResizable( false);
		frame.setTitle( "Team Awesome CNF converter ;-)" );
		
		JPanel panel = new JPanel();
		frame.getContentPane().add( panel, BorderLayout.CENTER );
		panel.setLayout( new GridLayout( 2, 0, 0, 0 ) );

		JPanel panel_2 = new JPanel();
		panel.add( panel_2 );
		panel_2.setLayout( new GridLayout( 0, 2, 0, 0 ) );

		JLabel lblNoFileSelected = new JLabel( "                No File Selected" );
		lblNoFileSelected.setFont( new Font( "Tahoma", Font.PLAIN, 13 ) );
		panel_2.add( lblNoFileSelected );

		JPanel panel_4 = new JPanel();
		panel_2.add( panel_4 );
		panel_4.setLayout( new FlowLayout( FlowLayout.CENTER, 5, 5 ) );

		JLabel label_3 = new JLabel( "                                                       " );
		panel_4.add( label_3 );

		fileChooserDemo = new FileChooserDemo(lblNoFileSelected);
		panel_4.add( fileChooserDemo );

		JPanel panel_1 = new JPanel();
		panel.add( panel_1 );
		panel_1.setLayout( new GridLayout( 0, 2, 0, 0 ) );

		JPanel panel_3 = new JPanel();
		panel_1.add( panel_3 );
		panel_3.setLayout( new GridLayout( 3, 0, 0, 0 ) );

		chckbxMatrixCnf = new JCheckBox( "Matrix CNF" );
		panel_3.add( chckbxMatrixCnf );

		chckbxDefinitionalCnf = new JCheckBox( "Definitional CNF" );
		panel_3.add( chckbxDefinitionalCnf );

		chckbxNaiveCnf = new JCheckBox( "Naive CNF" );
		panel_3.add( chckbxNaiveCnf );

		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
		panel_1.add( panel_5 );

		JLabel label_2 = new JLabel( "                                                       " );
		panel_5.add( label_2 );

		JButton btnConvertTon = new JButton( "Convert to CNF" );
		btnConvertTon.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
			{
				if(fileChooserDemo.choosenFile != null)
				{
//					JOptionPane.showMessageDialog( frame, fileChooserDemo.choosenFile.getAbsolutePath() );
					if(!chckbxNaiveCnf.isSelected() && !chckbxDefinitionalCnf.isSelected() && !chckbxMatrixCnf.isSelected())
					{
						JOptionPane.showMessageDialog(frame,"Please select at lease one conversion technique");
					}
					else
					{
						Formula formula = Parser.parse( fileChooserDemo.choosenFile.getAbsolutePath() );
						if(chckbxMatrixCnf.isSelected())
						{
							Formula fMat = formula.removeTopBottom().reducedConnectives().nnf().matrixCNF();
							if(fMat instanceof Conjunction)
							{
								ArrayList<ArrayList <Literal> > lits = new ArrayList<ArrayList<Literal> >();
								for(Formula f: ((Conjunction) fMat).getFormulas())
								{
									ArrayList<Literal> literas =  f.getLiterals();
									lits.add( literas );	
								}
								HumanReadableCNF.humanReadableCNFConverter( lits, fileChooserDemo.choosenFile.getPath()+" MatrixCNF  " );
							}
							else
							{
								JOptionPane.showMessageDialog(frame,"Matrix CNF evaluated the formula to \n" + fMat + "\n No files were created for this Conversion");
							}
						}
						if(chckbxDefinitionalCnf.isSelected())
						{
							Formula fDef = formula.toDefinitionalCNF();
							if(fDef instanceof Conjunction)
							{
								ArrayList<ArrayList <Literal> > lits = new ArrayList<ArrayList<Literal> >();
								for(Formula f: ((Conjunction) fDef).getFormulas())
								{
									ArrayList<Literal> literas =  f.getLiterals();
									lits.add( literas );	
								}
								HumanReadableCNF.humanReadableCNFConverter( lits, fileChooserDemo.choosenFile.getPath()+" DefinitionalCNF  " );
							}
							else
							{
								JOptionPane.showMessageDialog(frame,"Defintional CNF evaluated the formula to \n" + fDef + "\n No files were created for this Conversion");
							}
						}
						if(chckbxNaiveCnf.isSelected())
						{
							Formula fNNf = formula.removeTopBottom().reducedConnectives().nnf();
							Formula fNaive = fNNf.toNaiveCNF( fNNf );
							if(fNaive instanceof Conjunction)
							{
								ArrayList<ArrayList <Literal> > lits = new ArrayList<ArrayList<Literal> >();
								for(Formula f: ((Conjunction) fNaive).getFormulas())
								{
									ArrayList<Literal> literas =  f.getLiterals();
									lits.add( literas );	
								}
								HumanReadableCNF.humanReadableCNFConverter( lits, fileChooserDemo.choosenFile.getPath()+" NaiveCNF  " );
							}
							else
							{
								JOptionPane.showMessageDialog(frame,"Naive CNF evaluated the formula to \n" + fNaive + "\n No files were created for this Conversion");
							}
						}
					}
					
					JOptionPane.showMessageDialog(frame,"Files Converted, check the file directory for Ouput files");
				}
				else
				{
					JOptionPane.showMessageDialog( frame, "Please Select a file" );
				}
			}
		} );
		panel_5.add( btnConvertTon );
	}

}
