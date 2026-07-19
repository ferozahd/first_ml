# Regression Model Evaluation Metrics  
## Practical Guide for Understanding Model Performance Through Custom Implementations

This guide supports the following notebooks:

- `adjusted_r2_score.ipynb`
- `explained_variance.ipynb`
- `mae_evaluation.ipynb`
- `mape_evaluation.ipynb`
- `mase_evaluation.ipynb`
- `medae_evaluation.ipynb`
- `mse_evaluation.ipynb`
- `r2_score.ipynb`
- `rmse_evaluation.ipynb`

> **Important terminology:** These notebooks evaluate **regression model performance**. The word **accuracy** is normally used for classification. For regression, use terms such as **prediction error**, **goodness of fit**, **explained variation**, and **model performance**.

---

# 1. Why Implement Evaluation Metrics Manually?

Using built-in functions is efficient in production, but implementing each metric manually provides deeper understanding.

## Main advantages

1. **Understand the mathematics**  
   You learn exactly how prediction errors are calculated and aggregated.

2. **Understand metric behavior**  
   You can see why some metrics punish large errors more strongly than others.

3. **Identify limitations**  
   Manual implementation exposes problems such as division by zero, sensitivity to outliers, and invalid sample sizes.

4. **Debug models more effectively**  
   When a score changes unexpectedly, you can trace the reason instead of treating the metric as a black box.

5. **Validate library results**  
   Your custom result can be compared with `scikit-learn` or another library implementation.

6. **Select the correct metric**  
   Understanding the internal calculation helps you choose a metric based on the real cost of prediction errors.

## Recommended validation pattern

```python
custom_score = custom_metric(y_true, y_pred)
library_score = sklearn_metric(y_true, y_pred)

assert np.isclose(custom_score, library_score, rtol=1e-9, atol=1e-12)
```

---

# 2. General Evaluation Workflow

For every regression model, use the following process:

1. Separate training, validation, and test data.
2. Train the model only on the training set.
3. Tune decisions using the validation set.
4. Evaluate the final model once on the untouched test set.
5. Compare multiple metrics instead of relying on one score.
6. Inspect residual plots and individual large-error cases.
7. Compare the model with a simple baseline.
8. Take action based on the error pattern, not only the final number.

## Core notation

- ($y_i$): actual value
- ($\hat{y}_i$): predicted value
- ($n$): number of observations
- ($p$): number of input predictors
- ($\bar{y}$): mean of actual values
- ($e_i = y_i - \hat{y}_i$): residual or prediction error

---

# 3. Metric Selection Summary

| Metric | Main purpose | Better direction | Main strength | Main limitation |
|---|---|---:|---|---|
| R² | Measure fit relative to predicting the mean | Higher | Easy goodness-of-fit interpretation | Can hide large individual errors |
| Adjusted R² | Penalize unnecessary predictors | Higher | Useful for comparing nested regression models | Not suitable for every model type |
| Explained Variance | Measure captured variation | Higher | Shows how much variation is reproduced | May hide systematic prediction bias |
| MAE | Measure average absolute error | Lower | Easy to interpret in target units | Does not strongly punish large errors |
| MAPE | Measure average percentage error | Lower | Scale-independent percentage interpretation | Fails near zero actual values |
| MASE | Compare error with a naive baseline | Lower | Scale-free and useful for forecasting | Requires a valid baseline scale |
| Median AE | Measure typical absolute error | Lower | Highly resistant to outliers | Can hide severe tail errors |
| MSE | Strongly penalize large errors | Lower | Useful when large mistakes are costly | Squared units reduce interpretability |
| RMSE | Penalize large errors in original units | Lower | Interpretable and sensitive to large errors | Sensitive to outliers |

---

# 4. R² Score

## Definition

The coefficient of determination measures how well the model performs compared with a baseline that always predicts the mean of the actual target.

$$
R^2 = 1 - \frac{\sum_{i=1}^{n}(y_i-\hat{y}_i)^2}
{\sum_{i=1}^{n}(y_i-\bar{y})^2}
$$

## Advantage

- Measures improvement over the mean-prediction baseline.
- Provides a normalized goodness-of-fit score.
- Useful for comparing models trained on the same target and dataset.
- Commonly understood in statistics, physics, engineering, and machine learning.

## When to use

- Continuous regression problems.
- Comparing models evaluated on the same dataset.
- Studying how much target variation is captured.
- Linear regression and other regression models where goodness of fit matters.

## When not to use

- When the main concern is the actual size of errors in physical units.
- When comparing models across different datasets or target definitions.
- When the test target has almost no variation.
- When a high score could hide unacceptable errors in important cases.
- As the only model-selection metric.

## What to observe

| R² value | Interpretation |
|---:|---|
| \(1.0\) | Perfect prediction |
| Close to \(1\) | Strong fit relative to the mean baseline |
| \(0\) | No better than predicting the target mean |
| Less than \(0\) | Worse than predicting the target mean |

A negative R² is valid. It usually indicates poor generalization, data mismatch, underfitting, leakage-free but weak features, or an implementation problem.

## Action based on the score

- **High train R² and low test R²:** Reduce overfitting, simplify the model, add regularization, or obtain more data.
- **Low train and test R²:** Improve features, model capacity, target quality, or data representation.
- **Negative test R²:** Verify preprocessing, data alignment, target scaling, train-test distribution, and baseline performance.
- **High R² but high MAE/RMSE:** The model captures variation but still makes operationally large errors. Review target scale and residuals.
- **Stable R² across folds:** The model is more likely to generalize consistently.

---

# 5. Adjusted R² Score

## Definition

Adjusted R² modifies R² by penalizing the addition of predictors that do not provide enough improvement.

\[
R^2_{\text{adjusted}}
=
1-(1-R^2)\frac{n-1}{n-p-1}
\]

The formula requires:

\[
n > p + 1
\]

## Advantage

- Prevents misleading improvement caused by adding unnecessary predictors.
- Useful for feature-selection analysis.
- Helps compare nested linear regression models.
- Rewards useful predictors and penalizes weak ones.

## When to use

- Multiple linear regression.
- Comparing models using the same dataset and target.
- Comparing nested models with different numbers of predictors.
- Evaluating whether an added feature provides enough explanatory value.

## When not to use

- When \(n \le p+1\).
- As a universal metric for tree ensembles, neural networks, or highly nonlinear models.
- When the effective model complexity is not represented by the raw number of input columns.
- When evaluating predictive error in target units.

## What to observe

- Adjusted R² is normally less than or equal to R².
- If R² increases but adjusted R² decreases, the added feature may not be useful.
- If both scores increase, the new feature may provide meaningful information.
- A large gap between R² and adjusted R² may indicate too many weak predictors.

## Action based on the score

- **Adjusted R² increases after adding a feature:** Keep the feature provisionally, then verify with cross-validation.
- **Adjusted R² decreases:** Remove or reconsider the added feature.
- **Adjusted R² is much lower than R²:** Reduce dimensionality, remove redundant variables, or increase sample size.
- **Both scores remain low:** Better features or a different model family may be necessary.
- **Adjusted R² is high but test error is unstable:** Do not trust it alone; inspect cross-validation and residual behavior.

---

# 6. Explained Variance Score

## Definition

Explained variance measures how much of the variance in the target error is controlled by the model.

\[
\text{Explained Variance}
=
1-\frac{\operatorname{Var}(y-\hat{y})}
{\operatorname{Var}(y)}
\]

## Advantage

- Measures how well the model reproduces variation in the target.
- Useful when preserving the shape or variability of predictions matters.
- Can complement R² in scientific and engineering analysis.
- Helps determine whether residual variation is small compared with target variation.

## When to use

- Continuous regression.
- Comparing variation captured by different models.
- Scientific problems where reproducing changing behavior is important.
- As a complementary metric beside R², MAE, or RMSE.

## When not to use

- When systematic bias is a major concern.
- When the target variance is zero or almost zero.
- As the only metric for model quality.
- When actual error magnitude is more important than variance reproduction.

## What to observe

- A value near \(1\) indicates that most target variation is captured.
- A value near \(0\) indicates little improvement in explaining variation.
- A negative value indicates residual variation larger than target variation.
- Explained variance can remain high even when predictions have a nearly constant offset.

## Action based on the score

- **High explained variance but lower R²:** Check mean residual or systematic bias.
- **Low explained variance:** Improve features, model structure, or nonlinear representation.
- **Negative score:** Check data alignment, target leakage assumptions, preprocessing, and model failure.
- **High score but poor MAE/RMSE:** Variation is captured, but predictions may be shifted or badly scaled.
- **Different values across subsets:** Investigate distribution shift or subgroup-specific behavior.

---

# 7. Mean Absolute Error — MAE

## Definition

\[
MAE = \frac{1}{n}\sum_{i=1}^{n}|y_i-\hat{y}_i|
\]

## Advantage

- Expressed in the same unit as the target.
- Easy to explain: average absolute prediction error.
- Less sensitive to outliers than MSE or RMSE.
- Treats each unit of error linearly.

## When to use

- When all errors should contribute proportionally.
- When target units have direct practical meaning.
- When moderate resistance to outliers is desired.
- When the business or scientific cost grows approximately linearly with error size.

## When not to use

- When large errors must receive a much stronger penalty.
- When percentage error is required.
- When comparing targets with different units or scales without normalization.
- When rare catastrophic errors are more important than typical errors.

## What to observe

- MAE cannot be negative.
- MAE of zero means perfect prediction.
- The score must be interpreted relative to the target scale and baseline MAE.
- Compare train MAE, validation MAE, and test MAE.
- Compare MAE with RMSE. A much larger RMSE suggests some large errors.

## Action based on the score

- **MAE is above the acceptable domain threshold:** Improve features, tune the model, or revise the data.
- **Test MAE is much higher than train MAE:** Reduce overfitting.
- **MAE is low but RMSE is much higher:** Investigate outliers and rare large failures.
- **MAE differs strongly across groups:** Build subgroup diagnostics or specialized models.
- **MAE beats the baseline only slightly:** Reassess whether the model provides enough practical value.

---

# 8. Mean Absolute Percentage Error — MAPE

## Definition

\[
MAPE
=
\frac{100}{n}
\sum_{i=1}^{n}
\left|
\frac{y_i-\hat{y}_i}{y_i}
\right|
\]

## Advantage

- Expresses error as a percentage.
- Easy for nontechnical audiences to interpret.
- Scale-independent when actual values are safely away from zero.
- Useful for comparing relative forecast error across similar series.

## When to use

- Actual target values are strictly nonzero.
- Percentage error has practical meaning.
- The target is positive and not concentrated near zero.
- Relative error matters more than absolute-unit error.

## When not to use

- Actual values can be zero.
- Actual values can be very close to zero.
- The target includes negative values.
- Overprediction and underprediction should be treated symmetrically in all cases.
- Small actual values should not dominate the metric.

## What to observe

- A lower MAPE is better.
- Very large MAPE values often result from small denominators.
- A single near-zero actual value can dominate the result.
- MAPE can favor underprediction in some settings.
- Always inspect the distribution of actual target values before trusting MAPE.

## Action based on the score

- **MAPE is unexpectedly huge:** Check for zero or near-zero actual values.
- **MAPE is unstable across samples:** Use MAE, RMSE, WAPE, sMAPE, or MASE instead.
- **MAPE is acceptable overall but poor for low-value cases:** Segment the evaluation by target magnitude.
- **MAPE improves but MAE worsens:** The model may improve relative errors while increasing absolute errors.
- **Zeros are unavoidable:** Do not hide the problem with an arbitrary epsilon without clearly documenting the resulting metric change.

---

# 9. Mean Absolute Scaled Error — MASE

## Definition

For nonseasonal one-step forecasting:

\[
MASE
=
\frac{
\frac{1}{n}\sum_{i=1}^{n}|y_i-\hat{y}_i|
}{
\frac{1}{T-1}\sum_{t=2}^{T}|y_t-y_{t-1}|
}
\]

The denominator is the in-sample MAE of a naive forecast.

For seasonal data with seasonal period \(m\):

\[
\text{Scale}
=
\frac{1}{T-m}
\sum_{t=m+1}^{T}|y_t-y_{t-m}|
\]

## Advantage

- Scale-free.
- Can compare forecasting performance across series with different units.
- Works when actual values contain zero.
- Directly compares the model with a naive baseline.

## When to use

- Time-series forecasting.
- Comparing models across multiple series.
- Evaluating performance against naive or seasonal-naive forecasting.
- Targets with different scales.
- Cases where MAPE is invalid because of zeros.

## When not to use

- General independent regression without a meaningful ordering or naive forecast.
- When the naive-scale denominator is zero.
- When the baseline is poorly chosen.
- When train and evaluation series follow incompatible dynamics.
- When seasonality exists but a nonseasonal denominator is used.

## What to observe

| MASE value | Interpretation |
|---:|---|
| Less than \(1\) | Better than the selected naive baseline |
| Equal to \(1\) | Similar to the naive baseline |
| Greater than \(1\) | Worse than the naive baseline |
| \(0\) | Perfect prediction |

## Action based on the score

- **MASE < 1:** The model beats the naive baseline; verify consistency across forecast horizons.
- **MASE ≈ 1:** The model may not justify its complexity.
- **MASE > 1:** Improve the model or use the naive forecast.
- **MASE is undefined or extremely large:** Check whether the denominator is zero or nearly zero.
- **Seasonal data performs poorly:** Use a seasonal-naive scale with the correct period.
- **Different horizons have different MASE values:** Build horizon-specific diagnostics or models.

---

# 10. Median Absolute Error — MedAE

## Definition

\[
MedAE
=
\operatorname{median}
\left(
|y_i-\hat{y}_i|
\right)
\]

## Advantage

- Highly resistant to extreme outliers.
- Represents the typical absolute prediction error.
- Uses the same unit as the target.
- Useful when the error distribution is strongly skewed.

## When to use

- Datasets containing outliers.
- Robust model comparison.
- When typical-case performance matters.
- When a few extreme errors should not dominate the summary metric.

## When not to use

- When rare but severe errors are critical.
- When total or average error cost matters.
- As the only model-performance metric.
- When tail-risk behavior must be monitored.

## What to observe

- MedAE is often lower than MAE when the error distribution has large outliers.
- A large difference between MedAE and MAE indicates skewed or heavy-tailed errors.
- A low MedAE can coexist with a few unacceptable failures.
- Always inspect upper error percentiles such as the 90th, 95th, or 99th percentile.

## Action based on the score

- **Low MedAE and high MAE/RMSE:** Most predictions are good, but some errors are severe. Investigate tail cases.
- **High MedAE:** The model performs poorly for a large portion of observations.
- **MedAE improves but RMSE worsens:** Typical performance improved while extreme failures became worse.
- **Large subgroup differences:** Investigate whether one population receives systematically worse predictions.
- **Critical application:** Add maximum-error and percentile-error monitoring.

---

# 11. Mean Squared Error — MSE

## Definition

\[
MSE
=
\frac{1}{n}
\sum_{i=1}^{n}
(y_i-\hat{y}_i)^2
\]

## Advantage

- Strongly penalizes large errors.
- Smooth and differentiable, making it useful as a training loss.
- Mathematically convenient for optimization.
- Closely related to maximum-likelihood estimation under Gaussian-error assumptions.

## When to use

- Large errors are much more costly than small errors.
- Training regression models with gradient-based optimization.
- Residuals are reasonably compatible with a symmetric, light-tailed distribution.
- Comparing models on the same target scale.

## When not to use

- When strong outliers are caused by noise or measurement errors.
- When interpretability in the original target unit is required.
- When comparing problems with different target scales.
- When error cost grows linearly rather than quadratically.

## What to observe

- MSE cannot be negative.
- MSE equals zero only for perfect predictions.
- Its unit is the square of the target unit.
- A small number of large errors can dominate the result.
- Compare MSE with MAE and inspect residual outliers.

## Action based on the score

- **High MSE with moderate MAE:** Large errors are dominating. Inspect extreme residuals.
- **High train and test MSE:** The model may be underfitting or features may be weak.
- **Low train MSE and high test MSE:** The model is likely overfitting.
- **MSE is unstable across folds:** Investigate outliers, small sample size, or distribution shift.
- **Large-error penalty is not appropriate:** Train or evaluate with MAE, Huber loss, or another robust metric.

---

# 12. Root Mean Squared Error — RMSE

## Definition

\[
RMSE
=
\sqrt{
\frac{1}{n}
\sum_{i=1}^{n}
(y_i-\hat{y}_i)^2
}
\]

## Advantage

- Expressed in the original target unit.
- Penalizes large errors more strongly than MAE.
- Easy to compare with the natural scale of the target.
- Widely used in engineering, forecasting, and machine learning.

## When to use

- Large errors should receive stronger penalties.
- Error interpretation in target units is required.
- Comparing models on the same dataset and target.
- Residual errors are approximately symmetric and extreme outliers are meaningful.

## When not to use

- When outliers are unreliable or caused by data corruption.
- When typical error matters more than large-error sensitivity.
- When comparing targets with different units or scales.
- As the only metric for skewed or heavy-tailed error distributions.

## What to observe

- RMSE cannot be negative.
- RMSE of zero means perfect prediction.
- RMSE is always greater than or equal to MAE.
- A large RMSE-to-MAE gap indicates large residuals or outliers.

A useful diagnostic ratio is:

\[
\text{Error concentration ratio}
=
\frac{RMSE}{MAE}
\]

This ratio is not a universal performance score, but a larger value generally signals a more uneven error distribution.

## Action based on the score

- **RMSE is close to MAE:** Errors are relatively consistent.
- **RMSE is much greater than MAE:** Investigate large prediction failures.
- **Test RMSE is much greater than train RMSE:** Apply regularization, simplify the model, or collect more representative data.
- **RMSE exceeds the domain tolerance:** Improve the model or reject deployment.
- **RMSE varies strongly across folds:** Use repeated cross-validation and inspect data subsets.

---

# 13. How to Read Multiple Metrics Together

A single metric is rarely sufficient.

## Pattern 1: High R², high RMSE

The model captures the overall trend but still makes large errors in target units.

### Recommended action

- Inspect residuals.
- Check high-value target cases.
- Compare RMSE with operational tolerance.
- Consider target transformation or weighted training.

## Pattern 2: Low MAE, high RMSE

Most predictions are reasonable, but a small number of errors are very large.

### Recommended action

- Inspect the largest residuals.
- Check data quality and outliers.
- Consider robust training or specialized handling for rare cases.
- Monitor 95th- and 99th-percentile absolute errors.

## Pattern 3: High explained variance, lower R²

The model captures variation but may have systematic offset or bias.

### Recommended action

- Calculate mean residual.
- Plot actual versus predicted values.
- Check calibration.
- Correct systematic bias only after confirming it on validation data.

## Pattern 4: Good MAPE, poor MAE

Relative error is acceptable, but absolute errors are too large for high-value cases.

### Recommended action

- Evaluate high-target observations separately.
- Add MAE or RMSE thresholds.
- Consider weighted metrics or cost-sensitive training.

## Pattern 5: Good MAE, MASE greater than 1

The numerical error appears small, but the model performs worse than a naive forecast.

### Recommended action

- Use the naive forecast instead unless the model provides another important benefit.
- Improve temporal features.
- Verify the MASE denominator and seasonal period.

## Pattern 6: High train scores, weak test scores

The model does not generalize.

### Recommended action

- Reduce model complexity.
- Add regularization.
- Use cross-validation.
- Remove leakage.
- Collect more representative training data.
- Review train-test distribution differences.

---

# 14. Score Monitoring and Decision Framework

## Step 1: Establish a baseline

Examples:

- Predict the training-target mean.
- Predict the training-target median.
- Use the previous observation for time-series data.
- Use a seasonal-naive forecast.

A complex model should provide meaningful improvement over a simple baseline.

## Step 2: Define practical thresholds

Do not label a score as “good” without domain context.

Example:

```text
Acceptable MAE: less than 2.0 units
Maximum RMSE: less than 4.0 units
Minimum test R²: greater than 0.80
Maximum subgroup MAE gap: less than 0.5 units
```

Thresholds should come from scientific tolerance, business cost, safety limits, or experimental requirements.

## Step 3: Compare training and test performance

| Observation | Likely problem | Action |
|---|---|---|
| Train good, test poor | Overfitting | Regularize, simplify, collect more data |
| Train poor, test poor | Underfitting | Improve features or model capacity |
| Both good | Promising model | Check robustness and deployment conditions |
| Test unexpectedly better | Easy test split or random variation | Recheck splitting and repeat evaluation |

## Step 4: Monitor residual behavior

Check:

- Mean residual
- Residual standard deviation
- Residual histogram
- Residuals versus predicted values
- Residuals versus each important feature
- Maximum absolute error
- 90th, 95th, and 99th percentile absolute errors
- Error by subgroup, range, time, or experimental condition

## Step 5: Take model actions

### Improve data when

- Important ranges have few samples.
- Measurement noise is high.
- Labels are inconsistent.
- Train and test distributions differ.
- Missing values are handled incorrectly.

### Improve features when

- Both train and test scores are weak.
- Residuals correlate with an existing input.
- Important physical relationships are not represented.
- Nonlinear transformations may describe the process better.

### Improve the model when

- Current model capacity is insufficient.
- Residual patterns show nonlinearity.
- Interactions are not captured.
- Different regularization strengths improve validation performance.

### Reject deployment when

- Test error exceeds the domain threshold.
- Performance is unstable across folds.
- Critical subgroups have unacceptable error.
- Rare large errors create safety or financial risk.
- The model does not consistently beat a simple baseline.

---

# 15. Custom Implementation Checklist

Each notebook should test the following conditions.

## Input validation

```python
assert len(y_true) == len(y_pred)
assert len(y_true) > 0
assert np.all(np.isfinite(y_true))
assert np.all(np.isfinite(y_pred))
```

## Shape validation

Convert inputs carefully:

```python
y_true = np.asarray(y_true, dtype=float).reshape(-1)
y_pred = np.asarray(y_pred, dtype=float).reshape(-1)
```

This prevents accidental broadcasting between shapes such as `(n,)` and `(n, 1)`.

## Edge cases to test

- Perfect predictions
- Constant actual target
- Constant predictions
- One observation
- Zero actual values
- Negative actual values
- Very large outlier
- NaN or infinite values
- Different input lengths
- MASE denominator equal to zero
- Adjusted R² with \(n \le p+1\)

## Numerical comparison

```python
np.isclose(custom_score, library_score, rtol=1e-9, atol=1e-12)
```

## Test example

```python
y_true = np.array([3.0, 5.0, 2.5, 7.0])
y_pred = np.array([2.5, 5.5, 2.0, 8.0])
```

For every metric:

1. Calculate by hand for a very small example.
2. Calculate using the custom function.
3. Calculate using the trusted library.
4. Compare the three results.
5. Test edge cases.
6. Explain why the result changes when one prediction becomes an outlier.

---

# 16. Recommended Notebook Structure

Use the same structure in every notebook.

```markdown
# Metric Name

## 1. Objective
## 2. Mathematical Formula
## 3. Meaning of Each Symbol
## 4. Manual Calculation
## 5. Custom Python Implementation
## 6. Library Implementation
## 7. Result Comparison
## 8. Advantages
## 9. When to Use
## 10. When Not to Use
## 11. How to Interpret the Score
## 12. Actions Based on the Score
## 13. Edge Cases
## 14. Final Observation
```

## Suggested final observation template

```markdown
## Final Observation

The custom implementation produced the same result as the trusted library
within numerical tolerance. This confirms that the mathematical logic was
implemented correctly for the tested cases. However, the metric should not be
interpreted alone. Its value must be compared with a baseline, domain-specific
error tolerance, train-test performance, residual distribution, and other
complementary regression metrics.
```

---

# 17. Recommended Metric Combinations

## General regression

Use:

- R²
- MAE
- RMSE
- Residual analysis

## Regression with strong outliers

Use:

- MAE
- Median Absolute Error
- RMSE
- Error percentiles

## Time-series forecasting

Use:

- MASE
- MAE
- RMSE
- Horizon-specific evaluation

Use MAPE only when actual values are safely nonzero and percentage error is meaningful.

## Feature comparison in multiple linear regression

Use:

- Adjusted R²
- Cross-validated MAE or RMSE
- Residual analysis
- Statistical and domain reasoning

## High-risk applications

Use:

- MAE
- RMSE
- Maximum absolute error
- High-percentile errors
- Subgroup error
- Calibration and residual diagnostics
- Domain-specific acceptance thresholds

---

# 18. Final Guidance

No regression metric independently proves that a model is reliable.

A strong evaluation should answer five separate questions:

1. **Does the model beat a simple baseline?**
2. **What is the typical prediction error?**
3. **How severe are the largest errors?**
4. **Does performance generalize to unseen data?**
5. **Is the error acceptable for the real scientific, engineering, or business application?**

Use R² and explained variance to understand goodness of fit, MAE and Median AE to understand typical error, MSE and RMSE to expose large errors, MAPE for valid percentage-based cases, and MASE for forecast performance relative to a naive baseline.

The final decision should be based on several metrics, residual diagnostics, cross-validation stability, baseline comparison, and domain-specific tolerance.