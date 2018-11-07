package com.example.openmapvalidator.service.convert;

import com.example.openmapvalidator.helper.ConfigurationService;
import com.example.openmapvalidator.helper.Const;
import com.example.openmapvalidator.service.file.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

        List<String> windowsCommandPathList = new ArrayList<>(1);

        try {

            String fileNameWithPath = mapFolderResource.getFile().getAbsolutePath() +
                    File.separator + fileName;

            if (isWindows) {
                OSM_ROOT = Const.OSM_COMMAND_WINDOWS_ROOT;
                String path = root + OSM_ROOT;
                String commandPathWithName = path + File.separator + Const.OSM_COMMAND;

                List<String> commandList = getCommandsForOsm2pgsql(commandPathWithName, fileNameWithPath);

                StringBuilder sb = new StringBuilder();
                for (String commandOfOsm : commandList) {
                    sb.append(commandOfOsm)
                            .append(Const.SPACE);
                }
                sb.append(Const.SHELL_EXIT_COMMAND);

                LOGGER.info("command: {}", commandList);

                fileHandler.openFileAndOverrideContent(root + Const.OSM_BAT_FILE_PATH_WITH_NAME, sb.toString());

                windowsCommandPathList.add(root + Const.OSM_BAT_FILE_PATH);
                executeCommandWithExec(windowsCommandPathList, true);

            } else {

                OSM_ROOT = Const.OSM_COMMAND_UNIX_ROOT;

                String path = root + OSM_ROOT;

                String commandPathWithName = path + File.separator + Const.OSM_COMMAND;

                List<String> commandList = getCommandsForOsm2pgsql(commandPathWithName, fileNameWithPath);

                LOGGER.info("command: {}", commandList);

                executeCommandWithExec(commandList, false);

            }

            LOGGER.debug("------");

        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     *
     * @return list of commands to run osm2pgsql command for unix and windows system
     */
    private List<String> getCommandsForOsm2pgsql(String command, String fileNameWithPath) {

        String stylePath = new File(System.getProperty("user.dir")).getAbsolutePath() +
                Const.OSM_COMMAND_WINDOWS_ROOT +
                File.separator + Const.OSM_STYLE;

        List<String> listOfCommands = new ArrayList<>(30);
        listOfCommands.add(command);
        listOfCommands.add(Const.OSM_COMMAND_CREATE_OPTION);
        listOfCommands.add(Const.OSM_COMMAND_DATABASE_OPTION);
        listOfCommands.add(configurationService.getOSM_COMMAND_DATABASE_ARGUMENT());
        listOfCommands.add(Const.OSM_COMMAND_USERNAME_OPTION);
        listOfCommands.add(configurationService.getPSQL_USERNAME());
        listOfCommands.add(Const.OSM_DEFAULT_STYLE_OPTION);
        listOfCommands.add(stylePath);
        listOfCommands.add(fileNameWithPath);

        return listOfCommands;
    }

    private void executeCommandWithExec(List<String> commandList, boolean isWin) {
        Process p;
        try {
            if (!isWin) {

                ProcessBuilder pb = new ProcessBuilder();
                pb.command(commandList);
                p = pb.start();

            } else {
                String command = Const.SHELL_BAT_COMMAND + Const.SPACE + Const.SHELL_BAT_NAME;
                p = Runtime.getRuntime().exec(command, null, new File(commandList.get(0)));

            }

            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
