package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel{
	private Path path;
	private boolean modification;
	private JTextArea editor;
	private List<SingleDocumentListener> listeners=new ArrayList<SingleDocumentListener>();
	
	
	public DefaultSingleDocumentModel(Path p, String txt) {
		path=p;
		editor=new JTextArea();
		editor.setText(txt);
		editor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
				fire((listener, model)->listener.documentModifyStatusUpdated(model));
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
				fire((listener, model)->listener.documentModifyStatusUpdated(model));
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
				fire((listener, model)->listener.documentModifyStatusUpdated(model));
				
			}
			
		});
		modification=false;
	}
	
	
	@Override
	public JTextArea getTextComponent() {
		return editor;
	}
	@Override
	public Path getFilePath() {
		return path;
	}
	@Override
	public void setFilePath(Path path) {
		this.path=path;
		fire((listener, model)->listener.documentFilePathUpdated(model));
		
	}
	@Override
	public boolean isModified() {
		return modification;
	}
	@Override
	public void setModified(boolean modified) {
		modification=modified;
		fire((listener, model)->listener.documentModifyStatusUpdated(model));
		
	}
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
		
	}
	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
		
	}
	
	public void fire(BiConsumer<SingleDocumentListener,DefaultSingleDocumentModel> l) {
		for(SingleDocumentListener listener:listeners)
			l.accept(listener, this);
	}
	
}
