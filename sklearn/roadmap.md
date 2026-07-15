## Dataset transformation 
 must know : 
- fit
- transform
- fit_transform
#### Scalling 
```python
from sklearn.preprocessing import (
    StandardScaler,
    MinMaxScaler,
    RobustScaler,
    OneHotEncoder, 
    OrdinalEncoderr, 
    LabelEncoder
)
```

# NLP 
```python 
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer
```

## Model training 
` classifierr model `
- LogisticRegression 
- SVC
- RandomForestClassifier

`Regiression`
- LinearRegresion
- RandomForestRegressor
  
`Clustering`
- KMeans 

## Evalulate Model and validation 
```python
from sklearn.metrics import (
        accuracy_score,
        confusion_matrix,
        classification_report
        )
from sklearn.model_selection import cross_val_score,KFold
```
## Hyperparameter tuning 

```python
from sklearn.model_selection import GridSearch, RandomizedSearchCV 
```

## Creating ML pipeline 
```python
from sklearn.pipeline import Pipeline
pipeline=Pipeline([
    ("scaler", StandardScaler()),
    ("model",LogicticRegression())
])
```



# Model selection 
## 1. Linear Regression
* **Use Cases:** Predicting continuous numerical values where the relationship is approximately linear.
* **Examples:**
  * House price prediction
  * Salary prediction
  * Weight prediction
  * Temperature prediction

## 2. Polynomial Regression
* **Use Cases:** Predicting continuous values where the relationship is curved or non-linear.
* **Examples:**
  * Age vs. heart rate changes
  * Population growth patterns
  * Biological measurements that change gradually
## Logistic Regression 
* **Use cases:** Predicting category or probabilities
* **Examples:**
  * Disease vs no disease 
  * Spam vs NO spam 
  * Customer will buy vs will not by 
  * pass or fail 
## Dicision Tree
* **Use case** Making rule-based decisions from data
* **Examples:** 
  * Medical diagnosis 
  * loan approval 
  * Customer classification 
  * selecting treatments based on patient features 
## Random Forest (Tree Ensemble)
* **Use case:** Handling complex relationships and many interacting variables , 
* **Examples :** 
  * Disease risk prediction 
  * fraud detection 
  * credit scoring 
  * customer behavious analysis 

## Gradient Boosting (XGBoost , LightGBM, CatBoost)
* **Use case:** Hight performance prediction tasks with structured/tabular data , 
* **Examples:** 
  * Financial risk prediction 
  * Medical outcome prediction 
  * Sales forecasting 
  * Competition-level machine learning task 
## Support vector machine (SVM)
* **Use case** Classification or regression when data separation is complex but the dataset is not extremely large . 
* **Example: **
  * Image classification 
  * Text classification 
  * Biological data analysis 
## K-Nearest neighbors (KNN)
* **Use cases :** Predition based on similarity to previous 
* **Examples :**
  * Recommendation system 
  * Pattern recognition 
  * Simple classification problem 

## Time Series Models (ARIMA,LSTM , Transformers)
* **Use cases :** Data that changes over time and depends on previous events 
* **Examples :**
  * Stock trends 
  * Weather forecasting 
  * Heart monitoring 
  * Demand forecasting 



___
Problem to be solved 
- Predicting disease and suggestion , what to change to reduce risk 
