package daripher.autoleveling.network.message;

import daripher.autoleveling.AutoLevelingMod;
import daripher.autoleveling.event.MobsLevelingEvents;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncLevelingData(int entityId, int level) implements CustomPacketPayload {
  public static final Type<SyncLevelingData> TYPE =
      new Type<>(ResourceLocation.fromNamespaceAndPath(AutoLevelingMod.MOD_ID, "sync_level"));

  public static final StreamCodec<ByteBuf, SyncLevelingData> STREAM_CODEC =
      StreamCodec.composite(
          ByteBufCodecs.VAR_INT,
          SyncLevelingData::entityId,
          ByteBufCodecs.VAR_INT,
          SyncLevelingData::level,
          SyncLevelingData::new);

  public SyncLevelingData(LivingEntity entity) {
    this(entity.getId(), MobsLevelingEvents.getLevel(entity));
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  public static void handle(SyncLevelingData message, IPayloadContext context) {
    context.enqueueWork(
        () -> {
          Minecraft client = Minecraft.getInstance();
          ClientLevel level = client.level;
          if (level == null) return;
          Entity entity = level.getEntity(message.entityId());
          if (entity instanceof LivingEntity living) {
            MobsLevelingEvents.setLevel(living, message.level());
          }
        });
  }
}
