# Questions

---
## #Phase 1: Rolling window mental model 
- What is a window
- Fixed size window v/s time based window
- Moving context concept
- Rolling v/s aggregation
- Rolling v/s Expanding v/s Shifting

## #Phase 2: Core Rolling API 
- Understanding Series.rolling()
- Understanding DataFrame.rolling()
- Rolling Object life cycle
- Syntax patterns
- Default behaviours
- Return object
- How rolling connects with aggregation functions


## #Phase 3: Window p[arameters deep drive 
- Window parameter
- Interger windows
- Offset /time windows
- `min_periods()`
- center
- closed
- right closed
- left closed
- both closed
- Neither closed
- Step parameterr
- axis behaviour
- Method parameter


## #Phase 4: Internal Mechines 
- How pandas builds windows
- Window bounderies
- Index-based windows
- Position based windows
- Time based windows
- Handling irregular timestamps
- Window alignment rules
- Missing values inside windows

## #Phase 5: Rolling aggregation methods 
```python
rolling().mean()
rolling().sum()
rolling().min()
rolling().max()
rolling().std()
rolling().var()
rolling().median()
rolling().count()
```
- Mathematical interpretation of each operation

## #Phase 6: Custom rolling logic 
- rolling().apply()
- Writing custom fundcations
- Function input behaviours
- lambda function with rolling
- Return Scalar values
- Complex calculations inside windows
- Custom statistics measures

> Example
 - Custom volatility
 - Custom score calculation
 - anomaly detection logic


## #Phase 7: Advanced custom functions
- Passing additional arguments
- Named function v/s lambda
- Reusable rolling functions
- Performance considerations
- Avoiding unnecessary python loops


## #Phase 8: Time based rolling

- DatetimeIndex requirements
- Rolling by days, hours , minutes
- Difference between rolling(7) and rolling("7D")
- Calendar based windows
- Irregular event data
- Financial time series examples

## #Phase 9: Grouped rolling
- groupby().rolling()
- Independent windows per group
- Multi-user analytics
- Multi device monitoring
- Customer debaviour feature

## #Phase 10: Rolling feature Engineering for Machine learning 
- Lag features
- Moving averages
- Recent activity features
- Rolling statistics
- Trend features
- User-level rolling features
- Preventing data leakage

## #Phase 11: Advanced window concepts 
- Rolling correlation
- Rolling covariance
- Rolling regression ideas
- Weighted rolling calculations
- Exponential weighted windows
- Comparison between rolling and EWM



## #Phase 12 : Performance Engineering 
- Vectorized rolling operations
- When appy() become slow
- Optimizing cutom function
- Memory considerations
- Large dataset strategies

## #Phase 13: Production patterns
- Building resuable rolling pipelines
- Validation of window features
- Handing missing timestamps
- Testing rolling logic
- Documentiong assumptions
  