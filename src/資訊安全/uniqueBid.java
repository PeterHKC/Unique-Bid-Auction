package 資訊安全;

import java.util.Arrays;

public class uniqueBid {
	
	
	/*
	 * testing all components
	 */
	public static void main(String[] args) 
	{
		double pass = 0;
		int n = 10000;
		int good = 50;
		int bad = 100-good;
		int max = 100;
		int min = 1;
		int interval = 10;
		/*
		 * setBidders
		 * arg1: # of bidders
		 * arg2: ratio of good bidders
		 * arg3: interval of the bucket
		 * arg4: max price offered by bidders
		 * arg5: min price offered by bidders
		 */
		for(int i = 0; i < n; i++)
		{
			Bidder bidder = new Bidder();
			
			bidder.setBidders(good, 1, interval, max, min);
			bidder.attack(interval, max, min, bad);
			pass+=(double)bidder.showResult();
			//bidder.showBucket("number");
		}
		
		System.out.println("good/all: "+pass/n);
	}
}
