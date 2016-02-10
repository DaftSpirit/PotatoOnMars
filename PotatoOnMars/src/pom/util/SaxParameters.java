package pom.util;

import net.seninp.jmotif.sax.NumerosityReductionStrategy;

public class SaxParameters {
	
	public final static String dataFName = "data/database/load minuit.txt";
	public final static int slidingWindowSize = 24;
	public final static int paaSize = 24;
	public final static int alphabetSize = 12;
	public final static int steps = 24;
	public final static NumerosityReductionStrategy nrStrategy = NumerosityReductionStrategy.fromValue(0);
	public final static double nThreshold = 0.01;
	
}


// rajouter les données du use case en plus de l'apprentissage pour la méthode des fenetres (en validant a partir des doubles)' ?
// refaire l'analyse a chaque fois ? 
// fichier d'apprentissage qui reste tel quel ? doit on l'update au fur et a mesure des events + update du tableau ? update avec des donnéees potentiellement erronées ?

// load + nombre de requetes / reactivité
