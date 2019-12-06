package Ex1;

import java.util.Objects;

public class ComplexFunction implements complex_function {

	private Operation op=Operation.None; //HAVE TO BE STRING
	private function left, right=null;
	private ComplexFunction() {
		
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
		int end=0;
		int start=s.indexOf('(');
		int counter=1;
		for (int i=start+1;i<s.length();i++) {
			if(s.charAt(i)=='(') {
				counter++;
			}
			if (s.charAt(i)==',') {
				counter--;
			}
			if(counter==0) {
				end=s.charAt(i);
				
			}
		}
		
		return s.substring(start+1, end-1);
	}
	public String stringRight(String s) {
		int end=0;
		int start=s.indexOf('(');
		int counter=1;
		for (int i=start+1;i<s.length();i++) {
			if(s.charAt(i)=='(') {
				counter++;
			}
			if (s.charAt(i)==',') {
				counter--;
			}
			if(counter==0) {
				end=s.charAt(i);
				
			}
		}
		return s.substring(end+1, s.length());
			
		
	}
	public Operation checkWhichOperation(String s){
		switch(s.charAt(0)){
			case 'P': {
				return Operation.Plus;
			}
			case 'T': {
				return Operation.Times;
			}
			case 'D': {
				return  Operation.Divid;
			}
			case 'M': {
				if (s.charAt(1)=='a') {
					return Operation.Max;
				}
				else{
					return Operation.Min;
				}

			}
			case 'C': {
				return Operation.Comp;
			}
			case 'N': {
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
		f.right=initFromString(right);
		f.left=initFromString(left);
		return f;


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
		for(int i=0;i<100;i++){
			if (this.f(i)!=that.f(i)) return false;
			double negative= (-1)*i;
			if(this.f(negative)!=that.f(negative)) return false;
		}
		return true;
	}

}
