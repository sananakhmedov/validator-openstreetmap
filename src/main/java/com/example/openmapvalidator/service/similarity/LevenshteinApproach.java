package com.example.openmapvalidator.service.similarity;

import info.debatty.java.stringsimilarity.Levenshtein;
import org.springframework.stereotype.Component;
/**
 * @author Sanan.Ahmadzada
 */
@Component
public class LevenshteinApproach implements SimilarityStrategy {

    private final Levenshtein levenshtein;

    public LevenshteinApproach(Levenshtein levenshtein) {
        this.levenshtein = levenshtein;
    }

    @Override
    public double similarity(String left, String right) {
        return levenshtein.distance(left, right);
    }
}
