# Architecture

Bisto consists of an Android client and a small PHP/MySQL backend.

## Android client

- Activities and fragments implement registration, profiles, contacts, chat, camera, calls, and the main dashboard.
- Volley performs HTTP requests to the PHP endpoints.
- RecyclerView adapters render contacts and text/image messages.
- Picasso loads profile and conversation images.
- A local SQLite helper stores device-side data.
- Web assets provide the original peer-to-peer calling experiment.

The API base URL is defined as `api_base_url` in `app/src/main/res/values/strings.xml`. The checked-in value targets a web server on the Android emulator's host machine and can be changed for a physical device.

## PHP API

The scripts in `src/PHP_Files` expose the original profile and message operations:

- registration and login;
- profile listing and lookup;
- message listing, creation, editing, and deletion;
- image upload paths for profiles and messages.

`conn.php` reads MySQL settings from `BISTO_DB_*` environment variables. `database/schema.sql` provides a reproducible version of the two-table schema that was originally documented only through screenshots.

## Historical scope

This repository retains the original 2021 API, including its query patterns and plaintext password model. A production rewrite should use password hashing, prepared statements, authenticated tokens, HTTPS, per-user authorization, safer upload validation, and stable message identifiers.
