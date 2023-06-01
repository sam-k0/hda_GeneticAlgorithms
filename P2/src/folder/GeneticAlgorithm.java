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
        List<Folder> populationFoldings = currentPopulation.getFoldings();

        double avgFitness = currentPopulation.getAvgFitness();
        double totalFitness = currentPopulation.getTotalFitness();

        List<Double> propFitnesses = currentPopulation.getProportinalFitnesses();

        List<Double> cumulativeFitness = new ArrayList<>();
        Double currentCum = 0.0;

        List<Folder> forNewPop = new ArrayList<>();
        int expectedNum = currentPopulation.num;
        int alreadyAddedNum = 0;

        for(Double d : propFitnesses)
        {
            currentCum += d;
            cumulativeFitness.add(currentCum);
        }

        while(expectedNum > alreadyAddedNum) // Genug leute in die nächste gen mitnehmen
        {
            for(int i = 0; i < populationFoldings.size(); i++) // über alle Foldings aus der current pop loopen
            {
                double rand = Math.random();
                if(rand <=  cumulativeFitness.get(i))
                {
                    System.out.println("Added! " + alreadyAddedNum + "/" + expectedNum);
                    forNewPop.add(populationFoldings.get(i));

                    alreadyAddedNum ++;
                    break;
                }   
            }
        }
        
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
