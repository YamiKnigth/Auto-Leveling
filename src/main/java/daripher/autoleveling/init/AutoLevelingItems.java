package daripher.autoleveling.init;

import daripher.autoleveling.AutoLevelingMod;
import daripher.autoleveling.item.BlacklistToolItem;
import daripher.autoleveling.item.WhitelistToolItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AutoLevelingItems {
  public static final DeferredRegister.Items REGISTRY =
      DeferredRegister.createItems(AutoLevelingMod.MOD_ID);

  public static final DeferredHolder<Item, Item> BLACKLIST_TOOL =
      REGISTRY.register("blacklist_tool", BlacklistToolItem::new);
  public static final DeferredHolder<Item, Item> WHITELIST_TOOL =
      REGISTRY.register("whitelist_tool", WhitelistToolItem::new);
}
