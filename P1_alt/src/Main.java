
import java.io.*;
import folder.*;
import folder.Element.Color;
import folder.Element.Direction;

public class Main
{
	public static void main(String args[])
	{
		System.out.println("Amogir");
		Folder f = new Folder();

		f.setCoord(8, 15, new Element(Direction.up, Color.black,0));
		f.setCoord(8, 14, new Element(Direction.left, Color.white,1));
		f.setCoord(7, 14, new Element(Direction.down, Color.black,2));
		f.setCoord(7, 15, new Element(Direction.left, Color.black,3));

		f.setCoord(6, 15, new Element(Direction.up, Color.white,4));
		f.setCoord(6, 14, new Element(Direction.up, Color.white,5));
		f.setCoord(6, 13, new Element(Direction.right, Color.white,6));
		f.setCoord(7, 13, new Element(Direction.empty, Color.black,7));

		System.out.println(f.berechneFitness());

		Folder f2 = new Folder();
		f2.setCoord(8, 8, new Element(Direction.up, Color.black, 0));
		f2.setCoord(8, 7, new Element(Direction.left, Color.white, 1));
		f2.setCoord(7, 7, new Element(Direction.down, Color.black, 2));
		f2.setCoord(7, 8, new Element(Direction.left, Color.black, 3));
		f2.setCoord(6, 8, new Element(Direction.down, Color.white, 4));
		f2.setCoord(6, 9, new Element(Direction.right, Color.white, 5));
		f2.setCoord(7, 9, new Element(Direction.up, Color.white, 6));
		f2.setCoord(7, 8, new Element(Direction.empty, Color.white,7));
		System.out.println(f2.berechneFitness());;
	}
}


