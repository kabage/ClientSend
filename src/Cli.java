import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Cli {

	private static final Logger log = Logger.getLogger(Cli.class.getName());

	private String[] args = null;
	String key1, key2, imsi, phone_number, message, query;
	private Options options = new Options();
	CommandLine cmd;
	CmdClient client;

	public Cli(String[] args) {

		this.args = args;

		options.addOption(null, "help", false, "options available");
		options.addOption(null, "query", false, "query data on device");
		options.addOption(null, "key1", true, "first api key");
		options.addOption(null, "key2", true, "second api key");
		options.addOption(null, "imsi", true, "phone_identifier");
		options.addOption(null, "phone_number", true, "recepient phone number");
		options.addOption(null, "message", true, "the message to be sent");

	}

	public void parse() {

		CommandLineParser parser = new BasicParser();

		try {

			cmd = parser.parse(options, args);

			if (cmd.hasOption("help")) {
				help();

			} else if (cmd.hasOption("query")) {

				System.out.print("> ");
				Scanner in = new Scanner(System.in);
				String query = in.nextLine();
				in.close();
				System.out.println("the query made was" + ".. " + query);

				if (query.equalsIgnoreCase("select messages")) {

					System.out.println("chill....");

					key1 = cmd.getOptionValue("key1");
					key2 = cmd.getOptionValue("key2");
					imsi = cmd.getOptionValue("imsi");

					client = new CmdClient();
					client.connectsendMessage(key1, key2, imsi, "",
							"select messages", "query made");

				} else if (query.equalsIgnoreCase("select received_calls")) {
					System.out.println("chill....");
					key1 = cmd.getOptionValue("key1");
					key2 = cmd.getOptionValue("key2");
					imsi = cmd.getOptionValue("imsi");
					client = new CmdClient();
					client.connectsendMessage(key1, key2, imsi, "",
							"select received_calls", "query made");

				} else if (query.equalsIgnoreCase("select missed_calls")) {
					System.out.println("chill....");
					key1 = cmd.getOptionValue("key1");
					key2 = cmd.getOptionValue("key2");
					imsi = cmd.getOptionValue("imsi");
					client = new CmdClient();
					client.connectsendMessage(key1, key2, imsi, "",
							"select missed_calls", "query made");

				}
				// in.close();

			}

			else if (cmd.hasOption("message")) {
				key1 = cmd.getOptionValue("key1");
				key2 = cmd.getOptionValue("key2");
				imsi = cmd.getOptionValue("imsi");
				phone_number = cmd.getOptionValue("phone_number");
				message = cmd.getOptionValue("message");
				client = new CmdClient();

				client.connectsendMessage(key1, key2, imsi, phone_number,
						message, "message sent");

				System.out.println();

			} else if (cmd.hasOption("login")) {

			}

		} catch (ParseException e) {

			log.log(Level.SEVERE, "Failed to parse comand line properties", e);

			help();

		}

	}

	private void help() {
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Main", options);
		System.exit(0);

	}

}