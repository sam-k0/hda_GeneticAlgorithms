package folder;
import java.util.*;

public class GeneticAlgorithm {
    private List<Population> allPopulations;
    private Population currentPopulation;

    public GeneticAlgorithm(Population p)
    {
        currentPopulation = p;
    }

    private Population evolve()
    {
        // Retrieve the list of folders from the current population
        List<Folder> populationFoldings = currentPopulation.getFoldings();
        // Retrieve average and total fitness from the current population
        double avgFitness = currentPopulation.getAvgFitness();
        double totalFitness = currentPopulation.getTotalFitness();
        // Retrieve proportional fitness values from the current population
        List<Double> propFitnesses = currentPopulation.getProportinalFitnesses();
        // Calculate cumulative fitness values
        List<Double> cumulativeFitness = new ArrayList<>();
        Double currentCum = 0.0;
        for(Double d : propFitnesses)
        {
            currentCum += d;
            cumulativeFitness.add(currentCum);
        }
        // Create a new population to store selected individuals
        List<Folder> forNewPop = new ArrayList<>();
        int expectedNum = currentPopulation.num; // Set the expected number of individuals in the new population
        int alreadyAddedNum = 0;                 // Count the number of individuals already added to the new population

        
        // Select individuals for the new population
        while(expectedNum > alreadyAddedNum) // Genug leute in die nächste gen mitnehmen
        {
            for(int i = 0; i < populationFoldings.size(); i++) // über alle Foldings aus der current pop loopen
            {
                double rand = Math.random();
                if(rand <=  cumulativeFitness.get(i))
                {
                    //System.out.println("Added! " + alreadyAddedNum + "/" + expectedNum);
                    forNewPop.add(populationFoldings.get(i));

                    alreadyAddedNum ++;
                    break;
                }   
            }
        }
        // Create and return a new population with the selected individuals
        return new Population(forNewPop);  
    }

    public List<Population> getAllPopulations()
    {
        return allPopulations;
    }

    public void run(int numOfGenerations)
    {
        allPopulations = new ArrayList<>();
        allPopulations.add(currentPopulation);

        for(int i = 0; i < numOfGenerations; i++)
        {
            currentPopulation = evolve();
            allPopulations.add(currentPopulation);
        }

        //System.out.println(allPopulations.size());
    }

}
