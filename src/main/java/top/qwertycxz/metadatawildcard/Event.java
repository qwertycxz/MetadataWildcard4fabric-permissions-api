package top.qwertycxz.metadatawildcard;

import static java.util.Optional.empty;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static me.lucko.fabric.api.permissions.v0.Options.get;
import static net.fabricmc.fabric.api.event.Event.DEFAULT_PHASE;
import static net.minecraft.resources.ResourceLocation.tryParse;

import java.util.Set;

import me.lucko.fabric.api.permissions.v0.OfflineOptionRequestEvent;
import me.lucko.fabric.api.permissions.v0.OptionRequestEvent;
import net.minecraft.resources.ResourceLocation;

class Event {
	static final ResourceLocation WILDCARD_PHASE = tryParse("metadatawildcard4fabric-permissions-api");
	final Set<String> prefixStrings;

	Event(Set<String> prefixStrings) {
		this.prefixStrings = prefixStrings;
		OfflineOptionRequestEvent.EVENT.addPhaseOrdering(DEFAULT_PHASE, WILDCARD_PHASE);
		OfflineOptionRequestEvent.EVENT.register(WILDCARD_PHASE, (uuid, key) -> {
			if (isEmpty(key)) return completedFuture(empty());
			return get(uuid, key.replaceAll("[^\\.]+\\.?\\*?\\z", "*"));
		});
		OptionRequestEvent.EVENT.addPhaseOrdering(DEFAULT_PHASE, WILDCARD_PHASE);
		OptionRequestEvent.EVENT.register(WILDCARD_PHASE, (source, key) -> {
			if (isEmpty(key)) return empty();
			return get(source, key.replaceAll("[^\\.]+\\.?\\*?\\z", "*"));
		});
	}

	boolean isEmpty(String key) {
		return key.equals("*") || prefixStrings.parallelStream().noneMatch(prefix -> key.startsWith(prefix));
	}
}
