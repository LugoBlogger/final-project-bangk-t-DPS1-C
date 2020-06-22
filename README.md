# Hand Sign Classifier with MobileNet V2

## Foreword

Welcome to our preliminary project for Bangk!t final project. We called it preliminary since this project is the part of a big project that we hope to achieve in the near future (we hope so). In this project, we want to create an app to track hand motion such that it can control pointer in a ticketing machine. By doing that, people who are in queue line do not need to touch the screen or push any button.

But we find out it is not easy to implement. Then we start to study first how to deploy image classification. To do that, we use simple dataset (unfortunately very few in numbers) of [American Sign Language (ASL)](https://www.kaggle.com/jordiviader/american-sign-language-alphabet-static). We have learned this small dataset gave us overfitting, but we can overcome it (not so much) by lowering our neural nets. We also choose this dataset among all ASL dataset in kaggle, because the dataset is represented in RGB. It means that it is closer enough to the real-world case.

From this image classification app, we can also improve our understanding on how mobile app deployment works.

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

## Usage

1. Clone this directory to your local repository by typing in the terminal `git clone https://github.com/LugoBlogger/final-project-bangkit-DPS1-C.git`

2. You can start playing around with our four models in these following files:
     - `classif-pre-trained-baseline-DataSet-Signos.ipynb`  

     - `classif-pre-trained-adam-DataSet-Signos.iypnb`  

     - `classif-pre-trained-rmsprop-DataSet-Signos.iypnb`  

     - `classif-pre-trained-sgd-DataSet-Signos.iypnb`

3. After you run one of each model above, `classif_preTrained.tflite` will be generated. You can use this `.tflite` as a model in your Android app.

4. Open `simple-ui-android-app` by using Android Studio. Click **Project** tab manager and set its tab list to **Android** (there will by many tab list and Android tab list is the last one). Copy your generated `.tflite` from  previous step to `app/assets`.

     (optional) If you change your `.tflite` filename, you need to change a small code under `app/java/com.example.handsignclassification/tflite/ClassifierCustomModel`. Find the following line

     ```java
    @Override
    protected String getModelPath() {
        // you can download this file from
        // see build.gradle for where to obtain this file. It should be auto
        // downloaded into assets.
        //return "model.tflite";
        return "classif_preTrained.tflite";
    }
     ```
     
     Change string text after `return` into your new `.tflite` filename.
     
 5. Run the app, by clicking toolbar menu `Run` -> `Run 'app'`.

## Several results

For baseline model (`classif-pre-trained-baseline-DataSet-Signos.ipynb`), we choose the following layers structure (based on CNN Coursera course, with a little tweaking)

-

## Our team

- Kevin Chandra (`simple-ui-android-app`)
- Octavia Citra Resmi Rachmawati (`android-app`)
- Yogic Wahy Rhamadianto (`forecast`, data pre-processing, baseline model)
- Henokh Lugo Hariyanto (ML model construction)

## Important resouces

- Q. Xiang, et.al., (2019) Fruit Image Classification Based on MobileNetV2 with Transfer Learning Technique
- https://www.tensorflow.org/tutorials/images/transfer_learning
