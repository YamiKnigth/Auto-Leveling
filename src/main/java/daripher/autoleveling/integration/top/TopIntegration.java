package daripher.autoleveling.integration.top;

import java.util.function.Function;
import mcjty.theoneprobe.api.ITheOneProbe;
import net.neoforged.fml.InterModComms;

public final class TopIntegration {
  private TopIntegration() {}

  public static void register() {
    InterModComms.sendTo(
        "theoneprobe",
        "getTheOneProbe",
        () ->
            (Function<ITheOneProbe, Void>)
                probe -> {
                  probe.registerEntityProvider(LevelProbeEntityProvider.INSTANCE);
                  return null;
                });
  }
}
