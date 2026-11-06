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

Releases are published automatically by GitHub Actions when changes land on `1.21.1` (including merging a pull request), using the current `mod_version` from `gradle.properties`.

Flow:

1. Bump `mod_version` and add a matching section in `changelog.txt`
2. Merge / push to `1.21.1`
3. If a GitHub Release for `v{mod_version}` does not exist yet, CI builds the jar and publishes it

You can still:

- push a tag manually: `git tag -a v1.21.1a -m "..." && git push origin v1.21.1a`
- run the **Release** workflow from the Actions tab (`workflow_dispatch`)

If the release for that version already exists, the workflow skips (no duplicates). Notes come from that version’s block in `changelog.txt`.
