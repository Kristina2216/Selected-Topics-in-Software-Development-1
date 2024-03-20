package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;
import hr.fer.zemris.java.gui.charts.BarChart;
import hr.fer.zemris.java.gui.charts.BarChartDemo;
import hr.fer.zemris.java.gui.charts.XYValue;
import ispit.ExamZad01_1;
import ispit.ExamZad01_2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;

import javax.swing.Action;

import javax.swing.JButton;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;






public class JNotepadPP extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private DefaultMultipleDocumentModel tabs;
	private FormLocalizationProvider flp=new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	private int finalDoc=0;
	
	
	public JNotepadPP() {
		flp.addLocalizationListener(
				new ILocalizationListener() {
					public void localizationChanged() {
						}
				}
		);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int fileNumber=tabs.getNumberOfDocuments();
				System.out.println(fileNumber);
				for(int i=fileNumber-1;i>=0;i--) {
					SingleDocumentModel file=tabs.getDocument(i);
					Object[] options = {"Save",
		                    "Discard",
		                    "Cancel"};
					if(file.isModified()) {
						String openDocName="unsaved";
						if(file.getFilePath()!=null) {
							String[] path =file.getFilePath().toString().split("\\\\");
							openDocName=path[path.length-1];
						}
						int action=JOptionPane.showOptionDialog(
								JNotepadPP.this, 
								"Document "+openDocName+" has been modified. Would you like to save the changes?", 
								"Warning",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								options, options[2]);
						if(action==0) {
							if(openDocName.equals("unsaved")) {
								JFileChooser jfc = new JFileChooser();
								jfc.setDialogTitle("Save document");
								if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
									JOptionPane.showMessageDialog(
											JNotepadPP.this, 
											"Nista nije snimljeno.", 
											"Upozorenje", 
											JOptionPane.WARNING_MESSAGE);
									return;
								}
								Path openedFilePath = jfc.getSelectedFile().toPath();
								file.setFilePath(openedFilePath);
							}
							tabs.saveDocument(file, file.getFilePath());
							tabs.closeDocument(file);
							continue;
						}else if(action==1) {
							tabs.closeDocument(file);
							continue;
						}else {
							JNotepadPP.this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
						}
					}else {
						tabs.closeDocument(file);
						if(i==0) finalDoc=1;
						i=tabs.getNumberOfDocuments()-1;
					}
					if(i==0)
						return;
					System.out.println(i);
				}
				
			}
		});
		setSize(300, 600);
		initGUI();
	}
	
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());
		tabs=new DefaultMultipleDocumentModel();
		tabs.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				JNotepadPP.this.setTitle(currentModel.getFilePath()==null?"unknown":currentModel.getFilePath().toString());
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				JNotepadPP.this.setSize(JNotepadPP.this.getWidth()+100, JNotepadPP.this.getHeight());
				
			}

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				JNotepadPP.this.setSize(JNotepadPP.this.getWidth()-100, JNotepadPP.this.getHeight());
				
			}
			
		});
		tabs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(tabs.getNumberOfDocuments()!=0)
					tabs.setCurrent(tabs.getDocument(tabs.getSelectedIndex()));
				}
		 });
		createActions();
		createMenus();
		createToolbars();
		this.getContentPane().add(tabs, BorderLayout.CENTER);
		
	}

	private Action openDocumentAction = new LocalizableAction("Open", "openDesc", this.flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if(fc.showOpenDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			tabs.loadDocument(filePath);
		}
	};
	
	private Action saveDocumentAction =new LocalizableAction("Save", "saveDesc", this.flp){
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if(jfc.showSaveDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(
							JNotepadPP.this, 
							"Nista nije snimljeno.", 
							"Upozorenje", 
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				Path openedFilePath = jfc.getSelectedFile().toPath();
			try {
				tabs.saveDocument(tabs.getCurrentDocument(),openedFilePath);
			}catch(Exception ex){
				JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Pogreska prilikom zapisivanja datoteke "+openedFilePath.toFile().getAbsolutePath()+".\n"+ex, 
					"Pogreska", 
					JOptionPane.ERROR_MESSAGE);
			return;
			}
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Datoteka je snimljena.", 
					"Informacija", 
					JOptionPane.INFORMATION_MESSAGE);
		}
			
	};
	
	private Action deleteSelectedPartAction = new LocalizableAction("Delete", "deleteDesc", this.flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = tabs.getCurrentDocument().getTextComponent().getDocument();
			int len = Math.abs(tabs.getCurrentDocument().getTextComponent().getCaret().getDot()-tabs.getCurrentDocument().getTextComponent().getCaret().getMark());
			if(len==0) return;
			int offset = Math.min(tabs.getCurrentDocument().getTextComponent().getCaret().getDot(),tabs.getCurrentDocument().getTextComponent().getCaret().getMark());
			try {
				doc.remove(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	private Action toggleCaseAction = new LocalizableAction("Toggle", "toggleDesc", this.flp){
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = tabs.getCurrentDocument().getTextComponent().getDocument();
			int len = Math.abs(tabs.getCurrentDocument().getTextComponent().getCaret().getDot()-tabs.getCurrentDocument().getTextComponent().getCaret().getMark());
			int offset = 0;
			if(len!=0) {
				offset = Math.min(tabs.getCurrentDocument().getTextComponent().getCaret().getDot(),tabs.getCurrentDocument().getTextComponent().getCaret().getMark());
			} else {
				len = doc.getLength();
			}
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch(BadLocationException ex) {
				ex.printStackTrace();
			}
		}

		private String changeCase(String text) {
			char[] znakovi = text.toCharArray();
			for(int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if(Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if(Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			return new String(znakovi);
		}
	};
	
	private Action exitAction = new LocalizableAction("Exit",  "exitDesc", this.flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(finalDoc==1)
				System.exit(0);
		}
	};
	
	private Action setHR= new AbstractAction() {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};
	private Action setEN= new AbstractAction() {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};
	private Action setDE= new AbstractAction() {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};
	private Action zad01= new AbstractAction() {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(()->{
				ExamZad01_1 c=new ExamZad01_1();
				c.setVisible(true);
			
		});
		}
	};
	
	private Action zad12= new AbstractAction() {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(()->{
				ExamZad01_2 c=new ExamZad01_2();
				c.setVisible(true);
			
		});
		}
	};
	private Action zad02= new AbstractAction() {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			List<hr.fer.zemris.java.gui.charts.XYValue> points = new ArrayList<>();
			int docNumber=tabs.getNumberOfDocuments();
			int[] numbers=new int[docNumber];
			Iterator it= tabs.iterator();
			int min=tabs.getCurrentDocument().getTextComponent().getText().length();
			int max=tabs.getCurrentDocument().getTextComponent().getText().length();
			for(int i=0;i<docNumber;i++) {
				DefaultSingleDocumentModel s=(DefaultSingleDocumentModel) it.next();
				numbers[i]=s.getTextComponent().getText().length();
				if (numbers[i]<min)
					min=numbers[i];
				if (numbers[i]>max)
					max=numbers[i];
				points.add(new XYValue(i, numbers[i]));
			}
			BarChart bar=new BarChart(points,"","", min, max, 10);
			SwingUtilities.invokeLater(() -> {
	            BarChartDemo graph = new BarChartDemo(bar);
	            graph.setVisible(true);
	        });
		}
	};

	private void createActions() {
		setHR.putValue(Action.NAME, "hr");
		
		zad01.putValue(Action.NAME, "Zadatak 1.1");
		zad12.putValue(Action.NAME, "Zadatak 1.2");
		zad02.putValue(Action.NAME, "Zadatak 2");
		
		
		setEN.putValue(Action.NAME, "en");
	
		
		setDE.putValue(Action.NAME, "de");
		
		
		
		openDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control O")); 
		openDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_O); 
		 
		
		
		saveDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control S")); 
		saveDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_S); 
		
		
		
		deleteSelectedPartAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("F2")); 
		deleteSelectedPartAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_D); 
		
		
		toggleCaseAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control F3")); 
		toggleCaseAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_T); 
		
		
		
		
		exitAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_X); 
		 
	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu(new LocalizableAction("File", null, this.flp));
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu editMenu = new JMenu(new LocalizableAction("Edit",null, this.flp));
		menuBar.add(editMenu);
		
		JMenu languageMenu = new JMenu(new LocalizableAction("Language", null, this.flp));
		languageMenu.add(new JMenuItem(setHR));
		languageMenu.add(new JMenuItem(setEN));
		languageMenu.add(new JMenuItem(setDE));
		menuBar.add(languageMenu);
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu ispitMenu = new JMenu(new LocalizableAction("Ispit", null, this.flp));
		ispitMenu.add(new JMenuItem(zad01));
		ispitMenu.add(new JMenuItem(zad12));
		ispitMenu.add(new JMenuItem(zad02));
		//ispitMenu.add(new JMenuItem(setDE));
		menuBar.add(ispitMenu);
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		
		editMenu.add(new JMenuItem(deleteSelectedPartAction));
		editMenu.add(new JMenuItem(toggleCaseAction));
		
		this.setJMenuBar(menuBar);
	}

	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);
		
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(deleteSelectedPartAction));
		toolBar.add(new JButton(toggleCaseAction));
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}
	
}
