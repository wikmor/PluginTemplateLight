package me.wikmor.template;

import org.bukkit.entity.Player;

public class PlayerUtil {

	public static String SPECIAL_TAG = "Special Tag";

	public static void sendMessage(Player player, String message) {
		player.sendMessage("[Boss] " + message);
	}
}
