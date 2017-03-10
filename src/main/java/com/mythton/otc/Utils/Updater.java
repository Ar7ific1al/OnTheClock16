/**
 * This file is part of OnTheClock.
 * OnTheClock is free software: you can redistribute it or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * OnTheClock is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with OnTheClock. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mythton.otc.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * 
 */
public class Updater {
	private String latestVersion;

    private boolean checkHigher(String currentVersion, String newVersion) {
        String current = toReadable(currentVersion);
        String newVers = toReadable(newVersion);
        return current.compareTo(newVers) < 0;
    }

    public void checkUpdate(String currentVersion) {
        String version = getSpigotVersion();

        if (version == null)
            return;

        if (!checkHigher(currentVersion, version))
            return;

        latestVersion = version;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    /**
     * Asks spigot for the version
     */
    private String getSpigotVersion() {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream().write(
                    ("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=37468").getBytes("UTF-8"));
            String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (version.length() <= 7) {
                return version;
            }
        }
        catch (Exception ex) {
            System.out.print("[OnTheClock] Failed to check for a update on spigot.");
        }
        return null;
    }

    private String toReadable(String version) {
        String[] split = Pattern.compile(".", Pattern.LITERAL).split(version.replace("v", ""));
        version = "";
        for (String s : split) {
            version += String.format("%4s", s);
        }
        return version;
    }
}
