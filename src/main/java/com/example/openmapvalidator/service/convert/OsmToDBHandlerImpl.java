package com.example.openmapvalidator.service.convert;

import com.example.openmapvalidator.helper.ConfigurationService;
import com.example.openmapvalidator.helper.Const;
import com.example.openmapvalidator.helper.StreamGobbler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.SessionScope;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.Executors;

/**
 * @author Sanan.Ahmadzada
 */
@Repository
public class OsmToDBHandlerImpl implements OsmToDBHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OsmToDBHandlerImpl.class);

    private final ConfigurationService configurationService;


    public OsmToDBHandlerImpl(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }


    // TODO check if it is okay to handle it in thread
    public void handle(String fileName) {

        long begin = System.currentTimeMillis();

        boolean isWindows = System.getProperty(Const.OS_NAME)
                .toLowerCase().startsWith(Const.OS_WINDOWS_NAME);

        Process process = null;

        String OSM_ROOT;
        String root = new File(System.getProperty("user.dir")).getAbsolutePath();
        ClassPathResource mapFolderResource = new ClassPathResource(Const.MAP_FOLDER_ROOT);

        try {


            String fileNameWithPath = mapFolderResource.getFile().getAbsolutePath() +
                    File.separator + fileName;

            String stylePath = mapFolderResource.getFile().getAbsolutePath() +
                    File.separator + Const.OSM_STYLE;


            ProcessBuilder builder = new ProcessBuilder();

            //builder.command("osm2pgsql", "--create", "--database", "map-db", fileName);


            //builder.directory(new ClassPathResource("map").getFile());
            //builder.directory(new File(System.getProperty("user.dir")));

            if (isWindows) {
                OSM_ROOT = Const.OSM_WINDOWS_ROOT;

                String path = root + OSM_ROOT;

                //String command = path + File.separator + Const.OSM_COMMAND + ".exe";
                String command = path + File.separator + Const.OSM_COMMAND;
                LOGGER.info("command path is : {}", command);


                //builder.command(command, "--create", "--database", "map-db", fileNameWithPath);

                String commandWin = command + " --create --username=postgres --database=map-db " +
                        " -S " + stylePath + Const.SPACE + fileNameWithPath;
                LOGGER.info("command win {}", commandWin);

                process = Runtime.getRuntime().exec(commandWin, null, new File(path));

                process.waitFor();

            } else {

                OSM_ROOT = Const.OSM_UNIX_ROOT;

                String path = root + OSM_ROOT;

                builder.directory(new File(path));

                String command = path + File.separator + Const.OSM_COMMAND;
                LOGGER.info("command path is : {}", command);


                //builder.command(command, "--create", "--database", "map-db", fileNameWithPath);
                builder.command(command, "--create", "--database", "map-db", "-S", stylePath,
                        fileNameWithPath);

                String[] args = new String[] {command, "--create", "--database", "map-db", "-S", stylePath, fileNameWithPath};
                Process proc = new ProcessBuilder(args).start();

                //String output = executeCommand(command + " --create --database map-db -S " + stylePath + " " +
                 //       fileNameWithPath + " &");

                //LOGGER.info("output: {}", output);

                //process = builder.start();

            }

            LOGGER.info("command: {}", Arrays.toString(builder.command().toArray()));

            LOGGER.debug("------");

            /*StreamGobbler streamGobbler =
                    new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            int exitCode = process.waitFor();
            assert exitCode == 0;

            long end = System.currentTimeMillis();

            LOGGER.info("end osm command runtime in ms : {}", end - begin);*/

        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            process.destroy();
        }
    }

    private String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p = null;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LOGGER.info("destroy");
            p.destroyForcibly();
        }

        return output.toString();

    }

}
