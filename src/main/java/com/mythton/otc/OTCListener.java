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
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OTCListener implements Listener
{
	private OTC plugin;

	public OTCListener(OTC instance)
	{
		plugin = instance;
		OTC.console.log(Level.INFO, "[OTC] Player join listener registered.");
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) throws IOException,
			InvalidConfigurationException
	{
		Player player = e.getPlayer();
		if (player.hasPermission("otc.clock"))
		{
			File f = new File(plugin.clockDir, player.getUniqueId() + ".clock");
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

}
