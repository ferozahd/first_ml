# Heart Disease Dataset Feature Analysis

## Target Variable: HeartDisease

Binary classification target:

| Value | Meaning |
|---|---|
| 0 | No Heart Disease |
| 1 | Heart Disease |

---

# Feature Analysis

## 1. Age

### Type
Numerical

### Relationship
Age is related because:
- Arteries become less elastic with aging.
- Plaque accumulation increases.
- Cardiovascular system weakens over time.

Expected relationship:

```
Age ↑ → Heart Disease Risk ↑
```

Expected Importance:
Medium

---

## 2. Sex

### Type
Categorical

Values:
- Male
- Female

Relationship:
- Male patients generally develop cardiovascular disease earlier.
- Premenopausal females may have hormonal protection.

Expected Importance:
Medium

---

## 3. ChestPainType

### Type
Categorical

| Code | Meaning |
|---|---|
| TA | Typical Angina |
| ATA | Atypical Angina |
| NAP | Non-Anginal Pain |
| ASY | Asymptomatic |

Relationship:

```
Abnormal ChestPainType
          ↓
Higher HeartDisease probability
```

Expected Importance:
Very High

---

## 4. RestingBP

### Type
Numerical

Relationship:
High blood pressure can:
- Damage arteries
- Increase heart workload
- Increase cardiovascular risk

Expected:

```
RestingBP ↑ → Risk ↑
```

Expected Importance:
Low-Medium

---

## 5. Cholesterol

### Type
Numerical

Relationship:

```
LDL ↑
 ↓
Plaque formation
 ↓
Blocked arteries
 ↓
Heart Disease
```

Data Quality Check:

```
Cholesterol == 0
```

Zero values require validation because they may represent missing data.

Expected Importance:
Needs Validation

---

## 6. FastingBS

### Type
Binary

| Value | Meaning |
|---|---|
| 0 | Fasting blood sugar ≤120 mg/dl |
| 1 | Fasting blood sugar >120 mg/dl |

Relationship:

- Artery damage
- Inflammation
- Increased cardiovascular risk

Expected:

```
FastingBS = 1
        ↓
Higher Risk
```

Expected Importance:
Medium

---

## 7. RestingECG

### Type
Categorical

Information:
- Electrical abnormalities
- Heart muscle enlargement
- Previous heart damage

Expected:

```
Abnormal ECG
      ↓
Risk ↑
```

Expected Importance:
Low-Medium

---

## 8. MaxHR

### Type
Numerical

Meaning:
Maximum heart rate achieved during exercise.

Healthy response:

```
Exercise
   ↓
Heart rate increases properly
```

Weak response:

```
Exercise
   ↓
Low maximum heart rate
```

Expected:

```
MaxHR ↓ → Risk ↑
```

Expected Importance:
High

---

## 9. ExerciseAngina

### Type
Binary

| Value | Meaning |
|---|---|
| Y | Chest pain during exercise |
| N | No chest pain |

Relationship:

```
Exercise
   ↓
Chest Pain
   ↓
Possible reduced blood supply
```

Expected:

```
ExerciseAngina = Yes
          ↓
High Risk
```

Expected Importance:
Very High

---

## 10. Oldpeak

### Type
Numerical

Meaning:
ST depression caused by exercise.

Expected:

```
Oldpeak ↑
    ↓
Abnormal heart response ↑
    ↓
Risk ↑
```

Expected Importance:
High

---

## 11. ST_Slope

### Type
Categorical

| Value | Meaning |
|---|---|
| Up | Normal recovery pattern |
| Flat | Possible abnormality |
| Down | More abnormal pattern |

Relationship:

```
Abnormal ST_Slope
        ↓
Heart Disease probability ↑
```

Expected Importance:
Very High

---

# Expected Feature Ranking (Before Testing)

| Importance | Features |
|---|---|
| Very High | ST_Slope, ExerciseAngina, ChestPainType |
| High | Oldpeak, MaxHR |
| Medium | Age, Sex, FastingBS |
| Low-Medium | RestingECG, RestingBP |
| Need Validation | Cholesterol |

---

# ML Analysis Workflow

1. Check target balance.
2. Check missing and invalid values.
3. Analyze feature relationship with HeartDisease.
4. Encode categorical variables.
5. Train baseline models.
6. Compare feature importance.