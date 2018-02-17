package forumlda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Model {
	ArrayList<Post> posts;
	
	int T, S, V, U, P;
	int nIter;
	
	float[] talpha;
	float talphaSum;
	float[] salpha;
	float salphaSum;
	float[] zalpha;
	float zalphaSum;
	
	float[] tbeta;
	float tbetaSum;
	float[] sbeta;
	float sbetaSum;
	
	float[] gamma;
	float gammaSum;
	
	boolean[][] x;
	short[][] zw; // z word
	short[][] zr; // z reply
	short[][] y;
	
	float[][] uttheta;
	float[][] ustheta;
	float[][] ztheta;
	
	float[][] sphi;
	float[][] tphi;
	
	int[][] countPTW;  // only for root post
	int[][] countTVW;
	int[][] countPTR;
	int[] countTW;
	int[] countSW;
	int[][] countU2R;
	int[][] countUTW;
	int[][] countUSW;
	int[][] countU2W;
	int[][] countSVW;
	

//	int[][] countUT;
//	int[][] countUS;	// u， s replay的数量
//	int[][] countZ;		// post topic count
//	
//	int[][] countU;	// u， 2 reply的数量（0 or 1）
//	
//	float[][] sphi;
//	int[][] countWS;
//	
//	float[][] tphi;
//	int[][] countWT; // words belong to T
//	
//	int[] countT;	// all word belong to T
//	
//	int[][][] countUTSTW; // user type max(st) (word)
//	int[][] countUTW;	// user type (word)
//	
//	int[][] countSVW;
//	int[] countSW;
//	
//	int[][] countTVW;
//	int[] countTW;
//	
////	int[][] countPT;	// post topic count

	
	public Model(ModelParams modelParams, ArrayList<Post> posts) {
		// TODO Auto-generated constructor stub
		this.posts = posts;
		
		this.T = modelParams.K;
		this.S = modelParams.S;
		this.V = modelParams.V;
		this.U = modelParams.U;
		this.P = this.posts.size();
		
		this.talpha = new float[T];
		this.talphaSum = 0;
		for (int i = 0; i < T; ++i) {
			this.talpha[i] = modelParams.alpha;
			this.talphaSum += this.talpha[i];
		}
		
		this.salpha = new float[S];
		this.salphaSum = 0;
		for (int i = 0; i < T; ++i) {
			this.salpha[i] = modelParams.alpha;
			this.salphaSum += this.salpha[i];
		}
		
		this.zalpha = new float[T];
		this.zalphaSum = 0;
		for (int i = 0; i < T; ++i) {
			this.zalpha[i] = modelParams.alpha;
			this.zalphaSum += this.zalpha[i];
		}
		
		this.tbeta = new float[V];
		this.tbetaSum = 0;
		for (int i = 0; i < V; ++i) {
			this.tbeta[i] = modelParams.beta;
			this.tbetaSum += this.tbeta[i];
		}
		
		this.sbeta = new float[V];
		this.sbetaSum = 0;
		for (int i = 0; i < V; ++i) {
			this.sbeta[i] = modelParams.beta;
			this.sbetaSum += this.sbeta[i];
		}
		
		this.gamma = new float[2];
		this.gammaSum = 0;
		for (int i = 0; i < 2; ++i) {
			this.gamma[i] = modelParams.gamma;
			this.gammaSum += this.gamma[i];
		}
		
		
		this.uttheta = new float[U][T];
		this.countUTW = new int[U][T];
		for (int i = 0; i < U; ++i) {
			for (int j = 0; j < T; ++j) {
				this.uttheta[i][j] = 0;
				this.countUTW[i][j] = 0;
			}
		}
		
		this.ustheta = new float[U][S];
		this.countUSW = new int[U][S];
		for (int i = 0; i < U; ++i) {
			for (int j = 0; j < S; ++j) {
				this.ustheta[i][j] = 0;
				this.countUSW[i][j] = 0;
			}
		}
		
		this.ztheta = new float[P][T];
		this.countPTW = new int[P][T];
		this.countPTR = new int[P][T];
		for (int i = 0; i < P; ++i) {
			for (int j = 0; j < T; ++j) {
				this.ztheta[i][j] = 0;
				this.countPTW[i][j] = 0;
				this.countPTR[i][j] = 0;
			}
		}
		
		this.sphi = new float[S][V];
		this.countSVW = new int[S][V];
		for (int i = 0; i < S ; ++i) {
			for (int j = 0; j < V; ++j) {
				this.sphi[i][j] = 0;
				this.countSVW[i][j] = 0;
			}
		}
		
		this.tphi = new float[T][V];
		this.countTVW = new int[T][V];
		for (int i = 0; i < T ; ++i) {
			for (int j = 0; j < V; ++j) {
				this.tphi[i][j] = 0;
				this.countTVW[i][j] = 0;
			}
		}
		
		this.countU2R = new int[U][2];
		this.countU2W = new int[U][2];
		for (int i = 0; i < U; ++i) {
			for (int j = 0; j < 2; ++j) {
				this.countU2R[i][j] = 0;
				this.countU2W[i][j] = 0;
			}
		}
		
		this.countTW = new int[T];
		for (int i = 0; i < T; ++i) {
			this.countTW[i] = 0;
		}
		
		this.countSW = new int[T];
		for (int i = 0; i < S; ++i) {
			this.countSW[i] = 0;
		}
				
	}
	
	public void intialize() {
		System.out.println("Start init.");
		
		this.zw = new short[this.posts.size()][];
		this.zr = new short[this.posts.size()][];
		this.x = new boolean[this.posts.size()][];
		
		for (int i = 0; i < this.posts.size(); ++i) {
			Post post = posts.get(i);
			zw[i] = new short[post.contents.get(0).content.length];
			zr[i] = new short[post.contents.size() - 1];
			x[i] = new boolean[post.contents.size() - 1];
			
			Content rootPost = post.contents.get(0);
			for(int j = 0; j < rootPost.content.length; ++j) {
				double rand = Math.random();
				double thred = 0;
				short tp = 0;
				for(short a = 0; a < T; ++a) {
					thred += (double) 1.0 / T;
					if (thred >= rand) {
						tp = a;
						break;
					}
				}
				zw[i][j] = tp;
				
				countPTW[i][tp] ++;
				countTVW[tp][rootPost.content[j]] ++;
				countTW[tp] ++;
				countU2W[rootPost.author][0] ++;
				countUTW[rootPost.author][z] ++;
				
//				countZ[i][tp] ++;
//				countT[tp] ++;
//				countWT[tp][rootPost.content[j]] ++;
				// count 
				// ...
			}
			 for (int j = 1; j < post.contents.size(); ++j) {
				 Content reply = post.contents.get(j);
				 double rand = Math.random();
				 boolean bufferX;
				 if (rand > 0.5) {
					 bufferX = true;
				 } else {
					 bufferX = false;
				 }
				 
				 x[i][j-1] = bufferX;
				 if (bufferX == true) {
					 rand = Math.random();
					 double thred = 0;
					 short tp = 0;
					 for (short a = 0; a < T; ++a) {
						 thred += (double) 1.0 / T;
						 if (thred > rand) {
							 tp = a;
							 break;
						 }
					 }
					 zr[i][j-1] = tp;
					 
					 countPTR[i][tp] ++;
					 countU2R[reply.author][1] ++;
					 
					 for (int k = 0; k < reply.content.length; ++k) {
						 int word = reply.content[k];
						 countTVW[tp][word] ++;
						 countTW[tp] ++;
						 countU2W[reply.author][1] ++;
						 countUTW[reply.author][tp] ++;
					 }
//					 countUT[i][tp] ++;
				 } else {
					 rand = Math.random();
					 double thred = 0;
					 short tp = 0;
					 for (short a = 0; a < S; ++a) {
						 thred += (double) 1.0 / S;
						 if (thred > rand) {
							 tp = a;
							 break;
						 }
					 }
					 zr[i][j-1] = tp;
					 
					 countU2R[reply.author][0] ++;
//					 countUS[i][tp] ++;
					 for (int k = 0; k < reply.content.length; ++k) {
						 int word = reply.content[k];
						 countSVW[tp][word] ++;
						 countSW[tp] ++;
						 countU2W[reply.author][0] ++;
						 countUSW[reply.author][tp] ++;
						 
					 }
				 }
			 }
			
		}
		System.out.println("End init.");
	}
	
	public void estimate() {
		int niter = 0;
		
		while (true) {
			niter ++;
			oneIter();
			
			if (niter >= nIter) {
				updateDistribution();
				break;
			}
		}
	}
	
	private void updateDistribution() {
		// TODO Auto-generated method stub
		
	}

	public void oneIter() {
		
		for (int i = 0; i < this.posts.size(); ++i) {
			Post post = posts.get(i);			
			Content rootPost = post.contents.get(0);
			for(int j = 0; j < rootPost.content.length; ++j) {
				sampleRootWords(i, j, rootPost.content[j]);
			}
			for (int j = 1; j < post.contents.size(); ++j) {
				 // do sth
				sampleReply(i, j-1, post.contents.get(j));
			}
			
		}
	}

	private void sampleReply(int p, int w, Content content) {
		// TODO Auto-generated method stub
		boolean rstX = x[p][w];
		short rstZ = zr[p][w];
		
		for (int i = 0; i < content.content.length; ++i) {
			int word = content.content[i];
			if (rstX == false) {
				countSVW[rstZ][word] --;
				countSW[rstZ] --;
				countU2W[content.author][0] --;
				countUSW[content.author][rstZ] --;
			} else {
				countTVW[rstZ][word] --;
				countTW[rstZ] --;
				countU2W[content.author][1] --;
				countUTW[content.author][rstZ] --;
			}
		}
		
		// -- sample
		if (rstX == false) {
			countU2R[content.author][0] --;
		} else {
			countU2R[content.author][1] --;
		}
		
		int rst = drawReply(p, w, content);
		
		if (rst < S) {
			rstX = false;
			rstZ = (short)rst;
		} else {
			rstX = true;
			rstZ = (short)(rst - S);
		}
		
		// recover
		for (int i = 0; i < content.content.length; ++i) {
			int word = content.content[i];
			if (rstX == false) {
				countSVW[rstZ][word] --;
				countSW[rstZ] --;
				countU2W[content.author][0] --;
				countUSW[content.author][rstZ] --;
			} else {
				countTVW[rstZ][word] --;
				countTW[rstZ] --;
				countU2W[content.author][1] --;
				countUTW[content.author][rstZ] --;
			}
		}
		
		if (rstX == false) {
			countU2R[content.author][0] --;
		} else {
			countU2R[content.author][1] --;
		}

	}

	private int drawReply(int p, int w, Content content) {
		// TODO Auto-generated method stub
		int word;
		
		HashMap<Integer, Integer> wordCnt = new HashMap<Integer, Integer>();
		for (int i = 0; i < content.content.length; ++i) {
			word = content.content[i];
			if (!wordCnt.containsKey(word)) {
				wordCnt.put(word, 1);
			} else {
				int count = wordCnt.get(word) + 1;
				wordCnt.put(word, count);
			}
		}
		
		double[] topicP;
		topicP = new double[S+T];
		int u = content.author;
		
		for(int i = 0; i < S; ++i) {
			topicP[i] = (countU2R[u][0] + gamma[0]) 
					* (countUSW[u][i] + salpha[i])
					/ (countU2W[u][0] + salphaSum);
			
			int t = 0;
			Set s = wordCnt.entrySet();
			Iterator it = s.iterator();
			double bufferP = 0;
			while(it.hasNext()) {
				Map.Entry m = (Map.Entry) it.next();
				word = (Integer) m.getKey();
				int count = (Integer) m.getValue();
				for (int j = 0; j < count; ++j) {
					double value = (countSVW[i][word] + sbeta[word] + j)
							/ (countSW[i] + sbetaSum + t);
					t ++;
					bufferP *= value;
				}
			}
			topicP[i] *= Math.pow(bufferP, 1.0);
		}
		
		for (int i = 0; i < T; ++i) {
			// lost some thing
			topicP[S + i] = (countU2R[u][1] + gamma[1]) 
					* (countUTW[u][i] + talpha[i])
					/ (countU2W[u][1] + talphaSum);
			
			int t = 0;
			Set s = wordCnt.entrySet();
			Iterator it = s.iterator();
			double bufferP = 0;
			while(it.hasNext()) {
				Map.Entry m = (Map.Entry) it.next();
				word = (Integer) m.getKey();
				int count = (Integer) m.getValue();
				for (int j = 0; j < count; ++j) {
					double value = (countTVW[i][word] + tbeta[word] + j)
							/ (countTW[i] + tbetaSum + t);
					t ++;
					bufferP *= value;
				}
			}
			topicP[S + i] *= Math.pow(bufferP, 1.0);
		}
		
		for (int i = 1; i < T+S; ++i) {
			topicP[i] += topicP[i-1];
		}
		double rand = Math.random() * topicP[T+S-1];
		int rst = 0;
		for (int i = 0; i < T+S; ++i) {
			if (topicP[i] >= rand) {
				rst = i;
				break;
			}
		}
		
		return rst;
	}

	private void sampleRootWords(int i, int j, int word) {
		// TODO Auto-generated method stub
		short z = zw[i][j];
		countPTW[i][z] --;
		countTVW[z][word] --;
		countTW[z] --;
		
		z = drawZ(i, j, word);
		
		countPTW[i][z] ++;
		countTVW[z][word] ++;
		countTW[z] ++;

	}

	private short drawZ(int p, int w, int word) {
		// TODO Auto-generated method stub
		double[] topicP;
		topicP = new double[T];
		
		for (int i = 0; i < T; ++i) {
			topicP[i] = (countPTW[p][i] + countPTR[p][i] + zalpha[i]) 
					/ (posts.get(p).contents.get(0).content.length + posts.get(p).contents.size() - 1 - 2 + zalphaSum)
					* (countTVW[i][word] + tbeta[i]) 
					/ (countTW[i] + tbetaSum);
			
		}
		
		for (int i = 1; i < T; ++i) {
			topicP[i] += topicP[i-1];
		}
		
		double rand = Math.random() * topicP[T-1];
		short topic = 0;
		for (short i = 0; i < T; ++i) {
			if(rand <= topicP[i]) {
				topic = i;
				break;
			}
		}
		
		return topic;
	}

}
