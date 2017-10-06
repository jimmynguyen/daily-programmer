import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * /r/dailyprogrammer challenge #333 - easy
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/72ivih/20170926_challenge_333_easy_packet_assembler/
 */
public class Challenge333Easy {
	public static void main(String[] args) {
		Map<String, Message> map = new HashMap<String, Message>();
		List<String> lines = readFile(args[0]);
		for (int i = 0; i < lines.size(); i++) {
			String packet = lines.get(i);
			String[] packet_parts = parsePacket(packet);
			String message_id  = packet_parts[0];
			String packet_id   = packet_parts[1];
			String num_packets = packet_parts[2];
			if (!map.containsKey(message_id)) {
				map.put(message_id, new Message(num_packets));
			}
			map.get(message_id).addPacket(packet_id, packet);
		}
	}

	public static List<String> readFile(String file_name) {
		List<String> lines = new ArrayList<String>();
		try {
			lines.addAll(Files.readAllLines(Paths.get(file_name), Charset.defaultCharset()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}

	public static String[] parsePacket(String packet) {
		return packet.split("\\s+");
	}

	private static class Message {
		int packet_count;
		String[] packets;
		public Message(String num_packets) {
			packet_count = 0;
			packets = new String[Integer.valueOf(num_packets)];
		}
		public void addPacket(String packet_id, String packet) {
			packet_count++;
			packets[Integer.valueOf(packet_id)] = packet;
			if (packet_count == packets.length) {
				this.printPackets();
			}
		}
		private void printPackets() {
			for (String packet : packets) {
				System.out.println(packet);
			}
		}
	}
}
