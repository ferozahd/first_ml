**MACHINE LEARNING MODEL  
SELECTION HANDBOOK**

When to choose each model, why it fits, advantages, disadvantages, and why alternatives may be worse

**Regression - Classification - Clustering - Time-Series Forecasting**

Prepared as a practical study and project reference  
July 2026

# Contents

- 1\. First principles: how to choose a model

- 2\. Validation and metric rules

- 3\. Quick model-selection matrix

- 4\. Regression models

- 5\. Classification models

- 6\. Clustering model

- 7\. Time-series forecasting models

- 8\. Scenario-based recommendations

- 9\. Reusable experiment templates

- 10\. Recommended books and official references

Important correction: the items under "Time-Series Project Names" are possible notebook, repository, or report titles. They are not forecasting algorithms and therefore should not be compared with ARIMA, Prophet, LSTM, or other models.

## How to use this handbook

1.  Start with the prediction task: numerical target, class label, unlabeled grouping, or chronological forecast.

2.  Use the quick matrix to shortlist two to four sensible candidates.

3.  Build a simple baseline first. A complex model that barely beats a baseline is often not worth its cost.

4.  Evaluate with the correct split strategy and metric. The test protocol is more important than the brand name of the algorithm.

5.  Select the simplest model that meets accuracy, interpretability, latency, memory, and maintenance requirements.

# 1. First Principles: How to Choose a Model

## The decision starts with the target

| **Task**       | **Target**                       | **Examples**                                        |
|----------------|----------------------------------|-----------------------------------------------------|
| Regression     | A continuous numerical value     | Price, temperature, demand, revenue, duration       |
| Classification | A discrete class or probability  | Fraud/not fraud, disease class, churn probability   |
| Clustering     | No target label; discover groups | Customer segments, behavior groups, document groups |
| Time series    | A future value indexed by time   | Next-day demand, monthly sales, hourly load         |

## The six questions that eliminate most wrong choices

**1. Is the relationship approximately linear?:** If yes, start with LinearRegression or LogisticRegression. If not, tree ensembles, kernels, or neural networks may be needed.

**2. How much data is available?:** Kernel SVMs and deep recurrent networks can become expensive. Trees and linear models are often stronger on small-to-medium tabular datasets.

**3. Is interpretability mandatory?:** Linear/logistic models and shallow trees are easier to explain. Ensembles and neural networks usually need post-hoc interpretation.

**4. Are features on different scales?:** KNN, SVC, SVR, logistic regression, and neural networks usually require scaling. Decision trees and tree ensembles generally do not.

**5. Is the data chronological?:** Never randomly shuffle future observations into training. Use rolling-origin or expanding-window validation.

**6. What error is expensive?:** Choose the metric and threshold around business cost: false negatives, false positives, large numerical misses, or systematic bias.

## Bias, variance, and model complexity

- High bias: the model is too simple and underfits. Example: a straight line for a strongly curved relationship.

- High variance: the model learns noise and changes sharply with new training samples. Example: an unrestricted decision tree.

- Bagging methods such as Random Forest mainly reduce variance by averaging many trees.

- Boosting methods such as Gradient Boosting and XGBoost mainly reduce systematic errors by adding trees sequentially.

- Regularization, pruning, early stopping, and cross-validation are controls for complexity - not optional decoration.

## Baseline rule

For every project, compare against a trivial baseline: mean/median for regression, majority class for classification, naive or seasonal-naive forecast for time series. If a sophisticated model does not clearly beat the baseline on unseen data, do not deploy it.

# 2. Validation and Metric Rules

## Correct splitting

- Independent tabular rows: train/validation/test split or cross-validation. Use stratification for classification when possible.

- Grouped data: use group-aware splitting so the same patient, customer, machine, or location does not appear in both train and validation sets.

- Time series: preserve order. Use walk-forward, expanding-window, or rolling-window validation.

- Preprocessing must be fitted only on training folds. Put scaling, imputation, and encoding inside a Pipeline to prevent leakage.

## Metric chooser

| **Task**       | **Metric**        | **Interpretation**                                                                |
|----------------|-------------------|-----------------------------------------------------------------------------------|
| Regression     | MAE               | Typical absolute error; robust and easy to explain.                               |
| Regression     | RMSE              | Penalizes large errors more heavily.                                              |
| Regression     | R2                | Relative variance explained; not an error in original units.                      |
| Classification | Precision         | Use when false positives are expensive.                                           |
| Classification | Recall            | Use when false negatives are expensive.                                           |
| Classification | F1                | Balances precision and recall.                                                    |
| Classification | PR-AUC            | Often more informative for rare positive classes.                                 |
| Classification | ROC-AUC           | Ranking quality across thresholds; can look optimistic with severe imbalance.     |
| Clustering     | Silhouette        | Compactness versus separation; useful but not a substitute for domain validation. |
| Time series    | MAE / RMSE / MASE | Use out-of-time forecasts; MASE enables scale-free comparison.                    |

## Common leakage traps

- Scaling or imputing before the train/test split.

- Creating target statistics or features using future observations.

- Randomly splitting repeated records from the same entity.

- Selecting features using the entire dataset before cross-validation.

- Evaluating time-series models on randomly shuffled timestamps.

- Tuning hyperparameters on the final test set.

# 3. Quick Model-Selection Matrix

| **Family**           | **Best use**                                     | **Cost**                       | **Interpretability**              | **Scale?**                       | **Main risk**                              |
|----------------------|--------------------------------------------------|--------------------------------|-----------------------------------|----------------------------------|--------------------------------------------|
| Linear / Logistic    | Linear signal, strong baseline, interpretability | Low                            | High                              | Yes                              | Weak on complex nonlinear interactions     |
| Single Decision Tree | Rules and nonlinear splits                       | Low-Medium                     | High if shallow                   | No                               | Unstable and easy to overfit               |
| Random Forest        | Reliable nonlinear tabular baseline              | Medium                         | Medium                            | No                               | Large and weaker extrapolation             |
| SVR / SVC            | Small-medium scaled datasets; clear margin       | Medium-High                    | Low-Medium                        | Yes                              | Training scales poorly                     |
| KNN                  | Local similarity and simple low-dimensional data | Low training / high prediction | Medium                            | Yes                              | Poor in high dimensions                    |
| GaussianNB           | Very fast probabilistic baseline                 | Very low                       | Medium                            | Usually helpful                  | Independence/Gaussian assumptions          |
| Gradient Boosting    | High tabular accuracy with controlled boosting   | Medium-High                    | Low-Medium                        | No                               | Sensitive to tuning/noise                  |
| XGBoost              | Strong production-grade boosted trees            | High                           | Low-Medium                        | No                               | More tuning and complexity                 |
| KMeans               | Compact spherical clusters                       | Low-Medium                     | Centroids are interpretable       | Yes                              | Must choose k; sensitive to scale/outliers |
| ARIMA / SARIMA       | Autocorrelation-driven univariate series         | Medium                         | High statistical interpretability | Scale not relevant               | Needs stationarity treatment               |
| Holt-Winters / ETS   | Level, trend, and seasonality                    | Low-Medium                     | High                              | Scale not relevant               | Limited external regressors                |
| Prophet              | Business series with holidays/changepoints       | Medium                         | High component interpretability   | Scale not relevant               | Not automatically best                     |
| VAR                  | Several interacting stationary series            | High as variables/lags grow    | Medium                            | Often standardized for stability | Parameter explosion                        |
| LSTM / GRU           | Large sequential data and nonlinear dependencies | High                           | Low                               | Yes                              | Data-hungry and costly                     |

This table is a shortlist generator, not a substitute for validation. A model should win on unseen data and operational constraints, not on reputation.

# 4. Regression Models

Use these when the target is a continuous number. Compare models with MAE/RMSE and residual analysis, not only R2.

# LinearRegression

The first model to try when a continuous target can be explained by an additive linear relationship.

**Core idea:** Fits coefficients so that the weighted sum of features minimizes squared prediction error. Each coefficient represents the expected target change for a one-unit feature change, holding other features constant.

## Choose this model when

- You need a transparent baseline and coefficients that can be explained to stakeholders.

- The relationship is approximately linear after sensible transformations or feature engineering.

- The dataset has more observations than effective parameters and multicollinearity is manageable.

- Prediction speed, simplicity, and stable deployment matter more than squeezing out the final percentage of accuracy.

## Why it is a strong choice

- Fast to train and predict, even with many rows.

- Coefficients provide a direct global explanation of direction and magnitude.

- Works well when the true signal is mostly additive and linear.

- Provides a strong diagnostic baseline: residual patterns reveal nonlinearity, missing interactions, outliers, and heteroscedasticity.

## Main disadvantages and failure modes

- Cannot discover nonlinear thresholds or interactions unless you explicitly create transformed features.

- Ordinary least squares is sensitive to strong outliers because errors are squared.

- Correlated predictors can make coefficients unstable even when predictions remain acceptable.

- It extrapolates linearly outside the observed range, which can be unrealistic.

## Why not the closest alternatives?

- Use DecisionTreeRegressor when clear threshold rules and interactions dominate, but accept higher variance.

- Use RandomForestRegressor or boosting when nonlinear tabular accuracy matters more than coefficient-level explanation.

- Use SVR when the dataset is small-to-medium and a smooth nonlinear boundary is plausible after scaling.

- Do not replace a well-performing linear model with XGBoost merely because XGBoost is more sophisticated; added complexity needs measurable value.

## Data preparation and validation

- Impute missing values and encode categorical variables. One-hot encoding is common.

- Scaling is not mathematically required for ordinary least squares, but helps when polynomial terms, regularized variants, or numerical conditioning are involved.

- Inspect residuals versus fitted values, residual distribution, leverage points, and performance by subgroup.

- Use Ridge, Lasso, or Elastic Net when coefficients are unstable or the feature count is large.

## Most important controls

| **Parameter / decision** | **What it controls**                                                                                |
|--------------------------|-----------------------------------------------------------------------------------------------------|
| fit_intercept            | Whether the model estimates a constant offset.                                                      |
| positive                 | Restricts coefficients to non-negative values when domain logic requires it.                        |
| Feature transformations  | Often more important than estimator parameters: logs, polynomials, interactions, and domain ratios. |

## Practical decision rule

Choose LinearRegression first when interpretability matters or when a scatter/residual analysis suggests a mostly linear signal. Keep it if cross-validated error is close to complex models; simplicity is then a competitive advantage.

## Typical applications

House price with mostly additive features; Energy consumption versus weather variables; Manufacturing yield prediction; Marketing spend response baseline.

**Reference tags:** R1, R2

# DecisionTreeRegressor

A rule-based nonlinear model that partitions the feature space into regions and predicts a constant value in each region.

**Core idea:** Repeatedly splits the data using feature thresholds that reduce prediction impurity. A final leaf predicts the average or another criterion-based value of training targets in that leaf.

## Choose this model when

- You expect threshold effects such as "when temperature exceeds 30, demand changes sharply."

- You need an understandable rule structure and can keep the tree shallow.

- Features interact in ways that are hard to express with linear formulas.

- You need a fast prototype that handles mixed scales without standardization.

## Why it is a strong choice

- Captures nonlinear relationships and feature interactions automatically.

- Does not require feature scaling and is invariant to monotonic transformations of individual features.

- A shallow tree can be visualized as explicit decision rules.

- Can handle unusual target shapes without assuming normal errors or linearity.

## Main disadvantages and failure modes

- An unrestricted tree usually overfits and has high variance.

- Small data changes can produce a very different tree and different rules.

- Predictions are piecewise constant and cannot smoothly interpolate or extrapolate.

- Greedy splitting may miss globally better structures.

## Why not the closest alternatives?

- RandomForestRegressor is usually more accurate and stable because it averages many decorrelated trees, but it sacrifices a single simple rule set.

- Gradient boosting and XGBoost often produce better accuracy by correcting residual errors sequentially, but require more tuning.

- LinearRegression is preferable when the signal is truly linear and stable extrapolation is necessary.

- Use a single tree mainly for interpretability, teaching, rules, or as a base learner - not as the default accuracy champion.

## Data preparation and validation

- No scaling is required, but missing values and categorical variables need library-appropriate handling.

- Tune complexity with maximum depth, minimum leaf size, maximum leaf count, or cost-complexity pruning.

- Use cross-validation because a single split can give a misleading estimate for an unstable tree.

- Inspect leaf sample counts; leaves with very few samples are usually unreliable.

## Most important controls

| **Parameter / decision** | **What it controls**                                     |
|--------------------------|----------------------------------------------------------|
| max_depth                | Primary control on tree complexity and interpretability. |
| min_samples_leaf         | Prevents tiny leaves and smooths predictions.            |
| max_leaf_nodes           | Limits the total number of terminal regions.             |
| ccp_alpha                | Cost-complexity pruning strength.                        |
| criterion                | Defines how split quality is measured.                   |

## Practical decision rule

Choose a shallow DecisionTreeRegressor when you need explicit threshold rules. For general predictive performance, use it as a baseline and expect Random Forest or boosting to outperform it.

## Typical applications

Pricing rules; Simple risk scorecards; Agricultural yield thresholds; Operational decision policies.

**Reference tags:** R1, R3

# RandomForestRegressor

A robust nonlinear tabular baseline that averages many randomized decision trees.

**Core idea:** Builds many trees on bootstrapped samples and random feature subsets, then averages their predictions. Averaging reduces the variance of individual trees.

## Choose this model when

- You have tabular data with nonlinear effects and interactions but limited time for tuning.

- You need a dependable model that is less fragile than one decision tree.

- The dataset is small-to-large enough for tree ensembles and prediction latency is acceptable.

- You want feature-importance tools and partial dependence without demanding a single equation.

## Why it is a strong choice

- Strong out-of-the-box performance on many tabular problems.

- Less sensitive to scaling, monotonic transformations, and moderate outliers than distance-based models.

- Handles nonlinearities and interactions automatically.

- Parallelizable across trees and usually less tuning-sensitive than boosting.

## Main disadvantages and failure modes

- Large forests consume memory and can be slower at prediction than linear models.

- Cannot extrapolate target trends beyond the range represented in leaves.

- Impurity-based feature importance can be misleading for high-cardinality or correlated features; permutation importance is safer.

- May underperform well-tuned boosted trees when subtle additive corrections matter.

## Why not the closest alternatives?

- Choose a single tree only when a compact rule set is a hard requirement.

- Choose GradientBoostingRegressor or XGBRegressor when maximum tabular accuracy justifies tuning, early stopping, and more careful regularization.

- Choose LinearRegression when effects are linear and global coefficients are required.

- Choose SVR for smaller, well-scaled datasets with smooth nonlinear structure; Random Forest is usually easier on mixed tabular features.

## Data preparation and validation

- Scaling is unnecessary. Encode categorical features and handle missing values according to the implementation used.

- Use enough trees for stable validation error; additional trees usually increase cost rather than overfitting severely.

- Control individual tree depth and leaf size to prevent overly noisy partitions.

- Validate with group-aware or time-aware splits when observations are dependent.

## Most important controls

| **Parameter / decision** | **What it controls**                                             |
|--------------------------|------------------------------------------------------------------|
| n_estimators             | Number of trees; more improves stability until returns diminish. |
| max_depth                | Limits each tree complexity.                                     |
| min_samples_leaf         | Smooths leaf predictions and improves robustness.                |
| max_features             | Controls tree diversity versus individual tree strength.         |
| max_samples / bootstrap  | Controls row sampling per tree.                                  |
| n_jobs                   | Parallel computation across trees.                               |

## Practical decision rule

Use RandomForestRegressor as the default nonlinear benchmark for tabular regression. Move to boosting only when cross-validation shows a material gain that justifies extra tuning and operational complexity.

## Typical applications

Customer lifetime value; Equipment remaining-life baseline; Property valuation; Insurance severity estimation.

**Reference tags:** R1, R4

# SVR

A margin-based regressor that can fit smooth nonlinear functions through kernels.

**Core idea:** Finds a function that keeps most errors within an epsilon-insensitive tube while controlling model complexity. Kernels allow linear separation in an implicit higher-dimensional space.

## Choose this model when

- The dataset is small-to-medium rather than massive.

- Features can be carefully scaled and mostly numeric.

- You expect a smooth nonlinear relationship and can tune C, epsilon, and gamma.

- Prediction accuracy matters more than easy global interpretation.

## Why it is a strong choice

- Can model complex smooth nonlinear relationships with the RBF kernel.

- The epsilon-insensitive loss ignores small errors and focuses capacity on meaningful deviations.

- Often competitive on modest datasets with clean, scaled features.

- Regularization is built directly into the optimization objective.

## Main disadvantages and failure modes

- Training time and memory can grow poorly with the number of samples.

- Highly sensitive to feature scaling and hyperparameter choices.

- Harder to explain than linear models or shallow trees.

- Kernel models can be slow at prediction when many support vectors remain.

## Why not the closest alternatives?

- Use LinearRegression if the relationship is linear; it is faster and much easier to interpret.

- Use RandomForestRegressor for mixed tabular features and less scaling sensitivity.

- Use XGBRegressor for large tabular datasets and strong interactions, especially when tree-based structure fits the domain.

- Use LinearSVR or SGD-based regression when the dataset is too large for kernel SVR.

## Data preparation and validation

- Standardize numerical features inside a Pipeline. Fit the scaler only on training folds.

- Encode categorical variables; one-hot expansion may make kernels expensive if dimensionality becomes very high.

- Use randomized search on logarithmic ranges for C and gamma.

- Inspect validation performance across dataset size; rapidly increasing fit time is a warning sign.

## Most important controls

| **Parameter / decision** | **What it controls**                                                    |
|--------------------------|-------------------------------------------------------------------------|
| kernel                   | RBF is a common nonlinear default; linear is simpler and faster.        |
| C                        | Penalty for errors; high values fit training data more aggressively.    |
| epsilon                  | Width of the no-penalty tube around predictions.                        |
| gamma                    | RBF influence radius; high values create very local, complex functions. |
| cache_size               | Memory allocated for kernel computation.                                |

## Practical decision rule

Choose SVR for a scaled, modest-sized numerical dataset where smooth nonlinear structure is likely. Avoid it as a first choice for very large datasets or heavily mixed categorical tables.

## Typical applications

Material-property prediction; Small sensor calibration datasets; Chemical concentration estimation; Nonlinear engineering response surfaces.

**Reference tags:** R1, R5

# GradientBoostingRegressor

A sequential tree ensemble that improves predictions by fitting each new tree to current errors.

**Core idea:** Starts with a simple prediction and adds shallow trees stage by stage. Each tree follows the gradient of a chosen loss, allowing the ensemble to correct systematic residual patterns.

## Choose this model when

- You need strong tabular accuracy and can tune learning rate, tree size, and number of stages.

- The signal contains nonlinear interactions that a single tree or linear model misses.

- The dataset is not so large that classic gradient boosting becomes prohibitively slow.

- You want loss functions such as squared, absolute, Huber, or quantile regression.

## Why it is a strong choice

- Often more accurate than a single tree or Random Forest on structured tabular data.

- Can optimize different losses, including robust and quantile objectives.

- Shallow trees produce additive corrections that model complex functions efficiently.

- Feature scaling is generally unnecessary.

## Main disadvantages and failure modes

- Sequential fitting is less parallelizable than Random Forest.

- Can overfit noise if trees are deep, the learning rate is high, or too many stages are used.

- Requires more tuning and careful validation than Random Forest.

- Classic implementations can be slow on very large datasets compared with histogram-based boosters.

## Why not the closest alternatives?

- RandomForestRegressor is preferable when you need a robust low-tuning benchmark or heavy parallelism.

- XGBRegressor is often better for large, sparse, or production-tuned workloads and provides stronger regularization/engineering features.

- LinearRegression is preferable when interpretability and stable extrapolation dominate.

- DecisionTreeRegressor is preferable only when a single compact rule set is required.

## Data preparation and validation

- No feature scaling is required for tree splits.

- Use shallow base trees and a smaller learning rate with more estimators.

- Use validation-based early stopping when the implementation supports it; otherwise tune stages explicitly.

- Inspect residuals and subgroup performance; boosted models can fit spurious patterns in noisy data.

## Most important controls

| **Parameter / decision**   | **What it controls**                                              |
|----------------------------|-------------------------------------------------------------------|
| n_estimators               | Number of boosting stages.                                        |
| learning_rate              | Contribution of each tree; lower values usually need more trees.  |
| max_depth / max_leaf_nodes | Complexity of each weak learner.                                  |
| subsample                  | Below 1.0 introduces stochastic boosting and can reduce variance. |
| loss                       | Squared, absolute, Huber, or quantile objective.                  |
| min_samples_leaf           | Regularizes leaf estimates.                                       |

## Practical decision rule

Choose GradientBoostingRegressor when a Random Forest baseline is good but you need higher accuracy and can invest in tuning. Keep trees shallow and use conservative learning rates.

## Typical applications

Demand prediction; Credit loss severity; Web conversion value; Nonlinear process optimization.

**Reference tags:** R1, R4

# XGBRegressor

A highly optimized, regularized gradient-boosted tree model for demanding tabular regression.

**Core idea:** Builds additive trees using gradient and second-order information, with explicit regularization, row/column subsampling, missing-value routing, and efficient training algorithms.

## Choose this model when

- You want top-tier performance on structured tabular data and can tune carefully.

- The dataset is medium-to-large, sparse, or contains many interacting predictors.

- You need early stopping, regularization, weighted observations, and production-oriented training controls.

- Accuracy gains justify additional complexity and dependency management.

## Why it is a strong choice

- Excellent performance across many tabular regression tasks.

- Rich regularization controls reduce overfitting when used correctly.

- Efficient handling of sparse matrices and missing-value split directions.

- Supports custom objectives, evaluation metrics, early stopping, and hardware acceleration depending on configuration.

## Main disadvantages and failure modes

- More hyperparameters and more ways to overfit than simpler models.

- Can produce excellent validation scores from leakage, so disciplined pipelines are essential.

- Interpretation requires SHAP, permutation importance, or other post-hoc methods.

- For tiny or simple datasets, tuning overhead may exceed any real benefit.

## Why not the closest alternatives?

- RandomForestRegressor is easier when you need a stable baseline with less tuning.

- Classic GradientBoostingRegressor is simpler for moderate datasets and instructional work.

- LinearRegression is superior when coefficients, causality-oriented interpretation, or extrapolation are central.

- SVR may be better on a small smooth numerical dataset, but scales less gracefully.

## Data preparation and validation

- Scaling is unnecessary for tree splits, but careful encoding and leakage prevention remain essential.

- Use a validation set with early stopping and retain a final untouched test set.

- Tune learning rate and number of trees together, then depth/leaves, subsampling, and regularization.

- Use time-aware validation for forecasting features; do not let lag or rolling features access the future.

## Most important controls

| **Parameter / decision** | **What it controls**                                   |
|--------------------------|--------------------------------------------------------|
| n_estimators             | Maximum boosting rounds.                               |
| learning_rate            | Shrinkage per tree.                                    |
| max_depth / max_leaves   | Tree complexity and interaction order.                 |
| min_child_weight         | Minimum child weight; higher values regularize splits. |
| subsample                | Fraction of rows per tree.                             |
| colsample_bytree         | Fraction of features per tree.                         |
| reg_alpha / reg_lambda   | L1 and L2 regularization.                              |
| early_stopping_rounds    | Stops when validation improvement stalls.              |

## Practical decision rule

Choose XGBRegressor after a clean baseline pipeline is established and boosted trees show a consistent cross-validated advantage. Use early stopping and conservative complexity; otherwise it can memorize noise.

## Typical applications

Fraud loss amount; Large-scale sales prediction; Insurance pricing; Competition-grade tabular regression.

**Reference tags:** R6, R7

# 5. Classification Models

Use these when the target is a class label or class probability. Select metrics and thresholds from the cost of false positives and false negatives.

# LogisticRegression

The default interpretable probabilistic classifier for approximately linear decision boundaries.

**Core idea:** Models the log-odds of a class as a linear combination of features and converts the score into probabilities with a logistic or multinomial link.

## Choose this model when

- You need calibrated-like probabilities, coefficients, and a defensible baseline.

- The class boundary is approximately linear in the engineered feature space.

- The data is high-dimensional and sparse, such as bag-of-words text.

- Inference speed and model compactness are important.

## Why it is a strong choice

- Fast, compact, and easy to deploy.

- Coefficients show the direction and relative strength of feature effects after considering scale and encoding.

- Regularization handles many correlated or weak features better than an unregularized linear classifier.

- Works very well for sparse text and other high-dimensional linear problems.

## Main disadvantages and failure modes

- Cannot learn nonlinear thresholds or interactions without feature engineering.

- Coefficients can be unstable under severe multicollinearity.

- Probability quality may still require calibration checks, especially with class weighting or distribution shift.

- Sensitive to scaling when regularization is used and to influential outliers in feature space.

## Why not the closest alternatives?

- Use DecisionTreeClassifier when explicit nonlinear rules are required.

- Use RandomForestClassifier or boosting when complex interactions materially improve validation performance.

- Use SVC for a small-to-medium scaled dataset with a nonlinear margin, accepting lower interpretability and potentially slower training.

- Use GaussianNB for an extremely fast generative baseline or when its distributional assumptions are plausible.

## Data preparation and validation

- Standardize continuous features for stable regularized optimization; one-hot encode categorical variables.

- Use stratified cross-validation and metrics suited to class imbalance.

- Tune the decision threshold separately from training when business costs are asymmetric.

- Inspect calibration, coefficient stability, and subgroup errors.

## Most important controls

| **Parameter / decision** | **What it controls**                                                       |
|--------------------------|----------------------------------------------------------------------------|
| C                        | Inverse regularization strength; smaller values regularize more.           |
| penalty                  | L1, L2, or elastic-net depending on solver.                                |
| solver                   | Optimization algorithm; compatibility depends on penalty and dataset size. |
| class_weight             | Reweights classes when errors have unequal importance.                     |
| max_iter                 | Increase when optimization does not converge.                              |

## Practical decision rule

Choose LogisticRegression as the first serious classifier. Keep it when it is close to complex models, especially when explanation, probability use, low latency, or sparse features matter.

## Typical applications

Customer churn; Email response prediction; Credit approval baseline; Text sentiment classification.

**Reference tags:** R1, R2

# DecisionTreeClassifier

An interpretable nonlinear classifier built from feature-threshold rules.

**Core idea:** Recursively splits examples to create increasingly pure class regions. Each leaf predicts a class distribution based on training samples reaching that leaf.

## Choose this model when

- You need human-readable if/then rules and can constrain the tree.

- The decision depends on thresholds and interactions rather than a linear boundary.

- You need a quick model that does not require feature scaling.

- You want to expose the logic used by a larger tree ensemble.

## Why it is a strong choice

- Captures nonlinear interactions with little preprocessing.

- A shallow tree is easy to visualize and explain.

- Works with mixed feature scales and monotonic transformations.

- Produces class probabilities from leaf frequencies.

## Main disadvantages and failure modes

- High variance and instability; small data changes can alter the entire tree.

- Unrestricted trees overfit, especially with noisy or high-cardinality predictors.

- Leaf probabilities can be poorly calibrated when leaves are small.

- Axis-aligned splits may need many nodes for diagonal or smooth boundaries.

## Why not the closest alternatives?

- RandomForestClassifier is usually more accurate and stable for general prediction.

- Gradient boosting or XGBoost often provides stronger class ranking and minority-class detection after tuning.

- LogisticRegression is preferable for a linear, high-dimensional, or coefficient-driven problem.

- KNN or SVC may represent smooth local boundaries better when dimensions are low and scaling is reliable.

## Data preparation and validation

- No scaling is required. Encode categorical data and handle missing values appropriately.

- Use maximum depth, minimum leaf size, or pruning to control complexity.

- Use stratified or group-aware cross-validation.

- For imbalanced data, examine class weights and metrics beyond accuracy.

## Most important controls

| **Parameter / decision** | **What it controls**                               |
|--------------------------|----------------------------------------------------|
| max_depth                | Limits rule depth and overfitting.                 |
| min_samples_leaf         | Prevents unreliable tiny leaves.                   |
| criterion                | Impurity measure such as Gini or entropy/log loss. |
| class_weight             | Adjusts the cost of class errors.                  |
| ccp_alpha                | Pruning strength.                                  |
| max_leaf_nodes           | Caps the number of terminal decisions.             |

## Practical decision rule

Choose a shallow DecisionTreeClassifier when explanation through explicit rules is the central requirement. For pure predictive performance, treat it as a baseline or base learner.

## Typical applications

Loan policy rules; Medical triage prototype; Quality-control decision rules; Customer eligibility screening.

**Reference tags:** R1, R3

# RandomForestClassifier

A strong, low-tuning nonlinear classifier that averages many randomized trees.

**Core idea:** Fits many decision trees on resampled rows and randomized feature subsets, then aggregates votes or class probabilities. Diversity plus averaging reduces variance.

## Choose this model when

- You have structured tabular data with nonlinearities and interactions.

- You need a robust benchmark without extensive hyperparameter tuning.

- Feature scaling is inconvenient or feature distributions are irregular.

- You want good accuracy plus feature-importance and partial-dependence analysis.

## Why it is a strong choice

- Reliable performance on many tabular classification tasks.

- Resistant to overfitting compared with a single deep tree.

- Naturally models interactions and nonlinear thresholds.

- Parallel training across trees and no need for standardization.

## Main disadvantages and failure modes

- Can be memory-heavy and slower at inference than linear models.

- Probability estimates may need calibration.

- Impurity feature importance is biased in some settings; use permutation-based analysis.

- May lose to tuned boosting on subtle, imbalanced, or high-accuracy tasks.

## Why not the closest alternatives?

- LogisticRegression is better for sparse high-dimensional linear data and strict interpretability.

- GradientBoostingClassifier or XGBClassifier is preferable when validation shows a material gain and tuning resources exist.

- DecisionTreeClassifier is better only when one compact rule tree is mandatory.

- SVC may excel on small scaled datasets with a smooth margin but is less scalable.

## Data preparation and validation

- No scaling required. Encode categorical variables and prevent leakage.

- Use class weights or sampling strategies for imbalance, then evaluate PR-AUC, recall, precision, and threshold behavior.

- Increase trees until validation metrics stabilize.

- Control depth and leaf size; very deep trees can create noisy class probabilities.

## Most important controls

| **Parameter / decision** | **What it controls**                    |
|--------------------------|-----------------------------------------|
| n_estimators             | Number of trees.                        |
| max_features             | Feature subsampling and tree diversity. |
| max_depth                | Maximum tree complexity.                |
| min_samples_leaf         | Leaf smoothing and regularization.      |
| class_weight             | Class-specific error weighting.         |
| max_samples              | Row subsampling per tree.               |
| n_jobs                   | Parallelism.                            |

## Practical decision rule

Use RandomForestClassifier as the default nonlinear benchmark for tabular classification. Replace it only when a simpler model is nearly as good or a tuned booster is consistently better.

## Typical applications

Churn prediction; Fraud screening baseline; Disease risk classification; Machine-failure classification.

**Reference tags:** R1, R4

# KNeighborsClassifier

A local instance-based classifier that predicts from the labels of nearby training examples.

**Core idea:** Stores the training data and, at prediction time, finds the k nearest points according to a distance metric. The neighbors vote, optionally weighted by distance.

## Choose this model when

- The dataset is small, numerical, and low-dimensional.

- Nearby observations are expected to have similar labels.

- The decision boundary is irregular but local rather than globally parametric.

- You need an intuitive baseline and prediction cost is acceptable.

## Why it is a strong choice

- Almost no training time and conceptually simple.

- Can model highly nonlinear local boundaries.

- Naturally supports multiclass classification.

- Useful for demonstrating the role of distance and neighborhood structure.

## Main disadvantages and failure modes

- Prediction can be slow because distances to training samples must be computed.

- Sensitive to feature scaling, irrelevant variables, missing values, and the distance metric.

- Performance degrades in high dimensions because distances become less informative.

- Requires storing the training data and can be sensitive to class density imbalance.

## Why not the closest alternatives?

- LogisticRegression is better when the boundary is global and approximately linear.

- SVC may create a smoother, regularized nonlinear boundary on modest datasets.

- RandomForestClassifier is usually more robust to mixed tabular variables and irrelevant features.

- GaussianNB is far faster for prediction and memory when its assumptions are acceptable.

## Data preparation and validation

- Standardize or normalize features inside a Pipeline.

- Remove irrelevant dimensions or use dimensionality reduction when appropriate.

- Tune k, distance weighting, and the distance metric by cross-validation.

- Check whether rare classes are locally overwhelmed by majority-class neighbors.

## Most important controls

| **Parameter / decision** | **What it controls**                                         |
|--------------------------|--------------------------------------------------------------|
| n_neighbors              | Neighborhood size; small k is flexible, large k is smoother. |
| weights                  | Uniform voting or distance-weighted voting.                  |
| metric / p               | Distance definition, commonly Euclidean or Manhattan.        |
| algorithm                | Neighbor-search implementation.                              |
| leaf_size                | Performance tradeoff for tree-based search structures.       |

## Practical decision rule

Choose KNN only when locality is meaningful and dimensionality is low. It is a useful baseline, but it is rarely the best production choice for large or high-dimensional data.

## Typical applications

Simple pattern recognition; Small medical measurement datasets; Prototype recommendation labels; Educational classification examples.

**Reference tags:** R1, R8

# SVC

A maximum-margin classifier with kernels for powerful nonlinear decision boundaries.

**Core idea:** Finds a separating boundary with the largest margin between classes. Kernels allow nonlinear separation using similarities rather than explicit feature expansion.

## Choose this model when

- The dataset is small-to-medium and features can be scaled accurately.

- Classes are separated by a clear margin, possibly nonlinear.

- You have many features but not an enormous number of samples.

- High accuracy is more important than transparent coefficients or fast large-scale training.

## Why it is a strong choice

- Powerful in high-dimensional spaces and with nonlinear kernels.

- Margin maximization gives strong generalization in suitable problems.

- Uses only support vectors to define the decision function.

- Flexible through linear, polynomial, RBF, and custom kernels.

## Main disadvantages and failure modes

- Training scales poorly as sample count grows.

- Requires careful scaling and tuning of C and gamma.

- Probability estimates require additional calibration work and computation.

- Hard to interpret globally, and prediction can slow when many support vectors are retained.

## Why not the closest alternatives?

- LogisticRegression is faster, more interpretable, and often excellent for linear sparse data.

- RandomForestClassifier is easier for mixed tabular features and larger datasets.

- XGBClassifier is usually the stronger choice for large structured tabular datasets.

- KNN is simpler for very small local-neighborhood problems but often less regularized.

## Data preparation and validation

- Standardize features within cross-validation.

- Tune C and gamma on logarithmic scales. Start with RBF and a linear baseline.

- Use class weights for imbalance and evaluate decision thresholds carefully.

- Consider LinearSVC or SGDClassifier for very large linear problems.

## Most important controls

| **Parameter / decision** | **What it controls**                                       |
|--------------------------|------------------------------------------------------------|
| kernel                   | Boundary family: linear, RBF, polynomial, or custom.       |
| C                        | Misclassification penalty versus margin width.             |
| gamma                    | RBF/polynomial locality and complexity.                    |
| class_weight             | Class-specific penalty.                                    |
| probability              | Enables probability estimates but increases training cost. |
| degree                   | Polynomial-kernel degree.                                  |

## Practical decision rule

Choose SVC for a scaled, modest-sized dataset where a strong margin exists and nonlinear kernels are plausible. Avoid it for very large sample counts unless using a linear approximation.

## Typical applications

Image-feature classification; Bioinformatics with modest samples; Handwriting feature classification; Small industrial defect datasets.

**Reference tags:** R1, R5

# GaussianNB

An extremely fast probabilistic classifier assuming conditionally independent Gaussian features within each class.

**Core idea:** Estimates a Gaussian distribution for every feature in every class and combines feature likelihoods with class priors using Bayes rule.

## Choose this model when

- You need a very fast baseline with tiny memory requirements.

- Features are continuous and approximately Gaussian within each class after transformation.

- Conditional independence is a reasonable approximation or sample size is very small.

- You need incremental learning support in suitable workflows.

## Why it is a strong choice

- Very fast training and prediction.

- Works surprisingly well even when independence is imperfect, especially as a baseline.

- Naturally produces class probabilities.

- Requires little data to estimate simple per-class statistics.

## Main disadvantages and failure modes

- Correlated features can cause evidence to be counted repeatedly.

- Gaussian assumptions may be poor for skewed, bounded, multimodal, or categorical features.

- Decision boundaries are constrained by the distributional assumptions.

- Probability estimates can be overconfident when assumptions fail.

## Why not the closest alternatives?

- LogisticRegression is usually stronger when a discriminative linear boundary can be estimated reliably.

- RandomForestClassifier is better for nonlinear interactions and mixed distributions.

- SVC can model more flexible boundaries on scaled modest datasets.

- For count/text features, MultinomialNB or ComplementNB is usually more appropriate than GaussianNB.

## Data preparation and validation

- Inspect class-conditional feature distributions; log or power transforms may improve Gaussianity.

- Scaling is not mandatory for the formula, but numerical conditioning and comparability may improve after transformation.

- Remove duplicated or strongly redundant features where possible.

- Check probability calibration and class priors, especially under imbalance.

## Most important controls

| **Parameter / decision** | **What it controls**                                             |
|--------------------------|------------------------------------------------------------------|
| var_smoothing            | Adds stability to estimated variances.                           |
| priors                   | Manually specifies class prior probabilities when justified.     |
| Feature transformation   | Often the most important design choice for Gaussian assumptions. |

## Practical decision rule

Use GaussianNB as a speed baseline or when class-conditional Gaussian independence is defensible. Do not assume it is inferior without testing; on small data, its low variance can be valuable.

## Typical applications

Simple medical measurements; Real-time lightweight classification; Sensor-state classification; Small-sample scientific data.

**Reference tags:** R1, R9

# GradientBoostingClassifier

A sequential tree classifier that adds shallow trees to correct previous classification errors.

**Core idea:** Builds an additive decision function by fitting each new tree to the gradient of a classification loss. The ensemble gradually improves difficult cases.

## Choose this model when

- You need strong tabular classification accuracy and the dataset is moderate in size.

- Nonlinear interactions matter and a Random Forest baseline leaves systematic errors.

- You can tune learning rate, tree complexity, and number of stages.

- You want probability scores from an additive boosted-tree model.

## Why it is a strong choice

- High predictive accuracy on many structured datasets.

- Models nonlinearities and interactions without scaling.

- Shallow trees can build complex boundaries through additive corrections.

- Often uses fewer trees than a large Random Forest for comparable accuracy.

## Main disadvantages and failure modes

- Sequential training is slower and less parallelizable than Random Forest.

- Can overfit noise or minority-class anomalies without regularization.

- Requires more careful tuning and early-stopping strategy.

- Classic implementation is less optimized for very large datasets than modern histogram boosters.

## Why not the closest alternatives?

- RandomForestClassifier is easier and more robust when tuning time is limited.

- XGBClassifier is preferable for larger, sparse, highly tuned, or production-oriented problems.

- LogisticRegression is better when interpretability and a linear boundary are adequate.

- DecisionTreeClassifier is preferable only for a single transparent rule system.

## Data preparation and validation

- No scaling is required. Encode features and guard against leakage.

- Use stratified validation and tune the classification threshold separately.

- Start with shallow trees, low learning rate, and enough estimators.

- Check calibration and subgroup error because high ranking performance does not guarantee reliable probabilities.

## Most important controls

| **Parameter / decision**   | **What it controls**                       |
|----------------------------|--------------------------------------------|
| n_estimators               | Boosting stages.                           |
| learning_rate              | Contribution per tree.                     |
| max_depth / max_leaf_nodes | Base-tree interaction complexity.          |
| subsample                  | Stochastic boosting and variance control.  |
| min_samples_leaf           | Leaf regularization.                       |
| loss                       | Classification loss used for optimization. |

## Practical decision rule

Choose GradientBoostingClassifier when a clean Random Forest or logistic baseline is insufficient and the dataset is not so large that a more optimized booster is required.

## Typical applications

Lead conversion; Default-risk ranking; Subscription churn; Quality defect classification.

**Reference tags:** R1, R4

# XGBClassifier

A highly optimized, regularized gradient-boosted tree classifier for high-performance tabular learning.

**Core idea:** Adds trees sequentially using first- and second-order loss information, with explicit regularization, row/feature subsampling, missing-value routing, and scalable training methods.

## Choose this model when

- You need very strong ranking or classification performance on structured data.

- The dataset is medium-to-large, sparse, imbalanced, or interaction-heavy.

- You can use early stopping and a disciplined tuning process.

- Production requirements benefit from advanced weighting, objectives, and hardware-aware training.

## Why it is a strong choice

- Frequently among the strongest choices for tabular classification.

- Handles nonlinearities, interactions, missing values, and sparse inputs effectively.

- Extensive regularization and subsampling controls.

- Supports custom metrics/objectives and early stopping.

## Main disadvantages and failure modes

- Complex tuning space and higher risk of leakage-driven false confidence.

- Probabilities may need calibration despite strong ranking metrics.

- More difficult to explain and govern than logistic regression or a shallow tree.

- Can be excessive for tiny, simple, or extremely latency-sensitive tasks.

## Why not the closest alternatives?

- RandomForestClassifier is easier when a stable benchmark is enough.

- GradientBoostingClassifier is simpler for moderate instructional or classical workflows.

- LogisticRegression is preferable for sparse linear boundaries, compact deployment, and coefficient explanations.

- SVC can be competitive on small scaled datasets but scales poorly with rows.

## Data preparation and validation

- Use stratified or group-aware validation and keep a final untouched test set.

- Use early stopping on a separate validation fold; do not tune on the test set.

- Tune learning rate/estimators first, then complexity, sampling, and regularization.

- For imbalance, consider class/sample weights and threshold selection; do not optimize accuracy alone.

## Most important controls

| **Parameter / decision** | **What it controls**                    |
|--------------------------|-----------------------------------------|
| n_estimators             | Maximum boosting rounds.                |
| learning_rate            | Shrinkage per tree.                     |
| max_depth / max_leaves   | Interaction complexity.                 |
| min_child_weight         | Regularizes small partitions.           |
| subsample                | Row sampling.                           |
| colsample_bytree         | Feature sampling.                       |
| reg_alpha / reg_lambda   | L1/L2 regularization.                   |
| scale_pos_weight         | Positive-class weighting for imbalance. |
| early_stopping_rounds    | Validation-based stopping.              |

## Practical decision rule

Choose XGBClassifier after establishing leakage-safe baselines. Deploy it when gains are stable across folds, thresholds, and subgroups - not merely because it wins one split.

## Typical applications

Fraud detection; Credit risk; Customer propensity; Large-scale tabular competitions.

**Reference tags:** R6, R7

# 6. Clustering Model

Clustering has no ground-truth target in the usual setting. Mathematical compactness must be validated against domain usefulness and stability.

# KMeans

A centroid-based clustering algorithm for compact, roughly spherical groups in a scaled numerical space.

**Core idea:** Alternates between assigning each point to the nearest centroid and moving each centroid to the mean of its assigned points, minimizing within-cluster squared distance.

## Choose this model when

- You have unlabeled numerical data and expect compact, similarly sized clusters.

- Euclidean distance is meaningful after scaling and feature engineering.

- You can choose or search for the number of clusters k.

- You need a fast, scalable segmentation baseline and interpretable cluster centroids.

## Why it is a strong choice

- Fast, simple, and scalable to many observations.

- Centroids provide an understandable profile for each cluster.

- Works well when clusters are separated, convex, and similar in variance.

- Useful for compression, prototype creation, and customer segmentation.

## Main disadvantages and failure modes

- You must specify k, and statistical indices may not match business usefulness.

- Sensitive to scale, outliers, initialization, and irrelevant features.

- Fails on non-spherical, overlapping, varying-density, or strongly unequal-size clusters.

- Always assigns every point to a cluster, even genuine noise or anomalies.

## Why not the closest alternatives?

- Use density-based clustering such as DBSCAN when irregular shapes and noise points matter.

- Use hierarchical clustering when nested relationships and a dendrogram are valuable.

- Use Gaussian Mixture Models when soft probabilistic membership and elliptical clusters are plausible.

- Within your current list, KMeans is the only clustering model, so it cannot be fairly compared with the supervised or forecasting algorithms.

## Data preparation and validation

- Standardize features, or use domain-specific weights so one variable does not dominate distance.

- Remove or cap extreme outliers and consider dimensionality reduction for noisy high-dimensional spaces.

- Run multiple initializations and inspect stability across seeds.

- Choose k using silhouette, elbow/inertia, stability, and business interpretability together.

## Most important controls

| **Parameter / decision** | **What it controls**                                    |
|--------------------------|---------------------------------------------------------|
| n_clusters               | Number of clusters k.                                   |
| init                     | Centroid initialization; k-means++ is a strong default. |
| n_init                   | Number of independent initializations.                  |
| max_iter                 | Maximum update iterations.                              |
| tol                      | Convergence tolerance.                                  |
| random_state             | Reproducible initialization.                            |

## Practical decision rule

Choose KMeans when Euclidean distance is meaningful and the expected segments are compact. Reject it when clusters have complex shapes, strong outliers, or no defensible value of k.

## Typical applications

Customer segmentation; Image color quantization; Document embedding grouping; Machine operating-mode discovery.

**Reference tags:** R1, R10

# 7. Time-Series Forecasting Models

Time-series models must be evaluated chronologically. A random train/test split is invalid because it leaks future information into model selection.

Always include naive and seasonal-naive forecasts. They are not in your list, but they are mandatory baselines because many complex forecasting models fail to beat them consistently.

# ARIMA

A classical univariate model for autocorrelation after differencing removes non-stationarity.

**Core idea:** Combines autoregressive lags (AR), differencing (I), and moving-average error terms (MA). It forecasts a series using its own past values and past forecast errors.

## Choose this model when

- You have one main time series with meaningful autocorrelation.

- After transformations/differencing, the series is approximately stationary.

- Seasonality is absent or handled separately, or you use the seasonal extension.

- You need statistical diagnostics, interpretable lag structure, and prediction intervals.

## Why it is a strong choice

- Strong classical benchmark for univariate forecasting.

- Interpretable through lag orders and residual diagnostics.

- Works with relatively limited data compared with deep learning.

- Can include exogenous regressors in ARIMAX/SARIMAX formulations.

## Main disadvantages and failure modes

- Requires careful treatment of stationarity, differencing, and order selection.

- Primarily linear in lagged values and errors, so complex nonlinear effects are missed.

- Long seasonal patterns can require many parameters or a seasonal formulation.

- Forecast quality deteriorates under structural breaks not represented in training.

## Why not the closest alternatives?

- Use SARIMA when seasonality must be modeled directly with seasonal AR/MA/differencing terms.

- Use Holt-Winters/ETS when level, trend, and seasonality are the dominant structure and autocorrelation detail is less central.

- Use Prophet when calendar effects, holidays, and trend changepoints are the main business features.

- Use LSTM/GRU only when there is enough data and nonlinear sequential structure justifies the cost.

## Data preparation and validation

- Plot the series and examine transformations, trend, seasonality, outliers, and missing periods.

- Use ACF/PACF and information criteria as guides, but select models by rolling-origin validation.

- Check residual autocorrelation with diagnostics such as Ljung-Box; residuals should resemble white noise.

- Do not randomly split timestamps. Forecast only future observations from past data.

## Most important controls

| **Parameter / decision** | **What it controls**                                                  |
|--------------------------|-----------------------------------------------------------------------|
| (p,d,q)                  | AR order, differencing order, and MA order.                           |
| trend                    | Constant or deterministic trend treatment.                            |
| exog                     | External regressors known or forecastable for the prediction horizon. |
| enforce_stationarity     | Constrains AR parameters to a stationary region.                      |
| enforce_invertibility    | Constrains MA parameters for invertibility.                           |

## Practical decision rule

Choose ARIMA when one series has stable lag dependence after differencing. Benchmark it against naive forecasts and ETS; do not choose orders solely because they fit history best.

## Typical applications

Monthly demand without strong seasonality; Interest-rate changes; Inventory consumption; Industrial sensor forecasting.

**Reference tags:** R11, R12, R13

# SARIMA

ARIMA extended with seasonal autoregressive, differencing, and moving-average components.

**Core idea:** Models both short-term dependence and repeating seasonal dependence using (p,d,q) x (P,D,Q,s), where s is the seasonal period.

## Choose this model when

- The series has a stable repeating seasonal cycle such as 12 months, 7 days, or 24 hours.

- Both non-seasonal and seasonal autocorrelation are visible.

- You need a classical, interpretable model with seasonal diagnostics.

- The seasonal period is known and enough cycles are available.

## Why it is a strong choice

- Explicitly models seasonal lag relationships.

- Provides interpretable seasonal and non-seasonal orders.

- Works with modest data compared with deep neural networks.

- Can include external regressors through SARIMAX.

## Main disadvantages and failure modes

- Order search can become expensive and unstable.

- Large or multiple seasonal periods create complexity.

- Requires careful differencing to avoid under- or over-differencing.

- Assumes seasonal structure remains broadly stable into the future.

## Why not the closest alternatives?

- Use Holt-Winters when the pattern is mainly smooth level/trend/seasonality and you want fewer lag parameters.

- Use Prophet when holidays, multiple calendar effects, and trend changepoints dominate.

- Use ARIMA when seasonality is absent or already removed.

- Use dynamic harmonic regression or neural models for multiple/complex seasonalities not captured efficiently by one seasonal period.

## Data preparation and validation

- Confirm seasonal frequency from domain knowledge, not only a periodogram.

- Use seasonal plots and seasonal ACF patterns.

- Validate with multiple forecast origins covering different seasons.

- Check residual seasonality and autocorrelation after fitting.

## Most important controls

| **Parameter / decision**  | **What it controls**                                          |
|---------------------------|---------------------------------------------------------------|
| (p,d,q)                   | Non-seasonal ARIMA orders.                                    |
| (P,D,Q,s)                 | Seasonal AR, differencing, MA, and period.                    |
| exog                      | Future-known regressors.                                      |
| trend                     | Deterministic trend specification.                            |
| Order-selection criterion | AIC/AICc/BIC guide candidate search, then validation decides. |

## Practical decision rule

Choose SARIMA when one dominant seasonal period and lag-based dynamics are stable. Prefer a simpler seasonal model when its out-of-time accuracy is comparable.

## Typical applications

Monthly retail sales; Daily call volume with weekly seasonality; Quarterly production; Hourly load with daily seasonality.

**Reference tags:** R11, R12, R13

# Holt-Winters

Triple exponential smoothing for a series with level, trend, and seasonality.

**Core idea:** Updates level, trend, and seasonal components recursively, assigning more weight to recent observations. Additive or multiplicative seasonality can be used.

## Choose this model when

- The forecast is primarily driven by smooth level, trend, and one seasonal cycle.

- You need a strong, understandable business forecasting baseline.

- The dataset is not large and recent observations should receive more influence.

- Seasonal amplitude is constant (additive) or proportional to level (multiplicative).

## Why it is a strong choice

- Fast, practical, and often surprisingly accurate for seasonal business data.

- Components are intuitive and easy to explain.

- Needs less manual lag-order selection than SARIMA.

- Works well with modest history if several seasonal cycles exist.

## Main disadvantages and failure modes

- Usually handles one main seasonal pattern rather than many complex seasonalities.

- Limited treatment of external regressors in the basic form.

- Sensitive to structural breaks and unusual events unless adjusted.

- Multiplicative forms require positive values and careful interpretation.

## Why not the closest alternatives?

- Use simple exponential smoothing when there is no trend or seasonality.

- Use Holt double exponential smoothing when trend exists but seasonality does not.

- Use SARIMA when residual autocorrelation and lag-specific structure are important.

- Use Prophet when holidays and trend changepoints must be represented explicitly.

## Data preparation and validation

- Identify the seasonal period and ensure multiple cycles are observed.

- Choose additive versus multiplicative components from how seasonal amplitude changes with the level.

- Consider damped trend to avoid unrealistic long-run trend extrapolation.

- Use rolling-origin validation and compare with seasonal-naive forecasts.

## Most important controls

| **Parameter / decision**       | **What it controls**                                                 |
|--------------------------------|----------------------------------------------------------------------|
| trend                          | None, additive, or multiplicative trend depending on implementation. |
| seasonal                       | Additive or multiplicative seasonal component.                       |
| seasonal_periods               | Known number of observations per cycle.                              |
| damped_trend                   | Gradually reduces trend extrapolation.                               |
| smoothing_level/trend/seasonal | Weights for component updates, typically optimized.                  |

## Practical decision rule

Choose Holt-Winters for one seasonal series whose structure is well described by level, trend, and seasonality. It should be among the first benchmarks for monthly, weekly, or daily business demand.

## Typical applications

Monthly sales; Seasonal tourism; Weekly store traffic; Quarterly subscriptions.

**Reference tags:** R14, R15

# Exponential Smoothing

A family of forecasting methods that update recent level, trend, and seasonal components with exponentially decaying weights.

**Core idea:** Recent observations receive more weight than older observations. The family ranges from simple exponential smoothing (level only) to Holt trend and Holt-Winters seasonal models, and can be represented through ETS error-trend-seasonality combinations.

## Choose this model when

- You need a transparent family of univariate forecasting models.

- The series is driven by changing level, optional trend, and optional seasonality.

- You want a data-efficient baseline with fast fitting.

- You need prediction intervals and model comparison across ETS structures.

## Why it is a strong choice

- Flexible family covering level-only, trend, damped trend, and seasonal series.

- Computationally efficient and easy to update as new data arrives.

- Components align with business language.

- Excellent baseline before considering more complex models.

## Main disadvantages and failure modes

- Basic forms do not directly explain effects of many external variables.

- Complex autocorrelation beyond components may remain in residuals.

- Multiple or irregular seasonalities can be difficult.

- Long-horizon forecasts may become unrealistic without damping or constraints.

## Why not the closest alternatives?

- Holt-Winters is not a separate competitor so much as a specific seasonal exponential-smoothing method.

- Use ARIMA/SARIMA when lagged dependence and residual autocorrelation are central.

- Use Prophet when holidays, calendar events, and trend changepoints must be explicit.

- Use LSTM/GRU only after classical baselines fail and enough sequential data exists.

## Data preparation and validation

- Plot level, trend, and seasonal amplitude.

- Compare simple, Holt, damped, additive-seasonal, and multiplicative-seasonal variants.

- Use out-of-time validation and information criteria where appropriate.

- Check residuals for remaining autocorrelation and changing variance.

## Most important controls

| **Parameter / decision** | **What it controls**                                       |
|--------------------------|------------------------------------------------------------|
| error form               | Additive or multiplicative error in ETS formulations.      |
| trend                    | None, additive, multiplicative, or damped where supported. |
| seasonal                 | None, additive, or multiplicative.                         |
| smoothing parameters     | Control how quickly components adapt.                      |
| initialization           | Estimated or heuristic starting components.                |

## Practical decision rule

Use exponential smoothing as a core benchmark for nearly every univariate business series. It is especially strong when the story is level + trend + seasonality rather than complex causal predictors.

## Typical applications

Short-term inventory demand; Revenue run-rate; Call-center volume; Website traffic baseline.

**Reference tags:** R14, R15, R16

# Prophet

A decomposable business forecasting model with trend changepoints, seasonality, holidays, and optional regressors.

**Core idea:** Represents the series as trend plus seasonal components, holiday/event effects, and error. Trend flexibility is controlled through changepoints and regularization.

## Choose this model when

- The series has strong calendar patterns, holidays, missing dates, or trend changes.

- Business users need interpretable trend/seasonality/holiday components.

- You want a fast modeling workflow for daily or sub-daily operational series.

- Future values of additional regressors are known or can be provided.

## Why it is a strong choice

- Convenient handling of multiple seasonalities and holiday/event calendars.

- Automatic trend changepoints with tunable flexibility.

- Component plots are easy to explain.

- Tolerates missing observations and irregular historical coverage better than some classical workflows.

## Main disadvantages and failure modes

- Not automatically more accurate than ARIMA, ETS, or simple seasonal baselines.

- Outliers can be absorbed as trend changes and inflate uncertainty.

- Long-term forecasts depend heavily on trend and changepoint assumptions.

- External regressors must be known in the future; unknown future drivers cannot be used directly.

## Why not the closest alternatives?

- Use Holt-Winters/ETS when one smooth seasonal pattern dominates and simplicity is enough.

- Use SARIMA when detailed seasonal autocorrelation is stable and diagnostically important.

- Use XGBoost with lag/rolling/calendar features when nonlinear interactions among many predictors dominate, but enforce time-safe feature generation.

- Use LSTM/GRU only when large data and complex sequence behavior justify deep learning.

## Data preparation and validation

- Create a complete timestamp frame and verify frequency, time zones, and duplicated timestamps.

- Mark abnormal shocks or closures as events, missing values, or special regressors rather than letting them distort trend.

- Tune changepoint and seasonality prior scales with time-series cross-validation.

- Choose additive versus multiplicative seasonality based on whether seasonal amplitude grows with the level.

## Most important controls

| **Parameter / decision**           | **What it controls**                                        |
|------------------------------------|-------------------------------------------------------------|
| changepoint_prior_scale            | Trend flexibility; larger values permit more trend changes. |
| seasonality_prior_scale            | Strength/flexibility of seasonal components.                |
| holidays_prior_scale               | Regularization of holiday effects.                          |
| seasonality_mode                   | Additive or multiplicative seasonality.                     |
| n_changepoints / changepoint_range | Candidate trend-change structure.                           |
| interval_width                     | Reported uncertainty interval width.                        |

## Practical decision rule

Choose Prophet for calendar-rich business series when component interpretability and workflow speed matter. Require it to beat seasonal-naive and ETS/SARIMA on rolling validation.

## Typical applications

Daily e-commerce demand; Web traffic with holidays; Call-center arrivals; Marketing lead volume.

**Reference tags:** R17, R18, R19, R20

# VAR

A multivariate autoregressive model in which every series is predicted from lags of all series.

**Core idea:** Generalizes autoregression to a vector of variables. Each variable has an equation containing lagged values of itself and the other variables.

## Choose this model when

- You have several time series that influence one another dynamically.

- All variables are measured at the same regular frequency.

- The transformed series are stationary, or a cointegration-aware extension is used.

- You need impulse-response, Granger-style predictive analysis, or joint forecasts.

## Why it is a strong choice

- Captures feedback and lagged interactions across multiple series.

- Provides a coherent system rather than separate univariate models.

- Well-established statistical diagnostics and impulse-response analysis.

- Useful when cross-series lags add predictive value.

## Main disadvantages and failure modes

- Parameter count grows rapidly with variables and lag order, requiring much data.

- Stationarity assumptions and lag selection are critical.

- Forecasts are linear and can miss nonlinear interactions.

- Collinearity and unstable regimes can make estimates unreliable.

## Why not the closest alternatives?

- Use ARIMA/SARIMA/ETS when one target series is the main concern and other series add little value.

- Use VECM rather than plain VAR in differences when non-stationary variables are cointegrated and long-run relations matter.

- Use XGBoost with lagged features when nonlinear cross-variable interactions dominate, with careful time validation.

- Use LSTM/GRU when large multivariate sequences and nonlinear memory justify deep learning.

## Data preparation and validation

- Align timestamps, frequencies, and missing periods across all series.

- Test or reason about stationarity; transform or difference where needed.

- Keep the variable count and lag order small relative to available history.

- Choose lag order using information criteria plus rolling validation, then inspect residual autocorrelation and stability.

## Most important controls

| **Parameter / decision** | **What it controls**                        |
|--------------------------|---------------------------------------------|
| lags p                   | Number of past vector observations used.    |
| trend                    | Constant/trend terms.                       |
| Variable set             | The most consequential complexity decision. |
| Lag-order criterion      | AIC, BIC, HQIC, FPE as guides.              |
| Forecast horizon         | Long horizons compound system uncertainty.  |

## Practical decision rule

Choose VAR only when cross-series lag effects are central and enough observations exist for the parameter count. Do not add variables merely because they are available.

## Typical applications

GDP, inflation, and interest-rate dynamics; Price and volume systems; Multiple energy loads; Interacting industrial sensors.

**Reference tags:** R21, R22

# LSTM

A gated recurrent neural network designed to learn nonlinear temporal dependencies and retain information over long sequences.

**Core idea:** Uses input, forget, and output gates to control information stored in a recurrent memory cell, allowing gradients and information to persist across many timesteps.

## Choose this model when

- You have large sequential datasets or many related time series.

- Nonlinear interactions, long memory, and many covariates are expected.

- You can invest in window design, tuning, regularization, and hardware.

- Classical models and strong feature-based tree baselines have been evaluated and are insufficient.

## Why it is a strong choice

- Learns nonlinear temporal representations from raw or lightly engineered sequences.

- Can combine many time-varying and static covariates.

- Handles sequence-to-one and sequence-to-sequence forecasting architectures.

- Can share patterns across many related series.

## Main disadvantages and failure modes

- Data-hungry, computationally expensive, and sensitive to tuning.

- Harder to explain and debug than classical models.

- Can underperform naive, ETS, or boosted-tree baselines on small business datasets.

- Requires careful scaling, windowing, state handling, leakage prevention, and probabilistic-forecast design.

## Why not the closest alternatives?

- Use ARIMA/SARIMA or ETS for a single modest series with stable classical structure.

- Use Prophet for calendar/holiday-rich business forecasts requiring transparent components.

- Use GRU when similar accuracy can be achieved with fewer parameters and faster training.

- Use XGBoost on lag/rolling features when tabularized sequence features are sufficient and interpretability is more important.

## Data preparation and validation

- Scale inputs using training data only and define input/output windows without crossing split boundaries.

- Use chronological validation and compare multiple forecast horizons.

- Add regularization, dropout/recurrent dropout where appropriate, early stopping, and learning-rate scheduling.

- Benchmark against naive, seasonal-naive, ETS, ARIMA, and tree-based lag models.

## Most important controls

| **Parameter / decision**    | **What it controls**                                 |
|-----------------------------|------------------------------------------------------|
| units                       | Hidden-state and memory capacity.                    |
| sequence length             | Historical context window.                           |
| layers                      | Depth of recurrent representation.                   |
| dropout / recurrent_dropout | Regularization.                                      |
| return_sequences            | Whether the layer outputs every timestep.            |
| learning rate / batch size  | Optimization stability.                              |
| forecast strategy           | Direct, recursive, multi-output, or encoder-decoder. |

## Practical decision rule

Choose LSTM only when the dataset is large enough to learn complex sequence structure and it consistently beats strong simpler baselines. Deep learning is not a substitute for correct forecasting validation.

## Typical applications

Large-scale load forecasting; Multisensor predictive maintenance; Traffic sequence forecasting; Many related retail series.

**Reference tags:** R23, R24

# GRU

A simpler gated recurrent network that often provides LSTM-like sequence modeling with fewer parameters.

**Core idea:** Uses update and reset gates to control recurrent state without a separate memory cell, reducing architectural complexity compared with LSTM.

## Choose this model when

- You need nonlinear recurrent modeling but want a lighter model than LSTM.

- Training speed, memory, or dataset size makes LSTM unnecessarily heavy.

- Long-term dependencies exist but the full LSTM memory structure is not clearly needed.

- You will compare GRU and LSTM under the same validation protocol rather than choosing by reputation.

## Why it is a strong choice

- Fewer gates and parameters than LSTM in comparable configurations.

- Often trains faster and may generalize better on medium-sized sequence datasets.

- Captures nonlinear temporal dependencies and multivariate inputs.

- Works with the same broader recurrent forecasting architectures as LSTM.

## Main disadvantages and failure modes

- Still data-hungry and difficult to interpret.

- Can underperform classical and tree-based baselines on small tabularized time series.

- Windowing, scaling, leakage, recursive error accumulation, and tuning remain challenging.

- No universal guarantee that it is better or worse than LSTM.

## Why not the closest alternatives?

- Use LSTM when empirical validation shows benefit from its separate memory cell and additional capacity.

- Use ETS/ARIMA/SARIMA for small univariate series with stable structure.

- Use Prophet for explicit holidays and trend components.

- Use XGBoost with time features when recurrent representation learning is unnecessary.

## Data preparation and validation

- Use the same chronological splits and preprocessing rules as LSTM.

- Scale inputs and define leakage-safe windows.

- Tune hidden units, layers, sequence length, dropout, learning rate, and batch size.

- Compare parameter count, training time, horizon-wise error, and stability against LSTM.

## Most important controls

| **Parameter / decision**    | **What it controls**                            |
|-----------------------------|-------------------------------------------------|
| units                       | Recurrent capacity.                             |
| sequence length             | Historical context.                             |
| layers                      | Network depth.                                  |
| dropout / recurrent_dropout | Regularization.                                 |
| reset_after                 | GRU formulation detail in some implementations. |
| learning rate / batch size  | Training dynamics.                              |
| forecast strategy           | Recursive, direct, or multi-output.             |

## Practical decision rule

Choose GRU as the first recurrent baseline when compute or data is limited. Keep it over LSTM when accuracy is similar because the simpler recurrent architecture is easier to train and operate.

## Typical applications

Medium-scale sensor forecasting; Sequence classification; Demand forecasting across products; Weather-sequence modeling.

**Reference tags:** R23, R25

# 8. Scenario-Based Recommendations

| **Situation**                                 | **Start with**                   | **Pressure-test / alternative**                                                                        |
|-----------------------------------------------|----------------------------------|--------------------------------------------------------------------------------------------------------|
| Need an interpretable regression baseline     | LinearRegression                 | Add Ridge/Lasso if coefficients are unstable; compare with a shallow tree.                             |
| Small nonlinear numerical regression          | SVR                              | Scale features and tune C/gamma; compare with Random Forest.                                           |
| General tabular regression                    | RandomForestRegressor            | Then test Gradient Boosting/XGBoost for gains.                                                         |
| Maximum tabular regression accuracy           | XGBRegressor                     | Use early stopping and strict leakage control.                                                         |
| Need an interpretable classification baseline | LogisticRegression               | Tune threshold and check calibration.                                                                  |
| Need explicit classification rules            | Shallow DecisionTreeClassifier   | Accept lower stability; prune aggressively.                                                            |
| General tabular classification                | RandomForestClassifier           | Then compare boosted trees.                                                                            |
| Rare positive class                           | Logistic/Random Forest/XGBoost   | Use PR-AUC, recall/precision, weights, and threshold tuning; algorithm alone does not solve imbalance. |
| High-dimensional sparse text                  | LogisticRegression or linear SVM | Tree ensembles are usually inefficient on raw sparse text.                                             |
| Small low-dimensional local patterns          | KNeighborsClassifier             | Scale and remove irrelevant dimensions.                                                                |
| Very fast small-data probability baseline     | GaussianNB                       | Validate assumptions and calibration.                                                                  |
| Customer segmentation with compact groups     | KMeans                           | Scale, inspect stability, and validate business actionability.                                         |
| Single nonseasonal time series                | ARIMA + ETS                      | Let rolling validation decide.                                                                         |
| Single seasonal time series                   | Holt-Winters/ETS + SARIMA        | Use Prophet when holidays/changepoints matter.                                                         |
| Calendar-heavy daily business series          | Prophet + ETS/SARIMA baseline    | Model holidays and abnormal shocks explicitly.                                                         |
| Several interacting stationary series         | VAR                              | Keep variables/lags small relative to history.                                                         |
| Many related nonlinear sequences              | GRU/LSTM                         | Only after classical and boosted lag-feature baselines.                                                |
| Limited compute for recurrent model           | GRU                              | Compare against LSTM under identical protocol.                                                         |

## A practical tournament, not a random model zoo

- Regression: baseline mean -\> LinearRegression -\> Decision Tree -\> Random Forest -\> Gradient Boosting/XGBoost -\> SVR when dataset size and feature type justify it.

- Classification: majority baseline -\> LogisticRegression/GaussianNB -\> Decision Tree -\> Random Forest -\> Gradient Boosting/XGBoost -\> SVC/KNN when geometry and dataset size justify them.

- Time series: naive -\> seasonal naive -\> ETS/Holt-Winters -\> ARIMA/SARIMA -\> Prophet when calendar structure exists -\> lag-feature boosted trees -\> LSTM/GRU only with sufficient data.

- The tournament should use the same folds, preprocessing, metric, and test horizon for every candidate.

# 9. Reusable Experiment Templates

## Tabular regression template

> from sklearn.model_selection import cross_validate, KFold  
> from sklearn.pipeline import Pipeline  
> from sklearn.impute import SimpleImputer  
> from sklearn.preprocessing import StandardScaler  
> from sklearn.linear_model import LinearRegression  
>   
> cv = KFold(n_splits=5, shuffle=True, random_state=42)  
> model = Pipeline(\[  
> ("imputer", SimpleImputer(strategy="median")),  
> ("scaler", StandardScaler()),  
> ("model", LinearRegression())  
> \])  
>   
> scores = cross_validate(  
> model, X, y, cv=cv,  
> scoring={"mae": "neg_mean_absolute_error", "r2": "r2"},  
> return_train_score=True  
> )

## Classification threshold template

> \# Fit on training data, tune threshold on validation data,  
> \# and report final performance once on untouched test data.  
> prob = model.predict_proba(X_valid)\[:, 1\]  
> threshold = 0.35  
> pred = (prob \>= threshold).astype(int)  
>   
> \# Choose threshold from business cost or validation precision/recall,  
> \# not from the final test set.

## Time-series validation template

> \# Expanding-window idea  
> for cutoff in cutoffs:  
> train = series.loc\[:cutoff\]  
> test = series.loc\[cutoff + 1 : cutoff + horizon\]  
> model.fit(train)  
> forecast = model.forecast(horizon)  
> record_error(test, forecast)  
>   
> \# Aggregate errors across many forecast origins and horizons.  
> \# Never shuffle timestamps.

## Minimum experiment record

- Dataset version and target definition.

- Train/validation/test logic, including groups or time boundaries.

- Preprocessing pipeline and leakage controls.

- Baseline performance.

- Hyperparameter search space and budget.

- Primary and secondary metrics with uncertainty across folds.

- Latency, memory, model size, and interpretability requirements.

- Error analysis by subgroup, range, and forecast horizon.

- Final reason for selecting the model - including why the runner-up was rejected.

# 10. Recommended Books and Official References

## Recommended books

**An Introduction to Statistical Learning, 2nd ed.:** James, Witten, Hastie, Tibshirani, and Taylor. Excellent first rigorous treatment of regression, classification, trees, SVMs, and model assessment.

**The Elements of Statistical Learning, 2nd ed.:** Hastie, Tibshirani, and Friedman. Deeper theory of linear models, trees, boosting, kernels, and statistical learning.

**Hands-On Machine Learning with Scikit-Learn, Keras & TensorFlow:** Aurelien Geron. Practical pipelines, model comparison, tree ensembles, SVMs, and neural networks.

**Forecasting: Principles and Practice, 3rd ed.:** Hyndman and Athanasopoulos. Free online reference for ETS, ARIMA, Prophet, VAR, neural forecasting, evaluation, and practical forecasting.

**Interpretable Machine Learning:** Christoph Molnar. Practical methods for understanding complex models and their limitations.

## Official and primary references used

**R1 -** [<u>Scikit-learn User Guide</u>](https://scikit-learn.org/stable/user_guide.html)

**R2 -** [<u>Scikit-learn Linear Models</u>](https://scikit-learn.org/stable/modules/linear_model.html)

**R3 -** [<u>Scikit-learn Decision Trees</u>](https://scikit-learn.org/stable/modules/tree.html)

**R4 -** [<u>Scikit-learn Ensemble Methods</u>](https://scikit-learn.org/stable/modules/ensemble.html)

**R5 -** [<u>Scikit-learn Support Vector Machines</u>](https://scikit-learn.org/stable/modules/svm.html)

**R6 -** [<u>XGBoost Python API</u>](https://xgboost.readthedocs.io/en/stable/python/python_api.html)

**R7 -** [<u>XGBoost Documentation</u>](https://xgboost.readthedocs.io/en/stable/)

**R8 -** [<u>Scikit-learn Nearest Neighbors</u>](https://scikit-learn.org/stable/modules/neighbors.html)

**R9 -** [<u>Scikit-learn Naive Bayes</u>](https://scikit-learn.org/stable/modules/naive_bayes.html)

**R10 -** [<u>Scikit-learn KMeans</u>](https://scikit-learn.org/stable/modules/generated/sklearn.cluster.KMeans.html)

**R11 -** [<u>Statsmodels ARIMA API</u>](https://www.statsmodels.org/stable/generated/statsmodels.tsa.arima.model.ARIMA.html)

**R12 -** [<u>Forecasting: Principles and Practice - ARIMA</u>](https://otexts.com/fpp3/non-seasonal-arima.html)

**R13 -** [<u>Forecasting: Principles and Practice - Seasonal ARIMA</u>](https://otexts.com/fpp3/seasonal-arima.html)

**R14 -** [<u>Statsmodels Holt-Winters Exponential Smoothing</u>](https://www.statsmodels.org/stable/generated/statsmodels.tsa.holtwinters.ExponentialSmoothing.html)

**R15 -** [<u>Forecasting: Principles and Practice - Exponential Smoothing</u>](https://otexts.com/fpp3/expsmooth.html)

**R16 -** [<u>Statsmodels ETS Model</u>](https://www.statsmodels.org/stable/generated/statsmodels.tsa.exponential_smoothing.ets.ETSModel.html)

**R17 -** [<u>Prophet Quick Start</u>](https://facebook.github.io/prophet/docs/quick_start.html)

**R18 -** [<u>Prophet Trend Changepoints</u>](https://facebook.github.io/prophet/docs/trend_changepoints.html)

**R19 -** [<u>Prophet Seasonality, Holidays, and Regressors</u>](https://facebook.github.io/prophet/docs/seasonality%2C_holiday_effects%2C_and_regressors.html)

**R20 -** [<u>Prophet Uncertainty Intervals</u>](https://facebook.github.io/prophet/docs/uncertainty_intervals.html)

**R21 -** [<u>Statsmodels VAR API</u>](https://www.statsmodels.org/stable/vector_ar.html)

**R22 -** [<u>Forecasting: Principles and Practice - VAR</u>](https://otexts.com/fpp3/VAR.html)

**R23 -** [<u>Keras Recurrent Layers</u>](https://keras.io/api/layers/recurrent_layers/)

**R24 -** [<u>Keras LSTM Layer</u>](https://keras.io/api/layers/recurrent_layers/lstm/)

**R25 -** [<u>Keras GRU Layer</u>](https://keras.io/api/layers/recurrent_layers/gru/)

## Final model-selection principle

There is no universally best model. The best choice is the least complex model that produces reliable out-of-sample performance under the real data-generation process and satisfies explanation, latency, memory, fairness, and maintenance constraints.