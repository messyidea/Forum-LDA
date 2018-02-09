package forumlda;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import common.FileUtil;

public class ForumLdaMain {
	public static void main(String args[]) throws Exception {
		
		// default parameters
		String base = System.getProperty("user.dir") + "/data/";
		String dataDir = base + "/forumdata/";
		String dataFile = dataDir + "data.txt";
		String resDir = base + "/modelres/";
		String modelParamsFile = base + "/modelParameters.txt";
		String stopWordsFile = base + "/stopwords.txt";
		
		//CLI parse
		Options options = new Options();
		options.addOption("h", false, "display help");
		options.addOption("datadir", true, "forum data directory");
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		
		if (cmd.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("Forum LDA", options);
			System.exit(0);
		}
		
		if (cmd.hasOption("datedir")) {
			dataDir = cmd.getOptionValue("datedir");
		}
		
		//Init
		FileUtil.makeDir(resDir);
		
		ModelParams modelParams = new ModelParams();
		modelParams.parseFromFile(modelParamsFile);
		
		HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
		ArrayList<String> wordList = new ArrayList<String>();
		HashMap<String, Integer> authorMap = new HashMap<String, Integer>();
		ArrayList<String> authorList = new ArrayList<String>();
		
		ArrayList<Post> posts = new ArrayList<Post>();
		readPostFromFile(dataFile, posts, wordMap, wordList, authorMap, authorList);
		
		int wordNum = wordList.size();
		int authorNum = authorList.size();
		int postNum = posts.size();
		modelParams.getExtraParams(wordNum, authorNum, postNum);
		
		Model m = new Model(modelParams, posts);
		
	}
	
	public static void readPostFromFile(String filename, ArrayList<Post> posts, HashMap<String, Integer> wordMap, 
			ArrayList<String> wordList, HashMap<String, Integer> authorMap, ArrayList<String> authorList) {
		ArrayList<String> lines = new ArrayList<String>();
		FileUtil.readLines(filename, lines);
		
		ArrayList<String> tPost = new ArrayList<String>();
		for (int i = 0; i < lines.size(); i ++) {
			String line = lines.get(i);
			if (line.startsWith("-") || line.length() == 0) {
				Post post = new Post(tPost, wordMap, wordList, authorMap, authorList);
				if(post.id != -1) {
					posts.add(post);
				}
				continue;
			} 
			tPost.add(line);
		}
	}
	
}
