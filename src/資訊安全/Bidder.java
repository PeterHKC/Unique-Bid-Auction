package 資訊安全;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Bidder {
	private int n;
	private int price;
	private int interval;
	private int name;
	private boolean id;
	private Bidder[] pointer = null;
	private HashMap<Integer, ArrayList<Bidder>> bucket = new HashMap<Integer, ArrayList<Bidder>>();
	private int bucketNum;
	private int bidnumber;
	
	Bidder(){}
	
	Bidder(int name)
	{
		Random ran = new Random();
		this.id = ran.nextBoolean();
		this.price = ran.nextInt(100)+1;
		this.name = name;
	}
	Bidder(boolean id, int name)
	{
		Random ran = new Random();
		this.id = id;
		this.price = ran.nextInt(100)+1;
		this.name = name;
	}
	Bidder(boolean id, int max, int min, int name)
	{
		Random ran = new Random();
		this.id = id;
		this.price = ran.nextInt(max)+min;
		this.name = name;
	}
	Bidder(boolean id, int price, int name)
	{
		this.id = id;
		this.price = price;
		this.name = name;
	}
	public void flatAttack(int interval, int max, int min, int n)
	{
		int count = 0;
		Bidder bidder[] = new Bidder[n];
		setBucket(interval, max, min);
		int badnum = (n/this.bucketNum)!=0 ? n/this.bucketNum : 1; 
		for(int i = 0; i < this.bucketNum; i++)
		{
			for(int j = 0; j < badnum && count < n; j++)
			{
				bidder[j] = new Bidder(false, i*this.interval,count);
				int price = bidder[j].getPrice();
				int index = price/this.interval;
				int key = index > bucketNum-1 ? index*this.interval : (index+1)*this.interval;
				if(this.bucket.get(key) == null)
				{
					ArrayList<Bidder>  bidders = new ArrayList<Bidder>();
					bidders.add(bidder[j]);
					this.bucket.put(key, bidders);
				}
				else
				{
					ArrayList<Bidder>  bidders = new ArrayList<Bidder>(this.bucket.get(key));
					bidders.add(bidder[j]);
					this.bucket.put(key, bidders);
				}
				count++;
			}
		}
		
	}
	public void onepriceAttack(int interval, int max, int min, int n)
	{
		int count = 0;
		Bidder bidder[] = new Bidder[n];
		setBucket(interval, max, min);
		
		for(int i = 0; i < this.bucketNum; i++)
		{
			for(int j = 0; j < n && count <= n; j++)
			{
				bidder[j] = new Bidder(false, i*this.interval,count);
				int price = bidder[j].getPrice();
				int index = price/this.interval;
				int key = index > bucketNum-1 ? index*this.interval : (index+1)*this.interval;
				if(this.bucket.get(key) == null)
				{
					ArrayList<Bidder>  bidders = new ArrayList<Bidder>();
					bidders.add(bidder[j]);
					this.bucket.put(key, bidders);
				}
				else
				{
					ArrayList<Bidder>  bidders = new ArrayList<Bidder>(this.bucket.get(key));
					bidders.add(bidder[j]);
					this.bucket.put(key, bidders);
				}
				count++;
			}
		}
	}
	public void steppedAttack(int interval, int max, int min, int n)
	{
		int count = 0;
		Bidder bidder[] = new Bidder[n];
		setBucket(interval, max, min);
		
		for(int i = 0; i < this.bucketNum; i++)
		{
			for(int j = 0; j < i && count < n; j++)
			{
				bidder[j] = new Bidder(false, i*this.interval,count);
				int price = bidder[j].getPrice();
				int index = price/this.interval;
				int key = index > bucketNum-1 ? index*this.interval : (index+1)*this.interval;
				if(this.bucket.get(key) == null)
				{
					ArrayList<Bidder>  bidders = new ArrayList<Bidder>();
					bidders.add(bidder[j]);
					this.bucket.put(key, bidders);
				}
				else
				{
					ArrayList<Bidder>  bidders = new ArrayList<Bidder>(this.bucket.get(key));
					bidders.add(bidder[j]);
					this.bucket.put(key, bidders);
				}
				count++;
			}
		}
		
	}
	public void setBidders(int n, double ratioGood, int interval, int max, int min)
	{
		this.n = n;
		int goodNum = (int) (ratioGood*n);
		int count = 0;
		Bidder bidder[] = new Bidder[n];
		setBucket(interval, max, min);
		
		for(int i = 0; i < n; i++)
		{
			if (count < goodNum)
			{
				bidder[i] = new Bidder(true, max, min,i);
				count++;
			}
			else
			{
				bidder[i] = new Bidder(false, max, min,i);
			}
			int price = bidder[i].getPrice();
			int index = price/this.interval;
			int key = index > bucketNum-1 ? index*this.interval : (index+1)*this.interval;
			
			if(this.bucket.get(key) == null)
			{
				ArrayList<Bidder>  bidders = new ArrayList<Bidder>();
				bidders.add(bidder[i]);
				this.bucket.put(key, bidders);
			}
			else
			{
				ArrayList<Bidder>  bidders = new ArrayList<Bidder>(this.bucket.get(key));
				bidders.add(bidder[i]);
				this.bucket.put(key, bidders);
			}
		}
		this.pointer = bidder;
	}
	
	public void setBucket(int interval, int max, int min)
	{
		double n = (max-min)/interval+1;
		this.bucketNum = (int) Math.ceil(n);
		this.interval = interval;
	}
	
	public int getName(){return this.name;}
	public HashMap<Integer, ArrayList<Bidder>> getBucket(){return this.bucket;}
	public int getPrice(){return price;}
	public boolean getID(){return id;}
	public String getIDString(){return String.valueOf(id);}
	public double showResult(String choose)
	{
		HashMap<Integer, ArrayList<Bidder>> bidders = new HashMap<Integer, ArrayList<Bidder>>(getResultBucket(choose));
		//System.out.print("good/all\t");
		int good = 0;
		int bad = 0;
		for(Integer key: bidders.keySet())
		{
			for(int i = 0; i < bidders.get(key).size(); i++)
			{
				boolean standpoint = bidders.get(key).get(i).getID();
				if(standpoint)
					good++;
				else
					bad++;
			}
		}
		return (double)good/(double)(bad+good);
		
	}
	public void showWinner(String choose)
	{
		HashMap<Integer, ArrayList<Bidder>> bidders = new HashMap<Integer, ArrayList<Bidder>>(getResultBucket(choose));
		System.out.println("winner\tprice\tstandpoint");
		for(Integer key: bidders.keySet())
		{
			for(int i = 0; i < bidders.get(key).size(); i++)
			{
				System.out.print(bidders.get(key).get(i).getName()+"\t"+key+"\t");
				System.out.println(bidders.get(key).get(i).getID()?"good":"bad");
			}
		}
	}
	public HashMap<Integer, ArrayList<Bidder>> getResultBucket(String arg)
	{
		HashMap<Integer, ArrayList<Bidder>> bidders = new HashMap<Integer, ArrayList<Bidder>>();
		int sorted[] = new int[this.bucket.keySet().size()];
		int i = 0;
		for(Integer key: this.bucket.keySet())
		{
			if(this.bucket.get(key).size() == 0)
			{
				sorted[i] = 65535;
			}
			else
			{
				sorted[i] = this.bucket.get(key).size();
			}
			i++;
		}
		Arrays.sort(sorted);
		if(arg == "high")
			this.bidnumber = sorted[sorted.length-1];
		else if(arg == "low")
			this.bidnumber = sorted[0];
		
		for(Integer key: this.bucket.keySet())
		{
			if(this.bucket.get(key).size() == this.bidnumber)
			{
				bidders.put(key, this.bucket.get(key));
			}
		}
		return bidders;
	}
	
	public void showBidder()
	{
		int n = this.n;
		for (int i = 0; i < n; i++)
		{
			System.out.println(this.pointer[i].getName()+"\t"+this.pointer[i].getIDString() +" " + this.pointer[i].getPrice());
		}
	}
	
	public void showBucket(String arg, String choose)
	{
		if(arg == "number")
		{
			int count = 0;
			System.out.println("price\tnumber");
			for(Integer key: this.bucket.keySet())
			{
				int temp = this.bucket.get(key).size();
				count += temp;
				System.out.println(key+"\t" + temp);
			}
			System.out.println("total number of bidders = "+count);
		}
		else if(arg == "result")
		{
			HashMap<Integer, ArrayList<Bidder>> bidders = new HashMap<Integer, ArrayList<Bidder>>(getResultBucket(choose));
			
			System.out.println("price\tnumber");
			for(Integer i: bidders.keySet())
			{
				System.out.println(i+"\t"+bidders.get(i).size());
			}
		}		
		else if (arg == "good" || arg == "bad")
		{
			int totalgoodman = 0;
			int totalbadman = 0;
			System.out.print("price\t");
			System.out.print(arg == "good"?"good":"bad");
			System.out.println("\ttotal");
			for(Integer key: this.bucket.keySet())
			{
				int goodcount = 0;
				int badcount = 0;
				System.out.print(key+"\t");
				ArrayList<Bidder> bidders = new ArrayList<Bidder>(this.bucket.get(key));
				for(int j = 0; j < bidders.size(); j++)
				{
					if(bidders.get(j).getID() == true)
					{
						goodcount++;
						totalgoodman++;
					}
					else
					{
						badcount++;
						totalbadman++;
					}
				}
				System.out.print(arg == "good"?goodcount:badcount);
				System.out.println("\t"+bidders.size());
			}
			System.out.print("number of total "+arg+" man: ");
			System.out.println(arg == "good"?totalgoodman:totalbadman);
		}
	}
}
