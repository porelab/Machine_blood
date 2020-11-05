package application;

import java.io.OutputStream;

//for sending data from pc to mcu
public class SerialWriter implements Runnable {
	OutputStream out;
	writeFormat wrt;
	int sleep;

	public SerialWriter(OutputStream out, writeFormat wrt) {
		this.out = out;
		this.wrt = wrt;
		sleep = 0;
	}

	public SerialWriter(OutputStream out, writeFormat wrt, int slp) {
		this.out = out;
		this.wrt = wrt;
		sleep = slp;
	}

	public void run() {
		try {

			long time=System.currentTimeMillis();
			
			System.out.println("\n Preparing  : "+ wrt.showDataGet());
			Thread.sleep(sleep);
			try {
			for (Integer dout : wrt.wData) {

				// TODO Auto-generated method stub

				
					// Thread.sleep(sleep);
					out.write(dout);
					// System.out.println("NOW SENDING...");
				

			}
			System.out.println("\n Now sending  : "+ wrt.showDataGet()+" after  :"+(System.currentTimeMillis()-time));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
