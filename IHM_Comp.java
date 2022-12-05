	import javax.swing.*;
	import javax.swing.tree.DefaultMutableTreeNode;
	
	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.util.ArrayList;
	import java.util.stream.Collectors;
	
	public class IHM_Comp extends JFrame{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JPanel panwest,paneast;
		String[] motC = {"if","program","int","function","void","then","else","begin","fin","var","const","end"};
		Character[] op = {'<','>','+','-','*','/','='};
		Character[] sp = {'}','{',';',')','(','[',']','.'};
		JTextArea codec;
		
		public IHM_Comp() {
			this.setSize(680,480);
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			
			//WEST PANEL (MENU)
			panwest = new JPanel();
			DefaultMutableTreeNode project = new DefaultMutableTreeNode("Compilation");
			DefaultMutableTreeNode package1 = new DefaultMutableTreeNode("Compiler");
			DefaultMutableTreeNode classe1 = new DefaultMutableTreeNode("Compiler");
			package1.add(classe1);
			project.add(package1);
			
			JTree jt = new JTree(project);
			panwest.add(jt);
			
			//CENTER PANEL (TEXT)
			JTabbedPane tb = new JTabbedPane();
			codec = new JTextArea(" ");
			JScrollPane sp = new JScrollPane(codec);
			tb.add("Compiler",sp);
			
			//SOUTH PANEL(BOUTTONS)
			JPanel south = new JPanel();
			JButton run = new JButton("Run");
			south.add(run);
			JButton reset = new JButton("Reset");
			south.add(reset);
			reset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					codec.setText("");  
					System.out.print("-------------------------------------------------\n"); 
					}});
			run.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (verifLang(codec.getText())==true) {
						Analyser(codec.getText()); 
					}else JOptionPane.showMessageDialog(null, "LE CODE DOIR ETRE EN LETTRES LATIN UNIQUEMENT SANS ACCENTS ET SANS CARACTERES SPECIAUX.",
						      "ERREUR COMPI", JOptionPane.ERROR_MESSAGE);
					   
					}});
			
			this.getContentPane().add(south,BorderLayout.SOUTH);
			this.getContentPane().add(panwest,BorderLayout.WEST);
			this.getContentPane().add(tb,BorderLayout.CENTER);
		}
		
	//FONCTION DE VERIFICATION 	
		@SuppressWarnings("removal")
		private void Analyser(String prog){
	
		    StringBuilder crm = new StringBuilder(prog);
		    ArrayList<String> functs = new ArrayList<String>();
		    ArrayList<String> voids = new ArrayList<String>();
		    ArrayList<String> list_mot = new ArrayList<String>();
		    ArrayList<String> list_motC = new ArrayList<String>();
		    ArrayList<String> list_id = new ArrayList<String>();
		   ArrayList<String> list_num = new ArrayList<String>();
		   ArrayList<Character> list_op = new ArrayList<Character>();
		   ArrayList<Character> list_sp = new ArrayList<Character>();
		   
		    String mot = "";
		    String num = "";
		    String program = "";
		    
		    for(int i=0; i<crm.length(); i++){
		        Character charAt = crm.charAt(i);
		        
		        //pour les nombres
		        if (Character.isDigit(charAt)) {
		        	num = num + charAt;
		        }else {
		        	if (!num.isEmpty()) {
			        	if (list_num.contains(num)==false) {
			        	list_num.add(new String(num));
			        	}
			        }
		        	num ="";
		        }
		        //pour les mots
		        if(Character.isAlphabetic(charAt)){
		            	mot = mot + charAt;
		        }
		       
		        //Remplir les lists
		        else{
		   			
		        	
		        	if(!mot.isEmpty()) {
		        		list_mot.add(new String(mot));
		        		
		        		for(String id : list_mot) {
		        			if(id=="INT") {
		        				list_id.add(new String(id+1));
		        			}
		        		}
		        	}
		        
		        //remplir liste mots clé
		        for(int j=0;j<motC.length;j++) {
	        			if ( (mot.equals(motC[j])) || (mot.equals(motC[j].toUpperCase()))) {
	        				if((list_motC.contains(mot)==false) && (list_motC.contains(mot.toUpperCase())==false)){
	        					list_motC.add(new String(mot));
	        					
	        					}
	        					
	        				}
	        		}
		        //remplir la liste des operateurs
		        for(int o=0;o<op.length;o++) {
	    			if (charAt==op[o]){
	    				if((list_op.contains(charAt)==false)){
	    					list_op.add(new Character(charAt));
	    					}
	    					
	    				}
	    		}
		      //remplir la liste des separateurs
		        for(int s=0;s<sp.length;s++) {
	    			if (charAt==sp[s]){
	    				if((list_sp.contains(charAt)==false)){
	    					list_sp.add(new Character(charAt));
	    					}
	    					
	    				}
	    		}
		        	
		        mot = ""; 
		        }
		   
		    }
		  
		    for(int i=0; i < list_mot.size(); i++) 
	        {
		    	//liste des identificateurs
		    	if(list_mot.get(i).equals("INT")) {
		    		if(list_mot.get(i-1).equals("INT")) {
		                list_id.add(list_mot.get(i-2));
		            }else list_id.add(list_mot.get(i-1));
	                
	            }
		    	//avoir le nom du programme
		    	if(list_mot.get(i).equals("PROGRAM")) {
	                program = list_mot.get(i+1);
	            }
		    	
		    	//avoir tout les fonctions et les procédures
		    	if((list_mot.get(i).contains("FUNCTION"))){
	                functs.add(list_mot.get(i+1));
	            }
		    	if((list_mot.get(i).contains("VOID"))) {
	                voids.add(list_mot.get(i+1));
	            }
	        }
		    
		    //enlever les elements dupliquer dans la list des id
		    ArrayList<String> newidList = (ArrayList<String>) list_id.stream().distinct().collect(Collectors.toList());
		    
		    System.out.println("Nom du Programme: "+program);
		    System.out.println("Les Fonctions: "+functs+" // "+"Les Procédures: "+voids);
		    System.out.println("Mots clé: "+list_motC);
		    System.out.println("Identificateurs: "+newidList);
		    System.out.println("Constants: "+list_num);
		    System.out.println("Opérateurs: "+list_op);
		    System.out.println("Séparateurs: "+list_sp);
		    System.out.println("Nombre de lignes: "+codec.getLineCount()); //afficher le nombre de lignes
		}
	
		//verif en cas input des lettres autre que les lettres latins
		private boolean verifLang(String lang) {
			StringBuilder langue = new StringBuilder(lang);
			String motL="";
			
			for(int i=0; i<langue.length(); i++){
		        Character charAt = langue.charAt(i);
		        if(Character.isAlphabetic(charAt)){
	            	motL = motL + charAt;
	        }
		        else {
		        	if(!motL.isEmpty()) {
		        		if(!motL.matches("^[a-zA-Z][a-zA-Z\\s]+$")) {
		        			return false;
		        		}
		    	}
		        }
		        	
		}
			return true;
		}
		   
		public static void main(String[] args) {
			new IHM_Comp();
		}
	
	}
