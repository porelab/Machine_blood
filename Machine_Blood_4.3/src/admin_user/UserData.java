package admin_user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import firebase.FirebaseConnect;

public class UserData {
	
	/*Get All User Data*/
	
	DocumentSnapshot d;
	Map<String,Object> alldata=null,orderdata=null,files=null;

	public int pen=0,run=0,rec=0,com=0,bubblefile=0,permiabilityfile=0,porometerfile=0;


	public UserData(DocumentSnapshot d)
	{
	this.d=d;	
	alldata=d.getData();
	parseUser();
	}

	void parseUser()
	{
	getOrders();
	getFiles();
	}


	Map<String,Object> getFiles()
	{
	try {
	ApiFuture<QuerySnapshot> query = FirebaseConnect.db
	.collection("users").document(d.getId()).collection("files").get();
	// ...
	// query.get() blocks on response
	QuerySnapshot querySnapshot = query.get();

	if(querySnapshot.getDocuments().size()>0)
	{
	orderdata=new HashMap<String, Object>();
	List<QueryDocumentSnapshot> documents = querySnapshot
	.getDocuments();
	for (QueryDocumentSnapshot document : documents) {

	orderdata.put(document.getId(), document.getData());
	if(document.getId().equals("bubblepoint"))
	{
	try{
	Map<String,Object> temp=document.getData();
	List<String> templist=(List<String>)temp.get("files_name");
	bubblefile=templist.size();
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	}
	else if(document.getId().equals("permiability"))
	{
	try{
	Map<String,Object> temp=document.getData();
	List<String> templist=(List<String>)temp.get("files_name");
	permiabilityfile=templist.size();
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}

	}
	else
	{
	try{
	Map<String,Object> temp=document.getData();
	List<String> templist=(List<String>)temp.get("files_name");
	porometerfile=templist.size();
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}

	}

	}
	System.out.println("Porometer : "+porometerfile);
	}
	else
	{
	System.out.println("No order found");
	}

	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return orderdata;
	}

	Map<String,Object> getOrders()
	{
	try {
	ApiFuture<QuerySnapshot> query = FirebaseConnect.db
	.collection("users").document(d.getId()).collection("order").get();
	// ...
	// query.get() blocks on response
	QuerySnapshot querySnapshot = query.get();

	if(querySnapshot.getDocuments().size()>0)
	{
	orderdata=new HashMap<String, Object>();
	List<QueryDocumentSnapshot> documents = querySnapshot
	.getDocuments();
	for (QueryDocumentSnapshot document : documents) {

	orderdata.put(document.getId(), document.getData());

	if(document.getData().get("status").toString().equals("pen")||document.getData().get("status").toString().equals("pending"))
	{
	pen++;
	}
	else if(document.getData().get("status").toString().equals("rec"))
	{
	rec++;
	}
	else if(document.getData().get("status").toString().equals("run"))
	{
	run++;
	}
	else
	{
	System.out.println(document.getData().get("status").toString());
	com++;
	}
	}
	System.out.println("Oderdata : "+orderdata.keySet());
	}
	else
	{
	System.out.println("No order found");
	}

	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return orderdata;
	}
}
