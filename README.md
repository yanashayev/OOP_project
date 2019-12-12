# Object-Oriented project 

![Imgur](https://i.imgur.com/80iIGs7.png)

# Introduction:

This project is part of assignment in Ariel University. Main purpose of the project is to create instances of Monom or Polynom or ComplexFunction and drawing those instances on graph by f(x) values.

## Monom Class:
Monom - mathematical function represent by ax^b, where a real number and b is an none negative integer.

```
Monom m= new Monom ("7x^2");
int x=1;
double result=m.f(x);   //result= 49.0
```

## Polynom Class:
Polynom - form collection of monoms.

```
Polynom p= new Polynom("x^2+3x+1"); 
int x=1;  
double result= p.f(x); //result=5.0
```

## ComplexFunction Class:

ComplexFunction - shape of - Operation(function , function) - when function is Monom, Polynom or ComplexFunction.

```
int x=1;

ComplexFunction c= new ComplexFunction("plus(7x^2,x^2+3x+1)")
double result=c.f(x);//result=54.0
ComplexFunction c1= new ComplexFunction("plus(mul(3x,2x),div(10x,2))");
double result1=c1.f(x);//result=11.0

```

## Fuction_GUI Class:

Function_GUI - class thats can hold a collection of functions, write and read the functions to file and show them on GUI.
```

Functions_GUI fg = new Functions_GUI();
String s = "plus(mul(3x,2x),div(10x,2))";
function f = new ComplexFunction().initFromString(s); // make it 6.0x^2+5.0x
fg.add(f);
int w=1000, h=600, res=200;
Range rx = new Range(-10,10);
Range ry = new Range(-5,15);
fg.drawFunctions(w,h,rx,ry,res); //draw the function f
// can draw it with defult cunstractor:
Functions_GUI fg = new Functions_GUI();
String s = "plus(mul(3x,2x),div(10x,2))";
function f = new ComplexFunction().initFromString(s); // make it 6.0x^2+5.0x
fg.add(f);
fg.drawFunctions();

```
# NOTE: More details about classes and interfaces of the project can be found on Wiki
