package gtPlusPlus.core.client.keys;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.*;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import gtPlusPlus.core.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class AutoWalkKeyBind extends GenericKeyBind {

	public AutoWalkKeyBind() {
		super("key.auto.forward", Keyboard.KEY_GRAVE, "key.categories.movement");
		this.keybind = this;
	}

	public static boolean drawHud = true;
	public static int hudHeight = 20;
	public static long tickCounter = 0;

	private GenericKeyBind keybind;

	private boolean autorunning;

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (Utils.isClient()) {
			acceptInput();
		}
	}

	@SubscribeEvent
	public void drawHUD(RenderGameOverlayEvent.Post event) {

		if (drawHud && this.autorunning && event.type == ElementType.ALL) {
			String message = "Auto-Run";

			Minecraft mc = Minecraft.getMinecraft();
			int w = mc.fontRenderer.getStringWidth("OoO" + message + "oOo");

			int x = (event.resolution.getScaledWidth() - w) / 2;
			int y = hudHeight;

			String displayMessage = message;
			int dots = (int) ((tickCounter / 10) % 2);

			switch (dots) {
			case 0:
				displayMessage = "OoO " + message + " oOo";
				break;
			case 1:
				displayMessage = "oOo " + message + " OoO";
				break;
			default:
				displayMessage = "OoO " + message + " oOo";
				break;
			}

			mc.fontRenderer.drawStringWithShadow(displayMessage, x, y, 0xFFFFFFFF);
		}

	}

	private void acceptInput() {
		Minecraft mc = Minecraft.getMinecraft();
		if(GameSettings.isKeyDown(mc.gameSettings.keyBindForward)) {
			this.autorunning = false;
			tickCounter = 0;
		}
		else if(GameSettings.isKeyDown(this.keybind)) {
			this.autorunning = !this.autorunning;
			if (!this.autorunning) {
				EntityPlayerSP aPlayer = Minecraft.getMinecraft().thePlayer;
				KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), this.autorunning);
				if (aPlayer.isSprinting()) {
					aPlayer.setSprinting(false);
				}
				tickCounter = 0;
			}
			//Logger.INFO("Toggling autowalk state. Now: "+(this.autorunning ? "On" : "Off"));
		}
	}

	@SubscribeEvent
	public void onInput(ClientTickEvent event) {
		if (!Minecraft.getMinecraft().isGamePaused() && Minecraft.getMinecraft().thePlayer != null) {
			EntityPlayerSP aPlayer = Minecraft.getMinecraft().thePlayer;
			if(event.side == Side.CLIENT && event.phase == TickEvent.Phase.END) {
				if (this.autorunning) {
					if (!aPlayer.isSprinting()) {
						aPlayer.setSprinting(true);
					}
					tickCounter++;
					KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), this.autorunning);
				}
			}
		}
	}

}
