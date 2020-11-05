package admin_user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import firebase.FirebaseConnect;

public class Users {

	/*Get All User*/
	
	 public Map<String,UserData> getUsers() {
		 
		 Map<String,UserData> users=new HashMap<String, UserData>();
			try {
		 ApiFuture<QuerySnapshot> query = FirebaseConnect.db
		 .collection("users").get();
		 // ...
		 // query.get() blocks on response
		 QuerySnapshot querySnapshot = query.get();
		 List<QueryDocumentSnapshot> documents = querySnapshot
		 .getDocuments();
		 for (QueryDocumentSnapshot document : documents) {


			 UserData us=new UserData(document);
		 users.put(document.getId(), us);
		 
		 }


		 

		 } catch (Exception e) {

		 e.printStackTrace();

		 }
		 
		 return users;
		 }
	
}
