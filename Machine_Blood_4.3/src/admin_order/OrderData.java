package admin_order;

import java.util.HashMap;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.WriteResult;

import firebase.FirebaseConnect;

/*Get all order data in Cloud*/

public class OrderData {
DocumentSnapshot d;
public Map<String,Object> data,sampledata;
String uid;
Webservices ws;
public OrderData(DocumentSnapshot d)
{
this.d=d;	
ws=new Webservices();
ws.setUrl("https://m19.io/api/statusupdate.aspx");
data=d.getData();
sampledata=(Map<String, Object>)data.get("samples");

}

boolean setRun()
{
Map<String,Object> up=new HashMap<String, Object>();
up.put("status", "run");

DocumentReference doc=FirebaseConnect.db.collection("order").document("pending").collection("pending").document(d.getId());

ApiFuture<WriteResult> res=doc.update(up);
ws.clearParam();
ws.addParam( "type","run");
ws.addParam("email", data.get("email").toString());
ws.addParam("uid", data.get("userid").toString());
ws.addParam("name", data.get("name").toString());
ws.addParam("oid", data.get("orderid").toString());


        
ws.connect();

if(ws.getData().toString().trim().equals("yes"))
{
	data.remove("status");
data.put("status", "run");
return true;
}
else
{
return false;
}
}

boolean setCom()
{
Map<String,Object> up=new HashMap<String, Object>();
up.put("status", "com");

DocumentReference doc=FirebaseConnect.db.collection("order").document("pending").collection("pending").document(d.getId());

ApiFuture<WriteResult> res=doc.update(up);
ws.clearParam();
ws.addParam("type", "com");
ws.addParam("email", data.get("email").toString());
ws.addParam("uid", data.get("userid").toString());
ws.addParam("name", data.get("name").toString());
ws.addParam("oid", data.get("orderid").toString());

ws.connect();

if(ws.getData().toString().trim().equals("yes"))
{
	data.remove("status");
data.put("status", "com");
return true;
}
else
{
return false;
}





}

boolean setRec()
{
Map<String,Object> up=new HashMap<String, Object>();
up.put("status", "rec");
up.put("timestamp", FieldValue.serverTimestamp());

DocumentReference doc=FirebaseConnect.db.collection("order").document("pending").collection("pending").document(d.getId());

ApiFuture<WriteResult> res=doc.update(up);

ws.clearParam();
ws.addParam("type", "rec");
ws.addParam("email", data.get("email").toString());
ws.addParam("uid", data.get("userid").toString());
ws.addParam("name", data.get("name").toString());
ws.addParam("oid", data.get("orderid").toString());


ws.connect();

if(ws.getData().toString().trim().equals("yes"))
{

	data.remove("status");
data.put("status", "rec");
return true;
}
else
{
return false;
}


}


}