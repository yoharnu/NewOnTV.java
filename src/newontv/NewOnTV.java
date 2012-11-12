/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newontv;

import java.io.File;

/**
 *
 * @author dthoma31
 */
public class NewOnTV {

    public static final String MirrorPath = "http://thetvdb.com";
    public static final String APIkey = "768A3A72ACDABC4A";
    public static final String language = "en";

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2) {
            usage();
        }
        {
            File temp = new File(args[0]);
            if (!temp.exists()) {
                System.err.println("Error:  File does not exist:  " + args[0]);
                System.exit(1);
            }
            if (!temp.isFile()) {
                System.err.println("Error:  \"" + args[0] + "\" is not a file.");
                System.exit(1);
            }
        }

        if (args.length == 1) {
            CheckNew.run(args[0]);
        } else if (args[1].equalsIgnoreCase("edit")) {
            EditList.run(args[0]);
        } else {
            usage();
        }
    }

    private static void usage() {
        System.out.println("Usage:  java [...] <showFile> (edit)");
        System.exit(1);
    }
}
