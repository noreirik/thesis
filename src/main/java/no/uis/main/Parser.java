package no.uis.main;

import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * TODO
 */
public abstract class Parser {

	private static final Logger logger = LogManager.getLogger(Parser.class);
	
	private ERCSVReader reader;
	private List<String[]> records;
	
	public Parser(Character separator, Character quoteChar) {
		reader = new ERCSVReader(separator, quoteChar);
	}
	
	public abstract <T> void parse(String fileName, Collection<T> output);
	
	public ERCSVReader getReader() {
		return reader;
	}
	public void setRecords(List<String[]> records) {
		this.records = records;
	}
	public List<String[]> getRecords() {
		return this.records;
	}
}
