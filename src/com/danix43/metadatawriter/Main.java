package com.danix43.metadatawriter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
	public static void main(String[] args) {
		Path path = Paths.get("C:\\Users\\PC\\Desktop\\20200104_143806.jpg");
		DataHandler data = new DataHandler(path);
		data.printMetadata();
		try {
			data.logToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
