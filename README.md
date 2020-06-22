# Hand Sign Classifier with MobileNet V2

## Foreword

Welcome to our preliminary project for Bangk!t final project. We called it preliminary since this project is the part of a big project that we hope to achieve in the near future (we hope so). In this project, we want to create an app to track hand motion, but we find out it is not easy to implement. Then we start to study first how to deploy image classification. To do that, we use simple dataset (unfortunately very few in numbers) of [American Sign Language](https://www.kaggle.com/jordiviader/american-sign-language-alphabet-static). From this image classification app, we can improve our understanding on how mobile app deployment works.

To simplify further, we use pre-build Android app from [TensorFlow example](https://github.com/tensorflow/examples/tree/master/lite/examples/image_classification/android)

## Directory structure

(We only mention the important files and directories)

```
root
├─── forecast (our initial project - defunct)
└─── hand-sign-classification
     ├─── android-app
     ├─── simple-ui-android-app (our program)
     ├─── dataset
     ├─── classif-pre-trained-baseline-DataSet-Signos.ipynb
     ├─── classif-pre-trained-adam-DataSet-Signos.ipynb
     ├─── classif-pre-trained-rmsprop-DataSet-Signos.ipynb
     ├─── classif-pre-trained-sgd-DataSet-Signos.ipynb
     ├─── classif_preTrained.tflite
     └─── simple-ui-android-app.apk (download this if you want to install in your device)
```

## Our team

- Kevin Chandra (`simple-ui-android-app`)
- Octavia Citra Resmi Rachmawati (`android-app`)
- Yogic Wahy Rhamadianto (`forecast`, data pre-processing, baseline model)
- Henokh Lugo Hariyanto (ML model construction)

## Important resouces

- Q. Xiang, et.al., (2019) Fruit Image Classification Based on MobileNetV2 with Transfer Learning Technique
- https://www.tensorflow.org/tutorials/images/transfer_learning
