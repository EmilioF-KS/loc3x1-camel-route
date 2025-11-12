# Generated Java Processors for IBM `map:custom`

This document records rationales for processors derived from IBM Integration `map:custom` blocks.

Principles:
- Side-effect free transformations (e.g., string trimming, passthrough assignments).
- JDK-only classes to avoid external build dependencies within this repo.
- Client-agnostic; no secrets or environment-specific paths.

Notes:
- Discovery parses `map:custom` entries to capture input/output properties and the embedded `map:javaCode`.
- Generation emits classes with `process(String input)` implementing the inferred operation.
- For full Camel integration, downstream projects can wrap these in `org.apache.camel.Processor`.

Rationale entries are generated per block by the synthesis tooling and can be appended here.

CLI usage:
- Generate processors and append rationales:
  - `python agent/scripts/generate_processors.py --map-file "CHUBB/00-Emilio-Logic/a7/RandLocationToCimLocationMap 1.xml" --output-dir agent/output/java_processors --rationales docs/processors.md`
- Optional compile check (if `javac` is available):
  - Use `agent/src/java_compile.py` helpers or run `javac -d classes agent/output/java_processors/*.java` manually.