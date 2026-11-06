#!/usr/bin/env python3
"""Extract one version section from changelog.txt into Markdown for GitHub Releases."""

from __future__ import annotations

import re
import sys
from pathlib import Path


def extract(changelog: str, version: str) -> str:
    lines = changelog.splitlines()
    header = version
    start = None
    for i, line in enumerate(lines):
        if line.strip() == header:
            start = i
            break
    if start is None:
        return f"## Auto Leveling {version}\n\nNo changelog entry found for `{version}`.\n"

    body: list[str] = []
    for line in lines[start + 1 :]:
        if not line.startswith(" ") and line.strip() and not line.startswith("\t"):
            # Next version header (e.g. "1.14")
            if re.fullmatch(r"[0-9]+(\.[0-9A-Za-z]+)+", line.strip()):
                break
        body.append(line)

    while body and not body[0].strip():
        body.pop(0)
    while body and not body[-1].strip():
        body.pop()

    md_items = []
    for line in body:
        text = line.strip()
        if not text:
            continue
        md_items.append(f"- {text}")

    parts = [f"## Auto Leveling {version}", ""]
    if md_items:
        parts.extend(md_items)
    else:
        parts.append(f"Release `{version}`.")
    parts.append("")
    return "\n".join(parts)


def main() -> int:
    if len(sys.argv) != 2:
        print(f"Usage: {sys.argv[0]} <version>", file=sys.stderr)
        return 2
    version = sys.argv[1]
    path = Path(__file__).resolve().parents[1] / "changelog.txt"
    print(extract(path.read_text(encoding="utf-8"), version), end="")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
