package util;

import java.io.BufferedReader;


import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/**
 * A utility class to read/write text lines 
 * from/to a compressed text file (.txt.gz) 
 */
public class ZipFileUtil extends BaseFileUtil {

	public ZipFileUtil() {
		super();
	}
	
	protected BufferedReader createReaderChain(String file) 
			throws IOException {
		return new BufferedReader(
			    new InputStreamReader(
			     new GZIPInputStream(
			      new FileInputStream(file))));
	}
	
	protected BufferedWriter createWriterChain(String file) 
			throws FileNotFoundException {
		try {
			return new BufferedWriter(
				    new OutputStreamWriter(
				     new GZIPOutputStream(
				      new FileOutputStream(file))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

}
