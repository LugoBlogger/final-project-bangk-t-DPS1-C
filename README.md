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
     
 5. Run the app, by clicking toolbar menu `Run` -> `Run 'app'` in Android Studio.

## Several results

All of our models have fixed hyperparameters (`learning rate = 1e-5`, `max_epochs = 100`, and `accuracy >= 0.9`) (but you can change it as you want to explore more possibilities).

- For baseline model (`classif-pre-trained-baseline-DataSet-Signos.ipynb`), we choose the following layers structure (based on CNN Coursera course, with a little modification) and use Adam optimizers.
     ```py
     model = tf.keras.Sequential([
          base_model,  # MobileNet V2
          tf.keras.layers.Conv2D(32, (5,5), activation='relu'),
          tf.keras.layers.Dropout(0.2),
          tf.keras.layers.GlobalAveragePooling2D(),
          tf.keras.layers.Dense(256, activation='relu'),
          tf.keras.layers.Dropout(0.5),
          tf.keras.layers.Dense(num_classes, activation="softmax")])
     ```
     <p align="center">
     <img src="https://raw.githubusercontent.com/LugoBlogger/final-project-bangkit-DPS1-C/master/slide-image-resources/baseline-fine.png" width="500"/>
     </p>
 
     As we can see after 100 epochs, the accuracy does not go much higher than 0.9. Then the next graph (right to the vertical green line), after we unfreeze some layer from MobileNet V2, doesn't give any improvement. 


- We tried several improvement by changing the optimizers. We get that changing the optimizers gives much faster to attain accuracy `>= 0.9` for small `max_epochs`. For (Adam, RMSprop, and SGD), we simplify `GlobalAveragePooling2D()` to `Flatten()` and remove `Conv2D` and `Dropout(0.2)`. This means that we do improvement by finding the right optimizer and reduce the layers. We reduce the layers because small dataset is easily prone of overfitting. We choose this improvement because it is the simpler one to fight overfitting (even though we still have overfitting because of the very few numbers of our dataset).

     The following lines are layers in three models (Adams, RMSprop, and SGD).
     ```py
     model = tf.keras.Sequential([
          base_model,  # MobileNet V2
          tf.keras.layers.Flatten(),
          tf.keras.layers.Dense(256, activation='relu'),
          tf.keras.layers.Dropout(0.5),
          tf.keras.layers.Dense(num_classes, activation="softmax")])
     ```
     
### Table of comparison

|                     | baseline     | Adam        | RMSprop     | SGD          |
|:-                   | :-:          | :-:         | :-:         | :-:          |
| time/epoch          | ~ 30 s       | ~ 44 s      | ~ 45 s      | ~ 40 s       |
| reach acc >= 0.9 in | > 100 epochs | ~ 14 epochs | ~ 16 epochs | > 100 epochs |

Fom the above table, we see that Adam and RMSprop give two reliable results with small maximum epochs to attain accuracy greater than or equal to 0.9.
We see an improvement in time to train and also memory consumption to store `.tflite` (less layer, less memory consumption). 
     
## Our team

- Kevin Chandra (`simple-ui-android-app`)
- Octavia Citra Resmi Rachmawati (`android-app`)
- Yogic Wahy Rhamadianto (`forecast`, data pre-processing, baseline model)
- Henokh Lugo Hariyanto (ML model construction)

## Important resouces

- Q. Xiang, et.al., (2019) Fruit Image Classification Based on MobileNetV2 with Transfer Learning Technique
- https://www.tensorflow.org/tutorials/images/transfer_learning
