import folder.*;
import folder.Element.Color;
import folder.Element.Direction;

import java.io.*;

public class Main
{
	public static void main(String args[])
	{
		System.out.println("Amogir");


		Folder f = new Folder();

		f.addElement(new Element(0, Direction.STRAIGHT, Color.BLACK));
		f.addElement(new Element(1, Direction.LEFT, Color.WHITE));
		f.addElement(new Element(2, Direction.LEFT, Color.BLACK));
		f.addElement(new Element(3, Direction.RIGHT, Color.BLACK));
		f.addElement(new Element(4, Direction.RIGHT, Color.WHITE));
		f.addElement(new Element(5, Direction.STRAIGHT, Color.WHITE));
		f.addElement(new Element(6, Direction.RIGHT, Color.WHITE));
		f.addElement(new Element(7, Direction.STRAIGHT, Color.BLACK));

		//f.

	}
}


