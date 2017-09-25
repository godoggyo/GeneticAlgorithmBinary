import java.util.ArrayList;
import java.util.Random;

public class Population {

    private String target;

    private Individual[] population;

    private double mutationRate;

    public Population(String target, int size, double mutationRate){
        this.target = target;
        this.mutationRate = mutationRate;
        population = new Individual[size];
        createPopulation(size);
        fitness();
    }

    private void createPopulation(int length){
        for(int i = 0; i < length; i++){
            Individual individual = new Individual(target.length());
            population[i] = individual;
        }
    }

    /**
     * Fitness function to calculate the fitness of the population.
     */

    private void fitness(){
        for(int i = 0; i < population.length; i++){
            population[i].fitness(target);
        }
    }

    /**
     * Add each individual to the selection pool a certain number of times based on it's score. Higher individuals
     * have more entries and, therefore, a higher chance of being picked during crossover.
     * @param individual The individual to add to the selection list.
     * @param times The score of the individual or the times to be added to the list.
     * @param list The list to add the specific individual to.
     * @return The updated selection list.
     */
    private ArrayList<Individual> addSelection(Individual individual, int times, ArrayList<Individual> list){
        for(int i = 0; i < times; i++){
            list.add(individual);
        }
        return list;
    }

    /**
     * Method to retrieve the score of the individual, we add one so that if you have all score 0, they still get added
     * to the list and have an equal chance of being picked.
     * @return The list to be used as selection for crossover.
     */
    private Individual[] selection(){
        ArrayList<Individual> list = new ArrayList<>();
        for(int i = 0; i < population.length; i++){
            int add = population[i].getScore() + 1;
            list = addSelection(population[i], add, list);
        }
        return list.toArray(new Individual[list.size()]);
    }

    /**
     * This section picks the parents randomly from the selection list and crosses them over.
     * @param selection the array used to pick the parents.
     * @param rand pass in a random object I use in the simulate method so I don't have to create a new one.
     */
    private void crossover(Individual[] selection, Random rand){
        for(int i = 0; i < population.length; i++){
            int x = rand.nextInt(selection.length);
            int y = rand.nextInt(selection.length);
            population[i] = new Individual(selection[x], selection[y]);
        }
        //update fitness for new population
        fitness();
    }

    /**
     * This method is used to switch a random gene to it's inverse if there is a mutation.
     * @param rand pass in a random object I use in the simulate method so I don't have to create a new one.
     */
    private void mutate(Random rand){
        StringBuilder string = new StringBuilder();
        int isMutation = rand.nextInt(population.length);
        int mutatedGene = rand.nextInt(target.length());
        population[isMutation].createMutant(mutatedGene);
    }

    private Individual getFittest(){
        Individual fittest = population[0];
        for(int i = 1; i < population.length; i++){
            if(population[i].getScore() > fittest.getScore()){
                fittest = population[i];
            }
        }
        return fittest;
    }

    /**
     * This is used to run the genetic algorithm from main.
     * @return the fittest individual or the one that will match the target at the end of the run.
     */
    public Individual simulate(){
        Individual fittest = getFittest();
        int count = 0;
        Random rand = new Random();
        while(!getFittest().getGenes().equals(target)) {
            Individual[] selection = selection();
            crossover(selection, rand);
            if (rand.nextDouble() < mutationRate) {
                mutate(rand);
            }
            fittest = getFittest();
            System.out.println(fittest.getGenes());
        }
        return fittest;
    }

    /**
     * Get an individual form the population.
     * @param index the index of the desired individual.
     * @return the desired individual.
     */
    public Individual getIndividual(int index) {
        return population[index];
    }

    /**
     * Get an individual in string format, or a toString() equivalent.
     * @param index the index of the desired individual.
     * @return the desired individual in string form.
     */
    public String getIndividualString(int index){
        return population[index].getGenes();
    }
}