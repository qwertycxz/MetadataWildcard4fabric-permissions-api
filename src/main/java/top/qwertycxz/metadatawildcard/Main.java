package top.qwertycxz.metadatawildcard;

import static java.util.Optional.empty;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static me.lucko.fabric.api.permissions.v0.Options.get;
import static net.fabricmc.fabric.api.event.Event.DEFAULT_PHASE;
import static net.minecraft.resources.ResourceLocation.tryParse;
import me.lucko.fabric.api.permissions.v0.OfflineOptionRequestEvent;
import me.lucko.fabric.api.permissions.v0.OptionRequestEvent;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.resources.ResourceLocation;

public class Main implements DedicatedServerModInitializer {
	static final ResourceLocation WILDCARD_PHASE = tryParse("metadatawildcard4fabric-permissions-api");

	public void onInitializeServer() {
		OfflineOptionRequestEvent.EVENT.addPhaseOrdering(DEFAULT_PHASE, WILDCARD_PHASE);
		OfflineOptionRequestEvent.EVENT.register(WILDCARD_PHASE, (uuid, key) -> {
			if (key.equals("*")) return completedFuture(empty());
			return get(uuid, key.replaceAll("[^\\.]+\\.?\\*?\\z", "*"));
		});
		OptionRequestEvent.EVENT.addPhaseOrdering(DEFAULT_PHASE, WILDCARD_PHASE);
		OptionRequestEvent.EVENT.register(WILDCARD_PHASE, (source, key) -> {
			if (key.equals("*")) return empty();
			return get(source, key.replaceAll("[^\\.]+\\.?\\*?\\z", "*"));
		});
	}
}
