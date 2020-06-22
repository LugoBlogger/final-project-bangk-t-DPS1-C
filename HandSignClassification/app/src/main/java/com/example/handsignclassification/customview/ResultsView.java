package com.example.handsignclassification.customview;

import com.example.handsignclassification.tflite.Classifier.Recognition;

import java.util.List;

public interface ResultsView {
  public void setResults(final List<Recognition> results);
}
