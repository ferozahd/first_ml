import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import confusion_matrix, classification_report, accuracy_score

# Small dataset
data = pd.DataFrame({
    'Glucose': [85, 89, 140, 130, 120, 150],
    'BMI': [22, 28, 35, 33, 30, 40],
    'Age': [25, 28, 45, 50, 35, 55],
    'Outcome': [0, 0, 1, 1, 0, 1]
})

# Features and target
X = data[['Glucose', 'BMI', 'Age']]
y = data['Outcome']

# Split dataset (we will use 50% for testing to see clearly)
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.5, random_state=42)
print(y_test)
# print(f'X train {X_train}')
# print(f'X Test {X_test}')
# print(f'Y train {y_train}')
# print(f'Y Test {y_test}')
# Scale features
# scaler = StandardScaler()
# X_train = scaler.fit_transform(X_train)
# X_test = scaler.transform(X_test)

# Train Logistic Regression
# model = LogisticRegression()
# model.fit(X_train, y_train)

# Predict
# y_pred = model.predict(X_test)

# Evaluate
# print("Input Test Data:\n", X_test)
# print("Actual Output:\n", y_test.values)
# print("Predicted Output:\n", y_pred)
# print("\nAccuracy: ", accuracy_score(y_test, y_pred))
# print("\nClassification Report:\n", classification_report(y_test, y_pred))
# print("\nConfusion Matrix:\n", confusion_matrix(y_test, y_pred))