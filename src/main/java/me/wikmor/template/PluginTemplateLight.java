package me.wikmor.template;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.MathUtil;
import org.mineacademy.fo.RandomUtil;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.remain.CompMaterial;

/**
 * PluginTemplate is a simple template you can use every time you make
 * a new plugin. This will save you time because you no longer have to
 * recreate the same skeleton and features each time.
 *
 * It uses Foundation for fast and efficient development process.
 */
public final class PluginTemplateLight extends SimplePlugin {

	//private static final Random RANDOM = new Random();

	private final List<Material> rewards = Arrays.asList(
			Material.DIAMOND,
			Material.BAKED_POTATO);

	private final Map<String, String> lastPlayerMessage = new HashMap<>();

	/**
	* Automatically perform login ONCE when the plugin starts.
	*/
	@Override
	protected void onPluginStart() {
	}

	/* ------------------------------------------------------------------------------- */
	/* Events */
	/* ------------------------------------------------------------------------------- */

	/**
	 * An example event that checks if the right clicked entity is a cow, and makes an explosion.
	 * You can write your events to your main class without having to register a listener.
	 *
	 * @param event
	 */
	@EventHandler
	public void onRightClick(PlayerInteractEntityEvent event) {
		if (event.getRightClicked().getType() == EntityType.COW)
			event.getRightClicked().getWorld().createExplosion(event.getRightClicked().getLocation(), 5);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();

		// Run this code only if the chance 50%,
		if (RandomUtil.chance(50)) {
			// run this code
		}

		Material randomMaterial = RandomUtil.nextItem(this.rewards);

		/*int randomIndex = random.nextInt(this.rewards.size());
		Material randomMaterial = this.rewards.get(randomIndex);*/

		inventory.addItem(new ItemStack(randomMaterial));

		//Collections.shuffle(this.rewards);
		//inventory.addItem(new ItemStack(this.rewards.get(0)));

		/*ItemStack[] contents = inventory.getContents();

		for (int slot = 0; slot < contents.length; slot++) {
			ItemStack item = contents[slot];
			boolean isDiamond = item != null && item.getType() == Material.DIAMOND;

			if (item == null || isDiamond) {
				int diamondAmount = isDiamond ? item.getAmount() : 0;

				contents[slot] = new ItemStack(Material.DIAMOND, diamondAmount + 1);
				player.sendMessage("You've been given 1x Diamond to your " + slot + " slot.");

				break;
			}
		}

		inventory.setContents(contents);*/
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		String message = event.getMessage();

		String lastMessage = this.lastPlayerMessage.get(playerName);
		double similarity = lastMessage != null ? ChatUtil.getSimilarityPercentage(lastMessage, message) : 0;

		if (similarity > 0.85) {
			player.sendMessage(ChatColor.RED + "Please don't type message " + MathUtil.formatOneDigit(similarity * 100) + "% similar.");

			event.setCancelled(true);

		} else
			this.lastPlayerMessage.put(playerName, message);
	}

	@EventHandler
	public void onRightClickAnything(PlayerInteractEvent event) {
		if (event.getClickedBlock() != null && event.getClickedBlock().getType() == CompMaterial.GRASS_BLOCK.getMaterial())
			handleClickingGrass(event.getClickedBlock(), event.getPlayer());
	}

	void handleClickingGrass(Block block, Player player) {
		block.setType(CompMaterial.DIAMOND_BLOCK.getMaterial());

		player.sendMessage("You've turned this grass block into a diamond!");

		//throw new Error("The clicked block is null!");
	}
}
