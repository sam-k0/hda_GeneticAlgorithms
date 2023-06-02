package folder;
import java.util.*;
import folder.CSVDumper;

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

    private void dumpToFile()
    {
        int i = 0;
        String[] headers = {
        "Gen",
        "Avg Gen Fitness",
        "Fit of Gen best",
        "Fit of Total Best",
        "total best contact cnt",
        "total best overlaps cnt"
        };

        CSVDumper dumper = new CSVDumper("Generations.csv", headers);
        
        Folder bestBestFolder = allPopulations.get(0).getBestCandidate();
        Folder bestOfThisGen = allPopulations.get(0).getBestCandidate();
        for(Population p : this.allPopulations) // Iterate all populations
        {
            ArrayList<String> ldata = new ArrayList<>();

            // add values
            bestOfThisGen = p.getBestCandidate(); // Get best of this generation
            if(bestBestFolder.getFitness() < bestOfThisGen.getFitness()) // if this generations best is better than total best
            {
                bestBestFolder = bestOfThisGen; // update total best
            }

            ldata.add(String.valueOf(i)); // gen
            ldata.add(String.valueOf(p.getAvgFitness())); // avg gen fit
            ldata.add(String.valueOf(bestOfThisGen.getFitness())); // fit of gen best
            ldata.add(String.valueOf(bestBestFolder.getFitness())); // fit of total best
            ldata.add(String.valueOf(bestBestFolder.getContacts())); // contacts
            ldata.add(String.valueOf(bestBestFolder.getOverlaps()));  // overlaps


            String[] data =  ldata.toArray(new String[0]);
            dumper.writeToCSVFile(data);            
            i++;
        }

        dumper.saveCSVFile();
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

        dumpToFile();
    }

}
