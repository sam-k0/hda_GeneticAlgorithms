
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

		f.setCoord(8, 15, new Element(Direction.up, Color.white));
		f.setCoord(8, 14, new Element(Direction.left, Color.black));
		f.setCoord(7, 14, new Element(Direction.up, Color.white));
		f.setCoord(7, 13, new Element(Direction.right, Color.white));
		f.setCoord(8, 13, new Element(Direction.right, Color.black));
		f.setCoord(9, 13, new Element(Direction.down, Color.white));
		f.setCoord(9, 14, new Element(Direction.right, Color.black));
		f.setCoord(10, 14, new Element(Direction.right, Color.black));

		f.printme();

		System.out.println(f.berechneFitness());
	}
}


