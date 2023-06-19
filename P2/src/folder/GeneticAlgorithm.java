package folder;
import java.util.*;


public class GeneticAlgorithm {
    private List<Population> allPopulations;
    private Population currentPopulation;

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

    public Population selectionTournament(int tournamentSize)
    {
        int populationSize = currentPopulation.num;
        Random random = new Random();
        List<Folder> forNewPop = new ArrayList<>();

        // Perform tournament selection multiple times to create the new population
        for (int i = 0; i < populationSize; i++) {
            List<Folder> tournament = new ArrayList<>();

            // Select random individuals for the tournament
            for (int j = 0; j < tournamentSize; j++) {
                int randomIndex = random.nextInt(currentPopulation.num);
                Folder folder = currentPopulation.getFoldingAt(randomIndex);
                tournament.add(folder);
            }

            // Sort the tournament individuals by fitness (ascending order)
            Collections.sort(tournament, new Comparator<Folder>() {
                @Override
                public int compare(Folder folder1, Folder folder2) {
                    // Compare fitness values (assuming higher is better)
                    return Double.compare(folder1.getFitness(), folder2.getFitness());
                }
            });

            // Select the individual with the highest fitness (last in the sorted tournament list)
            Folder selectedFolder = tournament.get(tournamentSize - 1);

            // Add the selected individual to the new population
            forNewPop.add(selectedFolder);
        }

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
        "total best overlaps cnt",
        "crossover rate",
        "mutation rate"
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
            p.dumpToFile("Generation" + i + ".csv");
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

        for(int i = 0; i < numOfGenerations; i++)
        {
            // Calculate mutation and crossover rates
            mutationRate = maxMutationRate * (1 - ((double) i / numOfGenerations));
            crossoverRate = maxCrossoverRate * (1 - ((double) i / numOfGenerations));
            
            System.out.println("Generation " + i + " of " + numOfGenerations + " (mutation rate: " + mutationRate + ", crossover rate: " + crossoverRate + ")");

            currentPopulation.crossoverRate = crossoverRate;
            currentPopulation.mutationRate = mutationRate;
            currentPopulation = selectionTournament(5); // Select new population
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
