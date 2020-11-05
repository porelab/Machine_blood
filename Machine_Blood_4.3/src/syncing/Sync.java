package syncing;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import toast.Toast;
import application.Main;
import application.Myapp;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.OnFailureListener;
import com.google.firebase.tasks.Task;

import data_read_write.DatareadN;
import firebase.FirebaseConnect;

//for sync test file online from local drive
public class Sync {


	Map<String,Object> os=new HashMap<>();
	int ind=0;
	
	SimpleBooleanProperty bol=null;

	List<DatareadN> drlist;
	public Sync(SimpleBooleanProperty bol)
	{
		this.bol=bol;
		
		
	}
	
	public Sync()
	{
		
	}
	
	public   void SyncOnline()
	{  
		try{
		if(bol!=null)
		{
				bol.set(true);
		}
		drlist=new ArrayList<DatareadN>();
		
				//FirebaseConnect.InitApp();
		File f=new File("TableCsvs/"+Myapp.uid);
		if(f.isDirectory())
		{
			f=new File("TableCsvs/"+Myapp.uid);
			if(f.isDirectory())
			{
			
				File[] listOfFiles = f.listFiles();

			    for (int i = 0; i < listOfFiles.length; i++) {
			    //	System.out.println("dire : "+listOfFiles[i].getName());
			      if (listOfFiles[i].isDirectory()) {
			        
			    	 File[] ff=listOfFiles[i].listFiles();
			    	 //System.out.println(ff.length);
			    	 if(ff.length>0)
			    	 {
			    	 Map<String,Object> temp=new HashMap<String, Object>();		
			    	 
			    	 for(int j=0;j<ff.length;j++)
			    	 {
			    		 DatareadN dd=new DatareadN();
			    		 
			    		 
			    		 dd.fileRead(ff[j]);
			    		 
			    		 
			    			if(dd.data!=null)
			    			{
			    				try
			    				{
			    					if(dd.data.containsKey("testdate")&&!dd.data.containsKey("upload"))
			    					{
			    						drlist.add(dd);	
			    					 System.out.println("Sample name : "+listOfFiles[i].getName());
						    		 System.out.println("Test name : "+ff[j].getName().substring(0,ff[j].getName().indexOf('.')));		
						    		 dd.data.put("upload", "yes");
						    		 temp.put(ff[j].getName().substring(0,ff[j].getName().indexOf('.')), dd.data);
			    					}
			    				}
			    				catch(Exception e)
			    				{
			    					System.out.println("sync error22 : "+ff[j]);
			    					
			    				}
			    					
			    					
			    			}
			    			
			    		 
			    		 
			    		
			    		 
			    	//	 System.out.println(" DFLOW : "+dd.getMap().get("dflow"));
			    		 
			    			
			    	//	 FirebaseConnect.ref.child("users").child(id).child(ch).child(listOfFiles[i].getName()).child(ff[j].getName().substring(0,ff[j].getName().indexOf('.'))).setValue("data");
			    		
			    	 }
			    	 if(temp.size()>0)
			    	 {
			    	 os.put(listOfFiles[i].getName(), temp);
			    	 }
			    	 else
			    	 {
			    		 System.out.println("Not upload"+listOfFiles[i].getName());
			    	 }
			    	 
			    	 }
			    	 
			      }
			    }
				
			    
			    List<String> samplenames=new ArrayList<>(os.keySet());
		    	
		    	 System.out.println(samplenames);
		    	 uploadFile(samplenames);
		    	 
		    	 setTimer();
			    
			}
			else
			{
				if(bol!=null)
				{
					bol.set(false);
				}
			
				System.out.println("not anyfile");
			}
		}
		else
		{
			if(bol!=null)
			{
				bol.set(false);
			}
		
			System.out.println("not anyfile");
		}
		}
		catch(Exception e)
		{
			System.out.println("Error 123:"+e.getMessage());
		}
		
		
		
		
		
	}
	
	
	boolean checkEmptyFile(File file)
	{
		try{
	        if (file.exists()) {
	            FileReader fr = new FileReader(file);
	            if (fr.read() == -1) {
	            	file.delete();
	                System.out.println("EMPTY");
	                return false;
	            } else {
	                System.out.println("NOT EMPTY");
	                return true;
	            }
	        } else {
	            System.out.println("DOES NOT EXISTS");
	            return false;
	        }
		}
		catch(Exception e)
		{
		
			return false;
		}
		
	}
	
	void setTimer()
	{
	    Timer timer = new Timer();
	    TimerTask task = new TimerTask()
	    {
	            public void run()
	            {
	            ///	System.out.println("Connection : "+Myapp.isInternetConnected.get());
	            Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(Myapp.isInternetConnected.get()==false)
		            	{
							if(bol!=null)
							{
								bol.set(false);
							}
		            		Toast.makeText(Main.mainstage, "Internet Connection Problem", 1000, 100, 100);
		            	}
						
						System.out.println(" I M  CALLLLLLLLINNNNNGGGGGGG");
					}
				});	
	            }

	    };
	    timer.schedule(task, 15000);
	}

	
	
	void setUpdate()
	{
		if(drlist.size()!=0)
		{
			for(int i=0;i<drlist.size();i++)
			{

				drlist.get(i).updatetoUploadedFile();
			}
		}
	}
	void uploadFile(List<String> saname)
	{
		
		if(saname.size()<=ind)
		{
			System.out.println("ALL COMPLETED "+saname.size()+" - "+ind);
			setUpdate();
			if(bol!=null)
			{
				bol.set(false);
			}
		
		}
		else
		{
			if(bol!=null)
			{
				bol.set(true);
			}
			
			
			DocumentReference reff1=FirebaseConnect.db.collection("users").document(Myapp.uid).collection("files").document("hydrostatic");
			
		
		DocumentReference reff=FirebaseConnect.db.collection("users").document(Myapp.uid).collection("files").document("hydrostatic").collection("files").document(saname.get(ind));
		
		reff.set(os.get(saname.get(ind))).addListener(new Runnable() {
			
			@Override
			public void run() {
			}
		}, new Executor() {
			
			@Override
			public void execute(Runnable command) {
				System.out.println("Complete --- > "+saname.get(ind));
				try{
				ApiFuture<DocumentSnapshot> olddata= reff1.get();
				if(olddata.get().exists())
				{
					Map<String,Object> tempos=olddata.get().getData();
					List<String> sm=(List<String>)tempos.get("files_name");
					if(!sm.contains(saname.get(ind)))
					{
						sm.add(saname.get(ind));
						Map<String,Object> tempos1=new HashMap<String, Object>();
						tempos1.put("files_name", sm);
						reff1.update(tempos1);
					}
					
				}
				else
				{
				List<String> sm=new ArrayList<String>();
				sm.add(saname.get(ind));
				Map<String,Object> tempos=new HashMap<String, Object>();
				tempos.put("files_name", sm);
				
				reff1.set(tempos);
				}
				ind++;
				uploadFile(saname);
				}
				catch(Exception e)
				{
					System.out.println("Error :  "+e.getMessage());
					e.printStackTrace();

					ind++;
					uploadFile(saname);
				}
			}
		});
		
		
		
		}
		
		
		
	}
	
	
	
	
	
	
	
}
