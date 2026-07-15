# NumPy Roadmap for a Machine Learning Engineer

> A practical, progressive curriculum for mastering the parts of NumPy that matter in machine learning, data preprocessing, numerical computing, and model implementation.

---

## How to Use This Roadmap

This is **not** a plan to memorize the entire NumPy API. NumPy serves many domains, including physics, signal processing, finance, engineering, image processing, and scientific simulation. A machine learning engineer does not need equal mastery of every feature.

Your goal is to develop four capabilities:

1. **Read array code accurately** — understand shapes, axes, dtypes, broadcasting, and memory behavior.
2. **Write vectorized numerical code** — replace unnecessary Python loops with correct array operations.
3. **Build ML algorithms from first principles** — implement preprocessing, losses, gradients, and classical models.
4. **Debug numerical problems** — identify shape errors, silent copies, overflow, instability, and data leakage.

### Recommended study cycle

For every module:

1. Read the listed documentation.
2. Reproduce every important example without copying.
3. Complete the exercises.
4. Write a one-page summary in your own words.
5. Pass the mastery checkpoint before moving forward.
6. Revisit the topic while building later projects.

### Suggested pace

- **Steady track:** 60–90 minutes per day, 5 days per week, for 8–10 weeks.
- **Intensive track:** 2–3 hours per day for 4–5 weeks.
- **Long-term track:** Complete one module each week and one project each month.

Do not rush through the early shape, indexing, and broadcasting modules. Weakness in those areas causes most NumPy bugs in ML code.

---

# Progress Dashboard

## Foundation

- [ ] Module 0 — Environment and documentation workflow
- [ ] Module 1 — The `ndarray` mental model
- [ ] Module 2 — Array creation and inspection
- [ ] Module 3 — Indexing, slicing, and selection
- [ ] Module 4 — Shapes, axes, and dimensional transformations
- [ ] Module 5 — Broadcasting and vectorization

## Numerical ML Core

- [ ] Module 6 — Universal functions and aggregations
- [ ] Module 7 — Dtypes, precision, missing values, and numerical stability
- [ ] Module 8 — Randomness and reproducibility
- [ ] Module 9 — Linear algebra for machine learning
- [ ] Module 10 — Data preprocessing patterns

## Engineering Practice

- [ ] Module 11 — Performance, memory, copies, and views
- [ ] Module 12 — Input/output and framework interoperability
- [ ] Module 13 — Testing, debugging, and typing numerical code
- [ ] Module 14 — Advanced array expressions

## Projects

- [ ] Project 1 — Vectorized tabular preprocessing pipeline
- [ ] Project 2 — Linear regression from scratch
- [ ] Project 3 — Logistic regression from scratch
- [ ] Project 4 — K-means clustering from scratch
- [ ] Project 5 — PCA from scratch
- [ ] Project 6 — Small neural network from scratch

---

# Priority Map

## Tier A — Must master

These are daily-use ML skills:

- Array shape, rank, dimensions, and axes
- Indexing, slicing, boolean masks, and integer-array indexing
- Reshaping, transposing, stacking, concatenating, and adding/removing axes
- Broadcasting rules
- Vectorized arithmetic and comparisons
- Reductions with `axis` and `keepdims`
- Dtypes, casting, floating-point precision, `NaN`, and infinity
- Random sampling with `np.random.default_rng`
- Matrix multiplication and core linear algebra
- Copies, views, mutation, and memory usage
- Numerical testing with tolerances

## Tier B — Working proficiency

Learn well enough to use confidently:

- Sorting, searching, counting, and set operations
- `where`, `select`, `clip`, and piecewise transformations
- `einsum`, `tensordot`, and batched matrix operations
- File formats such as `.npy` and `.npz`
- Type hints with `numpy.typing`
- Performance measurement and memory profiling
- Sliding windows and stride-aware code
- Interoperability with pandas, scikit-learn, PyTorch, TensorFlow, and JAX

## Tier C — Learn only when required

Do not spend early study time here:

- Structured and record arrays
- Masked-array subsystem
- Polynomial classes
- Fourier transforms
- Advanced datetime operations
- C API, Cython integration, F2PY, and custom ufunc internals
- NumPy subclassing
- Low-level SIMD and build-system details

---

# Module 0 — Environment and Documentation Workflow

## Objective

Create a reliable learning environment and learn how to navigate NumPy documentation instead of depending only on tutorials.

## Setup

```bash
python -m venv .venv
```

Activate the environment, then install:

```bash
python -m pip install --upgrade pip
python -m pip install numpy jupyterlab matplotlib pytest
```

Optional development tools:

```bash
python -m pip install mypy ruff memory-profiler
```

Check versions:

```python
import sys
import numpy as np

print(sys.version)
print(np.__version__)
np.show_config()
```

## Documentation workflow

Use three documentation layers:

1. **User Guide** — concepts and explanations.
2. **API Reference** — exact parameters, returns, behavior, and edge cases.
3. **Release notes and migration guides** — compatibility changes.

Useful interactive tools:

```python
help(np.reshape)
np.reshape?
np.reshape??
np.info(np.reshape)
```

In Jupyter:

```python
%pdoc np.reshape
%timeit np.arange(1_000_000) ** 2
```

## Exercise

Create a notebook named:

```text
numpy_ml_engineer_lab.ipynb
```

Add sections for:

- Experiments
- Shape journal
- Errors and explanations
- Performance comparisons
- Reusable snippets
- Questions to revisit

## Mastery checkpoint

You can:

- [ ] Find a function in the API reference.
- [ ] Explain the difference between the User Guide and API Reference.
- [ ] Inspect your installed NumPy version.
- [ ] Use `help`, `?`, and `%timeit`.
- [ ] Reproduce documentation examples in a clean notebook.

## Official reading

- [NumPy documentation](https://numpy.org/doc/stable/)
- [NumPy User Guide](https://numpy.org/doc/stable/user/)
- [NumPy API Reference](https://numpy.org/doc/stable/reference/)
- [NumPy quickstart](https://numpy.org/doc/stable/user/quickstart.html)
- [Absolute basics for beginners](https://numpy.org/doc/stable/user/absolute_beginners.html)

---

# Module 1 — The `ndarray` Mental Model

## Objective

Understand what a NumPy array actually is. This is more important than memorizing functions.

## Core model

A NumPy array consists conceptually of:

- A block of memory
- A `dtype` describing each element
- A `shape`
- A number of dimensions, `ndim`
- Strides describing how to move through memory
- Metadata controlling layout and writeability

```python
import numpy as np

x = np.array(
    [
        [1.0, 2.0, 3.0],
        [4.0, 5.0, 6.0],
    ],
    dtype=np.float32,
)

print(x)
print("shape:", x.shape)
print("ndim:", x.ndim)
print("size:", x.size)
print("dtype:", x.dtype)
print("itemsize:", x.itemsize)
print("nbytes:", x.nbytes)
print("strides:", x.strides)
print("flags:", x.flags)
```

## Essential distinctions

### Scalar, vector, matrix, tensor

```python
scalar = np.array(5)                 # shape: ()
vector = np.array([1, 2, 3])         # shape: (3,)
matrix = np.array([[1, 2, 3]])       # shape: (1, 3)
tensor = np.zeros((32, 224, 224, 3)) # shape: (batch, height, width, channels)
```

A one-dimensional array of shape `(n,)` is not the same as:

- Row-like shape `(1, n)`
- Column-like shape `(n, 1)`

This distinction affects broadcasting and matrix multiplication.

### Homogeneous storage

An `ndarray` normally stores elements of one dtype. Mixed Python values may be coerced to a common dtype.

```python
np.array([1, 2.5, 3])
# usually becomes floating point
```

### Shape semantics in ML

Use explicit conventions:

- Tabular features: `(n_samples, n_features)`
- Regression targets: `(n_samples,)` or `(n_samples, 1)`
- Class scores: `(n_samples, n_classes)`
- Image batches: `(batch, height, width, channels)` or `(batch, channels, height, width)`
- Sequence batches: `(batch, sequence_length, embedding_dim)`

Do not manipulate arrays until you can state what each axis means.

## Exercises

1. Create arrays representing:
   - 100 samples with 8 features
   - 64 RGB images of size `224 × 224`
   - 32 sequences of 50 tokens with 128-dimensional embeddings
2. Print every structural attribute.
3. Compare `(5,)`, `(1, 5)`, and `(5, 1)`.
4. Predict the output shape before running every operation.

## Shape journal template

For each important array, record:

```text
Name:
Shape:
Dtype:
Meaning of axis 0:
Meaning of axis 1:
Meaning of remaining axes:
Expected value range:
Contains NaN/Inf:
Owns its memory:
```

## Mastery checkpoint

You can explain:

- [ ] Why `(n,)`, `(1, n)`, and `(n, 1)` differ.
- [ ] The meaning of `shape`, `ndim`, `size`, `itemsize`, and `nbytes`.
- [ ] Why NumPy arrays are usually homogeneous.
- [ ] What each axis represents in common ML tensors.
- [ ] How an array can change shape without changing its data.

---

# Module 2 — Array Creation and Inspection

## Objective

Create arrays intentionally, with correct shape and dtype.

## Core creation functions

```python
np.array(...)
np.asarray(...)
np.zeros(...)
np.ones(...)
np.full(...)
np.empty(...)
np.arange(...)
np.linspace(...)
np.eye(...)
np.identity(...)
np.zeros_like(...)
np.ones_like(...)
np.full_like(...)
```

## Important examples

```python
x = np.array([1, 2, 3], dtype=np.float32)

features = np.zeros((1_000, 20), dtype=np.float32)
labels = np.zeros(1_000, dtype=np.int64)

grid = np.linspace(0.0, 1.0, num=101)
indices = np.arange(0, 100, 5)

weights = np.empty((20, 1), dtype=np.float32)
weights.fill(0.0)
```

## `array` versus `asarray`

Conceptually:

- `np.array` commonly creates an array and offers copy control.
- `np.asarray` converts array-like input while avoiding a copy when possible.

Always consult the API reference for the NumPy version you use, especially when code depends on copy semantics.

## Initialization warning

`np.empty` does not initialize values. Use it only when every element will definitely be overwritten.

```python
unsafe = np.empty((3, 3))
print(unsafe)  # arbitrary existing memory values
```

## Shape inspection utility

Create a reusable helper:

```python
def describe_array(name: str, arr: np.ndarray) -> None:
    print(
        f"{name}: shape={arr.shape}, ndim={arr.ndim}, "
        f"dtype={arr.dtype}, min={np.nanmin(arr)}, "
        f"max={np.nanmax(arr)}, nbytes={arr.nbytes}"
    )
```

## Exercises

1. Create feature matrices with `float32` and labels with `int64`.
2. Generate 100 evenly spaced thresholds between `-5` and `5`.
3. Construct a one-hot identity matrix for 10 classes.
4. Compare memory usage of one million values stored as:
   - `float64`
   - `float32`
   - `int64`
   - `uint8`
5. Write a function that validates an input array's expected shape and dtype.

## Mastery checkpoint

- [ ] You select dtype deliberately.
- [ ] You know when `empty` is unsafe.
- [ ] You can create arrays matching standard ML tensor shapes.
- [ ] You inspect array metadata before computation.
- [ ] You understand that conversion may or may not allocate new memory.

## Official reading

- [Array creation](https://numpy.org/doc/stable/user/basics.creation.html)
- [Array creation routines](https://numpy.org/doc/stable/reference/routines.array-creation.html)

---

# Module 3 — Indexing, Slicing, and Selection

## Objective

Select and modify data correctly without confusing views, copies, masks, or axis positions.

## Topics

### Basic indexing

```python
x[0]
x[-1]
x[2, 3]
```

### Slicing

```python
x[:10]
x[::2]
x[:, 0]
x[:, 1:4]
x[..., -1]
```

### Boolean indexing

```python
mask = x > 0
positive = x[mask]

valid_rows = ~np.isnan(x).any(axis=1)
clean_x = x[valid_rows]
```

### Integer-array indexing

```python
rows = np.array([0, 3, 7])
selected = x[rows]

columns = np.array([1, 4])
selected_columns = x[:, columns]
```

### Paired indexing versus rectangular selection

```python
x[[0, 1], [2, 3]]
```

The expression above selects paired coordinates, not a rectangular submatrix.

For a rectangular selection:

```python
x[np.ix_([0, 1], [2, 3])]
```

### Conditional replacement

```python
x = np.where(x < 0, 0, x)
x = np.clip(x, 0, 1)
```

### Assignment through slices

```python
x[:, 0] = 0
x[x < 0] = 0
```

## Critical rule: basic versus advanced indexing

- Basic slicing generally returns a **view**.
- Advanced indexing generally returns a **copy**.

This affects mutation, memory use, and performance.

## Exercises

1. Select every second sample.
2. Select the first and last feature columns.
3. Remove rows containing any `NaN`.
4. Cap outliers to the 1st and 99th percentile.
5. Create train and validation subsets using shuffled integer indices.
6. Select all samples whose label is one of `{2, 5, 7}`.
7. Replace negative values without using a Python loop.
8. Demonstrate a case where modifying a slice modifies the original array.
9. Demonstrate a case where modifying an advanced-indexed result does not modify the original.

## ML mini-task: stratified-like manual selection

Given labels:

```python
y = np.array([0, 0, 0, 1, 1, 1, 2, 2, 2])
```

Build index arrays for each class and sample two indices per class using a random generator.

## Mastery checkpoint

You can predict:

- [ ] The output shape of an indexing expression.
- [ ] Whether an operation returns a view or copy.
- [ ] Whether assignment mutates the original.
- [ ] The difference between paired and rectangular advanced indexing.
- [ ] How to filter rows with boolean conditions.
- [ ] How to combine conditions using `&`, `|`, and `~`.

## Common mistakes

Incorrect:

```python
mask = x > 0 and x < 1
```

Correct:

```python
mask = (x > 0) & (x < 1)
```

Do not omit parentheses because comparison and bitwise operator precedence can produce errors or incorrect expressions.

## Official reading

- [Indexing on ndarrays](https://numpy.org/doc/stable/user/basics.indexing.html)
- [Indexing routines](https://numpy.org/doc/stable/reference/routines.indexing.html)
- [Copies and views](https://numpy.org/doc/stable/user/basics.copies.html)

---

# Module 4 — Shapes, Axes, and Dimensional Transformations

## Objective

Manipulate dimensions without losing the semantic meaning of the data.

## Core functions and methods

```python
reshape
ravel
flatten
transpose
swapaxes
moveaxis
squeeze
expand_dims
newaxis
concatenate
stack
vstack
hstack
column_stack
split
array_split
repeat
tile
```

## Reshaping

```python
x = np.arange(24)
x_2d = x.reshape(6, 4)
x_3d = x.reshape(2, 3, 4)
```

Use `-1` for one inferred dimension:

```python
batch = images.reshape(images.shape[0], -1)
```

## Flattening

```python
flat_view_when_possible = x.ravel()
flat_copy = x.flatten()
```

Understand the copy difference.

## Adding axes

```python
x = np.arange(5)

row = x[np.newaxis, :]   # (1, 5)
column = x[:, np.newaxis] # (5, 1)

same_column = np.expand_dims(x, axis=1)
```

## Removing singleton axes

```python
x = np.zeros((32, 1, 10))
y = np.squeeze(x, axis=1)
```

Prefer specifying `axis` when accidental removal would be dangerous.

## Transposition

```python
images_nchw = np.transpose(images_nhwc, (0, 3, 1, 2))
```

For two-dimensional arrays:

```python
x.T
```

For one-dimensional arrays, `x.T` does not turn `(n,)` into `(n, 1)`.

## Combining arrays

```python
combined_rows = np.concatenate([x_train, x_valid], axis=0)
combined_features = np.concatenate([x_numeric, x_encoded], axis=1)

batch_stack = np.stack([sample_a, sample_b], axis=0)
```

Key distinction:

- `concatenate` joins along an existing axis.
- `stack` creates a new axis.

## Axis reasoning method

Before any operation, write:

```text
Input shape:
Meaning of each axis:
Operation:
Target axis:
Expected output shape:
```

## Exercises

1. Convert image data from NHWC to NCHW and back.
2. Flatten a batch of images while preserving the batch axis.
3. Convert a target `(n,)` to `(n, 1)` and back.
4. Combine two feature matrices column-wise.
5. Stack five model prediction vectors into shape `(5, n_samples)`.
6. Split a dataset into four nearly equal chunks.
7. Explain why `x.T` has no visible effect for a one-dimensional array.

## Mastery checkpoint

- [ ] You can infer reshape results.
- [ ] You know the difference between `reshape`, `ravel`, and `flatten`.
- [ ] You can add and remove dimensions safely.
- [ ] You distinguish `stack` from `concatenate`.
- [ ] You can reorder image and sequence axes.
- [ ] You never transpose blindly without documenting axis meaning.

## Official reading

- [Array manipulation routines](https://numpy.org/doc/stable/reference/routines.array-manipulation.html)

---

# Module 5 — Broadcasting and Vectorization

## Objective

Master NumPy's central mechanism for writing compact and efficient ML computations.

## Broadcasting rule

When comparing shapes from the last dimension toward the first, dimensions are compatible when:

1. They are equal, or
2. One of them is `1`.

Missing leading dimensions are treated as size `1`.

## Examples

### Scalar with matrix

```python
x = np.ones((4, 3))
y = x * 10
```

Shapes:

```text
x: (4, 3)
10: ()
result: (4, 3)
```

### Feature-wise standardization

```python
x = np.random.default_rng(0).normal(size=(100, 8))
mean = x.mean(axis=0)       # (8,)
std = x.std(axis=0)         # (8,)
z = (x - mean) / std        # (100, 8)
```

### Pairwise distances

```python
a = np.random.default_rng(0).normal(size=(5, 3))
b = np.random.default_rng(1).normal(size=(7, 3))

diff = a[:, np.newaxis, :] - b[np.newaxis, :, :]
distances = np.sqrt(np.sum(diff**2, axis=2))
```

Shapes:

```text
a[:, None, :]: (5, 1, 3)
b[None, :, :]: (1, 7, 3)
diff:          (5, 7, 3)
distances:     (5, 7)
```

### Bias addition

```python
logits = x @ weights + bias
```

For multiclass output:

```text
x:       (n_samples, n_features)
weights: (n_features, n_classes)
bias:    (n_classes,)
logits:  (n_samples, n_classes)
```

## Vectorization principle

Replace element-by-element Python execution with array expressions.

Loop version:

```python
result = np.empty_like(x)

for i in range(x.shape[0]):
    for j in range(x.shape[1]):
        result[i, j] = max(0, x[i, j])
```

Vectorized version:

```python
result = np.maximum(x, 0)
```

## Broadcasting danger

Broadcasting can create a logically huge intermediate result.

```python
# Potentially massive:
pairwise = x[:, None, :] - x[None, :, :]
```

If `x` has one million rows, the pairwise tensor is infeasible.

Vectorization does not guarantee acceptable memory use.

## Exercises

1. Standardize each feature without loops.
2. Normalize every row to unit L2 norm.
3. Add a different bias value to every column.
4. Compute pairwise Euclidean distances.
5. Build an RBF kernel matrix.
6. Implement batch mean squared error.
7. Explain whether these shape pairs broadcast:
   - `(32, 10)` and `(10,)`
   - `(32, 10)` and `(32,)`
   - `(4, 1, 8)` and `(3, 8)`
   - `(5, 7, 1)` and `(1, 9)`

## Mastery checkpoint

- [ ] You apply the right-to-left broadcasting rule.
- [ ] You predict output shapes before execution.
- [ ] You use `None` or `expand_dims` intentionally.
- [ ] You can vectorize common preprocessing and loss calculations.
- [ ] You can identify a vectorized expression that consumes excessive memory.
- [ ] You understand why broadcasting often avoids explicit data repetition.

## Official reading

- [Broadcasting](https://numpy.org/doc/stable/user/basics.broadcasting.html)
- [Universal function basics](https://numpy.org/doc/stable/user/basics.ufuncs.html)

---

# Module 6 — Universal Functions and Aggregations

## Objective

Use element-wise operations and reductions correctly across arbitrary axes.

## Universal functions

Common ufunc categories:

### Arithmetic

```python
np.add
np.subtract
np.multiply
np.divide
np.power
np.sqrt
np.exp
np.log
np.log1p
np.expm1
np.abs
```

### Comparisons and logic

```python
np.equal
np.not_equal
np.greater
np.less
np.logical_and
np.logical_or
np.logical_not
```

### Element-wise extrema

```python
np.maximum
np.minimum
np.fmax
np.fmin
```

### Trigonometric functions

Useful occasionally in feature engineering:

```python
np.sin
np.cos
np.arctan2
```

## Reductions

```python
np.sum
np.mean
np.std
np.var
np.min
np.max
np.argmin
np.argmax
np.median
np.percentile
np.quantile
np.any
np.all
np.count_nonzero
```

## Axis and `keepdims`

```python
x = np.arange(24).reshape(2, 3, 4)

x.mean(axis=0)                 # shape (3, 4)
x.mean(axis=1)                 # shape (2, 4)
x.mean(axis=2)                 # shape (2, 3)
x.mean(axis=(1, 2))            # shape (2,)
x.mean(axis=(1, 2), keepdims=True)  # shape (2, 1, 1)
```

Use `keepdims=True` when the reduced result must broadcast back against the original array.

## Stable softmax

Naive:

```python
np.exp(logits) / np.exp(logits).sum(axis=1, keepdims=True)
```

Stable:

```python
shifted = logits - logits.max(axis=1, keepdims=True)
exp_shifted = np.exp(shifted)
probabilities = exp_shifted / exp_shifted.sum(axis=1, keepdims=True)
```

## Conditional functions

```python
np.where
np.select
np.clip
np.piecewise
```

## Sorting and searching

```python
np.sort
np.argsort
np.partition
np.argpartition
np.searchsorted
np.unique
```

For top-k elements, `argpartition` can be more appropriate than sorting the entire array.

## Exercises

1. Implement ReLU, sigmoid, and stable softmax.
2. Compute feature-wise min-max scaling.
3. Find the indices of the five largest prediction scores.
4. Count missing values per feature.
5. Compute confusion-matrix ingredients for binary classification:
   - true positives
   - true negatives
   - false positives
   - false negatives
6. Compute accuracy, precision, recall, and F1 without scikit-learn.
7. Compare `maximum` and `fmax` when `NaN` is present.

## Mastery checkpoint

- [ ] You understand element-wise ufunc behavior.
- [ ] You use reduction axes correctly.
- [ ] You use `keepdims` to preserve broadcastable shape.
- [ ] You implement stable softmax.
- [ ] You distinguish element-wise maximum from an array reduction.
- [ ] You can calculate common classification metrics with arrays.

## Official reading

- [Mathematical functions](https://numpy.org/doc/stable/reference/routines.math.html)
- [Statistics](https://numpy.org/doc/stable/reference/routines.statistics.html)
- [Logic functions](https://numpy.org/doc/stable/reference/routines.logic.html)
- [Sorting, searching, and counting](https://numpy.org/doc/stable/reference/routines.sort.html)

---

# Module 7 — Dtypes, Precision, Missing Values, and Numerical Stability

## Objective

Prevent silent numerical errors that can invalidate training or evaluation.

## Dtype decisions in ML

Common choices:

- `float32`: standard for many neural-network workloads and lower memory use.
- `float64`: useful for numerical analysis, some classical algorithms, and debugging precision.
- `int64`: common for labels and indices.
- `uint8`: common for raw images before normalization.
- `bool`: masks and binary indicators.

## Casting

```python
x_float = x.astype(np.float32)
```

Casting may allocate a new array.

Inspect conversion safety:

```python
np.can_cast(np.float64, np.float32, casting="safe")
np.result_type(np.float32, np.int64)
```

## Integer overflow

Fixed-width NumPy integers can overflow.

```python
x = np.array([300], dtype=np.int8)
print(x)
```

Inspect limits:

```python
np.iinfo(np.int32)
np.finfo(np.float32)
```

## Floating-point comparison

Incorrect:

```python
prediction == expected
```

Preferred:

```python
np.isclose(prediction, expected)
np.allclose(predictions, expected_predictions)
```

## Missing and invalid values

```python
np.isnan(x)
np.isinf(x)
np.isfinite(x)
np.nanmean(x, axis=0)
np.nanstd(x, axis=0)
np.nan_to_num(x)
```

Do not use `nan_to_num` blindly. Decide whether missingness should be:

- Removed
- Imputed
- Represented by a missing-value indicator
- Rejected as invalid input

## Floating-point error handling

```python
with np.errstate(divide="raise", invalid="raise", over="raise"):
    result = np.log(x)
```

Global settings should be used cautiously:

```python
old_settings = np.seterr(all="warn")
np.seterr(**old_settings)
```

## Numerical stability patterns

### Stable sigmoid

```python
def sigmoid(x: np.ndarray) -> np.ndarray:
    out = np.empty_like(x, dtype=np.result_type(x, np.float64))
    positive = x >= 0
    out[positive] = 1.0 / (1.0 + np.exp(-x[positive]))
    exp_x = np.exp(x[~positive])
    out[~positive] = exp_x / (1.0 + exp_x)
    return out
```

### Stable logarithm

```python
eps = np.finfo(np.float64).eps
safe_probabilities = np.clip(probabilities, eps, 1.0 - eps)
loss = -np.log(safe_probabilities)
```

### Prefer solving systems over explicit inverse

Less desirable:

```python
solution = np.linalg.inv(a) @ b
```

Preferred:

```python
solution = np.linalg.solve(a, b)
```

## Exercises

1. Compare memory and error behavior of `float32` and `float64`.
2. Trigger integer overflow and explain it.
3. Create an array containing `NaN`, `Inf`, and `-Inf`; detect each.
4. Write a validation function that rejects non-finite training data.
5. Implement stable binary cross-entropy.
6. Compare exact equality and tolerance-based equality.
7. Use `np.errstate` to convert silent warnings into exceptions.

## Mastery checkpoint

- [ ] You choose dtypes deliberately.
- [ ] You understand fixed-width overflow.
- [ ] You use tolerance-based floating-point comparisons.
- [ ] You validate finite values before training.
- [ ] You know common stability transformations.
- [ ] You do not use an explicit matrix inverse without a reason.

## Official reading

- [Data types](https://numpy.org/doc/stable/user/basics.types.html)
- [Floating-point error handling](https://numpy.org/doc/stable/reference/routines.err.html)
- [NumPy 2.0 migration guide](https://numpy.org/doc/stable/numpy_2_0_migration_guide.html)

---

# Module 8 — Randomness and Reproducibility

## Objective

Use modern NumPy random generation correctly for experiments, sampling, initialization, and dataset splitting.

## Use `Generator`

```python
rng = np.random.default_rng(seed=42)
```

Prefer generator methods:

```python
rng.random((3, 4))
rng.integers(0, 10, size=100)
rng.normal(loc=0.0, scale=1.0, size=(100, 5))
rng.uniform(-1.0, 1.0, size=20)
rng.choice(1_000, size=100, replace=False)
rng.shuffle(indices)
rng.permutation(indices)
```

## Reproducible train-validation split

```python
def train_valid_split_indices(
    n_samples: int,
    valid_fraction: float,
    seed: int,
) -> tuple[np.ndarray, np.ndarray]:
    if not 0.0 < valid_fraction < 1.0:
        raise ValueError("valid_fraction must be between 0 and 1.")

    rng = np.random.default_rng(seed)
    indices = rng.permutation(n_samples)
    split = int(n_samples * (1.0 - valid_fraction))
    return indices[:split], indices[split:]
```

## Random weight initialization

```python
fan_in = 128
fan_out = 64

weights = rng.normal(
    loc=0.0,
    scale=np.sqrt(2.0 / fan_in),
    size=(fan_in, fan_out),
)
```

## Reproducibility limitations

A seed helps reproduce a particular pipeline, but full reproducibility can also depend on:

- NumPy version
- Algorithm changes
- BLAS/LAPACK implementation
- Threading
- Hardware
- Downstream frameworks
- Data ordering and preprocessing

Record environment information with experimental results.

## Exercises

1. Generate identical arrays from two generators using the same seed.
2. Show that continuing to call one generator advances its state.
3. Build a reproducible batch sampler.
4. Implement bootstrap resampling.
5. Simulate noisy linear-regression data.
6. Create class-balanced sample indices.
7. Compare `shuffle`, `permutation`, and `permuted`.

## Mastery checkpoint

- [ ] You use `default_rng`.
- [ ] You pass generators or seeds explicitly into reusable functions.
- [ ] You can reproduce dataset splits.
- [ ] You distinguish in-place shuffle from returned permutations.
- [ ] You understand that a seed alone does not guarantee universal reproducibility.

## Official reading

- [Random Generator](https://numpy.org/doc/stable/reference/random/generator.html)
- [Random sampling](https://numpy.org/doc/stable/reference/random/index.html)

---

# Module 9 — Linear Algebra for Machine Learning

## Objective

Use vector, matrix, and decomposition operations that directly support ML algorithms.

## Shape conventions

For supervised learning:

```text
X: (n_samples, n_features)
w: (n_features,)
b: scalar
y_hat = X @ w + b
```

For multiclass prediction:

```text
X: (n_samples, n_features)
W: (n_features, n_classes)
b: (n_classes,)
logits = X @ W + b
```

## Essential operations

```python
x @ y
np.matmul(x, y)
np.dot(x, y)
np.inner(x, y)
np.outer(x, y)
np.linalg.norm(x)
np.linalg.solve(a, b)
np.linalg.lstsq(a, b, rcond=None)
np.linalg.svd(x)
np.linalg.eigh(x)
np.linalg.qr(x)
np.linalg.matrix_rank(x)
np.linalg.cond(x)
```

## Matrix multiplication

Understand the distinction among:

- Element-wise multiplication: `a * b`
- Matrix multiplication: `a @ b`
- Inner and outer products
- Batched matrix multiplication

## Least squares

```python
weights, residuals, rank, singular_values = np.linalg.lstsq(
    x_design,
    y,
    rcond=None,
)
```

## Covariance

```python
x_centered = x - x.mean(axis=0)
covariance = (x_centered.T @ x_centered) / (x.shape[0] - 1)
```

## PCA with SVD

```python
x_centered = x - x.mean(axis=0)
u, singular_values, vt = np.linalg.svd(x_centered, full_matrices=False)

components = vt[:n_components]
x_reduced = x_centered @ components.T
```

## Eigenvalue guidance

For real symmetric covariance matrices, prefer:

```python
np.linalg.eigh(covariance)
```

rather than the general-purpose `eig`.

## Condition number

```python
condition_number = np.linalg.cond(x)
```

A high condition number warns that the problem may be numerically sensitive.

## Exercises

1. Compute vector dot and outer products.
2. Verify matrix multiplication dimensions manually.
3. Solve a linear system.
4. Fit least-squares regression.
5. Compute a covariance matrix without `np.cov`.
6. Implement PCA with SVD.
7. Compare `eig` and `eigh` on a symmetric matrix.
8. Detect rank deficiency.
9. Explain why `solve` is generally preferred over `inv(a) @ b`.

## Mastery checkpoint

- [ ] You distinguish element-wise and matrix multiplication.
- [ ] You track dimensions through linear models.
- [ ] You use `solve` and `lstsq`.
- [ ] You understand SVD's role in PCA and low-rank approximation.
- [ ] You inspect rank and condition number.
- [ ] You use appropriate routines for symmetric matrices.

## Official reading

- [Linear algebra](https://numpy.org/doc/stable/reference/routines.linalg.html)

---

# Module 10 — Data Preprocessing Patterns

## Objective

Implement core tabular and tensor preprocessing without pandas or scikit-learn.

## Pattern 1: train-only statistics

Correct:

```python
train_mean = x_train.mean(axis=0)
train_std = x_train.std(axis=0)

x_train_scaled = (x_train - train_mean) / train_std
x_valid_scaled = (x_valid - train_mean) / train_std
x_test_scaled = (x_test - train_mean) / train_std
```

Incorrect: calculating separate validation or test statistics introduces inconsistent transformations and can create leakage.

## Pattern 2: safe standardization

```python
def fit_standardizer(x: np.ndarray) -> tuple[np.ndarray, np.ndarray]:
    mean = x.mean(axis=0)
    std = x.std(axis=0)
    safe_std = np.where(std == 0, 1.0, std)
    return mean, safe_std


def apply_standardizer(
    x: np.ndarray,
    mean: np.ndarray,
    std: np.ndarray,
) -> np.ndarray:
    return (x - mean) / std
```

## Pattern 3: min-max scaling

```python
minimum = x_train.min(axis=0)
maximum = x_train.max(axis=0)
scale = np.where(maximum == minimum, 1.0, maximum - minimum)

x_scaled = (x - minimum) / scale
```

## Pattern 4: one-hot encoding

```python
def one_hot(y: np.ndarray, n_classes: int) -> np.ndarray:
    if y.ndim != 1:
        raise ValueError("y must be one-dimensional.")
    if np.any((y < 0) | (y >= n_classes)):
        raise ValueError("Class index out of range.")

    encoded = np.zeros((y.size, n_classes), dtype=np.float32)
    encoded[np.arange(y.size), y] = 1.0
    return encoded
```

## Pattern 5: shuffling features and labels together

```python
indices = rng.permutation(x.shape[0])
x_shuffled = x[indices]
y_shuffled = y[indices]
```

## Pattern 6: mini-batches

```python
def iterate_minibatches(
    x: np.ndarray,
    y: np.ndarray,
    batch_size: int,
    rng: np.random.Generator,
):
    if x.shape[0] != y.shape[0]:
        raise ValueError("x and y must contain the same number of samples.")
    if batch_size <= 0:
        raise ValueError("batch_size must be positive.")

    indices = rng.permutation(x.shape[0])

    for start in range(0, x.shape[0], batch_size):
        batch_indices = indices[start : start + batch_size]
        yield x[batch_indices], y[batch_indices]
```

## Pattern 7: imputation

```python
feature_medians = np.nanmedian(x_train, axis=0)

x_train_imputed = np.where(np.isnan(x_train), feature_medians, x_train)
x_valid_imputed = np.where(np.isnan(x_valid), feature_medians, x_valid)
```

## Pattern 8: image normalization

```python
images = images.astype(np.float32) / 255.0
```

## Pattern 9: label prediction

```python
predicted_classes = probabilities.argmax(axis=1)
```

## Pattern 10: confusion matrix

```python
def confusion_matrix(
    y_true: np.ndarray,
    y_pred: np.ndarray,
    n_classes: int,
) -> np.ndarray:
    matrix = np.zeros((n_classes, n_classes), dtype=np.int64)
    np.add.at(matrix, (y_true, y_pred), 1)
    return matrix
```

## Exercises

Build a preprocessing class or set of pure functions that:

1. Validates shape and finite values.
2. Splits train and validation data.
3. Learns imputation values from training data only.
4. Learns scaling parameters from training data only.
5. Transforms validation data with saved parameters.
6. One-hot encodes labels.
7. Produces shuffled mini-batches.
8. Supports inverse transformation for numeric features.

## Mastery checkpoint

- [ ] You prevent data leakage.
- [ ] You preserve feature/sample alignment.
- [ ] You handle zero-variance features.
- [ ] You can implement one-hot encoding and mini-batching.
- [ ] Your transformations separate `fit` from `transform`.
- [ ] You validate assumptions before modifying data.

---

# Module 11 — Performance, Memory, Copies, and Views

## Objective

Write NumPy code that is not only correct but also memory-aware and efficient.

## First rule

Do not optimize by intuition alone. Measure.

```python
%timeit operation()
```

For larger sections:

```python
from time import perf_counter

start = perf_counter()
result = operation()
elapsed = perf_counter() - start
print(elapsed)
```

## Vectorization versus temporary arrays

This expression creates intermediates:

```python
result = ((x - mean) ** 2).sum(axis=1)
```

For moderate arrays this is appropriate. For very large arrays, investigate memory use and chunking.

## In-place operations

```python
x -= mean
x /= std
```

Potential benefit: fewer allocations.

Risks:

- Mutating data needed elsewhere
- Casting errors
- Unexpected changes through shared views
- Reduced readability
- Gradient or caching issues in surrounding systems

Use in-place operations only when ownership is clear.

## Copies and views

```python
x = np.arange(10)
view = x[2:8]
copy = x[[2, 3, 4, 5, 6, 7]]

print(np.shares_memory(x, view))
print(np.shares_memory(x, copy))
```

Inspect:

```python
view.base
view.flags["OWNDATA"]
```

## Contiguity

```python
x.flags["C_CONTIGUOUS"]
x.flags["F_CONTIGUOUS"]

x_c = np.ascontiguousarray(x)
x_f = np.asfortranarray(x)
```

Transposes often create non-contiguous views.

## Memory calculation

```python
expected_bytes = np.prod(x.shape) * x.dtype.itemsize
assert expected_bytes == x.nbytes
```

## Chunking

When full pairwise computation is too large:

```python
def pairwise_distances_in_chunks(
    x: np.ndarray,
    centers: np.ndarray,
    chunk_size: int,
) -> np.ndarray:
    output = np.empty((x.shape[0], centers.shape[0]), dtype=np.float64)

    for start in range(0, x.shape[0], chunk_size):
        stop = min(start + chunk_size, x.shape[0])
        diff = x[start:stop, None, :] - centers[None, :, :]
        output[start:stop] = np.sqrt(np.sum(diff**2, axis=2))

    return output
```

A Python loop over large chunks is often better than constructing an impossible full intermediate.

## Efficient expressions

Investigate:

```python
np.einsum
np.tensordot
np.matmul
out=
where=
```

Example squared row norms:

```python
norms = np.einsum("ij,ij->i", x, x)
```

## Benchmark checklist

For each candidate:

- Runtime
- Peak memory
- Allocation count
- Readability
- Numerical equivalence
- Behavior across realistic shapes
- Contiguous versus non-contiguous input

## Exercises

1. Benchmark a Python loop against vectorized ReLU.
2. Benchmark `flatten` and `ravel`.
3. Show which slicing operations share memory.
4. Compare `float64` and `float32` memory.
5. Measure pairwise-distance memory requirements before allocating.
6. Implement chunked inference.
7. Compare equivalent matrix operations using `@`, `einsum`, and loops.
8. Find an operation that becomes slower due to a huge temporary array.

## Mastery checkpoint

- [ ] You measure before optimizing.
- [ ] You know whether important arrays own or share memory.
- [ ] You understand contiguity and strides at a practical level.
- [ ] You estimate memory before large broadcasting operations.
- [ ] You can use chunking.
- [ ] You do not equate “vectorized” with “always optimal.”

## Official reading

- [Copies and views](https://numpy.org/doc/stable/user/basics.copies.html)
- [Array internals](https://numpy.org/doc/stable/reference/internals.html)
- [CPU/SIMD optimizations](https://numpy.org/doc/stable/reference/simd/index.html)

---

# Module 12 — Input/Output and Framework Interoperability

## Objective

Move numerical data safely between files and ML libraries.

## NumPy binary formats

```python
np.save("features.npy", x)
x_loaded = np.load("features.npy")

np.savez(
    "dataset.npz",
    x_train=x_train,
    y_train=y_train,
    x_valid=x_valid,
    y_valid=y_valid,
)

data = np.load("dataset.npz")
x_train = data["x_train"]
```

Use compressed archives when appropriate:

```python
np.savez_compressed(...)
```

## Text formats

```python
np.loadtxt(...)
np.genfromtxt(...)
np.savetxt(...)
```

For complex tabular data, pandas is often more robust. NumPy text loaders remain useful for simple numeric files.

## Memory mapping

```python
mapped = np.load("large_array.npy", mmap_mode="r")
```

Useful when an array is too large to load fully and access is compatible with memory mapping.

## Interoperability patterns

### pandas

```python
x = dataframe.to_numpy(dtype=np.float32)
```

Be cautious with mixed columns and object dtype.

### scikit-learn

Most estimators accept two-dimensional NumPy arrays:

```text
X: (n_samples, n_features)
y: (n_samples,)
```

### PyTorch

```python
tensor = torch.from_numpy(x)
array = tensor.detach().cpu().numpy()
```

Memory may be shared for compatible CPU arrays. Mutation can therefore affect both objects.

### TensorFlow

```python
tensor = tf.convert_to_tensor(x)
array = tensor.numpy()
```

### JAX

```python
jax_array = jnp.asarray(x)
numpy_array = np.asarray(jax_array)
```

Device transfer and immutability semantics differ from NumPy.

## Boundary validation

At every library boundary, inspect:

```python
print(type(x))
print(x.shape)
print(x.dtype)
print(x.flags)
```

Also verify:

- Device
- Gradient tracking
- Copy or shared memory
- Channel order
- Batch axis
- Label dtype
- Contiguity

## Exercises

1. Save and reload a dataset with `.npz`.
2. Verify exact or tolerance-based equality after loading.
3. Load a `.npy` file with memory mapping.
4. Convert a pandas DataFrame and detect object dtype.
5. Test shared memory between NumPy and PyTorch.
6. Write a boundary-validation function.

## Mastery checkpoint

- [ ] You choose appropriate binary formats.
- [ ] You can preserve multiple arrays in one archive.
- [ ] You understand memory mapping at a practical level.
- [ ] You inspect dtype and shape after framework conversion.
- [ ] You recognize when memory may be shared.

## Official reading

- [Input and output](https://numpy.org/doc/stable/reference/routines.io.html)
- [I/O with NumPy](https://numpy.org/doc/stable/user/basics.io.html)
- [Interoperability with NumPy](https://numpy.org/doc/stable/user/basics.interoperability.html)

---

# Module 13 — Testing, Debugging, and Typing Numerical Code

## Objective

Make array code reliable, maintainable, and easier to review.

## Assertions for development

```python
assert x.ndim == 2
assert x.shape[0] == y.shape[0]
assert np.issubdtype(x.dtype, np.floating)
assert np.isfinite(x).all()
```

For reusable production code, raise informative exceptions rather than relying only on `assert`.

## Numerical testing

```python
import numpy.testing as npt

npt.assert_array_equal(actual_labels, expected_labels)
npt.assert_allclose(actual_values, expected_values, rtol=1e-6, atol=1e-8)
```

Use exact equality for exact discrete outputs and tolerance-based checks for floating-point calculations.

## Shape tests

Test more than one “happy path”:

- One sample
- One feature
- Empty input, if supported
- Non-contiguous input
- Integer input
- `float32` and `float64`
- Extreme values
- `NaN` and infinity
- Mismatched sample counts

## Property-based thinking

Even without a property-testing library, test mathematical properties.

Examples:

- Standardized feature mean is approximately zero.
- Standardized non-constant feature standard deviation is approximately one.
- Softmax rows sum to one.
- Probabilities stay between zero and one.
- Confusion-matrix entries sum to the number of samples.
- PCA components are approximately orthonormal.
- Loss decreases for a simple learnable dataset.

## Gradient checking

For a scalar loss:

```python
def finite_difference_gradient(
    function,
    parameters: np.ndarray,
    epsilon: float = 1e-5,
) -> np.ndarray:
    gradient = np.zeros_like(parameters, dtype=np.float64)

    for index in np.ndindex(parameters.shape):
        original = parameters[index]

        parameters[index] = original + epsilon
        loss_plus = function(parameters)

        parameters[index] = original - epsilon
        loss_minus = function(parameters)

        parameters[index] = original

        gradient[index] = (loss_plus - loss_minus) / (2 * epsilon)

    return gradient
```

Use this to validate vectorized analytical gradients on small problems.

## Typing

```python
from numpy.typing import NDArray
import numpy as np

FloatArray = NDArray[np.floating]
IntArray = NDArray[np.integer]

def predict(x: FloatArray, weights: FloatArray) -> FloatArray:
    return x @ weights
```

Python type hints usually express dtype more easily than exact runtime shape. Validate shape at runtime.

## Debug function

```python
def validate_features(
    x: np.ndarray,
    *,
    n_features: int | None = None,
) -> np.ndarray:
    x = np.asarray(x)

    if x.ndim != 2:
        raise ValueError(f"Expected a 2D feature matrix, got shape {x.shape}.")

    if n_features is not None and x.shape[1] != n_features:
        raise ValueError(
            f"Expected {n_features} features, got {x.shape[1]}."
        )

    if not np.issubdtype(x.dtype, np.number):
        raise TypeError(f"Expected numeric dtype, got {x.dtype}.")

    if not np.isfinite(x).all():
        raise ValueError("Feature matrix contains NaN or infinity.")

    return x
```

## Exercises

1. Test your standardizer.
2. Test one-hot encoding on invalid labels.
3. Test stable softmax with large positive and negative logits.
4. Gradient-check linear and logistic regression.
5. Add dtype and shape hints to previous modules.
6. Test functions with non-contiguous arrays.
7. Write informative failure messages.

## Mastery checkpoint

- [ ] You test numerical values with appropriate tolerances.
- [ ] You test shapes, dtypes, and edge cases.
- [ ] You can gradient-check an analytical derivative.
- [ ] You write runtime validation at API boundaries.
- [ ] You use `numpy.typing` without assuming it replaces runtime checks.

## Official reading

- [Testing guidelines and utilities](https://numpy.org/doc/stable/reference/testing.html)
- [Typing with `numpy.typing`](https://numpy.org/doc/stable/reference/typing.html)

---

# Module 14 — Advanced Array Expressions

## Objective

Learn advanced tools only after broadcasting and ordinary matrix multiplication are solid.

## `einsum`

Useful for concise tensor contractions.

Examples:

```python
# Row-wise dot products
row_dot = np.einsum("ij,ij->i", a, b)

# Matrix multiplication
product = np.einsum("ik,kj->ij", a, b)

# Batched matrix multiplication
batched = np.einsum("bij,bjk->bik", a, b)

# Trace
trace = np.einsum("ii->", matrix)
```

Do not use `einsum` merely to make code look advanced. Use it when it improves clarity, avoids awkward reshaping, or reduces intermediates.

## `tensordot`

```python
result = np.tensordot(a, b, axes=([2], [0]))
```

Always document which axes are contracted.

## `take_along_axis`

Useful for selecting elements according to per-row indices:

```python
top_indices = np.argsort(scores, axis=1)[:, -3:]
top_scores = np.take_along_axis(scores, top_indices, axis=1)
```

## Indexed accumulation

```python
np.add.at
np.maximum.at
np.minimum.at
```

Useful for histograms, scatter-add behavior, and confusion matrices.

## Sliding windows

```python
from numpy.lib.stride_tricks import sliding_window_view

windows = sliding_window_view(sequence, window_shape=5)
```

Be careful: stride-based views can expose overlapping memory and lead to surprising write behavior.

## Exercises

1. Reimplement row-wise squared norms with `einsum`.
2. Compute batched attention score products.
3. Select top-k class scores per sample.
4. Build a confusion matrix using `np.add.at`.
5. Generate time-series windows.
6. Compare readability and performance of `einsum`, `@`, and broadcasting.

## Mastery checkpoint

- [ ] You can read basic Einstein summation notation.
- [ ] You use advanced expressions only when they improve the code.
- [ ] You document contracted axes.
- [ ] You understand indexed accumulation.
- [ ] You handle sliding-window views cautiously.

## Official reading

- [`numpy.einsum`](https://numpy.org/doc/stable/reference/generated/numpy.einsum.html)
- [`numpy.tensordot`](https://numpy.org/doc/stable/reference/generated/numpy.tensordot.html)
- [`numpy.take_along_axis`](https://numpy.org/doc/stable/reference/generated/numpy.take_along_axis.html)
- [`sliding_window_view`](https://numpy.org/doc/stable/reference/generated/numpy.lib.stride_tricks.sliding_window_view.html)

---

# Capstone Projects

These projects are the real completion criteria. Reading documentation without implementing algorithms will not produce durable mastery.

---

## Project 1 — Vectorized Tabular Preprocessing Pipeline

### Requirements

Build a reusable pipeline that:

- Accepts numeric two-dimensional arrays
- Validates sample count, shape, dtype, and finite values
- Splits data reproducibly
- Imputes missing values using training data only
- Standardizes or min-max scales features
- Handles constant columns
- Stores learned parameters
- Transforms validation and test arrays
- Produces mini-batches
- Saves and reloads state
- Includes unit tests

### Constraints

- No pandas
- No scikit-learn preprocessing
- No Python loop over individual samples or features
- Clear docstrings and type hints

### Completion evidence

- [ ] Tests pass.
- [ ] No leakage.
- [ ] Transformations work for `float32` and `float64`.
- [ ] Parameters can be saved and reloaded.
- [ ] Results match scikit-learn within tolerance on a test dataset.

---

## Project 2 — Linear Regression From Scratch

### Requirements

Implement:

- Prediction
- Mean squared error
- Closed-form least squares
- Batch gradient descent
- Mini-batch gradient descent
- L2 regularization
- Feature standardization
- R²
- Gradient checking

### Core equations

```text
ŷ = Xw + b

MSE = (1/n) Σ(ŷ - y)²

∂L/∂w = (2/n) Xᵀ(ŷ - y)

∂L/∂b = (2/n) Σ(ŷ - y)
```

### Tests

- Recover coefficients from synthetic data.
- Compare with `np.linalg.lstsq`.
- Compare with scikit-learn.
- Confirm loss decreases.
- Test singular or ill-conditioned data.

---

## Project 3 — Logistic Regression From Scratch

### Requirements

Implement:

- Stable sigmoid
- Binary cross-entropy
- Prediction probabilities
- Class prediction
- Gradient descent
- L2 regularization
- Accuracy, precision, recall, F1
- Confusion matrix
- Gradient checking

### Stability requirement

Clip probabilities or formulate the loss carefully to avoid `log(0)`.

### Extension

Implement multiclass softmax regression with cross-entropy.

---

## Project 4 — K-Means Clustering From Scratch

### Requirements

Implement:

- Random or k-means++ initialization
- Pairwise distances
- Cluster assignment
- Centroid updates
- Empty-cluster handling
- Inertia
- Convergence criteria
- Multiple initializations
- Reproducible randomness

### Engineering requirement

Support chunked distance computation for larger datasets.

### Tests

- Compare clusters and inertia with scikit-learn.
- Test duplicate points and empty clusters.
- Verify monotonic non-increase of inertia during valid updates.

---

## Project 5 — PCA From Scratch

### Requirements

Implement PCA using SVD:

- Center features
- Compute singular values and components
- Transform data
- Inverse transform
- Explained variance
- Explained variance ratio
- Select number of components by cumulative variance
- Verify component orthogonality

### Tests

- Compare with scikit-learn PCA.
- Reconstruct data and measure reconstruction error.
- Test highly correlated and rank-deficient data.

---

## Project 6 — Small Neural Network From Scratch

### Requirements

Build a fully connected classifier:

- Dense layer
- ReLU
- Stable softmax
- Cross-entropy
- Forward pass
- Backpropagation
- Mini-batch training
- Weight initialization
- Accuracy
- Gradient checking
- Learning curves

Suggested architecture:

```text
input -> dense -> ReLU -> dense -> softmax
```

### Restrictions

- NumPy only for model computation
- Matplotlib allowed for plotting
- A dataset loader may come from another package, but convert data to NumPy arrays

### Final extension

Complete the official NumPy community tutorial on a feedforward neural network for MNIST:

- [Deep learning on MNIST with NumPy](https://numpy.org/numpy-tutorials/tutorial-deep-learning-on-mnist/)

---

# Function Checklist for ML Engineers

You do not need to memorize every signature. You should recognize these names and know when to look them up.

## Array construction

```text
array, asarray, arange, linspace, zeros, ones, full, empty,
zeros_like, ones_like, full_like, eye
```

## Inspection

```text
shape, ndim, size, dtype, itemsize, nbytes, strides, flags
```

## Shape manipulation

```text
reshape, ravel, flatten, transpose, moveaxis, swapaxes,
squeeze, expand_dims, concatenate, stack, split, array_split
```

## Indexing and selection

```text
where, nonzero, argwhere, ix_, take, take_along_axis,
put_along_axis, compress
```

## Math and activation support

```text
exp, log, log1p, sqrt, power, abs, maximum, minimum,
clip, sign
```

## Reductions

```text
sum, mean, std, var, min, max, argmin, argmax,
median, percentile, quantile, any, all, count_nonzero
```

## Missing and finite values

```text
isnan, isinf, isfinite, nanmean, nanstd, nanmedian,
nan_to_num
```

## Sorting and searching

```text
sort, argsort, partition, argpartition, searchsorted,
unique
```

## Linear algebra

```text
matmul, dot, inner, outer, einsum, tensordot,
linalg.norm, linalg.solve, linalg.lstsq, linalg.svd,
linalg.eigh, linalg.qr, linalg.matrix_rank, linalg.cond
```

## Random

```text
random.default_rng, Generator.random, integers, normal,
uniform, choice, shuffle, permutation
```

## I/O

```text
save, load, savez, savez_compressed, loadtxt,
genfromtxt, savetxt
```

## Validation and testing

```text
isclose, allclose, testing.assert_array_equal,
testing.assert_allclose, result_type, can_cast
```

---

# Common NumPy Mistakes in Machine Learning

## 1. Confusing element-wise and matrix multiplication

```python
x * w  # element-wise
x @ w  # matrix multiplication
```

## 2. Reducing over the wrong axis

Write the axis meaning before calling `mean`, `sum`, `argmax`, or `std`.

## 3. Accidentally changing target shape

A target can silently move between `(n,)` and `(n, 1)`, causing unintended broadcasting.

Example:

```python
predictions.shape == (100, 1)
targets.shape == (100,)
```

Subtracting them produces shape `(100, 100)`, not `(100, 1)`.

## 4. Data leakage

Never fit scaling, imputation, or feature-selection parameters on validation or test data.

## 5. Assuming slicing always creates a copy

A slice often shares memory with the original.

## 6. Assuming vectorization is always memory-efficient

A compact expression can allocate a huge intermediate tensor.

## 7. Using legacy global random state everywhere

Prefer explicit `Generator` objects.

## 8. Ignoring dtype

Silent promotion, overflow, truncation, and extra memory use can result.

## 9. Comparing floating-point values exactly

Use `isclose`, `allclose`, or NumPy testing helpers.

## 10. Computing inverse matrices unnecessarily

Use `solve` or `lstsq`.

## 11. Using `squeeze()` without specifying an axis

It can remove a dimension that later becomes meaningful.

## 12. Mixing training and inference mutation

In-place operations can corrupt reused input arrays or saved features.

## 13. Ignoring non-finite values

Check `np.isfinite(x).all()` at important boundaries.

## 14. Failing to test edge shapes

Code that works for `(100, 20)` may fail for:

- `(1, 20)`
- `(100, 1)`
- `(20,)`
- Empty arrays
- Non-contiguous arrays

---

# Ten-Week Study Schedule

## Week 1 — Array fundamentals

- Module 0
- Module 1
- Module 2
- Daily shape prediction drills

Deliverable: array-inspection notebook.

## Week 2 — Indexing and shapes

- Module 3
- Module 4
- Copy/view experiments

Deliverable: dataset selection and reshaping notebook.

## Week 3 — Broadcasting and ufuncs

- Module 5
- Module 6
- Reimplement activations and metrics

Deliverable: vectorization challenge notebook.

## Week 4 — Numerical correctness

- Module 7
- Module 8
- Stability and reproducibility tests

Deliverable: robust numerical utility module.

## Week 5 — Linear algebra

- Module 9
- Implement covariance, least squares, and PCA primitives

Deliverable: linear algebra notebook with shape derivations.

## Week 6 — Data engineering

- Module 10
- Begin Project 1

Deliverable: tested preprocessing pipeline.

## Week 7 — Performance and interoperability

- Module 11
- Module 12
- Benchmark and conversion experiments

Deliverable: performance report with runtime and memory comparisons.

## Week 8 — Reliability

- Module 13
- Gradient-checking utilities
- Finish tests for previous code

Deliverable: reusable numerical test suite.

## Week 9 — Classical ML

- Project 2
- Project 3
- Compare against scikit-learn

Deliverable: linear and logistic regression package.

## Week 10 — Unsupervised learning and neural foundations

- Project 4
- Project 5
- Start Project 6
- Study Module 14 when needed

Deliverable: K-means, PCA, and a neural-network training prototype.

---

# Daily Practice Template

## 60-minute session

### 10 minutes — retrieval

Without notes, answer:

- What shape will this operation return?
- Which axis is reduced?
- Does this create a view or copy?
- What dtype will likely result?
- Will broadcasting occur?

### 20 minutes — documentation

Read one focused documentation section. Do not read passively; execute examples.

### 20 minutes — implementation

Write one function from scratch.

Examples:

- Standardizer
- Softmax
- Pairwise distance
- One-hot encoder
- Mini-batch iterator
- Confusion matrix
- PCA transform

### 10 minutes — testing and notes

- Add at least two tests.
- Record one mistake and its cause.
- Write one question for future review.

---

# Weekly Review Questions

Answer without executing code first.

1. What is the difference between shape `(n,)` and `(n, 1)`?
2. Which indexing operations usually return views?
3. How does broadcasting compare dimensions?
4. What does `axis=0` mean for a feature matrix?
5. Why is `keepdims=True` useful?
6. Why can `float32` and `float64` produce different results?
7. How do you detect `NaN` and infinity?
8. Why is stable softmax shifted by the row maximum?
9. Why should random generators be passed explicitly?
10. Why is `solve` better than explicitly computing an inverse?
11. When can vectorization consume too much memory?
12. How do you prevent preprocessing leakage?
13. How do you test floating-point output?
14. When do NumPy and PyTorch share memory?
15. How do you verify an analytical gradient?

Any question you cannot answer becomes next week's first revision topic.

---

# Definition of NumPy Mastery for an ML Engineer

You have completed this roadmap when you can do all of the following without depending on copied code:

## Shape and semantics

- [ ] Explain every axis of every important tensor.
- [ ] Predict output shapes for indexing, reductions, matrix multiplication, and broadcasting.
- [ ] Diagnose accidental `(n, n)` broadcasting caused by `(n,)` versus `(n, 1)`.

## Implementation

- [ ] Write vectorized preprocessing functions.
- [ ] Implement linear regression, logistic regression, K-means, and PCA.
- [ ] Implement a small neural-network forward and backward pass.
- [ ] Write mini-batch training code.

## Numerical reliability

- [ ] Select appropriate dtypes.
- [ ] Handle `NaN`, infinity, overflow, and floating-point comparison.
- [ ] Use stable sigmoid, softmax, and cross-entropy.
- [ ] Gradient-check model derivatives.

## Performance

- [ ] Identify copies and views.
- [ ] Estimate memory before allocation.
- [ ] Benchmark alternatives.
- [ ] Use chunking for large computations.
- [ ] Avoid unnecessary intermediates.

## Engineering quality

- [ ] Validate inputs.
- [ ] Write tests with tolerances.
- [ ] Save and load arrays and model state.
- [ ] Move arrays safely between ML frameworks.
- [ ] Read official documentation effectively.

At this point, additional NumPy learning should become project-driven rather than syllabus-driven.

---

# Topics to Defer Until a Real Need Appears

You should not attempt to “finish all of NumPy.” That target is inefficient and poorly defined.

Study these only when a project requires them:

- FFT and frequency-domain processing
- Polynomial manipulation
- Structured arrays
- Advanced datetime handling
- Masked arrays
- Custom dtypes
- Custom ufunc creation
- NumPy subclassing
- C API
- F2PY
- Build systems and ABI details
- Low-level SIMD optimization

The correct professional goal is not total API coverage. It is strong command of the array model, numerical reasoning, and the ability to learn specialized sections quickly.

---

# Official Documentation Reading Order

Follow this sequence:

1. [Absolute basics](https://numpy.org/doc/stable/user/absolute_beginners.html)
2. [Quickstart](https://numpy.org/doc/stable/user/quickstart.html)
3. [Array creation](https://numpy.org/doc/stable/user/basics.creation.html)
4. [Indexing](https://numpy.org/doc/stable/user/basics.indexing.html)
5. [Data types](https://numpy.org/doc/stable/user/basics.types.html)
6. [Broadcasting](https://numpy.org/doc/stable/user/basics.broadcasting.html)
7. [Copies and views](https://numpy.org/doc/stable/user/basics.copies.html)
8. [Ufunc basics](https://numpy.org/doc/stable/user/basics.ufuncs.html)
9. [Array manipulation routines](https://numpy.org/doc/stable/reference/routines.array-manipulation.html)
10. [Statistics](https://numpy.org/doc/stable/reference/routines.statistics.html)
11. [Random Generator](https://numpy.org/doc/stable/reference/random/generator.html)
12. [Linear algebra](https://numpy.org/doc/stable/reference/routines.linalg.html)
13. [Input/output](https://numpy.org/doc/stable/reference/routines.io.html)
14. [Testing](https://numpy.org/doc/stable/reference/testing.html)
15. [Typing](https://numpy.org/doc/stable/reference/typing.html)
16. [NumPy 2.0 migration guide](https://numpy.org/doc/stable/numpy_2_0_migration_guide.html)
17. [Deep learning on MNIST](https://numpy.org/numpy-tutorials/tutorial-deep-learning-on-mnist/)

---

# Final Rule

Whenever NumPy code behaves unexpectedly, inspect these in order:

```text
1. shape
2. axis meaning
3. dtype
4. broadcasting
5. indexing mode
6. copy versus view
7. finite values
8. memory allocation
9. numerical stability
10. version-specific behavior
```

Most difficult-looking NumPy bugs become ordinary once these ten checks are performed systematically.
