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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mythton.otc.OTC;
import com.mythton.otc.Commands.OTCCommand;

import net.md_5.bungee.api.ChatColor;

/**
 * 
 */
public class MainInventoryListener implements Listener {

	OTC plugin;
	public MainInventoryListener(OTC plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) throws FileNotFoundException, IOException, InvalidConfigurationException {
		if(e.getInventory() == null) {
			return;
		}
		
		if (e.getInventory().getTitle().contains("Offenses: ")) {
			ItemStack itemStack = e.getCurrentItem();
			if (itemStack != null && itemStack.getType() != Material.AIR) {
				if (itemStack.getItemMeta().getDisplayName() != null) {
					Player player = (Player) e.getWhoClicked();
					if (itemStack.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "2017")) {
						e.setResult(Result.DENY);
						if (itemStack.getAmount() == 0)
							return;

						ItemStack jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec;
						ItemMeta janM, febM, marM, aprM, mayM, junM, julM, augM, sepM, octM, novM, decM;
						
						jan = new ItemStack(Material.SNOW_BALL, jan(player.getUniqueId(), 2017));
						janM = jan.getItemMeta();
						
						jan.setItemMeta(janM);
						
						feb = new ItemStack(Material.ARROW, feb(player.getUniqueId(), 2017));
						febM = feb.getItemMeta();
						
						feb.setItemMeta(febM);
						
						mar = new ItemStack(Material.YELLOW_FLOWER, mar(player.getUniqueId(), 2017));
						marM = mar.getItemMeta();
						
						mar.setItemMeta(marM);
						
						apr = new ItemStack(Material.FEATHER, apr(player.getUniqueId(), 2017));
						aprM = apr.getItemMeta();
						
						apr.setItemMeta(aprM);
						
						may = new ItemStack(Material.BEETROOT, may(player.getUniqueId(), 2017));
						mayM = may.getItemMeta();
						
						may.setItemMeta(mayM);
						
						jun = new ItemStack(Material.BOAT, jun(player.getUniqueId(), 2017));
						junM = jun.getItemMeta();
						
						jun.setItemMeta(junM);
						
						jul = new ItemStack(Material.FIREWORK, jul(player.getUniqueId(), 2017));
						julM = jul.getItemMeta();
						
						jul.setItemMeta(julM);
						
						aug = new ItemStack(Material.APPLE, aug(player.getUniqueId(), 2017));
						augM = aug.getItemMeta();
						
						aug.setItemMeta(augM);
						
						sep = new ItemStack(Material.BOOK, sep(player.getUniqueId(), 2017));
						sepM = sep.getItemMeta();
						
						sep.setItemMeta(sepM);
						
						oct = new ItemStack(Material.LEAVES, oct(player.getUniqueId(), 2017));
						octM = oct.getItemMeta();
						
						oct.setItemMeta(octM);
						
						nov = new ItemStack(Material.SNOW, nov(player.getUniqueId(), 2017));
						novM = nov.getItemMeta();
						
						nov.setItemMeta(novM);
						
						dec = new ItemStack(Material.DOUBLE_PLANT, dec(player.getUniqueId(), 2017));
						decM = dec.getItemMeta();
						
						dec.setItemMeta(decM);
					} 
				}
			}
		}
	}
	
	private int jan(UUID pUUID, int year) throws FileNotFoundException, IOException, InvalidConfigurationException {
		int num = 0;
		UUID uuid = OTCCommand.uuidMap.get(pUUID);
		
		File file = new File(plugin.clockDir, uuid.toString() + ".clock");
		FileConfiguration timesheet = new YamlConfiguration();
		
		timesheet.load(file);
		num = timesheet.getConfigurationSection(year + ".January").getKeys(false).size();
		
		return num;
	}
	
	private int feb(UUID pUUID, int year) throws FileNotFoundException, IOException, InvalidConfigurationException {
		int num = 0;
		UUID uuid = OTCCommand.uuidMap.get(pUUID);
		
		File file = new File(plugin.clockDir, uuid.toString() + ".clock");
		FileConfiguration timesheet = new YamlConfiguration();
		
		timesheet.load(file);
		num = timesheet.getConfigurationSection(year + ".February").getKeys(false).size();
		
		return num;
	}
	
	private int mar(UUID pUUID, int year) throws FileNotFoundException, IOException, InvalidConfigurationException {
		int num = 0;
		UUID uuid = OTCCommand.uuidMap.get(pUUID);
		
		File file = new File(plugin.clockDir, uuid.toString() + ".clock");
		FileConfiguration timesheet = new YamlConfiguration();
		
		timesheet.load(file);
		num = timesheet.getConfigurationSection(year + ".March").getKeys(false).size();
		
		return num;
	}
	
	private int apr(UUID pUUID, int year) throws FileNotFoundException, IOException, InvalidConfigurationException {
		int num = 0;
		UUID uuid = OTCCommand.uuidMap.get(pUUID);
		
		File file = new File(plugin.clockDir, uuid.toString() + ".clock");
		FileConfiguration timesheet = new YamlConfiguration();
		
		timesheet.load(file);
		num = timesheet.getConfigurationSection(year + ".April").getKeys(false).size();
		
		return num;
	}
	
	private int may(UUID pUUID, int year) throws FileNotFoundException, IOException, InvalidConfigurationException {
		int num = 0;
		UUID uuid = OTCCommand.uuidMap.get(pUUID);
		
		File file = new File(plugin.clockDir, uuid.toString() + ".clock");
		FileConfiguration timesheet = new YamlConfiguration();
		
		timesheet.load(file);
		num = timesheet.getConfigurationSection(year + ".May").getKeys(false).size();
		
		return num;
	}
	
	private int jun(UUID pUUID, int year) throws FileNotFoundException, IOException, InvalidConfigurationException {
		int num = 0;
		UUID uuid = OTCCommand.uuidMap.get(pUUID);
		
		File file = new File(plugin.clockDir, uuid.toString() + ".clock");
		FileConfiguration timesheet = new YamlConfiguration();
		
		timesheet.load(file);
		num = timesheet.getConfigurationSection(year + ".June").getKeys(false).size();
		
		return num;
	}
	
	private int jul(UUID pUUID, int year) throws FileNotFoundException, IOException, InvalidConfigurationException {
		int num = 0;
		UUID uuid = OTCCommand.uuidMap.get(pUUID);
		
		File file = new File(plugin.clockDir, uuid.toString() + ".clock");
		FileConfiguration timesheet = new YamlConfiguration();
		
		timesheet.load(file);
		num = timesheet.getConfigurationSection(year + ".July").getKeys(false).size();
		
		return num;
	}
	
	private int aug(UUID pUUID, int year) throws FileNotFoundException, IOException, InvalidConfigurationException {
		int num = 0;
		UUID uuid = OTCCommand.uuidMap.get(pUUID);
		
		File file = new File(plugin.clockDir, uuid.toString() + ".clock");
		FileConfiguration timesheet = new YamlConfiguration();
		
		timesheet.load(file);
		num = timesheet.getConfigurationSection(year + ".August").getKeys(false).size();
		
		return num;
	}
	
	private int sep(UUID pUUID, int year) throws FileNotFoundException, IOException, InvalidConfigurationException {
		int num = 0;
		UUID uuid = OTCCommand.uuidMap.get(pUUID);
		
		File file = new File(plugin.clockDir, uuid.toString() + ".clock");
		FileConfiguration timesheet = new YamlConfiguration();
		
		timesheet.load(file);
		num = timesheet.getConfigurationSection(year + ".September").getKeys(false).size();
		
		return num;
	}
	
	private int oct(UUID pUUID, int year) throws FileNotFoundException, IOException, InvalidConfigurationException {
		int num = 0;
		UUID uuid = OTCCommand.uuidMap.get(pUUID);
		
		File file = new File(plugin.clockDir, uuid.toString() + ".clock");
		FileConfiguration timesheet = new YamlConfiguration();
		
		timesheet.load(file);
		num = timesheet.getConfigurationSection(year + ".October").getKeys(false).size();
		
		return num;
	}
	
	private int nov(UUID pUUID, int year) throws FileNotFoundException, IOException, InvalidConfigurationException {
		int num = 0;
		UUID uuid = OTCCommand.uuidMap.get(pUUID);
		
		File file = new File(plugin.clockDir, uuid.toString() + ".clock");
		FileConfiguration timesheet = new YamlConfiguration();
		
		timesheet.load(file);
		num = timesheet.getConfigurationSection(year + ".November").getKeys(false).size();
		
		return num;
	}
	
	private int dec(UUID pUUID, int year) throws FileNotFoundException, IOException, InvalidConfigurationException {
		int num = 0;
		UUID uuid = OTCCommand.uuidMap.get(pUUID);
		
		File file = new File(plugin.clockDir, uuid.toString() + ".clock");
		FileConfiguration timesheet = new YamlConfiguration();
		
		timesheet.load(file);
		num = timesheet.getConfigurationSection(year + ".December").getKeys(false).size();
		
		return num;
	}
}
