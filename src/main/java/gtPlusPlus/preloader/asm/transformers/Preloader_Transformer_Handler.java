package gtPlusPlus.preloader.asm.transformers;

import static gtPlusPlus.preloader.asm.ClassesToTransform.COFH_ORE_DICTIONARY_ARBITER;
import static gtPlusPlus.preloader.asm.ClassesToTransform.FORGE_CHUNK_MANAGER;
import static gtPlusPlus.preloader.asm.ClassesToTransform.FORGE_ORE_DICTIONARY;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_BASE_TILE_ENTITY;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_CHARGEPAD;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_ELECTRIC;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_GENERATOR;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_HEAT_GENERATOR;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_KINETIC_GENERATOR;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_LUMINATOR;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_MACHINE1;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_MACHINE2;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_MACHINE3;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_PERSONAL;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_REACTOR_ACCESS_HATCH;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_REACTOR_CHAMBER;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_REACTOR_FLUID_PORT;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_REACTOR_REDSTONE_PORT;
import static gtPlusPlus.preloader.asm.ClassesToTransform.IC2_BLOCK_REACTOR_VESSEL;
import static gtPlusPlus.preloader.asm.ClassesToTransform.LWJGL_KEYBOARD;
import static gtPlusPlus.preloader.asm.ClassesToTransform.MINECRAFT_GAMESETTINGS;
import static gtPlusPlus.preloader.asm.ClassesToTransform.MINECRAFT_GAMESETTINGS_OBF;
import static gtPlusPlus.preloader.asm.ClassesToTransform.THAUMCRAFT_ITEM_WISP_ESSENCE;

import java.io.File;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import gtPlusPlus.api.objects.data.AutoMap;
import gtPlusPlus.core.util.reflect.ReflectionUtils;
import gtPlusPlus.preloader.CORE_Preloader;
import gtPlusPlus.preloader.Preloader_Logger;
import gtPlusPlus.preloader.asm.AsmConfig;
import gtPlusPlus.preloader.asm.transformers.Preloader_ClassTransformer.OreDictionaryVisitor;

public class Preloader_Transformer_Handler implements IClassTransformer {

    public static final AsmConfig mConfig;
    public static final AutoMap<String> IC2_WRENCH_PATCH_CLASS_NAMES = new AutoMap<String>();

    static {
        mConfig = new AsmConfig(new File("config/GTplusplus/asm.cfg"));
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_BASE_TILE_ENTITY);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_MACHINE1);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_MACHINE2);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_MACHINE3);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_KINETIC_GENERATOR);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_HEAT_GENERATOR);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_GENERATOR);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_REACTOR_ACCESS_HATCH);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_REACTOR_CHAMBER);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_REACTOR_FLUID_PORT);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_REACTOR_REDSTONE_PORT);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_REACTOR_VESSEL);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_PERSONAL);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_CHARGEPAD);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_ELECTRIC);
        IC2_WRENCH_PATCH_CLASS_NAMES.add(IC2_BLOCK_LUMINATOR);
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        // Fix LWJGL index array out of bounds on keybinding IDs
        if ((transformedName.equals(LWJGL_KEYBOARD) || transformedName.equals(MINECRAFT_GAMESETTINGS_OBF)
                || transformedName.equals(MINECRAFT_GAMESETTINGS)) && AsmConfig.enabledLwjglKeybindingFix
        // Do not transform if using lwjgl3
                && !ReflectionUtils.doesClassExist("org.lwjgl.system.Platform")) {
            boolean isClientSettingsClass = !transformedName.equals("org.lwjgl.input.Keyboard");
            Preloader_Logger.INFO("LWJGL Keybinding index out of bounds fix", "Transforming " + transformedName);
            return new ClassTransformer_LWJGL_Keyboard(basicClass, isClientSettingsClass).getWriter().toByteArray();
        }

        // Enable mapping of Tickets and loaded chunks. - Forge
        if (transformedName.equals(FORGE_CHUNK_MANAGER) && AsmConfig.enableChunkDebugging) {
            Preloader_Logger.INFO("Chunkloading Patch", "Transforming " + transformedName);
            return new ClassTransformer_Forge_ChunkLoading(basicClass, false).getWriter().toByteArray();
        }

        // Fix the OreDictionary - Forge
        if (transformedName.equals(FORGE_ORE_DICTIONARY) && AsmConfig.enableOreDictPatch) {
            Preloader_Logger.INFO("OreDictTransformer", "Transforming " + transformedName);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            new ClassReader(basicClass).accept(new OreDictionaryVisitor(classWriter), 0);
            return classWriter.toByteArray();
        }

        // Fix the OreDictionary COFH
        if (transformedName.equals(COFH_ORE_DICTIONARY_ARBITER)
                && (AsmConfig.enableCofhPatch || CORE_Preloader.DEV_ENVIRONMENT)) {
            Preloader_Logger.INFO("COFH", "Transforming " + transformedName);
            return new ClassTransformer_COFH_OreDictionaryArbiter(basicClass).getWriter().toByteArray();
        }

        // Fix IC2 Wrench Harvesting
        for (String y : IC2_WRENCH_PATCH_CLASS_NAMES) {
            if (transformedName.equals(y)) {
                Preloader_Logger.INFO("IC2 getHarvestTool Patch", "Transforming " + transformedName);
                return new ClassTransformer_IC2_GetHarvestTool(
                        basicClass,
                        !CORE_Preloader.DEV_ENVIRONMENT,
                        transformedName).getWriter().toByteArray();
            }
        }

        // Fix Thaumcraft stuff
        // Patching ItemWispEssence to allow invalid item handling
        if (transformedName.equals(THAUMCRAFT_ITEM_WISP_ESSENCE) && AsmConfig.enableTcAspectSafety) {
            Preloader_Logger.INFO("Thaumcraft WispEssence_Patch", "Transforming " + transformedName);
            return new ClassTransformer_TC_ItemWispEssence(basicClass, !CORE_Preloader.DEV_ENVIRONMENT).getWriter()
                    .toByteArray();
        }

        return basicClass;
    }
}
