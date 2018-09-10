package com.example.openmapvalidator.service.similarity;
/**
 * @author Sanan.Ahmadzada
 */
public class SimilarityStrategyContext {

    private final SimilarityStrategy strategy;

    public SimilarityStrategyContext(SimilarityStrategy strategy) {
        this.strategy = strategy;
    }

    public double executeStrategy(String left, String right) {
        return strategy.similarity(left, right);
    }

}
