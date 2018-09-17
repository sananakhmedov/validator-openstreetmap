package com.example.openmapvalidator.service.similarity;

import com.example.openmapvalidator.helper.ConfigurationService;
import com.example.openmapvalidator.helper.Const;
import com.example.openmapvalidator.model.PlaceDBModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * @author Sanan.Ahmadzada
 */
@Service
public class SimilarityCheckHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimilarityCheckHandler.class);

    private final ConfigurationService configurationService;
    private final SimilarityStrategyContext similarityStrategyContext;
    private final SimilarityStrategyMapper strategyMapper;

    public SimilarityCheckHandler(ConfigurationService configurationService, SimilarityStrategyMapper strategyMapper) {
        this.configurationService = configurationService;
        this.strategyMapper = strategyMapper;

        SimilarityStrategy strategy = this.strategyMapper.getSimilarityAlgorithm(configurationService
                .getSIMILARITY_ALGORITHM());

        this.similarityStrategyContext = new SimilarityStrategyContext(strategy);
    }

    /**
     * compare given google and openstreetmap place name value for similarity by defined score
     *
     * @param node from open street map
     * @param nameResultFromGooglePlace google places api model
     * @param foursquareName
     * @param microsoftPlaceName
     * @return true if they are not similar otherwise false
     */
    public boolean handle(PlaceDBModel node, String nameResultFromGooglePlace, String foursquareName,
                       String microsoftPlaceName) {

        LOGGER.debug("\n" + "*******COMPARE************");
        LOGGER.debug(node.getOsm_id() + " {} -> {}", Const.OPENSTREET, node.getName());
        LOGGER.debug(node.getOsm_id() + " {} -> {}", Const.GOOGLE, nameResultFromGooglePlace);
        LOGGER.debug(node.getOsm_id() + " {} -> {}", Const.FOURSQUARE, foursquareName);
        LOGGER.debug(node.getOsm_id() + " {} -> {}", Const.MICROSOFT, microsoftPlaceName);

        LOGGER.debug("*********FINISH**************" + "\n");

        double similarity = similarityStrategyContext.executeStrategy(node.getName(), nameResultFromGooglePlace);
        LOGGER.info(node.getOsm_id() + " ******* similarity : {}", similarity);

        return similarity < Double.valueOf(configurationService.getSIMILARITY_SCORE());
    }
}
