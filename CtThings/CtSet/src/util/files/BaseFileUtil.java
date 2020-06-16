package util.files;

import java.io.BufferedReader;


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public abstract class BaseFileUtil {
	
	public BaseFileUtil() {
	}
	
	public List<String> readLines(String inFileName) 
			throws FileNotFoundException {
		List<String> res = new LinkedList<>();
		String line = null;
		try {
			BufferedReader br = createReaderChain(inFileName);
			try {
				line = br.readLine();
		    	while (line != null) {
					res.add(line);
					line = br.readLine();
				}
			} finally {
				br.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
		return res;
	}

	public void writeLines(String outFileName, List<String> lines) {
		try {
			BufferedWriter out = createWriterChain(outFileName);
			try {
				for (String line : lines) {
					out.write(line);
				}
			} finally {
				out.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected abstract BufferedReader createReaderChain(String inFileName)
			throws IOException;

	protected abstract BufferedWriter createWriterChain(String outFileName) 
			throws IOException;

}