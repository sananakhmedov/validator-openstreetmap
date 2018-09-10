package com.example.openmapvalidator.service.database;

import com.example.openmapvalidator.helper.ConfigurationService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
/**
 * @author Sanan.Ahmadzada
 */
@Component
public class DatabaseSession implements DBSessionProvider {

    private final ConfigurationService configurationService;

    @Autowired
    public DatabaseSession(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public SqlSession getDBSession() throws IOException {
        String resource = configurationService.getMYBATIS_CONFIG_PATH();
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(inputStream);

        return sqlSessionFactory.openSession();
    }
}
