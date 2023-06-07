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


    public Population selection2() {
        double totalFitness = 0.0;

        Random random = new Random();

        for (Folder folding : currentPopulation.getFoldings()) {
            totalFitness += folding.getFitness();
        }

        List<Folder> selectedFoldings = new ArrayList<>();
        int populationSize = currentPopulation.num;

        for (int i = 0; i < populationSize; i++) {
            double randomFitness = random.nextDouble() * totalFitness;
            double cumulativeFitness = 0.0;

            for (Folder folding : currentPopulation.getFoldings()) {
                cumulativeFitness += folding.getFitness();
                if (cumulativeFitness >= randomFitness) {
                    selectedFoldings.add(folding);
                    break;
                }
            }
        }

        return new Population(selectedFoldings);
    }

    private Population selection()
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
                if(rand <= cumulativeFitness.get(i))
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
            ldata.add(String.format(Locale.GERMANY,"%f",p.getAvgFitness())); // avg gen fit
            ldata.add(String.format(Locale.GERMANY,"%f",bestOfThisGen.getFitness())); // fit of gen best
            ldata.add(String.format(Locale.GERMANY,"%f",bestBestFolder.getFitness())); // fit of total best
            ldata.add(String.valueOf(bestBestFolder.getContacts())); // contacts
            ldata.add(String.valueOf(bestBestFolder.getOverlaps()));  // overlaps


            String[] data =  ldata.toArray(new String[0]);
            dumper.writeToCSVFile(data);            
            i++;
        }

        dumper.saveCSVFile();
        ImageGenerator bestimg = new ImageGenerator(bestBestFolder, "besteFaltung.png");
    }

    public void run(int numOfGenerations, double mutationRate, double crossoverRate)
    {
        allPopulations = new ArrayList<>();
        allPopulations.add(currentPopulation);
        int expectedNum = currentPopulation.num; // Set the expected number of individuals in the new population

        for(int i = 0; i < numOfGenerations; i++)
        {
            currentPopulation = selection2(); // Select new population
            currentPopulation.mutate(mutationRate);
            currentPopulation.crossover(crossoverRate);
            allPopulations.add(currentPopulation);

            // Check for errors (population size is too small)
            if(currentPopulation.getFoldings().size() < expectedNum)
            {
                System.out.println("ERROR: Population size is too small!");            
            }
        }

        dumpToFile(); // erstellt auch das Bild der besten Faltung
    }

}
