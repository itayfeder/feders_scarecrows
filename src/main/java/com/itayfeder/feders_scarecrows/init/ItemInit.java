package com.itayfeder.feders_scarecrows.init;

import com.itayfeder.feders_scarecrows.FedersScarecrowsMod;
import com.itayfeder.feders_scarecrows.common.entities.ScarecrowType;
import com.itayfeder.feders_scarecrows.common.items.CostumeItem;
import com.itayfeder.feders_scarecrows.common.items.ScarecrowItem;
import com.itayfeder.feders_scarecrows.common.items.api.ParentedItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = FedersScarecrowsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemInit {
    public static final Item SCARECROW = new ScarecrowItem((new Item.Properties()).stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), Items.ARMOR_STAND);
    public static final Item WOLF_COSTUME = new CostumeItem(ScarecrowType.WOLF ,(new Item.Properties()).stacksTo(64).tab(CreativeModeTab.TAB_DECORATIONS), SCARECROW);
    public static final Item OCELOT_COSTUME = new CostumeItem(ScarecrowType.OCELOT ,(new Item.Properties()).stacksTo(64).tab(CreativeModeTab.TAB_DECORATIONS), WOLF_COSTUME);
    public static final Item GUARDIAN_COSTUME = new CostumeItem(ScarecrowType.GUARDIAN ,(new Item.Properties()).stacksTo(64).tab(CreativeModeTab.TAB_DECORATIONS), OCELOT_COSTUME);
    //public static final Item WOLF_SCARECROW = new WolfScarecrowItem((new Item.Properties()).stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), SCARECROW);
    //public static final Item OCELOT_SCARECROW = new OcelotScarecrowItem((new Item.Properties()).stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), WOLF_SCARECROW);
    //public static final Item GUARDIAN_SCARECROW = new GuardianScarecrowItem((new Item.Properties()).stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), OCELOT_SCARECROW);

    public static void register(IForgeRegistry<Item> registry, Item item, String id) {
        item.setRegistryName(new ResourceLocation(FedersScarecrowsMod.MOD_ID, id));
        registry.register(item);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Item> registry) {
        register(registry.getRegistry(), SCARECROW, "scarecrow");
        //register(registry.getRegistry(), WOLF_SCARECROW, "wolf_scarecrow");
        //register(registry.getRegistry(), OCELOT_SCARECROW, "ocelot_scarecrow");
        //register(registry.getRegistry(), GUARDIAN_SCARECROW, "guardian_scarecrow");
        register(registry.getRegistry(), WOLF_COSTUME, "wolf_costume");
        register(registry.getRegistry(), OCELOT_COSTUME, "ocelot_costume");
        register(registry.getRegistry(), GUARDIAN_COSTUME, "guardian_costume");

    }
}
