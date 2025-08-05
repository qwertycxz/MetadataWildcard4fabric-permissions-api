# Metadata Wildcard for fabric-permissions-api

[![GitHub commit activity](https://img.shields.io/github/commit-activity/t/qwertycxz/MetadataWildcard4fabric-permissions-api)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub commits since latest release](https://img.shields.io/github/commits-since/qwertycxz/MetadataWildcard4fabric-permissions-api/latest)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub contributors](https://img.shields.io/github/contributors/qwertycxz/MetadataWildcard4fabric-permissions-api)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub Created At](https://img.shields.io/github/created-at/qwertycxz/MetadataWildcard4fabric-permissions-api)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub last commit](https://img.shields.io/github/last-commit/qwertycxz/MetadataWildcard4fabric-permissions-api)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub top language](https://img.shields.io/github/languages/top/qwertycxz/MetadataWildcard4fabric-permissions-api)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/qwertycxz/MetadataWildcard4fabric-permissions-api/check.yml)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub branch check runs](https://img.shields.io/github/check-runs/qwertycxz/MetadataWildcard4fabric-permissions-api/master)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/qwertycxz/MetadataWildcard4fabric-permissions-api/total)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub Sponsors](https://img.shields.io/github/sponsors/qwertycxz)](https://ko-fi.com/qwertycxz)
[![GitHub Issues or Pull Requests](https://img.shields.io/github/issues/qwertycxz/MetadataWildcard4fabric-permissions-api)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub Issues or Pull Requests](https://img.shields.io/github/issues-pr/qwertycxz/MetadataWildcard4fabric-permissions-api)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub Discussions](https://img.shields.io/github/discussions/qwertycxz/MetadataWildcard4fabric-permissions-api)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![Modrinth Game Versions](https://img.shields.io/modrinth/game-versions/metadatawildcard4fabric-permissions-api)](https://modrinth.com/mod/metadatawildcard4fabric-permissions-api)
[![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/qwertycxz/MetadataWildcard4fabric-permissions-api)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub repo file or directory count](https://img.shields.io/github/directory-file-count/qwertycxz/MetadataWildcard4fabric-permissions-api)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub repo size](https://img.shields.io/github/repo-size/qwertycxz/MetadataWildcard4fabric-permissions-api)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub followers](https://img.shields.io/github/followers/qwertycxz)](https://github.com/qwertycxz)
[![GitHub forks](https://img.shields.io/github/forks/qwertycxz/MetadataWildcard4fabric-permissions-api)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)
[![GitHub User's stars](https://img.shields.io/github/stars/qwertycxz)](https://github.com/qwertycxz)
[![Modrinth Followers](https://img.shields.io/modrinth/followers/metadatawildcard4fabric-permissions-api)](https://modrinth.com/mod/metadatawildcard4fabric-permissions-api)
[![GitHub Release](https://img.shields.io/github/v/release/qwertycxz/MetadataWildcard4fabric-permissions-api)](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api)

[LuckPerms](https://github.com/lucko/LuckPerms 'GitHub') supports wildcard permissions:

```sh
lp user <username> permission set luckperms.* true
```

But lacks wildcard support for metadata:

```sh
lp user <username> meta set some.mod.need.metadata.* 69
```

This [fabric-permissions-api](https://github.com/lucko/fabric-permissions-api 'GitHub') addon enables wildcard metadata resolution for any mod using the permissions API.

## Synopsis

This mod works like when [`apply-wildcards`](https://luckperms.net/wiki/Configuration#apply-wildcards)`= true` and [`apply-sponge-implicit-wildcards`](https://luckperms.net/wiki/Configuration#apply-sponge-implicit-wildcards)`= false`

## Download

[Maven Central](https://central.sonatype.com/artifact/top.qwertycxz/metadatawildcard4fabric-permissions-api)

[GitHub Packages](https://github.com/qwertycxz/MetadataWildcard4fabric-permissions-api/packages)

[Modrinth](https://modrinth.com/mod/metadatawildcard4fabric-permissions-api/versions)

## Requirement

* Java ⩾ 15
* Minecraft ⩾ 1.16.5
* Fabric
* fabric-permissions-api ⩾ 0.3
* Any permission mod supports [fabric-permissions-api](https://github.com/lucko/fabric-permissions-api 'GitHub') (e.g. [LuckPerms](https://github.com/lucko/LuckPerms 'GitHub'))

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
modImplementation("top.qwertycxz:metadatawildcard4fabric-permissions-api:0.0.1")

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
