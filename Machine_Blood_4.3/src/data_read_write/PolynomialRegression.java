package data_read_write;

import java.util.List;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import Jama.Matrix;



//find equation from points



public class PolynomialRegression {
    private final int n;                // number of observations
    private final String variableName;  // name of the predictor variable
    private int degree;                 // degree of the polynomial regression
    private Matrix beta;                // the polynomial regression coefficients
    private double sse;                 // sum of squares due to error
    private double sst;                 // total sum of squares

    double[] coeff;
  /**
     * Performs a polynomial reggression on the data points {@code (y[i], x[i])}.
     * Uses N as the name of the predictor variable.
      *
     * @param  x the values of the predictor variable
     * @param  y the corresponding values of the response variable
     * @param  degree the degree of the polynomial to fit
     * @throws IllegalArgumentException if the lengths of the two arrays are not equal
     */
    public PolynomialRegression(double[] x, double[] y, int degree) {
        this(x, y, degree, "n");
    }

    
    
  /**
     * Performs a polynomial reggression on the data points {@code (y[i], x[i])}.
     *
     * @param  x the values of the predictor variable
     * @param  y the corresponding values of the response variable
     * @param  degree the degree of the polynomial to fit
     * @param  variableName the name of the predictor variable
     * @throws IllegalArgumentException if the lengths of the two arrays are not equal
     */
    
    public PolynomialRegression(List<Double> x, List<Double> y, int degree, String variableName) {
        this.degree = degree;
        this.variableName = variableName;

        n = x.size();

        final WeightedObservedPoints obs = new WeightedObservedPoints();

		for (int i = x.size()-1; i >=0; i--) {
			obs.add(x.get(i),y.get(i));
		}

        
        
        final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);

		// Retrieve fitted parameters (coefficients of the polynomial function).
		 coeff = fitter.fit(obs.toList());
		double temp;
		for(int i=0,j=coeff.length-1;i<coeff.length/2;i++,j--)
		{
	
			temp=coeff[i];
			coeff[i]=coeff[j];
			coeff[j]=temp;
	
			
		}
       
        
    }
    
    public PolynomialRegression(double[] x, double[] y, int degree, String variableName) {
        this.degree = degree;
        this.variableName = variableName;

        n = x.length;

        final WeightedObservedPoints obs = new WeightedObservedPoints();

		for (int i = x.length-1; i >=0; i--) {
			obs.add(x[i],y[i]);
		}

        
        
        final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);

		// Retrieve fitted parameters (coefficients of the polynomial function).
		 coeff = fitter.fit(obs.toList());
		double temp;
		for(int i=0,j=coeff.length-1;i<coeff.length/2;i++,j--)
		{
	
			temp=coeff[i];
			coeff[i]=coeff[j];
			coeff[j]=temp;
	
			
		}
       
        
    }

    

	public double beta(int j) {
        // to make -0.0 print as 0.0
        if (Math.abs(beta.get(j, 0)) < 1E-4) return 0.0;
        return beta.get(j, 0);
    }

   /**
     * Returns the degree of the polynomial to fit.
     *
     * @return the degree of the polynomial to fit
     */
    public int degree() {
        return degree;
    }

   /**
     * Returns the coefficient of determination <em>R</em><sup>2</sup>.
     *
     * @return the coefficient of determination <em>R</em><sup>2</sup>,
     *         which is a real number between 0 and 1
     */
    public double R2() {
        if (sst == 0.0) return 1.0;   // constant function
        return 1.0 - sse/sst;
    }

   /**
     * Returns the expected response {@code y} given the value of the predictor
     *    variable {@code x}.
     *
     * @param  x the value of the predictor variable
     * @return the expected response {@code y} given the value of the predictor
     *         variable {@code x}
     */
    public double predict(double x) {
        // horner's method
        double y = 0.0;
        for (int j = degree; j >= 0; j--)
            y = beta(j) + (x * y);
        return y;
    }

    public double[] polyfit()
    {
    	
          return coeff;
    }
    
   /**
     * Returns a string representation of the polynomial regression model.
     *
     * @return a string representation of the polynomial regression model,
     *         including the best-fit polynomial and the coefficient of
     *         determination <em>R</em><sup>2</sup>
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int j = degree;

        // ignoring leading zero coefficients
        while (j >= 0 && Math.abs(beta(j)) < 1E-5)
            j--;

        // create remaining terms
        while (j >= 0) {
        	System.out.println("Value if J"+beta(j));
            if      (j == 0) s.append(String.format("%.2f ", beta(j)));
            else if (j == 1) s.append(String.format("%.2f %s + ", beta(j), variableName));
            else             s.append(String.format("%.2f %s^%d + ", beta(j), variableName, j));
            j--;
        }
        s = s.append("  (R^2 = " + String.format("%.3f", R2()) + ")");
        return s.toString();
    }

   public double polynomial(double x,double[] cof)
   {
	   
	   int len=cof.length;
	   int i,j;
	   double ans=0;
	   for(i=0,j=len-1;i<len;i++,j--)
	   {
		   ans=ans+cof[i]*Math.pow(x,j);
		   
		   
	   }
	   
	   
	   
	   return ans;
   }
   
   /**
     * Unit tests the {@code PolynomialRegression} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        double[] x = { 10, 20, 40, 80, 160, 200 };
        double[] y = { 100, 350, 1500, 6700, 20160, 40000 };
        PolynomialRegression regression = new PolynomialRegression(x, y, 2);
        System.out.println("Regration eqation"+regression);
        double[] d=regression.polyfit();
        for(int i=0;i<d.length;i++)
        {
        System.out.println(d[i]);
        }
    }
}
