#!/usr/bin/env python3
"""Validate the Android/PHP project structure and configuration hygiene."""

from pathlib import Path
import re
import subprocess
import xml.etree.ElementTree as ET


ROOT = Path(__file__).resolve().parents[1]
ANDROID = ROOT / "mobile/app/src/main"
PHP = ROOT / "api"


def main() -> None:
    required = [
        ROOT / "database/schema.sql",
        ROOT / ".env.example",
        PHP / "conn.php",
        ANDROID / "AndroidManifest.xml",
    ]
    missing = [str(path.relative_to(ROOT)) for path in required if not path.is_file()]
    if missing:
        raise SystemExit(f"Missing required files: {', '.join(missing)}")

    for path in ANDROID.glob("res/**/*.xml"):
        ET.parse(path)
    ET.parse(ANDROID / "AndroidManifest.xml")

    java_sources = "\n".join(
        path.read_text(encoding="utf-8", errors="replace")
        for path in ANDROID.glob("java/**/*.java")
    )
    private_ips = re.findall(r"https?://(?:192\.168|172\.(?:1[6-9]|2\d|3[01])|10\.)[^\"']+", java_sources)
    if private_ips:
        raise SystemExit(f"Hardcoded private API addresses found in Java: {private_ips}")

    connection = (PHP / "conn.php").read_text(encoding="utf-8")
    if "BISTO_DB_PASSWORD" not in connection:
        raise SystemExit("PHP database configuration must use environment variables")

    tracked = subprocess.check_output(["git", "ls-files"], cwd=ROOT, text=True).splitlines()
    forbidden = [path for path in tracked if "/.idea/" in f"/{path}" or path.endswith("google-services.json")]
    if forbidden:
        raise SystemExit("Local configuration is tracked: " + ", ".join(forbidden))

    print("Validated Android resources, PHP configuration, and repository hygiene.")


if __name__ == "__main__":
    main()
