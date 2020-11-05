package firebase;

import java.io.FileInputStream;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.pdf.codec.Base64.InputStream;

// for connection with online cloud..

public class FirebaseConnect {
	
	public static FirebaseDatabase db1;
	

	public static FirebaseConnect cc;
	public static Firestore db ;
	

	public static FirestoreOptions options=null;
	public static FirebaseDatabase fb=null;

	public FirebaseConnect()
	{
			cc=this;
	}
	
	public static void InitApp(FirestoreOptions op,java.io.InputStream ii)
	{

		try{
		
				options = op;
			    db = op.getService();

			  //  InputStream ii1=(InputStream) cc.getClass().getResourceAsStream("/firebase/serviceAccountKey.json");
				
			    FirebaseOptions options1 = new FirebaseOptions.Builder()
				  .setCredential(FirebaseCredentials.fromCertificate(ii))
				  .setDatabaseUrl("https://nyiapp-3a612.firebaseio.com")
				  .build(); 
			    
			    FirebaseApp app=FirebaseApp.initializeApp(options1);
				 fb=FirebaseDatabase.getInstance(app);
   		
    		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}
