package forumlda;

import java.util.ArrayList;

public class Model {
	ArrayList<Post> posts;
	
	int T, S, V, U;
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
	
	int[][] countUT;
	int[][] countUS;
	int[][] countZ;
	
	float[][] sphi;
	int[][] countWS;
	
	float[][] tphi;
	int[][] countWT;

	
	public Model(ModelParams modelParams, ArrayList<Post> posts) {
		// TODO Auto-generated constructor stub
		this.posts = posts;
		
		this.T = modelParams.K;
		this.S = modelParams.S;
		this.V = modelParams.V;
		this.U = modelParams.U;
		
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
		this.countUT = new int[U][T];
		for (int i = 0; i < U; ++i) {
			for (int j = 0; j < T; ++j) {
				this.uttheta[i][j] = 0;
				this.countUT[i][j] = 0;
			}
		}
		
		this.ustheta = new float[U][S];
		this.countUS = new int[U][S];
		for (int i = 0; i < U; ++i) {
			for (int j = 0; j < T; ++j) {
				this.ustheta[i][j] = 0;
				this.countUS[i][j] = 0;
			}
		}
		
		this.ztheta = new float[U][T];
		this.countZ = new int[U][T];
		for (int i = 0; i < U; ++i) {
			for (int j = 0; j < T; ++j) {
				this.ztheta[i][j] = 0;
				this.countZ[i][j] = 0;
			}
		}
		
		this.sphi = new float[S][V];
		this.countWS = new int[S][V];
		for (int i = 0; i < S ; ++i) {
			for (int j = 0; j < V; ++j) {
				this.sphi[i][j] = 0;
				this.countWS[i][j] = 0;
			}
		}
		
		this.tphi = new float[T][V];
		this.countWT = new int[T][V];
		for (int i = 0; i < T ; ++i) {
			for (int j = 0; j < V; ++j) {
				this.tphi[i][j] = 0;
				this.countWT[i][j] = 0;
			}
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
				 }
			 }
			
		}
		System.out.println("End init.");
	}

}
