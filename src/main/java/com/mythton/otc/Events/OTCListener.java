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
package com.mythton.otc.Events;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.mythton.otc.OTC;
import com.mythton.otc.Utils.Log;
import com.mythton.otc.Utils.OTCHelper;
import com.mythton.otc.Utils.Updater;

public class OTCListener implements Listener
{
	private OTC plugin;
	File file;
	FileConfiguration settings = new YamlConfiguration();

	public OTCListener(OTC instance)
	{
		plugin = instance;
		OTC.console.log(Level.INFO, "[OTC] Player join listener registered.");
		
		try {
			file = new File(plugin.getDataFolder(), "settings.yml");
			settings.load(file);
			if(settings.getBoolean("updateCheck")) {
				checkUpdates(plugin.getDescription().getVersion());
			} else {
				Log.LogMessage("Update checking disabled. Skipping...", plugin.getServer().getConsoleSender());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) throws IOException,
			InvalidConfigurationException
	{
		Player player = e.getPlayer();
		if (player.hasPermission("otc.clock"))
		{
			File f = new File(plugin.clockDir, player.getUniqueId().toString() + ".clock");
			if (!f.exists()){
				OTCHelper.makeNewClockFile(player);
			}
			
			OTCHelper.clock(player, true);
	
			if (player.hasPermission("otc.announce.mod")
					&& !player.hasPermission("otc.announce.custom"))
			{
				e.setJoinMessage(OTCHelper.broadcastJoin(player, true));
			}
			else if (player.hasPermission("otc.announce.custom"))
			{
				e.setJoinMessage(OTCHelper.broadcastJoin(player, true));
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) throws IOException,
			InvalidConfigurationException
	{
		Player player = e.getPlayer();
		if (player.hasPermission("otc.clock"))
		{
			OTCHelper.clock(player, false);
		}
	}
	
	private void checkUpdates(String ver) {
		Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				try {
					Updater updater = new Updater();
					updater.checkUpdate("v" + ver);
					
					String latest = updater.getLatestVersion();
					
					if(latest == null)
						return;
					
					latest = "v" + latest;
					
					Bukkit.getScheduler().runTask(plugin, new Runnable() {
						@Override
						public void run() {
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(p.isOp()) {
									p.sendMessage(Log.ColorMessage("&eOnTheClock v&6" + updater.getLatestVersion() + " &eis available!"));
									p.sendMessage(Log.ColorMessage("&eDownload: &6https://www.spigotmc.org/resources/ontheclock.37468/"));
								}
							}
						}
					});
				} catch (Exception e) {
					Log.LogMessage("OnTheClock failed to check for updates.", plugin.getServer().getConsoleSender());
				}
			}
		}, 0, (20 * 60 * 60 * 6));
	}
}
