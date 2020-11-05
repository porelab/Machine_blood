
package application;

import gnu.io.CommPort;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import toast.Toast;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

//this class is for data flow and data storing while page changes
public class DataStore 
{
	

	public static 	CommPort commPort;
	public static SimpleBooleanProperty isconfigure=new SimpleBooleanProperty(false);
	public static SerialPort serialPort;

	public static InputStream in;
	  public static  void setLoadListener()
	    {
	    	DataStore.isconfigure.addListener(new ChangeListener<Boolean>() {

	    		@Override
	    		public void changed(
	    				ObservableValue<? extends Boolean> arg0,
	    				Boolean arg1, Boolean arg2) {
	    		
	    			System.out.println("Chaangedd ------->"+arg2);
	    			if(arg2==true)
	    			{
	    				Main.splashstage.hide();
	    				Main.mainstage.show();
	    				
	    			}
	    			
	    		}
	    	});
	    }
	
	//for scada
	public static SimpleBooleanProperty sv1=new SimpleBooleanProperty(false);
	public static SimpleBooleanProperty sv2=new SimpleBooleanProperty(false);
	public static SimpleBooleanProperty sv3=new SimpleBooleanProperty(false);
	public static SimpleBooleanProperty sv4=new SimpleBooleanProperty(false);
	
	public static DoubleProperty spr=new SimpleDoubleProperty(0);
	public static DoubleProperty sfc=new SimpleDoubleProperty(0);
	public static DoubleProperty sfm1=new SimpleDoubleProperty(0);
	public static DoubleProperty sfm2=new SimpleDoubleProperty(0);
	public static DoubleProperty spg1=new SimpleDoubleProperty(0);
	public static DoubleProperty spg2=new SimpleDoubleProperty(0);
	
	public static SimpleStringProperty ssfm1=new SimpleStringProperty("0.0");
	public static SimpleStringProperty ssfm2=new SimpleStringProperty("0.0");
	public static SimpleStringProperty sspg1=new SimpleStringProperty("0.0");
	public static SimpleStringProperty sspg2=new SimpleStringProperty("0.0");
	
	
	
	
	public static int leakinterval,leakpress;
	
	public static SimpleBooleanProperty porometer_summary=new SimpleBooleanProperty(false);	
	
	static double maxflowofmachine=55000;
	public static SimpleBooleanProperty isDevicefatch=new SimpleBooleanProperty(false);
	public static String software_id;
	public static String firebase_url="https://pushnotification-305f7.firebaseio.com/";
	
	public static Stage gtg;

	public static SimpleBooleanProperty startTest=new SimpleBooleanProperty(false);
	
	// for userinput screen
	
	public static SimpleBooleanProperty testComplete=new SimpleBooleanProperty(false);
	
	
	public static SimpleBooleanProperty user_li=new SimpleBooleanProperty(true);
	public static SimpleBooleanProperty live_li=new SimpleBooleanProperty(false);
	

	public static SimpleDoubleProperty livepressure=new SimpleDoubleProperty(0);
	public static SimpleDoubleProperty liveflow=new SimpleDoubleProperty(0);
	
	
	//for online upload

	public static SimpleStringProperty ttime=new SimpleStringProperty("now");
	public static SimpleStringProperty tper=new SimpleStringProperty("0%");
	public static SimpleStringProperty tstatus=new SimpleStringProperty("loading");
	
	//new string

	public static ObservableList<String> leakpressure=FXCollections.observableArrayList();
	public static ObservableList<String> leaktime=FXCollections.observableArrayList();
	
	   //for csv generation...

	public static ObservableList<String> flow=FXCollections.observableArrayList();
	public static ObservableList<String> dpressure=FXCollections.observableArrayList();
	public static ObservableList<String> dflow=FXCollections.observableArrayList();

		
		
		// for user input screen...
	public static String dtempdata="",dtimedata="",dwdata="",sample_id="",id;
	
	public static long starttime;
	public static String thresoldvalue;

	public static  ObservableList<String> tempdata=FXCollections.observableArrayList();
	
	
	public static String surface,diameter,sampleid,thresold,thikness,starttime1,completetime,operator,customerid,comments,testdur;
	

		
		// for live graph...
	public static int pressure_min=0,pressure_max=0;
		
	    
		
	//configure Page 
	public static String pg,fm,pr,fc,thfirtbp,thmoderat,thcontinous;
	
	public static SimpleStringProperty sysinfolab=new SimpleStringProperty("");
	//for reminder
	
	public static List<List<String>> todayeventlist=new ArrayList<List<String>>();
	
   //for csv generation...
	public static ObservableList<String> pressure=FXCollections.observableArrayList();
	public static ObservableList<String> temp=FXCollections.observableArrayList();

	
	// for user input screen...
	public static String path_csv="";
	
	
	// for live graph...
	public static int xdiff=0,flow_min=0;
	
	public static int no_sample=0;
	public static  int xSeriesData = 0;
	public static ConcurrentLinkedQueue<Number> dataQ = new ConcurrentLinkedQueue<Number>();   
	
	
	
    // for live graph listenrt
	public static int refresh=0;
	
     //for new test screen
	public static TabPane test_tab1;
	public static Tab user1,liveprocess1,livegraphs1,reports1;
 	
    // for connection is redy or not
 	
	public static SimpleBooleanProperty connect_hardware=new SimpleBooleanProperty(false);
	public static SimpleBooleanProperty connect_internet=new SimpleBooleanProperty(false);
 	
 	
    //for serial commnunicatior

	public static Set<Character> listOfHeads = new HashSet<Character>();// Contains all Heads eg. to read Pressure P, to read Temperature T  
	public static List<List<Integer>> allDataRead = new ArrayList<List<Integer>>();// List of all the data read 
 //	static HashMap<String,ObservableList<Integer>> intList = new HashMap<String,ObservableList<Integer>>(); // map for value received for P, T, H,F etc.
	public static HashMap<String,ObservableList<Double>> intList = new HashMap<String,ObservableList<Double>>(); // map for value received for P, T, H,F etc.
 	
	public static OutputStream out;
   
    static String type,pg1,pg2,fm1,fm2,chemb;
    
    public static boolean isCurveFit=false;
	
    public static List<String> getColorMultiple() {

		List<String> grpclr = new ArrayList<String>();
		grpclr.add("#035aa6");
		grpclr.add("#fcbf1e");
		grpclr.add("#c70039");
		grpclr.add("#f37121");
		grpclr.add("#e2979c");
		grpclr.add("#481380");
		grpclr.add("#00a8cc");
		grpclr.add("#95B64F");
		grpclr.add("#be79df");
		grpclr.add("#ff1e56");
		
		return grpclr;
	}	
    
	public static void Refresh()
	{

		
		System.out.println(" Calling refresh start");
		refresh=1;
		

		listOfHeads = new HashSet<Character>();// Contains all Heads eg. to read Pressure P, to read Temperature T  
	 	allDataRead = new ArrayList<List<Integer>>();// List of all the data read 
	    intList = new HashMap<String,ObservableList<Double>>(); 
		
		
		
		pressure=null;
		pressure=FXCollections.observableArrayList();
		
		temp=null;
		temp=FXCollections.observableArrayList();
		

		 xdiff=0;
		 no_sample=0;
		 xSeriesData=0;
		 
		// dataQ=null;
		// dataQ=new ConcurrentLinkedQueue<Number>();
		 
		 System.out.println(" Calling refresh end");
		
	}
	
	public static void getconfigdata()
	{
		Database db=new Database();		
		List<List<String>> ll=db.getData("select * from configdata");
		type =(ll.get(0).get(0));
		pg1 =(ll.get(0).get(1));
		pg2 =(ll.get(0).get(2));
		fm1 =(ll.get(0).get(3));
		fm2 =(ll.get(0).get(4));
		chemb=(ll.get(0).get(7));
		pr=(ll.get(0).get(5));
		fc=(ll.get(0).get(6));
		
		String curvefittype =(ll.get(0).get(16));
		
		if(curvefittype.equals("on"))
		{
			isCurveFit=true;
		}
		else
		{
			isCurveFit=false;
		}
		
		System.out.println("type"+DataStore.getType());
		System.out.println("pg1"+DataStore.getPg1());
		System.out.println("pg2"+DataStore.getPg2());
		System.out.println("fm1"+DataStore.getFm1());
		System.out.println("fm2"+DataStore.getFm2());
		System.out.println("Pr : "+DataStore.getPr());
		System.out.println("fc : "+DataStore.getFc());
		
		System.out.println("chemb"+DataStore.getChemb());
		System.out.println("Curve FIt : "+isCurveFit);
	
	
	}
	public boolean isCurveFit()
	{
		return isCurveFit;
	}
	public static String getPr() {
		return pr;
	}

	public static void setPr(String pr) {
		DataStore.pr = pr;
	}

	public static String getFc() {
		return fc;
	}

	public static void setFc(String fc) {
		DataStore.fc = fc;
	}

	public static String getPg1() {
	System.out.println();
		return pg1;
	}

	public static String getPg2() {
	
		return pg2;
	}

	
	public static String getFm1() {
	
		return fm1;
	}

	public static String getFm2() {
	
		return fm2;
	}
	
	public static String getChemb() {
	
		return chemb;
	}

	public static String getType() {
		return type;
	}

	public static String getCom()
	{
		String cm="";
		Database db=new Database();		
		List<List<String>> ll=db.getData("select comport from configdata");
		cm =(ll.get(0).get(0));
			
		
		return cm;
	}

	
	public static boolean hardReset()
	{
		try{

			new Thread(new Runnable() {
				
				@Override
				public void run() {
		
			
					
					
				
				}
			}).start();
			
			
			return true;
		}
		catch(Exception e)
		{
			return false;
		
		}
		
		
	}
	static void sendData(writeFormat w, int slp) {
		System.out.println("Sending Data......");
		w.showData();
		Thread t = new Thread(new SerialWriter(DataStore.out, w, slp));
		try {

			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	public static List<String> getAdmin_screen1()
	{
	
		List<String> data=new ArrayList<String>();
		Database db=new Database();		
		List<List<String>> ll=db.getData("select * from admin_screen1");
		
		String va =(ll.get(0).get(1));
		String vb =(ll.get(0).get(2));
		String vc =(ll.get(0).get(3));
		String vd =(ll.get(0).get(4));
		String ve =(ll.get(0).get(5));
		String vf =(ll.get(0).get(6));
		String vg =(ll.get(0).get(7));
		String vh =(ll.get(0).get(8));
		
		String pc =(ll.get(0).get(9));
		String fc =(ll.get(0).get(10));
		
		data.add(va);
		data.add(vb);
		data.add(vc);
		data.add(vd);
		data.add(ve);
		data.add(vf);
		data.add(vg);
		data.add(vh);
		
		data.add(pc);
		data.add(fc);
		
		return data;
	}

	public static String getchambertype()
	{
		String ch="";
		Database db=new Database();		
		List<List<String>> ll=db.getData("select chambertype from configdata");
		ch =(ll.get(0).get(0));
			
		
		return ch;
	}
	
	public static String getthfirstbp()
	{
		String bp="";
		Database db=new Database();		
		List<List<String>> ll=db.getData("select thfirst from configdata");
		bp =(ll.get(0).get(0));

		return bp;
	}
	public static String getthmoderat()
	{
		String bp="";
		Database db=new Database();		
		List<List<String>> ll=db.getData("select thmoderate from configdata");
		bp =(ll.get(0).get(0));

		return bp;
	}
	public static String getthcontinous()
	{
		String bp="";
		Database db=new Database();		
		List<List<String>> ll=db.getData("select thcontinous from configdata");
		bp =(ll.get(0).get(0));

		return bp;
	}
	
	public static String getPressureCrossover()
	{
		int bp;
		Database db=new Database();		
		List<List<String>> ll=db.getData("select pc from admin_screen1");
		bp =Integer.parseInt((ll.get(0).get(0)));
		return bp+"";
	}
	
	public static String getFlowCrossover()
	{
		int bp;
		Database db=new Database();		
		List<List<String>> ll=db.getData("select fc from admin_screen1");
		bp =Integer.parseInt((ll.get(0).get(0)));
		return bp+"";
	}
	
		
	public static void defaultsetting()
	{
		Database db=new Database();
		
		String conficupdate = "update configdata set  pg1='5',pg2='100',fm1='10000',fm2='200000',pr='130',chambertype='Manual',curvefittgb='on',fc='30',thfirst='1500',thmoderate='2500',thcontinous='3000'"; 
		
		String admindata = "update admin_screen1 set  pc='1',fc='1',va='0',vb='1',vc='0',vd='1',ve='1',vf='0',vg='0',vh='0'";
		
		if(db.Insert(conficupdate) && db.Insert(admindata))
		{
			 Toast.makeText(Main.mainstage, "Successfully Set Default Setting..", 1000, 200, 200);

		}
		else {
//			System.out.println("Configration Data save d Eroorr.....");
		}
	}
	
	/*Get Unite*/
	
	public static String getUnitepg1() {
		String pg1;
		Database db = new Database();
		List<List<String>> ll = db.getData("select pg1 from testunite");
		pg1 = (ll.get(0).get(0));
		return pg1 + "";
	}

	public static String getUnitepg2() {
		String pg2;
		Database db = new Database();
		List<List<String>> ll = db.getData("select pg2 from testunite");
		pg2 = (ll.get(0).get(0));
		return pg2 + "";
	}

	public static String getPrunite() {
		String pr;
		Database db = new Database();
		List<List<String>> ll = db.getData("select pr from testunite");
		pr = (ll.get(0).get(0));
		return pr + "";
	}

	/*Absolute or Relative*/
	
	public static boolean isabsolutepg1() {
		Database db = new Database();
		List<List<String>> ll = db.getData("select p1scaletype from configdata");
		String pgisabsolute = (ll.get(0).get(0));

		if (pgisabsolute.equals("absolute")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isabsolutepg2() {
		Database db = new Database();
		List<List<String>> ll = db.getData("select p2scaletype from configdata");
		String prisabsolute = (ll.get(0).get(0));

		if (prisabsolute.equals("absolute")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/* Prefrance Unite */

	public static String getUnitepressure() {
		String pressure;
		Database db = new Database();
		List<List<String>> ll = db.getData("select pressure from unite");
		pressure = (ll.get(0).get(0));
		return pressure + "";
	}

	public static String getUniteflow() {
		String flow;
		Database db = new Database();
		List<List<String>> ll = db.getData("select flow from unite");
		flow = (ll.get(0).get(0));
		return flow + "";
	}

	public static String getUnitediameter() {
		String dia;
		Database db = new Database();
		List<List<String>> ll = db.getData("select length from unite");
		dia = (ll.get(0).get(0));
		return dia + "";
	}
	public static int getRoundOff()
	{
		int roundoff;
		Database db = new Database();
		List<List<String>> ll = db.getData("select roundoff from unite");
		roundoff = Integer.parseInt(ll.get(0).get(0));
		return roundoff;
		
		
	}

	/*Unite Convert*/
	public static double toSccs(double sccm) {
		double ans = sccm / 60;
		return ans;

	}

	public static double toSccs(String sccm) {
		double ans = Double.parseDouble(sccm) / 60;
		return ans;
	}
	
	public static double toCfm(double sccm) {
		double ans = sccm / 60;
		return ans;

	}

	public static double toCfm(String sccm) {
		double ans = Double.parseDouble(sccm) / 60;
		return ans;
	}

	public static double toBar(double psi) {
		return psi * 0.0689476;

	}

	public static double toBar(String psi) {
		return Double.parseDouble(psi) * 0.0689476;
	}

	
	public static double toKiloPascal(String psi) {
		return Double.parseDouble(psi) * 6.89476;
	}
	
	public static double toKiloPascal(double psi) {
		return psi * 6.89476;
	}
	
	public static double toCmh2o(String psi) {
		return Double.parseDouble(psi) * 70.307;
	}
	
	public static double toCmh2o(double psi) {
		return psi * 70.307;
	}
	
	public static double toTorr(double psi) {

		return psi * 51.7149;

	}

	public static double toTorr(String psi) {

		return Double.parseDouble(psi) * 51.7149;
	}

	public static double toNm(double mm) {

		return mm * 1000;

	}

	public static double toNm(String mm) {

		return Double.parseDouble(mm) * 1000;
	}

	public static double ConvertFlow(String flow) {
		double ans;
		if (DataStore.getUniteflow().equals("sccs")) {

			ans = toSccs(flow);

		} 
		else if (DataStore.getUniteflow().equals("cfm")) {
			ans = DataStore.toCfm(flow);
		}
		else {
			ans = Double.parseDouble(flow);
		}
		return Myapp.getRoundDouble(ans, getRoundOff());
	}

	public static double ConvertFlow(Double flow) {

		if (DataStore.getUniteflow().equals("sccs")) {

			flow = toSccs(flow);

		}
		else if (DataStore.getUniteflow().equals("cfm")) {
			flow = toCfm(flow);
		}

		return Myapp.getRoundDouble(flow, getRoundOff());
	}

	public static double ConvertDiameter(String diameter) {
		double ans;
		if (DataStore.getUnitediameter().equals("nm")) {

			ans = Double.parseDouble(diameter) * 1000;

		} else {
			ans = Double.parseDouble(diameter);
		}
		return Myapp.getRoundDouble(ans, getRoundOff());
	}

	public static double ConvertDiameter(Double diameter) {
		if (DataStore.getUnitediameter().equals("nm")) {

			diameter = diameter * 1000;

		}

		return Myapp.getRoundDouble(diameter, getRoundOff());
	}

	public static double ConvertPressure(String pressure) {
		double ans;
		if (DataStore.getUnitepressure().equals("bar")) {
			ans = DataStore.toBar(pressure);
		} else if (DataStore.getUnitepressure().equals("torr")) {
			ans = DataStore.toTorr(pressure);
		}
		else if(DataStore.getUnitepressure().equals("kilopascal"))
		{
			ans=toKiloPascal(pressure);
		}
		else if(DataStore.getUnitepressure().equals("cmH2O"))
		{
			ans=toCmh2o(pressure);
		}
		else {
			ans = Double.parseDouble(pressure);
		}
		
		return Myapp.getRoundDouble(ans, getRoundOff());
	}
	
	
	public static List<String> ConvertPressure(List<String> pressure) {

		List<String> data=new ArrayList<String>();

		
		if (DataStore.getUnitepressure().equals("bar")) {
			for(int i=0;i<pressure.size();i++)
			{
				data.add(toBar(pressure.get(i))+"");
			}
		
		} else if (DataStore.getUnitepressure().equals("torr")) {
			for(int i=0;i<pressure.size();i++)
			{
				data.add(toTorr(pressure.get(i))+"");
			}
		}
		else if (DataStore.getUnitepressure().equals("kilopascal")) {
			for(int i=0;i<pressure.size();i++)
			{
				data.add(toKiloPascal(pressure.get(i))+"");
			}
		}
		else if (DataStore.getUnitepressure().equals("cmH2O")) {
			for(int i=0;i<pressure.size();i++)
			{
				data.add(toCmh2o(pressure.get(i))+"");
			}
		}
		else
		{
		data.addAll(pressure);
		}
		
		return data;
		
		
	}
	

	public static double ConvertPressure(Double pressure) {

		if (DataStore.getUnitepressure().equals("bar")) {
			pressure = DataStore.toBar(pressure);
		} else if (DataStore.getUnitepressure().equals("torr")) {
			pressure = DataStore.toTorr(pressure);
		}
		else if(DataStore.getUnitepressure().equals("kilopascal"))
		{
			pressure=toKiloPascal(pressure);
		}
		else if(DataStore.getUnitepressure().equals("cmH2O"))
		{
			pressure=toCmh2o(pressure);
		}
		
		return Myapp.getRoundDouble(pressure, getRoundOff());
	}

	public static double barToPsi(double bar)
	{

		//System.out.println("bar to psi");
		return bar*14.5038;
	}
	public static double torrToPsi(double torr)
	{
		//System.out.println("torr to psi");
		return torr*0.0193368;
	}
	public static double sccsToSccm(double sccs)
	{
		//System.out.println("sccs to sccm");
		return 60*sccs;
		
	}
	
	/*Get IP Address*/

	

	
	
	
}
