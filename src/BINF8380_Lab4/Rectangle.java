package BINF8380_Lab4;

public class Rectangle{
	public static double width;
	public static double height;
	Rectangle(double wid, double hght){
		 width = wid;
		 height = hght;
	}
	public double getArea() {
		return width * height;
	}
	public double getPerimeter() {
		return (2*width) + (2* height); 
	}
	
	public static void main(String[] args) throws Exception
	{
		Rectangle r = new Rectangle(10, 5);
		System.out.println(r.getArea());  //50
		System.out.println(r.getPerimeter());  // 30
	}
}
