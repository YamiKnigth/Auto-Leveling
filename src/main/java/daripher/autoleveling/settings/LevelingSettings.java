package daripher.autoleveling.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import daripher.autoleveling.AutoLevelingMod;
import daripher.autoleveling.config.Config;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

public interface LevelingSettings {

  int startingLevel();

  int maxLevel();

  float levelsPerDistance();

  float levelsPerDeepness();

  int randomLevelBonus();

  float levelsPerDay();

  float levelPowerPerDistance();

  float levelPowerPerDeepness();

  Map<Holder<Attribute>, AttributeModifier> attributeModifiers();

  static Map<Holder<Attribute>, AttributeModifier> readAttributeModifiers(JsonObject jsonObject) {
    if (!jsonObject.has("attribute_modifiers")) {
      return Map.of();
    }
    Map<Holder<Attribute>, AttributeModifier> modifiers = new HashMap<>();
    JsonArray jsonPairs = jsonObject.get("attribute_modifiers").getAsJsonArray();
    jsonPairs.forEach(
        jsonElement -> {
          JsonObject elementJson = jsonElement.getAsJsonObject();
          Holder<Attribute> attribute = readAttribute(elementJson);
          AttributeModifier modifier = readAttributeModifier(elementJson);
          if (attribute != null) {
            modifiers.put(attribute, modifier);
          }
        });
    return modifiers;
  }

  static @Nullable Holder<Attribute> readAttribute(JsonObject jsonObject) {
    ResourceLocation attributeId =
        ResourceLocation.parse(jsonObject.get("attribute").getAsString());
    Optional<Holder.Reference<Attribute>> attribute =
        BuiltInRegistries.ATTRIBUTE.getHolder(attributeId);
    if (attribute.isEmpty()) {
      AutoLevelingMod.LOGGER.error("Attribute '{}' can not be found!", attributeId);
      return null;
    }
    return attribute.get();
  }

  static AttributeModifier readAttributeModifier(JsonObject jsonObject) {
    double amount = jsonObject.get("amount").getAsDouble();
    AttributeModifier.Operation operation =
        AttributeModifier.Operation.BY_ID.apply(jsonObject.get("operation").getAsInt());
    return new AttributeModifier(Config.BONUS_MODIFIER_ID, amount, operation);
  }

  static @Nullable BlockPos readSpawnPosOverride(JsonObject jsonObject) {
    if (!jsonObject.has("spawn_pos_override")) return null;
    JsonObject posJson = jsonObject.get("spawn_pos_override").getAsJsonObject();
    int x = posJson.get("x").getAsInt();
    int y = posJson.get("y").getAsInt();
    int z = posJson.get("z").getAsInt();
    return new BlockPos(x, y, z);
  }

  static float readOptionalFloat(
      JsonObject jsonObject, String name, ModConfigSpec.ConfigValue<Double> alternative) {
    if (!jsonObject.has(name)) {
      return alternative.get().floatValue();
    }
    return jsonObject.get(name).getAsFloat();
  }

  static float readLevelsPerDay(JsonObject jsonObject) {
    return readOptionalFloat(jsonObject, "levels_per_day", Config.COMMON.levelsPerDay);
  }

  static float readLevelPowerPerDistance(JsonObject jsonObject) {
    return readOptionalFloat(
        jsonObject, "level_power_per_distance", Config.COMMON.levelPowerPerDistance);
  }

  static float readLevelPowerPerDeepness(JsonObject jsonObject) {
    return readOptionalFloat(
        jsonObject, "level_power_per_deepness", Config.COMMON.levelPowerPerDeepness);
  }
}
