package de.rexlmanu.lobby.methods;

public enum HeadsType {

	rexlManu("rexlManu", 500,"rexlManu","§arexlManu"),
	rexlNico("rexlNico", 500,"rexlNico","§arexlNico"),
	GermanLetsPlay("GermanLetsPlay",500,"GermanLetsPlay","§aGermanLetsPlay"),
	Zombey("Zombey", 500,"Zombey","§aZombey"),
	Maudado("Maudado", 500,"Maudado","§aMaudado"),
	Paluten("Paluten", 500,"Paluten","§aPaluten"),
	Wintercracker("Wintercracker", 500,"Wintercracker", "§aWintercracker");
	
	private String ingamename;
	private int prize;
	private String Mysqlname;
	private String displayname;
	
	private HeadsType(String ingamename,int prize, String Mysqlname, String displayname){
		this.displayname = displayname;
		this.Mysqlname = Mysqlname;
		this.ingamename = ingamename;
		this.prize = prize;
	}
	public String getIngamename() {
		return ingamename;
	}
	public int getPrize() {
		return prize;
	}
	public String getMysqlname() {
		return Mysqlname;
	}
	public String getDisplayname() {
		return displayname;
	}
}
