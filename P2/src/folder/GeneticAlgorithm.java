package folder;
import java.util.*;


public class GeneticAlgorithm {
    private List<Population> allPopulations;
    public Population currentPopulation;
    private String[] headers = {
        "Gen",
        "Avg Gen Fitness",
        "Fit of Gen best",
        "Fit of Total Best",
        "total best contact cnt",
        "total best overlaps cnt",
        "crossover rate",
        "mutation rate"
        };

    public GeneticAlgorithm(Population p)
    {
        currentPopulation = p;
    }


    public Population selection() {
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

    private Population tournamentSelection(Population population) {
        final int k = 2;                                                                                // amount of candidates in tournament
        List<Folder> newGeneration = new ArrayList<>();

        while (newGeneration.size() < population.num) {                              // 4. until we have a new generation with same size
            List<Folder> tournamentCandidates = new ArrayList<>();
            for (int i = 0; i < k; i++) {                                                                 // 1. choose k random candidates out of population
                int aminoSelection = new SplittableRandom().nextInt(population.num);
                tournamentCandidates.add(population.getFoldingAt(aminoSelection));
            }
            newGeneration.add(tournament(tournamentCandidates));                // 2. tournament between those candidates
        }
        return new Population(newGeneration);
    }

    private Folder tournament(List<Folder> tournamentCandidates) {
        double t = 0.75;                                                                                // 75% chance of the higher fitness to win in tournament
        Folder winner = tournamentCandidates.get(0);

        for (Folder folding : tournamentCandidates) {                                                  // loop through all candidates
            if (folding.getFitness() > winner.getFitness()){
                winner = folding;                                                                           // if a candidate with a higher fitness is found override first per default
            }
            else if(t< new SplittableRandom().nextDouble())
            winner = folding;                                                                           // however: with a 25% chance of a candidate with a lower fitness override to the new one anyway
        }

        return currentPopulation.cloneFolding(winner);                                                     // 3. winner gets added to new generation
    }

    public List<Population> getAllPopulations()
    {
        return allPopulations;
    }

    private void dumpToFile()
    {
        int i = 0;
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
            ldata.add(String.format(Locale.GERMANY,"%f",p.crossoverRate)); // crossover rate
            ldata.add(String.format(Locale.GERMANY,"%f",p.mutationRate)); // mutation rate

            String[] data = ldata.toArray(new String[0]);
            dumper.writeToCSVFile(data);            
            i++;
        }

        dumper.saveCSVFile();
        new ImageGenerator(bestBestFolder, "besteFaltung.png");

        i = 0;

        for(Population p : this.allPopulations) // Iterate all populations
        {
            //p.dumpToFile("Generation" + i + ".csv");
            i++;
        }
    }

    public void run(int numOfGenerations, double maxMutationRate, double maxCrossoverRate)
    {
        allPopulations = new ArrayList<>();
        allPopulations.add(currentPopulation);
        int expectedNum = currentPopulation.num; // Set the expected number of individuals in the new population
        double mutationRate = maxMutationRate;
        double crossoverRate = maxCrossoverRate;

        // Für logging
        //CSVDumper dumper = new CSVDumper("GenerationsMain.csv", headers);
        //Folder bestOfThisGen = currentPopulation.cloneFolding(currentPopulation.getBestCandidate()); // Clone
        //Folder bestBestFolder = currentPopulation.cloneFolding(currentPopulation.getBestCandidate()); // Clone

        for(int i = 0; i < numOfGenerations; i++)
        {
            // Calculate mutation and crossover rates
            mutationRate = maxMutationRate * (1 - ((double) i / numOfGenerations));
            crossoverRate = maxCrossoverRate * (1 - ((double) i / numOfGenerations));
            
            System.out.println("Generation " + i + " of " + numOfGenerations + " (mutation rate: " + mutationRate + ", crossover rate: " + crossoverRate + ")");

            currentPopulation.crossoverRate = crossoverRate;
            currentPopulation.mutationRate = mutationRate;
            currentPopulation = tournamentSelection(currentPopulation.clonePopulation());// new population
            currentPopulation.mutate(mutationRate);
            currentPopulation.crossover(crossoverRate);
            
            // Logging
            //currentPopulation.crossoverRate = crossoverRate;
            //currentPopulation.mutationRate = mutationRate;
            
            //bestOfThisGen = currentPopulation.cloneFolding(currentPopulation.getBestCandidate()); // Clone

            //if(bestBestFolder.getFitness() < bestOfThisGen.getFitness()) // if this generations best is better than total best
            //{
            //   bestBestFolder = bestOfThisGen; // update total best
            // }

            /*ArrayList<String> ldata = new ArrayList<>();
            ldata.add(String.valueOf(i)); // gen
            ldata.add(String.format(Locale.GERMANY,"%f",currentPopulation.getAvgFitness())); // avg gen fit
            ldata.add(String.format(Locale.GERMANY,"%f",bestOfThisGen.getFitness())); // fit of gen best
            ldata.add(String.format(Locale.GERMANY,"%f",bestBestFolder.getFitness())); // fit of total best
            ldata.add(String.valueOf(bestBestFolder.getContacts())); // contacts
            ldata.add(String.valueOf(bestBestFolder.getOverlaps()));  // overlaps
            ldata.add(String.format(Locale.GERMANY,"%f",currentPopulation.crossoverRate)); // crossover rate
            ldata.add(String.format(Locale.GERMANY,"%f",currentPopulation.mutationRate)); // mutation rate

            String[] data = ldata.toArray(new String[0]);
            dumper.writeToCSVFile(data);
            */

            allPopulations.add(currentPopulation.clonePopulation());

            // Check for errors (population size is too small)
            if(currentPopulation.getFoldings().size() < expectedNum)
            {
                System.out.println("ERROR: Population size is too small!");            
            }
        }
        //dumper.saveCSVFile();

        dumpToFile(); // erstellt auch das Bild der besten Faltung
    }

}
