 import javax.swing.*;
 import java.io.*;
 import java.awt.*;
 import java.awt.event.*;
 import java.util.*;
 import javax.swing.event.*;
 import javax.swing.colorchooser.*;
 import javax.swing.text.*;
 import java.awt.print.*;// for printing interfaces
 import java.applet.*;// used for static getAudioClip()
 import java.net.*;	  // used to get URL
 //import javax.sound.sampled.*;// to be used later, not there in JDK1.2
 							  // use JDK 1.3 to compile





 /**
 *@Programmed by Khurram Shahzad.
 *@Bahria University MCS-1 Project for OOPS.
 *@Instructor Mr Ghulam Nabi.
 *@Relese 1.2.0.
 *copy right@2001
 *developed using TextPad
 *********************************************************************
 *This text editor named Kpad will be developed in stages            *
 *the first stage will be a simple text editor with resemblance      *
 *of MS Notepad. It will be then enhanced in next version for        *
 *advanced facilities like TextPad **********************************/



 public class Kpad extends JFrame
 {

  /*********** menubar, menus and menu items***************************/

  JMenuBar menubar;  // the menu bar

  JMenu fileMenu,editMenu,searchMenu,toolsMenu,helpMenu,codeMenu,decodeMenu,cryptoAnalysisMenu;// menus
  JPopupMenu popChoice;


  JMenuItem newMenuItem,openMenuItem,saveMenuItem,openDecodedMenuItem,saveCodedMenuItem,
            saveAsMenuItem,printMenuItem,exitMenuItem;// items of file menu
  JMenuItem cutMenuItem,copyMenuItem,pasteMenuItem,
            undoMenuItem,selectAllMenuItem,setFontMenuItem;//items of edit menu
  JMenuItem findMenuItem,helpMenuItem;//items of search and help menus

  JMenuItem compileMenuItem;// item for tools menu

  JMenuItem codeCaeser,decodeCaeser,codeSubstitution,decodeSubstitution,codeWoodenBlock,decodeWoodenBlock;
  JMenuItem codeMonoSubstitution,deocdeMonoSubstitution,codeVigenere,decodeVigenere;
  JMenuItem analyseCeaser;
  JMenuItem closeFileInList;
  /*********************************************************************/
 	TextBox textBox; // this will display text of selected open file.

 	JList     fileList; // this will display all open files
 	                    // one of which can be selected to be displayed in
 	                    // the textBox.
 	boolean isSelected = false;// a flag for determining if there is a selection in the list.
    JSplitPane splitPane; // a aplit window to add textBox and fileList.

    JToolBar   toolBar;   // a toolbar for commonly used menu items.

    JButton saveButton,printButton,newDocumentButton,fileOpenButton,
            cutButton,copyButton,pasteButton,playMusicButton;//undoButton;

   Vector openedFiles; // a vector to hold all opened files

   String selectedText = null; // for cut paste utility

   AudioClip clip;   // for playing sound files.
   URL currentDirURL;


   ImageIcon icon; // window icon
   Image image;


   JLabel statusBar;

   String currentFileName = " ";

   JFileChooser fc = new JFileChooser();
 /*************************************************************************/

 boolean textChanged = false; // records weather the text is changed or not.

 /*************************************************************************/


  public Kpad()  // The Constructor.
  {
	super("Kpad");
	this.setDefaultCloseOperation(1);
	System.out.println("run");

	/************************* setting up sound system*******************/
	File currentDir = new File(System.getProperty("user.dir"));
		try
		{
			  currentDirURL = currentDir.toURL();
		}
		catch(MalformedURLException e)
		{
			System.err.println("bad URL"+ e);
			System.exit(0);
		}

  String soundFile = "TADA.WAV";


try
	{

	    clip = Applet.newAudioClip(new URL(currentDirURL,soundFile));
	}
	catch(MalformedURLException e)
	{
		System.err.println("bad URL"+ e);
		System.exit(1);
	}

	clip.play();
	//clip = null;
	//System.gc();

    statusBar = new JLabel("Kpad");
  /***********************************sound system is set****************/

  /***********************************creating custom icon***************/

    icon = new ImageIcon("writting11.gif");
    image = icon.getImage();
    this.setIconImage(image);
  /**********************************************************************/
	 //creating and pupulating the menus

	 //final JFileChooser fc = new JFileChooser();
	       Filter filter = new Filter();
	       //filter.addExtention("txt");
	       filter.addExtention("java");
	       //filter.addExtention("doc");
	       //filter.addExtention("class");
	     //fc.setFileFilter(filter);

	 final Encripter encripter = new Encripter();

	 fileMenu = new JMenu("File");
	 fileMenu.setMnemonic(KeyEvent.VK_F);
	 editMenu = new JMenu("Edit");
	 editMenu.setMnemonic(KeyEvent.VK_E);
	 searchMenu = new JMenu("Search");
	 searchMenu.setMnemonic(KeyEvent.VK_S);
	 helpMenu = new JMenu("Help");
	 helpMenu.setMnemonic(KeyEvent.VK_H);
	toolsMenu = new JMenu("tools");
	 toolsMenu.setMnemonic(KeyEvent.VK_T);
	 codeMenu = new JMenu("Save coded");
	 decodeMenu = new JMenu("Open decoded");
	 cryptoAnalysisMenu = new JMenu("Crypto Analysis");
	 cryptoAnalysisMenu.setMnemonic(KeyEvent.VK_A);

	 // menuItems for file menu

	 newMenuItem = new JMenuItem("New",KeyEvent.VK_N);
	 newMenuItem.setAccelerator(KeyStroke.getKeyStroke(
	         KeyEvent.VK_1, ActionEvent.ALT_MASK));
	 newMenuItem.getAccessibleContext().setAccessibleDescription(
	         "This doesn't really do anything");

	 openMenuItem = new JMenuItem("Open",KeyEvent.VK_O);
	 saveMenuItem = new JMenuItem("Save",KeyEvent.VK_F12);
	 saveAsMenuItem = new JMenuItem("Save as",KeyEvent.VK_F12);
	 openDecodedMenuItem = new JMenuItem("Open decoded",KeyEvent.VK_F3);
	 saveCodedMenuItem = new JMenuItem("Save Coded",KeyEvent.VK_F2);
	 printMenuItem = new JMenuItem("Print",KeyEvent.VK_P);
	 exitMenuItem = new JMenuItem("Exit",KeyEvent.VK_X);
	 codeCaeser = new JMenuItem("code Caeser");
	 decodeCaeser = new JMenuItem("decode Caeser");
	 codeSubstitution = new JMenuItem("code Substitution");
	 decodeSubstitution = new JMenuItem("decode Substitution");
	 codeWoodenBlock = new JMenuItem("code Wooden Block Cipher");
	 decodeWoodenBlock = new JMenuItem("decode Wooden Block Cipher");
	 codeVigenere = new JMenuItem("code Vigenere Cipher");
	 decodeVigenere = new JMenuItem("decode Vigenere Cipher");
	 codeMonoSubstitution = new JMenuItem("code MonoSubstitution");
	 deocdeMonoSubstitution = new JMenuItem("decode MonoSubstitution");
	 analyseCeaser = new JMenuItem("Analyse Ceaser");


	 fileMenu.add(newMenuItem);
	 fileMenu.add(openMenuItem);
	 fileMenu.add(saveMenuItem);
	 fileMenu.add(saveAsMenuItem);
	 fileMenu.add(codeMenu);
	 fileMenu.add(decodeMenu);
	 fileMenu.add(printMenuItem);
	 fileMenu.add(exitMenuItem);

	 codeMenu.add(codeCaeser);
	 codeMenu.add(codeSubstitution);
	 codeMenu.add(codeWoodenBlock);
	 decodeMenu.add(decodeCaeser);
	 decodeMenu.add(decodeSubstitution);
	 decodeMenu.add(decodeWoodenBlock);
	 codeMenu.add(codeVigenere);
	 decodeMenu.add(decodeVigenere);
	 codeMenu.add(codeMonoSubstitution);
	 decodeMenu.add(deocdeMonoSubstitution);
	 cryptoAnalysisMenu.add(analyseCeaser);

	 analyseCeaser.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			 //doVigenereCipher();
			 doCeaserAnalysis();
		 }
	 });



	 codeVigenere.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			 doVigenereCipher();
		 }
	 });

	 decodeVigenere.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			doVigenereDecipher();
		 }
	 });

	 codeMonoSubstitution.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			doMonoSubstitutionCipher();
		 }
	 });

	 deocdeMonoSubstitution.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			doMonoSubstitutionDecipher();
		 }
	 });

	 codeWoodenBlock.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			 File saveFile;
			 int returnVal = fc.showSaveDialog(Kpad.this);
			 if(returnVal == JFileChooser.APPROVE_OPTION)
			 {
			  saveFile = fc.getSelectedFile();
			  WoodenBlockCipher enc = new WoodenBlockCipher();
			  String message = textBox.getText();
			  String coded = enc.encript(message);
			  try
			  {
			    enc.writeBytes(coded,saveFile);
			  }
			  catch(FileNotFoundException fe)
			  {
			    JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
			  }
			  catch(IOException ioe)
			  {
			  	JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
	   		  }
			 }// end if
			 String soundFile = "Chimes.wav";
			 try
			 {
			   AudioClip clip = Applet.newAudioClip(new URL(currentDirURL,soundFile));
			   clip.play();
			 }
			 catch(MalformedURLException ex)
			 {
			 	System.err.println("bad URL"+ ex);
			 	//System.exit(1);
		    }
		 }
	 }
	 );

	 decodeWoodenBlock.addActionListener(new ActionListener()
	 {
	 	public void actionPerformed(ActionEvent e)
		{
		File file = null;
		String s = null;
			int returnVal = fc.showOpenDialog(Kpad.this);
		    if (returnVal == JFileChooser.APPROVE_OPTION)
			 {
			   file = fc.getSelectedFile();
			   openedFiles.add(file);
			   fileList.setListData(openedFiles);
			   WoodenBlockCipher enc = new WoodenBlockCipher();
			   try{s = enc.readBytes(file);}
			   catch(FileNotFoundException fe){}
			   catch(IOException ioe){}
			   String message = enc.decript(s);
			   textBox.setText(message);
			}
			String soundFile = "Chimes.wav";
						 try
						 {
						   AudioClip clip = Applet.newAudioClip(new URL(currentDirURL,soundFile));
						   clip.play();
						 }
						 catch(MalformedURLException ex)
						 {
						 	System.err.println("bad URL"+ ex);
						 	//System.exit(1);
		    }
		}
	}
	);

	 codeCaeser.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			 File saveFile = null;
			 String key =  JOptionPane.showInputDialog("Enter one alphabet key please");
			 if(key.length()>1)
			 {
			 JOptionPane.showMessageDialog(null,"The key must be one alphabet only","UNAUTHORISED KEY",JOptionPane.PLAIN_MESSAGE);
			 return;
		     }
			 if( !(Character.isUpperCase(key.charAt(0))||Character.isLowerCase(key.charAt(0))) )
			 {
			 JOptionPane.showMessageDialog(null,"The key must be an alphabet only","UNAUTHORISED KEY",JOptionPane.PLAIN_MESSAGE);
			 return;
		 	 }
			 int returnVal = fc.showSaveDialog(Kpad.this);
			  if(returnVal == JFileChooser.APPROVE_OPTION)
			  {
		   	   saveFile = fc.getSelectedFile();
		   	   Encriptor enc = new Encriptor(key.charAt(0));
		   	   String message = textBox.getText();
		   	   String coded = enc.codeCaeser(message);
			   try
			   {
				   enc.writeBytes(coded,saveFile);
			   }
			   catch(FileNotFoundException fe)
			   {
				   JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
			   }
			   catch(IOException ioe)
			   {
			    	JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
			   }
		     }


		 }
	 }
	 );


	 codeSubstitution.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			 File saveFile;
			 String key =  JOptionPane.showInputDialog("Enter 26 alphabets key please The alphabets must not be repeated");
			 if(key.length()>26||key.length()<26)
			 {
			 JOptionPane.showMessageDialog(null,"The Key must be 26 letters","Error",JOptionPane.PLAIN_MESSAGE);
			 return;
		     }
		     if(hasRepeatedCharacters(key))
		     {
			  JOptionPane.showMessageDialog(null,"The Key must not have repeated Characters","Error",JOptionPane.PLAIN_MESSAGE);
			 return;
		     }
		     int returnVal = fc.showSaveDialog(Kpad.this);
			 if(returnVal == JFileChooser.APPROVE_OPTION)
			 {
			 saveFile = fc.getSelectedFile();
			 Encriptor enc = new Encriptor(key);
			 String message = textBox.getText();
			 String coded = enc.codeSubstitutionCipher(message);
			 try
			 {
			   enc.writeBytes(coded,saveFile);
			 }
			 catch(FileNotFoundException fe)
			 {
			    JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
		     }
		     catch(IOException ioe)
			 {
			 	JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
			 }
		    }
		    else
		    {}


		 }
	 }
	 );


	 decodeCaeser.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			 File file;
			 String s = null;
			 String key =  JOptionPane.showInputDialog("Enter one alphabet key please");
			 if(key.length()>1)
			 {
			 JOptionPane.showMessageDialog(null,"The key must be one alphabet only","UNAUTHORISED KEY",JOptionPane.PLAIN_MESSAGE);
			 return;
			 }
			 if( !(Character.isUpperCase(key.charAt(0))||Character.isLowerCase(key.charAt(0))) )
			 {
			 JOptionPane.showMessageDialog(null,"The key must be an alphabet only","UNAUTHORISED KEY",JOptionPane.PLAIN_MESSAGE);
			 return;
		 	 }
		 	 int returnVal = fc.showOpenDialog(Kpad.this);
			 if (returnVal == JFileChooser.APPROVE_OPTION)
			 {
			  file = fc.getSelectedFile();
			  openedFiles.add(file);
		      fileList.setListData(openedFiles);
		      Encriptor enc2 = new Encriptor(key.charAt(0));
		      try{s = enc2.readBytes(file);}
		      catch(FileNotFoundException fe){}
		      catch(IOException ioe){}
		      String message = enc2.decodeCaeser(s);
		      textBox.setText(message);
		     }

		 }
	 }
	 );

	 decodeSubstitution.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {


			 File file;
			 String s = null;
			 String key =  JOptionPane.showInputDialog("Enter 26 alphabets key please");
			 if(key.length()>26||key.length()<26)
			 {
			 JOptionPane.showMessageDialog(null,"The Key must be 26 letters","Error",JOptionPane.PLAIN_MESSAGE);
			 return;
			 }
			 if(hasRepeatedCharacters(key))
			 {
			  JOptionPane.showMessageDialog(null,"The Key must not have repeated Characters","Error",JOptionPane.PLAIN_MESSAGE);
			 return;
			 }
			 int returnVal = fc.showOpenDialog(Kpad.this);
			 if (returnVal == JFileChooser.APPROVE_OPTION)
			 {
			  file = fc.getSelectedFile();
			  openedFiles.add(file);
			  fileList.setListData(openedFiles);
			  Encriptor enc2 = new Encriptor(key);
			  try{s = enc2.readBytes(file);}
			  catch(FileNotFoundException fe){}
			  catch(IOException ioe){}
			  String message = enc2.decodeSubstitutionCipher(s);
			  textBox.setText(message);
		     }

		 }
	 }
	 );


	 // writing event handlers for menu items

	newMenuItem.addActionListener(new ActionListener()
          {
			  public void actionPerformed(ActionEvent e)
			  {
				  textBox.setText(null);
				  clip.play();
			  }
		  }
		  );




	 openMenuItem.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		{
		 int returnVal = fc.showOpenDialog(Kpad.this);
		 if (returnVal == JFileChooser.APPROVE_OPTION)
		  {
		   File file = fc.getSelectedFile();
		   openedFiles.add(file);
		   fileList.setListData(openedFiles);
		   currentFileName = file.getName();
		   statusBar.setText(currentFileName);
		  FileReader reader = null;
		  try
		  {

		  reader = new FileReader(file);
		 textBox.read(reader,null);
		 }
		catch(IOException ioe)
		  {
		  textBox.append("unable to open file" + file.getName());
		  }
		}// end if
		}// method ends
	}// class ends
  );// addActionListener ends





          saveMenuItem.addActionListener(new ActionListener()
		            {
		                public void actionPerformed(ActionEvent e)
		                {

		   					int returnVal = fc.showSaveDialog(Kpad.this);
		   					if(returnVal == JFileChooser.APPROVE_OPTION)
		   					{
		   					   File saveFile = fc.getSelectedFile();
		   					   FileWriter  writer = null;
		   					     try
		   					     {
		  						   writer = new FileWriter(saveFile);
		  						    textBox.write(writer);
		  						    writer.close();
		  					      }
		  					     catch(IOException ex)
		  					      {
		  					    	textBox.setText(null);
		  						    textBox.append("unable to save file");
		  					       }

		  				    }

		  				    String savesoundFile = "ChatKick.Wav";
							try
								{

								    AudioClip clip = Applet.newAudioClip(new URL(currentDirURL,savesoundFile));
								    clip.play();
								}
								catch(MalformedURLException ex)
								{
									System.err.println("bad URL"+ ex);
									System.exit(1);
								}
		   				}
          });


         openDecodedMenuItem.addActionListener(new ActionListener()
         {
			 public void actionPerformed(ActionEvent ev)
			 {
				 String code =  JOptionPane.showInputDialog("Enter your password please");
				 if(code.equals("fatima")||code.equals("Bahria")||code.equals("khurram"))
				 {
				 int returnVal = fc.showOpenDialog(Kpad.this);
				 	if (returnVal == JFileChooser.APPROVE_OPTION)
				 	 {
				 	  File file = fc.getSelectedFile();
				 	  openedFiles.add(file);
		              fileList.setListData(openedFiles);
		              try
		              {
			       	  DataInputStream in = new DataInputStream(new BufferedInputStream(
				 		  					new FileInputStream(file)));


				 	 String coded = in.readUTF();
				 	 String decoded = encripter.decode(coded);
				 	 textBox.setText(decoded);
				 	 in.close();
				     }
				     catch(IOException e)
				     {
						 textBox.setText("Unable to open file "+ file.getName());
					 }
				 }// end if
			 }
			 else
			 {
			 JOptionPane.showMessageDialog(null,"Sorry you are not authorised for this function","UNAUTHORISED ACCESS",JOptionPane.PLAIN_MESSAGE);
			 System.exit(1);
		     }

			}// end action performed method
		}// end class
		);// end action listener method



		/*******************/

		saveCodedMenuItem.addActionListener(new ActionListener()
		{

		    public void actionPerformed(ActionEvent e)
		    {
			String code =  JOptionPane.showInputDialog("Enter your password please");
			if(code.equals("fatima")||code.equals("Bahria")||code.equals("khurram"))
		    {
			int returnVal = fc.showSaveDialog(Kpad.this);
			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
		   	File saveFile = fc.getSelectedFile();
		   	try
		   	{
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
						  		   new FileOutputStream(saveFile)));
			String tobeCoded = textBox.getText();
			String coded = encripter.code(tobeCoded);
			out.writeUTF(coded);
			out.close();
		    }
		    catch(IOException ex)
		    {}
		    }// end if
		}
		else
		JOptionPane.showMessageDialog(null,"Sorry you are not authorised for this function","UNAUTHORISED ACCESS",JOptionPane.PLAIN_MESSAGE);
		}// end action performed
	}// end class
	);


	exitMenuItem.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent ev)
		{
			//dispose();
			System.exit(0);
		}
	}
	);

    //  menuItems for Edit menu

     cutMenuItem = new JMenuItem("Cut",KeyEvent.VK_C);
     copyMenuItem = new JMenuItem("Copy",KeyEvent.VK_O);
     pasteMenuItem = new JMenuItem("Paste",KeyEvent.VK_P);
     undoMenuItem = new JMenuItem("Undo",KeyEvent.VK_U);
     selectAllMenuItem = new JMenuItem("Select All",KeyEvent.VK_S);
     setFontMenuItem = new JMenuItem("Font",KeyEvent.VK_F);

     // adding them to the edit menu

     editMenu.add(cutMenuItem);
     editMenu.add(copyMenuItem);
	 editMenu.add(pasteMenuItem);
	 editMenu.add(undoMenuItem);
	 editMenu.add(selectAllMenuItem);
	 editMenu.add(setFontMenuItem);


	 // actions for edit menu items


	 cutMenuItem.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			 textBox.cut();

			 String cutsoundFile = "Camera.wav";
			 try
				{

			 	 AudioClip clip = Applet.newAudioClip(new URL(currentDirURL,cutsoundFile));
			 	 clip.play();
			 	}
			 	catch(MalformedURLException ex)
			 	{
			 	System.err.println("bad URL"+ ex);
			 	System.exit(1);
				}
		 }
	 }
	 );

	 copyMenuItem.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			 textBox.copy();
		 }
	 }
	 );


	 pasteMenuItem.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			 textBox.paste();
		 }
	 }
	 );

	 selectAllMenuItem.addActionListener(new ActionListener()
	 {
		 public void actionPerformed(ActionEvent e)
		 {
			 textBox.selectAll();
		 }
	 }
	 );



    setFontMenuItem.addActionListener(new ActionListener()
    {

		public void actionPerformed(ActionEvent e)
		{
			final FontChooser chooser = new FontChooser(Kpad.this);
			boolean first = true;
			chooser.setVisible(true);
			if(chooser.getNewFont() != null)
			{
				textBox.setFont(chooser.getNewFont());
				textBox.setForeground(chooser.getNewColor());
			}
		}
	});

    compileMenuItem = new JMenuItem("compile",KeyEvent.VK_C);
    toolsMenu.add(compileMenuItem);
    compileMenuItem.addActionListener(new ActionListener()
    {
		public void actionPerformed(ActionEvent eve)
		{
				if(currentFileName.endsWith("java"))
				CompilerClass.compile(currentFileName);
				else
				JOptionPane.showMessageDialog(null,"Only files with .jva extentio can be compiled","UNAUTHORISED ACCESS",JOptionPane.PLAIN_MESSAGE);


		}
	});



	//   menu items for the search menu



	 findMenuItem = new JMenuItem("Find",KeyEvent.VK_F);
	 searchMenu.add(findMenuItem);

	 // menu items for the help menu

	 helpMenuItem = new JMenuItem("About",KeyEvent.VK_H);
	 helpMenu.add(helpMenuItem);
	 helpMenuItem.addActionListener(new ActionListener()
	     {

	 		public void actionPerformed(ActionEvent e)
		    {
				JOptionPane.showMessageDialog(null,"This is a simple text editor made using javax.swing package developed by Khurram Shahzad of Bahria University",
				    						 "About Kpad",JOptionPane.INFORMATION_MESSAGE);
		     }
		 });


	 menubar = new JMenuBar();
	 this.setJMenuBar(menubar);
	 menubar.add(fileMenu);
	 menubar.add(editMenu);
	 menubar.add(searchMenu);
	 menubar.add(helpMenu);
	 menubar.add(cryptoAnalysisMenu);

/**********************************************************************************/


   fileList = new JList();
   openedFiles = new Vector();  // vector for holding opened files.

   //fileList = new JList(openedFiles);
   fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   fileList.setSelectedIndex(0);
   fileList.addListSelectionListener(new ListSelectionListener()
   {
	   public void valueChanged(ListSelectionEvent e) {
	       if (e.getValueIsAdjusting())
	           return;

	       JList theList = (JList)e.getSource();
	       if (theList.isSelectionEmpty())
	       {
	           textBox.setText(null);
	       }
	       else
	       {
	           int index = theList.getSelectedIndex();
	           File selectedFile =  (File)openedFiles.elementAt(index);

	           try
	           {
	           FileReader reader2 = new FileReader(selectedFile);
	           textBox.read(reader2,null);
	           currentFileName = selectedFile.getName();
		   	   statusBar.setText(currentFileName);
		       }
		       catch(IOException ev)
		       {
				   textBox.setText(null);
				   textBox.append("unable to open the file + selectedFile");
		       }
	       }
	   }// method ends


   }// class ends
   );// add method ends




   JScrollPane listScrollPane = new JScrollPane(fileList);

   textBox = new TextBox();
   textBox.setMargin(new Insets(5,5,5,5));
   textBox.setFont(new Font("Serif",Font.BOLD+Font.ITALIC,18));
   JScrollPane  textScrollPane = new JScrollPane(textBox,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
   								JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );

   splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                              listScrollPane, textScrollPane);
   splitPane.setOneTouchExpandable(true);
   splitPane.setDividerLocation(100);
   splitPane.setDividerSize(5);

   Dimension minimumSize = new Dimension(35, 100);
   listScrollPane.setMinimumSize(minimumSize);
   textScrollPane.setMinimumSize(minimumSize);

  //this.getContentPane().add(splitPane);
  /********************************************************************************/
  // adding a toolbar and buttons for some common functions
  toolBar = new JToolBar();
  toolBar.setFloatable(true);


   //first button
          saveButton = new JButton(new ImageIcon("images\\save.gif"));
          saveButton.setToolTipText("Save");
          saveButton.addActionListener(new ActionListener()
          {
              public void actionPerformed(ActionEvent e)
              {

 					int returnVal = fc.showSaveDialog(Kpad.this);
 					if(returnVal == JFileChooser.APPROVE_OPTION)
 					{
 					   File saveFile = fc.getSelectedFile();
 					   FileWriter  writer = null;
 					     try
 					     {
						   writer = new FileWriter(saveFile);
						    textBox.write(writer);
						    writer.close();
					      }
					     catch(IOException ex)
					      {
					    	textBox.setText(null);
						    textBox.append("unable to open file");
					       }

				    }
 				}
          });
          toolBar.add(saveButton);

          //toolBar.addSeparator();
          /*playMusicButton = new JButton("Play Music");
          playMusicButton.setToolTipText("Song");
          playMusicButton.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e)
			     {
					 String SongFile = "Song.wav";
					 try
					  {

					 	AudioClip clip = Applet.newAudioClip(new URL(currentDirURL,SongFile));
					 	clip.play();
					  }
					 catch(MalformedURLException ex)
					 {
					 	System.err.println("bad URL"+ ex);

				     }

				 }
			 });*/


          //second button
          printButton = new JButton(new ImageIcon("images\\print.gif"));
          printButton.setToolTipText("Print");
          printButton.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e)
			     {
			  	   PrinterJob job = PrinterJob.getPrinterJob();
			  	   	PageFormat pf = job.pageDialog(job.defaultPage());
			  	  	job.setPrintable(textBox);
			  	 	 if (job.printDialog())
			  	          {
			  	  			try
			  	  			{
			  	  			job.print();
			  	  			}
			  	  			catch(PrinterException pe)
			  	  			{
			  	  			System.out.println(pe);
			  	  			}
						}// end if
	        	}
			   });
          toolBar.add(printButton);

		  toolBar.addSeparator();

          //third button
          newDocumentButton = new JButton(new ImageIcon("images\\new.gif"));
          newDocumentButton.setToolTipText("Open new document");
          newDocumentButton.addActionListener(new ActionListener()
          {
			  public void actionPerformed(ActionEvent e)
			  {
				  textBox.setText(null);
			  }
		  }
		  );

          toolBar.add(newDocumentButton);

          //separator
         // toolBar.addSeparator();

          //fourth button
          fileOpenButton = new JButton(new ImageIcon("images\\open.gif"));
          fileOpenButton.setToolTipText("Open File");
          fileOpenButton.addActionListener(new ActionListener() {

              public void actionPerformed(ActionEvent e)
			                {
			  				int returnVal = fc.showOpenDialog(Kpad.this);
			  				if (returnVal == JFileChooser.APPROVE_OPTION)
			  				{
			  			      File file = fc.getSelectedFile();
			  			      openedFiles.add(file);
			  			      fileList.setListData(openedFiles);
			  			      FileReader reader = null;
			  			      currentFileName = file.getName();
		                       statusBar.setText(currentFileName);
			  			      try
			  			        {
			  				   	reader = new FileReader(file);
			  					textBox.read(reader,null);
			  				     }
			  				  catch(IOException ioe)
			  				    {
			  					textBox.append("unable to open file" + file.getName());
			  				    }
			  			    }// end if


			  			    String openSoundFile = "dooropen.wav";
						    try
							{

							AudioClip clip = Applet.newAudioClip(new URL(currentDirURL,openSoundFile));
							clip.play();
							}
							catch(MalformedURLException ex)
							{
							System.err.println("bad URL"+ ex);
							System.exit(1);
				             }
			               }// method ends
			            }// class ends
          );// addActionListener ends


        toolBar.add(fileOpenButton);
        //toolBar.addSeparator();

        //fifth button cut button


		// sixth button copy
		 copyButton = new JButton(new ImageIcon("images\\copy.gif"));
		 copyButton.setToolTipText("Copy");
	     copyButton.addActionListener(new ActionListener() {
				     public void actionPerformed(ActionEvent e)
				     {
					 textBox.copy();
					 }
				  });
		  toolBar.add(copyButton);
		  //toolBar.addSeparator();

		  //seventh Button paste button

			pasteButton = new JButton(new ImageIcon("images\\paste.gif"));
			pasteButton.setToolTipText("Paste");
			pasteButton.addActionListener(new ActionListener() {
			              public void actionPerformed(ActionEvent e) {
			                  textBox.paste();
			              }
			          });
		toolBar.add(pasteButton);
        //toolBar.addSeparator();

        // cut Button

         cutButton = new JButton(new ImageIcon("images\\cut.gif"));
				          cutButton.setToolTipText("Cut selected text");
				          cutButton.addActionListener(new ActionListener() {
				              public void actionPerformed(ActionEvent e) {
		                  textBox.cut();
		                  String cutsoundFile = "Camera.wav";
		                  try
						  {

						   AudioClip clip = Applet.newAudioClip(new URL(currentDirURL,cutsoundFile));
						   clip.play();
						  }
						  catch(MalformedURLException ex)
						  {
						  System.err.println("bad URL"+ ex);

				          }
					  }
				  });
				  toolBar.add(cutButton);
				  //toolBar.add(playMusicButton);
		 // toolBar.addSeparator();

        // eight button undo
       /* undoButton = new JButton(new ImageIcon("images\\undo.gif"));
		undoButton.setToolTipText("Undo last change");
		undoButton.addActionListener(new ActionListener() {
		           public void actionPerformed(ActionEvent e) {
		                  // reserved for action
		              }
		          });
		 toolBar.add(undoButton);
        toolBar.addSeparator();*/
       /* DocumentListener myListener = ??;
		    JTextArea myArea = ??;
		    myArea.getDocument().addDocumentListener(myListener);*/


     textBox.getDocument().addDocumentListener( new DocumentListener(){
		 public void insertUpdate(DocumentEvent e)
		 {
			 textChanged = true;
			 System.out.print(textBox.getText().charAt(e.getOffset()));
		 }

         public void removeUpdate(DocumentEvent e)
         {
			 textChanged = true;
			 System.out.println(textChanged);
		 }
         public void changedUpdate(DocumentEvent e)
         {
			 textChanged = true;
			 System.out.println(textChanged);
		 }
	 });


	this.addWindowListener(new WindowAdapter() {

			        public void windowClosing(WindowEvent e)
	        {
				if(textChanged==true)
				{
				  if(JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(null,"Do you want to save changes you have made",
                                    			"Alert !",JOptionPane.YES_NO_OPTION))
				   {
					// code for if text has been changed
				   }
			   }

				System.exit(0);
		}// end of method
    });
  /********************************************************************************/
  JPanel contentPane = new JPanel();
          contentPane.setLayout(new BorderLayout());
          contentPane.setPreferredSize(new Dimension(400, 100));
          contentPane.add(toolBar, BorderLayout.NORTH);
          contentPane.add(splitPane, BorderLayout.CENTER);
          contentPane.add(statusBar,BorderLayout.SOUTH);
          setContentPane(contentPane);

/*********************************************************************************/
// setting up and innitializing JPopupMenu

popChoice = new JPopupMenu();
popChoice.setInvoker(fileList);
MouseListener popupListener = new PopupListener();
fileList.addMouseListener(popupListener);
closeFileInList = new JMenuItem("close");
popChoice.add(closeFileInList);
closeFileInList.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent ev)
	{   //JList theList = (JList)ev.getSource();
		int index = fileList.getSelectedIndex();
	    File selectedFile =  (File)openedFiles.elementAt(index);
	    openedFiles.remove(selectedFile);
	    fileList.setListData(openedFiles);
	    statusBar.setText("Kpad");
	}
});

this.pack();

		this.setBounds(50,50,600,400);

	 setVisible(true);

 }  // constructor ends



 public boolean hasRepeatedCharacters(String key)
  	{
 		//System.out.println(key);
  		boolean repeated = false;
  		key = key.toUpperCase();
  		for(int i=0;i<(key.length()-1);i++)
  		 {
  			 for(int j=i+1;j<key.length();j++)
  			 {
  				 if(key.charAt(i)==key.charAt(j))
  				 {
  				 repeated = true;
  			 	}
  			 }
  		 }
  		 return repeated;
	}

 private void doVigenereCipher()
 {
	 File saveFile = null;
	 long beginTime = 0L,endTime  = 0L,difference = 0L;
	 			 String key =  JOptionPane.showInputDialog("Enter alphabetic key please");
	 			 if(key.length()> textBox.getText().length())
	 			 {
	 			 JOptionPane.showMessageDialog(null,"The key must be less than the text to be coded","UNAUTHORISED KEY",JOptionPane.PLAIN_MESSAGE);
	 			 return;
	 		     }
	 		     int returnVal = fc.showSaveDialog(Kpad.this);
	 			 if(returnVal == JFileChooser.APPROVE_OPTION)
	 			 {
	 			 saveFile = fc.getSelectedFile();
	 			 Vigenere enc = new Vigenere (key);
	 			 String message = textBox.getText();
	 			 beginTime = System.currentTimeMillis();
	 			 String coded = enc.doVigenereCipher(message);
	 			 endTime = System.currentTimeMillis();
	 			 try
	 			 	{
	 			 	   enc.writeBytes(coded,saveFile);
	 			 	}
	 			 	catch(FileNotFoundException fe)
	 			 	{
	 			 	   JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
	 			 	}
	 			 	catch(IOException ioe)
	 			 	{
						JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
					}
	 		     }// endif
	 		    // JOptionPane.showMessageDialog(null,"The time it took to cipher is "+ difference +" milli seconds","Statistics",JOptionPane.PLAIN_MESSAGE);

 }


 private void doVigenereDecipher()
 {
	File file;
	long beginTime = 0L,endTime  = 0L,difference = 0L;
	Date beg,end;
	String s = null;
	String key =  JOptionPane.showInputDialog("Enter alphabetic key please");

	 int returnVal = fc.showOpenDialog(Kpad.this);
	 if (returnVal == JFileChooser.APPROVE_OPTION)
   	{
	 	 file = fc.getSelectedFile();
	 	 openedFiles.add(file);
	 	 fileList.setListData(openedFiles);
	 	 Vigenere  enc = new Vigenere(key);
	 	 try{s = enc.readBytes(file);}
	 	 catch(FileNotFoundException fe){}
	 	 catch(IOException ioe){}
	 	 if(key.length() > s.length()) return;
	 	 beginTime = System.currentTimeMillis();
	 	 beg = new Date();
	 	 String message = enc.doVigenereDecipher(s);
	 	 end = new Date();
	 	 //endTime = System.currentTimeMillis();
	 	 difference = beg.getTime() - end.getTime();
	    textBox.setText(message);
	   //JOptionPane.showMessageDialog(null,"The time it took to cipher is "+ difference +" milli seconds","Statistics",JOptionPane.PLAIN_MESSAGE);


	}
 }


 private void doMonoSubstitutionCipher()
 {
	 File saveFile = null;
	 String key =  JOptionPane.showInputDialog("Enter alphabetic key please");
	 int returnVal = fc.showSaveDialog(Kpad.this);
	 	if(returnVal == JFileChooser.APPROVE_OPTION)
	 	 {
	 	 	saveFile = fc.getSelectedFile();
	 	 	MonoSubstitutionCipher enc = new MonoSubstitutionCipher(key);
	 	 	String message = textBox.getText();
	 	 	String coded = enc.code(message);
	 	 	try
	 	 	{
	 	 	   enc.writeBytes(coded,saveFile);
	 	 	}
	 	 	catch(FileNotFoundException fe)
	 	 	{
	 	 	   JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
	 	 	}
	 	 	catch(IOException ioe)
			{
				JOptionPane.showMessageDialog(null,"An IO Exception has occured","Exception",JOptionPane.PLAIN_MESSAGE);
			}
	 	}//endif
	 	//playChimesSound();
 }

 private void doMonoSubstitutionDecipher()
 {
	 File file;
	 String s = null;
	 String key =  JOptionPane.showInputDialog("Enter alphabetic key please");

	 	 int returnVal = fc.showOpenDialog(Kpad.this);
	 	 if (returnVal == JFileChooser.APPROVE_OPTION)
	     {
	 	 	 file = fc.getSelectedFile();
	 	 	 openedFiles.add(file);
	 	 	 fileList.setListData(openedFiles);
	 	 	 MonoSubstitutionCipher enc = new MonoSubstitutionCipher(key);
	 	 	 try{s = enc.readBytes(file);}
	 	 	 catch(FileNotFoundException fe){}
	 	 	 catch(IOException ioe){}
	 	 	 if(key.length() > s.length()) return;
	 	 	 String message = enc.decode(s);
	 	     textBox.setText(message);
	   }
	   //playChimesSound();
 }


 private void playChimesSound()
 {

	 String soundFile = "Chimes.wav";
	 try
	 {
	 	  AudioClip clip = Applet.newAudioClip(new URL(currentDirURL,soundFile));
	 	  clip.play();
	 }
	 catch(MalformedURLException ex)
	 {
	 	System.err.println("bad URL"+ ex);
	 	//System.exit(1);
     }
 }


 private void doCeaserAnalysis()
 {
	 Thread currThread = Thread.currentThread();// setting the priority of event handeling Thread
	 currThread.setPriority(Thread.MAX_PRIORITY);//speeds up the the decoding by a magnitude.(experimented with 2.6MB of data).

	 long beginTime = 0L,endTime  = 0L,difference = 0L;
	 String message = textBox.getText();
	 CeaserAnalyser analyser = new CeaserAnalyser();
	 beginTime = System.currentTimeMillis();

	 message = analyser.analyseAndDecodeCeaser(message);
	 endTime = System.currentTimeMillis();
	 textBox.setText(message);


	 difference = endTime - beginTime;
	 JOptionPane.showMessageDialog(null,"The time it took to cipher Analyse " + message.length()  +" bytes is "+ difference +" milli seconds","Statistics",JOptionPane.PLAIN_MESSAGE);
	// currThread.setPriority(Thread.NORM_PRIORITY);

 }
 /**Inner class for handeling popup menu trigger event(left click for window platform. */
 class PopupListener extends MouseAdapter {
         public void mousePressed(MouseEvent e) {
             maybeShowPopup(e);
         }

         public void mouseReleased(MouseEvent e) {
             maybeShowPopup(e);
         }

         private void maybeShowPopup(MouseEvent e) {
			 //fileList.clearSelection();
             if ( (e.isPopupTrigger()) && (fileList.getSelectedIndex() != -1)   )
             {
                 popChoice.show(e.getComponent(),
                            e.getX(), e.getY());
             }
         }
    }
/***********************************************************************
*******************************MAIN*************************************
************************************************************************/

public static void main(String args[])
{
	UIManager myUIMgr = new UIManager();
		        UIManager.LookAndFeelInfo[] installedLnFs = myUIMgr.getInstalledLookAndFeels();
		        Object[] options = new Object[installedLnFs.length];

		        for (int i = 0; i < installedLnFs.length; i++)
		        {
		           options[i] = installedLnFs[i].getName();
		        }


		        int option = JOptionPane.showOptionDialog(null, "Select a Look and Feel", "KPad",
		                               JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
		                               new ImageIcon("Splash.jpeg"), options, options[0]);

		        // Set LnF to selected
		        try
		        {
		            myUIMgr.setLookAndFeel(installedLnFs[option].getClassName());
		        }
		        catch(Exception e)
		        {
	            e.printStackTrace();
			    }

	Thread t1 = new Thread(new Startup());
	Thread t2 = new Thread(new StartProgram());
	t1.start();
	t2.start();
    //System.out.println("Loading Kpad Please wait");
	//Kpad myPad = new Kpad();

}
}

/********************************end of Kpad class*********************
***********************************************************************
***********************************************************************
***********************************************************************
***********************************************************************
***********************************************************************/





/**************************************************************************/


/*************************************************************************/

