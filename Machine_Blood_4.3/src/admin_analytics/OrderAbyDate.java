package admin_analytics;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import firebase.FirebaseConnect;

public class OrderAbyDate {

	Map<Integer,Map<Integer,Integer>> udata;

	public Map<Integer,Map<Integer,Integer>> getOrderByDate() {
		
		udata=new HashMap<Integer, Map<Integer,Integer>>(); 
		try {
			ApiFuture<QuerySnapshot> query =  FirebaseConnect.db
			.collection("order").document("pending")
			.collection("pending").get();
			
		
			
			// ...
			// query.get() blocks on response
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> documents = querySnapshot
					.getDocuments();
			for (QueryDocumentSnapshot document : documents) {

				
				Timestamp t=(Timestamp)document.getCreateTime();
				Date d=t.toDate();
				System.out.println("Id : "+document.getId()+ " : "+document.getCreateTime()+" :    : "+d.getMonth());
				
				if(!udata.containsKey(d.getYear()+1900))
				{
					Map<Integer,Integer> temp=new HashMap<Integer, Integer>();
					temp.put(0, 0);
					temp.put(1, 0);
					temp.put(2, 0);
					temp.put(3, 0);
					temp.put(4, 0);
					temp.put(5, 0);
					temp.put(6, 0);
					temp.put(7, 0);
					temp.put(8, 0);
					temp.put(9, 0);
					temp.put(10, 0);
					temp.put(11, 0);
					udata.put(d.getYear()+1900, temp);
					Map<Integer,Integer> temp1=(Map<Integer, Integer>)udata.get(d.getYear()+1900);
					
					int n=temp1.get(d.getMonth())+1;
					temp1.replace(d.getMonth(), n);
					udata.replace(d.getYear()+1900, temp1);
					
				}
				else
				{
					Map<Integer,Integer> temp=(Map<Integer, Integer>)udata.get(d.getYear()+1900);
					
					int n=temp.get(d.getMonth())+1;
					temp.replace(d.getMonth(), n);
					udata.replace(d.getYear()+1900, temp);
				}
				
				
			}
			
			
		} catch (Exception e) {

			e.printStackTrace();
			
		
		}
		return udata;
	}
}
