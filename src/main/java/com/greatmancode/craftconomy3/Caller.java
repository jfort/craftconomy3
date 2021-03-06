/*
 * This file is part of Craftconomy3.
 *
 * Copyright (c) 2011-2012, Greatman <http://github.com/greatman/>
 *
 * Craftconomy3 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Craftconomy3 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Craftconomy3.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.greatmancode.craftconomy3;

import java.io.File;
import java.util.List;

import com.greatmancode.craftconomy3.commands.interfaces.CommandManager;

/**
 * Represents a server Caller
 * 
 * @author greatman
 * 
 */
public interface Caller {

	String CHAT_PREFIX = "{{DARK_GREEN}}[{{WHITE}}Money{{DARK_GREEN}}]{{WHITE}} ";

	/**
	 * Disable the plugin
	 */
	void disablePlugin();

	/**
	 * Check the permissions of a player
	 * 
	 * @param playerName The player name to check
	 * @param perm The permission node to check
	 * @return True if the player have the permission. Else false (Always true for the Console)
	 */
	boolean checkPermission(String playerName, String perm);

	/**
	 * Sends a message to a player
	 * 
	 * @param playerName The player name to send the message
	 * @param message The message to send
	 */
	void sendMessage(String playerName, String message);

	/**
	 * Retrieve the world name that a player is currently in
	 * 
	 * @param playerName The player name to retrieve the world
	 * @return The world name the player is currently in. Returns "" when the player is offline
	 */
	String getPlayerWorld(String playerName);

	/**
	 * Checks if a player is online
	 * 
	 * @param playerName The player name
	 * @return True if the player is online. Else false.
	 */
	boolean isOnline(String playerName);

	/**
	 * Add color in a message
	 * 
	 * @param message The message to add color in
	 * @return The message with colors.
	 */
	String addColor(String message);

	/**
	 * Checks if a world exist.
	 * 
	 * @param worldName The world name to check
	 * @return True if the world exist. Else false.
	 */
	boolean worldExist(String worldName);

	/**
	 * Retrieve the default world of the server
	 * 
	 * @return The default world name
	 */
	String getDefaultWorld();

	/**
	 * Get the data folder (Aka. the plugin folder)
	 * 
	 * @return The data folder
	 */
	File getDataFolder();

	/**
	 * Schedule something to be run each X seconds.
	 * 
	 * @param entry the runnable class
	 * @param firstStart When we should run this class first?
	 * @param repeating What is the interval to be run at? (In seconds)
	 */

	/**
	 * Schedule a repeating task to be run in non-async mode.
	 * 
	 * @param entry The Runnable to be run.
	 * @param firstStart When should the task be run (In seconds)
	 * @param repeating How much seconds to be waiting bewtween each repeats? (0 to disable)
	 * @return the task ID
	 */
	int schedule(Runnable entry, long firstStart, long repeating);

	/**
	 * Schedule a repeating task to be run.
	 * 
	 * @param entry The Runnable to be run.
	 * @param firstStart When should the task be run (In seconds)
	 * @param repeating How much seconds to be waiting bewtween each repeats? (0 to disable)
	 * @param async Should the task be async? (Threaded)
	 * @return the task ID
	 */
	int schedule(Runnable entry, long firstStart, long repeating, boolean async);

	/**
	 * Cancel a current scheduled task
	 * 
	 * @param id The task ID.
	 */
	void cancelSchedule(int id);

	/**
	 * Delay a task
	 * 
	 * @param entry The task to delay
	 * @param start When should the task be started? (In seconds)
	 * @return The task ID
	 */
	int delay(Runnable entry, long start);

	/**
	 * Delay a task
	 * 
	 * @param entry The task to delay
	 * @param start When should the task be started? (In seconds)
	 * @param async Should the task be Async? (Threaded)
	 * @return The task ID
	 */
	int delay(Runnable entry, long start, boolean async);

	/**
	 * Retrieve a list of online players
	 * 
	 * @return A list of all players online.
	 */
	List<String> getOnlinePlayers();

	/**
	 * Add a command in the server
	 * 
	 * @param name The name of the command
	 * @param help The help line of the command
	 * @param manager The manager that manage the command.
	 */
	void addCommand(String name, String help, CommandManager manager);

	/**
	 * Retrieve the server version.
	 * 
	 * @return The server version.
	 */
	String getServerVersion();

	/**
	 * Retrieve the plugin version.
	 * 
	 * @return The plugin version.
	 */
	String getPluginVersion();

	/**
	 * Check if the user is a Operator.
	 * 
	 * @param playerName The player name to check
	 * @return True if the player is a OP else false.
	 */
	boolean isOp(String playerName);

	/**
	 * Load a library.
	 * 
	 * @param path The path to the .jar of the library.
	 */
	void loadLibrary(String path);
	
	/**
	 * Register a permission on the server.
	 * @param permissionNode The permission node to register.
	 */
	void registerPermission(String permissionNode);
}
