import numpy as np
import pickle
model3 = pickle.load(open('model.pkl', 'rb'))


def prediction(username, fullname, description, external_url, private,
               number_of_posts, number_of_followers, number_of_following,
               num_numerical_username, num_alphabets_username, ratio_numerical_username,
               fullname_tokens, num_numerical_fullname, num_dots_fullname,
               ratio_numerical_fullname, fullname_equals_username, description_length):
    # Define test case logic for simulating fake account detection
    if (
            username.count('.') >= len(username) or  # Username is entirely dots
            num_alphabets_username < num_numerical_username or  # Username has more numbers than alphabets
            num_dots_fullname > 3 or  # Fullname has too many dots
            num_numerical_fullname > 3 or  # Fullname has too many numbers
            (external_url == 1 and num_dots_fullname > 0) or  # Fullname contains dots and there is an external URL
            (
                    number_of_posts == 0 and number_of_following > 5000 and number_of_followers < 20) or  # No posts, high followings, low followers
            fullname.isdigit() or  # Fullname is all numbers
            len(username) < 4 or len(fullname) < 4 or  # Username or fullname without 4 or more characters
            any(fake_word in description.lower() for fake_word in ['spam', 'fake', 'scam'])
    # Description contains fake-related words
    ):
        return 'fake'
    else:
        return 'not fake'


def query(profile_pic, ratio_numerical_username, fullname_tokens,
          ratio_numerical_fullname, fullname_equals_username, description_length,
          external_url, private, number_of_posts, number_of_followers, number_of_following):
    # Prepare input array for prediction
    input_query = np.array([
        profile_pic,
        ratio_numerical_username,
        fullname_tokens,
        ratio_numerical_fullname,
        fullname_equals_username,
        description_length,
        external_url,
        private,
        number_of_posts,
        number_of_followers,
        number_of_following
    ]).reshape(1, -1)

    return input_query


def model1(model, input_query):
    # Predict using the loaded model
    result = model3.predict(input_query)

    # Check the shape of the result and process accordingly
    if result.ndim == 1 or result.shape[1] == 1:
        result = (result > 0.5).astype(int)
        result_label = 'fake' if result[0] == 1 else 'not fake'
    else:
        result_label = 'fake' if np.argmax(result) == 1 else 'not fake'

    return result_label
