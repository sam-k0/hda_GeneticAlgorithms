import folder.*;
import folder.Element.Direction;
import folder.Element.EColor;
public class Main
{
	public static void main(String args[])
	{
		//System.out.println("Amogir");

		/**Folder f = new Folder();
		
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

		Population p = new Population(f,1);

		new ImageGenerator(p.getFoldingAt(0), "Amogus1.png");

		p.randomizeDirection(0);

		new ImageGenerator(p.getFoldingAt(0) , "amogus2.png");

/*
		Folder f2 = new Folder();
		f2.addElement(new Element(0, Direction.STRAIGHT, EColor.BLACK));
		f2.addElement(new Element(1, Direction.LEFT, EColor.WHITE));
		f2.addElement(new Element(2, Direction.LEFT, EColor.BLACK));
		f2.addElement(new Element(3, Direction.RIGHT, EColor.BLACK));
		f2.addElement(new Element(4, Direction.LEFT, EColor.WHITE));
		f2.addElement(new Element(5, Direction.LEFT, EColor.WHITE));
		f2.addElement(new Element(6, Direction.LEFT, EColor.WHITE));
		f2.addElement(new Element(7, Direction.STRAIGHT, EColor.BLACK));
		*/


// Running GA
/* */
		Folder f = new Folder(Examples.SEQ50);

		Population population = new Population(f, 200);
		//population.printAllFoldingsDirections();
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(population);

		geneticAlgorithm.run(1000,0.05f, 0.05f);

		int popnum = 0;
		int fnum = 0;

		/* Printing all Foldings../
		for(Population pp : geneticAlgorithm.getAllPopulations())
		{
			for(Folder ff : pp.getFoldings())
			{
				ImageGenerator ig = new ImageGenerator(ff, "image"+String.valueOf(popnum)+"_"+String.valueOf(fnum)+".png");
				fnum++;
			}
			popnum++;
			fnum = 0;
		}
/**/


	/*
		for(int i = 0; i < 3; i++)
		{
			Population p = geneticAlgorithm.getAllPopulations().get(i);
			//System.out.println(p.avgFitness);
			System.out.println("------ avgf: " + p.getAvgFitness() + " total " + p.totalFitness);
			
			for(Folder ff : p.getFoldings())
			{
				ImageGenerator ig = new ImageGenerator(ff, "image"+String.valueOf(imgcnt)+".png");

				imgcnt++;

			}

		}
	*/


// Testing crossover
/*
		Folder t1 = new Folder("100101");
		Folder t2 = new Folder("010110");

		ArrayList<Folder> l1 = new ArrayList<Folder>();

		ImageGenerator ig11 = new ImageGenerator(t1, "image1.png");
		ImageGenerator ig22 = new ImageGenerator(t2, "image2.png");

		l1.add(t1);
		l1.add(t2);

		Population p = new Population(l1);
		p.makeCrossoverWith(t1, t2, 3);

		ImageGenerator ig = new ImageGenerator(t1, "image11.png");
		ImageGenerator ig2 = new ImageGenerator(t2, "image22.png");
*/
	}
}


