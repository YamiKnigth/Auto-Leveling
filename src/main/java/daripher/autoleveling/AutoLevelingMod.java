package daripher.autoleveling;

import com.mojang.logging.LogUtils;
import daripher.autoleveling.config.Config;
import daripher.autoleveling.init.AutoLevelingAttributes;
import daripher.autoleveling.init.AutoLevelingItems;
import daripher.autoleveling.init.AutoLevelingLootItemConditions;
import daripher.autoleveling.init.AutoLevelingTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import org.slf4j.Logger;

@Mod(AutoLevelingMod.MOD_ID)
public class AutoLevelingMod {
  public static final Logger LOGGER = LogUtils.getLogger();
  public static final String MOD_ID = "autoleveling";

  public AutoLevelingMod(IEventBus modEventBus, ModContainer modContainer) {
    AutoLevelingLootItemConditions.REGISTRY.register(modEventBus);
    AutoLevelingItems.REGISTRY.register(modEventBus);
    AutoLevelingAttributes.REGISTRY.register(modEventBus);
    AutoLevelingTabs.REGISTRY.register(modEventBus);
    modEventBus.addListener(this::attachMobAttributes);
    modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
    modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
  }

  private void attachMobAttributes(EntityAttributeModificationEvent event) {
    event
        .getTypes()
        .forEach(
            entityType -> {
              event.add(entityType, AutoLevelingAttributes.PROJECTILE_DAMAGE_MULTIPLIER);
              event.add(entityType, AutoLevelingAttributes.EXPLOSION_DAMAGE_MULTIPLIER);
            });
  }
}
