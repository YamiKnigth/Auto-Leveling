package daripher.autoleveling.integration.top;

import daripher.autoleveling.client.LeveledEntityInfo;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class LevelProbeEntityProvider implements IProbeInfoEntityProvider {
  public static final LevelProbeEntityProvider INSTANCE = new LevelProbeEntityProvider();

  private LevelProbeEntityProvider() {}

  @Override
  public String getID() {
    return "autoleveling:level";
  }

  @Override
  public void addProbeEntityInfo(
      ProbeMode mode,
      IProbeInfo probeInfo,
      Player player,
      Level level,
      Entity entity,
      IProbeHitEntityData data) {
    if (!(entity instanceof LivingEntity living)) {
      return;
    }
    for (Component line : LeveledEntityInfo.buildLines(living)) {
      probeInfo.text(line);
    }
  }
}
