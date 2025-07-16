# Metadata Wildcard for fabric-permissions-api

LuckPerms supports wildcard permissions:

```sh
lp user <username> permission set luckperms.* true
```

But lacks wildcard support for metadata:

```sh
lp user <username> meta set some.mod.need.metadata.* 69
```

This fabric-permissions-api addon enables wildcard metadata resolution for any mod using the permissions API.

## Requirement

* Minecraft ⩾ 1.16.5
* Fabric
* fabric-permissions-api ⩾ 0.3
* Any permission mod supports fabric-permissions-api (i.e. LuckPerms)

## Usage

### For players

1. Install the mod in your `mod` directory
2. Start your game/server once to generate config
3. Edit `config/MetadataWildcard4fabric-permissions-api/prefix.txt`

Default config:

```
minecraft.selector
```

When checking a key like `minecraft.selector.foo.bar`:
1. First checks for an exact match
2. If not found, checks parent keys with wildcards in descending order:
	* `minecraft.selector.foo.*`
	* `minecraft.selector.*`
3. Returns unset if no match is found

### For modders

Add dependency in `build.gradle`:

```groovy
// Optional dependency (users install manually)
modImplementation "top.qwertycxz:metadatawildcard4fabric-permissions-api:0.0.1"

// OR bundle with your mod
include(modImplementation("top.qwertycxz:metadatawildcard4fabric-permissions-api:0.0.1"))
```

#### Runtime Configuration

```java
MetadataWildcard.prefixStrings.add("your.mod.metadata.prefix");
```

Equivalent to adding `your.mod.metadata.prefix` in `prefix.txt`.

## Contributor

[@qwertycxz](https://github.com/qwertycxz)

## How could I contribute?

[Issue](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api/issues/new) and [Pull-requests](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api/compare) are both welcomed.

## License

[Apache 2.0](LICENSE) © qwertycxz
