package forumlda;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

public class ForumLdaMain {
	public static void main(String args[]) throws Exception {
		
		String base = System.getProperty("user.dir") + "/data/";
		String dataDir = base + "/forumdata/";
		String resDir = base + "/modelres/";
		String modelParas = base + "/modelParameters.txt";
		String stopWordsFile = base + "/stopwords.txt";
		
		Options options = new Options();
		options.addOption("h", false, "display help");
		options.addOption("datadir", true, "forum data directory");
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		
	}
}
