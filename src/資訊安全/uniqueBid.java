package 資訊安全;

import java.io.IOException;
import java.util.Arrays;

import com.anselm.plm.utilobj.LogIt;

public class uniqueBid {
	
	public static void main(String[] args)
	{
		
		flatResult();
		onepriceResult();
		steppedResult();
		
	}
	public static void steppedResult()
	{
		uniqueBid res = new uniqueBid();
		int totalbidder = 100;
		String choose = "low";
		String attack = "stepped";
		res.run(totalbidder, 10, choose, attack);
		res.run(totalbidder, 50, choose, attack);
		res.run(totalbidder, 100, choose, attack);
		res.run(totalbidder, 500, choose, attack);
	}
	public static void flatResult()
	{
		uniqueBid res = new uniqueBid();
		int totalbidder = 100;
		String choose = "high";
		String attack = "flat";
		res.run(totalbidder, 10, choose, attack);
		res.run(totalbidder, 50, choose, attack);
		res.run(totalbidder, 100, choose, attack);
		res.run(totalbidder, 500, choose, attack);
	}
	public static void onepriceResult()
	{
		uniqueBid res = new uniqueBid();
		int totalbidder = 100;
		String choose = "low";
		String attack = "oneprice";
		res.run(totalbidder, 10, choose, attack);
		res.run(totalbidder, 50, choose, attack);
		res.run(totalbidder, 100, choose, attack);
		res.run(totalbidder, 500, choose, attack);
	}
	/*
	 * testing all components
	 */
	public void run(int totalbidder, int bucketnum, String choose, String attack)
	{
		LogIt log = new LogIt();
		
		
		int total = totalbidder;
		int n = 10000;
		int interval = 10;
		int max = bucketnum*10;
		int min = 1;
		
		try {
			log.setLogFile(attack+choose+bucketnum+".csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * setBidders
		 * arg1: # of bidders
		 * arg2: ratio of good bidders
		 * arg3: interval of the bucket
		 * arg4: max price offered by bidders
		 * arg5: min price offered by bidders
		 */
		log.log("number of buckets = "+bucketnum);
		log.log("attack type = "+attack);
		log.log("dense type = " +choose);
		log.log("input,output\n");
		for(int j = totalbidder/10; j <= totalbidder; j+=(totalbidder/10))
		{
			int good = j;
			int bad = total-j;
			double pass = 0;
			for(int i = 0; i < n; i++)
			{
				Bidder bidder = new Bidder();
				
				bidder.setBidders(good, 1, interval, max, min);
				if(attack == "flat")
					bidder.flatAttack(interval, max, min, bad);
				else if(attack =="oneprice")
					bidder.onepriceAttack(interval, max, min, bad);
				else if(attack == "stepped")
					bidder.steppedAttack(interval, max, min, bad);
				pass+=(double)bidder.showResult(choose);
				//System.out.println(pass);
				//bidder.showBucket("bad","high");
			}
			log.log(good+","+(pass/n)*100+"\n");
		}
	}
}
