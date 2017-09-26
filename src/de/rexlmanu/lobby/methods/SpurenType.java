package de.rexlmanu.lobby.methods;

import org.bukkit.Effect;
import org.bukkit.Material;

public enum SpurenType {

	Flame("Flame", "§6Flame", Material.BLAZE_POWDER, 1000, Effect.LAVA_POP),
	Emerald("Emerald", "§aEmerald", Material.EMERALD, 1000,Effect.HAPPY_VILLAGER),
	Water("Water", "§1Water", Material.WATER_BUCKET, 1000, Effect.WATERDRIP),
	Music("Music", "§dMusic", Material.NOTE_BLOCK, 1000, Effect.NOTE),
	Snow("Snow", "§fSnow", Material.SNOW_BALL, 1000, Effect.SNOW_SHOVEL),
	Slime("Slime", "§2Slime", Material.SLIME_BALL, 1000, Effect.SLIME),
	Smoke("Smoke", "§7Smoke", Material.FIREWORK_CHARGE, 1000, Effect.PARTICLE_SMOKE);

	private String mysqlname;
	private String name;
	private Material guiitem;
	private int prize;
	private Effect effect;

	private SpurenType(String mysqlname, String name, Material guiitem, int prize, Effect effect) {
		this.mysqlname = mysqlname;
		this.name = name;
		this.guiitem = guiitem;
		this.prize = prize;
		this.effect = effect;
	}

	public String getName() {
		return name;
	}

	public Material getGuiitem() {
		return guiitem;
	}

	public int getPrize() {
		return prize;
	}

	public Effect getEffect() {
		return effect;
	}
	public String getMysqlname() {
		return mysqlname;
	}

}
