package com.example.openmapvalidator.service.similarity;

import com.example.openmapvalidator.helper.Const;
import org.springframework.stereotype.Component;
/**
 * @author Sanan.Ahmadzada
 */
@Component
public class SimilarityStrategyMapper {

    private final JaroWinklerApproach jaroWinkler;
    private final LevenshteinApproach levenshtein;
    private final JaccardApproach jaccard;

    public SimilarityStrategyMapper(JaroWinklerApproach jaroWinkler, LevenshteinApproach levenshtein,
                                    JaccardApproach jaccard) {
        this.jaroWinkler = jaroWinkler;
        this.levenshtein = levenshtein;
        this.jaccard = jaccard;
    }

    public SimilarityStrategy getSimilarityAlgorithm(String name) {
        SimilarityStrategy strategy;
        switch (name) {
            case Const.LEVENSHTEIN :
                strategy = levenshtein;
                break;
            case Const.JACCARD :
                strategy = jaccard;
                break;
            default: // JARO WINKLER
                strategy = jaroWinkler;
        }

        return strategy;
    }
}
