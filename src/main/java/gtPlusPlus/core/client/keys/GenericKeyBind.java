package gtPlusPlus.core.client.keys;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class GenericKeyBind extends KeyBinding {

	public GenericKeyBind(String aKeyInternalName, int aKeyCode, String aKeyCategory) {
		super(aKeyInternalName, aKeyCode, aKeyCategory);
		ClientRegistry.registerKeyBinding(this);
	}

}
