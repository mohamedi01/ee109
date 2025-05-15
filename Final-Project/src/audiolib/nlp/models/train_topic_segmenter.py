#!/usr/bin/env python3
"""
train_topic_segmenter.py

Fine-tune a DistilBERT-based topic classifier on the AG News dataset.
Saves model and tokenizer to 'models/topic_segmenter/'.
"""
from datasets import load_dataset, ClassLabel
from transformers import (
    AutoTokenizer,
    AutoModelForSequenceClassification,
    TrainingArguments,
    Trainer,
)


def main():
    # 1) Load AG News dataset
    splits = load_dataset(
        "ag_news",
        split={"train": "train", "validation": "test"}
    )
    ds_train = splits["train"]
    ds_val   = splits["validation"]

    # 2) Feature 'label' already integer; extract class names
    num_labels = ds_train.features["label"].num_classes
    label_names = ds_train.features["label"].names
    class_feat = ClassLabel(names=label_names)

    # 3) Load tokenizer and model
    tokenizer = AutoTokenizer.from_pretrained("distilbert-base-uncased")
    model     = AutoModelForSequenceClassification.from_pretrained(
        "distilbert-base-uncased",
        num_labels=num_labels
    )

    # 4) Preprocess function
    def preprocess(batch):
        enc = tokenizer(
            batch["text"],
            truncation=True,
            padding="max_length",
            max_length=128
        )
        enc["labels"] = batch["label"]
        return enc
    ds_train = ds_train.map(preprocess, batched=True, remove_columns=ds_train.column_names)
    ds_val   = ds_val.map(preprocess, batched=True, remove_columns=ds_val.column_names)

    # 5) Training arguments and Trainer
    training_args = TrainingArguments(
        output_dir="models/topic_segmenter",
        evaluation_strategy="epoch",
        save_strategy="epoch",
        per_device_train_batch_size=16,
        per_device_eval_batch_size=16,
        num_train_epochs=3,
        logging_steps=50,
        load_best_model_at_end=True,
        metric_for_best_model="accuracy",
    )
    def compute_metrics(p):
        preds = p.predictions.argmax(-1)
        labels = p.label_ids
        accuracy = (preds == labels).astype(float).mean()
        return {"accuracy": accuracy}

    trainer = Trainer(
        model=model,
        args=training_args,
        train_dataset=ds_train,
        eval_dataset=ds_val,
        tokenizer=tokenizer,
        compute_metrics=compute_metrics,
    )

    # 6) Train and save
    trainer.train()
    trainer.save_model("models/topic_segmenter")
    tokenizer.save_pretrained("models/topic_segmenter")


if __name__ == "__main__":
    main()
