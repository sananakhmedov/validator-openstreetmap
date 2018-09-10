package com.example.openmapvalidator.service.similarity;
/**
 * @author Sanan.Ahmadzada
 */
public interface SimilarityStrategy {
    /**
     * find similarity score between two given strings based on which
     * similarity algorithm chosen.
     *
     * @param left
     * @param right
     * @return
     */
    double similarity(String left, String right);
}
