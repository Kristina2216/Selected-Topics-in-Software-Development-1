package hr.fer.zemris.java.gui.prim;

import javax.swing.JFrame;

	import java.awt.BorderLayout;
	import java.awt.Container;
	import java.awt.GridLayout;
	import java.util.ArrayList;
	import java.util.List;

	import javax.swing.JButton;
	import javax.swing.JList;
	import javax.swing.JPanel;
	import javax.swing.JScrollPane;
	import javax.swing.ListModel;
	import javax.swing.SwingUtilities;
	import javax.swing.WindowConstants;
	import javax.swing.event.ListDataEvent;
	import javax.swing.event.ListDataListener;

	public class PrimDemo extends JFrame {

		private static final long serialVersionUID = 1L;

		public PrimDemo() {
			setLocation(20, 50);
			setSize(300, 200);
			setTitle("PrimDemo");
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			
			initGUI();
		}

		static class PrimeListModel<T> implements ListModel<T> {
			private List<T> elementi = new ArrayList<>();
			private List<ListDataListener> promatraci = new ArrayList<>();
			
			@Override
			public void addListDataListener(ListDataListener l) {
				promatraci.add(l);
			}
			@Override
			public void removeListDataListener(ListDataListener l) {
				promatraci.remove(l);
			}
			
			@Override
			public int getSize() {
				return elementi.size();
			}
			@Override
			public T getElementAt(int index) {
				return elementi.get(index);
			}
			
			public void add(T element) {
				int pos = elementi.size();
				elementi.add(element);
				
				ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
				for(ListDataListener l : promatraci) {
					l.intervalAdded(event);
				}
			}
			public void remove(int pos) {
				elementi.remove(pos);
				ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, pos, pos);
				for(ListDataListener l : promatraci) {
					l.intervalRemoved(event);
				}
			}
			public int getNextNumber() {
				int i=(int)this.getElementAt(this.getSize()-1)+1;
				int nextNumber=0;
				while(nextNumber==0) {
					for(int j=2;j<=i/2;j++) {
						if(i%j==0)
							break;
						nextNumber++;
					}
					if(nextNumber==i/2-1)
						return i;
					nextNumber=0;
					i++;
				}
				return 0;
			}
		}
		
		
		private void initGUI() {
			Container cp = getContentPane();
			cp.setLayout(new BorderLayout());
			
			PrimeListModel<Integer> model = new PrimeListModel<>();
			
			JList<Integer> list = new JList<>(model);
			JList<Integer> list2 = new JList<>(model);
			
			JPanel bottomPanel = new JPanel(new GridLayout(1, 0));

			JButton dodaj = new JButton("next");
			bottomPanel.add(dodaj);
			model.add(1);
			
			dodaj.addActionListener(e -> {
				model.add(model.getNextNumber());
			});
			JPanel central = new JPanel(new GridLayout(1, 0));
			central.add(new JScrollPane(list));
			central.add(new JScrollPane(list2));
			
			cp.add(central, BorderLayout.CENTER);
			cp.add(bottomPanel, BorderLayout.PAGE_END);
	
		}

		public static void main(String[] args) {

			SwingUtilities.invokeLater(() -> {
				JFrame frame = new PrimDemo();
				frame.pack();
				frame.setVisible(true);
			});
		}
	}
