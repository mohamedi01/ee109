import csv
from pathlib import Path
import re
import string

def get_project_root() -> Path:
    """
    Returns the project root directory.
    Assumes this file is at src/testutility/text_processing_utils.py
    """
    # Path(__file__) is src/testutility/text_processing_utils.py
    # .parent is src/testutility/
    # .parent.parent is src/
    # .parent.parent.parent is Final-Project/ (the project root)
    return Path(__file__).resolve().parent.parent.parent

def _load_homophones_from_csv_internal(csv_path: Path) -> dict[str, str]:
    """
    Internal helper to load homophone groups from a CSV file.
    The first word in each line is the canonical form.
    """
    homophone_map = {}
    
    with open(csv_path, 'r', encoding='utf-8') as f:
        reader = csv.reader(f)
        for row in reader:
            if not row or row[0] == "#":  # Skip empty lines or comment lines
                continue
            # Normalize all words to lowercase and strip whitespace
            words = [word.strip().lower() for word in row if word.strip()]
            
            if len(words) >= 1:
                canonical_form = words[0]
                # Map all other words in the group to this canonical form.
                for variant in words[1:]:
                    if variant != canonical_form: # Avoid mapping a word to itself
                        homophone_map[variant] = canonical_form
            
    return homophone_map

def get_comprehensive_canonical_map() -> dict[str, str]:
    """
    Creates and returns a comprehensive canonical map including:
    1. Homophones loaded from 'data/normalization/homophones.csv'.
    2. Digit-to-word mappings (e.g., "1" -> "one").
    """
    project_root = get_project_root()
    homophones_csv_path = project_root / "data" / "normalization" / "homophones.csv"
    
    # Load homophones from CSV
    canonical_map = _load_homophones_from_csv_internal(homophones_csv_path)
    
    return canonical_map

# Load the comprehensive map once when the module is imported
DEFAULT_COMPREHENSIVE_CANONICAL_MAP = get_comprehensive_canonical_map()

def normalize_text_for_wer(text: str) -> str:
    """
    Normalizes text for WER calculation:
    1. Converts to lowercase.
    2. Removes all punctuation.
    3. Replaces common contractions.
    4. Replaces numerals with words (e.g., "1" -> "one").
    5. Removes/Replaces other specific patterns/characters for consistency.
    """
    text = text.lower()

    # Remove all punctuation
    # string.punctuation is '"!#$%&'()*+,-./:;<=>?@[\]^_`{|}~'
    text = ''.join(char for char in text if char not in string.punctuation)

    # Replace common contractions (add more as needed)
    text = text.replace("\'s", " is") # e.g., "it's" -> "it is"

    # Normalize fancy apostrophes (e.g., ’ to ')
    text = text.replace('’', "'")
    
    # Normalize whitespace
    text = re.sub(r'\s+', ' ', text).strip()
    return text

def apply_canonical_map_to_text(text: str, canonical_map: dict) -> str:
    """
    Applies a canonical mapping (e.g., for homophones and numbers) to a text string.
    Assumes the input text has already undergone basic normalization (e.g., lowercasing).
    """
    if not canonical_map: 
        return text

    words = text.split()
    normalized_words = [canonical_map.get(word, word) for word in words]
    return " ".join(normalized_words)