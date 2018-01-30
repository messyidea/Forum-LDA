package common;

import java.io.File;

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
	
	
}
