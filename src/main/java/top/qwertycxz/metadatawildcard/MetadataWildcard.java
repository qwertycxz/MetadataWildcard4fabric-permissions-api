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

/**
 * An addon for fabric-permissions-api that enables wildcard support in metadata.
 * <p>
 * <h3>Example</h3>
 *
 * <pre>
 * MetadataWildcard.prefixStrings.add("foo.bar");
 * </pre>
 *
 * When checking a key like {@code foo.bar.baz.qux}:
 * <ol>
 * <li>First checks for an exact match</li>
 * <li>If not found, checks parent keys with wildcards in descending order:
 * <ul>
 * <li>{@code foo.bar.baz.*}</li>
 * <li>{@code foo.bar.*}</li>
 * </ul>
 * </li>
 * <li>Returns unset if no match is found</li>
 * </ol>
 * </p>
 */
public class MetadataWildcard implements DedicatedServerModInitializer {
	/**
	 * A phase after default phase.
	 */
	static final ResourceLocation WILDCARD_PHASE = tryParse("$id");
	/**
	 * <p>
	 * {@code foo.bar.baz.qux} -> {@code qux}
	 * </p>
	 * <p>
	 * {@code foo.bar.baz.*} -> {@code baz.*}
	 * </p>
	 */
	static final String regex = "[^\\.]+\\.?\\*?\\z";
	/**
	 * Only look up metadata with these prefixes.
	 */
	public static final CopyOnWriteArraySet<String> prefixStrings = new CopyOnWriteArraySet<String>();

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

	/**
	 * If this metadata should be empty.
	 *
	 * @param key key of metadata
	 * @return {@code true} for empty, and {@code false} for recursion continues
	 */
	static boolean isEmpty(String key) {
		return key.equals("*") || prefixStrings.parallelStream().noneMatch(prefix -> key.startsWith(prefix));
	}

	public void onInitializeServer() {
		var configDirectory = getInstance().getConfigDir().resolve("MetadataWildcard4fabric-permissions-api");
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
			throw new RuntimeException("Failed to load config file for mod Metadata Wildcard for fabric-permissions-api");
		}
	}
}
