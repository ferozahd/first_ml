# Classification evalution 
- **Confusion matrix**
- **Accuracy**
- **Precision**
- **Recall**
- **F1 score**
- **ROC AUC**
- **PR AUC**
- **Log Loss**

---
## Confusion matrix 

```python
from sklearn.metrics import confusion_matrix
```
A confusion matrix evaluates the performance of a binary classification model.

The four possible prediction results are:

- **TP — True Positive**
- **TN — True Negative**
- **FP — False Positive**
- **FN — False Negative**

## Example: Disease Detection

Suppose a machine-learning model predicts whether a patient has a disease.

- **Positive** means the patient has the disease.
- **Negative** means the patient does not have the disease.

## 1. True Positive — TP

The patient actually has the disease, and the model correctly predicts that the patient has the disease.

**Example:**

- Actual result: Disease
- Model prediction: Disease

Therefore:

> The prediction is a **True Positive (TP)**.

---

## 2. True Negative — TN

The patient does not have the disease, and the model correctly predicts that the patient does not have the disease.

**Example:**

- Actual result: No disease
- Model prediction: No disease

Therefore:

> The prediction is a **True Negative (TN)**.

---

## 3. False Positive — FP

The patient does not have the disease, but the model incorrectly predicts that the patient has the disease.

**Example:**

- Actual result: No disease
- Model prediction: Disease

Therefore:

> The prediction is a **False Positive (FP)**.

A false positive is also called a **false alarm**.

---

## 4. False Negative — FN

The patient actually has the disease, but the model incorrectly predicts that the patient does not have the disease.

**Example:**

- Actual result: Disease
- Model prediction: No disease

Therefore:

> The prediction is a **False Negative (FN)**.

A false negative is dangerous in medical diagnosis because a sick patient may be classified as healthy.

---

# Confusion Matrix Structure

|                      | Predicted Negative | Predicted Positive |
|----------------------|-------------------:|-------------------:|
| **Actual Negative**  | TN                 | FP                 |
| **Actual Positive**  | FN                 | TP                 |

The matrix representation is:

---
&rarr; _Check multilabel confusion matrix also_

---

$$ 2.\text{ Accuracy score} $$
---
```python
from sklearn.metrics import accuracy_score
```
Accuracy measures how many predictions a classification model made correctly. it's the average the total $\text{True Positive}$ and $\text{True Negative}$ , it self does not describe $\text{True Positive}$ or $\text{True Negative}$ alone


## Formula

$$
\text{Accuracy} = \frac{\text{Number of Correct Predictions}}{\text{Total Predictions}}
$$

$$
\text{Accuracy}=\frac{TP+TN}{TP+FN+FP+FN}
$$


---
$$3.Precision$$
---
Precision is ratio about True positive by True positive and False positive,
```text
Example:
We have 50 black eyes people , if our model said that we have 45 black eyes people and 5 people does not have black eyes ,
```
```text
total = TP + FP
identified = TP 
```
$$
\text{precision}=\frac{45}{50} =0.9
$$

---
$$4.Recall$$
-
Recall calculates the ratio of special cases from true prediction. 
```text 
Suppose we have 70 patient, those whom we need to diagonisis weather they have fever or not. suppose 25 people really have no fever and 45 patient really have fever, 
but our system saying 
Amoung 45 patient our model identifed 42(TP) correctly and amoung 25 non fever patient our model identifed 17(TN) patient correctly but for next 11 patient was confused prediction, thir prediction was random 3 was (FP) and 8 was (FN)
```

so our confusion matrix was 

| 42(TP) | 3(FP) |
|--------:|------|
| 17(TN)  | 8(FN) |

Formula:
$$
\text{Recall score} =
\frac{\text{Total true positive}}{\text{Total success test score}}
$$
$$
\text{Recall score} =
\frac{TP}{TP+TN}
$$
$$
\text{Recall score} =
\frac{42}{42+17} =
\frac{42}{59} = 0.71
$$

---
$$\text{Scoreing table}$$
--

| value        | Acceptance                                          |
|:------------:|-----------------------------------------------------|
|0.7<          | Bad model                                           |
|0.7           | May be fair                                         |
|0.8           | Decend                                              |
|0.9           | Usually very good                                   |
|0.96>         | It's excellent                                      |
|1.0           | Perfect, need to check overfiting and data leakage  |
|______________|_____________________________________________________|

---
$$\text{5. ROC AUC}$$
---

