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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

/**
 * Resource found at
 * https://www.spigotmc.org/threads/infinite-inventory-with-pages.178964/
 */
public class ScrollerInventory implements Listener {

	Plugin plugin;

	public ScrollerInventory(Plugin plugin) {
		this.plugin = plugin;
	}

	public ArrayList<Inventory> pages = new ArrayList<Inventory>();
	public UUID id;
	public int currpage = 0;
	public static HashMap<UUID, ScrollerInventory> users = new HashMap<UUID, ScrollerInventory>();

	// Running this will open a paged inventory for the specified player, with
	// the items in the arraylist specified.
	public ScrollerInventory(ArrayList<ItemStack> items, String name, Player p) {
		this.id = UUID.randomUUID();
		// create new blank page
		Inventory page = getBlankPage(name);
		// According to the items in the arraylist, add items to the
		// ScrollerInventory
		for (int i = 0; i < items.size(); i++) {
			// If the current page is full, add the page to the inventory's
			// pages arraylist, and create a new page to add the items.
			if (page.firstEmpty() == 46) {
				pages.add(page);
				page = getBlankPage(name);
				page.addItem(items.get(i));
			} else {
				// Add the item to the current page as per normal
				page.addItem(items.get(i));
			}
		}
		pages.add(page);
		// open page 0 for the specified player
		p.openInventory(pages.get(currpage));
		users.put(p.getUniqueId(), this);
	}

	public static final String nextPageName = ChatColor.AQUA + "Next Page";
	public static final String previousPageName = ChatColor.AQUA + "Previous Page";

	// This creates a blank page with the next and prev buttons
	private Inventory getBlankPage(String name) {
		Inventory page = Bukkit.createInventory(null, 54, name);

		ItemStack nextpage = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
		ItemMeta meta = nextpage.getItemMeta();
		meta.setDisplayName(nextPageName);
		nextpage.setItemMeta(meta);

		ItemStack prevpage = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 2);
		meta = prevpage.getItemMeta();
		meta.setDisplayName(previousPageName);
		prevpage.setItemMeta(meta);

		page.setItem(53, nextpage);
		page.setItem(45, prevpage);
		return page;
	}

	@EventHandler(ignoreCancelled = true)
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;
		Player p = (Player) e.getWhoClicked();
		// Get the current scroller inventory the player is looking at, if the
		// player is looking at one.
		if (!ScrollerInventory.users.containsKey(p.getUniqueId()))
			return;
		ScrollerInventory inv = ScrollerInventory.users.get(p.getUniqueId());
		if (e.getCurrentItem() == null)
			return;
		if (e.getCurrentItem().getItemMeta() == null)
			return;
		if (e.getCurrentItem().getItemMeta().getDisplayName() == null)
			return;
		if (!e.getCurrentItem().getType().equals(Material.AIR))
			e.setResult(Result.DENY);
		// If the pressed item was a nextpage button
		if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.nextPageName)) {
			e.setResult(Result.DENY);
			// If there is no next page, don't do anything
			if (inv.currpage >= inv.pages.size() - 1) {
				return;
			} else {
				// Next page exists, flip the page
				inv.currpage += 1;
				p.openInventory(inv.pages.get(inv.currpage));
			}
			// if the pressed item was a previous page button
		} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.previousPageName)) {
			e.setResult(Result.DENY);
			// If the page number is more than 0 (So a previous page exists)
			if (inv.currpage > 0) {
				// Flip to previous page
				inv.currpage -= 1;
				p.openInventory(inv.pages.get(inv.currpage));
			}
		}
	}
}