package forumlda;

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
		
		ModelParams mp = new ModelParams();
		mp.parseFromFile(modelParamsFile);
		
		
		
	}
	
	private static void getParamFromFile() {
		
	}
}
