package application;

import java.util.ArrayList;
import java.util.List;

//for package creation for sending data from PC to MCU
public class writeFormat {

	public String showDataGet()
	{
		String dd="";
		for(int i=0;i<wData.size();i++)
		{
			int j=wData.get(i);
			char c=(char)j;
			dd=dd+c;
		}
		
		return dd;
	}
	public List<Integer> wData = new ArrayList<Integer>();
	
	public writeFormat()
	{
		wData.add((int)'\n');
		wData.add((int)'\r');
		//wData.add((int)'');
		//
	}
	public void showData()
	{
		for(int i=0;i<wData.size();i++)
		{
			int j=wData.get(i);
			char c=(char)j;
			System.out.print(c);
		}
	}
	public void stopTN()
	{
		
		wData.add((int)'X');
		wData.add((int)'X');
		wData.add((int)'-');
		wData.add((int)'-');
		wData.add((int)'-');
}
	public void addStart()
	{
		wData.add((int)'S');
		wData.add((int)'T');
		wData.add((int)'A');
		wData.add((int)'R');
		wData.add((int)'T');
		
		
	}
	public void addStartL()
	{
		wData.add((int)'S');
		wData.add((int)'T');
		wData.add((int)'A');
		wData.add((int)'R');
		wData.add((int)'T');
		wData.add((int)'L');
		
	}
	
	
	public void startBpN()
	{
		wData.add((int)'S');
		wData.add((int)'B');
		wData.add((int)'-');
		wData.add((int)'-');
		wData.add((int)'-');
	}
	public void stopBpN()
	{
		wData.add((int)'X');
		wData.add((int)'B');

		wData.add((int)'-');
		wData.add((int)'-');
		wData.add((int)'-');
	}

	public void startWetN(List<Integer> val)
	{
		wData.add((int)'S');
		wData.add((int)'W');

		wData.add(val.get(0));
		wData.add(val.get(1));
		wData.add(val.get(2));
	}
	public void stopWetN()
	{
		wData.add((int)'X');
		wData.add((int)'W');
		wData.add((int)'-');
		wData.add((int)'-');
		wData.add((int)'-');
	}

	public void startDryN()
	{
		wData.add((int)'S');
		wData.add((int)'D');
		wData.add((int)'-');
		wData.add((int)'-');
		wData.add((int)'-');
	}
	public void stopDryN()
	{
		wData.add((int)'X');
		wData.add((int)'D');
		wData.add((int)'-');
		wData.add((int)'-');
		wData.add((int)'-');
	}
	

	public void setValve(char v1,char v2,char v3,char v4)
	{
		wData.add((int)'V');
		wData.add((int)v1);
		wData.add((int)v2);
		wData.add((int)v3);
		wData.add((int)v4);
	}
	
	public void setLatching(char v1,char v2,char v3,char v4,char v5,char v6,char v7,char v8)
	{
		wData.add((int)'L');
		wData.add((int)'V');
		wData.add((int)v1);
		wData.add((int)v2);
		wData.add((int)v3);
		wData.add((int)v4);
		wData.add((int)v5);
		wData.add((int)v6);
		wData.add((int)v7);
		wData.add((int)v8);
		wData.add((int)'1');
		wData.add((int)'1');
		wData.add((int)'1');
		wData.add((int)'1');
		wData.add((int)'1');
		wData.add((int)'1');
		
	}
	
	public void setCrossOver()
	{
		wData.add((int)'C');
		wData.add((int)'O');
		wData.add((int)'F');
		wData.add((int)'1');
		wData.add((int)'0');
	}
	public void addInt(int c)
	{
		wData.add(c);
	}
	public void addChar(char c)
	{
		wData.add((int)c);
	}
	public void addStartd()
	{
		wData.add((int)'S');
		wData.add((int)'T');
		wData.add((int)'A');
		wData.add((int)'R');
		wData.add((int)'T');
		wData.add((int)'D');
		
		
	}
	public void addStartw()
	{
		wData.add((int)'S');
		wData.add((int)'T');
		wData.add((int)'A');
		wData.add((int)'R');
		wData.add((int)'T');
		wData.add((int)'W');
		
		
	}
	public void addStop()
	{
		wData.add((int)'S');
		wData.add((int)'T');
		wData.add((int)'O');
		wData.add((int)'P');
	
	}
	public void addStopL()
	{
		wData.add((int)'S');
		wData.add((int)'T');
		wData.add((int)'O');
		wData.add((int)'P');
		wData.add((int)'L');
	}
	public void addStopd()
	{
		wData.add((int)'S');
		wData.add((int)'T');
		wData.add((int)'O');
		wData.add((int)'P');
		wData.add((int)'D');
	
	}
	public void addStopw()
	{
		wData.add((int)'S');
		wData.add((int)'T');
		wData.add((int)'O');
		wData.add((int)'P');
		wData.add((int)'W');
	
	}
	public void addData(char nam,List<Integer> vals)
	{
		
		wData.add((int)nam);

		/*if(wData.size()==3)
		{
			wData.add((int)'-');
		}*/
		wData.addAll(vals);
		wData.add((int)'-');
		
	}
	public void addData2(char nam,List<Integer> vals)
	{
		
		wData.add((int)nam);

		/*if(wData.size()==3)
		{
			wData.add((int)'-');
		}*/
		wData.addAll(vals);

		
	}
	public void addData1(List<Integer> vals)
	{
		
	
		/*if(wData.size()==3)
		{
			wData.add((int)'-');
		}*/
		wData.addAll(vals);
		
		
	}
	public void addBlank(int i)
	{
		for(int j=0;j<i;j++)
		{
		wData.add((int)'-');
		}
	}
	public void addLast()
	{
		wData.add((int)'C');
		wData.add((int)'E');
		wData.add((int)'\n');
	}
	public void addChkSm()
	{
		if(wData.size()>10)
		{
			wData.add((int)'O');
			int sum=0;
			for(int i=2;i<11;i++)
			{
				//sum += wData.get(i);
				sum = sum | wData.get(i);
			}
			wData.add(sum);
			//System.out.println("Sum = "+sum);
			//wData.add(Integer.parseInt(Integer.toHexString(sum)));
		}
	}

}
