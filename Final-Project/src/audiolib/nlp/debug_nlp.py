from audiolib.nlp import analyze_text

SAMPLE_TEXT = """
“Mr. Mitchell, how do we access the punk?”

That’s what a student asked at Emerson College in Boston after a recent screening of “Shortbus,” my 2006 film, 
which chronicles a real-life bohemian New York City art and sex salon scene that flourished before most of the 
college-age viewers in the hall were born. When the film was rereleased a few years ago, I sensed that members
of this younger, judgier generation loved it but felt: There’s got be something to cancel about it! Last year 
a young woman asked me if the story of an Asian woman, the protagonist of “Shortbus,” seeking an orgasm was “my
story to tell.” I replied, trying not to sound defensive, “Through the alchemy of writer and performer, it became
our story to tell.” She smiled, but only with her mouth.
"""

result = analyze_text(SAMPLE_TEXT, device="cpu")
print("Summary:", result["summary"])