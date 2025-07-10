package top.qwertycxz.metadatawildcard;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.lines;
import static java.nio.file.Files.writeString;
import static java.util.stream.Collectors.toSet;
import static net.fabricmc.loader.api.FabricLoader.getInstance;

import java.io.IOException;

import net.fabricmc.api.DedicatedServerModInitializer;

/**
 * A fabric-permissions-api addon, allowing using wildcards in metadata.
 *
 * @see me.lucko.fabric.api.permissions.v0.Options
 */
public class Main implements DedicatedServerModInitializer {
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
			new Event(lines(prefixConfig).parallel().collect(toSet()));
		}
		catch (IOException e) {
			throw new RuntimeException("Failed to load config file for mod Metadata Wildcard for fabric-permissions-api");
		}
	}
}
