package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{
	
	private List<SingleDocumentModel> docs=new ArrayList<SingleDocumentModel>();
	private SingleDocumentModel current;
	private List<MultipleDocumentListener> listeners=new ArrayList<MultipleDocumentListener>();
	Icon edited;
	Icon unedited;
	
	public DefaultMultipleDocumentModel() {
		getIcons();
		makeSingleDocumentModel("", null);
	}
	
	
	private static final long serialVersionUID = 1L;

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return this.docs.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		return makeSingleDocumentModel("", null);
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if(path==null) throw new NullPointerException("Path cannot be null!");
		for(SingleDocumentModel doc:docs) {
			if(doc.getFilePath()!=null) {
				if (doc.getFilePath().compareTo(path)==0) {
					fireCurrentChanged(current,doc);
					current=doc;
					return doc;
				}
			}
		}
		if(!Files.isReadable(path)) {
			throw new RuntimeException("Incorrect path!");
		}
		
		byte[] okteti;
		try {
			okteti = Files.readAllBytes(path);
		} catch(Exception ex) {
			throw new RuntimeException("File not readable!");
		}
		String tekst = new String(okteti, StandardCharsets.UTF_8);
		SingleDocumentModel m =makeSingleDocumentModel(tekst,path);
		fireCurrentChanged(current,m);
		current=m;
		return m;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		for(SingleDocumentModel doc:docs) {
			if(doc.getFilePath()!=null && newPath!=null && doc.getFilePath().compareTo(newPath)==0 && doc!=current)
				throw new RuntimeException("A file with that path is alredy opened!");
		}
		if(newPath==null && current.getFilePath()!=null)
			newPath=current.getFilePath();
		else 
			current.setFilePath(newPath);
		fireCurrentChanged(current, current);
		byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, podatci);
		} catch (IOException e1) {
			throw new RuntimeException();
		}
		model.setModified(false);
		
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index=-1;
		for(SingleDocumentModel doc:docs) {
			index++;
			if(doc.getFilePath()!=null) {
				if (doc.getFilePath().compareTo(model.getFilePath())==0) {
					if(doc.getFilePath()==current.getFilePath()) {
						if(getNumberOfDocuments()!=1)
							fireCurrentChanged(current, docs.get(docs.size()-1));
						current=docs.get(docs.size());
						break;
					}
					docs.remove(doc);
					this.removeTabAt(index);
					fire((listener, document)->listener.documentRemoved(document));
					return;
				}
			}
			System.out.println("Removing index: "+index);
			docs.remove(model);
			this.removeTabAt(index);
			System.out.println("Removed index: "+index);
			fire((listener, document)->listener.documentRemoved(document));
			if(getNumberOfDocuments()==0)
				makeSingleDocumentModel("", null);
			break;
			
		}
		
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
		
	}

	@Override
	public int getNumberOfDocuments() {
		return docs.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		if(index<0) throw new IllegalArgumentException("Index must be a non negative number!");
		return docs.get(index);
	}
	
	public void fire(BiConsumer<MultipleDocumentListener,SingleDocumentModel> l) {
		for(MultipleDocumentListener listener:listeners)
			l.accept(listener, current);
	}
	public void fireCurrentChanged(SingleDocumentModel old,SingleDocumentModel updated) {
		for(MultipleDocumentListener listener:listeners) {
			listener.currentDocumentChanged(old, updated);
		}
	}
	
	public SingleDocumentModel makeSingleDocumentModel(String tekst,Path path){
		JPanel panel = new JPanel(new BorderLayout());
		SingleDocumentModel newDoc=new DefaultSingleDocumentModel(path, tekst);
		docs.add(newDoc);
        JScrollPane scroll=new JScrollPane(newDoc.getTextComponent());
        panel.add(scroll, BorderLayout.CENTER);
        this.addTab("",edited, panel, path==null?"unedited":path.toString());
        fireCurrentChanged(current, newDoc);
        current=newDoc;
        newDoc.addSingleDocumentListener(new SingleDocumentListener() {
        	@Override
        	public void documentModifyStatusUpdated(SingleDocumentModel model){
        		setIcon(model);
        	}
        	
        	@Override
        	public void documentFilePathUpdated(SingleDocumentModel model) {
        		setTabName(model);
        	}
        	
        });
        setTabName(newDoc);
        setIcon(newDoc);
        fire((listener, model)->listener.documentAdded(model));
        return newDoc;
	}
	
	public void setIcon(SingleDocumentModel model) {
		int index = docs.indexOf(model);
		if(!model.isModified()) {
			this.setIconAt(index, unedited);
		}
		else
			this.setIconAt(index, edited);
		return;
	}
	
	public void setTabName(SingleDocumentModel model) {
		int index = docs.indexOf(model);
		JComponent component = (JComponent)this.getComponentAt(index);
		if(model.getFilePath()==null) {
			this.setTitleAt(index, "unnamed");
			component.setToolTipText("unnamed");
			return;
		}
		String[] path=model.getFilePath().toString().split("\\\\");
		this.setTitleAt(index, path[path.length-1]);
		this.setToolTipText(model.getFilePath().toString());
		return;
	}
	
	public void getIcons() {
		try {
			InputStream is = this.getClass().getResourceAsStream("icons/edited.png");
			if(is==null) throw new FileNotFoundException();
			byte[] bytes = is.readAllBytes();
			edited = resizeImage(new ImageIcon(bytes));
			is = this.getClass().getResourceAsStream("icons/unedited.png");
			if(is==null) throw new FileNotFoundException();
			bytes = is.readAllBytes();
			unedited = resizeImage(new ImageIcon(bytes));
			is.close();
		}catch(Exception e) {};
	return;
	
	}
	
	public ImageIcon resizeImage(ImageIcon icon) {
		Image image = icon.getImage();  
		Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);  
		return new ImageIcon(newimg); 
	}
	
	public void setCurrent(SingleDocumentModel m) {
		if(m!=current) {
			fireCurrentChanged(current, m);
			current=m;
		}		
	}
}
