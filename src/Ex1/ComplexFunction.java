package Ex1;

import java.util.Objects;


public class ComplexFunction implements complex_function {
	public static void main(String arg[]){
		String s="mul(min(21x,7x),8x))";
		ComplexFunction f= new ComplexFunction(s);
		System.out.println(f.toString());
		System.out.println(f.f(1));
	}

	private Operation op; //HAVE TO BE STRING
	private function left, right=null;
	public ComplexFunction() {
		op = Operation.None;
		left = new Polynom();
		right = new Polynom();
	}
	public ComplexFunction(String s){
		ComplexFunction c =(ComplexFunction) initFromString(s);
		this.op=c.op;
		this.left=c.left;
		this.right=c.right;
	}
	public ComplexFunction(String op, function left, function right) {
		this.op=checkWhichOperation(op);
		this.left=left;
		this.right=right;

	}
	public ComplexFunction(Operation op, function left, function right) {
		this.op=op;
		this.left=left;
		this.right=right;

	}

	public ComplexFunction(function left) {
		this.left=left;
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
		return this.op+"("+this.left+","+this.right+")";

	}

	@Override
	public double f(double x) {

		switch (op){
			case Max:return Math.max(left.f(x),right.f(x));
			case Min:return Math.min(left.f(x),right.f(x));
			case Comp:return left.f(right.f(x));
			case None:throw new RuntimeException("None action");
			case Plus:return left.f(x)+right.f(x);
			case Divid:{
				if(right.f(x)==0){ throw new RuntimeException("divide by 0");}
				else{
					return left.f(x)/right.f(x);
				}
			}
			case Times:return left.f(x)*right.f(x);
			case Error:
			default:
				throw new RuntimeException("ERROR OP IS EROOR");
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


	}
	public String stringRight(String s) {
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
		try{
			Polynom p = new Polynom(s);
			return p;
		}catch (Exception e){

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
		 return initFromString(this.toString());

	}

	@Override
	public void plus(function f1) {
		ComplexFunction temp= (ComplexFunction) this.copy();
		this.op = Operation.Plus;
		this.right=f1;
		this.left=temp;

	}

	@Override
	public void mul(function f1) { //MULTIPLY
		ComplexFunction temp= (ComplexFunction) this.copy();
		this.op = Operation.Times;
		this.right=f1;
		this.left=temp;

	}

	@Override
	public void div(function f1) {
		ComplexFunction temp= (ComplexFunction) this.copy();
		this.op = Operation.Divid;
		this.right=f1;
		this.left=temp;

	}

	@Override
	public void max(function f1) {
		ComplexFunction temp= (ComplexFunction) this.copy();
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
