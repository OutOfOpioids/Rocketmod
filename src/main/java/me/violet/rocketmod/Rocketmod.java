package me.violet.rocketmod;

import me.violet.rocketmod.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rocketmod implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "rocketmod";

	public static final Rocketmod INSTANCE = new Rocketmod();
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static MinecraftClient mc = MinecraftClient.getInstance();

	private static KeyBinding toggle;
	private double delay = 30;

	private boolean wasPressed = false;
	private boolean enabled = false;
	private int tick = 0;

	@Override
	public void onInitialize() {

	}

	@Override
	public void onInitializeClient() {
		toggle = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Toggle",
				InputUtil.Type.KEYSYM,
				-1,
				"Rocketmod"
		));

		ConfigManager.load();
	}

	public void onTick() {
		if(mc.player == null || mc.world == null) return;

		handleInputs();

		if(!enabled) return;
		if(delay == 0) return;
		if(!mc.player.isFallFlying()) return;

		tick++;

		if(tick >= delay) {
			tick = 0;

			int rocketSlot = -1;

			for(int i = 0; i < 9; i++) {
				if(mc.player.getInventory().getStack(i).getItem() instanceof FireworkRocketItem) {
					rocketSlot = i;
					break;
				}
			}

			if(rocketSlot == -1) {
				LOGGER.info("No rockets");
				return;
			}

			final Packet<?> updateSelectedSlotC2SPacket = new UpdateSelectedSlotC2SPacket(rocketSlot);
			mc.player.networkHandler.sendPacket(updateSelectedSlotC2SPacket);
			mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
			mc.player.swingHand(Hand.MAIN_HAND);
		}
	}

	private void handleInputs() {
		if(toggle.isPressed()) {
			if(!wasPressed) this.setEnabled(!enabled);
			wasPressed = true;
		} else {
			wasPressed = false;
		}
	}

	public void setEnabled(boolean b) {
		enabled = b;
		mc.inGameHud.getChatHud().addMessage(Text.of("Rocketmod toggled: " + (enabled ? "on!" : "off!")));
	}

	public void updateDelay(double d) {
		INSTANCE.delay = d;
	}

	public double getDelay() {
		return delay;
	}
}