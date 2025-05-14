import pytest

from audiolib.nlp import (
    analyze_text,
    measure_latency,
    measure_throughput,
    evaluate_classifiers,
)

SAMPLE_TEXT = ( """
“Mr. Mitchell, how do we access the punk?”

That’s what a student asked at Emerson College in Boston after a recent screening of “Shortbus,” my 2006 film, 
which chronicles a real-life bohemian New York City art and sex salon scene that flourished before most of the 
college-age viewers in the hall were born. When the film was rereleased a few years ago, I sensed that members
of this younger, judgier generation loved it but felt: There’s got be something to cancel about it! Last year 
a young woman asked me if the story of an Asian woman, the protagonist of “Shortbus,” seeking an orgasm was “my
story to tell.” I replied, trying not to sound defensive, “Through the alchemy of writer and performer, it became
our story to tell.” She smiled, but only with her mouth.
"""
)

@pytest.fixture(scope="module")
def sample_texts():
    # a list of sample inputs for performance and evaluation
    return [SAMPLE_TEXT for _ in range(3)]

def test_analyze_text_smoke():
    """Smoke test for analyze_text: types and non-empty outputs."""
    res = analyze_text(SAMPLE_TEXT, device="cpu")
    assert isinstance(res, dict)
    # check keys
    assert set(res.keys()) == {"keyword", "topic", "summary"}
    # summary
    summary = res["summary"]
    assert isinstance(summary, str)
    assert len(summary) > 0
    # keyword
    key_label, key_conf = res["keyword"]
    assert isinstance(key_label, str)
    assert isinstance(key_conf, float)
    assert 0.0 <= key_conf <= 1.0
    # topic
    top_label, top_conf = res["topic"]
    assert isinstance(top_label, str)
    assert isinstance(top_conf, float)
    assert 0.0 <= top_conf <= 1.0

@pytest.mark.parametrize("func", [measure_latency, measure_throughput])
def test_performance_funcs_return_positive(func, sample_texts):
    """measure_latency and measure_throughput should return positive numbers."""
    result = func(sample_texts, device="cpu")
    assert isinstance(result, float)
    assert result > 0

@pytest.mark.parametrize(
    "texts, keyword_labels, topic_labels",
    [
        (
            ["hello world", "hello world"],  # dummy texts
            ["hello", "hello"],               # dummy matching labels
            ["greeting", "greeting"],         # dummy matching labels
        )
    ]
)
def test_evaluate_classifiers_types_and_ranges(texts, keyword_labels, topic_labels):
    """Test evaluate_classifiers returns metrics in [0,1] and correct keys."""
    metrics = evaluate_classifiers(texts, keyword_labels, topic_labels, device="cpu")
    assert set(metrics.keys()) == {"keyword_accuracy", "keyword_f1", "topic_accuracy"}
    for v in metrics.values():
        assert isinstance(v, float)
        assert 0.0 <= v <= 1.0
