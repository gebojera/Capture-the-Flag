package sylaires.ctf.display;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import sylaires.ctf.cosmetic.CosmeticRegistry;
import sylaires.ctf.cosmetic.CosmeticType;
import sylaires.ctf.cosmetic.ICosmetic;
import sylaires.ctf.util.ItemBuilder;
import sylaires.ctf.util.MenuBuilder;

/*
 * Copyright 2022, Sylaires. All rights reserved.
 */

public class KillEffectGUI implements InventoryHolder {
	
	Inventory gui;
	private ArrayList<GUIButton> buttons = new ArrayList<GUIButton>();
	
	private KillEffectGUI(Player p) {
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, " ", (byte) 2).toItem();
		gui = new MenuBuilder(ChatColor.GOLD + "Kill Effects", 27, this).fillWith(glass).construct();
		
		GUIButtonBack button1 = new GUIButtonBack(this); button1.setItem(22, gui); buttons.add(button1);
		
		GUIButtonClearCosmetic button2 = new GUIButtonClearCosmetic(CosmeticType.KILL, p); button2.setItem(4, gui); buttons.add(button2);
		
		ArrayList<Class<? extends ICosmetic>> kills = new ArrayList<Class<? extends ICosmetic>>();
		
		for(Class<? extends ICosmetic> kill : CosmeticRegistry.getClasses(CosmeticType.KILL)) {
			kills.add(kill);
		}
		
		int nextSlot = 10;
		
		for(Class<? extends ICosmetic> kill : kills) {
			GUIButtonCosmetic ab = new GUIButtonCosmetic(p, kill, CosmeticType.KILL);
			buttons.add(ab);
			if(nextSlot == 17) nextSlot = 19;
			if(nextSlot == 26) nextSlot = 28;
			if(nextSlot == 35) nextSlot = 37;
			ab.setItem(nextSlot, gui);
			nextSlot++;
		}
		if(nextSlot < 43) {
			if(nextSlot == 17) nextSlot = 19;
			if(nextSlot == 26) nextSlot = 28;
			if(nextSlot == 35) nextSlot = 37;
			GUIButtonUpcoming button = new GUIButtonUpcoming(this);
			buttons.add(button);
			button.setItem(nextSlot, gui);
		}
		
		p.openInventory(gui);
	}
	
	public GUIButton getButtonBySlot(int slot) {
		for(GUIButton button : buttons) {
			if(button.getSlot() == slot) {
				return button;
			}
		}
		return null;
	}
	
	public static KillEffectGUI getNew(Player p) {
		return new KillEffectGUI(p);
	}
	
	@Override
	public Inventory getInventory() {
		return gui;
	}

}
