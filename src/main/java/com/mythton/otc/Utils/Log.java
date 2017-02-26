/**
 * This file is part of Function_One.
 * Function_One is free software: you can redistribute it or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Function_One is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Function_One. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mythton.otc.Utils;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class Log {
	public static void LogMessage(String message, ConsoleCommandSender console) {
		console.sendMessage(message);
	}

	public static String ColorMessage(String input) {

		String output = "";

		output = input.replaceAll("&([0-9a-f])", "\u00A7$1");
		output = output.replaceAll("&([k-o])", "\u00A7$1");

		return output;
	}

	public static void msg(CommandSender sender, String msg) {
		sender.sendMessage(Log.ColorMessage(msg));
	}
}
