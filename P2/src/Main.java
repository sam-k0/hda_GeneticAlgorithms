import folder.*;
import folder.Element.Color;
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
		f.addElement(new Element(0, Direction.STRAIGHT, Color.BLACK));
		f.addElement(new Element(1, Direction.LEFT, Color.WHITE));
		f.addElement(new Element(2, Direction.LEFT, Color.BLACK));
		f.addElement(new Element(3, Direction.RIGHT, Color.BLACK));
		f.addElement(new Element(4, Direction.RIGHT, Color.WHITE));
		f.addElement(new Element(5, Direction.STRAIGHT, Color.WHITE));
		f.addElement(new Element(6, Direction.RIGHT, Color.WHITE));
		f.addElement(new Element(7, Direction.STRAIGHT, Color.BLACK));

		System.out.println("Fit:"+f.getFitness());
		System.out.println("Over.:"+f.getOverlaps());

		System.out.println("----------");

		Population population = new Population(f, 100);
		//population.printAllFoldingsDirections();
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(population);

		geneticAlgorithm.run(100);


		for(int i = 0; i < 100; i++)
		{
			Population p = geneticAlgorithm.getAllPopulations().get(i);
			//System.out.println(p.avgFitness);
			System.out.println("------ avgf: " + p.getAvgFitness() + " total " + p.totalFitness);
			
			for(Folder ff : p.getFoldings())
			{
				//System.out.println(ff.getPrintDirections());
			}

		}

	}
}


