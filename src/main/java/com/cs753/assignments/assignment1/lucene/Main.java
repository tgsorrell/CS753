package com.cs753.assignments.assignment1.lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.unh.cs.treccar_v2.Data;
import edu.unh.cs.treccar_v2.read_data.DeserializeData;
import com.cs753.assignments.assignment1.lucene.search.Indexer;

/**
 * Main class that will run the required indexing and queries for the
 * assignment.
 * 
 * @author Johnny
 *
 */
public class Main {

	public static void main(String[] args) throws IOException {

		/*
		 * Part 1:
		 * 
		 * Use Lucene to create an index of the paragraphs of
		 * train.test200.cbor.paragraphs contained in the "test200" dataset of the TREC
		 * Complex Answer Retrieval Data.
		 */

		/*
		 * read paragraphs from cbor file and map them keep track of ( ids -> paragraph
		 * )
		 */
		Map<String, String> stringFields = getParagraphsAndMapThem();

		// index paragraphs
		Indexer indexer = Indexer.getIndexer();
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
		 * 10 results. These will need to use SearchEngine and TextFields for
		 * tokenizing.
		 */

		// Q1 "power nap benefits"

		// Q2 "whale vocalization production sound

		// Q3 pokemon puzzle league

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
			final File file = new File("src/main/resources/test200/test200-train/train.pages.cbor-paragraphs.cbor");
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
