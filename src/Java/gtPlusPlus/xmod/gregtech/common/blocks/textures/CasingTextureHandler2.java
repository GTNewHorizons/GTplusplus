package gtPlusPlus.xmod.gregtech.common.blocks.textures;

import net.minecraft.util.IIcon;

import gregtech.api.enums.Textures;

public class CasingTextureHandler2 {

	public static  IIcon getIcon(final int aSide, final int aMeta) { //Texture ID's. case 0 == ID[57]
		if ((aMeta >= 0) && (aMeta < 16)) {
			switch (aMeta) {
				//Centrifuge
				case 0:
					return TexturesGtBlock.Casing_Material_RedSteel.getIcon();
					//Coke Oven Frame
				case 1:
					return TexturesGtBlock.Casing_Material_HastelloyX.getIcon();
					//Coke Oven Casing Tier 1
				case 2:
					return TexturesGtBlock.Casing_Material_HastelloyN.getIcon();
					//Coke Oven Casing Tier 2
				case 3:
					return TexturesGtBlock.Casing_Material_Fluid_IncoloyDS.getIcon();
					//Material Press Casings
				case 4:
					return TexturesGtBlock.Casing_Material_Grisium.getIcon();				
					//Sifter Structural
				case 5:
					return TexturesGtBlock.Casing_Machine_Metal_Panel_A.getIcon();
					//Sifter Sieve
				case 6:
					return TexturesGtBlock.Casing_Machine_Metal_Grate_A.getIcon();

					//Vanadium Radox Battery
				case 7:
					return TexturesGtBlock.Casing_Redox_1.getIcon();
					//Power Sub-Station Casing
				case 8:
					return TexturesGtBlock.Casing_Machine_Metal_Sheet_A.getIcon();
					//Cyclotron Coil
				case 9:
					return TexturesGtBlock.Overlay_Machine_Cyber_A.getIcon();
					//Cyclotron External Casing
				case 10:
					return Textures.BlockIcons.MACHINE_CASING_RADIATIONPROOF.getIcon();
					//Multitank Exterior Casing
				case 11:
					return TexturesGtBlock.Casing_Material_Tantalloy61.getIcon();
					//Reactor Casing I
				case 12:
					return TexturesGtBlock.Casing_Machine_Simple_Top.getIcon();
					//Reactor Casing II
				case 13:
					if (aSide <2) {
						return TexturesGtBlock.TEXTURE_TECH_A.getIcon();					
					}
					else {
						return TexturesGtBlock.TEXTURE_TECH_B.getIcon();					
					}
				case 14:
					return TexturesGtBlock.Casing_Material_RedSteel.getIcon();
				case 15:
					return TexturesGtBlock.Casing_Machine_Acacia_Log.getIcon();	
					/*if (aSide <2) {
						if (aSide == 1) {
							return TexturesGtBlock.Casing_Machine_Podzol.getIcon();					
						}
						return TexturesGtBlock.Casing_Machine_Acacia_Log.getIcon();							
					}
					else {
						return TexturesGtBlock.Casing_Machine_Farm_Manager.getIcon();					
					}*/
				default:
					return TexturesGtBlock.Overlay_UU_Matter.getIcon();

			}
		}
		return TexturesGtBlock._PlaceHolder.getIcon();
	}

}