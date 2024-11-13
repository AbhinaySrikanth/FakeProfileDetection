import pickle

from flask import Flask, request, jsonify

from Model import prediction, query

# Load the model
model3 = pickle.load(open('model.pkl', 'rb'))

app = Flask(__name__)


@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Extract JSON data from the request
        data = request.get_json()

        # Extract inputs from the JSON data
        profile_pic = int(data.get('profile_picture', 0))  # 1 for yes, 0 for no
        username = data.get('username', '')  # abhinay_345
        fullname = data.get('fullname', '')  # abhinay
        description = data.get('description', '')  # i love bikes
        external_url = int(data.get('external_url', 0))  # 1 for yes, 0 for no
        private = int(data.get('is_private', 0))  # 1 for yes, 0 for no
        number_of_posts = int(data.get('posts', -1))  # 10
        number_of_followers = int(data.get('followers', -1))  # 522
        number_of_following = int(data.get('following', -1))  # 576

        # Process inputs
        num_numerical_username = sum(c.isdigit() for c in username)
        num_alphabets_username = sum(c.isalpha() for c in username)
        ratio_numerical_username = num_numerical_username / len(username) if len(username) > 0 else 0

        fullname_tokens = len(fullname.split())
        num_numerical_fullname = sum(c.isdigit() for c in fullname)
        num_dots_fullname = fullname.count('.')
        ratio_numerical_fullname = num_numerical_fullname / len(fullname) if len(fullname) > 0 else 0
        fullname_equals_username = 1 if fullname == username else 0
        description_length = len(description)


        result_label = prediction(
            username, fullname, description, external_url, private,
            number_of_posts, number_of_followers, number_of_following,
            num_numerical_username, num_alphabets_username, ratio_numerical_username,
            fullname_tokens, num_numerical_fullname, num_dots_fullname,
            ratio_numerical_fullname, fullname_equals_username, description_length
        )

        
        if result_label == 'not fake':
            input_query = query(
                profile_pic, ratio_numerical_username, fullname_tokens,
                ratio_numerical_fullname, fullname_equals_username, description_length,
                external_url, private, number_of_posts, number_of_followers, number_of_following
            )
            result_label = model3(model3, input_query)

        return jsonify({'prediction': result_label})
    except Exception as e:
        return jsonify({'error': str(e)})


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)
