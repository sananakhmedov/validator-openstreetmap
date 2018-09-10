package com.example.openmapvalidator.service.database;

import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public interface DBSessionProvider {
    SqlSession getDBSession() throws IOException;
}
