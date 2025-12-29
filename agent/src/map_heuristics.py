from difflib import SequenceMatcher


def normalize(name: str) -> str:
    return ''.join(ch for ch in name.lower() if ch.isalnum())


def suggest_matches(src_fields, dst_fields, threshold: float = 0.92):
    # src_fields/dst_fields are lists of names
    suggestions = []
    norm_src = [(s, normalize(s)) for s in src_fields]
    norm_dst = [(d, normalize(d)) for d in dst_fields]
    for s_orig, s_norm in norm_src:
        for d_orig, d_norm in norm_dst:
            if s_norm == d_norm:
                continue  # identical handled by deterministic synthesis
            ratio = SequenceMatcher(None, s_norm, d_norm).ratio()
            if ratio >= threshold:
                suggestions.append((s_orig, d_orig, ratio))
    return suggestions