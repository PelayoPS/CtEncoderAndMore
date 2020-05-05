package util.files;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A utility class to read/write text lines from/to a text file
 */
public class FileUtil extends BaseFileUtil {

	public FileUtil() {
		super();
	}
	
	@Override
	protected BufferedReader createReaderChain(String inFileName) 
			throws FileNotFoundException {
		return new BufferedReader(
				new FileReader(inFileName));
	}


	@Override
	protected BufferedWriter createWriterChain(String outFileName) 
			throws IOException {
		return new BufferedWriter(
				new FileWriter(outFileName,true));
	}

}
