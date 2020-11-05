package admin_analytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import firebase.FirebaseConnect;

public class OrderA {
	
Map<String, Integer> olist;
	
int totalord=0;

	public Map<String, Integer> getOrders() {
		try {
			
				olist=new HashMap<String, Integer>();
				olist.put("com", 0);
				olist.put("pen", 0);
				olist.put("rec", 0);
				olist.put("run", 0);
				
				
				ApiFuture<QuerySnapshot> query;
				
			query = FirebaseConnect.db
					.collection("order").document("pending")
					.collection("pending").get();
				
			// ...
			// query.get() blocks on response
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> documents = querySnapshot
					.getDocuments();
		
				for (QueryDocumentSnapshot document : documents) {


					totalord++;
					if(olist.containsKey(document.getData().get("status").toString()))
					{

						int n=olist.get(document.getData().get("status"))+1;
						olist.replace(document.getData().get("status").toString(), n);
								
					}
				
				}
			

		} catch (Exception e) {

			e.printStackTrace();

		}
		return olist;
	}

	
}
