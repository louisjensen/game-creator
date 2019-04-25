package launcher;

import data.external.DatabaseEngine;

public class JarLauncher {
    /**
     * These are two runner classes to handle errors that arose when having jar files created with main's that were bound
     * to class's that extended Application
     * @author Anna Darwish
     */
    public static void main(String[] args){
        DatabaseEngine.getInstance().open();
        LauncherMain.main(args);
    }
}
