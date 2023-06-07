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
    
    // Only use this for the first population
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
                if(newDir < 0.4) // nach links
                {
                    newE.setDirection(Direction.LEFT);
                }
                else if(newDir > 0.4 && newDir < 0.8)// nach rechts
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


    private void randomizeDirections()
    {
        for(Folder current : population)
        {
            Element currentE = current.getHead();
            while(true)
            {
                double doChange = Math.random();
                if(doChange >= 0.5) // Mit 50% wird was geändert
                {
                    double newDir = Math.random();
                    if(newDir < 0.4) // nach links
                    {
                        currentE.setDirection(Direction.LEFT);
                    }
                    else if(newDir > 0.4 && newDir < 0.8)// nach rechts
                    {
                        currentE.setDirection(Direction.RIGHT);
                    }
                    else // geradeaus
                    {
                        currentE.setDirection(Direction.STRAIGHT);
                    }
                }
                currentE = currentE.getNext();
                if(currentE == null)
                {break;}
            }
        }
    }

    // Makes a deep copy of the given folding
    private Folder cloneFolding(Folder folder)
    {
        Folder newFolder = new Folder(); 
        Element current = folder.getHead();

        while(true)
        {
            Element newE = new Element(current.getId(), current.getDirection(), current.getColor());

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

        // calculate some metadata
        
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

    public double getProportinalFitnessAt(int i)
    {
        return proportionalFitnesses.get(i);
    }

    public List<Folder> getFoldings()
    {
        return population;
    }

    public Folder getFoldingAt(int pos)
    {
        return population.get(pos);
    }

    // Prints info about all foldings
    public void printAllFoldingsDirections()
    {
        int i = 0;

        for(Folder f : population)
        {
            System.out.println(f.getPrintDirections() + " Fitness: " + f.getFitness() + " |Prop Fitness: " + getProportinalFitnessAt(i) + " |Total Pop Fit: "+getTotalFitness());
            i++;
        }
    }

    public Folder getBestCandidate()
    {
        Folder best = population.get(0);
        for(Folder folder : population)
        {
            if(folder.getFitness() > best.getFitness())
            {
                best = folder;
            }
        }
        return best;
    }
}
