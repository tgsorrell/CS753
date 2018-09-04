package com.cs753.assignments.assignment1.lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.cs753.assignments.assignment1.lucene.search.Indexer;
import com.cs753.assignments.assignment1.lucene.search.SearchEngine;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import edu.unh.cs.treccar_v2.Data;
import edu.unh.cs.treccar_v2.read_data.DeserializeData;

/**
 * Main class that will run the required indexing and queries for the
 * assignment.
 * 
 * @author Johnny
 *
 */
public class Main {

	static String resourcePath = "src/main/resources/test200/test200-train/train.pages.cbor-paragraphs.cbor";

	public static void main(String[] args) throws IOException, ParseException {

		/*
		 * Part 1:
		 *
		 * Use Lucene to create an index of the paragraphs of
		 * train.test200.cbor.paragraphs contained in the "test200" dataset of the TREC
		 * Complex Answer Retrieval Data.
		 */

		//Parse arguments
		if(args.length < 2) {
			System.out.println("Usage: java Main <index directory> <resource directory>");
			System.exit(1);
		}

		//read paragraphs from cbor file and map them keep track of ( ids -> paragraph)
		Map<String, String> stringFields = getParagraphsAndMapThem();
		Indexer indexer = Indexer.getIndexer();

			
		if (!args[0].equals("default")) {
			indexer.setIndexPath(args[0]);
			SearchEngine.setIndexPath(args[0]);
		}
		if (!args[1].equals("default"))
			resourcePath = args[1];


		/*
		 * not passing any searchable text at this point. Just indexing. This is why we
		 * are only using StringFields to index with because there is no tokenization
		 * needed at this stage.
		 */
		try {
			indexer.indexObject(stringFields, null);

		} catch (IOException e) {
			throw new IOException("Could not index object");
		}

		
		/*
		 * Part 2:
		 * 
		 * Run the following queries and list the paragraph IDs and content of the top
		 * 10 results.
		 */
		SearchEngine se = new SearchEngine();

		// Q1 "power nap benefits"
		TopDocs powerDocs = se.performSearch( "power nap benefits", 10 );
		// Q2 "whale vocalization production sound
		TopDocs whaleDocs = se.performSearch( "whale vocalization production sound", 10 );
		// Q3 pokemon puzzle league
		TopDocs pokemonDocs = se.performSearch( "pokemon puzzle league", 10 );
		
		// obtain the ScoreDocs
		ScoreDoc[] powerHits = powerDocs.scoreDocs;
		ScoreDoc[] whaleHits = whaleDocs.scoreDocs;
		ScoreDoc[] pokemonHits = pokemonDocs.scoreDocs;
		
		/* 
		 * retrieve each matching document
		 * then iterate through paragraphs to print id and content
		 * or can you use indexes to look at this?
		 */
		
		// at the moment just printing to try to see what im getting
		for ( int i = 0; i < powerHits.length; i++ ) {
			
			Document powerDoc = se.getDocument( powerHits[i].doc );
			System.out.println("power: " + powerDoc.toString() );
		}
		
		for ( int i = 0; i < whaleHits.length; i++ ) {
			
			Document whaleDoc = se.getDocument( whaleHits[i].doc );
			System.out.println("whale: " + whaleDoc.toString() );
		}
		
		for ( int i = 0; i < pokemonHits.length; i++ ) {
			
			Document pokemonDoc = se.getDocument( pokemonHits[i].doc );
			System.out.println("pokemon: " + pokemonDoc.toString() );
		}

	}

	/**
	 * Read the file's paragraphs and iterate through them in order to save their
	 * id's and text to the stringFields map.
	 * 
	 * @return HashMap mappings of what will be StringField objects ( id -> text )
	 * @throws IOException
	 */
	private static Map<String, String> getParagraphsAndMapThem() throws IOException {

		Map<String, String> stringFields = new HashMap<>();

		/*
		 * I'm trying to use the ReadDataTest.class code under mode.equals("paragraph")
		 * for this basically. This class is under : Maven Dependencies ->
		 * trec-car-tools-java-12.jar -> edu.unh.cs.treccar_v2 -> read_data
		 */
		try {
			final File file = new File(resourcePath);
			final FileInputStream fileInputStream = new FileInputStream(file);

			// use trec-car-tools to read paragraphs
			for (Data.Paragraph p : DeserializeData.iterableParagraphs(fileInputStream)) {
				// iterate through file paragraphs, put id and body to stringFields
				stringFields.put(p.getParaId(), p.getTextOnly());
			}

		} catch (IOException e) {
			throw new IOException("Invalid file");
		}

		return stringFields;
	}
}
