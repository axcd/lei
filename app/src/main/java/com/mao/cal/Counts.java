package com.mao.cal;

public class Counts
{
	private int[][] counts;
	private int n;

	public Counts(int[][] counts){
		setCounts(counts);
		setN(counts.length*counts[0].length);
		set();
	}

	public void setN(int n)
	{
		this.n = n;
	}

	public int getN()
	{
		return n;
	}

	public void setCounts(int[][] counts)
	{
		this.counts = counts;
	}

	public int[][] getCounts()
	{
		return counts;
	}


	private void set()
	{
		for(int i=0;i<counts.length;i++)
		{
			for(int j=0;j<counts[0].length;j++)
			{
			    int x = (int)(Math.random()*6);
				if(x==1)
				{
					n--;
					counts[i][j] = -1;

					if(i-1>=0&&j-1>=0&&counts[i-1][j-1]!=-1) 
						counts[i-1][j-1]++;

					if(i-1>=0&&counts[i-1][j]!=-1)
						counts[i-1][j]++;

					if(i-1>=0&&j+1<counts[0].length&&counts[i-1][j+1]!=-1)
						counts[i-1][j+1]++;

					if(j-1>=0&&counts[i][j-1]!=-1) 
						counts[i][j-1]++;

					if(j+1<counts[0].length&&counts[i][j+1]!=-1) 
						counts[i][j+1]++;

					if(i+1<counts.length&&j-1>=0&&counts[i+1][j-1]!=-1) 
						counts[i+1][j-1]++;

					if(i+1<counts.length&&counts[i+1][j]!=-1) 
						counts[i+1][j]++;

					if(i+1<counts.length&&j+1<counts[0].length&&counts[i+1][j+1]!=-1)
						counts[i+1][j+1]++;				
				}
			}
		}
	}

}
