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
		
		System.out.println("Hamming dist:" + f.calculateHammingDistance(2891, 3927));

		// Aufg. 1
		f.addElement(new Element(0, Direction.STRAIGHT, Color.BLACK));
		f.addElement(new Element(1, Direction.LEFT, Color.WHITE));
		f.addElement(new Element(2, Direction.LEFT, Color.BLACK));
		f.addElement(new Element(3, Direction.RIGHT, Color.BLACK));
		f.addElement(new Element(4, Direction.RIGHT, Color.WHITE));
		f.addElement(new Element(5, Direction.STRAIGHT, Color.WHITE));
		f.addElement(new Element(6, Direction.RIGHT, Color.WHITE));
		f.addElement(new Element(7, Direction.STRAIGHT, Color.BLACK));

		System.out.println(f.getFitness());


		Folder f2 = new Folder();
		f2.addElement(new Element(0, Direction.STRAIGHT, Color.BLACK));
		f2.addElement(new Element(1, Direction.LEFT, Color.WHITE));
		f2.addElement(new Element(2, Direction.LEFT, Color.BLACK));
		f2.addElement(new Element(3, Direction.RIGHT, Color.BLACK));
		f2.addElement(new Element(4, Direction.LEFT, Color.WHITE));
		f2.addElement(new Element(5, Direction.LEFT, Color.WHITE));
		f2.addElement(new Element(6, Direction.LEFT, Color.WHITE));
		f2.addElement(new Element(7, Direction.STRAIGHT, Color.BLACK));

		System.out.println(f2.getFitness());


	}
}


