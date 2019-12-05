package Ex1;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.function.Predicate;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{
	public LinkedList <Monom> List= new LinkedList <Monom> ();

	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		Iterator <Monom> it=iteretor();
		while (it.hasNext()) {
			it.remove();
		}
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		int i=0; int j=0; 
		if (s.charAt(j)=='-') {
			j++;
		}
		while (j<s.length()) {
			if (s.charAt(j)=='+' || s.charAt(j)=='-' ) {
				Monom m=new Monom (s.substring(i, j));
				addToList (m);
				i=j;
			}
			j++; 
		}
		if (i!=j) {
			Monom m=new Monom (s.substring(i, j));
			addToList (m);
		}


	}

	private void addToList (Monom m) {
		List.add(m);
		Monom_Comperator c = new Monom_Comperator(); 
		List.sort(c);		
	}

	public String toString () {
		String ans="";
		for (int i=0; i<List.size(); i++) {
			ans+= List.get(i).toString()+ "+";
		}
		return ans;
	}

	@Override
	public double f(double x) {
		// TODO Auto-generated method stub
		Iterator <Monom> it= iteretor();
		double sum=0;
		while(it.hasNext()) {
			Monom temp=it.next();
			sum+=temp.f(x);
		}

		return sum;
	}

	@Override
	public void add(Polynom_able p1) {
		// TODO Auto-generated method stub
		Polynom popo = (Polynom) p1;
		Iterator <Monom> it= popo.iteretor();
		while (it.hasNext()) {
			Monom temp=it.next();
			add (temp);
		}
	}

	@Override
	public void add(Monom m1) {
		// TODO Auto-generated method stub
		Iterator <Monom> it = iteretor ();
		if (!it.hasNext()) {
			addToList (m1);
		}
		else {
			Monom temp=it.next();
			while (it.hasNext() && temp.get_power()<m1.get_power()) {
				temp=it.next();
			}

			if (temp.get_power()==m1.get_power()) {
				temp.add(m1);
				it.remove(); 
				addToList (temp);

			}

			else if (!it.hasNext() || temp.get_power()>m1.get_power()) {
				addToList (m1);

			}
		}

	}

	@Override
	public void substract(Polynom_able p1) {
		// TODO Auto-generated method stub
		if (this.toString().equals(p1.toString())) {
			this.List.clear();
			this.List.add(new Monom (0,0));
			return;
		}
		Polynom popo = (Polynom) p1;
		Iterator <Monom> it= popo.iteretor();
		while (it.hasNext()) {
			Monom temp=it.next();
			temp.set_coefficient(temp.get_coefficient()*-1);
			add (temp);	

		}
	}

	@Override

	public void multiply(Polynom_able p1) {
		Polynom p2 = (Polynom) p1;
		Iterator <Monom> it2=p2.iteretor();
		//LinkedList <Monom> ans=new LinkedList <Monom> ();
		Polynom ans=new Polynom ();

		while (it2.hasNext()) {
			Iterator <Monom> it=iteretor();
			Monom temp2=it2.next();
			while (it.hasNext()) {
				Monom temp1=it.next();
				Monom temp3=new Monom (temp2);
				temp3.multipy(temp1);
				ans.add(temp3);
			}
		}
		this.List.clear();
		this.List=ans.List;
	}

	@Override
	public boolean equals(Object p1) { //LEKAABEL OBJECT
		// TODO Auto-generated method stub
		if (!(p1 instanceof Polynom)) {return false;}
		Polynom p2= (Polynom) p1;
		Iterator <Monom> it1= iteretor();
		Iterator <Monom> it2= p2.iteretor();
		while (it1.hasNext() && it2.hasNext()) {
			Monom temp1=it1.next();
			Monom temp2=it2.next();
			if (temp1.get_coefficient() != temp2.get_coefficient() || temp1.get_power()!= temp2.get_power()) {
				return false;
			}
			return true;
		}

		return false;
	}

	@Override
	public boolean isZero() {
		// TODO Auto-generated method stub
		Iterator <Monom> it= iteretor ();
		boolean check=true;
		while (it.hasNext()) {
			Monom temp=it.next();
			if (temp.get_coefficient()!=0) check=false;
		}
		return check;
	}

	@Override
	public double root(double x0, double x1, double eps) {
		// TODO Auto-generated method stub
		//assuming (f(x0)*f(x1)<=0
		double mid= (x0+x1)/2;
		double left=x0; 
		double right=x1;
		while (!(f(mid)<= eps && f(mid)>= -1*eps)) {
			if (f(mid)>0) {
				if (f(x0)>0) left=mid;
				else right=mid;
			}
			if (f(mid)<0) {
				if (f(x0)<0) left=mid;
				else right=mid;
			}
			mid=(left+right)/2;
		}
		return mid;
	}

	@Override
	public Polynom_able copy() {
		// TODO Auto-generated method stub
		Polynom copy= new Polynom ();
		Iterator <Monom> it= iteretor ();
		while (it.hasNext()) {
			Monom temp=it.next();
			copy.add(temp);
		}
		Polynom_able ans= (Polynom_able) copy;
		return ans;
	}

	@Override
	public Polynom_able derivative() {
		// TODO Auto-generated method stub
		Polynom ans= new Polynom ();
		Iterator <Monom> it= iteretor ();
		while (it.hasNext()) {
			Monom temp=it.next();
			ans.add(temp.derivative());
		}
		Polynom_able ans1= (Polynom_able) ans;
		return ans1;
	}

	@Override
	public double area(double x0, double x1, double eps) {
		// TODO Auto-generated method stub
		double area=0;
		for (double i=x0; i<=x1; i+=eps) {
			if (f(i)>0) area+=f(i)*eps;
			else  area+= -1*f(i)*eps;
		}
		return area;
	}

	@Override
	public Iterator<Monom> iteretor() {
		// TODO Auto-generated method stub
		return List.iterator();
	}

	@Override
	public void multiply(Monom m1) {
		// TODO Auto-generated method stub
		Iterator <Monom> it = iteretor ();
		LinkedList <Monom> l2=new LinkedList <Monom>();
		while (it.hasNext()) {
			Monom temp=it.next();
			temp.multipy(m1);
			it.remove();
			l2.add(temp);
			Monom_Comperator c = new Monom_Comperator(); 
			l2.sort(c);
		}
		while(!l2.isEmpty()) {
			this.List.add(l2.getFirst());
			l2.remove();
		}
	}
	@Override
	public function initFromString(String s) {
		Polynom p= new Polynom(s);
			return p;
	}

}