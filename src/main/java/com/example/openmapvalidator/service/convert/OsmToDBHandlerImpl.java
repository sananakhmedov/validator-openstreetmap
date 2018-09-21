package com.example.openmapvalidator.service.convert;

import com.example.openmapvalidator.helper.ConfigurationService;
import com.example.openmapvalidator.helper.Const;
import com.example.openmapvalidator.model.PlaceDBModel;
import com.example.openmapvalidator.service.database.PostgreSQLSelect;
import com.example.openmapvalidator.service.file.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sanan.Ahmadzada
 */
@Repository
public class OsmToDBHandlerImpl implements OsmToDBHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OsmToDBHandlerImpl.class);

    private final ConfigurationService configurationService;
    private final PostgreSQLSelect postgreSQLSelect;
    private final FileHandler fileHandler;

    public OsmToDBHandlerImpl(ConfigurationService configurationService, PostgreSQLSelect postgreSQLSelect,
                              FileHandler fileHandler) {
        this.configurationService = configurationService;
        this.postgreSQLSelect = postgreSQLSelect;
        this.fileHandler = fileHandler;
    }


    // TODO check if it is okay to handle it in thread
    public void handle(String fileName) {

        //List<PlaceDBModel> list = postgreSQLSelect.selectPlaceDBModel();
        //LOGGER.info("elements " + Arrays.toString(list.toArray()));

        boolean isWindows = System.getProperty(Const.OS_NAME)
                .toLowerCase().startsWith(Const.OS_WINDOWS_NAME);

        String OSM_ROOT;
        String root = new File(System.getProperty("user.dir")).getAbsolutePath();
        ClassPathResource mapFolderResource = new ClassPathResource(Const.MAP_FOLDER_ROOT);

        try {


            String fileNameWithPath = mapFolderResource.getFile().getAbsolutePath() +
                    File.separator + fileName;

            String stylePath = mapFolderResource.getFile().getAbsolutePath() +
                    File.separator + Const.OSM_STYLE;


            if (isWindows) {
                OSM_ROOT = Const.OSM_COMMAND_WINDOWS_ROOT;
                String path = root + OSM_ROOT;
                String command = path + File.separator + Const.OSM_COMMAND;

                StringBuilder sb = new StringBuilder()
                        .append(command)
                        .append(Const.SPACE)
                        .append(Const.OSM_COMMAND_CREATE_OPTION)
                        .append(Const.SPACE)
                        .append(Const.OSM_COMMAND_DATABASE_OPTION)
                        .append(Const.SPACE)
                        .append(configurationService.getOSM_COMMAND_DATABASE_ARGUMENT())
                        .append(Const.SPACE)
                        .append(Const.OSM_DEFAULT_STYLE_OPTION)
                        .append(Const.SPACE)
                        .append(stylePath)
                        .append(Const.SPACE)
                        .append(fileNameWithPath);

                LOGGER.info("command: {}", sb.toString());

                fileHandler.openFileAndOverrideContent(root + Const.OSM_BAT_FILE_PATH, sb.toString());

                executeCommandWithExec(root + Const.OSM_BAT_FILE_PATH);

            } else {

                OSM_ROOT = Const.OSM_COMMAND_UNIX_ROOT;

                String path = root + OSM_ROOT;

                String command = path + File.separator + Const.OSM_COMMAND;

                StringBuilder sb = new StringBuilder()
                        .append(command)
                        .append(Const.SPACE)
                        .append(Const.OSM_COMMAND_CREATE_OPTION)
                        .append(Const.SPACE)
                        .append(Const.OSM_COMMAND_DATABASE_OPTION)
                        .append(Const.SPACE)
                        .append(configurationService.getOSM_COMMAND_DATABASE_ARGUMENT())
                        .append(Const.SPACE)
                        .append(Const.OSM_DEFAULT_STYLE_OPTION)
                        .append(Const.SPACE)
                        .append(stylePath)
                        .append(Const.SPACE)
                        .append(fileNameWithPath);

                LOGGER.info("command: {}", sb.toString());

                fileHandler.openFileAndOverrideContent(root + Const.OSM_SH_FILE_PATH, sb.toString());

                executeCommandWithExec(root + Const.OSM_SH_FILE_PATH);

            }

            LOGGER.debug("------");

        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void executeCommandWithExec(String command) {
        Process p;
        try {
            String[] cmd = { "sh", command};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
