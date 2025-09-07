package top.qwertycxz.metadatawildcard;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.lines;
import static java.nio.file.Files.writeString;
import static java.util.Optional.empty;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toSet;
import static me.lucko.fabric.api.permissions.v0.Options.get;
import static net.fabricmc.fabric.api.event.Event.DEFAULT_PHASE;
import static net.fabricmc.loader.api.FabricLoader.getInstance;
import static net.minecraft.resources.ResourceLocation.tryParse;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import me.lucko.fabric.api.permissions.v0.OfflineOptionRequestEvent;
import me.lucko.fabric.api.permissions.v0.OptionRequestEvent;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.resources.ResourceLocation;

/// An addon for fabric-permissions-api that enables wildcard support in metadata.
/// # Example
/// ```java
/// MetadataWildcard.prefixStrings.add("foo.bar");
/// ```
/// When checking a key like `foo.bar.baz.qux`:
/// 1. First checks for an exact match
/// 2. If not found, checks parent keys with wildcards in descending order:
/// 	* `foo.bar.baz.*`
/// 	* `foo.bar.*`
/// 3. Returns unset if no match is found
public class MetadataWildcard implements DedicatedServerModInitializer {
	/// Only look up metadata with these prefixes.
	public static final CopyOnWriteArraySet<String> prefixStrings = new CopyOnWriteArraySet<String>();
	/// A phase after default phase.
	static final ResourceLocation WILDCARD_PHASE = tryParse("$lowercase");
	/// `foo.bar.baz.qux` -> `qux`
	///
	/// `foo.bar.baz.*` -> `baz.*`
	static final String regex = "[^\\.]+\\.?\\*?$";

	static {
		OfflineOptionRequestEvent.EVENT.addPhaseOrdering(DEFAULT_PHASE, WILDCARD_PHASE);
		OfflineOptionRequestEvent.EVENT.register(WILDCARD_PHASE, (uuid, key) -> {
			if (isEmpty(key)) return completedFuture(empty());
			return get(uuid, key.replaceAll(regex, "*"));
		});
		OptionRequestEvent.EVENT.addPhaseOrdering(DEFAULT_PHASE, WILDCARD_PHASE);
		OptionRequestEvent.EVENT.register(WILDCARD_PHASE, (source, key) -> {
			if (isEmpty(key)) return empty();
			return get(source, key.replaceAll(regex, "*"));
		});
	}

	/// If this metadata should be empty.
	///
	/// @param key key of metadata
	/// @return `true` for empty, and `false` for recursion continues
	static boolean isEmpty(String key) {
		return "*".equals(key) || prefixStrings.parallelStream().noneMatch(key::startsWith);
	}

	public void onInitializeServer() {
		var configDirectory = getInstance().getConfigDir().resolve("$capital");
		var prefixConfig = configDirectory.resolve("prefix.txt");
		try {
			try {
				prefixConfig.toRealPath();
			}
			catch (IOException e) {
				createDirectories(configDirectory);
				writeString(prefixConfig, """
				minecraft.selector
				""");
			}
			prefixStrings.addAll(lines(prefixConfig).parallel().collect(toSet()));
		}
		catch (IOException e) {
			throw new RuntimeException("Failed to load config file for mod $name");
		}
	}
}
