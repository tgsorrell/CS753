package com.cs753.assignments.assignment1.lucene.search;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Index documents or business objects using the IndexWriter.
 * 
 * @author Johnny
 *
 */
public class Indexer {
	
	private IndexWriter indexWriter = null;
	private static Indexer indexer = null;
	private String indexPath = "src/main/java/com/cs753/assignments/assignment1/lucene/indexes";
	
	private Indexer() {}
	
	public static Indexer getIndexer() {
		
		if ( indexer == null ) {
			
			indexer = new Indexer();
		}
		return indexer;
	}

	public void setIndexPath(String path){
		indexPath = path;
	}
	
	/**
	 * 
	 * IndexDir Directory that specifies the directory in which the Lucene index
	 * will be created.
	 * 
	 * IndexWriterConfig specifies the configuration of the index, which is
	 * the version of the Lucene library and the document analyzer to be used
	 * when Lucene indexes the data.
	 * 
	 * the analyzer's job is to parse each field of the data into indexable tokens or keywords.
	 * @throws IOException 
	 */
	public IndexWriter getIndexWriter() throws IOException {
		
		if ( indexWriter == null ) {
			
			try {
				Path path = Paths.get(indexPath);
				Directory indexDir = FSDirectory.open( path );
				
				IndexWriterConfig config = new IndexWriterConfig( new StandardAnalyzer() );
				indexWriter = new IndexWriter( indexDir, config );
				
			} catch ( IOException e ) {
				throw new IOException("Bad Path");
			}
		}
		return indexWriter;
	}
	
	/**
	 * We need to index our documents or business objects. To index a document you use the Lucene 
	 * Document class, to which you add the fields that you want indexed. A Lucene Document is 
	 * basically a container for a set of indexed fields.
	 * 
	 * A field class can either be a StringField or a TextField. 
	 * Use StringField for a field with an atomic value that should not be tokenized.
	 * Use TextField for a field that needs to be tokenized into a set of words.
	 * 
	 * A Field Object has the following parameters:
	 * 		Field name - The name of the field.
	 * 		Field value - Value of the field, can be String or a Reader if the object is a file.
	 * 		Storage flag - Specifies whether the actual value of the field needs to be stored in the com.cs753.assignments.assignment1.lucene index.
	 * 
	 * @param stringFields any stringfield objects to be created as a map of ( name -> values ).
	 * @param textFields any textfield objects to be created as a map of ( name -> text )
	 * @throws IOException 
	 */
	public void indexObject( Map<String,String> stringFields, Map<String,String> textFields ) throws IOException {
		
		IndexWriter writer = null;
		try {
			writer = getIndexWriter();
		} catch ( IOException e ) {
			throw new IOException("Could not get IndexWriter");
		}
		
		Document doc = new Document();
		
		// add all StringFields to the document
		if ( stringFields != null && !stringFields.isEmpty() ) {
			
			for (Entry<String, String> stringEntry : stringFields.entrySet()) {

				doc.add(new StringField(stringEntry.getKey(), stringEntry.getValue(), Field.Store.YES));
			}
		}
		
		// add all TextFields to the document
		if ( textFields != null && !textFields.isEmpty() ) {
			
			for (Map.Entry<String, String> textEntry : textFields.entrySet()) {

				doc.add(new TextField(textEntry.getKey(), textEntry.getValue(), Field.Store.YES));
			}
		}
		
		writer.addDocument(doc);
	}
}
