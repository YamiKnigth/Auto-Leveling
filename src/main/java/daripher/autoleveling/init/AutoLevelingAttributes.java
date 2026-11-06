package daripher.autoleveling.init;

import daripher.autoleveling.AutoLevelingMod;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AutoLevelingAttributes {
  public static final DeferredRegister<Attribute> REGISTRY =
      DeferredRegister.create(Registries.ATTRIBUTE, AutoLevelingMod.MOD_ID);

  public static final DeferredHolder<Attribute, Attribute> PROJECTILE_DAMAGE_MULTIPLIER =
      rangedAttribute("monster.projectile_damage_bonus", 1, 1, 1000);
  public static final DeferredHolder<Attribute, Attribute> EXPLOSION_DAMAGE_MULTIPLIER =
      rangedAttribute("monster.explosion_damage_bonus", 1, 1, 1000);

  private static DeferredHolder<Attribute, Attribute> rangedAttribute(
      String name, double defaultValue, double minValue, double maxValue) {
    Supplier<Attribute> supplier =
        () ->
            new RangedAttribute("attribute.name.autoleveling." + name, defaultValue, minValue, maxValue)
                .setSyncable(true);
    return REGISTRY.register(name, supplier);
  }
}
