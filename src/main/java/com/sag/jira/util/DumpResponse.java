package com.sag.jira.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DumpResponse {
	private String FILENAME = "";

	public DumpResponse(String filepath) {
		this.FILENAME = "responses/" + filepath;
		initialize();
	}

	static PrintWriter printWriter;

	public void initialize() {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(FILENAME);
			printWriter = new PrintWriter(fileWriter);
		} catch (IOException e) {
		}

	}

	public void dump(String data) {
		printWriter.print(data);
		printWriter.println();
	}

	public void dump(List<String> data) {
		for (String object : data) {
			printWriter.print(data);
		}
	}

	public void close() {
		printWriter.close();

	}
}