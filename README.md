# LOC3X1 Camel Route — BPEL Branch

This branch (bpel) hosts work to integrate and align the LOC3X1 Camel route with BPEL-driven orchestration and testing flows.

## Overview
- Base repository: https://github.com/EmilioF-KS/loc3x1-camel-route
- Focus: orchestration, mappings, and test harness compatible with BPEL specifications.
- Goal: make routes testable end-to-end while keeping module structure clear and maintainable.

## Getting Started
1. Ensure Java 17+ and Maven 3.9+ are installed.
2. Build modules individually or via the master test runner:
   - `scripts/test_master.sh --section orchestration`
   - `scripts/test_master.sh --all`
3. See `modules/orchestration/ROUTES.md` for generated route details.

## Branch Notes
- Added testing conveniences for orchestration.
- Placeholder modules may exist to ensure complete master test runs.
- Future work: enrich services/inbound modules with real endpoints.

## Contributing
- Use feature branches and pull requests against `bpel`.
- Keep changes focused and update docs when modifying routes or tests.