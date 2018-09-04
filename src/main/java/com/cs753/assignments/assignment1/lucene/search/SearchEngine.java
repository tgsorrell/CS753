package com.cs753.assignments.assignment1.lucene.search;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
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
			searcher = new IndexSearcher( DirectoryReader.open( FSDirectory.open( path )));
		} catch (IOException e) {
			e.printStackTrace();
		}
			/*
			 * There's a very common mistakes that people often make, 
			 * so I have to mention it here. When you use Lucene, 
			 * you have to specify the Analyzer twice, 
			 * once when you create an IndexWriter object (for index construction) and 
			 * once more when you create a QueryParser (for query parsing). 
			 * 
			 * Please note that it is extremely important that you use the same analyzer for both. 
			 * In our example, since we created IndexWriter using StandardAnalyzer before, 
			 * we are also passing StandardAnalyzer to QueryParser.
			 */
			//parser = new QueryParser( "id", new StandardAnalyzer() );
		
	}
	
	public static void setIndexPath(String path){
		indexPath = path;
	}
	
	public TopDocs performSearch( String queryString, int n ) throws ParseException, IOException {

		parser = new QueryParser("text", new StandardAnalyzer());
		Query query = parser.parse( queryString );
		return searcher.search(query, n);
	}
	
	public Document getDocument( int docId ) throws IOException {
		
		return searcher.doc( docId );
	}
}
