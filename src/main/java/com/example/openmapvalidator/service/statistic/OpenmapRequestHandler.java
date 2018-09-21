package com.example.openmapvalidator.service.statistic;

import com.example.openmapvalidator.helper.Const;
import com.example.openmapvalidator.service.database.DBSessionProvider;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Sanan.Ahmadzada
 */
@Component
public class OpenmapRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenmapRequestHandler.class);

    private final DBSessionProvider databaseSession;

    @Autowired
    public OpenmapRequestHandler(DBSessionProvider databaseSession) {
        this.databaseSession = databaseSession;
    }

    public int countOpenmapPlaces() throws IOException {

        SqlSession session = databaseSession.getDBSession();
        Integer count = session.selectOne(Const.OSM_PSQL_PLACE_COUNT_QUERY_IDENTIFIER);
        session.close();
        LOGGER.debug("Openmap statistic request handler returns count : {}", count);
        return count;
    }

}
