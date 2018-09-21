package com.example.openmapvalidator.service.database;

import com.example.openmapvalidator.model.PlaceDBModel;
import com.example.openmapvalidator.service.MapPlacesValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostgreSQLSelect {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgreSQLSelect.class);

    public List<PlaceDBModel> selectPlaceDBModel() {

        List<PlaceDBModel> placeDBModels = new ArrayList<>();
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");

            stmt = PostgreSQLJDBC.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM public.PLANET_OSM_POINT WHERE public_transport IS NULL AND name IS NOT NULL;" );
            while ( rs.next() ) {
                String osm_id = Long.toString(rs.getLong("osm_id"));
                String name = rs.getString("name");
                String shop  = rs.getString("shop");
                String amenity = rs.getString("amenity");

                System.out.println( "osm_id = " + osm_id );
                System.out.println( "NAME = " + name );
                System.out.println( "SHOP = " + shop );
                System.out.println( "AMENITY = " + amenity );
                System.out.println();

                PlaceDBModel placeDBModel = new PlaceDBModel();
                placeDBModel.setAmenity(amenity);
                placeDBModel.setName(name);
                placeDBModel.setOsm_id(osm_id);
                placeDBModel.setShop(shop);

                placeDBModels.add(placeDBModel);
            }
            rs.close();
            stmt.close();
            PostgreSQLJDBC.getConnection().close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return placeDBModels;
    }
}
