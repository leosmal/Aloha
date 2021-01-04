package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
	Directory rootDirectory = null;
	Directory currentDirectory = null;

	public Solution() {
		// creating base diractory
		rootDirectory = new Directory();
		currentDirectory = rootDirectory;
	}

	public static void main(String[] args) {
		List<Command> commands = new ArrayList<Command>();
		Solution assesment = new Solution();
		Command currentCommand = null;
		initializeCommands(assesment, commands);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Enter command:");
			for (String promptCommand = br.readLine(); promptCommand != null; promptCommand = br
					.readLine()) {

				currentCommand = validateCommand(promptCommand, commands);
				if (currentCommand == null) {
					System.out.println("Unrecognizad Command");
					System.out.println("Enter command:");
					break;
				}
				if (!validateParameter(promptCommand, currentCommand)) {
					System.out.println("Unrecognizad parameters");
					System.out.println("Enter command:");
					break;
				}
				executeCommand(currentCommand);
			}
			br.close();
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	/*
	 * Basic validation ,check the command is present
	 */
	private static Command validateCommand(String promptCommand,
			List<Command> commandList) {
		String[] parts = promptCommand.split(" ");

		for (Command command : commandList) {
			if (command.getCommandName().equals(parts[0]))
				return command;
		}
		return null;
	}

	/*
	 * Basic check ,validate that at least the minimum number of arguments are
	 * present
	 */
	private static boolean validateParameter(String promptCommand,
			Command currentCommand) {
		String[] parts = promptCommand.split(" ");
		if (currentCommand.getArguments().size() == parts.length - 1) {
			List <String> arguments = new ArrayList<>();
			//Omitting index 0 since its the command
			for (int i = 1; i < parts.length; i++) {
				arguments.add(parts[i]);
			}
			//replacing template arguments with actual ones
			currentCommand.setArguments(arguments);
			return true;
		}
			
		return false;
	}

	// Executing command
	private static boolean executeCommand(Command currentCommand) {

		try {
			currentCommand.execute();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	private static void initializeCommands(Solution assesment,
			List<Command> commandList) {
		// mkdir command
		Command mkdir = assesment.new mkdirCommand();
		mkdir.setCommandName("mkdir");
		mkdir.setArguments(Arrays.asList(new String[] { "dirname" }));
		commandList.add(mkdir);

		// quit command
		Command quit = assesment.new quitCommand();
		quit.setCommandName("quit");
		quit.setArguments(Arrays.asList(new String[] {}));
		commandList.add(quit);

		//
		// // current directory command
		// Command pwd = assesment.new Command();
		// pwd.setCommandName("pwd");
		// pwd.setArguments(Arrays.asList(new String[] { "dirname" }));
		//
		// // ls command
		// Command ls = assesment.new Command();
		// ls.setCommandName("ls");
		// ls.setArguments(Arrays.asList(new String[] { "*extra*" }));
		//
		// // touch command
		// Command touch = assesment.new Command();
		// touch.setCommandName("touch");
		// touch.setArguments(Arrays.asList(new String[] { "filename" }));

	}

	abstract class Command {

		private String commandName;
		private List<String> arguments;

		public abstract void execute();
		
		public String getCommandName() {
			return commandName;
		}

		public void setCommandName(String commandName) {
			this.commandName = commandName;
		}

		public List<String> getArguments() {
			return arguments;
		}

		public void setArguments(List<String> arguments) {
			this.arguments = arguments;
		}
		
	}

	class mkdirCommand extends Command {
		
		public void execute() {
			Directory newDirectory = new Directory();
			newDirectory.setName(getArguments().get(0));
			if (currentDirectory.getSubDirs() == null) {
				List<Directory> subdirs = new ArrayList<Directory>();
				currentDirectory.setSubDirs(subdirs);
			}
			currentDirectory.getSubDirs().add(newDirectory);
			System.out.println("directory Created");
		}
	}

	class quitCommand extends Command {

		public void execute() {
			System.out.println("Exiting");
			System.exit(0);
		}
	}

	class Directory {
		String name;
		List<Directory> subDirs;
		List<File> files;

		public List<Directory> getSubDirs() {
			return subDirs;
		}

		public void setSubDirs(List<Directory> subDirs) {
			this.subDirs = subDirs;
		}

		public List<File> getFiles() {
			return files;
		}

		public void setFiles(List<File> files) {
			this.files = files;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	class File {
		String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
