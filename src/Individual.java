import java.util.Random;

public class Individual {

    private String genes;

    private Random rand;

    private int score;

    /**
     * Constructor for an Individual in a population.
     * @param length the length of the individual, should be the same length as the target string.
     */
    public Individual(int length){
        rand = new Random();
        genes = createGenes(length);
    }

    /**
     * Constructor used for making a new individual during crossover.
     * @param parent1 first parent picked during crossover.
     * @param parent2 second parent picked during crossover.
     */
    public Individual(Individual parent1, Individual parent2){
        rand = new Random();
        genes = crossover(parent1, parent2);
    }

    /**
     * Fills the individual with it's specific genes. Since we are working with binary, either a 1 or a 0 is added randomly.
     * @param length the length of the individual based on the target string.
     * @return the total genes that make up the individual in string format.
     */
    private String createGenes(int length){
        StringBuilder string = new StringBuilder();
        for(int i = 0; i < length; i++){
            if(rand.nextBoolean()){
                string.append("1");
            }
            else{
                string.append("0");
            }
        }
        return string.toString();
    }

    /**
     * Get a specific gene of an individual at the specified index.
     * @param index the index of the specified gene.
     * @return the gene at the specified index.
     */
    private char getGene(int index){
        return genes.charAt(index);
    }

    /**
     * Used by the child constructor to mix the genes randomly based on the selected parents genes.
     * @param parent1 first parent selected for crossover.
     * @param parent2 second parent selected for crossover.
     * @return the new string to be used as the genes for the child of parents.
     */
    private String crossover(Individual parent1, Individual parent2){
        StringBuilder string = new StringBuilder();
        for(int i = 0; i < parent1.getGenes().length(); i++){
            if(rand.nextBoolean()){
                string.append(parent1.getGene(i));
            }
            else{
                string.append(parent2.getGene(i));
            }
        }
        return string.toString();
    }

    /**
     * Get the fitness of the specific individual to be used for comparing to the desired result.
     * @param target the desired result of the fittest individuals genes.
     */
    public void fitness(String target){
        for(int i = 0; i < genes.length(); i++){
            if(genes.charAt(i) == target.charAt(i)){
                score++;
            }
        }
    }

    /**
     * Used to mutate an individual by taking switching whatever value is at the specified index.
     * @param index the index of the individual being mutated in the population.
     */
    public void createMutant(int index){
        StringBuilder string = new StringBuilder();
        for(int i = 0; i < genes.length(); i++){
            if(i == index){
                if(genes.charAt(i) == '1'){
                    string.append("0");
                }
                else{
                    string.append("1");
                }
            }
            else{
                string.append(genes.charAt(i));
            }
        }
        genes = string.toString();
    }

    /**
     * Get the fitness score of an individual.
     * @return the fitness score of the individual.
     */
    public int getScore(){
        return score;
    }

    /**
     * Get the genes of the specified individual.
     * @return the genes of the specified individual.
     */
    public String getGenes(){
        return genes;
    }
}
