from pathlib import Path
import json # For pretty printing the output
import argparse # Import argparse
from audiolib.pipeline import process_audio_to_nlp

def main():
    # --- Argument Parsing ---
    parser = argparse.ArgumentParser(description="Run the DSP-ASR-NLP pipeline on an audio file.")
    parser.add_argument(
        "audio_file", 
        type=Path, 
        help="Path to the audio file to process (e.g., data/short_sentences/harvard_f.wav)"
    )
    parser.add_argument(
        "--device", 
        type=str, 
        default="cpu", 
        choices=["cpu", "cuda"], 
        help="Device to run processing on (cpu or cuda). Default: cpu"
    )
    args = parser.parse_args()

    audio_file_to_process = args.audio_file
    processing_device = args.device

    # --- Check if the audio file exists ---
    if not audio_file_to_process.is_file():
        print(f"Error: Audio file not found at {audio_file_to_process}")
        script_dir = Path(__file__).resolve().parent
        potential_project_root_path = script_dir / audio_file_to_process
        if potential_project_root_path.is_file():
            audio_file_to_process = potential_project_root_path
            print(f"Info: Resolved audio file to {audio_file_to_process} (relative to script location).")
        else:
            print(f"Error: Audio file not found at {args.audio_file} (and not found relative to script location either).")
            return

    print(f"Processing audio file: {audio_file_to_process} on device: {processing_device}...")

    # --- Run the pipeline ---
    try:
        pipeline_results = process_audio_to_nlp(
            audio_path=audio_file_to_process,
            device=processing_device
        )

        # --- Print the results ---
        print("\n--- Pipeline Results ---")
        print(f"Transcript: {pipeline_results.get('transcript')}")
        
        nlp_analysis = pipeline_results.get("nlp_analysis", {})
        print("\nNLP Analysis:")
        print(f"  Keyword: {nlp_analysis.get('keyword')}")
        print(f"  Topic:   {nlp_analysis.get('topic')}")
        print(f"  Summary: {nlp_analysis.get('summary')}")

    except FileNotFoundError as e:
        print(f"Error during processing: {e}. Please ensure the audio file path is correct.")
    except RuntimeError as e:
        print(f"Runtime error during processing: {e}")
    except Exception as e:
        print(f"An unexpected error occurred: {e}")

if __name__ == "__main__":
    main() 