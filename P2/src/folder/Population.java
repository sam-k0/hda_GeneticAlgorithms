package folder;
import java.util.*;

import javax.print.Doc;

import folder.Element.Direction;

public class Population {
    private List<Folder> population;
    public int num;
    public double totalFitness;
    public double avgFitness;

    List<Double> proportionalFitnesses = new ArrayList<>();
    
    private Folder cloneFoldingAndRandomizeDir(Folder folder)
    {
        Folder newFolder = new Folder();
         
        Element current = folder.getHead();

        while(true)
        {
            Element newE = new Element(current.getId(), current.getDirection(), current.getColor());
            // randomize dir
            double doChange = Math.random();
            if(doChange >= 0.5) // Mit 50% wird was geändert
            {
                double newDir = Math.random();
                if(newDir < 0.3) // nach links
                {
                    newE.setDirection(Direction.LEFT);
                }
                else if(newDir > 0.3 && newDir < 0.6)// nach rechts
                {
                    newE.setDirection(Direction.RIGHT);
                }
                else // geradeaus
                {
                    newE.setDirection(Direction.STRAIGHT);
                }
            }

            newFolder.addElement(newE);            
            current = current.getNext(); // In der original einen Element weiter gehen
            
            if(current == null) // end of sequence
            {break;}
        }

        return newFolder;

    }

    public Population(Folder baseFolding, int populationSizeWanted)
    {
        num = populationSizeWanted;

        population = new ArrayList<Folder>();

        for(int i = 0; i < num; i++)
        {
            population.add(cloneFoldingAndRandomizeDir(baseFolding));
        }

        System.out.println("Created randomized pop with " + population.size() + " Foldings");
    }

    public Population(List<Folder> population)
    {
        this.population = population;
        this.num = population.size();


        double tot = 0.0;
        for(Folder current : population) // Fitness berechnen für alle
        {
            tot += current.getFitness();
        }
        totalFitness = tot;


        // avg
        this.avgFitness = (double)tot/(double)population.size();

        // calc prop fitnesses in same order als foldings
        for(Folder currentFolding : population)
        {
            proportionalFitnesses.add(currentFolding.getFitness() / totalFitness);
        }
        
    }

    public double getTotalFitness()
    {
        return totalFitness;
    }

    public double getAvgFitness()
    {
        return avgFitness;
    }

    public List<Double> getProportinalFitnesses()
    {
        return proportionalFitnesses;
    }

    public List<Folder> getFoldings()
    {
        return population;
    }

    public Folder getFoldingAt(int pos)
    {
        return population.get(pos);
    }
}
