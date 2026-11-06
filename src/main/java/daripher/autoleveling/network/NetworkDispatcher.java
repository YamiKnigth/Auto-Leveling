package daripher.autoleveling.network;

import daripher.autoleveling.AutoLevelingMod;
import daripher.autoleveling.network.message.SyncLevelingData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = AutoLevelingMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkDispatcher {
  @SubscribeEvent
  public static void register(RegisterPayloadHandlersEvent event) {
    PayloadRegistrar registrar = event.registrar("1");
    registrar.playToClient(
        SyncLevelingData.TYPE, SyncLevelingData.STREAM_CODEC, SyncLevelingData::handle);
  }
}
