package forumlda;

import java.util.ArrayList;

import common.FileUtil;

public class ModelParams {
	int T;
	int S;
	int U;
	int V;
	int P;

	float alpha;
	float beta;
	float gamma;
	
	int iteration;
	
	public void parseFromFile(String filename) {
		ArrayList<String> lines = new ArrayList<String>();
		FileUtil.readLines(filename, lines);
		for(int i = 0; i < lines.size(); i ++) {
			String[] strArr = lines.get(i).split(":", 2);
			String key = strArr[0].trim();
			String value = strArr[1].trim();
			switch(key) {
			case "serious_topics":
				this.T = Integer.parseInt(value);
				break;
			case "unserious_topics":
				this.S = Integer.parseInt(value);
				break;
			case "alpha":
				this.alpha = Float.parseFloat(value);
				break;
			case "beta":
				this.beta = Float.parseFloat(value);
				break;
			case "gamma":
				this.gamma = Float.parseFloat(value);
				break;
			case "iteration":
				this.iteration = Integer.parseInt(value);
				break;
			default:
				System.out.println("Unknow param: " + key);
			}
		}
	}

	public void getExtraParams(int wordNum, int authorNum, int postNum) {
		// TODO Auto-generated method stub
		this.U = authorNum;
		this.V = wordNum;
		this.P = postNum;
	}
}
