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
package com.mythton.otc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.update.spiget.SpigetUpdate;
import org.inventivetalent.update.spiget.UpdateCallback;
import org.inventivetalent.update.spiget.comparator.VersionComparator;

import com.mythton.otc.Commands.OTCCommand;
import com.mythton.otc.Events.OTCListener;
import com.mythton.otc.Utils.Log;
import com.mythton.otc.Utils.OTCHelper;

public class OTC extends JavaPlugin
{
	//	Push from Eclipse test
	public static final Logger console = Logger.getLogger("OTC");
	public OTCHelper helper;
	public FileConfiguration _settings;

	File pluginDir = new File("plugins/OnTheClock16/");
	public File clockDir = new File("plugins/OnTheClock16/Players/");

	@Override
	public void onEnable()
	{
		PluginManager pm = getServer().getPluginManager();
		PluginDescriptionFile pdFile = this.getDescription();
		String ver = pdFile.getVersion();

		console.log(Level.INFO, "[OTC] Starting OTC v" + ver + "...");

		if (!pluginDir.exists())
		{
			console.log(Level.INFO,
					"[OTC] Performing first-time startup operations.");
			pluginDir.mkdir();
			clockDir.mkdir();
			console.log(Level.INFO, "[OTC] First-time startup operations complete.");
		}
		if (!clockDir.exists())
		{
			console.log(Level.INFO, "[OTC] Players directory was not found; the directory has been created.");
		}
		
		File settings = new File(getDataFolder(), "settings.yml");
		if (!settings.exists())
			saveResource("settings.yml", false);

		try
		{
			helper = new OTCHelper(this);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (InvalidConfigurationException e)
		{
			e.printStackTrace();
		}
		
		pm.registerEvents(new OTCListener(this), this);

		getCommand("otc").setExecutor(new OTCCommand(this));
		
		try {
			_settings.load(settings);
			if(_settings.getBoolean("update.check")) {
				checkUpdates();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		new Metrics(this);
	}
	
	@Override
	public void onDisable()
	{
		console.log(Level.INFO, "[OTC] Clocking out players...");
		for (Player p : Bukkit.getServer().getOnlinePlayers())
		{
			if (p.hasPermission("otc.clock")){
				try
				{
					OTCHelper.clock(p, false);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (InvalidConfigurationException e)
				{
					e.printStackTrace();
				}
			}
		}
		console.log(Level.INFO, "[OTC] OTC disabled.");
	}
	
	private void checkUpdates() {
		SpigetUpdate updater = new SpigetUpdate(this, 35992);
		updater.setVersionComparator(VersionComparator.EQUAL);
		
		updater.setVersionComparator(VersionComparator.SEM_VER);
		
		updater.checkForUpdate(new UpdateCallback() {
			public void updateAvailable(String newVersion, String url, boolean direct) {
				File settings = new File(getDataFolder(), "settings.yml");
				Log.LogMessage("OnTheClock has an update available!", getServer().getConsoleSender());
				try {
					_settings.load(settings);
					if(_settings.getBoolean("update.download")) {
						downloadUpdate();
					} else {
						Log.LogMessage("Download: " + url, getServer().getConsoleSender());
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InvalidConfigurationException e) {
					e.printStackTrace();
				}	
			}
			
			public void upToDate() {
				Log.LogMessage("Latest version of Function_One is running.", getServer().getConsoleSender());
			}
		});
	}
	
	private void downloadUpdate() {
		final SpigetUpdate updater = new SpigetUpdate(this, 35992);
		updater.checkForUpdate(new UpdateCallback() {
			public void updateAvailable(String newVersion, String url, boolean direct) {
				if(direct) {
					if(updater.downloadUpdate()) {
						
					} else {
						Log.LogMessage("OnTheClock updater failed; " + updater.getFailReason()
						, getServer().getConsoleSender());
					}
				}
			}
			
			public void upToDate() {	
			}
		});
	}
}
