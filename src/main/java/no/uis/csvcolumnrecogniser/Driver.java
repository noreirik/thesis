package no.uis.csvcolumnrecogniser;

import java.util.List;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ie.crf.CRFCliqueTree;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Triple; 


public class Driver {

	public static void main(String[] args) {

		String text = "the dog and the house";
		String propsFile = "/home/eirik/git/CoreNLP/anna/eirik.prop";
		String testFile = "/home/eirik/git/CoreNLP/anna/eirik2.test";
		String modelFile = "/home/eirik/git/CoreNLP/anna/eirik.ner.gz";

		CRFClassifier<CoreLabel> classifier = null;

		try {
			classifier = CRFClassifier.getClassifier(modelFile);
		} catch(Exception ex) {
			System.out.println(ex.toString());
			System.exit(-1);
		}

		// Copied from NERDemo
		String[] example = {"Good afternoon Rajat Raina, how are you today?",
		"I go to school at Stanford University, which is located in California." };
		for (String str : example) {
			System.out.println(classifier.classifyToString(str));
		}
		System.out.println("---");

		for (String str : example) {
			// This one puts in spaces and newlines between tokens, so just print not println.
			System.out.print(classifier.classifyToString(str, "slashTags", false));
		}
		System.out.println("---");

		for (String str : example) {
			// This one is best for dealing with the output as a TSV (tab-separated column) file.
			// The first column gives entities, the second their classes, and the third the remaining text in a document
			System.out.print(classifier.classifyToString(str, "tabbedEntities", false));
		}
		System.out.println("---");

		for (String str : example) {
			System.out.println(classifier.classifyWithInlineXML(str));
		}
		System.out.println("---");

		for (String str : example) {
			System.out.println(classifier.classifyToString(str, "xml", true));
		}
		System.out.println("---");

		for (String str : example) {
			System.out.print(classifier.classifyToString(str, "tsv", false));
		}
		System.out.println("---");

		// This gets out entities with character offsets
		int j = 0;
		for (String str : example) {
			j++;
			List<Triple<String,Integer,Integer>> triples = classifier.classifyToCharacterOffsets(str);
			for (Triple<String,Integer,Integer> trip : triples) {
				System.out.printf("%s over character offsets [%d, %d) in sentence %d.%n",
						trip.first(), trip.second(), trip.third, j);
			}
		}
		System.out.println("---");

		// This prints out all the details of what is stored for each token
		int i=0;
		for (String str : example) {
			for (List<CoreLabel> lcl : classifier.classify(str)) {
				for (CoreLabel cl : lcl) {
					System.out.print(i++ + ": ");
					System.out.println(cl.toShorterString());
				}
			}
		}

		System.out.println("---");
	}

}
;