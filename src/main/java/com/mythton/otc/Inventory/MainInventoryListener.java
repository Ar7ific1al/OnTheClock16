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
package com.mythton.otc.Inventory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mythton.otc.OTC;
import com.mythton.otc.Commands.OTCCommand;
import com.mythton.otc.Utils.OTCHelper;

import net.md_5.bungee.api.ChatColor;

/**
 * 
 */
public class MainInventoryListener implements Listener {
	OTCHelper otc;
	OTC plugin;
	public MainInventoryListener(OTC plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		List<String> list = new ArrayList<String>();
		list.add("Timesheet: ");
		list.add("2017 Timesheet");
		list.add("January");
		list.add("February");
		list.add("March");
		list.add("April");
		list.add("May");
		list.add("June");
		list.add("July");
		list.add("August");
		list.add("September");
		list.add("October");
		list.add("November");
		list.add("December");
		
		Player player = (Player) e.getPlayer();
		for(int i = 0; i < list.size(); i++) {
			if(e.getInventory().getTitle().contains(list.get(i))) {
				OTCCommand.uuidMap.remove(player.getUniqueId());
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) throws FileNotFoundException, IOException, InvalidConfigurationException {
		if(e.getInventory() == null) {
			return;
		}
		
		if (e.getInventory().getTitle().contains("Timesheet: ")) {
			ItemStack itemStack = e.getCurrentItem();
			if (itemStack != null && itemStack.getType() != Material.AIR) {
				if (itemStack.getItemMeta().getDisplayName() != null) {
					Player player = (Player) e.getWhoClicked();
					if (itemStack.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "2017")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;

						months(player);
					}
				}
			}
		} else if(e.getInventory().getTitle().equals("2017 Timesheet")) {
			ItemStack itemStack = e.getCurrentItem();
			if (itemStack != null && itemStack.getType() != Material.AIR) {
				if (itemStack.getItemMeta().getDisplayName() != null) {
					Player player = (Player) e.getWhoClicked();
					if (itemStack.getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "January")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;
						
						days(1, player);
						
					} else if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "February")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;
						
						days(2, player);
						
					} else if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "March")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;
						
						days(3, player);
						
					} else if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "April")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;
						
						days(4, player);
						
					} else if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "May")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;
						
						days(5, player);
						
					} else if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "June")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;
						
						days(6, player);
						
					} else if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.RED + "July")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;
						
						days(7, player);
						
					} else if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "August")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;
						
						days(8, player);
						
					} else if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "September")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;
						
						days(9, player);
						
					} else if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "October")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;
						
						days(10, player);
						
					} else if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.BLUE + "November")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;
						
						days(11, player);
						
					} else if(itemStack.getItemMeta().getDisplayName().equals(ChatColor.DARK_BLUE + "December")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;
						
						days(12, player);
					}
				}
			}
		}
	}
	
	private void months(Player player) throws FileNotFoundException, IOException, InvalidConfigurationException {
		Inventory inv = null;
		ItemStack jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec;
		ItemMeta janM, febM, marM, aprM, mayM, junM, julM, augM, sepM, octM, novM, decM;
		
		jan = new ItemStack(Material.SNOW_BALL, otc.jan(player.getUniqueId(), 2017));
		janM = jan.getItemMeta();
		janM.setDisplayName(ChatColor.DARK_AQUA + "January");
		jan.setItemMeta(janM);
		
		feb = new ItemStack(Material.ARROW, otc.feb(player.getUniqueId(), 2017));
		febM = feb.getItemMeta();
		febM.setDisplayName(ChatColor.LIGHT_PURPLE + "February");
		feb.setItemMeta(febM);
		
		mar = new ItemStack(Material.YELLOW_FLOWER, otc.mar(player.getUniqueId(), 2017));
		marM = mar.getItemMeta();
		marM.setDisplayName(ChatColor.AQUA + "March");
		mar.setItemMeta(marM);
		
		apr = new ItemStack(Material.FEATHER, otc.apr(player.getUniqueId(), 2017));
		aprM = apr.getItemMeta();
		aprM.setDisplayName(ChatColor.GREEN + "April");
		apr.setItemMeta(aprM);
		
		may = new ItemStack(Material.BEETROOT, otc.may(player.getUniqueId(), 2017));
		mayM = may.getItemMeta();
		mayM.setDisplayName(ChatColor.GREEN + "May");
		may.setItemMeta(mayM);
		
		jun = new ItemStack(Material.BOAT, otc.jun(player.getUniqueId(), 2017));
		junM = jun.getItemMeta();
		junM.setDisplayName(ChatColor.DARK_GREEN + "June");
		jun.setItemMeta(junM);
		
		jul = new ItemStack(Material.FIREWORK, otc.jul(player.getUniqueId(), 2017));
		julM = jul.getItemMeta();
		julM.setDisplayName(ChatColor.RED + "July");
		jul.setItemMeta(julM);
		
		aug = new ItemStack(Material.APPLE, otc.aug(player.getUniqueId(), 2017));
		augM = aug.getItemMeta();
		augM.setDisplayName(ChatColor.YELLOW + "August");
		aug.setItemMeta(augM);
		
		sep = new ItemStack(Material.BOOK, otc.sep(player.getUniqueId(), 2017));
		sepM = sep.getItemMeta();
		sepM.setDisplayName(ChatColor.YELLOW + "September");
		sep.setItemMeta(sepM);
		
		oct = new ItemStack(Material.LEAVES, otc.oct(player.getUniqueId(), 2017));
		octM = oct.getItemMeta();
		octM.setDisplayName(ChatColor.GOLD + "October");
		oct.setItemMeta(octM);
		
		nov = new ItemStack(Material.COOKED_CHICKEN, otc.nov(player.getUniqueId(), 2017));
		novM = nov.getItemMeta();
		novM.setDisplayName(ChatColor.BLUE + "November");
		nov.setItemMeta(novM);
		
		dec = new ItemStack(Material.DOUBLE_PLANT, otc.dec(player.getUniqueId(), 2017));
		decM = dec.getItemMeta();
		decM.setDisplayName(ChatColor.DARK_BLUE + "December");
		dec.setItemMeta(decM);
		
		inv = Bukkit.getServer().createInventory(player, 27, "2017 Timesheet");
		inv.setItem(0, jan);
		inv.setItem(2, feb);
		inv.setItem(4, mar);
		inv.setItem(6, apr);
		inv.setItem(8, may);
		inv.setItem(9, jun);
		inv.setItem(11, jul);
		inv.setItem(13, aug);
		inv.setItem(15, sep);
		inv.setItem(17, oct);
		inv.setItem(21, nov);
		inv.setItem(23, dec);
		
		player.openInventory(inv);
	}
	
	private void days(int month, Player player) {
		UUID uuid = OTCCommand.uuidMap.get(player.getUniqueId());
		switch(month) {
			 case 1:
				 
			 case 2:
				 
			 case 3:
				 
			 case 4:
				 
			 case 5:
				 
			 case 6:
				 
			 case 7:
				 
			 case 8:
				 
			 case 9:
				 
			 case 10:
				 
			 case 11:
				 
			 case 12:
				 
		}
	}
}
