package Ex1;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	//@Override
	//public boolean equals (Object m1) { //if this the monom is equal to our monom return true
		//if(!(m1 instanceof Monom)) {return false;}
	public boolean equals (Monom m1) { //if this the monom is equal to our monom return true
		if (m1.get_coefficient()==this.get_coefficient() && m1.get_power()==this.get_power()) return true;
		return false;
	}
	
	public Monom(double a, int b){ //set the monom
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	
	public double get_coefficient() {
		return this._coefficient;
	}

	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	public double f(double x) { //return the fun with the x
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;} // check if the monom is zero
	
	// ***************** add your code below **********************
	public Monom(String s) { //ake the string and devide to a coe and power
		try {
			int i=0;
			String coefficient="";
			String power="";
			
			if (s.charAt(i)=='-') {
				coefficient+='-';
				i++;
			}
			if (s.charAt(i)=='+') {
				i++;
			}
			while (i<s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i)=='.')) {
				coefficient+=s.charAt(i);
				i++;
			}
			
			if (i>=s.length()) power+=0; // if there's nothing after the number , like: 58 
			if (i<s.length() && s.charAt(i)=='*') i++; // if there is *
			/*if (i<s.length() && Character.isLetter(s.charAt(i))) {  // if there is x
				i++;
				if (i>=s.length()) power+=1; // if there's nothing after x , like: 5x
			}*/
			
			if (i<s.length() && s.charAt(i)=='x') {  // if there is x
				i++;
				if (i>=s.length()) power+=1; // if there's nothing after x , like: 5x
			}
			if (i<s.length() && s.charAt(i)=='^') i++; // if there is ^
			
			while (i<s.length() && Character.isDigit(s.charAt(i))) {
				power+=s.charAt(i);
				i++;
			}
			
			if (coefficient.length()==0 || (coefficient.length()==1 && coefficient.charAt(0)=='-')) { // there's no numbers before x or there's just - before x 
					coefficient+=1;
					this._coefficient=Double.parseDouble(coefficient);
				}
			else this._coefficient=Double.parseDouble(coefficient);
			
			if (power.length()==0) this._power=0; // there's no power
			this._power=Integer.parseInt(power);
			
		}
		catch(Exception e) {
			throw new RuntimeException("error");		
		}

	}
	
	public void add(Monom m) {  // and the monom to our monom
		if (this._power==m._power) {
			this._coefficient+=m._coefficient;
		}
	}
	
	public void sub(Monom m) { // sub the monom from our monom
		if (this._power==m._power) {
			this._coefficient-=m._coefficient;
		}
	}
	
	public void multipy(Monom d) { // multiply the monom from our monom
			this._coefficient=this._coefficient*d._coefficient;
			this._power+=d._power;
	}
	
	public String toString() { //print the monom
		String ans = this._coefficient+"x"+"^"+this._power;
		return ans;
	}
	// you may (always) add other methods.

	//****************** Private Methods and Data *****************
	public void set_coefficient(double a){
		this._coefficient = a;
	}
	public void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;


	@Override
	public function initFromString(String s) {
		Monom m= new Monom(s);
		return m;
	
		
	}

	@Override
	public function copy() {
		return initFromString(this.toString());

	}
	
	
	
}