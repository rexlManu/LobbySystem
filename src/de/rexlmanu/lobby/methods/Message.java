package de.rexlmanu.lobby.methods;


public enum Message {

	PREFIX("§6Lobby §8▌ §r"),
	NOPERMISSION("§6Lobby §8▌ §cDazu hast du keine Rechte."),
	CONSOLESENDER("§6Lobby §8▌ §cDas kann nur ein Spieler ausführen.");
	
	private String value;
	
	private Message(String value){
		this.value = value;
	}
	public String getValue(){
		return this.value;
	}
	
	public static String get(Message msg){
		return msg.getValue();
	}
	
}
