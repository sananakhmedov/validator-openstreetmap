package com.example.openmapvalidator.service.similarity;

import info.debatty.java.stringsimilarity.Jaccard;
import org.springframework.stereotype.Component;
/**
 * @author Sanan.Ahmadzada
 */
@Component
public class JaccardApproach implements SimilarityStrategy {

    private final Jaccard jaccard;

    public JaccardApproach(Jaccard jaccard) {
        this.jaccard = jaccard;
    }

    @Override
    public double similarity(String left, String right) {
        return jaccard.similarity(left, right);
    }
}
