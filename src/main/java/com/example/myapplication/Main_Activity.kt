package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var profilePictureRadioGroup: RadioGroup
    private lateinit var externalUrlRadioGroup: RadioGroup
    private lateinit var privateGroup: RadioGroup
    private lateinit var usernameEditText: EditText
    private lateinit var fullnameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var postsEditText: EditText
    private lateinit var followersEditText: EditText
    private lateinit var followingEditText: EditText
    private lateinit var predictButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        profilePictureRadioGroup = findViewById(R.id.profilePictureRadioGroup)
        externalUrlRadioGroup = findViewById(R.id.ExternalUrlRadioGroup)
        privateGroup = findViewById(R.id.PrivateGroup)
        usernameEditText = findViewById(R.id.username)
        fullnameEditText = findViewById(R.id.Fullname)
        descriptionEditText = findViewById(R.id.Description)
        postsEditText = findViewById(R.id.Posts)
        followersEditText = findViewById(R.id.Followers)
        followingEditText = findViewById(R.id.Following)
        predictButton = findViewById(R.id.predictButton)

        predictButton.setOnClickListener {
            val profilePicture = if (profilePictureRadioGroup.checkedRadioButtonId == R.id.profilePictureYes) 1 else 0
            val externalUrl = if (externalUrlRadioGroup.checkedRadioButtonId == R.id.ExternalUrlYes) 1 else 0
            val isPrivate = if (privateGroup.checkedRadioButtonId == R.id.PrivateYes) 1 else 0
            val username = usernameEditText.text.toString()
            val fullname = fullnameEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val posts = postsEditText.text.toString().toIntOrNull() ?: -1
            val followers = followersEditText.text.toString().toIntOrNull() ?: -1
            val following = followingEditText.text.toString().toIntOrNull() ?: -1

            if (username.isEmpty() || fullname.isEmpty() || description.isEmpty() || posts == -1 || followers == -1 || following == -1) {
                Toast.makeText(this, "Please fill all the fields correctly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isFake = detectFakeAccount(username, fullname, description, posts, followers, following, externalUrl, isPrivate, profilePicture)
            displayPredictionResult(isFake)
        }
    }

    private fun detectFakeAccount(
        username: String,
        fullname: String,
        description: String,
        posts: Int,
        followers: Int,
        following: Int,
        externalUrl: Int,
        isPrivate: Int,
        profilePicture: Int
    ): Boolean {
        val numDotsUsername = username.count { it == '.' }
        val numAlphabetsUsername = username.count { it.isLetter() }
        val numNumericalUsername = username.count { it.isDigit() }
        val numDotsFullname = fullname.count { it == '.' }
        val numNumericalFullname = fullname.count { it.isDigit() }
        val numXFullname = fullname.count { it == 'x' }

        return numDotsUsername >= 1 ||
                numAlphabetsUsername < numNumericalUsername ||
                numDotsFullname > 3 ||
                numNumericalFullname > 3 ||
                (externalUrl == 1 && posts == 0 && followers < 20) ||
                numXFullname > 2 ||
                (posts == 0 && following > 5000 && followers < 20) ||
                fullname.all { it.isDigit() } ||
                username.length < 4 || fullname.length < 4 ||
                description.contains("spam", ignoreCase = true) || description.contains("fake", ignoreCase = true) || description.contains("scam", ignoreCase = true) ||
                (followers < 1 && following < 1) ||
                (isPrivate == 0 && profilePicture == 0)
    }

    private fun displayPredictionResult(isFake: Boolean) {
        val intent = Intent(this, PredictionResultActivity::class.java)
        intent.putExtra("prediction", if (isFake) 1 else 0)
        startActivity(intent)
    }
}
