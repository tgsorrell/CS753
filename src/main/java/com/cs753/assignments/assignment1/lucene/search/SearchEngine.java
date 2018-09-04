package com.cs753.assignments.assignment1.lucene.search;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;


/** 
 * The class SearchEngine provides a method called performSearch which takes a query string 
 * and the maximum number of matching documents that should be returned as the input parameters 
 * and returns the list of matching documents as a Lucene TopDocs object. 
 * 
 * The method takes the query string, parses it using QueryParser and performs search() using IndexSearcher.
 * 
 * 
 * @author Johnny
 */
public class SearchEngine {

	private IndexSearcher searcher = null;
	private QueryParser parser = null;
	private static String indexPath = "src/main/java/com/cs753/assignments/assignment1/lucene/indexes";
	
	public SearchEngine() throws IOException {
		
		try {
			Path path = Paths.get(indexPath);
			Directory dir = FSDirectory.open( path );
			IndexReader reader = DirectoryReader.open(dir);
			searcher = new IndexSearcher(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setIndexPath(String path){
		indexPath = path;
	}
	
	public TopDocs performSearch( String queryString, int n ) throws ParseException, IOException {
		parser = new QueryParser("text", new StandardAnalyzer());
		Query query = parser.parse(queryString);
		return searcher.search(query, n);
	}
	
	public Document getDocument( int docId ) throws IOException {
		return searcher.doc( docId );
	}
}
