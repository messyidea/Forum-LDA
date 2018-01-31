package forumlda;

import java.util.ArrayList;

import common.FileUtil;

public class ModelParams {
	int K;
	int U;
	float alpha;
	float beta;
	int iteration;
	
	public void parseFromFile(String filename) {
		ArrayList<String> lines = new ArrayList<String>();
		FileUtil.readLines(filename, lines);
		for(int i = 0; i < lines.size(); i ++) {
			String[] strArr = lines.get(i).split(":", 2);
			String key = strArr[0].trim();
			String value = strArr[1].trim();
			switch(key) {
			case "topics":
				this.K = Integer.parseInt(value);
				break;
			case "alpha":
				this.alpha = Float.parseFloat(value);
				break;
			case "beta":
				this.beta = Float.parseFloat(value);
				break;
			case "iteration":
				this.iteration = Integer.parseInt(value);
				break;
			default:
				System.out.println("Unknow param: " + key);
			}
		}
	}
}
