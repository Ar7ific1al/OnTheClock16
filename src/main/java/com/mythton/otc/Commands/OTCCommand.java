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
package com.mythton.otc.Commands;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.mythton.otc.OTC;
import com.mythton.otc.Utils.Log;
import com.mythton.otc.Utils.OTCHelper;
import com.mythton.otc.Utils.UUIDFetcher;

public class OTCCommand implements CommandExecutor
{
	OTC plugin;

	public OTCCommand(OTC instance)
	{
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("otc"))
			{
				try
				{
					if (args.length < 1)
					{
						player.sendMessage(OTCHelper.formatString("&6[OTC] &eOnTheClock was originally "
										+ "written by Ar7ific1al and is now maintained by JagSwag2014", ""));	

						if (player.hasPermission("otc.announce.custom"))
						{
							player.sendMessage(OTCHelper.formatString("&6[OTC] &eYou have permission &cotc.announce.custom&e."
									+ " Use &c/otc cb Message&e to set your own custom join message."
									+ " You can use format codes. Here's an example:",""));
							player.sendMessage("/otc cb &e" + player.getName() + " is here to &osteal &eyour potatoes!");
						}
					}
					else
					{
						if (args[0].equalsIgnoreCase("custom")
								&& player.hasPermission("otc.announce.custom"))
						{
							File f = new File(plugin.clockDir,
									player.getUniqueId() + ".clock");
							FileConfiguration tempfc = new YamlConfiguration();
							tempfc.load(f);
							if (args.length < 2)
							{
								player.sendMessage(OTCHelper.formatString("&6[OTC] &eYour announcement is currently set to: ","")
										+ tempfc.getString("BroadcastMessage"));
							}
							else if (args.length > 1)
							{
								String message = "";
								for (int i = 1; i < args.length; i++)
								{
									message += args[i];
									if (i != args.length)
										message += " ";
								}
								tempfc.set("BroadcastMessage", message);
								tempfc.save(f);
								player.sendMessage(OTCHelper.formatString("&6[OTC] &eYour announcement was changed."
										+ " Here is a preview:\n" + message, ""));
							}
						} else if(args[0].equalsIgnoreCase("view")){
							if(args.length == 1) {
								if(player.hasPermission("otc.view")) {
									
								} else {
									noPerms(player);
								}
							} else {
								if(player.hasPermission("otc.view.other")) {
									if(Bukkit.getPlayer(args[1]) != null) {
										UUID uuid = Bukkit.getPlayer(args[1]).getUniqueId();
										
										File file = new File(plugin.clockDir, uuid.toString() + ".clock");
										if(file.exists()) {
											FileConfiguration timesheet = new YamlConfiguration();
											timesheet.load(file);
											
										} else {
											player.sendMessage(Log.ColorMessage("&4Error: No timesheet found."));
										}
									} else {
										UUID uuid = null;
										UUIDFetcher uuidF = new UUIDFetcher(Arrays.asList(args[1]));
										try {
											Map<String, UUID> map = uuidF.call();
											uuid = map.get(args[1]);
										} catch (Exception e) {
											noPlayer(player);
											return true;
										}
										
										File file = new File(plugin.clockDir, uuid.toString() + ".clock");
										if(file.exists()) {
											FileConfiguration timesheet = new YamlConfiguration();
											timesheet.load(file);
											
										} else {
											player.sendMessage(Log.ColorMessage("&4Error: No timesheet found."));
										}
									}
								} else {
									noPerms(player);
									return true;
								}
							}
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}
	
	private void noPerms(Player player) {
		player.sendMessage(Log.ColorMessage("&4Error: You do not have permission to use that."));
	}
	
	private void noPlayer(Player player) {
		player.sendMessage(Log.ColorMessage("&4Error: No player could be found by that name."));
	}
}
