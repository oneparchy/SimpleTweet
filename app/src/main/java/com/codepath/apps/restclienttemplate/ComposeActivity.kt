package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ComposeActivity : AppCompatActivity() {

    lateinit var etNewTweet: EditText
    lateinit var btnTweet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etNewTweet = findViewById(R.id.etNewTweet)
        btnTweet = findViewById(R.id.btnTweet)

        //Handle the user's click on the tweet button
        btnTweet.setOnClickListener {
            //capture the content of the new tweet
            val tweetContent = etNewTweet.text.toString()
            //Ensure the tweet is not blank
            if (tweetContent.isEmpty()) {
                Toast.makeText(this,"Tweet cannot be blank!",Toast.LENGTH_SHORT).show()
            } else
            //Make sure the tweet is within allowed character count
            if (tweetContent.length > 140) {
                Toast.makeText(this,"Tweet is too long!",Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, tweetContent, Toast.LENGTH_SHORT).show()
                //send to Twitter API to publish the tweet
            }
        }

    }
}