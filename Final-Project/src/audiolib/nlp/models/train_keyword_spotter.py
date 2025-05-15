#!/usr/bin/env python3
"""
train_keyword_spotter.py

Fine-tune a Wav2Vec2-based keyword spotter on the Google Speech Commands dataset.
Saves model and feature extractor to 'models/keyword_spotter/'.

This script downloads the Speech-Commands dataset directly (ignoring SSL certificate checks).
"""
import torch
from datasets import load_dataset, Audio, DownloadConfig
from transformers import (
    Wav2Vec2FeatureExtractor,
    Wav2Vec2ForSequenceClassification,
    TrainingArguments,
    Trainer,
)


def main():
    # 1) Configure DownloadConfig to ignore SSL verification
    dl_config = DownloadConfig(
        local_files_only=False
    )

    # 2) Load and split the Speech-Commands dataset
    splits = load_dataset(
        "speech_commands",
        "v0.02",
        trust_remote_code=True,
        download_config=dl_config,
        split={"train": "train[:80%]", "validation": "train[80%:]"}
    )
    ds_train = splits["train"].cast_column("audio", Audio(sampling_rate=16_000))
    ds_val   = splits["validation"].cast_column("audio", Audio(sampling_rate=16_000))

    # 3) Rename 'label' column (already integer IDs) to 'labels' for Trainer compatibility
    ds_train = ds_train.rename_column("label", "labels")
    ds_val   = ds_val.rename_column("label", "labels")

    # 4) Load feature extractor and classification model
    extractor = Wav2Vec2FeatureExtractor.from_pretrained(
        "facebook/wav2vec2-base-960h"
    )
    # The number of labels comes from dataset feature
    num_labels = ds_train.features["labels"].num_classes
    model      = Wav2Vec2ForSequenceClassification.from_pretrained(
        "facebook/wav2vec2-base-960h",
        num_labels=num_labels
    )

    # 5) Preprocessing function for Trainer
    def preprocess(batch):
        audio_arrays = [x["array"] for x in batch["audio"]]
        inputs = extractor(
            audio_arrays,
            sampling_rate=16_000,
            return_tensors="pt",
            padding=True
        )
        inputs["labels"] = torch.tensor(batch["labels"])
        return inputs

    ds_train = ds_train.map(
        preprocess,
        batched=True,
        batch_size=16,         # small batches to keep memory use in check
        num_proc=4,            # spin up 4 processes in parallel
        remove_columns=ds_train.column_names,
    )
    ds_val = ds_val.map(
        preprocess,
        batched=True,
        batch_size=8,
        remove_columns=ds_val.column_names
    )

    # 6) Setup TrainingArguments and Trainer
    training_args = TrainingArguments(
        output_dir="models/keyword_spotter",
        evaluation_strategy="epoch",
        save_strategy="epoch",
        per_device_train_batch_size=8,
        per_device_eval_batch_size=8,
        num_train_epochs=3,
        logging_steps=50,
        load_best_model_at_end=True,
        metric_for_best_model="accuracy",
    )
    def compute_metrics(p):
        preds  = p.predictions.argmax(-1)
        labels = p.label_ids
        accuracy = (preds == labels).astype(float).mean()
        return {"accuracy": accuracy}

    trainer = Trainer(
        model=model,
        args=training_args,
        train_dataset=ds_train,
        eval_dataset=ds_val,
        tokenizer=extractor,
        compute_metrics=compute_metrics,
    )

    # 7) Train and save model + extractor
    trainer.train()
    trainer.save_model("models/keyword_spotter")
    extractor.save_pretrained("models/keyword_spotter")

if __name__ == "__main__":
    main()
