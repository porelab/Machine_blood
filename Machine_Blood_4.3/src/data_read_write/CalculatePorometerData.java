package data_read_write;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.DataStore;
import application.Myapp;

//for range poresize distribution
public class CalculatePorometerData {

	public List<String> getDiameter(List<String> wetpressure, String surt) {
		List<String> ans = new ArrayList<String>();

		for (int i = 0; i < wetpressure.size(); i++) {
			double bp = Double.parseDouble(wetpressure.get(i));

			ans.add(getDiameterCalulate(bp, surt));
		}
		return ans;
	}

	public void getWetFlowSmooth(List<String> pressure, List<String> flow,
			int range, int degree) {

		List<Double> xx = new ArrayList<Double>();
		List<Double> yy = new ArrayList<Double>();

		for (int i = 0; i < pressure.size(); i++) {
			xx.add(Double.parseDouble(pressure.get(i)));
			yy.add(Double.parseDouble(flow.get(i)));
		}

		Map<Integer, Double> peaks = new HashMap<Integer, Double>();

		Map<Integer, Double> xpoints = new HashMap<Integer, Double>();
		for (int i = 1; i < xx.size() - 1; i++) {

			if (yy.get(i) > yy.get(i - 1) && yy.get(i) > yy.get(i + 1)) {

				int n = (int) Math.abs(yy.get(i) - yy.get(i - 1));
				int n1 = (int) Math.abs(yy.get(i) - yy.get(i + 1));
				if (n > range && n1 > range) {
					peaks.put(i, yy.get(i));
					xpoints.put(i, xx.get(i));
					System.out.println(i + " - Peak up : " + n + " y[i] : "
							+ yy.get(i - 1) + "-" + yy.get(i) + "-"
							+ yy.get(i + 1));
				}

			} else if (yy.get(i) < yy.get(i - 1) && yy.get(i) < yy.get(i + 1)) {

				int n = (int) Math.abs(yy.get(i) - yy.get(i - 1));
				int n1 = (int) Math.abs(yy.get(i) - yy.get(i + 1));
				if (n > range && n1 > range) {
					peaks.put(i, yy.get(i));
					xpoints.put(i, xx.get(i));
					System.out.println(i + " - Peak up : " + n + " y[i] : "
							+ yy.get(i - 1) + "-" + yy.get(i) + "-"
							+ yy.get(i + 1));
				}

			}
		}

		System.out.println("Original : " + yy);
		// System.out.println("Original : "+xx);
		List<Integer> pp = new ArrayList<Integer>(peaks.keySet());

		for (int i = 0; i < pp.size(); i++) {

			int mm = pp.get(i) - i;
			System.out.println("Removing : " + yy.get(mm));

			yy.remove(mm);
			xx.remove(mm);
		}

		System.out.println(yy.size() + " : " + xx.size());

		if (xx.size() <= degree) {
			degree = xx.size();
		}

		PolynomialRegression p = new PolynomialRegression(xx, yy, degree, "n");
		double[] re = p.polyfit();

		for (int i = 0; i < peaks.size(); i++) {

			double xpoint = xpoints.get(pp.get(i));

			System.out.println("" + pp.get(i) + "- >" + xpoint);
			double ypoint = p.polynomial(xpoint, re);

			xx.add(pp.get(i), xpoint);
			yy.add(pp.get(i), ypoint);

		}

		flow.clear();

		for (int i = 0; i < xx.size(); i++) {
			double xpoint = xx.get(i);
			double ypoint = p.polynomial(xpoint, re);

			flow.add("" + ypoint);
		}

	}

	public String getDiameterCalulate(double bp, String stt) {

		// double d1=(double)0.415 * st/bp;
		double d1 = (double) (4 * Double.parseDouble(stt) * 10000 * Double
				.parseDouble(Myapp.tfactore)) / (bp * 68947.6);

		if (Myapp.crossection.equals("Eliptical")) {
			d1 = d1 * 0.72;
		} else if (Myapp.crossection.equals("Slit")) {
			d1 = d1 * 0.71;
		} else if (Myapp.crossection.equals("Rectangular")) {
			d1 = d1 * 0.75;
		}

		String d = getRound(d1, 4);
		return d;
	}

	public List<String> getAvgDiameter(List<String> dia) {

		List<String> ans = new ArrayList<String>();

		for (int i = 0; i < dia.size(); i++) {
			if (i == 0) {
				ans.add(dia.get(i));
			} else {

				double a = (double) (Double.parseDouble(dia.get(i)) + Double
						.parseDouble(dia.get(i - 1))) / 2;
				ans.add(getRound(a, 4));

			}
		}
		return ans;

	}

	public List<String> getHalfDryFlow(List<String> df) {

		List<String> ans = new ArrayList<String>();

		for (int i = 0; i < df.size(); i++) {

			double a = (double) Double.parseDouble(df.get(i)) / 2;
			ans.add(getRound(a, 4));

		}
		return ans;

	}

	public List<String> getPSD(List<String> ffp, List<String> diameters) {
		List<String> ans = new ArrayList<String>();

		for (int i = 0; i < ffp.size(); i++) {
			if (i == 0) {
				ans.add("0");
			} else {
				double diadif = Double.parseDouble(diameters.get(i - 1))
						- Double.parseDouble(diameters.get(i));
				double a = (double) Double.parseDouble(ffp.get(i)) / diadif;
				ans.add(getRound(a, 4));

			}

		}

		return ans;
	}

	public List<String> getIncrFF(List<String> cff) {

		List<String> ans = new ArrayList<String>();

		for (int i = 0; i < cff.size(); i++) {
			if (i == 0) {
				ans.add(cff.get(i));
			} else {
				double diff;
				
					diff = Double.parseDouble(cff.get(i))
							- Double.parseDouble(cff.get(i - 1));
				
				ans.add(getRound(diff, 4));
			}
		}
		return ans;

	}
	public List<String> getIncrFF1(List<String> cff) {

		List<String> ans = new ArrayList<String>();

		for (int i = 0; i < cff.size(); i++) {
			if (i == 0) {
				ans.add(cff.get(i));
			} else {
				double diff;
				if (Double.parseDouble(cff.get(i)) > Double.parseDouble(cff
						.get(i - 1))) {
					diff = Double.parseDouble(cff.get(i))
							- Double.parseDouble(cff.get(i - 1));
				} else {
					diff = Double.parseDouble(cff.get(i - 1))
							- Double.parseDouble(cff.get(i));
				}
				ans.add(getRound(diff, 4));
			}
		}
		return ans;

	}

	public List<String> getCff(List<String> df, List<String> wf) {

		List<String> ans = new ArrayList<String>();

		for (int i = 0; i < df.size(); i++) {
			double fw = Double.parseDouble(wf.get(i));
			double fd = Double.parseDouble(df.get(i));
			double ans1 = (100 * fw) / fd;
			ans.add(getRound(ans1, 4));
		}
		return ans;

	}

	public List<String> getNewDryFlow(List<String> dp, List<String> df,
			List<String> wp, int degree) {
		List<String> ans = new ArrayList<String>();

		double[] xx = new double[dp.size()];
		double[] yy = new double[dp.size()];
		for (int i = 0; i < xx.length; i++) {
			xx[i] = Double.parseDouble(df.get(i)); // *0.001; flow
			yy[i] = Double.parseDouble(dp.get(i)); // pressure
		}

		if (degree > dp.size()) {
			degree = dp.size();
		}

		PolynomialRegression pp = new PolynomialRegression(yy, xx, degree);
		double[] polycoff = pp.polyfit();

		for (int i = 0; i < wp.size(); i++) {
			double d = Double.parseDouble(wp.get(i));
			double d1 = pp.polynomial(d, polycoff);

			ans.add(getRound(d1, 4));
		}

		return ans;
	}

	public String getRound(Double r, int round) {

		if (round == 2) {
			r = (double) Math.round(r * 100) / 100;
		} else if (round == 3) {
			r = (double) Math.round(r * 1000) / 1000;

		} else {
			r = (double) Math.round(r * 10000) / 10000;

		}

		return r + "";

	}

	public double getMeanPressure(List<String> pp, List<String> hlf,
			List<String> wetflow) {

		List<Double> xx = new ArrayList<Double>();
		List<Double> yy = new ArrayList<Double>();
		List<Double> yy1 = new ArrayList<Double>();

		for (int i = 0; i < pp.size(); i++) {
			xx.add(Double.parseDouble(pp.get(i)));

			yy.add(Double.parseDouble(hlf.get(i)));
			yy1.add(Double.parseDouble(wetflow.get(i)));
		}

		double prey = 0, cury = 0, prex = 0, curx = 0;
		double prey1 = 0, cury1 = 0, prex1 = 0, curx1 = 0;

		int big; // if big==0 then yy is bigger or 1 then yy1
		if (yy.get(0) > yy1.get(0)) {
			big = 0;
		} else {
			big = 1;
		}

		if (big == 0) {
			System.out.println("BIG ----- 0");

			for (int i = 0; i < xx.size(); i++) {
				if (yy.get(i) < yy1.get(i)) {

					cury = yy.get(i);
					cury1 = yy1.get(i);
					curx = xx.get(i);
					curx1 = xx.get(i);
					break;
				}

				prey = yy.get(i);
				prey1 = yy1.get(i);
				prex = xx.get(i);
				prex1 = xx.get(i);

			}

			System.out.println("line 1 - point : (" + prex + " , " + prey
					+ ") - (" + curx + "," + cury + ")");

			System.out.println("line 2 - point : (" + prex1 + " , " + prey1
					+ ") - (" + curx1 + "," + cury1 + ")");

			// boolean
			// bol=Line2D.linesIntersect((float)prex,(float)prey,(float)curx,(float)cury,(float)prex1,(float)prey1,(float)curx1,(float)cury1);
			// System.out.println(bol);

			double xxx1 = prex, xxx2 = prex1, xxx3 = curx, xxx4 = curx1, yyy1 = prey, yyy2 = prey1, yyy3 = cury, yyy4 = cury1;
			double d = getLineLineIntersection(xxx1, yyy1, xxx3, yyy3, xxx2,
					yyy2, xxx4, yyy4);

			if (d > 0) {
				d = Double.parseDouble(getRound(d, 4));
			}
			return d;

		} else {
			System.out.println("BIG ----- 1");

			int cnt = 0;
			for (int i = 0; i < xx.size(); i++) {
				// System.out.println("prey : "+yy[i] +
				// " prex :"+xx[i]+" - prey1 : "+yy1[i]+" prex1 : "+xx[i]);
				prey = yy.get(i);
				prey1 = yy1.get(i);
				prex = xx.get(i);
				prex1 = xx.get(i);
				if (yy.get(i + 1) < yy1.get(i + 1)) {
					// System.out.println("BREAK  >>> prey : "+yy[i+1] + "");
					cury = yy.get(i + 1);
					cury1 = yy1.get(i + 1);
					curx = xx.get(i + 1);
					curx1 = xx.get(i + 1);
					cnt = i;
					break;
				}

			}

			System.out.println("line 1 - point : (" + prex + " , " + prey
					+ ") - (" + curx + "," + cury + ")");

			System.out.println("line 2 - point : (" + prex1 + " , " + prey1
					+ ") - (" + curx1 + "," + cury1 + ")");

			// boolean
			// bol=Line2D.linesIntersect((float)prex,(float)prey,(float)curx,(float)cury,(float)prex1,(float)prey1,(float)curx1,(float)cury1);
			// System.out.println(bol);

			double xxx1 = prex, xxx2 = prex1, xxx3 = curx, xxx4 = curx1, yyy1 = prey, yyy2 = prey1, yyy3 = cury, yyy4 = cury1;
			double d = getLineLineIntersection(xxx1, yyy1, xxx3, yyy3, xxx2,
					yyy2, xxx4, yyy4);

			if (d > 0) {
				d = Double.parseDouble(getRound(d, 4));
			}
			return d;

		}

	}

	public double getLineLineIntersection(double x1, double y1, double x2,
			double y2, double x3, double y3, double x4, double y4) {
		double det1And2 = det(x1, y1, x2, y2);
		double det3And4 = det(x3, y3, x4, y4);
		double x1LessX2 = x1 - x2;
		double y1LessY2 = y1 - y2;
		double x3LessX4 = x3 - x4;
		double y3LessY4 = y3 - y4;
		double det1Less2And3Less4 = det(x1LessX2, y1LessY2, x3LessX4, y3LessY4);
		if (det1Less2And3Less4 == 0) {
			// the denominator is zero so the lines are parallel and there's
			// either no solution (or multiple solutions if the lines overlap)
			// so return null.
			System.out.println("ZZZZEERROOO");

			return 0;
		}
		double x = (det(det1And2, x1LessX2, det3And4, x3LessX4) / det1Less2And3Less4);
		double y = (det(det1And2, y1LessY2, det3And4, y3LessY4) / det1Less2And3Less4);
		System.out.println("Points  : " + x + " , " + y);

		return x;
	}

	protected static double det(double a, double b, double c, double d) {
		return a * d - b * c;
	}

}
