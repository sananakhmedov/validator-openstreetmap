package com.example.openmapvalidator.service.convert;

import com.example.openmapvalidator.helper.ConfigurationService;
import com.example.openmapvalidator.helper.Const;
import com.example.openmapvalidator.service.database.PostgreSQLSelect;
import com.example.openmapvalidator.service.file.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.*;

/**
 * @author Sanan.Ahmadzada
 */
@Repository
public class OsmToDBHandlerImpl implements OsmToDBHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OsmToDBHandlerImpl.class);

    private final ConfigurationService configurationService;
    private final FileHandler fileHandler;

    public OsmToDBHandlerImpl(ConfigurationService configurationService,
                              FileHandler fileHandler) {
        this.configurationService = configurationService;
        this.fileHandler = fileHandler;
    }

    public void handle(String fileName) {

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
                        .append(Const.OSM_COMMAND_USERNAME_OPTION)
                        .append(Const.SPACE)
                        .append(configurationService.getPSQL_USERNAME())
                        .append(Const.SPACE)
                        .append(Const.OSM_DEFAULT_STYLE_OPTION)
                        .append(Const.SPACE)
                        .append(stylePath)
                        .append(Const.SPACE)
                        .append(fileNameWithPath)
                        .append(Const.SHELL_EXIT_COMMAND);

                LOGGER.info("command: {}", sb.toString());

                fileHandler.openFileAndOverrideContent(root + Const.OSM_BAT_FILE_PATH_WITH_NAME, sb.toString());

                executeCommandWithExec(root + Const.OSM_BAT_FILE_PATH, true);

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
                        .append(fileNameWithPath)
                        .append(Const.SHELL_EXIT_COMMAND);

                LOGGER.info("command: {}", sb.toString());

                fileHandler.openFileAndOverrideContent(root + Const.OSM_SH_FILE_PATH_WITH_NAME, sb.toString());

                executeCommandWithExec(root + Const.OSM_SH_FILE_PATH, false);

            }

            LOGGER.debug("------");

        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) throws IOException {
        File myFoo = new File("C:\\dev\\projects\\validator-openstreetmap\\bashscript\\windows\\osm2pgsql-bin\\osm.bat");
        FileWriter fooWriter = new FileWriter(myFoo, false); // true to append
        // false to overwrite.
        String content = "C:\\dev\\projects\\validator-openstreetmap\\bashscript\\windows\\osm2pgsql-bin\\osm2pgsql " +
                "--create --database map-db --username postgres -S " +
                "C:\\dev\\projects\\validator-openstreetmap\\target\\classes\\map\\default.style " +
                "C:\\dev\\projects\\validator-openstreetmap\\target\\classes\\map\\map.osm & exit";
        fooWriter.write(content);
        fooWriter.close();

        executeCommandWithExec("C:\\dev\\projects\\validator-openstreetmap\\bashscript\\windows\\osm2pgsql-bin\\",
                true);

        String content2 = "C:\\dev\\projects\\validator-openstreetmap\\bashscript\\windows\\osm2pgsql-bin\\osm2pgsql " +
                "--create --database map-db --username postgres -S " +
                "C:\\dev\\projects\\validator-openstreetmap\\target\\classes\\map\\default.style " +
                "C:\\dev\\projects\\validator-openstreetmap\\target\\classes\\map\\bostonmap.osm & exit";
        FileWriter fooWriter2 = new FileWriter(myFoo, false); // true to append
        fooWriter2.write(content2);
        fooWriter2.close();

        executeCommandWithExec("C:\\dev\\projects\\validator-openstreetmap\\bashscript\\windows\\osm2pgsql-bin\\",
                true);
    }


    private static void executeCommandWithExec(String pathOfCommand, boolean isWin) {
        Process p;
        try {
            String[] cmd = new String[3];
            if (!isWin) {
                cmd[0] = Const.SHELL_SH_COMMAND;
                cmd[1] = pathOfCommand + Const.OSM_SH_FILE_PATH;

                p = Runtime.getRuntime().exec(cmd);
            } else {
                String command = Const.SHELL_BAT_COMMAND + Const.SPACE + Const.SHELL_BAT_NAME;

                p = Runtime.getRuntime().exec(command, null, new File(pathOfCommand));

            }

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
