package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtil {
	public static void makeDir(String dirName) {
		boolean result = false;
		File file = new File(dirName);
		try {
			result = file.exists();
			if (result) {
				System.out.println("Folder exists.");
			} else {
				result = file.mkdirs();
				if (result) {
					System.out.println("Create folder successfully");
				} else {
					System.out.println("Error on create folder");
				}
			}
		} catch (Exception err) {
			System.err.println("unexcepted error");
			err.printStackTrace();
		}
	}
	
	public static void readLines(String file, ArrayList<String> lines) {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(new File(file)));
			String line = null;
			while((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
