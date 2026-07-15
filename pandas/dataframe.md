# DataFrame 
## Data inspection 
- **df.head()** 
- **df.info()**
- **df.describe()**
- **df.shape()**

## Cleaning 
- **df.dropna()**
- **df.fillna(value)**
- **df.drop()**
- **df.rename()**
- **astype()**

## Data selection and filtering 
- **df.loc()**
- **df.iloc()**
- **df.drop_duplicate()**

## Aggregation 
- **df[column].value_counts()**
- **group_by()**
- **short_values()**
- **apply()**
- **map()**






---
# Pandas Series Methods After Selecting a Column

When you select a single column:

```python
df["Age"]
```

Pandas returns a **Series**. A Series supports many methods and properties.

---

## 1. Basic Inspection

```python
df["Age"].head()
df["Age"].head(10)
df["Age"].tail()
df["Age"].tail(10)
df["Age"].describe()
df["Age"].info()
df["Age"].dtype
df["Age"].size
df["Age"].shape
df["Age"].index
df["Age"].name
```

---

## 2. Numerical Summary Methods

```python
df["Age"].count()
df["Age"].sum()
df["Age"].mean()
df["Age"].median()
df["Age"].mode()
df["Age"].min()
df["Age"].max()
df["Age"].std()
df["Age"].var()
df["Age"].quantile()
df["Age"].quantile(0.25)
df["Age"].quantile(0.50)
df["Age"].quantile(0.75)
df["Age"].prod()
df["Age"].sem()
df["Age"].skew()
df["Age"].kurt()
```

---

## 3. Missing-Value Methods

```python
df["Age"].isna()
df["Age"].isnull()
df["Age"].notna()
df["Age"].notnull()
df["Age"].dropna()
df["Age"].fillna(0)
df["Age"].fillna(df["Age"].mean())
df["Age"].fillna(df["Age"].median())
df["Age"].ffill()
df["Age"].bfill()
```

Count missing values:

```python
df["Age"].isna().sum()
```

Count non-missing values:

```python
df["Age"].notna().sum()
```

---

## 4. Unique Values and Frequency

```python
df["Age"].unique()
df["Age"].nunique()
df["Age"].value_counts()
df["Age"].value_counts(normalize=True)
df["Age"].value_counts(dropna=False)
```

---

## 5. Filtering and Condition Checking

```python
df["Age"] > 30
df["Age"] < 30
df["Age"] >= 30
df["Age"] <= 30
df["Age"] == 30
df["Age"] != 30
```

Range checking:

```python
df["Age"].between(20, 40)
```

Membership checking:

```python
df["Age"].isin([20, 25, 30])
```

Check whether all values satisfy a condition:

```python
(df["Age"] > 18).all()
```

Check whether at least one value satisfies a condition:

```python
(df["Age"] > 60).any()
```

Use a condition to filter the DataFrame:

```python
df[df["Age"] > 30]
df[df["Age"].between(20, 40)]
df[df["Age"].isin([20, 25, 30])]
```

---

## 6. Sorting and Ranking

```python
df["Age"].sort_values()
df["Age"].sort_values(ascending=False)
df["Age"].sort_index()
df["Age"].nlargest(5)
df["Age"].nsmallest(5)
df["Age"].rank()
df["Age"].rank(ascending=False)
```

---

## 7. Transformation Methods

```python
df["Age"].astype(int)
df["Age"].astype(float)
df["Age"].round()
df["Age"].round(2)
df["Age"].abs()
df["Age"].clip(lower=18, upper=60)
df["Age"].replace(25, 30)
df["Age"].replace({25: 30, 35: 40})
```

Using `apply()`:

```python
df["Age"].apply(lambda x: x * 2)
```

Using `map()`:

```python
df["Age"].map(lambda x: x + 1)
```

Conditional transformation:

```python
df["Age"].apply(lambda x: "Adult" if x >= 18 else "Minor")
```

---

## 8. Arithmetic Operations

```python
df["Age"] + 5
df["Age"] - 5
df["Age"] * 2
df["Age"] / 2
df["Age"] // 2
df["Age"] % 2
df["Age"] ** 2
```

Equivalent methods:

```python
df["Age"].add(5)
df["Age"].sub(5)
df["Age"].mul(2)
df["Age"].div(2)
df["Age"].floordiv(2)
df["Age"].mod(2)
df["Age"].pow(2)
```

---

## 9. Cumulative Methods

```python
df["Age"].cumsum()
df["Age"].cumprod()
df["Age"].cummin()
df["Age"].cummax()
```

---

## 10. Difference and Percentage Change

```python
df["Age"].diff()
df["Age"].pct_change()
```

---

## 11. Position and Index Methods

```python
df["Age"].idxmin()
df["Age"].idxmax()
df["Age"].first_valid_index()
df["Age"].last_valid_index()
```

Access values by label:

```python
df["Age"].loc[0]
```

Access values by position:

```python
df["Age"].iloc[0]
df["Age"].iloc[0:5]
```

---

## 12. Duplicate Methods

```python
df["Age"].duplicated()
df["Age"].drop_duplicates()
```

Count duplicated values:

```python
df["Age"].duplicated().sum()
```

---

## 13. Sampling

```python
df["Age"].sample()
df["Age"].sample(5)
df["Age"].sample(frac=0.10)
```

For reproducible sampling:

```python
df["Age"].sample(5, random_state=42)
```

---

## 14. Conversion Methods

```python
df["Age"].to_list()
df["Age"].tolist()
df["Age"].to_numpy()
df["Age"].to_frame()
df["Age"].to_dict()
df["Age"].to_string()
```

---

## 15. String Column Methods

For a text column:

```python
df["Name"].str.lower()
df["Name"].str.upper()
df["Name"].str.title()
df["Name"].str.strip()
df["Name"].str.len()
df["Name"].str.contains("Sara")
df["Name"].str.startswith("S")
df["Name"].str.endswith("a")
df["Name"].str.replace("Sara", "Sarah")
df["Name"].str.split()
df["Name"].str.slice(0, 3)
```

Example filtering:

```python
df[df["Name"].str.contains("Sara", na=False)]
```

---

## 16. Date Column Methods

First convert the column:

```python
df["OrderDate"] = pd.to_datetime(df["OrderDate"])
```

Then use:

```python
df["OrderDate"].dt.year
df["OrderDate"].dt.month
df["OrderDate"].dt.day
df["OrderDate"].dt.day_name()
df["OrderDate"].dt.month_name()
df["OrderDate"].dt.quarter
df["OrderDate"].dt.weekday
df["OrderDate"].dt.is_month_end
df["OrderDate"].dt.is_month_start
```

---

## 17. Grouping With a Selected Column

```python
df.groupby("City")["Age"].mean()
df.groupby("City")["Age"].median()
df.groupby("City")["Age"].min()
df.groupby("City")["Age"].max()
df.groupby("City")["Age"].sum()
df.groupby("City")["Age"].count()
```

Multiple aggregations:

```python
df.groupby("City")["Age"].agg(["count", "mean", "median", "min", "max"])
```

---

## 18. Correlation and Covariance

```python
df["Age"].corr(df["Score"])
df["Age"].cov(df["Score"])
```

---

## 19. Boolean Methods

```python
df["Age"].eq(30)
df["Age"].ne(30)
df["Age"].gt(30)
df["Age"].ge(30)
df["Age"].lt(30)
df["Age"].le(30)
```

---

## 20. Renaming and Copying

```python
df["Age"].rename("CustomerAge")
df["Age"].copy()
```

---

## 21. Resetting the Index

```python
df["Age"].reset_index()
df["Age"].reset_index(drop=True)
```

---

## 22. Combining Series

```python
df["Age"].combine_first(df["BackupAge"])
```

---

## 23. Binning Numerical Values

Using `pd.cut()`:

```python
pd.cut(
    df["Age"],
    bins=[0, 18, 30, 50, 100],
    labels=["Child", "Young Adult", "Adult", "Senior"]
)
```

Using `pd.qcut()`:

```python
pd.qcut(df["Age"], q=4)
```

---

## 24. Display All Available Series Methods

```python
dir(df["Age"])
```

In Jupyter Notebook or VS Code:

```python
df["Age"].
```

Press **Tab** after the dot to display available methods.

---

## Important Note

The methods you can use depend on the column's data type:

- Numerical column: `mean()`, `median()`, `std()`, `sum()`
- Text column: `.str.lower()`, `.str.contains()`, `.str.strip()`
- Date column: `.dt.year`, `.dt.month`, `.dt.day_name()`
- Any Series: `head()`, `tail()`, `isna()`, `value_counts()`, `sort_values()`