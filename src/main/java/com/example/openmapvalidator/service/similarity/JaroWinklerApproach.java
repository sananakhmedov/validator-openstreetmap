package com.example.openmapvalidator.service.similarity;

import info.debatty.java.stringsimilarity.JaroWinkler;
import org.springframework.stereotype.Component;
/**
 * @author Sanan.Ahmadzada
 */
@Component
public class JaroWinklerApproach implements SimilarityStrategy {

    private final JaroWinkler jaroWinkler;

    public JaroWinklerApproach(JaroWinkler jaroWinkler) {
        this.jaroWinkler = jaroWinkler;
    }

    @Override
    public double similarity(String left, String right) {
        return jaroWinkler.similarity(left, right);
    }
}
