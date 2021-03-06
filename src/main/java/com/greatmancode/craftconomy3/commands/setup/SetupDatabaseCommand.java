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
package com.greatmancode.craftconomy3.commands.setup;

import com.alta189.simplesave.exceptions.ConnectionException;
import com.alta189.simplesave.exceptions.TableRegistrationException;
import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.SetupWizard;
import com.greatmancode.craftconomy3.commands.interfaces.CraftconomyCommand;
import com.greatmancode.craftconomy3.utils.Tools;

public class SetupDatabaseCommand extends CraftconomyCommand {

	private static final String ERROR_MESSAGE = "{{DARK_RED}}A error occured. The error is: {{WHITE}}%s";
	private static final String CONFIG_NODE = "System.Database.Type";

	@Override
	public void execute(String sender, String[] args) {
		if (SetupWizard.getState() == SetupWizard.DATABASE_SETUP) {
			if (args.length == 0) {
				Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_GREEN}}Database setup step. Please select the database backend by using those following commands. (Use SQLite if unsure)");
				Common.getInstance().getServerCaller().sendMessage(sender, "/ccsetup database type <sqlite/mysql/h2>");
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("type")) {
					typeHandler(sender, args[1]);
				} else if (args[0].equals("address")) {
					Common.getInstance().getConfigurationManager().getConfig().setValue("System.Database.Address", args[1]);
					Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_GREEN}}Alright! Please type {{WHITE}}/ccsetup database port <Your port> {{DARK_GREEN}}to set your MySQL port (Usually 3306)");
				} else if (args[0].equals("port")) {
					setPort(sender, args[1]);
				} else if (args[0].equals("username")) {
					Common.getInstance().getConfigurationManager().getConfig().setValue("System.Database.Username", args[1]);
					Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_GREEN}}Saved! Please type {{WHITE}}/ccsetup database password <Password> {{DARK_GREEN}}to set your MySQL password (enter \"\" for none)");
				} else if (args[0].equals("password")) {
					Common.getInstance().getConfigurationManager().getConfig().setValue("System.Database.Password", args[1]);
					Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_GREEN}}Last step for database! Please type {{WHITE}}/ccsetup database db <Database Name> {{DARK_GREEN}}to set your MySQL database.");
				} else if (args[0].equals("db")) {
					Common.getInstance().getConfigurationManager().getConfig().setValue("System.Database.Db", args[1]);
					Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_GREEN}}Done! Please type {{WHITE}}/ccsetup database test {{DARK_GREEN}}to test your settings!");
				}
			} else if (args.length == 1 && args[0].equals("test")) {
				testMySQL(sender);
			} else {
				Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_RED}}Wrong usage.");
			}
		} else {
			Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_RED}}Wrong setup status for this cmd. If you didin't start the setup yet, use /ccsetup");
		}

	}

	private void testMySQL(String sender) {
		if (Common.getInstance().getConfigurationManager().getConfig().getString(CONFIG_NODE).equals("mysql")) {
			try {
				Common.getInstance().initialiseDatabase();
				SetupWizard.setState(SetupWizard.MULTIWORLD_SETUP);
				Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_GREEN}}Awesome! You can type {{WHITE}}/ccsetup multiworld {{DARK_GREEN}}to continue the setup!");
			} catch (TableRegistrationException e) {
				Common.getInstance().getServerCaller().sendMessage(sender, String.format(ERROR_MESSAGE, e.getMessage()));
				Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_GREEN}}Be sure that you entered valid information! Commands are: {{WHITE}}/ccsetup database <address/port/username/password/db> <Value>");
			} catch (ConnectionException e) {
				Common.getInstance().getServerCaller().sendMessage(sender, String.format(ERROR_MESSAGE, e.getMessage()));
				Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_GREEN}}Be sure that you entered valid information! Commands are: {{WHITE}}/ccsetup database <address/port/username/password/db> <Value>");
			}
		} else {
			Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_RED}}Must be in MySQL mode for this command.");
		}

	}

	private void setPort(String sender, String stringPort) {
		if (Tools.isInteger(stringPort)) {
			int port = Integer.parseInt(stringPort);
			Common.getInstance().getConfigurationManager().getConfig().setValue("System.Database.Port", port);
			Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_GREEN}}Saved! Please type {{WHITE}}/ccsetup database username <Username> {{DARK_GREEN}}to set your MySQL username");
		} else {
			Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_RED}}Invalid port!");
		}

	}

	private void typeHandler(String sender, String type) {
		if (type.equalsIgnoreCase("sqlite")) {
			Common.getInstance().getConfigurationManager().getConfig().setValue(CONFIG_NODE, "sqlite");
			try {
				Common.getInstance().initialiseDatabase();
				SetupWizard.setState(SetupWizard.MULTIWORLD_SETUP);
				Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_GREEN}}Awesome! You can type {{WHITE}}/ccsetup multiworld {{DARK_GREEN}}to continue the setup!");
			} catch (TableRegistrationException e) {
				Common.getInstance().getServerCaller().sendMessage(sender, String.format(ERROR_MESSAGE, e.getMessage()));
			} catch (ConnectionException e) {
				Common.getInstance().getServerCaller().sendMessage(sender, String.format(ERROR_MESSAGE, e.getMessage()));
			}
		} else if (type.equalsIgnoreCase("mysql")) {
			Common.getInstance().getConfigurationManager().getConfig().setValue(CONFIG_NODE, "mysql");
			Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_GREEN}}Alright! Please type {{WHITE}}/ccsetup database address <Your host> {{DARK_GREEN}}to set your MySQL address");
		} else if (type.equalsIgnoreCase("h2")) {
			Common.getInstance().getConfigurationManager().getConfig().setValue(CONFIG_NODE, "h2");
			try {
				Common.getInstance().initialiseDatabase();
				SetupWizard.setState(SetupWizard.MULTIWORLD_SETUP);
				Common.getInstance().getServerCaller().sendMessage(sender, "{{DARK_GREEN}}Awesome! You can type {{WHITE}}/ccsetup multiworld {{DARK_GREEN}}to continue the setup!");
			} catch (TableRegistrationException e) {
				Common.getInstance().getServerCaller().sendMessage(sender, String.format(ERROR_MESSAGE, e.getMessage()));
			} catch (ConnectionException e) {
				Common.getInstance().getServerCaller().sendMessage(sender, String.format(ERROR_MESSAGE, e.getMessage()));
			}
		}

	}

	@Override
	public String help() {
		return "/ccsetup - Start the setup";
	}

	@Override
	public int maxArgs() {
		return 2;
	}

	@Override
	public int minArgs() {
		return 0;
	}

	@Override
	public boolean playerOnly() {
		return false;
	}

	@Override
	public String getPermissionNode() {
		return "craftconomy.setup";
	}
}
