# Regression Model Evaluation Metrics — Practical Decision Guide

---
    
| **MAE**          | **MSE**            | $R^2$                      |
|------------------|--------------------|----------------------------|
| **RMSE**         |    **MAPE**        | **Adjusted $R^2$**         |
---

$$ MAE (Means absolute error)$$
```python
from sklearn.metrics import mean_absolute_error
```
MAE value it self does not provide any information , if mean_absolute_error this method return some value like 10,20,70,350 . we understand nothing from here.

To understand the MAE value we need to know the range of true $y$ , if $y$ range in between [1,1000] then we understand MAE values meaning perfectly

- 10 means here $1\%$ in scale of range [1,1000]
- 100 means here $10\%$ in scale of range [1,1000]

what to consider here 

| Percentage                | Acceptance        |
|---------------------------|-------------------|
| $1\%$                     | Excellent         |
| $5\%$                     | It's Good         |
| $10\%$                    | Okey              |
| more than $10\%$          | It's bad          |
|___________________________|___________________|
---
$$ RMSE (\text{Root Mean Square Error})  $$
```python
from sklearn.metrics import root_mean_squared_error
```
It's same as MAE , its value it self does nothing, we need to know the range and same way the acceptable range is same as MAE 

Now question:

_Why do you need to use RMSE?_
Because MAE just tell overall error, or function loss and RMSE provides more details information 

|Losses                |      MAE      |    RMSE   |
|----------------------|---------------|-----------|
| 5, 5, 5, 5           |       5       |     5     |
| 0, 0, 0, 20          |       5       |    10     |
|______________________|_______________|___________|

```txt
MAE tells you:
What is the normal prediction error?

RMSE tells you:
Are there dangerous extreme errors?
```

**⚠ NOTE:** _RMSE is not be lowe than MAE, RMSE always will be close to equal or higher ._

---

| condition           | Message                                                             |
|---------------------|---------------------------------------------------------------------|
| RMSE < MAE          | redfleg , check code , mode and logic , we have technical error     |
| RMSE > MAE          | Much bigger difference is problem                                   |
| RMSE $\approx$ MAE  | Model mostly working good, a perfect model                   |
|______________|_______________________________________________________|


What to do if we have big difference in between RMSE and MAE 

### Check carefully
- Check outlier
- Quality
    - Wrong label
    - Real world quality loss
- Missing values
-  Model

---


$$ R^2 \text{ (Coefficient of Determination)} $$

```python
from sklearn.metrics import r2_score

r2 = r2_score(y_true, y_pred)
print(r2)
```

$R^2$ tells us how much variation in the true $y$ values is explained by the model compared with predicting the mean of $y$.

```text
R² = 1  → Perfect prediction
R² = 0  → Same performance as predicting the mean
R² < 0  → Worse than predicting the mean
```

### What to consider

| $R^2$ value        | Acceptance                           |
|--------------------|--------------------------------------|
| $0.95$ to $1.00$   | Excellent, but check data leakage    |
| $0.90$ to $0.95$   | Very good                            |
| $0.80$ to $0.90$   | Good                                 |
| $0.70$ to $0.80$   | Possibly acceptable                  |
| $0.50$ to $0.70$   | Weak to moderate                     |
| Below $0.50$       | Usually weak                         |
| Below $0$          | Red flag                             |
|____________________|______________________________________|

**⚠ NOTE:** These ranges are not universal. Acceptable $R^2$ depends on the domain, data noise and business requirements.

### Why is $R^2$ not enough?

$R^2$ does not tell us the actual prediction error.

```text
R² tells you:
How much variation does the model explain?

MAE and RMSE tell you:
How large are the prediction errors?
```

A model can have a high $R^2$ but still have an unacceptable MAE or RMSE.

Therefore:

```text
Always evaluate R² together with MAE and RMSE.
```

---

$$ \text{Adjusted } R^2 $$

Adjusted $R^2$ evaluates the model while considering the number of input features.

$$
\text{Adjusted } R^2
=
1-(1-R^2)\frac{n-1}{n-p-1}
$$

Where:

```text
n = Number of observations
p = Number of input features
```

```python
n = len(y_true)
p = X_test.shape[1]

adjusted_r2 = 1 - (1 - r2) * (n - 1) / (n - p - 1)

print(adjusted_r2)
```

### Why do we need Adjusted $R^2$?

Normal $R^2$ usually stays the same or increases when new features are added, even when those features are not useful.

Adjusted $R^2$ penalizes unnecessary features.

```text
R² tells you:
How much variation is explained?

Adjusted R² tells you:
Is that performance justified by the number of features?
```

### Example

| Model     | Features | $R^2$    | Adjusted $R^2$ |
|-----------|---------:|---------:|---------------:|
| Model A   | 3        | $0.90$   | $0.897$        |
| Model B   | 20       | $0.91$   | $0.887$        |

Model B has a slightly higher $R^2$, but its Adjusted $R^2$ is lower.

This indicates that the additional features are not providing enough useful information.

---

### Relationship between $R^2$ and Adjusted $R^2$

$$
\text{Adjusted } R^2 \leq R^2
$$

| Condition                                 | Message                                                     |
|-------------------------------------------|-------------------------------------------------------------|
| Adjusted $R^2 \approx R^2$                | Most features are likely contributing useful information    |
| Adjusted $R^2$ slightly lower than $R^2$  | Normal condition                                            |
| Adjusted $R^2$ much lower than $R^2$      | Too many weak or unnecessary features may exist.            |
| Adjusted $R^2 < 0$                        | Model is weak compared with                                 |
| Adjusted $R^2 > R^2$                      | Check the formula, code or regression setup                 |

### What to check when the difference is large

- Too many features compared with the number of observations
- Irrelevant features
- Duplicate features
- Highly correlated features
- Low-variance features
- Poor feature engineering
- Excessive polynomial features
- Overfitting
- Data leakage

---

### Final decision

```text
High R² + Adjusted R² close to R²
→ Strong performance with justified features

High R² + Adjusted R² much lower than R²
→ Too many weak or unnecessary features may exist

Low or negative R²
→ Model does not explain the target well

High R² + high MAE or RMSE
→ Variation is explained, but prediction errors are still too large
```

---

$$ MSE \text{ (Mean Squared Error)} $$

```python
from sklearn.metrics import mean_squared_error

mse = mean_squared_error(y_true, y_pred)
print(mse)
```

MSE calculates the average squared difference between the actual and predicted values.

$$
MSE = \frac{1}{n}\sum_{i=1}^{n}(y_i-\hat{y}_i)^2
$$

### Important relationship

$$
RMSE = \sqrt{MSE}
$$

$$
MSE = RMSE^2
$$

### Why do we need MSE?

MSE gives much more punishment to large prediction errors because every error is squared.

| Errors         | MAE    | MSE    | RMSE   |
|----------------|-------:|-------:|-------:|
| $5,5,5,5$      | $5$    | $25$   | $5$    |
| $0,0,0,20$     | $5$    | $100$  | $10$   |

Both cases have the same MAE, but the second case has a much larger MSE because of the extreme error of $20$.

```text
MAE:
Measures the average absolute error.

MSE:
Strongly punishes large prediction errors.

RMSE:
Returns the squared error to the original target unit.
```

### Important limitation

MSE is not expressed in the original target unit.

```text
If the target unit is kilograms:

MAE unit  = kilograms
RMSE unit = kilograms
MSE unit  = kilograms²
```

Therefore, the raw MSE value is difficult to interpret directly.

> **⚠ NOTE:** Use MSE mainly for model training, optimization and model comparison. Use MAE or RMSE for practical interpretation.

---

### MSE conditions

| Condition                | Message                                             |
|--------------------------|-----------------------------------------------------|
| $MSE = 0$.               | Perfect predictions                                 |
| Low MSE                  | Predictions are generally close to actual values    |
| High MSE                 | Large prediction errors may exist                   |
| MSE increases strongly   | Check outliers and extreme errors                   |
| MSE cannot be negative   | A negative value indicates a calculation error      |

### What to check when MSE is high

- Outliers
- Incorrect target values
- Wrong labels
- Unit mistakes
- Missing-value problems
- Poor feature engineering
- Model underfitting
- Distribution shift
- Some groups producing very large errors

---

### Final decision

```text
Use MSE:
For optimization and strongly penalizing large errors.

Use RMSE:
To interpret the squared error in the original target unit.

Use MAE:
To understand the average normal prediction error.
```

---

$$ MAPE \text{ (Mean Absolute Percentage Error)} $$

```python
from sklearn.metrics import mean_absolute_percentage_error

mape = mean_absolute_percentage_error(y_true, y_pred) * 100
print(f"{mape:.2f}%")
```

MAPE measures the average absolute prediction error as a percentage of each actual value.

$$
MAPE
=
\frac{100}{n}
\sum_{i=1}^{n}
\left|
\frac{y_i-\hat{y}_i}{y_i}
\right|
$$

Example:

```text
MAPE = 8%
```

This means the predictions differ from the actual values by approximately $8\%$ on average.

### What to consider

| MAPE value | Acceptance |
|-------------------:|------------------------|
| $0\%$ to $5\%$     | Excellent              |
| $5\%$ to $10\%$    | Good                   |
| $10\%$ to $20\%$   | Possibly acceptable    |
| More than $20\%$   | Potentially weak       |

> **⚠ NOTE:** These ranges are only rough guidelines. Acceptable MAPE depends on the domain and business requirements.

---

### Critical limitation of MAPE

MAPE cannot work properly when an actual value is zero.

```text
Actual value = 0

Percentage error = Error / 0
```

Division by zero is undefined.

MAPE also becomes extremely large when actual values are close to zero.

### Example

```text
Actual value    = 1
Predicted value = 3
Absolute error  = 2
Percentage error = 200%
```

The absolute error is only $2$, but MAPE reports a $200\%$ error because the actual value is very small.

---

### When should we use MAPE?

Use MAPE when:

- All actual target values are positive
- Actual values are not zero
- Near-zero values are uncommon
- Percentage error is meaningful for the problem

Do not use MAPE when:

- Actual values contain zero
- Actual values are close to zero
- Target values can be negative
- Small target values dominate the metric

---

### Do not confuse MAPE with range-normalized MAE

#### Range-normalized MAE

$$
\text{Normalized MAE}
=
\frac{MAE}{y_{\max}-y_{\min}}
\times 100
$$

This compares the overall MAE with the total target range.

#### MAPE

$$
MAPE
=
\text{Mean}
\left(
\frac{\text{absolute error}}{\text{actual value}}
\right)
\times 100
$$

This calculates a percentage error separately for every observation.

```text
Normalized MAE:
How large is MAE compared with the full target range?

MAPE:
How large is each error compared with its actual value?
```

---

### MAPE conditions

| Condition                  | Message                                 |
|----------------------------|-----------------------------------------|
| $MAPE = 0\%$               | Perfect predictions                     |
| Low MAPE                   | Small relative prediction errors        |
| High MAPE                  | Large relative errors                   |
| High MAPE but low MAE      | Actual values may be close to zero      |
| Extremely large MAPE       | Check zero and near-zero target values  |

---
### MAPE conditions

| MAPE value | Interpretation | Message |
|---------------------------:|-----------------|------------------------------------------------------------------|
| $MAPE = 0\%$               | Perfect         | Predictions exactly match the actual values                      |
| $0\% < MAPE \leq 5\%$      | Excellent       | Very small relative prediction error                             |
| $5\% < MAPE \leq 10\%$     | Good            | Generally acceptable prediction error                            |
| $10\% < MAPE \leq 20\%$    | Moderate        | May be acceptable depending on the business requirement          |
| $20\% < MAPE \leq 50\%$    | High            | Model has large relative prediction errors                       |
| $MAPE > 50\%$              | Extremely high  | Model is usually unreliable or actual values may be near zero    |

> **⚠ NOTE:** These ranges are practical guidelines, not universal rules. Acceptance depends on the domain, data noise and business tolerance.

### Important diagnostic condition

| Condition                        | Possible reason.                                                                |
|----------------------------------|---------------------------------------------------------------------------------|
| High MAPE but low MAE            | Actual target values may be very small or close to zero                         |
| Extremely large MAPE.            | Check zero values, near-zero values, wrong labels and data-quality problem.     |
| Acceptable MAPE but high MAE     | Percentage error looks reasonable, but the absolute business error may still be too large |
---

### What to check when MAPE is high

- Zero actual values
- Near-zero actual values
- Incorrect target values
- Small-value observations
- Specific groups with poor predictions
- Large underprediction or overprediction
- Data-quality problems
- Distribution shift

---

### Final decision

```text
Use MAPE:
When targets are positive and percentage error is meaningful.

Do not use MAPE:
When targets contain zero, near-zero or negative values.

Use MAE or RMSE:
When MAPE becomes unstable or misleading.
```
