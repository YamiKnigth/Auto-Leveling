package daripher.autoleveling.client;

import daripher.autoleveling.config.Config;
import daripher.autoleveling.event.MobsLevelingEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Shared tooltip lines for Jade / The One Probe: level + final values of attributes modified by
 * Auto Leveling, skipping ones already shown by those overlays (health / armor).
 */
public final class LeveledEntityInfo {
  /** Attributes typically already displayed by TOP (and similar overlays). */
  private static final Set<Holder<Attribute>> SKIPPED_ATTRIBUTES =
      Set.of(Attributes.MAX_HEALTH, Attributes.ARMOR);

  private LeveledEntityInfo() {}

  public static List<Component> buildLines(LivingEntity entity) {
    List<Component> lines = new ArrayList<>();
    if (!MobsLevelingEvents.hasLevel(entity) || !MobsLevelingEvents.shouldShowLevel(entity)) {
      return lines;
    }

    int level = MobsLevelingEvents.getLevel(entity) + 1;
    lines.add(Component.translatable("autoleveling.tooltip.level", level));

    for (AttributeInstance instance : entity.getAttributes().getSyncableAttributes()) {
      AttributeModifier modifier = instance.getModifier(Config.BONUS_MODIFIER_ID);
      if (modifier == null) {
        continue;
      }
      Holder<Attribute> attribute = instance.getAttribute();
      if (SKIPPED_ATTRIBUTES.contains(attribute)) {
        continue;
      }
      lines.add(formatAttributeLine(attribute, instance.getValue()));
    }
    return lines;
  }

  private static Component formatAttributeLine(Holder<Attribute> attribute, double value) {
    MutableComponent name = Component.translatable(attribute.value().getDescriptionId());
    return Component.translatable("autoleveling.tooltip.attribute", name, formatValue(value));
  }

  private static String formatValue(double value) {
    if (Math.abs(value - Math.rint(value)) < 1.0E-4) {
      return Integer.toString((int) Math.rint(value));
    }
    return String.format(java.util.Locale.ROOT, "%.2f", value);
  }
}
