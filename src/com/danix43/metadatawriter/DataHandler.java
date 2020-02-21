package com.danix43.metadatawriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

/**
 * TODO: - Project stalled because the library used in reading metadata doesn't
 * support writing - Look up for alternatives or put on the vault
 * 
 * @author Danix43 <daninitu39@gmail.com>
 */
public class DataHandler {

	private static final Logger LOGGER = Logger.getLogger(DataHandler.class.getName());
	private Metadata METADATA;
	private Path PATH_TO_FILE;

	public DataHandler(Path pathToFile) {
		this.PATH_TO_FILE = pathToFile;
		try {
			this.METADATA = ImageMetadataReader.readMetadata(PATH_TO_FILE.toFile());
		} catch (IOException | ImageProcessingException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}

	public void printMetadata() {
		for (Directory directory : METADATA.getDirectories()) {
			System.out.println();
			System.out.println("Directory: " + directory);

			for (Tag tag : directory.getTags()) {
				System.out.println("Tag: " + tag);
			}

			for (String error : directory.getErrors()) {
				System.err.println("Error: " + error);
			}
		}
	}

	public void logToFile() throws IOException {
		String name = PATH_TO_FILE.toString().replace(getFileExtension(PATH_TO_FILE.toFile()), "log");
		Path file = Files.createFile(Paths.get(name));
		try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {

			for (Directory directory : METADATA.getDirectories()) {
				writer.write(" ");
				writer.write("Directory: " + directory);

				for (Tag tag : directory.getTags()) {
					writer.write("Tag: " + tag);
				}

				for (String error : directory.getErrors()) {
					writer.write("Error: " + error);
				}
			}
		} catch (IOException e) {
			LOGGER.warning(e::getMessage);
		}
	}

	private String getFileExtension(File file) {
		String name = file.getName();
		if (name.lastIndexOf('.') != -1 && name.lastIndexOf('.') != 0) {
			return name.substring(name.lastIndexOf('.') + 1);
		} else {
			return "";
		}
	}

}
