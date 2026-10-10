
public class ComplexNumber {
	private double realPart;
	private double iPart;
	
	public ComplexNumber(double r, double i){
		realPart = r;
		iPart = i;
	}
	public ComplexNumber sin(){
		double x = (Math.sin(realPart) * Math.cosh(iPart));
		double y =  (Math.cos(realPart) * Math.sinh(iPart));
		return (new ComplexNumber(x, y));
	}
	public ComplexNumber tan(){
		double x = (Math.sin(realPart * 2))/(Math.cos(realPart * 2) + Math.cosh(iPart*2));
		double y = (Math.sinh(realPart * 2))/(Math.cos(realPart * 2) + Math.cosh(iPart*2));
		return new ComplexNumber(x,y);
	}
	public ComplexNumber naturalExponent(){
		double x = Math.exp(realPart) * Math.cos(iPart);
		double y = Math.exp(realPart) * Math.sin(iPart);
		return new ComplexNumber(x,y);
	}
	public ComplexNumber naturalLog(){
		double x = Math.log(abs());
		double y = arg();
		return new ComplexNumber(x,y);
	}
	public ComplexNumber multiply(ComplexNumber multiplier){
		double x = (realPart * multiplier.getReal()) - (iPart * multiplier.getImaginary());
		double y = (realPart * multiplier.getImaginary()) + (iPart * multiplier.getReal());
		return new ComplexNumber(x,y);
	}
	public double toSphereX(){
		return ((realPart*2)/((realPart*realPart) + (iPart*iPart) + 1));
	}
	public double toSphereY(){
		return ((iPart*2)/((realPart*realPart) + (iPart*iPart) + 1));
	}
	public double toSphereZ(){
		double absValSquared = (realPart*realPart) + (iPart*iPart);
//		System.out.println("original");
//		System.out.println(absValSquared);
//		System.out.println("minus 1");
//		System.out.println(absValSquared - 1.0);
//		System.out.println("plus 1");
//		System.out.println(absValSquared + 1.0);
		return ((absValSquared - 1)/(absValSquared + 1));
	}
	public String toString(){
		return (realPart + " " + iPart + "i");
	}
	public double abs(){
		return (Math.sqrt((realPart * realPart) + (iPart * iPart)));
	}
	public double arg(){
		return (Math.atan2(iPart, realPart));
	}
	public double getReal(){
		return realPart;
	}
	public double getImaginary(){
		return iPart;
	}
	public ComplexNumber add(ComplexNumber adder){
		return( new ComplexNumber(realPart + adder.getReal(), iPart + adder.getImaginary()));
	}
	public ComplexNumber square(){
		return (new ComplexNumber(((realPart*realPart) - (iPart*iPart)), (2 * (iPart*realPart))));
	}
	public ComplexNumber reciprocal(){
		double x = (realPart/((realPart * realPart) + (iPart * iPart)));
		double y = (iPart/((realPart * realPart) + (iPart * iPart)));
		return new ComplexNumber(x,y);
	}
	public ComplexNumber pow(ComplexNumber power){
		return (naturalLog().multiply(power).naturalExponent());
	}
	
}
