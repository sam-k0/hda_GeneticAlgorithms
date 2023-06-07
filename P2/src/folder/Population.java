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


    // randomize directon at one position
    private void randomizeDirection(int index)
    {
        Folder current = population.get(index);
        // Choose a random position in the current element to change the Direction
        int pos = (int)(Math.random() * current.getLength()-1);
        Element currentE = current.getHead();
        for(int i = 0; i < pos; i++)
        {
            currentE = currentE.getNext();
        }
        // Change now
        Direction oldDir = currentE.getDirection();
        Direction newDir = oldDir;
        boolean changedCorrectly = false;
        while(!changedCorrectly) // While they are the same, change
        {
            // Change now
            double newDirChance = Math.random();
            if(newDirChance < 0.33) // nach links
            {
                newDir = Direction.LEFT;
            }
            else if(newDirChance > 0.33 && newDirChance < 0.66)// nach rechts
            {
                newDir = Direction.RIGHT;
            }
            else // geradeaus
            {
                newDir = Direction.STRAIGHT;
            }

            if(oldDir != newDir)
            {
                changedCorrectly = true;
            }
        }
    }

    public void mutate(double mutationRate)
    {
        // Calculate number of elements to mutate
        int numToMutate = (int)(mutationRate * population.size());
        ArrayList<Integer> alreadyMutated = new ArrayList<Integer>();
        
        // Get a random element and mutate it
        for(int i = 0; i < numToMutate; i++)
        {
            int index = (int)(Math.random() * population.size());
            while(alreadyMutated.contains(index)) // Make sure to select 2 different
            {
                index = (int)(Math.random() * population.size());
            }
            alreadyMutated.add(index);
            randomizeDirection(index); // call the function and hope that java passes a pointer
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

    // assuming they have the same length
    // Makes a crossover between two things
    private void makeCrossoverWith(Folder oneFolder, Folder otherFolder, int crossoverPoint)
    {
        Element curr = oneFolder.getHead();
        Element otherCurr = otherFolder.getHead();
        Element temp;
        int i = 0;

        while(i < crossoverPoint)
        {
            curr = curr.getNext();
            otherCurr = otherCurr.getNext();
            i++;
        }
        temp = curr.getNext();
        curr.setNext(otherCurr.getNext());
        otherCurr.setNext(temp);
    }


/*
 // Assign index1 and index2 to two different random Foldings
        index1 = (int)(Math.random() * population.size());
        index2 = (int)(Math.random() * population.size());
        while(index1 == index2) // Make sure to select 2 different
        {
            index2 = (int)(Math.random() * population.size());
        }

        // Crossover the two selected foldings
        // Get a crossover point and make sure its not the first or last element
        int crossoverPoint = (int)(Math.random() * population.get(index1).getLength()-1);
        while(crossoverPoint == 0)
        {
            crossoverPoint = (int)(Math.random() * population.get(index1).getLength()-1);
        }
        
        makeCrossoverWith(population.get(index1), population.get(index2), crossoverPoint);
 */

    public void crossover(double crossoverRate)
    {
        // Calculate number of elements to mutate
        int numToCrossover = (int)(crossoverRate * population.size());
        int index1, index2;
        List<Integer> alreadyCrossovered = new ArrayList<Integer>();
        // Loop numToCrossover times and check that the index1 and index2 are not already selected
        for(int i = 0; i < numToCrossover; i++)
        {
            index1 = (int)(Math.random() * population.size());
            while(alreadyCrossovered.contains(index1))
            {
                index1 = (int)(Math.random() * population.size());
            }
            alreadyCrossovered.add(index1);

            index2 = (int)(Math.random() * population.size());
            while(index1 == index2 || alreadyCrossovered.contains(index2)) // Make sure to select 2 different
            {
                index2 = (int)(Math.random() * population.size());
            }
            alreadyCrossovered.add(index2);

            // Crossover the two selected foldings
            // Get a crossover point and make sure its not the first or last element
            int crossoverPoint = (int)(Math.random() * population.get(index1).getLength()-1);
            while(crossoverPoint == 0)
            {
                crossoverPoint = (int)(Math.random() * population.get(index1).getLength()-1);
            }
            
            makeCrossoverWith(population.get(index1), population.get(index2), crossoverPoint);
        }

    }
}
