import folder.*;
import folder.Element.EColor;
import folder.Element.Direction;

import java.io.*;

public class Main
{
	public static void main(String args[])
	{
		//System.out.println("Amogir");

		Folder f = new Folder();
		
		//System.out.println("Hamming dist:" + f.calculateHammingDistance(2891, 3927));

		// Aufg. 1
		f.addElement(new Element(0, Direction.STRAIGHT, EColor.BLACK));
		f.addElement(new Element(1, Direction.LEFT, EColor.WHITE));
		f.addElement(new Element(2, Direction.LEFT, EColor.BLACK));
		f.addElement(new Element(3, Direction.RIGHT, EColor.BLACK));
		f.addElement(new Element(4, Direction.RIGHT, EColor.WHITE));
		f.addElement(new Element(5, Direction.STRAIGHT, EColor.WHITE));
		f.addElement(new Element(6, Direction.RIGHT, EColor.WHITE));
		f.addElement(new Element(7, Direction.STRAIGHT, EColor.BLACK));


		Folder f2 = new Folder();
		f2.addElement(new Element(0, Direction.STRAIGHT, EColor.BLACK));
		f2.addElement(new Element(1, Direction.LEFT, EColor.WHITE));
		f2.addElement(new Element(2, Direction.LEFT, EColor.BLACK));
		f2.addElement(new Element(3, Direction.RIGHT, EColor.BLACK));
		f2.addElement(new Element(4, Direction.LEFT, EColor.WHITE));
		f2.addElement(new Element(5, Direction.LEFT, EColor.WHITE));
		f2.addElement(new Element(6, Direction.LEFT, EColor.WHITE));
		f2.addElement(new Element(7, Direction.STRAIGHT, EColor.BLACK));
		
		System.out.println("Fit:"+f.getFitness());
		System.out.println("Over.:"+f.getOverlaps());

		System.out.println("----------");

		//ImageGenerator ig = new ImageGenerator(f2);

		

		Population population = new Population(f, 100);
		//population.printAllFoldingsDirections();
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(population);

		geneticAlgorithm.run(4);

		int imgcnt = 0;

		/*for(int i = 0; i < 3; i++)
		{
			Population p = geneticAlgorithm.getAllPopulations().get(i);
			//System.out.println(p.avgFitness);
			System.out.println("------ avgf: " + p.getAvgFitness() + " total " + p.totalFitness);
			
			for(Folder ff : p.getFoldings())
			{
				ImageGenerator ig = new ImageGenerator(ff, "image"+String.valueOf(imgcnt)+".png");

				imgcnt++;

			}

		}*/

		
		

	}
}


