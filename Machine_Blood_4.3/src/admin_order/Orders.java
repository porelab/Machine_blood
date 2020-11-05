package admin_order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import firebase.FirebaseConnect;

public class Orders {

	Map<String, OrderData> olist;
	
	/*Get order status*/

	public Map<String, OrderData> getOrders(String status) {
		try {
				olist=new HashMap<String, OrderData>();
				ApiFuture<QuerySnapshot> query;
				if(status.equals("")||status==null)
				{
			query = FirebaseConnect.db
					.collection("order").document("pending")
					.collection("pending").get();
				}
				else
				{
					query = FirebaseConnect.db
							.collection("order").document("pending")
							.collection("pending").whereEqualTo("status", status).get();
					
				}
			// ...
			// query.get() blocks on response
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> documents = querySnapshot
					.getDocuments();
		
				for (QueryDocumentSnapshot document : documents) {


					if(status.equals("")||status==null)
					{
						if(!document.getData().get("status").toString().equals("com")||!document.getData().get("status").toString().equals("complete"))
						{
						olist.put(document.getId(), new OrderData(document));
					
						}
					}
					else
					{

						olist.put(document.getId(), new OrderData(document));
					}
						
				}
			

		} catch (Exception e) {

			e.printStackTrace();

		}
		return olist;
	}

	
	
}
