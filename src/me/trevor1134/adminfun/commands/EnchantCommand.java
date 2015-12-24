package me.trevor1134.adminfun.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.PlayerCommandBase;

public class EnchantCommand extends PlayerCommandBase {

	public EnchantCommand(final AdminFun plugin) {
		super(plugin, "enchant", "enchant");
	}

	@Override
	public boolean onPlayerCommand(CommandSender s, String[] args) {
		Player p = (Player) s;
		if (isAuthorized(p)) {
			if (args.length == 0) {
				giveEnchantedItems(p);
				p.sendMessage(ChatColor.GREEN + "[God] " + ChatColor.RED + "Like the enchantments?");
				return true;
			} else {
				p.sendMessage(getUsageMessage());
			}
		} else {
			denyAccess(s);
		}
		return false;
	}

	private ItemStack enchantArmour(final ItemStack item) {
		item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 10);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 10);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 10);
		item.addUnsafeEnchantment(Enchantment.THORNS, 10);
		return item;
	}

	private ItemStack enchantTools(final ItemStack item) {
		item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 10);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);
		return item;
	}

	private void giveEnchantedItems(final Player p) {
		final ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD, 1);
		final ItemStack diamondPickaxe = enchantTools(new ItemStack(Material.DIAMOND_PICKAXE, 1));
		final ItemStack diamondShovel = enchantTools(new ItemStack(Material.DIAMOND_SPADE, 1));
		final ItemStack diamondAxe = enchantTools(new ItemStack(Material.DIAMOND_AXE, 1));
		final ItemStack diamondHelmet = enchantArmour(new ItemStack(Material.DIAMOND_HELMET, 1));
		final ItemStack diamondChestplate = enchantArmour(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
		final ItemStack diamondLeggings = enchantArmour(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
		final ItemStack diamondBoots = enchantArmour(new ItemStack(Material.DIAMOND_BOOTS, 1));
		final ItemStack bow = new ItemStack(Material.BOW, 1);
		final ItemStack arrow = new ItemStack(Material.ARROW, 1);

		diamondSword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
		diamondSword.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 10);
		diamondSword.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 10);
		diamondSword.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 10);
		diamondSword.addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);
		diamondSword.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 10);
		diamondSword.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

		bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 10);
		bow.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 10);
		bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 10);
		bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 10);

		p.getInventory().addItem(diamondSword);
		p.getInventory().addItem(diamondPickaxe);
		p.getInventory().addItem(diamondShovel);
		p.getInventory().addItem(diamondAxe);
		p.getInventory().addItem(diamondHelmet);
		p.getInventory().addItem(diamondChestplate);
		p.getInventory().addItem(diamondLeggings);
		p.getInventory().addItem(diamondBoots);
		p.getInventory().addItem(bow);
		p.getInventory().addItem(arrow);
	}

}
