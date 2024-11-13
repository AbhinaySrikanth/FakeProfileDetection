# Fake Social Media Profile Detection

This project is an application designed to detect and report fake profiles on social media, specifically tailored to identify fake Instagram accounts. Utilizing machine learning algorithms, this app flags suspicious profiles based on various criteria to improve platform security and help users avoid misleading profiles.

## Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Machine Learning Model](#machine-learning-model)
- [Backend and API Integration](#backend-and-api-integration)
- [Setup and Installation](#setup-and-installation)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Profile Detection**: Uses a trained machine learning model to classify profiles as either "fake" or "not fake."
- **Custom Criteria for Fake Profiles**: Additional logic to detect suspicious profiles based on:
  - External URL without posts and fewer than 20 followers.
  - Full name containing more than two "x" characters.
  - More than three dots or numbers in the full name.
- **Real-Time Prediction API**: RESTful API for profile detection, deployable to mobile and web platforms.
- **User-Friendly Interface**: Mobile app interface to flag, review, and report fake accounts.
- **Detailed Analytics**: Performance metrics, including precision, recall, and confusion matrix, to measure model accuracy.

## Project Structure

- **Machine Learning Model**: Trained using TensorFlow and Keras, the model predicts the probability of a profile being fake.
- **Backend API**: Flask API developed in Python, deployed as an endpoint for the mobile application.
- **Mobile Application**: Android app built with Kotlin and integrated with the backend API using Retrofit.

## Machine Learning Model

The model is trained to recognize patterns associated with fake profiles using a dataset of features extracted from user profiles. Key steps include:
1. **Data Preprocessing**: Handled missing values, filtered unnecessary columns, and standardized inputs.
2. **Model Training**: Keras-based neural network model trained and saved as a `.pkl` file.
3. **Testing**: Evaluated using metrics such as TPR, FPR, precision, and recall to ensure accuracy.
4. **Deployment**: Model saved in serialized format for integration with the Flask API.

## Backend and API Integration

The backend uses Flask to serve predictions to the Android app:
- **Model Loading**: Loads the pre-trained `.pkl` file to make predictions.
- **API Endpoints**: Provides a POST endpoint for profile analysis, returning JSON responses with prediction results.
- **Android Integration**: Integrated with the Android app using Retrofit for smooth communication with the Flask API.

## Setup and Installation

### Prerequisites
- **Python 3.8+**
- **Android Studio** for mobile app development
- **Postman** for API testing

### Installation Steps
1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/fake-profile-detection.git
   cd fake-profile-detection
   ```

2. **Install Backend Dependencies**
   ```bash
   pip install -r requirements.txt
   ```

3. **Run the Flask API**
   ```bash
   python app.py
   ```

4. **Android Setup**
   - Open the Android project in Android Studio.
   - Configure the Retrofit client to point to your local API endpoint.

5. **Testing the API**
   - Use Postman to test the `/predict` endpoint to ensure it returns expected results.

## Technologies Used

- **Machine Learning**: Python, TensorFlow, Keras
- **Backend**: Flask for API, Pickle for model serialization
- **Mobile Application**: Kotlin, Retrofit for API integration
- **Database**: Firebase or SQLite (if applicable for data storage)

## Contributing

Contributions are welcome! Please feel free to fork this repository, make your updates, and submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

