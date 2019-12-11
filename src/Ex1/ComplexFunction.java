package Ex1;

import java.util.Objects;


public class ComplexFunction implements complex_function {
	public static void main(String arg[]){
		String s="mul(min(21x,7x),8x))";
		ComplexFunction f= new ComplexFunction(s);
		System.out.println(f.toString());
		System.out.println(f.f(1));

		Monom m = new Monom("2x");
		Polynom p = new Polynom("2x^2");
		ComplexFunction cf = new ComplexFunction(Operation.Plus,p,m);
		System.out.println(cf.toString());

		ComplexFunction r = new ComplexFunction(Operation.Times,cf,m);
		System.out.println(r);

//		ComplexFunction cc = new ComplexFunction(cf.copy());
//		System.out.println(cc.toString());
//		String s4="plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.2999999999999998x+5.0)";
//		String s3="plus(div(1.0x+1.0,mul(mul(1.0x+3.0,1.0x-2.0),1.0x-4.0)),2.0)";
//		String s0="div(plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.2999999999999998x+5.0),-1.0x^4+2.4x^2+3.1)";
//		String s5="-1.0x^4+2.4x^2+3.1";
//		String s6="0.1x^5-1.2999999999999998x+5.0";
//		String s7="max(max(max(max(plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.2999999999999998x+5.0),plus(div(1.0x+1.0,mul(mul(1.0x+3.0,1.0x-2.0),1.0x-4.0)),2.0)),div(plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.2999999999999998x+5.0),-1.0x^4+2.4x^2+3.1)),-1.0x^4+2.4x^2+3.1),0.1x^5-1.2999999999999998x+5.0)";
//		String s8="min(min(min(min(plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.2999999999999998x+5.0),plus(div(1.0x+1.0,mul(mul(1.0x+3.0,1.0x-2.0),1.0x-4.0)),2.0)),div(plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.2999999999999998x+5.0),-1.0x^4+2.4x^2+3.1)),-1.0x^4+2.4x^2+3.1),0.1x^5-1.2999999999999998x+5.0)";
//ComplexFunction f4= new ComplexFunction(s4);
//		System.out.println(f4.toString());

	}

	private Operation op= Operation.None; //HAVE TO BE STRING
	private function left, right=null;
	public ComplexFunction() {

	}
	public ComplexFunction(String s) {
		function f=initFromString(s);
		if (f instanceof ComplexFunction) {
			ComplexFunction c = (ComplexFunction) f;
			this.op = c.op;
			this.left = c.left;
			this.right = c.right;
		}
		else{
			this.op = Operation.None;
			this.left = f;

		}
	}

	public ComplexFunction(String op, function left, function right) {
		this.op=checkWhichOperation(op);
		this.left=left;
		this.right=right;

	}
	public ComplexFunction(Operation op, function left, function right) {
		this.op=op;
		this.left=left.copy();
		this.right=right.copy();

	}

	public ComplexFunction(function left) {
		this.left=left.copy();
	}

	public ComplexFunction(function left,function right) {
		this.right=right;
		this.left=left;
	}
	public ComplexFunction(Operation op, function left) {
		this.left=left;
		this.op=op;
	}
	public ComplexFunction(String op, function left) {
		this.left=left;
		this.op=checkWhichOperation(op);
	}

	@Override
	public String toString() {
		if(this.right==null){
			return ""+this.left;
		}
		switch (getOp()) {
			case Times: {
				return "Mul(" + this.left + "," + this.right + ")";
			}
			default:
				return this.op + "(" + this.left + "," + this.right + ")";
		}
	}

	@Override
	public double f(double x) {

		switch (this.op){
			case Max:return Math.max(left.f(x),right.f(x));
			case Min:return Math.min(left.f(x),right.f(x));
			case Comp:return left.f(right.f(x));
			case None:{
				if (right == null) {
					return left.f(x);
				}else {
					throw new RuntimeException("There is no Operation to calculate between the functions");
				}
			}
			case Plus:return left.f(x)+right.f(x);
			case Divid:{
				if(right.f(x)==0){ throw new RuntimeException("divide by 0");}
				else{
					return left.f(x)/right.f(x);
				}
			}
			case Times:return left.f(x)*right.f(x);

			case Error: {
				throw new RuntimeException("ERROR OP IS EROOR");
			}
			default :
				return this.f(x);
		}
	}

	public String stringOp (String s) {
		String t="";
		int i=0;
		while(i<s.length()&&s.charAt(i)!='(') {
			t+=s.charAt(i);
			i++;
		}
		return t;
	}
	public String stringLeft(String s) {
		int counter=0;
		int start=0;
		int end=0;
		 for(int i=0;(i<s.length());i++){
		 	if(s.charAt(i)=='('){
		 		counter++;
		 		if(start==0){
		 			start=i+1;
				}
			}
		 	if(s.charAt(i)==','){
		 		counter--;
		 		if(counter==0){
		 			end=i;
		 			break;
				}
			}
		 }

		return s.substring(start,end);


	}public String stringRight(String s) {
		int counter=0;
		int start=0;
		int end=0;
		for(int i=0;(i<s.length());i++){
			if(s.charAt(i)=='('){
				counter++;
				if(start==0){
					start=i+1;
				}
			}
			if(s.charAt(i)==','){
				counter--;
				if(counter==0){
					end=i;
					break;
				}
			}
		}

		return s.substring(end+1,s.length()-2);



	}
	public Operation checkWhichOperation(String s){
		s=s.toLowerCase();
		switch(s){
			case ("plus"): {
				return Operation.Plus;
			}
			case ("mul"): {
				return Operation.Times;
			}
			case ("div"): {
				return  Operation.Divid;
			}
			case ("max"): {
				return Operation.Max;
			}
			case ("min"):{
				return Operation.Min;
			}
			case ("comp"): {
				return Operation.Comp;
			}
			case (""): {// change need to be without operation
				return Operation.None;
			}
			default:{
				return Operation.Error;
			}
		}
	}

	@Override
	public function initFromString(String s) {
		s=s.replaceAll(" ", "");


			try{
			Polynom p = new Polynom(s);
			return p;
		}catch (Exception e) {
			}
		ComplexFunction f= new ComplexFunction();


		String operation= stringOp(s);
		String left=stringLeft(s);
		String right=stringRight(s);


		f.op=checkWhichOperation(operation);
		if (isPoly(right)){
			f.right = new Polynom(right);
		}
		else {
			f.right = new ComplexFunction();
			f.right=initFromString(right);
		}
		if (isPoly(left)){
			f.left = new Polynom(left);
		}
		else {
			f.left = new ComplexFunction();
			f.left=initFromString(left);
		}

		return f;


	}

	private boolean isPoly(String s){
		try{
			new Polynom(s);
			return true;
		} catch (Exception x){
			return false;
		}
	}

	@Override
	public function copy() {
		 //return initFromString(this.toString());
		if(right() == null) {
			return new ComplexFunction(left.copy());
		}else {
			ComplexFunction copy = new ComplexFunction(this.op,left().copy(),right().copy());
			return copy;
		}
	}

	@Override
	public void plus(function f1) {
		function temp = this.copy();
		this.op = Operation.Plus;
		this.right=f1;
		this.left=temp;

	}

	@Override
	public void mul(function f1) { //MULTIPLY
		function temp=this.copy();
		this.op = Operation.Times;
		this.right=f1;
		this.left=temp.copy();

	}

	@Override
	public void div(function f1) {
		function temp= this.copy();
		this.op = Operation.Divid;
		this.right=f1;
		this.left=temp;

	}

	@Override
	public void max(function f1) {
		function temp= this.copy();
		this.op = Operation.Max;
		this.right=f1;
		this.left=temp;

	}

	@Override
	public void min(function f1) {
		ComplexFunction temp= (ComplexFunction) this.copy();
		this.op = Operation.Min;
		this.right=f1;
		this.left=temp;

	}

	@Override
	public void comp(function f1) { //f(g(x))
		ComplexFunction temp= (ComplexFunction) this.copy();
		this.op = Operation.Comp;
		this.right=f1;
		this.left=temp;

	}

	@Override
	public function left() {
		return this.left;
	}

	@Override
	public function right() {

		return this.right;
	}

	@Override
	public Operation getOp() {
		return this.op;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false; // not the same object
		ComplexFunction that = (ComplexFunction) o; //casting to complex function CAN I?????
		for(int i=-100;i<100;i++){
			if (this.f(i)!=that.f(i)) return false;
		}
		return true;
	}

}
