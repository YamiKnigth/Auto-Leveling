# Auto Leveling (NeoForge 1.21.1 port)

This is an unofficial **NeoForge 1.21.1 port/fork** of **Auto Leveling**, originally created by **[Daripher](https://github.com/Daripher)**.

- Original project (Forge): https://github.com/Daripher/Auto-Leveling
- Original CurseForge page: https://www.curseforge.com/minecraft/mc-mods/auto-leveling

The further you move from the spawn, the stronger enemies become.

## License

Licensed under **GNU GPL v3** (same as the original mod). See [`LICENSE.txt`](LICENSE.txt).

All credit for the original design and codebase goes to **Daripher**. This fork only adapts the mod to NeoForge 1.21.1 while aiming to preserve the 1.20.1 Forge behavior.

## Building

```bash
./gradlew build
```

The jar is written to `build/libs/AutoLeveling-1.21.1-neoforge-<mod_version>.jar`.

## Releasing (GitHub Releases)

Releases are published by GitHub Actions when you push a version tag that matches `mod_version` in `gradle.properties`.

1. Update `mod_version` and the matching section in `changelog.txt`
2. Commit and push to `1.21.1`
3. Create and push the tag:

```bash
git tag -a v1.21.1 -m "Release v1.21.1"
git push origin v1.21.1
```

Or run the **Release** workflow manually from the Actions tab (`workflow_dispatch`) with the same version.

The workflow builds the jar and creates a GitHub Release whose notes come from that version’s block in `changelog.txt`.
