package Ex1;

public class ComplexFunction implements complex_function {

	private Operation op=Operation.None;
	private function left, right=null;
	private ComplexFunction() {
		
	}
	public ComplexFunction(Operation op, function left, function right) {
		setOperation(op);
		setLeft(left);
		setRight(right);

	}

	public ComplexFunction(function left) {
		setLeft(left);
	}

	public ComplexFunction(function left,function right) {
		setRight(right);
		setLeft(left);
	}
	public ComplexFunction(Operation op, function left) {
		setLeft(left);
		setOperation(op);
	}
	public function getLeft() {
		return this.left;
	}
	public void setLeft (function left) {
		this.left=left;
	}
	public function getRight() {
		return this.right;
	}
	public void setRight(function right) {
		this.right=right;
	}
	public Operation getOperation() {
		return this.op;
	}
	public void setOperation (Operation op) {
		this.op=op;
	}


	@Override
	public double f(double x) {

		return 0;
	}
	
	public String stringOp (String s) {
		String t="";
		int i=0;
		while(s.charAt(i)!='(') {
			t+=s.charAt(i);
		}
		return t;
	}
	public String stringLeft(String s) {
		int end=0;
		int start=s.indexOf('(');
		int counter=1;
		for (int i=start+1;counter==0;i++) {
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
		for (int i=start+1;counter==0;i++) {
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

	@Override
	public function initFromString(String s) {
		ComplexFunction f= new ComplexFunction();
		String 
		


		return null;
	}

	@Override
	public function copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void plus(function f1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mul(function f1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void div(function f1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void max(function f1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void min(function f1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void comp(function f1) {
		// TODO Auto-generated method stub

	}

	@Override
	public function left() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public function right() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Operation getOp() {
		// TODO Auto-generated method stub
		return null;
	}


}
